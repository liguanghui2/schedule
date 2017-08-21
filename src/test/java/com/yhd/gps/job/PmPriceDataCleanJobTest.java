package com.yhd.gps.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.job.PmPriceDataCleanJob;

public class PmPriceDataCleanJobTest extends BaseSpringTest {

    @Autowired
    private PmPriceDataCleanJob pmPriceDataCleanJob;

    @Test
    public void test() {
        pmPriceDataCleanJob.execute();
    }
}