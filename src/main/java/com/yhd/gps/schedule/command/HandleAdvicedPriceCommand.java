package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.service.AdvicePriceService;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataPageExecCommand;
import com.yihaodian.backend.price.cityPick.vo.ConfirmedPriceVo;

/**
 * ---- 请加注释 ------
 * @Author shengxu1
 * @CreateTime 2017-4-28 下午04:45:41
 */
public class HandleAdvicedPriceCommand extends ShardingDataPageExecCommand<Integer, List<ConfirmedPriceVo>> {
    
    private AdvicePriceService advicePriceService;

    public HandleAdvicedPriceCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                        Integer pageSize, AdvicePriceService advicePriceService) {
        super(shardingIndex, finishSignal, bussinessType, pageSize);
        this.advicePriceService = advicePriceService;
    }

    @Override
    public List<ConfirmedPriceVo> fetchBussinessDataes(int shardingIndex, Integer pageSize, Integer currentPage) {
        List<ConfirmedPriceVo> confirmedPrices = advicePriceService.getScheduleConfirmedPrice(shardingIndex,pageSize);
        return confirmedPrices;
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<ConfirmedPriceVo> confirmedPrices) {
        Set<Long> dataIds = Collections.emptySet();
        List<Integer> result = new ArrayList<Integer>();;
        if(CollectionUtils.isEmpty(confirmedPrices)) {
            return new ExecResult<List<Integer>>(result, dataIds);
        }

        advicePriceService.handleConfirmedPrice(confirmedPrices);
        return new ExecResult<List<Integer>>(result, dataIds);
    }

    @Override
    public int updateData2Processed(final List<Long> list) {
        return 0;
    }

}