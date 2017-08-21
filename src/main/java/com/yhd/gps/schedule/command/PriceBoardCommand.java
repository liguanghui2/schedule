package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.pricechange.vo.PriceBoardInfoVo;
import com.yhd.gps.schedule.common.CommonUtils;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.remoteService.ProductInfoRemoteService;
import com.yhd.gps.schedule.service.PriceBoardDataRecordService;
import com.yhd.gps.schedule.vo.DateIntervalVo;
import com.yhd.pss.spi.pminfo.vo.FullFieldsPmInfoWithProductVo;
import com.yhd.pss.spi.pminfo.vo.PmInfo;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;

/**
 * @author sunfei
 * @CreateTime 2017-6-29 下午03:08:10
 */
public class PriceBoardCommand extends ShardingDataExecCommand<Integer, List<Long>> {
    private Logger logger = Logger.getLogger(PriceBoardCommand.class);
    private Integer pageSize;
    private ProductInfoRemoteService productRemoteService;
    private PriceBoardDataRecordService priceBoardDataRecordService;

    public PriceBoardCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType, Integer pageSize,
                             ProductInfoRemoteService productRemoteService,
                             PriceBoardDataRecordService priceBoardDataRecordService) {
        super(shardingIndex, finishSignal, bussinessType);
        this.pageSize = pageSize;
        this.productRemoteService = productRemoteService;
        this.priceBoardDataRecordService = priceBoardDataRecordService;
    }

    @Override
    public List<Long> fetchBussinessDataes(int shardingIndex) {
        try {
            // 1.获取所有的城市精选商家列表
            List<Long> allCityPickMerchantIds = productRemoteService.queryAllCityMerchantIds();
            // 记录这台机器需要处理的商家ids
            List<Long> dealMerchantIds = new ArrayList<Long>();
            for(Long merchantId : allCityPickMerchantIds) {
                // merchantId%16的值等于抢占的切片数，则该商家下的商品由当前机器来处理
                if(merchantId % 16 == shardingIndex) {
                    dealMerchantIds.add(merchantId);
                }
            }
            //-------------------------
            System.out.println("需要处理的商家ids为："+dealMerchantIds);
            
            if(CollectionUtils.isEmpty(dealMerchantIds)) {
                return Collections.emptyList();
            }
            for(Long dealMerchantId : dealMerchantIds) {
                Long currentPage = 1L;
                String refMerchant = MiscConfigUtils.getItemValue(ScheduleConstants
                        .buildCityPickRelevanceMerchantKey(dealMerchantId));
                // 2.分页查询城市精选商家下面的所有商品信息
                while(true) {
                    List<FullFieldsPmInfoWithProductVo> pmInfoVos = productRemoteService.queryPmInfosByMerchantId(
                            dealMerchantId, currentPage, Long.valueOf(pageSize));
                    if(CollectionUtils.isEmpty(pmInfoVos)) {
                        break;
                    }
                    
                    //------------------------
                    System.out.println("当前merchantId="+dealMerchantId+" ,currentPage="+currentPage+", 查pss PmInfoVos个数："+pmInfoVos.size());
                    
                    List<Long> originPmIds = new ArrayList<Long>();
                    List<Long> productIds = new ArrayList<Long>();
                    List<Long> refPmIds = new ArrayList<Long>();
                    // 需要去查询售价看板的pmIds
                    List<Long> queryPmIds = new ArrayList<Long>();
                    // 存在关联商品的pmIds
                    List<Long> hasRefPmIds = new ArrayList<Long>();
                    // 产品与原始pmId的映射
                    Map<Long, Long> productId2OriginPmIdMap = new HashMap<Long, Long>(pmInfoVos.size(), 1F);
                    // 产品与关联pmId的映射
                    Map<Long, Long> productId2refPmIdMap = new HashMap<Long, Long>(pmInfoVos.size(), 1F);
                    for(FullFieldsPmInfoWithProductVo fullFieldsPmInfoWithProductVo : pmInfoVos) {
                        Long oriPmid = fullFieldsPmInfoWithProductVo.getPmInfo().getId();
                        Long productId = fullFieldsPmInfoWithProductVo.getPmInfo().getProductId();
                        originPmIds.add(oriPmid);
                        productIds.add(productId);
                        productId2OriginPmIdMap.put(productId, oriPmid);
                    }
                    if(refMerchant != null) {
                        List<PmInfo> refPmInfoVos = productRemoteService.getPmInfoByMerchantIdAndProductIds(
                                Long.valueOf(refMerchant), productIds);
                        if(CollectionUtils.isNotEmpty(refPmInfoVos)) {
                            for(PmInfo pmInfo : refPmInfoVos) {
                                refPmIds.add(pmInfo.getId());
                                productId2refPmIdMap.put(pmInfo.getProductId(), pmInfo.getId());
                            }
                        }
                        // 能查到关联商品的用关联商品id查询，查询不到关联商品的，用原始商品id查询
                        queryPmIds.addAll(refPmIds);
                        // 查询到的关联商品id的个数同原始商品id的个数不一致，表示有些数据没有查询到关联商品，需要用原始商品id查询
                        if(refPmIds.size() < originPmIds.size()) {
                            for(Entry<Long, Long> entry : productId2refPmIdMap.entrySet()) {
                                Long productId = entry.getKey();
                                Long originPmId = productId2OriginPmIdMap.get(productId);
                                hasRefPmIds.add(originPmId);
                            }
                            originPmIds.removeAll(hasRefPmIds);
                            queryPmIds.addAll(originPmIds);
                        }
                    } else {
                        // 没有关联商家配置，直接使用原始pmIds查询售价看板数据
                        queryPmIds.addAll(originPmIds);
                    }
                    
                    //----------------------------------
                    System.out.println("需要查询的queryPmIds="+queryPmIds);
                    
                    Date currentTime = new Date();
                    DateIntervalVo dateIntervalVo = null;
                    if(ScheduleConstants.PRICE_BOARD_DAILY.equals(bussinessType)) {
                        // 日售价看板数据
                        dateIntervalVo = ScheduleDateUtils.getYesterdayDateRange(currentTime);
                    } else if(ScheduleConstants.PRICE_BOARD_WEEKLY.equals(bussinessType)) {
                        // 周售价看板数据
                        dateIntervalVo = ScheduleDateUtils.getLastWeekDateRange2(currentTime);
                    } else if(ScheduleConstants.PRICE_BOARD_MONTHLY.equals(bussinessType)) {
                        // 月售价看板数据
                        dateIntervalVo = ScheduleDateUtils.getLastMonthDateRange(currentTime);
                    } else if(ScheduleConstants.PRICE_BOARD_QUARTERLY.equals(bussinessType)) {
                        // 季度(90天)售价看板数据
                        dateIntervalVo = ScheduleDateUtils.getXDateRange(currentTime, 90);
                    } else if(ScheduleConstants.PRICE_BOARD_SEMESTERTLY.equals(bussinessType)) {
                        // 半年(180天)价格看板数据
                        dateIntervalVo = ScheduleDateUtils.getXDateRange(currentTime, 180);
                    } else {
                        logger.error("未知的价格看板业务类型：--> " + bussinessType);
                        return Collections.emptyList();
                    }
                    List<List<Long>> splitList = CommonUtils.splitList(queryPmIds,
                            ScheduleConstants.DEFAULT_QUERY_MAX_SIZE);
                    for(List<Long> list : splitList) {
                        List<PriceBoardInfoVo> priceBoardInfoVos = null;
                        // 3.查询gps售价看板数据
                        try {
                            priceBoardInfoVos = BusyStockClientUtil.getBusyPriceChangeFacadeService()
                                    .queryHistoryPriceStasticDataForBi(list, dateIntervalVo.getStartDate(),
                                            dateIntervalVo.getEndDate());
                        } catch (Exception e) {
                            logger.error("查询gps接口BusyPriceChangeFacadeService().queryHistoryPriceStasticDataForBi发生异常", e);
                            
                            //---------------------------------------------
                            System.out.println("查询gps接口BusyPriceChangeFacadeService().queryHistoryPriceStasticDataForBi发生异常");
                        }
                        //-----------------------------------
                        System.out.println("查询gps接口活得priceBoardInfoVos个数为："+priceBoardInfoVos.size());
                        
                        // 4.写入售价看板历史表
                        priceBoardDataRecordService.batchInsertPriceBoardData(priceBoardInfoVos, bussinessType, dateIntervalVo);
                    }
                    System.out.println("-----------------------------------------------------------------------");
                    currentPage ++;
                }
            }
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public ExecResult<Integer> doWork(List<Long> bussinessDataes) {
        return null;
    }

    @Override
    public int updateData2Processed(List<Long> dataIds) {
        return 0;
    }
}
