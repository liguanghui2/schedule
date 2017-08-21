package com.yhd.gps.busyservice.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busy.mail.BusyMailUtil;
import com.yhd.gps.busyservice.dao.BusyGpsPromotionDao;
import com.yhd.gps.busyservice.dao.BusyProductPromotionDao;
import com.yhd.gps.busyservice.handler.LPCompareHandler;
import com.yhd.gps.busyservice.service.BusyProductPromotionService;
import com.yhd.gps.freemaker.TemplateFactory;
import com.yhd.gps.promotion.vo.GPSPromotionVo;
import com.yhd.gps.schedule.common.Judger;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.vo.BusyLandingProductVo;
import com.yhd.gps.schedule.vo.LPCompareResultVo;
import com.yhd.gps.schedule.vo.PromotionPeriodTimeVo;
import com.yhd.gps.utils.DateUtil;
import com.yihaodian.busy.common.GPSUtils;

/**
 * 
 * landingPage促销信息service， 该service仅查询promotion_v2表
 * @Author lipengcheng
 * @CreateTime 2016-7-5 下午03:23:24
 */
public class BusyProductPromotionServiceImpl implements BusyProductPromotionService {
    private static final Log log = LogFactory.getLog(BusyProductPromotionServiceImpl.class);

    private BusyProductPromotionDao busyProductPromotionDao;

    private BusyGpsPromotionDao busyGpsPromotionDao;

    public void setBusyProductPromotionDao(BusyProductPromotionDao busyProductPromotionDao) {
        this.busyProductPromotionDao = busyProductPromotionDao;
    }

    public void setBusyGpsPromotionDao(BusyGpsPromotionDao busyGpsPromotionDao) {
        this.busyGpsPromotionDao = busyGpsPromotionDao;
    }

    @Override
    public List<BusyLandingProductVo> getLandingProductVoListByDate(Date startDate, Date endDate,
                                                                    List<Long> promotionIds, Integer startRow,
                                                                    Integer endRow) {
        if(startDate == null || endDate == null || CollectionUtils.isEmpty(promotionIds) || startRow == null
           || endRow == null) {
            return Collections.emptyList();
        }
        List<BusyLandingProductVo> result = new ArrayList<BusyLandingProductVo>();
        // 查prod库促销
        List<BusyLandingProductVo> landingProductVos = busyProductPromotionDao.batchGetLandingProductVoListByDate(
                startDate, endDate, promotionIds, startRow, endRow);
        if(CollectionUtils.isEmpty(landingProductVos)) {
            return Collections.emptyList();
        }
        // 判断pmId是否为空
        Set<Long> promotionIdsWithoutPmId = new HashSet<Long>(landingProductVos.size());
        Set<Long> productIdsWithoutPmId = new HashSet<Long>(landingProductVos.size());
        for(BusyLandingProductVo busyLandingProductVo : landingProductVos) {
            if(busyLandingProductVo.getPmId() == null || busyLandingProductVo.getPmId() <= 0) {
                promotionIdsWithoutPmId.add(busyLandingProductVo.getPromotionId());
                productIdsWithoutPmId.add(busyLandingProductVo.getProductId());
            } else {
                result.add(busyLandingProductVo);
            }
        }

        // pmId为空的则是商城，关联productv2_merchant表查
        if(!promotionIdsWithoutPmId.isEmpty() && !productIdsWithoutPmId.isEmpty()) {
            List<BusyLandingProductVo> lpVos = busyProductPromotionDao.batchGetLandingProductVoListByDate41Mall(
                    startDate, endDate, new ArrayList<Long>(promotionIdsWithoutPmId), new ArrayList<Long>(
                        productIdsWithoutPmId));
            if(CollectionUtils.isNotEmpty(lpVos)) {
                result.addAll(lpVos);
            }
        }
        return result;
    }

    public void compareLPPromotion(Date startDate, Date endDate, Integer pageSize) {
        if(startDate == null || endDate == null || startDate.after(endDate) || pageSize == null || pageSize <= 0) {
            log.error("lp比对参数异常：startDate=" + startDate + ", endDate=" + endDate + ", pageSize=" + pageSize);
            return;
        }
        // 先查询出所有的促销id，比对促销id是否有差异
        // prod库lp促销
        List<Long> prodPromotionIds = busyProductPromotionDao.batchGetLandingProductPromotionIdListByDate(startDate,
                endDate);
        // gps库lp促销
        List<Long> gpsPromotionIds = busyGpsPromotionDao.batchGetGpsPromotionIdListByDate(startDate, endDate);

        // 求出不相交的promotionId
        List<Long> notIntersectionPromotionIds = GPSUtils.notIntersectionList(prodPromotionIds, gpsPromotionIds);
        notIntersectionPromotionIds.addAll(GPSUtils.notIntersectionList(gpsPromotionIds, prodPromotionIds));
        // 处理不相交的promotionId
        processAbnormalPromotion(notIntersectionPromotionIds);

        // 求出相交的promotionId
        Set<Long> intersectionPromotionIds = GPSUtils.intersectionSet(new HashSet<Long>(prodPromotionIds),
                new HashSet<Long>(gpsPromotionIds));
        // 没有相交的数据，则退出
        if(CollectionUtils.isEmpty(intersectionPromotionIds)) {
            return;
        }
        // 分批处理
        List<List<Long>> lists = GPSUtils.split(new ArrayList<Long>(intersectionPromotionIds), pageSize);
        for(List<Long> list : lists) {
            processNormalPromotion(list, startDate, endDate, pageSize);
        }

    }

    private static final void processAbnormalPromotion(List<Long> promotionIds) {
        if(CollectionUtils.isEmpty(promotionIds)) {
            return;
        }
        String content = "promotionId在表gps_promotion或promotionv2找不到,请检查！";
        sendEmail(promotionIds, content);
    }

    private void processNormalPromotion(List<Long> normalPromotionIds, Date startDate, Date endDate, Integer pageSize) {
        if(CollectionUtils.isEmpty(normalPromotionIds)) {
            return;
        }
        List<LPCompareResultVo> result = new ArrayList<LPCompareResultVo>();
        // 分页查询
        Integer startRow = 1;
        Integer endRow = startRow * pageSize;
        try {
            int loopTimes = 1;
            // 1.每次查询pageSize条记录进行比对
            List<BusyLandingProductVo> landingProductVos = getLandingProductVoListByDate(startDate, endDate,
                    normalPromotionIds, startRow, endRow);
            while(CollectionUtils.isNotEmpty(landingProductVos)) {
                Long start = System.currentTimeMillis();
                // 每次处理数据大小
                final int sizeOfLandingProductVos = landingProductVos.size();
                // 找出促销lp的promotionIds和pmIds
                Set<Long> promotionIds = new HashSet<Long>(sizeOfLandingProductVos);
                Set<Long> pmIds = new HashSet<Long>(sizeOfLandingProductVos);
                for(BusyLandingProductVo landingProductVo : landingProductVos) {
                    promotionIds.add(landingProductVo.getPromotionId());
                    pmIds.add(landingProductVo.getPmId());
                }
                // 查lp分时段表,并设置到上下文
                List<PromotionPeriodTimeVo> promotionPeriodTimeVos = busyProductPromotionDao
                        .batchGetPromotionPeriodTimeVoList(new ArrayList<Long>(promotionIds));
                if(CollectionUtils.isNotEmpty(promotionPeriodTimeVos)) {
                    for(PromotionPeriodTimeVo promotionPeriodTimeVo : promotionPeriodTimeVos) {
                        ScheduleContext.setValue(ScheduleConstants.LP_PROMOTION_PERIOD_TIME_PREFIX
                                                 + promotionPeriodTimeVo.getPromotionId(), promotionPeriodTimeVo);
                    }
                }

                // 2.根据promotionIds和pmId查gps lp
                List<GPSPromotionVo> gpsPromotionVos = null;
                if(CollectionUtils.isNotEmpty(promotionIds) && CollectionUtils.isNotEmpty(pmIds)) {
                    gpsPromotionVos = busyGpsPromotionDao.batchGetGpsPromotionVoByPromotionIdsAndPmIds(
                            new ArrayList<Long>(promotionIds), new ArrayList<Long>(pmIds), startDate, endDate);
                }

                // 3.开始比对
                if(CollectionUtils.isNotEmpty(gpsPromotionVos)) {
                    List<LPCompareResultVo> resultVos = compareLandingPagePromotionInner(landingProductVos,
                            gpsPromotionVos);
                    for(LPCompareResultVo resultVo : resultVos) {
                        if(Judger.isLPCompareHasError(resultVo)) {
                            result.add(resultVo);
                        }
                    }
                } else {
                    // 查不到GPSPromotion数据，则返回数据异常
                    result.addAll(buildBusyLPCompareResultVos(landingProductVos,
                            ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA));
                }

                Long end = System.currentTimeMillis();
                log.debug("---###---[GPS]compareLandingPageBetweenGpsAndPromotion: loop=[" + loopTimes++
                          + "], compareData=[" + sizeOfLandingProductVos + "], useTime=[" + (end - start) + "]ms");

                // 下一页
                startRow = endRow + 1;
                endRow = endRow + pageSize;
                // 继续查询下一页需要比对的数据
                landingProductVos = getLandingProductVoListByDate(startDate, endDate, normalPromotionIds, startRow,
                        endRow);
            }
        } catch (Exception e) {
            log.error(
                    "BusyLPCompareBetweenGpsAndPormotionService.compareLandingPageBetweenGpsAndPromotion error: "
                            + e.getMessage(), e);
        } finally {
            ScheduleContext.reset();
        }
        // 4.发送邮件
        sendEmail(result);
    }

    private static final List<LPCompareResultVo> compareLandingPagePromotionInner(List<BusyLandingProductVo> landingProductVos,
                                                                                  List<GPSPromotionVo> gpsPromotionVos) {
        List<LPCompareResultVo> result = null;
        if(CollectionUtils.isEmpty(landingProductVos) || CollectionUtils.isEmpty(gpsPromotionVos)) {
            return Collections.emptyList();
        }
        // 将landingProductVos转换成map
        Map<String, BusyLandingProductVo> landingProductVoMap = new HashMap<String, BusyLandingProductVo>(
            landingProductVos.size(), 1F);
        for(BusyLandingProductVo landingProductVo : landingProductVos) {
            landingProductVoMap.put(landingProductVo.getPromotionId() + "_" + landingProductVo.getPmId(),
                    landingProductVo);
        }
        result = new ArrayList<LPCompareResultVo>(gpsPromotionVos.size());

        for(GPSPromotionVo gpsPromotionVo : gpsPromotionVos) {
            BusyLandingProductVo landingProductVo = landingProductVoMap.get(gpsPromotionVo.getPromotionId() + "_"
                                                                            + gpsPromotionVo.getPmId());
            if(landingProductVo != null) {
                try {
                    // 比对landingProductVo和gpsPromotionVo
                    result.add(LPCompareHandler.handle(landingProductVo, gpsPromotionVo));
                    // 设置lp比对标记到productId，后续会处理未比对数据
                    landingProductVo.setProductId(ScheduleConstants.LP_HAS_COMPATED_FLAG);
                } catch (Exception e) {
                    log.error("比对异常", e);
                }
            } else {
                // map中取不到，返回数据异常
                result.add(buildBusyLPCompareResultVo(gpsPromotionVo.getPromotionId(), gpsPromotionVo.getPmId(),
                        ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA));
            }
        }
        // 处理landingProductVoMap中未匹配上的数据
        for(Map.Entry<String, BusyLandingProductVo> entry : landingProductVoMap.entrySet()) {
            BusyLandingProductVo landingProductVo = entry.getValue();
            if(landingProductVo != null
               && !ScheduleConstants.LP_HAS_COMPATED_FLAG.equals(landingProductVo.getProductId())) {
                // promotionv2表中存在多余数据，返回数据异常
                result.add(buildBusyLPCompareResultVo(landingProductVo.getPromotionId(), landingProductVo.getPmId(),
                        ScheduleConstants.LP_COMPARE_ERROR_CODE_ERROR_DATA));
            }
        }
        return result;
    }

    private static final List<LPCompareResultVo> buildBusyLPCompareResultVos(List<BusyLandingProductVo> landingProductVos,
                                                                             String errorCode) {
        List<LPCompareResultVo> result = null;
        if(CollectionUtils.isNotEmpty(landingProductVos)) {
            result = new ArrayList<LPCompareResultVo>(landingProductVos.size());
            for(BusyLandingProductVo landingProductVo : landingProductVos) {
                result.add(buildBusyLPCompareResultVo(landingProductVo.getPromotionId(), landingProductVo.getPmId(),
                        errorCode));
            }
        }
        return result;
    }

    private static final LPCompareResultVo buildBusyLPCompareResultVo(Long promotionId, Long pmId, String errorCode) {
        LPCompareResultVo result = null;
        if(promotionId != null && pmId != null) {
            result = new LPCompareResultVo();
            result.setPromotionId(promotionId);
            result.setPmId(pmId);
            result.setErrorCode(errorCode);
        }
        return result;
    }

    private static final void sendEmail(List<LPCompareResultVo> busyLPCompareResultVos) {
        if(CollectionUtils.isEmpty(busyLPCompareResultVos)) {
            return;
        }
        String title = "landingPage促销比对异常数据";
        String templateName = "lpCompareResult_zh_CN.ftl";
        // 获取当天0点时间
        Date currDay = ScheduleDateUtils.parseDate(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = sdf.format(DateUtil.addDays(currDay, -1));
        String endDate = sdf.format(DateUtil.addDays(currDay, 1));
        // 分批发邮件
        List<List<LPCompareResultVo>> lists = GPSUtils.split(busyLPCompareResultVos,
                ScheduleConstants.LP_COMPARE_SEND_MAIL_THRESHOLD);
        for(List<LPCompareResultVo> list : lists) {
            Map<String, Object> map = new HashMap<String, Object>(3, 1F);
            map.put("resultList", list);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            String html = null;
            try {
                html = TemplateFactory.generateHtmlFromFtl(templateName, map);
            } catch (Exception e) {
                String errorContent = "取freemaker模板异常，请检查或重新触发定时器！";
                log.error(errorContent, e);
                BusyMailUtil.sendMail(title, errorContent);
                // 异常只报警一次
                break;
            }
            BusyMailUtil.sendHtmlMail(title, html);
        }
    }

    private static final void sendEmail(List<Long> promotionIds, String content) {
        if(CollectionUtils.isEmpty(promotionIds)) {
            return;
        }
        if(StringUtils.isEmpty(content)) {
            content = "异常促销如下，请检查！";
        }
        String title = "landingPage促销比对异常数据";
        String templateName = "lpCompareResultWithoutPmId_zh_CN.ftl";
        // 获取当天0点时间
        Date currDay = ScheduleDateUtils.parseDate(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = sdf.format(DateUtil.addDays(currDay, -1));
        String endDate = sdf.format(DateUtil.addDays(currDay, 1));
        // 分批发邮件
        List<List<Long>> lists = GPSUtils.split(promotionIds, ScheduleConstants.LP_COMPARE_SEND_MAIL_THRESHOLD);
        for(List<Long> list : lists) {
            Map<String, Object> map = new HashMap<String, Object>(4, 1F);
            map.put("resultList", list);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("content", content);
            String html = null;
            try {
                html = TemplateFactory.generateHtmlFromFtl(templateName, map);
            } catch (Exception e) {
                String errorContent = "取freemaker模板异常，请检查或重新触发定时器！";
                log.error(errorContent, e);
                BusyMailUtil.sendMail(title, errorContent);
                // 异常只报警一次
                break;
            }
            BusyMailUtil.sendHtmlMail(title, html);
        }
    }
}