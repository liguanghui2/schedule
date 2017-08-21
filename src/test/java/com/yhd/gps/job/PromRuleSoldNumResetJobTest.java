package com.yhd.gps.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.spring.annotation.SpringBean;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.job.PromRuleSoldNumResetJob;

/**
 * 
 * 价格促销重置已售数量
 * @Author lipengcheng
 * @CreateTime 2016-7-21 上午09:55:15
 */
public class PromRuleSoldNumResetJobTest extends BaseSpringTest {

	@Autowired
    private PromRuleSoldNumResetJob promRuleSoldNumResetJob;

    @Test
    public void test() {
        promRuleSoldNumResetJob.execute();
    }
}