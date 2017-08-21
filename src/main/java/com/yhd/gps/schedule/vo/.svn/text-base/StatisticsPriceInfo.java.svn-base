package com.yhd.gps.schedule.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;


/**
 * 计算最高最低平均价格
 * @Author liuxiangrong
 * @CreateTime 2016-2-2 下午02:55:07
 */
public class StatisticsPriceInfo {
    /**
     * 最高价格
     */
    public BigDecimal maxPrice;

    /**
     * 最低价格
     */
    public BigDecimal minPrice;

    /**
     * 平均价格
     */
    public BigDecimal avgPrice;

    /**
     * 计算价格对象中的最高最低和平均价格
     * @param priceChangeVos
     */
    public final void statistics(final List<SimpleHistoryPriceChangeMsgVo> priceChangeVos) {
        BigDecimal priceCounts = BigDecimal.ZERO;
        for(SimpleHistoryPriceChangeMsgVo priceChangeVo : priceChangeVos) {
            BigDecimal price = priceChangeVo.getPrice();
            priceCounts = priceCounts.add(price);
            if(null == maxPrice || price.compareTo(maxPrice) > 0) {
                maxPrice = price;
            }
            if(null == minPrice || price.compareTo(minPrice) < 0) {
                minPrice = price;
            }
        }
        avgPrice = priceCounts.divide(new BigDecimal(priceChangeVos.size()), 2, BigDecimal.ROUND_HALF_DOWN);
        avgPrice = new BigDecimal(new DecimalFormat("#.00").format(avgPrice));
    }
}
