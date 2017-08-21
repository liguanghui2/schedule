package com.yhd.gps.schedule.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.dao.BusyPriceChangeMsgDao;
import com.yhd.gps.busyservice.msg.GPSPriceChangeMsgSender;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataPageExecCommand;
import com.yihaodian.busy.vo.BusyPriceChangeMsgVo;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-11 下午04:22:41
 */
public class GPSPriceChangeMsgSendCommand extends ShardingDataPageExecCommand<Integer, List<BusyPriceChangeMsgVo>> {
    private BusyPriceChangeMsgDao busyPriceChangeMsgDao;
    private GPSPriceChangeMsgSender sender;

    public GPSPriceChangeMsgSendCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                        Integer pageSize, BusyPriceChangeMsgDao busyPriceChangeMsgDao,
                                        GPSPriceChangeMsgSender sender) {
        super(shardingIndex, finishSignal, bussinessType, pageSize);
        this.busyPriceChangeMsgDao = busyPriceChangeMsgDao;
        this.sender = sender;
    }

    @Override
    public List<BusyPriceChangeMsgVo> fetchBussinessDataes(int shardingIndex, Integer pageSize, Integer currentPage) {
        return busyPriceChangeMsgDao.getUnSendPriceChangeMsgList(shardingIndex, pageSize);
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<BusyPriceChangeMsgVo> priceChangeMsgVos) {
        if(CollectionUtils.isEmpty(priceChangeMsgVos)) {
            Set<Long> dataIds = Collections.emptySet();
            List<Integer> result = Collections.emptyList();
            return new ExecResult<List<Integer>>(result, dataIds);
        }

        Set<Long> sendedMsgIds = new HashSet<Long>(priceChangeMsgVos.size(), 1F);

        Integer result = sender.send(priceChangeMsgVos, sendedMsgIds);
        return new ExecResult<List<Integer>>(Arrays.asList(result), sendedMsgIds);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        return busyPriceChangeMsgDao.batchUpdatePriceChangeMsgStatus2Send(ids);
    }

}