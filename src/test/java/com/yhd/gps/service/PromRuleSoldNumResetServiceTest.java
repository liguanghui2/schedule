package com.yhd.gps.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.dao.BusyProductPromRuleDao;
import com.yhd.gps.busyservice.service.PromRuleSoldNumResetService;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.gps.schedule.vo.DataProcessScannerVo;
import com.yhd.gps.schedule.vo.ProductPromRule4ResetSoldNumVo;
import com.yhd.gps.utils.DateUtil;
import com.yihaodian.busy.common.BusyConstants;

public class PromRuleSoldNumResetServiceTest extends BaseSpringWithUnitilsTest {

	@SpringBeanByName
    private DataProcessScannerDao dataProcessScannerDao;

    private PromRuleSoldNumResetService promRuleSoldNumResetService;

    @SpringBeanByName
    private BusyProductPromRuleDao busyProductPromRuleDao;

    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
        
        promRuleSoldNumResetService = mock(PromRuleSoldNumResetService.class);
    }
    
    @Test
    @DataSet({"/data/dao/data_process_scanner.xml" })
    @TestDataSource("public")
    public void queryDataProcessScanner() {
        int shardingIndex = 0;
        int pageSize = 200;
        // shardingIndex不同：0和1，只能查出0的
        List<DataProcessScannerVo> dataProcessScannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(dataProcessScannerVos);
        assertTrue(dataProcessScannerVos.size() == 2);

        // businessType不同
        dataProcessScannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_GPS_PROMOTION_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(dataProcessScannerVos);
        assertTrue(dataProcessScannerVos.size() == 1);

        // 下次执行时间超过当前时间，查不到
        dataProcessScannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_GPS_PROMOTION_UPDATE_PROM_TIME, shardingIndex,
                pageSize);
        assertNotNull(dataProcessScannerVos);
        assertTrue(dataProcessScannerVos.size() == 0);
    }

    @Test
    @DataSet({"/data/dao/data_process_scanner.xml" })
    @TestDataSource("public")
    public void batchUpdateDataProcessScannerNextProcessTimeByIds() {
        // 先查一下
        int shardingIndex = 0;
        int pageSize = 200;
        List<DataProcessScannerVo> scannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() == 2);

        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(99L);
        Date tomorrow = ScheduleDateUtils.addDays(ScheduleDateUtils.parseDate(new Date()), 1);
        int result = dataProcessScannerDao.batchUpdateDataProcessScannerNextProcessTimeByIds(ids, tomorrow);
        assertTrue(result == 1);
        // 验证扫描表下次执行时间被更新，查不到更新后的数据
        scannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() == 1);
    }

    @Test
    @DataSet({"/data/dao/data_process_scanner.xml" })
    @TestDataSource("public")
    public void batchDeleteDataProcessScannerByIds() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        ids.add(99L);
        int result = dataProcessScannerDao.batchDeleteDataProcessScannerByIds(ids);
        assertTrue(result == 2);
        // 验证被删除
        int shardingIndex = 0;
        int pageSize = 200;
        List<DataProcessScannerVo> scannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() == 0);
    }

    @Test
    @DataSet({"/data/service/PromRuleSoldNumResetServiceTest/prom_rule_sold_num_reset_service.xml" })
    @TestDataSource("public")
    public void resetPromRuleSoldNum() {
        Set<Long> scannerIds = new HashSet<Long>();
        int shardingIndex = 0;
        int pageSize = 200;

        List<DataProcessScannerVo> dataProcessScannerVos = new ArrayList<DataProcessScannerVo>();
        DataProcessScannerVo scannerVo = new DataProcessScannerVo();
        scannerVo.setId(1L);
        scannerVo.setBusinessType(BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM);
        scannerVo.setRefId(1L);
        scannerVo.setShardingIndex(0);
        scannerVo.setNextProcessTime(DateUtil.addDays(new Date(), -1));
        dataProcessScannerVos.add(scannerVo);

        // 成功重置,sold_num=0
        when(promRuleSoldNumResetService.resetPromRuleSoldNum(Mockito.anyList(), Mockito.anySet())).thenReturn(1);
        int result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        assertTrue(result == 1);
        List<Long> ruleIds = new ArrayList<Long>();
        ruleIds.add(1L);
        List<ProductPromRule4ResetSoldNumVo> ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByIds(ruleIds);
        assertNotNull(ruleVos);
        assertTrue(ruleVos.get(0).getSoldNum() == 2);

        scannerVo.setId(2L);
        scannerVo.setRefId(2L);
        // 重置失败，is_promotion不等于6
        when(promRuleSoldNumResetService.resetPromRuleSoldNum(Mockito.anyList(), Mockito.anySet())).thenReturn(0);
        result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        assertTrue(result == 0);
        ruleIds.clear();
        ruleIds.add(2L);
        ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByIds(ruleIds);
        assertNotNull(ruleVos);
        assertTrue(ruleVos.get(0).getSoldNum() == 2);

        scannerVo.setId(3L);
        scannerVo.setRefId(3L);
        // 重置失败，促销未开始
        result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        assertTrue(result == 0);
        ruleIds.clear();
        ruleIds.add(3L);
        ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByIds(ruleIds);
        assertNotNull(ruleVos);
        assertTrue(ruleVos.get(0).getSoldNum() == 2);

        shardingIndex = 1;
        // 先查一下扫描表记录，用于对比是否删除
        List<DataProcessScannerVo> scannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() == 3);
        scannerVo.setId(4L);
        scannerVo.setRefId(4L);
        // 重置失败，促销已结束
        result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        assertTrue(result == 0);
        ruleIds.clear();
        ruleIds.add(4L);
        ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByIds(ruleIds);
        assertNotNull(ruleVos);
        assertTrue(ruleVos.get(0).getSoldNum() == 2);
        // 促销结束，扫描记录会被删除
        scannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() == 3);

        scannerVo.setId(5L);
        scannerVo.setRefId(5L);
        // 重置失败，促销已无效
        result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        assertTrue(result == 0);
        ruleIds.clear();
        ruleIds.add(5L);
        ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByIds(ruleIds);
        assertNotNull(ruleVos);
        assertTrue(ruleVos.get(0).getSoldNum() == 2);
        // 促销已无效，扫描记录会被删除
        scannerVos = dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() == 3);

        scannerVo.setId(6L);
        scannerVo.setRefId(6L);
        // 重置失败，soldNum=0
        result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        assertTrue(result == 0);
        ruleIds.clear();
        ruleIds.add(2L);
        ruleVos = busyProductPromRuleDao.batchGetProductPromRuleListByIds(ruleIds);
        assertNotNull(ruleVos);
        assertTrue(ruleVos.get(0).getSoldDate().compareTo(new Date()) < 0);
    }
}
