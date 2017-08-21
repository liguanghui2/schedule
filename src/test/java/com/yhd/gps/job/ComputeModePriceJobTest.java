package com.yhd.gps.job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unitils.spring.annotation.SpringBean;

import com.yhd.gps.BaseSpringTest;
import com.yhd.gps.schedule.job.ComputeModePriceJob;

/**
 * @author:liguanghui1
 * @date ：2017-4-26 上午10:52:06
 */
public class ComputeModePriceJobTest extends BaseSpringTest{
	@Autowired
    private ComputeModePriceJob computeModePriceJob;
    
    @Test
    public void test(){
        computeModePriceJob.execute();
    }
}
