package com.yhd.gps.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class JasonTest  extends AbstractDependencyInjectionSpringContextTests {
    
    @Test
    public void testVo(){
        BSProductPromRuleVo productPromRuleVo = new BSProductPromRuleVo();
        productPromRuleVo.setPmId(122L);
        String pStr = JSON.toJSONString(productPromRuleVo);
        System.out.println(pStr);
        JSONObject vo2 = JSON.parseObject(pStr);
        BSProductPromRuleVo vo = JSON.toJavaObject(vo2, BSProductPromRuleVo.class);
        assertEquals(vo.getPmId(), productPromRuleVo.getPmId());
    }
}
