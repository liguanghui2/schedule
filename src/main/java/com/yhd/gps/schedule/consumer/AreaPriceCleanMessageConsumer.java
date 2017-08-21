package com.yhd.gps.schedule.consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONArray;
import com.yhd.gps.busyservice.dao.BusyProductPromRuleDao;
import com.yhd.gps.busyservice.dao.PmAreaPriceDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yihaodian.architecture.jumper.common.message.Message;
import com.yihaodian.architecture.jumper.consumer.BackoutMessageException;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.busy.vo.AreaPriceFatMessageVo;
import com.yihaodian.busy.vo.BusyPmAreaPriceVo;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;
import com.yihaodian.front.databus.DatabusListener;
import com.yihaodian.front.databus.config.DatabusConsumerConfig;
import com.yihaodian.front.databus.jumper.DatabusConsumerJumper;

/**
 * 
 * 区域价清理
 * @Author lipengcheng
 * @CreateTime 2017-1-6 下午04:20:45
 */
public class AreaPriceCleanMessageConsumer implements InitializingBean {
    private static final Log logger = LogFactory.getLog(AreaPriceCleanMessageConsumer.class);

    private static BusyProductPromRuleDao busyProductPromRuleDao = (BusyProductPromRuleDao) SpringBeanUtil
            .getBean("busyProductPromRuleDao");
    private static PmAreaPriceDao pmAreaPriceDao = (PmAreaPriceDao) SpringBeanUtil.getBean("pmAreaPriceDao");

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("根据区域价消息清理区域价表消息监听器已开启------！！！！！！");
        init();
    }

    /**
     * 
     * 清理过期促销对应的区域价
     * @Author lipengcheng
     * @CreateTime 2017-1-6 下午06:46:37
     */
    private static class AreaPriceCleanMessageListener extends DatabusListener<Long> {

        @Override
        public void onMessage(Message msg) throws BackoutMessageException {
            List<AreaPriceFatMessageVo> areaPriceFatMessageVos = JSONArray.parseArray(msg.getContent(),
                    AreaPriceFatMessageVo.class);
            if(CollectionUtils.isEmpty(areaPriceFatMessageVos)) {
                return;
            }
            try {
                // 取出所有促销过期状态的pmId
                List<Long> pmIds = new ArrayList<Long>(areaPriceFatMessageVos.size());
                for(AreaPriceFatMessageVo areaPriceFatMessageVo : areaPriceFatMessageVos) {
                    Long pmId = null;
                    if(null != areaPriceFatMessageVo
                       && BusyConstants.PRICE_MSG_TYPE_PRODUCT_PROM_RULE_END == areaPriceFatMessageVo.getMsgType()) {
                        pmId = areaPriceFatMessageVo.getPmInfoId();
                    }
                    if(null != pmId) {
                        pmIds.add(pmId);
                    }
                }
                if(CollectionUtils.isEmpty(pmIds)) {
                    return;
                }

                // 根据pmIds查促销
                List<BSProductPromRuleVo> bsProductPromRuleVos = busyProductPromRuleDao
                        .batchGetProductPromRuleListByPmIds(pmIds);
                if(CollectionUtils.isEmpty(bsProductPromRuleVos)) {
                    return;
                }
                List<Long> ruleIds = new ArrayList<Long>(bsProductPromRuleVos.size());
                Date now = new Date();
                for(BSProductPromRuleVo bsProductPromRuleVo : bsProductPromRuleVos) {
                    // 记录促销过期的促销id
                    if(bsProductPromRuleVo.getPromEndTime().before(now)) {
                        ruleIds.add(bsProductPromRuleVo.getId());
                    }
                }
                if(CollectionUtils.isEmpty(ruleIds)) {
                    return;
                }

                // 根据促销id查区域表
                List<BusyPmAreaPriceVo> busyPmAreaPriceVos = pmAreaPriceDao.getPmAreaPriceVoListByRefPriceIds(ruleIds);
                if(CollectionUtils.isEmpty(busyPmAreaPriceVos)) {
                    return;
                }
                List<Long> areaPriceIds = new ArrayList<Long>(busyPmAreaPriceVos.size());
                for(BusyPmAreaPriceVo busyPmAreaPriceVo : busyPmAreaPriceVos) {
                    areaPriceIds.add(busyPmAreaPriceVo.getId());
                }

                // 删除过期促销对应的区域价数据
                BusyStockClientUtil.getBusyAreaPriceFacadeService().batchDeletePmAreaPriceVoList4Schedule(areaPriceIds);
            } catch (Exception e) {
                logger.error("根据区域价消息清理区域价表异常：" + e.getMessage());
                throw new BackoutMessageException(e.getMessage());
            }
        }

        @Override
        public void onDatabusMessage(List<Long> items) {

        }
    }

    public static void init() {
        DatabusConsumerConfig consumerConfig = new DatabusConsumerConfig();
        DatabusConsumerJumper<Long> databusConsumer = new DatabusConsumerJumper<Long>();
        // 设置消费者id，自定义
        consumerConfig.setConsumerId(ScheduleConstants.GPS_SCHEDULE_JUMPER_CONSUMER_ID);
        databusConsumer.setConsumerConfig(consumerConfig);
        // 处理区域价变更消息
        databusConsumer.consumeMessage(BusyConstants.GPS_AREA_PRICE_CHANGED, new AreaPriceCleanMessageListener());
    }

}
