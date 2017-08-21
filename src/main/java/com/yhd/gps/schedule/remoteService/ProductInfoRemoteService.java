package com.yhd.gps.schedule.remoteService;

import java.util.List;

import com.yhd.pss.spi.pminfo.vo.FullFieldsPmInfoWithProductVo;
import com.yhd.pss.spi.pminfo.vo.PmInfo;

/**
 * @author sunfei
 * @CreateTime 2017-6-29 下午01:59:07
 */
public interface ProductInfoRemoteService {
    /**
     * 查询所有城市精选商家列表
     * @return
     */
    List<Long> queryAllCityMerchantIds();

    /**
     * 查询商家下的所有商品信息，带分页
     * @param merchantId
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<FullFieldsPmInfoWithProductVo> queryPmInfosByMerchantId(Long merchantId, Long currentPage, Long pageSize);

    /**
     * 根据merchant和产品ids查询商品信息
     * @param merchantId
     * @param productIds
     * @return
     */
    List<PmInfo> getPmInfoByMerchantIdAndProductIds(Long merchantId, List<Long> productIds);

}
