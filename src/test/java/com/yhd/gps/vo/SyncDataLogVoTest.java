package com.yhd.gps.vo;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.SyncDataLogVo;

/**
 * 数据同步错误日志
 * @author wangxiaowu
 * 
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class SyncDataLogVoTest extends AbstractDependencyInjectionSpringContextTests {
    
    @Test
    public void testVo(){
        SyncDataLogVo vo = new SyncDataLogVo();
        vo.setCreateTime(new Date());
        vo.setId(1L);
        vo.setKeyId(2L);
        
        System.out.println(vo.getCreateTime() + "\t" + vo.getId() + "\t" + vo.getKeyId());
    }

}
