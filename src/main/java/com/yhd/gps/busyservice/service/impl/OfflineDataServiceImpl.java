package com.yhd.gps.busyservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yhd.gps.busyservice.dao.PmPriceDao;
import com.yhd.gps.busyservice.service.OfflineDataService;
import com.yhd.gps.schedule.vo.PmPriceVo;

/**
 * @author:liguanghui1
 * @date ：2017-3-16 上午09:57:09
 */
public class OfflineDataServiceImpl implements OfflineDataService {

    private static final Logger LOG = Logger.getLogger(OfflineDataServiceImpl.class);

    private PmPriceDao pmPriceDao;

    @Override
    public List<PmPriceVo> getPmInfoIdsFromPmPriceForOfflineData(int shardingIndex, Long offset, String beginTime) {
        List<PmPriceVo> result = new ArrayList<PmPriceVo>();
        try {
            result = pmPriceDao.getPmInfoIdsFromPmPriceForOfflineData(shardingIndex, offset, beginTime);
        } catch (Exception e) {
            LOG.error("###初始化OfflineData查询PmPrice异常###", e);
        }
        return result;
    }

    public void setPmPriceDao(PmPriceDao pmPriceDao) {
        this.pmPriceDao = pmPriceDao;
    }

}
