package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyPriceChangeMsgDao {

    public int insertPriceChangeMsgVos(List<BusyPriceChangeMsgVo> msgs);

    public List<BusyPriceChangeMsgVo> getUnSendPriceChangeMsgList(int shardingIndex, Integer pageSize);

    public List<BusyPriceChangeMsgVo> getCompensatePriceChangeMsgList(Integer pageSize);

    public int batchUpdatePriceChangeMsgStatus2Send(List<Long> ids);

    public int clearExpiredPriceChangeTaskData(Integer delayDays);
    
    public List<BusyPriceChangeMsgVo> compensatePriceChangeMsgs(int shardingIndex, Integer pageSize, String startTime, String endTime);
    
    public int batchUpdateCompensatePriceChangeMsgStatus(List<Long> ids);
}