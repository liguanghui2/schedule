package com.yhd.gps.schedule.sharding.processor;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.service.JDBookSyncService;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yhd.schedule.sharding.core.ShardingDataProcessorForCrash;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.front.databus.client.FrontDatabusClient;

/**
 * @author sunfei
 * @CreateTime 2016-12-8 下午05:09:42
 */
public class JDBookSyncShardingDataProcessorForCrash extends ShardingDataProcessorForCrash {
    private JDBookSyncService jdBookSyncService;
    private String bussinessType;
    private Integer msgSize;

    public void setJdBookSyncService(JDBookSyncService jdBookSyncService) {
        this.jdBookSyncService = jdBookSyncService;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public void setMsgSize(Integer msgSize) {
        this.msgSize = msgSize;
    }

    @Override
    protected String getBussinessType() {
        return bussinessType;
    }

    @Override
    protected void processCore(int shardingIndex, CountDownLatch finishSignal) {
        Long offset = 0L;
        try {
            // 取商家信息
            List<Long> merchantIds = MiscConfigUtils
                    .getConfigListByItemKey(ScheduleConstants.JD_BOOK_SYNC_MERCHANTID_LIST);
            if(CollectionUtils.isEmpty(merchantIds)) {
                return;
            }
            while(true) {
                List<JDBookMessageVo> jdBookMessageVos = fetchJDBookSyncData(shardingIndex, offset, merchantIds);
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
        } catch (Exception e) {
        } finally {
            finishSignal.countDown();
        }
    }

    private List<JDBookMessageVo> fetchJDBookSyncData(int shardingIndex, Long offset, List<Long> merchantIds) {
        return jdBookSyncService.getJDBookInfoByshardingIndex(shardingIndex, offset, merchantIds);
    }
}
