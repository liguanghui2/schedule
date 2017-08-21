package com.yhd.gps.busyservice.service.impl;

import java.math.BigDecimal;
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
import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.JDBookSyncLogDao;
import com.yhd.gps.busyservice.service.JDBookSyncService;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.jd.JDBookSyncMessageConsumer;
import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yhd.gps.schedule.vo.JDBookSyncLogVo;
import com.yhd.pss.spi.baseinfo.vo.ProductListPriceAndProductIdVo;
import com.yhd.pss.spi.baseinfo.vo.input.ProductListPriceAndProductIdRequest;
import com.yihaodian.architecture.jumper.consumer.BackoutMessageException;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.vo.BSPmPriceVo;
import com.yihaodian.openapi.dto.jd.hessian.request.QueryJdWarePPriceGetRequest;
import com.yihaodian.openapi.dto.jd.hessian.response.JdPriceChange;
import com.yihaodian.openapi.dto.jd.hessian.response.JdWarePPriceGetResponse;
import com.yihaodian.openapi.dto.jd.hessian.response.QueryJdWarePPriceGetResponse;
import com.yihaodian.openapi.service.jd.util.JdJosApiServiceUtil;
import com.yihaodian.pss.client.PssClient;

/**
 * @author sunfei
 * @CreateTime 2016-11-29 下午04:03:19
 */
public class JDBookSyncServiceImpl implements JDBookSyncService {
    private static final Log logger = LogFactory.getLog(JDBookSyncServiceImpl.class);
    private BusyPmInfoDao busyPmInfoDao;
    private JDBookSyncLogDao jdBookSyncLogDao;

    public void setBusyPmInfoDao(BusyPmInfoDao busyPmInfoDao) {
        this.busyPmInfoDao = busyPmInfoDao;
    }

    public void setJdBookSyncLogDao(JDBookSyncLogDao jdBookSyncLogDao) {
        this.jdBookSyncLogDao = jdBookSyncLogDao;
    }

    @Override
    public List<JDBookMessageVo> getJDBookInfoByshardingIndex(Integer shardingIndex, Long offset, List<Long> merchantIds) {
        return busyPmInfoDao.getJDBookSyncInfoByShardingIndex(shardingIndex, offset, merchantIds);
    }

    @Override
    public int syncJDBookPrice(List<JDBookMessageVo> messageVos, String remark) throws BackoutMessageException {
        int result = 0;
        if(!checkParam(messageVos)) {
            return result;
        }
        try {
            Map<String, JDBookMessageVo> jdId2JDBookMessageVo = new HashMap<String, JDBookMessageVo>(
                JDBookSyncMessageConsumer.MAX_MSG_SIZE, 1F);
            for(JDBookMessageVo jdBookMessageVo : messageVos) {
                jdId2JDBookMessageVo.put(jdBookMessageVo.getOuterId(), jdBookMessageVo);
            }

            // 1. 调用open-api接口
            QueryJdWarePPriceGetRequest request = new QueryJdWarePPriceGetRequest();
            request.setSkuidList(new ArrayList<String>(jdId2JDBookMessageVo.keySet()));
            QueryJdWarePPriceGetResponse response = JdJosApiServiceUtil.getInstance().queryJdWarePPrices(request);
            // 先判断open-api是否返回异常
            if(null == response || response.getResponseCode() != JDBookSyncMessageConsumer.OPEN_API_SUCCESS) {
                throw new BackoutMessageException(
                    "查询open-api异常--"
                            + (response == null ? "null" : (response.getReason() == null ? "reason=null" : response
                                    .getReason())));
            }
            // 再判断京东是否返回异常
            JdWarePPriceGetResponse jdResponse = response.getJdWarePPriceGetResponse();
            if(null == jdResponse || !JDBookSyncMessageConsumer.JD_SUCCESS.equals(jdResponse.getCode())) {
                throw new BackoutMessageException(
                    "查询open-api异常-京东返回异常--"
                            + (jdResponse == null ? "null" : (jdResponse.getZhDesc() == null ? "zhDesc=null"
                                                                                            : jdResponse.getZhDesc())));
            }
            List<JdPriceChange> jdPriceChanges = jdResponse.getJdPriceChangeList();
            // 如果返回结果为空，则发邮件通知,并记录日志
            if(CollectionUtils.isEmpty(jdPriceChanges)) {
                batchInsertJDBookSyncLogVoList(processSyncAbnormalData(jdId2JDBookMessageVo, null, "查询京东返回值为空"));
                return result;
            }

            // build数据
            List<BSPmPriceVo> pmPriceVos = new ArrayList<BSPmPriceVo>(JDBookSyncMessageConsumer.MAX_MSG_SIZE);
            List<ProductListPriceAndProductIdVo> productListPriceAndProductIdVos = new ArrayList<ProductListPriceAndProductIdVo>(
                JDBookSyncMessageConsumer.MAX_MSG_SIZE);
            List<JDBookSyncLogVo> jdBookSyncLogVos = new ArrayList<JDBookSyncLogVo>(
                JDBookSyncMessageConsumer.MAX_MSG_SIZE);
            for(JdPriceChange jdPriceChange : jdPriceChanges) {
                String jdId = jdPriceChange.getId();
                // 京东导入到pm_info上的outer_id不带"J_"，京东接口返回的带"J_"，所以需要截取掉前两位
                jdId = jdId.substring(2);
                JDBookMessageVo jdBookMessageVo = jdId2JDBookMessageVo.get(jdId);
                if(null != jdBookMessageVo) {
                    // 该sku的后台京东价
                    BigDecimal price = null;
                    if(null != jdPriceChange.getOp()) {
                        price = new BigDecimal(jdPriceChange.getOp());
                        BSPmPriceVo pmPriceVo = new BSPmPriceVo();
                        pmPriceVo.setPmId(jdBookMessageVo.getPmId());
                        pmPriceVo.setNonMemberPrice(price);
                        pmPriceVos.add(pmPriceVo);
                    }

                    // 市场价 , 市场价用京东返回的手机端市场价
                    BigDecimal marketPrice = null;
                    if(null != jdPriceChange.getM()) {
                        marketPrice = new BigDecimal(jdPriceChange.getM());
                        ProductListPriceAndProductIdVo productListPriceAndProductIdVo = new ProductListPriceAndProductIdVo();
                        productListPriceAndProductIdVo.setProductId(jdBookMessageVo.getProductId());
                        productListPriceAndProductIdVo.setProductListPrice(marketPrice);
                        productListPriceAndProductIdVos.add(productListPriceAndProductIdVo);
                    }

                    // 日志
                    JDBookSyncLogVo logVo = transfer2JDBookSyncLogVo(jdBookMessageVo, price, marketPrice, remark);
                    if(null != logVo) {
                        jdBookSyncLogVos.add(logVo);
                    }
                }
            }
            // 2. 调用gps接口更新基准价
            if(CollectionUtils.isNotEmpty(pmPriceVos)) {
                BusyStockClientUtil.getBusyPriceFacadeService().batchUpdatePmPriceVoList(pmPriceVos);
            }

            // 3. 调用pss接口更新市场价
            if(CollectionUtils.isNotEmpty(productListPriceAndProductIdVos)) {
                ProductListPriceAndProductIdRequest ProductListPriceAndProductIdRequest = new ProductListPriceAndProductIdRequest();
                ProductListPriceAndProductIdRequest.setProductListPriceAndProductId(productListPriceAndProductIdVos);
                PssClient.getInstance(ScheduleConstants.PSS_TIME_OUT,ScheduleConstants.GROUP_NAME).getModifyProductRemoteService()
                        .updateProductListPriceByProductId(ProductListPriceAndProductIdRequest);
            }

            // 4. 记日志
            // 如果入参与京东返回结果数量不一致，则将京东未返回的数据也记入日志，不再发邮件（存在系列品虚码京东查不到结果的情况）
            if(jdPriceChanges.size() != jdId2JDBookMessageVo.size()) {
                jdBookSyncLogVos = processSyncAbnormalData(jdId2JDBookMessageVo, jdBookSyncLogVos,
                        "入参个数与查询京东返回的结果数量不一致！入：" + jdId2JDBookMessageVo.size() + "，出：" + jdPriceChanges.size());
            }
            batchInsertJDBookSyncLogVoList(jdBookSyncLogVos);

            // 返回真实更新价格的数据个数
            result = pmPriceVos.size();
        } catch (Exception e) {
            logger.error("同步京东图书价格异常：", e);
            // 发邮件提醒
            sendEmail(messageVos, e.toString());
            // 异常记日志
            batchInsertJDBookSyncLogVoList(transfer2JDBookSyncLogVoList(messageVos, null, null, remark + e.getMessage()));
            // 抛异常会重试
            throw new BackoutMessageException();
        }
        return result;
    }

    // 校验参数
    private boolean checkParam(List<JDBookMessageVo> messageVos) {
        boolean result = false;
        if(CollectionUtils.isEmpty(messageVos) || messageVos.size() > JDBookSyncMessageConsumer.MAX_MSG_SIZE) {
            return result;
        }
        for(JDBookMessageVo jdBookMessageVo : messageVos) {
            if(null == jdBookMessageVo || null == jdBookMessageVo.getPmId() || null == jdBookMessageVo.getProductId()
               || StringUtils.isEmpty(jdBookMessageVo.getOuterId())) {
                return result;
            }
        }
        result = true;
        return result;
    }

    /**
     * 发邮件
     * @param <T>
     * @param vo
     * @param errorMessage
     * @throws BackoutMessageException
     */
    private static final <T> void sendEmail(List<T> vo, String errorMessage) throws BackoutMessageException {
        if(CollectionUtils.isEmpty(vo)) {
            return;
        }
        try {
            String title = "京东商品价格同步异常";
            String errorContent = vo.toString();
            if(null != errorMessage) {
                errorContent = errorMessage + "\n" + errorContent;
            }
            BusyMailUtil.sendMail(title, errorContent);
        } catch (Exception e) {
            logger.error("同步京东图书价发邮件异常：" + e.getMessage());
            throw new BackoutMessageException();
        }
    }

    /**
     * 记录京东同步日志
     * @param jdBookSyncLogVos
     * @return
     */
    private int batchInsertJDBookSyncLogVoList(List<JDBookSyncLogVo> jdBookSyncLogVos) throws BackoutMessageException {
        int result = 0;
        if(CollectionUtils.isEmpty(jdBookSyncLogVos)) {
            return result;
        }
        try {
            result = jdBookSyncLogDao.batchInsertJDBookSyncLogVoList(jdBookSyncLogVos);
        } catch (Exception e) {
            logger.error("同步京东图书价格写日志异常:" + e.getMessage());
        }
        return result;
    }

    /**
     * 处理异常数据，异常数据转换为日志vo
     * @param jdId2JDBookMessageVo
     * @param jdBookSyncLogVos
     * @param errorMessage
     * @return
     * @throws BackoutMessageException
     */
    private List<JDBookSyncLogVo> processSyncAbnormalData(Map<String, JDBookMessageVo> jdId2JDBookMessageVo,
                                                          List<JDBookSyncLogVo> jdBookSyncLogVos, String errorMessage) throws BackoutMessageException {
        if(jdId2JDBookMessageVo == null || jdId2JDBookMessageVo.isEmpty()) {
            return jdBookSyncLogVos;
        }
        if(CollectionUtils.isEmpty(jdBookSyncLogVos)) {
            jdBookSyncLogVos = new ArrayList<JDBookSyncLogVo>(jdId2JDBookMessageVo.size());
        }

        try {
            // 记录日志list里不存在的jd商品id
            Set<String> jdIds = new HashSet<String>(jdId2JDBookMessageVo.keySet());
            for(JDBookSyncLogVo jdBookSyncLogVo : jdBookSyncLogVos) {
                if(jdIds.contains(jdBookSyncLogVo.getJdId())) {
                    jdIds.remove(jdBookSyncLogVo.getJdId());
                }
            }

            // 将日志list里不存在的jdId也记入日志
            for(String jdId : jdIds) {
                JDBookMessageVo jdBookMessageVo = jdId2JDBookMessageVo.get(jdId);
                JDBookSyncLogVo logVo = transfer2JDBookSyncLogVo(jdBookMessageVo, null, null, errorMessage);
                if(null != logVo) {
                    jdBookSyncLogVos.add(logVo);
                }
            }
            return jdBookSyncLogVos;
        } catch (Exception e) {
            logger.error("处理京东图书同步异常数据时发生异常：" + e.getMessage());
            throw new BackoutMessageException();
        }
    }

    /**
     * 转换成日志Vo
     * @param messageVo
     * @param price
     * @param marketPrice
     * @param remark
     * @return
     */
    private JDBookSyncLogVo transfer2JDBookSyncLogVo(JDBookMessageVo messageVo, BigDecimal price,
                                                     BigDecimal marketPrice, String remark) {
        JDBookSyncLogVo result = null;
        if(null == messageVo) {
            return result;
        }
        List<JDBookMessageVo> messageVos = new ArrayList<JDBookMessageVo>(1);
        messageVos.add(messageVo);
        List<JDBookSyncLogVo> jdBookSyncLogVos = transfer2JDBookSyncLogVoList(messageVos, price, marketPrice, remark);
        if(CollectionUtils.isNotEmpty(jdBookSyncLogVos)) {
            result = jdBookSyncLogVos.get(0);
        }
        return result;
    }

    /**
     * 批量转换成日志Vo
     * @param messageVos
     * @param price
     * @param marketPrice
     * @param remark
     * @return
     */
    private List<JDBookSyncLogVo> transfer2JDBookSyncLogVoList(List<JDBookMessageVo> messageVos, BigDecimal price,
                                                               BigDecimal marketPrice, String remark) {
        if(CollectionUtils.isEmpty(messageVos)) {
            return Collections.emptyList();
        }
        List<JDBookSyncLogVo> jdBookSyncLogVos = new ArrayList<JDBookSyncLogVo>(JDBookSyncMessageConsumer.MAX_MSG_SIZE);
        for(JDBookMessageVo jdBookMessageVo : messageVos) {
            JDBookSyncLogVo logVo = new JDBookSyncLogVo();
            logVo.setJdId(jdBookMessageVo.getOuterId());
            logVo.setPmId(jdBookMessageVo.getPmId());
            logVo.setProductId(jdBookMessageVo.getProductId());
            if(null != price) {
                logVo.setPrice(price);
            }
            if(null != marketPrice) {
                logVo.setMarketPrice(marketPrice);
            }
            logVo.setCreateTime(new Date());
            logVo.setHostIp(JDBookSyncMessageConsumer.localIp);
            if(null != remark) {
                logVo.setRemark(remark);
            }
            jdBookSyncLogVos.add(logVo);
        }
        return jdBookSyncLogVos;
    }
}
