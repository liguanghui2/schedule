package com.yhd.gps.schedule.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.PromotionPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.PromotionPriceChangeMsgSender;
import com.yhd.gps.pricechange.vo.PromotionPriceChangeMsg;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataPageExecCommand;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-12 下午04:30:08
 */
public class PromotionPriceChangeMsgSendCommand extends
                                               ShardingDataPageExecCommand<Integer, List<PromotionPriceChangeMsg>> {

    private PromotionPriceChangeMsgDao promotionPriceChangeMsgDao;
    private PromotionPriceChangeMsgSender sender;

    public PromotionPriceChangeMsgSendCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                              Integer pageSize, PromotionPriceChangeMsgDao promotionPriceChangeMsgDao,
                                              PromotionPriceChangeMsgSender sender) {
        super(shardingIndex, finishSignal, bussinessType, pageSize);
        this.promotionPriceChangeMsgDao = promotionPriceChangeMsgDao;
        this.sender = sender;
    }

    @Override
    public List<PromotionPriceChangeMsg> fetchBussinessDataes(int shardingIndex, Integer pageSize, Integer currentPage) {
        return promotionPriceChangeMsgDao.getUnSendPromotionPriceChangeMsgs(shardingIndex, pageSize);
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<PromotionPriceChangeMsg> promotionPriceChangeMsgs) {
        if(CollectionUtils.isEmpty(promotionPriceChangeMsgs)) {
            Set<Long> dataIds = Collections.emptySet();
            List<Integer> result = Collections.emptyList();
            return new ExecResult<List<Integer>>(result, dataIds);
        }

        Set<Long> sendedMsgIds = new HashSet<Long>(promotionPriceChangeMsgs.size(), 1F);

        Integer result = sender.send(promotionPriceChangeMsgs, sendedMsgIds);
        return new ExecResult<List<Integer>>(Arrays.asList(result), sendedMsgIds);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        return promotionPriceChangeMsgDao.batchUpdatePromotionPriceChangeMsgStatus2Send(ids);
    }

}