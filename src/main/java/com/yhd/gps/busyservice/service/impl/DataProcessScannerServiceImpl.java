package com.yhd.gps.busyservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyMerchantDao;
import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.BusyProductPromRuleDao;
import com.yhd.gps.busyservice.service.DataProcessScannerService;
import com.yhd.gps.busyservice.service.PromRuleSoldNumResetService;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.gps.schedule.vo.BusyPmInfoVo;
import com.yhd.gps.schedule.vo.DataProcessScannerVo;
import com.yhd.gps.schedule.vo.ProductPromRule4ResetSoldNumVo;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.busy.exception.BusyException;

public class DataProcessScannerServiceImpl implements DataProcessScannerService {

    private static final Log log = LogFactory.getLog(DataProcessScannerServiceImpl.class);

    private BusyProductPromRuleDao busyProductPromRuleDao;
    private DataProcessScannerDao dataProcessScannerDao;
    private BusyPmInfoDao busyPmInfoDao;
    private BusyMerchantDao busyMerchantDao;
    private PromRuleSoldNumResetService promRuleSoldNumResetService;

    public void setBusyProductPromRuleDao(BusyProductPromRuleDao busyProductPromRuleDao) {
        this.busyProductPromRuleDao = busyProductPromRuleDao;
    }

    public void setDataProcessScannerDao(DataProcessScannerDao dataProcessScannerDao) {
        this.dataProcessScannerDao = dataProcessScannerDao;
    }

    public void setBusyPmInfoDao(BusyPmInfoDao busyPmInfoDao) {
        this.busyPmInfoDao = busyPmInfoDao;
    }

    public void setBusyMerchantDao(BusyMerchantDao busyMerchantDao) {
        this.busyMerchantDao = busyMerchantDao;
    }

    public void setPromRuleSoldNumResetService(PromRuleSoldNumResetService promRuleSoldNumResetService) {
        this.promRuleSoldNumResetService = promRuleSoldNumResetService;
    }

    @Override
    public Long insertScannerByRuleId(Long ruleId) throws Exception {
        Long result = 0L;
        if(ruleId == null || ruleId <= 0) {
            return result;
        }
        try {
            List<Long> ruleIds = new ArrayList<Long>(1);
            ruleIds.add(ruleId);
            List<ProductPromRule4ResetSoldNumVo> ruleVos = busyProductPromRuleDao
                    .batchGetProductPromRuleListByIds(ruleIds);
            if(CollectionUtils.isEmpty(ruleVos)) {
                throw new BusyException("写入扫描表失败:查不到促销,ruleId=" + ruleId);
            }

            ProductPromRule4ResetSoldNumVo ruleVo = ruleVos.get(0);
            // is_promotion!=6,促销过期或促销失效,不记入扫描表
            if(ruleVo.getPromoteType() != BusyConstants.RULE_LIMIT_ALL_USER_ORDER_NOTALLOWBASEPRICE
               || ruleVo.getPromEndTime().before(new Date())
               || ruleVo.getRuleStatus() != ScheduleConstants.PRODUCT_PROM_RULE_STATUS_IS_VALID) {
                throw new BusyException("写入扫描表失败:限购类型不是6、促销过期或者促销失效,ruleId=" + ruleId);
            }

            BusyPmInfoVo pmInfoVo = busyPmInfoDao.getPmInfoVo(ruleVo.getPmId());
            // 不是sam商品,无切片或切片数小于=0
            Integer shardingCount = null;
            if(pmInfoVo == null
               || !BusyConstants.SITE_ID_SAM.equals(pmInfoVo.getSiteId())
               || (shardingCount = MiscConfigUtils
                       .getShardingCount(ScheduleConstants.SHARDING_COUNT_TABLE_NAME_PRODUCT_PROM_RULE)) == null
               || shardingCount <= 0) {
                throw new BusyException("写入扫描表失败:非SAM商品或者切片数为0,ruleId=" + ruleId);
            }

            DataProcessScannerVo scannerVo = dataProcessScannerDao.getDataProcessScannerByRefId(
                    BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, ruleVo.getId());
            if(scannerVo != null) {
                throw new BusyException("写入扫描表失败:已存在该扫描记录,scannerId=" + scannerVo.getId());
            }

            // 验证通过，开始写入扫描表
            scannerVo = new DataProcessScannerVo();
            scannerVo.setBusinessType(BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM);
            scannerVo.setRefId(ruleVo.getId());
            scannerVo.setNextProcessTime(new Date());// 当前时间，写入后可以手工通过kira触发定时器重置该促销已售数量
            scannerVo.setShardingIndex((int) (ruleVo.getId() % shardingCount));
            result = dataProcessScannerDao.insertDataProcessScanner(scannerVo);

            // 最后触发下重置soldNum
            List<DataProcessScannerVo> dataProcessScannerVos = new ArrayList<DataProcessScannerVo>(1);
            scannerVo.setId(result);
            dataProcessScannerVos.add(scannerVo);
            promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, new HashSet<Long>(1));
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public int initScanner4SamPromRule() throws Exception {
        int result = 0;
        Long startTime = System.currentTimeMillis();
        List<Long> merchantIds = busyMerchantDao.batchGetMerchantIds4Sam();
        // 分批执行
        List<List<Long>> list = GPSUtils.split(merchantIds, BusyConstants.MAX_QUERY_SIZE);
        for(List<Long> subList : list) {
            int offset = 0;
            final int pageSize = 200;
            List<ProductPromRule4ResetSoldNumVo> ruleVos = busyProductPromRuleDao
                    .batchGetProductPromRuleListByMerchantIds(subList, offset, pageSize);
            while(CollectionUtils.isNotEmpty(ruleVos)) {
                int count = initScanner4SamPromRuleCore(ruleVos);
                // 记录更新记录总数
                result += count;
                // 睡一会，避免数据库压力过大
                Thread.sleep(5000);
                // 查询下一页数据
                offset += pageSize;
                ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByMerchantIds(subList, offset, pageSize);
            }
        }
        Long endTime = System.currentTimeMillis();
        log.error("共新增数据：" + result + "条,耗时：" + (endTime - startTime) + "毫秒。");
        return result;
    }

    private int initScanner4SamPromRuleCore(List<ProductPromRule4ResetSoldNumVo> ruleVos) {
        int result = 0;
        Long startTime = System.currentTimeMillis();
        if(CollectionUtils.isEmpty(ruleVos)) {
            return result;
        }
        Set<Long> ruleIds = new HashSet<Long>(ruleVos.size());
        for(ProductPromRule4ResetSoldNumVo ruleVo : ruleVos) {
            ruleIds.add(ruleVo.getId());
        }
        // 查扫描表
        List<DataProcessScannerVo> scannerVos = dataProcessScannerDao
                .batchGetDataProcessScannerByRefIds(
                        BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, new ArrayList<Long>(
                            ruleIds));

        Set<Long> needAddRuleIds = new HashSet<Long>(ruleVos.size());
        if(CollectionUtils.isEmpty(scannerVos)) {
            // 如果扫描表查不到，则全部加入待新增列表
            needAddRuleIds.addAll(ruleIds);
        } else {
            // 否则查到的ruleId从ruleIds移除，ruleIds剩余部分加入待新增列表
            for(DataProcessScannerVo scannerVo : scannerVos) {
                ruleIds.remove(scannerVo.getRefId());
            }
            needAddRuleIds.addAll(ruleIds);
        }

        // 没有需要新增的数据
        if(CollectionUtils.isEmpty(needAddRuleIds)) {
            return result;
        }

        // 查切片
        Integer shardingCount = MiscConfigUtils
                .getShardingCount(ScheduleConstants.SHARDING_COUNT_TABLE_NAME_PRODUCT_PROM_RULE);
        if(shardingCount == null || shardingCount <= 0) {
            return result;
        }

        // 初始化新增数据
        List<DataProcessScannerVo> addScannerVos = new ArrayList<DataProcessScannerVo>(needAddRuleIds.size());
        for(Long ruleId : needAddRuleIds) {
            DataProcessScannerVo scannerVo = new DataProcessScannerVo();
            scannerVo.setBusinessType(BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM);
            scannerVo.setRefId(ruleId);
            scannerVo.setNextProcessTime(new Date());
            scannerVo.setShardingIndex((int) (ruleId % shardingCount));
            addScannerVos.add(scannerVo);
        }
        // 将待新增的数据新增到扫描表
        result = dataProcessScannerDao.batchInsertDataProcessScanner(addScannerVos);
        Long endTime = System.currentTimeMillis();
        log.error("循环新增数据：" + result + "条,耗时：" + (endTime - startTime) + "毫秒。");
        return result;
    }
}
