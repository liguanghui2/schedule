package com.yhd.gps.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.spring.annotation.SpringBean;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.job.GPSPromotionDataesCleanJob;

/**
 * 清除product_prom_rule、gps_promotion表的过期数据的定时任务
 * @Author  liuxiangrong
 * @CreateTime  2016-4-13 下午07:10:12
 */
public class GPSPromotionDataesCleanJobTest  extends BaseSpringTest {
    
	@Autowired
    private GPSPromotionDataesCleanJob gpsDataCleanJob;
    
    @Test
    @TestDataSource("public")
    public void test() {
        gpsDataCleanJob.execute();
    }
}