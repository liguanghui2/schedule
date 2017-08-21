package com.yhd.gps.busyservice.dao;

import java.util.Date;
import java.util.List;

import com.yhd.gps.schedule.vo.ProductPromRule4ResetSoldNumVo;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public interface BusyProductPromRuleDao {

    /**
     * 批量物理删除价格促销规则
     * @param ruleIds
     * @return
     */
    public int batchDeleteProductPromRuleFromDbByIds(final List<Long> ruleIds);

    /**
     * 查询需要清理掉的促销规则
     * @param outDate 结束时间在此之前的需要清理
     * @param deleteDate updateTime在此之前且rule_status=0的，需要清理
     * @param startRow
     * @param pageSize
     * @return
     */
    public List<BSProductPromRuleVo> queryOutDatePromRuleList(Date outDate, Date deleteDate, Integer startRow,
                                                              Integer pageSize);

    /**
     * 批量根据促销规则id查价格促销
     * @param ruleIds
     * @return
     */
    public List<ProductPromRule4ResetSoldNumVo> batchGetProductPromRuleListByIds(List<Long> ruleIds);

    /**
     * 批量根据促销规则id更新soldNum
     * @param ruleIds
     * @return
     */
    public int batchUpdateProductPromRuleSoldNumByRuleIds(List<Long> ruleIds);

    /**
     * 批量根据商家id查价格促销
     * @param ruleIds
     * @param offset
     * @param pageSize
     * @return
     */
    public List<ProductPromRule4ResetSoldNumVo> batchGetProductPromRuleListByMerchantIds(List<Long> merchantIds,
                                                                                         int offset, int pageSize);

    /**
     * 批量根据pmId查价格促销
     * @param pmIds
     * @return
     */
    public List<BSProductPromRuleVo> batchGetProductPromRuleListByPmIds(List<Long> pmIds);
}
