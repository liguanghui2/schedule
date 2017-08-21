package com.yhd.gps.busyservice.service;

import java.util.Date;
import java.util.List;

public interface BusyGpsProductSyncService {

    /**
     * gps_product数据补偿
     */
    public int compensateGpsProduct();

    /**
     * 补偿gps_product数据
     * @param startTime 补偿数据的开始时间
     * @param endTime 补偿数据的结束时间
     * @param pageSize 每页的数量
     * @return
     */
    public Long syncProductToGps(Date startTime, Date endTime, Long pageSize);

    /**
     * 重试出错的同步数据
     */
    public void handleSyncDataLog();

    /**
     * 通过productId的List查询对应的数据，同步到GPS_PRODUCT表中
     * @param productIdList
     * @return
     */
    public int compensateGpsProductByProductIds(List<Long> productIdList);

}
