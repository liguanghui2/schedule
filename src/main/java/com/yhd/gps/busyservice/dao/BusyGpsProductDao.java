package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.backendprice.vo.GpsProductVo;

public interface BusyGpsProductDao {

    /**
     * 通过id查询GpsProduct对象
     * 
     * @param id
     * @return GpsProduct对象
     */
    GpsProductVo queryGpsProductById(Long id);

    /**
     * 通过id更新GpsProduct
     * 
     * @param id
     * @return 更新的记录条数
     */
    int updateGpsProductById(GpsProductVo vo);

    /**
     * 通过id批量删除GpsProduct
     * 
     * @param ids
     * @return 删除的记录条数
     */
    int deleteGpsProductByIds(List<Long> ids);

    /**
     * 保存GpsProduct
     * 
     * @param vo
     * @return
     */
    Long insertGpsProduct(GpsProductVo vo);

    /**
     * 保存GpsProduct,若主键重复则更新
     * @param vo
     * @return
     */
    Long insertOrUpdateGpsProduct(GpsProductVo vo);

    /**
     * 批量插入gps_product
     * @param gpsProductList
     * @return
     */
    int batchInsertGpsProduct(List<GpsProductVo> gpsProductList);

    /**
     * 批量修改gps_product表的记录
     * @param gpsProductList
     * @return
     */
    int batchUpdateGpsProduct(List<GpsProductVo> gpsProductList);

    /**
     * 根据productId查gps_product中的ID，主要是为了验证记录是否已经存在
     * @param productIds
     * @return
     */
    List<GpsProductVo> batchGetGpsProductByIds(List<Long> productIds);

    /**
     * 求出在pm_price中有，但在gps_product中没有的product_id
     * @return
     */
    List<Long> getProductIdNotInGpsPmPrice();

    /**
     * 求出在product_prom_rule中有，但在gps_product中没有的product_id
     * @return
     */
    List<Long> getProductIdNotInGpsPromRule();

}
