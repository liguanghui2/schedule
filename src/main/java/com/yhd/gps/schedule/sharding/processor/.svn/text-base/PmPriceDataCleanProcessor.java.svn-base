package com.yhd.gps.schedule.sharding.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.backendprice.vo.GpsProductVo;
import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.busyservice.dao.BusyProductDao;
import com.yhd.gps.busyservice.dao.PmPriceDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.vo.PmPriceVo;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;

/**
 * 
 * pm_price表数据清理（永久下架商品）
 * @Author  liling
 * @CreateTime  2017-7-12 下午05:55:18
 */
public class PmPriceDataCleanProcessor extends ShardingDataProcessor {

    private static final Logger LOG = Logger.getLogger(PmPriceDataCleanProcessor.class);

    private BusyProductDao busyProductDao;
    private PmPriceDao pmPriceDao;
    private BusyMiscConfigDao busyMiscConfigDao;

    private String bussinessType;
    private Integer pageSize;

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected void processCore(int shardingIndex, CountDownLatch finishSignal) throws InterruptedException, ExecutionException {
        cleanHistoryPmPriceData();

        finishSignal.countDown();
    }

    // 清理pm_price数据
    private void cleanHistoryPmPriceData() {
        Long start = System.currentTimeMillis();
        try {
            // 上一次清理的时间
            BusyMiscConfigVo cleanPmPriceDataLastTime = busyMiscConfigDao
                    .getBusyMiscConfigVoByKey(ScheduleConstants.CLEAN_PM_PRICE_DATA_LAST_TIME);
            if(cleanPmPriceDataLastTime == null) {
                return;
            }

            // 上一次清理的时间无效时，则处理所有历史数据
            Date lastTime = null;
            if(cleanPmPriceDataLastTime.getItemEnabled()
               && StringUtils.isNotBlank(cleanPmPriceDataLastTime.getItemValue())) {
                lastTime = ScheduleDateUtils.parseDate(cleanPmPriceDataLastTime.getItemValue());
            }

            // 本次处理的截止时间是当天0点
            String today = ScheduleDateUtils.formatDay(new Date());
            Date endTime = ScheduleDateUtils.parseDate(today);
            if(lastTime != null && lastTime.compareTo(endTime) >= 0) {
                return;
            }

            Long offset = 0L;
            long deleteNum = 0;
            while(true) {
                // 查询指定时间段内永久下架的商品
                List<GpsProductVo> products = busyProductDao.getPageOffSaleProduct(lastTime, endTime, offset, pageSize,
                        -1);
                if(CollectionUtils.isEmpty(products)) {
                    break;
                }

                List<Long> productIds = new ArrayList<Long>();
                for(GpsProductVo productVo : products) {
                    productIds.add(productVo.getId());
                }
                // 取List中最后一个pmId值作为新的offset
                offset = products.get(products.size() - 1).getId();

                // 查询待删除的pm_price表数据
                List<PmPriceVo> pmPriceVos = pmPriceDao.getPmPricesByProductIds(productIds);
                if(CollectionUtils.isEmpty(pmPriceVos)) {
                    continue;
                }

                deleteNum += pmPriceVos.size();
                List<Long> pmPriceIds = new ArrayList<Long>();
                for(PmPriceVo pmPriceVo : pmPriceVos) {
                    pmPriceIds.add(pmPriceVo.getId());
                }

                // 待删除的pm_price表数据进行分页，调GPS接口删除，统一记录日志
                List<List<Long>> pmPriceList = GPSUtils.split(pmPriceIds, pageSize);
                for(List<Long> subPmPriceIds : pmPriceList) {
                    // 调用GPS删除接口
                    BusyStockClientUtil.getBusyPriceFacadeService().batchDeletePmPriceVoByIds(subPmPriceIds);
                }
            }

            // 更新上一次清理的时间
            cleanPmPriceDataLastTime.setItemValue(today);
            cleanPmPriceDataLastTime.setItemEnabled(true);
            busyMiscConfigDao.updateBusyMiscConfigVo(cleanPmPriceDataLastTime);

            LOG.debug("---###---[GPS]清理pm_price数据定时任务开始执行结束:---总共耗费时间:" + ((System.currentTimeMillis() - start) / 1000)
                      + " 秒 ,共清理数据" + deleteNum + "条 ---###---");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected String getBussinessType() {
        return bussinessType;
    }

    public void setBusyProductDao(BusyProductDao busyProductDao) {
        this.busyProductDao = busyProductDao;
    }

    public void setPmPriceDao(PmPriceDao pmPriceDao) {
        this.pmPriceDao = pmPriceDao;
    }

    public void setBusyMiscConfigDao(BusyMiscConfigDao busyMiscConfigDao) {
        this.busyMiscConfigDao = busyMiscConfigDao;
    }

}
