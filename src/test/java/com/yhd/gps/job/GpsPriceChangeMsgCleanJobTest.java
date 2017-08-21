package com.yhd.gps.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.spring.annotation.SpringBean;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.job.PriceChangeTaskCleanJob;

/**
 * 清除价格变化消息表的过期数据定时任务
 * @Author chenmao
 * @CreateTime 2015-12-9 下午04:40:06
 */
public class GpsPriceChangeMsgCleanJobTest extends BaseSpringTest {

	@Autowired
    private PriceChangeTaskCleanJob priceChangeTaskCleanJob;

    @Test
    @TestDataSource("public")
    public void test() {
        priceChangeTaskCleanJob.execute();
    }
}