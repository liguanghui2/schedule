package com.yhd.gps.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;

import com.yhd.gps.schedule.vo.BusyPmPriceLogVo;

/*** 
 *@author liguanghui1
 *@date  2016-8-15 下午02:54:05 
 *@version 1.0 
 *@parameter  
 */

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BusyPmPriceLogVoTest extends AbstractDependencyInjectionSpringContextTests{
    
    @Test
    public void VoTest(){
        BusyPmPriceLogVo busyPmPriceLogVo=new BusyPmPriceLogVo();
        busyPmPriceLogVo.setId(1L);
        busyPmPriceLogVo.setPmId(1L);
        busyPmPriceLogVo.setOpType("Op Type");
        busyPmPriceLogVo.setPriceId(12L);
        busyPmPriceLogVo.setProductId(1L);
        busyPmPriceLogVo.setPerPackNum(12);
        BigDecimal inPrice=new BigDecimal(100);
        busyPmPriceLogVo.setInPrice(inPrice);
        BigDecimal avgPrice=new BigDecimal(105);
        busyPmPriceLogVo.setAvgPrice(avgPrice);
        busyPmPriceLogVo.setCanVipDiscount(true);
        busyPmPriceLogVo.setPostTaxRate(avgPrice);
        busyPmPriceLogVo.setClientIP("10.10.10.10");
        busyPmPriceLogVo.setClientPoolName("GPS POOL");
        busyPmPriceLogVo.setClientVersion("1.1.1");
        busyPmPriceLogVo.setCostPrice(80.00);
        Date createTime=new Date();
        busyPmPriceLogVo.setPriceUpdateTime(createTime);
        busyPmPriceLogVo.setCreateTime(createTime);
        BigDecimal earnest=new BigDecimal(10);
        busyPmPriceLogVo.setEarnest(earnest);
        BigDecimal exciseTax=new BigDecimal(0.22);
        busyPmPriceLogVo.setExciseTax(exciseTax);
        busyPmPriceLogVo.setHasExtPriceType(2);
        busyPmPriceLogVo.setIsBindPackage(5);
        busyPmPriceLogVo.setIsLockPrice(1);
        BigDecimal marketPrice=new BigDecimal(100);
        busyPmPriceLogVo.setMarketPrice(marketPrice); 
        busyPmPriceLogVo.setMerchantId(2L);
        BigDecimal minimumSellingPrice=new BigDecimal(100);
        busyPmPriceLogVo.setMinimumSellingPrice(minimumSellingPrice);
        busyPmPriceLogVo.setNonMemberPrice(minimumSellingPrice);
        busyPmPriceLogVo.setPromoteType(3);
        busyPmPriceLogVo.setPromNonMemberPrice(marketPrice);
        busyPmPriceLogVo.setPromEndTime(createTime);
        busyPmPriceLogVo.setPromStartTime(createTime);
        busyPmPriceLogVo.setServerIP("10.10.10.10");
        busyPmPriceLogVo.setShareCost(exciseTax);
        busyPmPriceLogVo.setSpecialPriceLimitNumber(100);
        busyPmPriceLogVo.setStatus(1);
        busyPmPriceLogVo.setSubPmInfoUnit("subpminfoUnit");
        busyPmPriceLogVo.setUserPriceLimitNumber(100);
        busyPmPriceLogVo.setVaTax(exciseTax);
        busyPmPriceLogVo.getAvgPrice();
        busyPmPriceLogVo.getClientIP();
        busyPmPriceLogVo.getClientPoolName();
        busyPmPriceLogVo.getClientVersion();
        busyPmPriceLogVo.getCostPrice();
        busyPmPriceLogVo.getCostPrice();
        busyPmPriceLogVo.getCreateTime();
        busyPmPriceLogVo.getEarnest();
        busyPmPriceLogVo.getExciseTax();
        busyPmPriceLogVo.getHasExtPriceType();
        busyPmPriceLogVo.getId();
        busyPmPriceLogVo.getInPrice();
        busyPmPriceLogVo.getIsBindPackage();
        busyPmPriceLogVo.getIsLockPrice();
        busyPmPriceLogVo.getMarketPrice();
        busyPmPriceLogVo.getMerchantId();
        busyPmPriceLogVo.getMinimumSellingPrice();
        busyPmPriceLogVo.isCanVipDiscount();
        busyPmPriceLogVo.getNonMemberPrice();
        busyPmPriceLogVo.getOpType();
        busyPmPriceLogVo.getPerPackNum();
        busyPmPriceLogVo.getPmId();
        busyPmPriceLogVo.getPostTaxRate();
        busyPmPriceLogVo.getPriceId();
        busyPmPriceLogVo.getPriceUpdateTime();
        busyPmPriceLogVo.getProductId();
        busyPmPriceLogVo.getServerIP();
        busyPmPriceLogVo.getPromStartTime();
        busyPmPriceLogVo.getShareCost();
        busyPmPriceLogVo.getSpecialPriceLimitNumber();
        busyPmPriceLogVo.getStatus();
        busyPmPriceLogVo.getSubPmInfoUnit();
        busyPmPriceLogVo.getUserPriceLimitNumber();
        busyPmPriceLogVo.getVaTax();
        assertNotNull(busyPmPriceLogVo.getPromoteType());
        assertNotNull(busyPmPriceLogVo.getPromNonMemberPrice());
        assertNotNull(busyPmPriceLogVo.getPromEndTime());
        assertNotNull( busyPmPriceLogVo.toString());
        
        
    }
    
}
