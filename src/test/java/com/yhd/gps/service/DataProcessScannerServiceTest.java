package com.yhd.gps.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.busyservice.service.PromRuleSoldNumResetService;
import com.yhd.gps.busyservice.service.impl.DataProcessScannerServiceImpl;

public class DataProcessScannerServiceTest extends BaseSpringWithUnitilsTest {

	@SpringBeanByName
    private DataProcessScannerServiceImpl dataProcessScannerService;

    private PromRuleSoldNumResetService promRuleSoldNumResetService;
    
    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
        promRuleSoldNumResetService = mock(PromRuleSoldNumResetService.class);
        dataProcessScannerService.setPromRuleSoldNumResetService(promRuleSoldNumResetService);
        
        when(promRuleSoldNumResetService.resetPromRuleSoldNum(Mockito.anyList(), Mockito.anySet())).thenReturn(1);
        
    }
    
    @Test
    @DataSet({"/data/service/DataProcessScannerServiceTest/insertScannerByRuleId.xml" })
    @TestDataSource("public")
    public void insertScannerByRuleId() {
        Long ruleId = 99L;
        Long result = null;
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue("写入扫描表失败:查不到促销,ruleId=99".equals(e.getMessage()));
        }

        // is_promotion!=6
        ruleId = 1L;
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue("写入扫描表失败:限购类型不是6、促销过期或者促销失效,ruleId=1".equals(e.getMessage()));
        }

        // 促销过期
        ruleId = 2L;
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue("写入扫描表失败:限购类型不是6、促销过期或者促销失效,ruleId=2".equals(e.getMessage()));
        }

        // 促销失效
        ruleId = 3L;
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue("写入扫描表失败:限购类型不是6、促销过期或者促销失效,ruleId=3".equals(e.getMessage()));
        }

        // 非SAM商品
        ruleId = 4L;
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue("写入扫描表失败:非SAM商品或者切片数为0,ruleId=4".equals(e.getMessage()));
        }
    }

    @Test
    @DataSet({"/data/service/DataProcessScannerServiceTest/insertScannerByRuleId001.xml" })
    @TestDataSource("public")
    public void insertScannerByRuleId001() {
        Long ruleId = 1L;
        Long result = null;
        // 已存在扫描记录
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue("写入扫描表失败:已存在该扫描记录,scannerId=1".equals(e.getMessage()));
        }

        // 新增抛异常，内存数据库不支持语法： SELECT LAST_INSERT_ID() AS ID
        ruleId = 2L;
        try {
            result = dataProcessScannerService.insertScannerByRuleId(ruleId);
            assertNotNull(result);
            assertTrue(result > 0);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("user lacks privilege or object not found: LAST_INSERT_ID"));
        }
    }
}
