package com.yhd.gps.busyservice.dao;

import java.util.List;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-2 下午01:20:05
 */
public interface BusyMerchantDao {

    /**
     * 批量获取sam商家id列表
     * @param subPmIds
     * @return
     */
    List<Long> batchGetMerchantIds4Sam();

}
