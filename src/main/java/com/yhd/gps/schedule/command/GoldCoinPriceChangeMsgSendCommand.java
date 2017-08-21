package com.yhd.gps.schedule.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.GoldCoinPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.GoldCoinPriceChangeMsgSender;
import com.yhd.gps.schedule.vo.GoldCoinPriceChangeMsg;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataPageExecCommand;

public class GoldCoinPriceChangeMsgSendCommand extends
                                              ShardingDataPageExecCommand<Integer, List<GoldCoinPriceChangeMsg>> {

    private static final Log logger = LogFactory.getLog(GoldCoinPriceChangeMsgSendCommand.class);

    private GoldCoinPriceChangeMsgDao goldCoinPriceChangeMsgDao;
    private GoldCoinPriceChangeMsgSender sender;

    public GoldCoinPriceChangeMsgSendCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,

    Integer pageSize, GoldCoinPriceChangeMsgDao goldCoinPriceChangeMsgDao, GoldCoinPriceChangeMsgSender sender) {
        super(shardingIndex, finishSignal, bussinessType, pageSize);
        this.goldCoinPriceChangeMsgDao = goldCoinPriceChangeMsgDao;
        this.sender = sender;
    }

    @Override
    public List<GoldCoinPriceChangeMsg> fetchBussinessDataes(int shardingIndex, Integer pageSize, Integer currentPage) {
        List<GoldCoinPriceChangeMsg> result = goldCoinPriceChangeMsgDao.getUnSendGoldCoinPriceChangeMsg(shardingIndex,
                pageSize);
        return result;
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<GoldCoinPriceChangeMsg> goldCoinPriceChangeMsgs) {
        if(CollectionUtils.isEmpty(goldCoinPriceChangeMsgs)) {
            Set<Long> dataIds = Collections.emptySet();
            List<Integer> result = Collections.emptyList();
            return new ExecResult<List<Integer>>(result, dataIds);
        }
        Set<Long> dataIds = new HashSet<Long>(goldCoinPriceChangeMsgs.size(), 1F);
        try {
            sender.saveToGoldCoinPriceChangeHistoryAndSendMsg(goldCoinPriceChangeMsgs, dataIds);
        } catch (Exception e) {
            logger.error("####发送金币促销历史价格变化消息异常。####" + e.getMessage());
        }
        List<Integer> result = Collections.emptyList();
        return new ExecResult<List<Integer>>(result, dataIds);
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        Integer result = 0;
        try {
            result = goldCoinPriceChangeMsgDao.batchUpdateGoldCoinPriceChangeMsgStatus2Send(dataIds);
        } catch (Exception e) {
            logger.error("####更新金币消息状态异常!####"+ e.getMessage());
        }
        return result;
    }

}
