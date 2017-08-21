package com.yhd.gps.busyservice.dao;

import java.util.Date;
import java.util.List;

import com.yhd.gps.backendprice.vo.GpsProductVo;

/**
 * 获取product表基本信息
 * 
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyProductDao {

    /**
     * 根据productId批量从product和category表查gps_product
     * @param productIds
     * @return
     */
    List<GpsProductVo> batchGetGpsProduct(List<Long> productIds);

    /**
     * <pre>
     * 根据同步开始时间和结束时间从product和category中分页查出未同步过的gps_product查product(包含类目searchName)
     * 查询product表记录中修改的数据：
     *      1. modi_date在[startTime, endTime]区间内
     *      2. create_time在[startTime, endTime]区间内
     *      3. UPDATE_TIME在[startTime, endTime]区间内
     * </pre>
     * @param startTime
     * @param endTime
     * @param lastId
     * @param startRow
     * @param endRow
     * @return
     */
    List<GpsProductVo> getPageGpsProductForSync(Date startTime, Date endTime, Long lastId, Long startRow, Long endRow);

    /**
     * <pre>
     *  根据开始时间和结束时间查product(包含类目searchName)总数
     *  查询product表记录中修改的数据：
     *      1. modi_date在[startTime, endTime]区间内
     *      2. create_time在[startTime, endTime]区间内
     *      3. UPDATE_TIME在[startTime, endTime]区间内
     * </pre>
     * @param startTime
     * @param endTime
     * @return
     */
    Long getCountGpsProductForSync(Date startTime, Date endTime);

    /**
     * 查询product表永久下架的数据
     * @param endTime
     * @param offset
     * @param pageSize
     * @param shardingIndex
     * @return
     */
    public List<GpsProductVo> getPageOffSaleProduct(Date startTime,Date endTime, Long offset, Integer pageSize, int shardingIndex);
}