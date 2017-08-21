package com.yhd.gps.schedule.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.schedule.dao.GoldCoinPriceChangeHistoryDao;
import com.yhd.gps.schedule.service.GoldCoinPriceChangeHistoryService;
import com.yhd.gps.schedule.vo.HistoryGoldCoinPriceChangeMsgVo;

public class GoldCoinPriceChangeHistoryServiceImpl implements GoldCoinPriceChangeHistoryService {

    private GoldCoinPriceChangeHistoryDao goldCoinPriceChangeHistoryDao;

    private static final Logger logger = Logger.getLogger(GoldCoinPriceChangeHistoryServiceImpl.class);

    @Override
    public Integer batchInsertGoldCoinHistoryPriceChangeMsg(List<HistoryGoldCoinPriceChangeMsgVo> goldCoinPriceChangeMsgList) {
        if(CollectionUtils.isEmpty(goldCoinPriceChangeMsgList)) {
            return 0;
        }
        Integer rows = 0;
        try {
            rows = goldCoinPriceChangeHistoryDao.batchInsertGoldCoinHistoryPriceChangeMsg(goldCoinPriceChangeMsgList);
        } catch (Exception e) {
            logger.error("####批量插入金币促销历史价格数据异常!####" + e.getMessage());
        }
        return rows;
    }

    public GoldCoinPriceChangeHistoryDao getGoldCoinPriceChangeHistoryDao() {
        return goldCoinPriceChangeHistoryDao;
    }

    public void setGoldCoinPriceChangeHistoryDao(GoldCoinPriceChangeHistoryDao goldCoinPriceChangeHistoryDao) {
        this.goldCoinPriceChangeHistoryDao = goldCoinPriceChangeHistoryDao;
    }
}
