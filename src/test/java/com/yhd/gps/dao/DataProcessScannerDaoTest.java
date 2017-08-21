package com.yhd.gps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.gps.schedule.vo.DataProcessScannerVo;
import com.yihaodian.busy.common.BusyConstants;

public class DataProcessScannerDaoTest extends BaseSpringTest {

    @Autowired
    private DataProcessScannerDao dataProcessScannerDao;

    @Test
    public void getyDataProcessScanner() {
        Integer businessType = 99;
        int shardingIndex = 99;
        int pageSize = 200;
        List<DataProcessScannerVo> scannerVos = dataProcessScannerDao.getDataProcessScanner(businessType,
                shardingIndex, pageSize);
        assertNotNull(scannerVos);
        assertTrue(scannerVos.size() > 0);
    }

    @Test
    public void batchUpdateDataProcessScannerNextProcessTimeByIds() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        Date nextProcessTime = new Date();
        int result = dataProcessScannerDao.batchUpdateDataProcessScannerNextProcessTimeByIds(ids, nextProcessTime);
        assertNotNull(result);
        assertTrue(result == 1);
    }

    /**
     * 先新增再删除
     */
    @Test
    public void batchDeleteDataProcessScannerByIds() {
        DataProcessScannerVo scannerVo = new DataProcessScannerVo();
        scannerVo.setBusinessType(9);
        scannerVo.setRefId(9L);
        scannerVo.setIsDeal(1);
        scannerVo.setStartTime(new Date());
        scannerVo.setEndTime(new Date());
        scannerVo.setNextProcessTime(new Date());
        scannerVo.setShardingIndex(9);
        // 新增
        Long scannerId = dataProcessScannerDao.insertDataProcessScanner(scannerVo);
        assertNotNull(scannerId);
        assertTrue(scannerId > 0);

        // 查询
        DataProcessScannerVo addScannerVo = dataProcessScannerDao.getDataProcessScannerByRefId(9, 9L);
        assertNotNull(addScannerVo);
        assertTrue(addScannerVo.getRefId() == 9);

        // 删除
        List<Long> ids = new ArrayList<Long>();
        ids.add(scannerId);
        int result = dataProcessScannerDao.batchDeleteDataProcessScannerByIds(ids);
        assertNotNull(result);
        assertTrue(result == 1);

        // 再查
        DataProcessScannerVo delScannerVo = dataProcessScannerDao.getDataProcessScannerByRefId(9, 9L);
        assertNull(delScannerVo);
    }

    @Test
    public void getDataProcessScannerByRefId() {
        Integer businessType = BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM;
        Long refId = 1L;
        DataProcessScannerVo scannerVo = dataProcessScannerDao.getDataProcessScannerByRefId(businessType, refId);
        assertNotNull(scannerVo);
        assertTrue(scannerVo.getRefId() == 1);
    }

    @Test
    public void queryDataProcessScannerByRefId() {
        Integer businessType = BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM;
        List<Long> refIds = new ArrayList<Long>();
        Long refId = 1L;
        refIds.add(refId);
        List<DataProcessScannerVo> scannerVo = dataProcessScannerDao.batchGetDataProcessScannerByRefIds(businessType,
                refIds);
        assertNotNull(scannerVo);
        assertTrue(scannerVo.get(0).getRefId() == 1);
    }

    @Test
    public void batchInsertDataProcessScanner() {
        DataProcessScannerVo scannerVo = new DataProcessScannerVo();
        scannerVo.setBusinessType(9);
        scannerVo.setRefId(9L);
        scannerVo.setIsDeal(1);
        scannerVo.setStartTime(new Date());
        scannerVo.setEndTime(new Date());
        scannerVo.setNextProcessTime(new Date());
        scannerVo.setShardingIndex(9);
        List<DataProcessScannerVo> scannerVos = new ArrayList<DataProcessScannerVo>();
        scannerVos.add(scannerVo);
        // 新增
        int result = dataProcessScannerDao.batchInsertDataProcessScanner(scannerVos);
        assertNotNull(result);
        assertTrue(result == 1);

        // 查询
        DataProcessScannerVo addScannerVo = dataProcessScannerDao.getDataProcessScannerByRefId(9, 9L);
        assertNotNull(addScannerVo);
        assertTrue(addScannerVo.getRefId() == 9);

        // 删除
        List<Long> ids = new ArrayList<Long>();
        ids.add(addScannerVo.getId());
        result = dataProcessScannerDao.batchDeleteDataProcessScannerByIds(ids);
        assertNotNull(result);
        assertTrue(result == 1);
    }
}
