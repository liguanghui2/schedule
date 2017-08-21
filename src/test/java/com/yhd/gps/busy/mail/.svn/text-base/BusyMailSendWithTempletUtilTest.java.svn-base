package com.yhd.gps.busy.mail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.vo.MailTempletVo;
import com.yihaodian.busy.vo.PriceFatMessageVo;

/**
 * @author:liguanghui1
 * @date ：2016-9-5 上午10:47:53
 */
public class BusyMailSendWithTempletUtilTest extends BaseSpringTest {

    @Test
    public void sendEmailWithTempletTest(){
        MailTempletVo vo=new MailTempletVo();
        vo.setContent("这是一封来自GPS定时器的测试邮件");
        vo.setTemplateName("HistoryPriceChangeResult_zh_CN.ftl");
        vo.setTitle("This is a TestEmail!");
        Map<String,Object> map=new HashMap<String,Object>();
        PriceFatMessageVo messageVo=new PriceFatMessageVo();
        messageVo.setPmInfoId(1234L);
        messageVo.setChannelId(11332L);
        messageVo.setProductId(12345L);
        BigDecimal price=new BigDecimal(100.99);
        messageVo.setPrice(price);
        messageVo.setPriceId(1212L);
        messageVo.setPromRuleId(4L);
        messageVo.setPromoteType(2);
        messageVo.setRuleType(8);
        messageVo.setPromName("测试促销");
        PriceFatMessageVo messageVo2=new PriceFatMessageVo();
        messageVo2.setPmInfoId(2234L);
        messageVo2.setChannelId(21332L);
        messageVo2.setProductId(22345L);
        messageVo2.setPrice(price);
        messageVo2.setPriceId(2212L);
        messageVo2.setPromRuleId(4L);
        messageVo2.setPromoteType(2);
        messageVo2.setRuleType(8);
        messageVo2.setPromName("测试促销");
        List<PriceFatMessageVo> list=new ArrayList<PriceFatMessageVo>();
        list.add(messageVo);
        list.add(messageVo2);
        map.put("list", list);
        vo.setMap(map);
        assertNotNull(vo.getTitle());
        assertNotNull(vo.getTemplateName());
        assertNotNull(vo.getContent());
        assertNotNull(vo.getMap().get("list"));
        BusyMailSendWithTempletUtil.sendEmailWithTemplet(vo);
    }
}
