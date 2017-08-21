package com.yhd.gps.busyservice.dao;

import java.util.Date;
import java.util.List;

import com.yihaodian.busy.vo.HistoryPriceChangeMsgVo;
import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;

/**
 * 历史价格数据表DAO
 * @Author liuxiangrong
 * @CreateTime 2016-2-2 下午02:47:42
 */
public interface HistoryPriceChangeMsgDao {

    /**
     * 插入一条价格变化信息
     * @param priceChangeMsg
     * @return
     */
    public Long insertHistoryPriceChangeMsg(HistoryPriceChangeMsgVo priceChangeMsg);

    /**
     * 批量插入数据
     * @return
     */
    public Integer batchInsertHistoryPriceChangeMsg(List<HistoryPriceChangeMsgVo> priceChangeMsgList);

    /**
     * 使用切片号和时间范围获取pmIds
     * @param shardingIndex
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Long> queryUnDealPmIdsBySharding(int shardingIndex, Date startDate, Date endDate);

    /**
     * 查询最近几个月的历史数据
     * @param pmInfoId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<SimpleHistoryPriceChangeMsgVo> queryRecentHistoryPriceChangeMsgs(List<Long> pmInfoId, Date startDate,
                                                                           Date endDate);
    /**
     * 查询一段时间内商品最低价
     * @param pmInfoId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<SimpleHistoryPriceChangeMsgVo> queryMinHistoryPriceChangeMsgs(List<Long> pmInfoId, String startDate, String endDate);

    /**
     * 根据id更新处理过的历史价格消息
     * @param pmInfoIds
     * @param createTime
     * @return
     */
    public int batchUpdateHistoryPriceChangeMsg2Dealed(List<Long> ids);
    
    public int deleteHistoryPriceChangeMsgByPmIds(List<Long> pmIds);

}
