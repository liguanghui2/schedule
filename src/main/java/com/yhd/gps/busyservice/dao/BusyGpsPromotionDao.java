package com.yhd.gps.busyservice.dao;

import java.util.Date;
import java.util.List;

import com.yhd.gps.promotion.vo.GPSPromotionVo;

public interface BusyGpsPromotionDao {

    /**
     * 批量物理删除GpsPromotion
     * @param ids 表的主键id的List
     * @return 删除的GpsPromotion记录条数
     */
    public int batchDeleteGpsPromotionFromDbByIds(List<Long> ids);

    public List<GPSPromotionVo> batchGetGpsPromotionByIdsFromDb(List<Long> ids);

    /**
     * 查询需要清理掉的GPS_PROMOTION
     * @param outDate 结束时间在此之前的需要清理
     * @param deleteDate updateTime在此之前且rule_status=0的，需要清理
     * @param startRow
     * @param pageSize
     * @return
     */
    public List<GPSPromotionVo> queryOutDateGpsPromotionList(Date outDate, Date deleteDate, Integer startRow,
                                                             Integer pageSize);

    /**
     * 根据时间范围查gps-promotion的promotionId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Long> batchGetGpsPromotionIdListByDate(Date startDate, Date endDate);

    /**
     * 批量根据promotionIds和pmIds查gps_promotion记录
     * @param promotionIds
     * @param pmIds
     * @param startDate
     * @param endDate
     * @return
     */
    public List<GPSPromotionVo> batchGetGpsPromotionVoByPromotionIdsAndPmIds(List<Long> promotionIds, List<Long> pmIds,
                                                                             Date startDate, Date endDate);

}
