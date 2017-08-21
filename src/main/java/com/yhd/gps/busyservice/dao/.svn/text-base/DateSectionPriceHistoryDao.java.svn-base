package com.yhd.gps.busyservice.dao;

import java.util.List;

import com.yhd.gps.pricechange.vo.DateSectionPriceInfoVo;
import com.yhd.gps.pricechange.vo.input.QueryDateSectionPriceInfoByPmIdAndDateSectionRequest;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2015-6-11 下午01:55:06
 */
public interface DateSectionPriceHistoryDao {

    public Integer insertDateSectionPriceInfo(final List<DateSectionPriceInfoVo> dateSectionPriceInfos);

    public Integer updateDateSectionPriceInfo(List<DateSectionPriceInfoVo> dateSectionPriceInfos);

    public List<DateSectionPriceInfoVo> queryDateSectionPriceInfoByPmIdAndDateSection(QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request);
    
    public Integer deleteDateSectionPriceInfo(QueryDateSectionPriceInfoByPmIdAndDateSectionRequest request);
    
    // 以下方法为临时添加方法
    public List<Long> queryDateSectionPriceInfoByChannelId();
    
    public void deleteDateSectionPriceHistoryByIds(List<Long> ids);
}
