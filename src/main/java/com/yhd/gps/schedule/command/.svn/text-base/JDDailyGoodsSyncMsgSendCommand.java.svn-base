package com.yhd.gps.schedule.command;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.service.JDBookSyncService;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.front.databus.client.FrontDatabusClient;

/**
 * 
 * 京东日百价格同步消息发送
 * @Author lipengcheng
 * @CreateTime 2017-2-27 上午11:20:49
 */
public class JDDailyGoodsSyncMsgSendCommand extends ShardingDataExecCommand<List<Integer>, List<JDBookMessageVo>> {
    private Integer msgSize;
    private JDBookSyncService jdBookSyncService;

    public JDDailyGoodsSyncMsgSendCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                          Integer msgSize, JDBookSyncService jdBookSyncService) {
        super(shardingIndex, finishSignal, bussinessType);
        this.msgSize = msgSize;
        this.jdBookSyncService = jdBookSyncService;
    }

    @Override
    public List<JDBookMessageVo> fetchBussinessDataes(int shardingIndex) {
        Long offset = 0L;
        // 查询商家id
        List<Long> merchantIds = MiscConfigUtils
                .getConfigListByItemKey(ScheduleConstants.JD_DAILY_GOODS_SYNC_MERCHANTID_LIST);
        if(CollectionUtils.isEmpty(merchantIds)) {
            return Collections.emptyList();
        }
        while(true) {
            List<JDBookMessageVo> jdBookMessageVos = jdBookSyncService.getJDBookInfoByshardingIndex(shardingIndex,
                    offset, merchantIds);
            if(CollectionUtils.isEmpty(jdBookMessageVos)) {
                break;
            }
            List<List<JDBookMessageVo>> subJDBookMessageVos = GPSUtils.split(jdBookMessageVos, msgSize);
            for(List<JDBookMessageVo> list : subJDBookMessageVos) {
                // 发送pmId消息
                FrontDatabusClient.publishJDBookSyncMsg(list);
            }
            // 取List中最后一个pmId值作为新的offset
            offset = jdBookMessageVos.get(jdBookMessageVos.size() - 1).getPmId();
        }
        return Collections.emptyList();
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<JDBookMessageVo> jdBookMessageVo) {
        return new ExecResult<List<Integer>>(null, null);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        return 0;
    }
}