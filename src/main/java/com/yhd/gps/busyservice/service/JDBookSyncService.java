package com.yhd.gps.busyservice.service;

import java.util.List;

import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yihaodian.architecture.jumper.consumer.BackoutMessageException;

/**
 * @author sunfei
 * @CreateTime 2016-11-29 下午03:58:59
 */
public interface JDBookSyncService {
    public List<JDBookMessageVo> getJDBookInfoByshardingIndex(Integer shardingIndex, Long offset, List<Long> merchantIds);

    /**
     * 同步京东图书价格
     * @param jdBookMessageVos
     * @param remark
     * @return
     */
    public int syncJDBookPrice(List<JDBookMessageVo> jdBookMessageVos, String remark) throws BackoutMessageException;

}
