package com.yhd.gps.schedule.parall.param;

import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.pss.spi.pminfo.service.QueryPmInfoRemoteService;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.client.facade.BusyPriceFacadeService;
import com.yihaodian.pss.client.PssClient;

/**
 * ---- 补偿历史价格数据参数对象 ------
 * @Author  lipengfei
 * @CreateTime  2016-8-25 下午10:21:39
 */
public class CompensateHistoryPriceDataParam {
    
    private static final long serialVersionUID = 1L;
    
    private static final QueryPmInfoRemoteService queryPmInfoService = PssClient.getInstance(ScheduleConstants.PSS_TIME_OUT,ScheduleConstants.GROUP_NAME).getQueryPmInfoRemoteService();
    
    private static final BusyPriceFacadeService busyPriceFacadeService = BusyStockClientUtil.getBusyPriceFacadeService();
    
    private Long merchantId;
    
    private BusyPmInfoDao busyPmInfoDao ;
    
    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;
    
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public BusyPmInfoDao getBusyPmInfoDao() {
        return busyPmInfoDao;
    }

    public void setBusyPmInfoDao(BusyPmInfoDao busyPmInfoDao) {
        this.busyPmInfoDao = busyPmInfoDao;
    }

    public HistoryPriceChangeMsgDao getHistoryPriceChangeMsgDao() {
        return historyPriceChangeMsgDao;
    }

    public void setHistoryPriceChangeMsgDao(HistoryPriceChangeMsgDao historyPriceChangeMsgDao) {
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
    }

    public static QueryPmInfoRemoteService getQuerypminfoservice() {
        return queryPmInfoService;
    }

    public static BusyPriceFacadeService getBusyPriceFacadeService() {
        return busyPriceFacadeService;
    }

    public CompensateHistoryPriceDataParam(Long merchantId, BusyPmInfoDao busyPmInfoDao,
                                           HistoryPriceChangeMsgDao historyPriceChangeMsgDao) {
        super();
        this.merchantId = merchantId;
        this.busyPmInfoDao = busyPmInfoDao;
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
    }
}

