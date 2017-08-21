package com.yhd.gps.vo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.yhd.gps.schedule.vo.MailTempletVo;

/**
 * @author:liguanghui1
 * @date ：2016-9-6 下午01:29:22
 */
public class MailTempletVoTest {

    @Test
    public void VoTest(){
        MailTempletVo vo=new MailTempletVo();
        vo.setContent("this is content");
        vo.setTitle("this is title");
        vo.setTemplateName("this is templateName");
        Map<String, Object> map=new HashMap<String,Object>();
        vo.setMap(map);
        vo.getMap();
        assertNotNull(vo.getTitle());
        assertNotNull(vo.getContent());
        assertNotNull(vo.getTemplateName());
        vo.toString();
    }
}
