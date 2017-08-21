package com.yhd.gps.common;

import static org.junit.Assert.*;

import org.junit.Test;

import com.yhd.gps.schedule.common.JsonUtil;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

/**
 * @author:liguanghui1
 * @date ：2016-9-6 下午02:08:23
 */
public class JsonUtilTest {

    @Test
    public void testJsonUtil() {
        BSProductPromRuleVo productPromRuleVo = new BSProductPromRuleVo();
        productPromRuleVo.setPmId(122L);
        String result = JsonUtil.toJson(productPromRuleVo);
        assertNotNull(result);
    }
}
