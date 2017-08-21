package com.yhd.gps.busyservice.service;

import java.util.List;

import com.yihaodian.backend.price.cityPick.vo.EmailTypeVo;
import com.yihaodian.backend.price.cityPick.vo.SchedulePriceInfo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;

/**
 * Created by zhangshuqiang on 2017/5/15.
 */
public interface CityPickPriceService {

    /**
     * 查询档期定价
     * @param scheduleId
     * @param priceStatus
     * @return
     */
    public List<SchedulePriceInfo> querySchedulePriceList(Long scheduleId, Long merchantId, List<Integer> priceStatus);

    /**
     * 发送定价邮件
     * @param scheduleVo
     * @param priceStatus
     */
    public void sendCityPickPriceEmail(ScheduleVo scheduleVo, Integer priceStatus);
    
    /**
     * 根据类型获取邮件配置
     * @param emailType
     */
    public EmailTypeVo findAllEmailByType(Integer emailType);
}
