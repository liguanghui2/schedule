package com.yhd.gps.schedule.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.service.AdvicePriceService;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataPageExecCommand;
import com.yihaodian.backend.price.cityPick.vo.SensitivePageVo;
import com.yihaodian.pis.service.api.vo.OpponSiteBasePrice;

/**
 * Update sensitive price
 * Created by zhangshuqiang on 2017/5/19.
 *
 */
public class SensitivePriceUpdateCommand extends ShardingDataPageExecCommand<Void,List<SensitivePageVo>> {

    private AdvicePriceService advicePriceService;

    public SensitivePriceUpdateCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                       Integer pageSize, AdvicePriceService advicePriceService) {
        super(shardingIndex, finishSignal, bussinessType, pageSize);
        this.advicePriceService = advicePriceService;
    }

    @Override
    public List<SensitivePageVo> fetchBussinessDataes(int shardingIndex, Integer pageSize, Integer currentPage) {
        List<SensitivePageVo> sensitivePageVos = advicePriceService.getPmSensitiveInfoVoByCondition(
                shardingIndex, pageSize, currentPage);
        return sensitivePageVos;
    }

    @Override
    public ExecResult<List<Void>> doWork(List<SensitivePageVo> bussinessDataes) {

        Set<Long> pmInfoIds = new HashSet<Long>();
        //extract pmInfoIds
        for (SensitivePageVo sensitivePageVo : bussinessDataes)
        {
            pmInfoIds.add(sensitivePageVo.getPmId());
        }
        
        //query pis price and set price
        Map<Long,OpponSiteBasePrice> tMallPmId2Price = advicePriceService.queryOpponBasePriceByPmIds(new ArrayList<Long>(pmInfoIds), ScheduleConstants.TMALL_NANJING_SITE);
        Map<Long,OpponSiteBasePrice> suningPmId2Price = advicePriceService.queryOpponBasePriceByPmIds(new ArrayList<Long>(pmInfoIds), ScheduleConstants.SUNING_NANJING_SITE);

        List<SensitivePageVo> updatedSensitivePrices = new ArrayList<SensitivePageVo>();

        Set<Long> queryAIPmInfoList = new HashSet<Long>();

        //set pis price
        Iterator<SensitivePageVo> sensitiveIter = bussinessDataes.iterator();
        while (sensitiveIter.hasNext())
        {
            SensitivePageVo sensitivePageVo = (SensitivePageVo)sensitiveIter.next();
            OpponSiteBasePrice tMallPrice = tMallPmId2Price.get(sensitivePageVo.getPmId());
            OpponSiteBasePrice suningPrice = suningPmId2Price.get(sensitivePageVo.getPmId());
            if (!fillPisPrice(sensitivePageVo, tMallPrice, suningPrice)){
                queryAIPmInfoList.add(sensitivePageVo.getPmId());
            } else {
                updatedSensitivePrices.add(sensitivePageVo);
                sensitiveIter.remove();
            }
        }

        //query and set AI price
        if (CollectionUtils.isNotEmpty(queryAIPmInfoList))
        {
            Map<Long, BigDecimal> aiPmId2Price = advicePriceService.queryAiPriceByPmIds(new ArrayList<Long>(queryAIPmInfoList));
            if (!aiPmId2Price.isEmpty())
            {
                for (SensitivePageVo sensitivePageVo : bussinessDataes)
                {
                    BigDecimal price = aiPmId2Price.get(sensitivePageVo.getPmId());
                    if (price != null)
                    {
                        sensitivePageVo.setSensitiveMinPrice(price);
                        sensitivePageVo.setSensitiveMaxPrice(price);
                        updatedSensitivePrices.add(sensitivePageVo);
                    }
                }
            }
        }

        //update sensitive price
        advicePriceService.updateSensitivePrice(updatedSensitivePrices);

        return null;
    }

    private boolean fillPisPrice(SensitivePageVo sensitivePageVo, OpponSiteBasePrice tMallPrice,
                                OpponSiteBasePrice suningPrice)
    {
        boolean isSuccess = true;

        if (tMallPrice == null && suningPrice == null)
        {
            isSuccess = false;
        }else {
            if (tMallPrice == null) {
                doSetPisPrice(sensitivePageVo, suningPrice);
            } else if (suningPrice == null) {
                doSetPisPrice(sensitivePageVo, tMallPrice);
            } else {
                doSetPisPrice(sensitivePageVo, tMallPrice, suningPrice);
            }
        }

        return isSuccess;
    }

    private void doSetPisPrice(SensitivePageVo sensitivePageVo, OpponSiteBasePrice opponSiteBasePrice)
    {
        //只有一个第三方页面常见价时，敏感品最小价格和敏感品最大价格相等
        Float price = opponSiteBasePrice.getPrice();
        Float maxPrice = price;
        Float minPrice = price;
        sensitivePageVo.setSensitiveMaxPrice(new BigDecimal(maxPrice));
        sensitivePageVo.setSensitiveMinPrice(new BigDecimal(minPrice));
    }

    private void doSetPisPrice(SensitivePageVo sensitivePageVo, OpponSiteBasePrice opponSiteBasePrice1,
                               OpponSiteBasePrice opponSiteBasePrice2)
    {
        //有多个第三方页面常见价时，最低的放在敏感品最小价格，最高的放在敏感品最大价格
        Float maxPrice = Math.max(opponSiteBasePrice1.getPrice(), opponSiteBasePrice2.getPrice());
        Float minPrice = Math.min(opponSiteBasePrice1.getPrice(), opponSiteBasePrice2.getPrice());
        sensitivePageVo.setSensitiveMaxPrice(new BigDecimal(maxPrice));
        sensitivePageVo.setSensitiveMinPrice(new BigDecimal(minPrice));
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        return 0;
    }
}
