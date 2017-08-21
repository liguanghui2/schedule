package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.yhd.gps.busyservice.service.OfflineDataService;
import com.yhd.gps.schedule.common.KafkaProducerUtils;
import com.yhd.gps.schedule.vo.PmPriceVo;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.busy.common.GPSUtils;

//import com.yhd.shareservice.cache.GPSUtils;

/**
 * @author:liguanghui1
 * @date ：2017-3-15 下午06:53:52
 */
public class InitOfflineDataMsgSendCommand extends ShardingDataExecCommand<List<Integer>, List<Long>> {
    private static final Logger LOG = Logger.getLogger(InitOfflineDataMsgSendCommand.class);
    public static final int ONE = 1;

    private Integer msgSize;
    private OfflineDataService offlineDataService;

    public InitOfflineDataMsgSendCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                         Integer msgSize, OfflineDataService offlineDataService) {
        super(shardingIndex, finishSignal, bussinessType);
        this.msgSize = msgSize;
        this.offlineDataService = offlineDataService;
    }

    @Override
    public List<Long> fetchBussinessDataes(int shardingIndex) {
        Long offset = 0L;
        while(true) {
            List<PmPriceVo> pmPriceVos = offlineDataService.getPmInfoIdsFromPmPriceForOfflineData(shardingIndex,
                    offset, null);
            if(CollectionUtils.isEmpty(pmPriceVos)) {
                break;
            }
            List<List<PmPriceVo>> subpmPriceVos = GPSUtils.split(pmPriceVos, msgSize);
            for(List<PmPriceVo> list : subpmPriceVos) {
                try {
                    if(CollectionUtils.isEmpty(list)) {
                        continue;
                    }
                    List<Long> pmIds = new ArrayList<Long>();
                    for(PmPriceVo pmPriceVo : list) {
                        if(pmPriceVo != null) {
                            pmIds.add(pmPriceVo.getPmId());
                        }
                    }
                    String message = StringUtils.join(pmIds.toArray(), ":");
                    KafkaProducerUtils.sendMessage(KafkaProducerUtils.GPS_PM_PRICE_PMIDS, message);
                } catch (Exception e) {
                    LOG.info("发送kafka消息出现异常：" + e.getMessage());
                }
            }
            offset = pmPriceVos.get(pmPriceVos.size() - InitOfflineDataMsgSendCommand.ONE).getId();
        }
        return Collections.emptyList();
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<Long> bussinessDataes) {
        return null;
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        return 0;
    }

}
