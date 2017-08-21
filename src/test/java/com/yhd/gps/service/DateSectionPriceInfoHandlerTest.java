package com.yhd.gps.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.historyprice.DateSectionPriceInfoHandler;

public class DateSectionPriceInfoHandlerTest extends BaseSpringTest {

    @Autowired
    private DateSectionPriceInfoHandler DateSectionPriceInfoHandler;

    @Test
    public void computeDateSectionPriceInfo(){
        List<Long> pmIds = new ArrayList<Long>();
        pmIds.add(PM_INFO_ID_0);
        pmIds.add(PM_INFO_ID_9);
        
        int shardingIndex = 1;
        Set<Long> dealedMsgIds = new HashSet<Long>();
        
        DateSectionPriceInfoHandler.computeDateSectionPriceInfo(pmIds, shardingIndex, dealedMsgIds);
    }
}
