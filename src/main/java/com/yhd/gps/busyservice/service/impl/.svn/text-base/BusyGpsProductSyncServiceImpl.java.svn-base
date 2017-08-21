package com.yhd.gps.busyservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.yhd.gps.backendprice.vo.GpsProductVo;
import com.yhd.gps.busyservice.dao.BusyGpsProductDao;
import com.yhd.gps.busyservice.dao.BusyProductDao;
import com.yhd.gps.busyservice.dao.SyncDataLogDao;
import com.yhd.gps.busyservice.service.BusyGpsProductSyncService;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.vo.SyncDataLogVo;
import com.yihaodian.busy.common.GPSUtils;

public class BusyGpsProductSyncServiceImpl implements BusyGpsProductSyncService {
    private static final Logger LOG = Logger.getLogger(BusyGpsProductSyncServiceImpl.class);

    private BusyGpsProductDao busyGpsProductDao;
    private BusyProductDao busyProductDao;
    private SyncDataLogDao syncDataLogDao;

    public void setBusyGpsProductDao(BusyGpsProductDao busyGpsProductDao) {
        this.busyGpsProductDao = busyGpsProductDao;
    }

    public void setBusyProductDao(BusyProductDao busyProductDao) {
        this.busyProductDao = busyProductDao;
    }

    public void setSyncDataLogDao(SyncDataLogDao syncDataLogDao) {
        this.syncDataLogDao = syncDataLogDao;
    }

    @Override
    public int compensateGpsProduct() {
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        // 需要同步的productId
                        Set<Long> productIds = new HashSet<Long>();
                        List<Long> productIdsFromPmPrice = busyGpsProductDao.getProductIdNotInGpsPmPrice();
                        if(CollectionUtils.isNotEmpty(productIdsFromPmPrice)) {
                            productIds.addAll(productIdsFromPmPrice);
                        }

                        List<Long> productIdsFromPromRule = busyGpsProductDao.getProductIdNotInGpsPromRule();
                        if(CollectionUtils.isNotEmpty(productIdsFromPromRule)) {
                            productIds.addAll(productIdsFromPromRule);
                        }

                        if(CollectionUtils.isEmpty(productIds)) {
                            return;
                        }

                        // 若需要同步的product数量超过1000条,需要分批
                        final int maxBatchSize = 1000;
                        List<List<Long>> allInsertProductIds = GPSUtils.split(new ArrayList<Long>(productIds),
                                maxBatchSize);
                        for(List<Long> subProductIdList : allInsertProductIds) {
                            List<GpsProductVo> subSyncProductList = busyProductDao.batchGetGpsProduct(subProductIdList);
                            if(CollectionUtils.isNotEmpty(subSyncProductList)) {
                                busyGpsProductDao.batchInsertGpsProduct(subSyncProductList);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error(e);
                    }
                }
            }
        }.start();
        return 1;
    }

    @Override
    public void handleSyncDataLog() {
        // 求出1000条出错的记录
        List<SyncDataLogVo> list = syncDataLogDao.getSyncDataLogVoList();
        if(CollectionUtils.isEmpty(list)) {
            return;
        }

        List<Long> productIds = new ArrayList<Long>(list.size());
        for(SyncDataLogVo vo : list) {
            productIds.add(vo.getKeyId());
        }

        // 查product+category
        List<GpsProductVo> gpsProductList = busyProductDao.batchGetGpsProduct(productIds);
        updateOrInsertGpsProduct(gpsProductList, false);
    }

    @Override
    public Long syncProductToGps(Date startTime, Date endTime, Long pageSize) {
        // 查询需要10分钟需要同步的数据总条数
        Long totalCount = busyProductDao.getCountGpsProductForSync(startTime, endTime);
        if(totalCount <= 0) {
            return 0L;
        }
        LOG.info("Sync Product to GpsProduct, " + ScheduleDateUtils.format(startTime) + " to "
                 + ScheduleDateUtils.format(endTime) + ", count is " + totalCount);
        Long pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        // 分页获取的PRODUCT表的最晚同步的id
        Long lastId = null;
        for(Long currentPage = 1L; currentPage <= pageCount; currentPage++) {
            try {
                // 查询本页需同步的数据进行同步
                Long startRow = (currentPage - 1L) * pageSize + 1L;
                List<GpsProductVo> productList = busyProductDao.getPageGpsProductForSync(startTime, endTime, lastId,
                        startRow, startRow + pageSize);
                if(CollectionUtils.isEmpty(productList)) {
                    break;
                }
                GpsProductVo gpsProductVo = productList.get(productList.size() - 1);
                lastId = gpsProductVo.getId();

                // 更新或者新增GPS_PRODUCT数据，这里需要记录错误日志
                updateOrInsertGpsProduct(productList);
                LOG.info("currentPage=[" + currentPage + "], size=[" + productList.size() + "]");
            } catch (Exception e) {
                LOG.error("同步GpsProduct出错,startTime=[" + ScheduleDateUtils.format(startTime) + "],endTime=["
                          + ScheduleDateUtils.format(endTime) + "],errSyncPage=[" + currentPage + "]:", e);
            }
        }
        return totalCount;
    }

    /**
     * 保存出错日志
     * @param gpsProductList
     */
    private void saveSyncErrorLog(List<GpsProductVo> gpsProductList) {
        List<SyncDataLogVo> voList = new ArrayList<SyncDataLogVo>(gpsProductList.size());
        for(GpsProductVo gpsProduct : gpsProductList) {
            SyncDataLogVo vo = new SyncDataLogVo();
            vo.setKeyId(gpsProduct.getId());
            voList.add(vo);
        }
        syncDataLogDao.batchInsertSyncDataLog(voList);
    }

    /**
     * 删除出错日志
     * @param gpsProductList
     */
    private void deleteSyncErrorLog(List<GpsProductVo> gpsProductList) {
        List<Long> productIds = new ArrayList<Long>();
        for(GpsProductVo vo : gpsProductList) {
            productIds.add(vo.getId());
        }
        syncDataLogDao.batchDeleteSyncDataLogVo(productIds);
    }

    /**
     * 更新或者新增GPS_PRODUCT数据，这里需要记录错误日志
     * @param gpsProductList
     * @throws Exception
     */
    private void updateOrInsertGpsProduct(List<GpsProductVo> gpsProductList) throws Exception {
        try {
            updateOrInsertGpsProduct(gpsProductList, true);
        } catch (Exception e) {
            saveSyncErrorLog(gpsProductList);
            throw e;
        }
    }

    /**
     * 新增或修改gps_product
     * @param gpsProductList
     * @param isNeedRecordErrorLog 是否记错误日志
     */
    private void updateOrInsertGpsProduct(List<GpsProductVo> gpsProductList, boolean isNeedRecordErrorLog) {
        if(CollectionUtils.isEmpty(gpsProductList)) {
            return;
        }
        List<Long> productIds = new ArrayList<Long>();
        for(GpsProductVo vo : gpsProductList) {
            productIds.add(vo.getId());
        }
        // 分开哪些是update哪些是insert
        List<GpsProductVo> insertList = new ArrayList<GpsProductVo>(gpsProductList.size());
        List<GpsProductVo> updateList = new ArrayList<GpsProductVo>(gpsProductList.size());
        // 查出哪些是已经存在的
        List<GpsProductVo> exsitProductVos = busyGpsProductDao.batchGetGpsProductByIds(productIds);
        Map<Long, GpsProductVo> productIdMap = new HashMap<Long, GpsProductVo>(exsitProductVos.size());
        for(GpsProductVo vo : exsitProductVos) {
            productIdMap.put(vo.getId(), vo);
        }
        for(GpsProductVo vo : gpsProductList) {
            GpsProductVo oldVo = productIdMap.get(vo.getId());
            if(oldVo != null && !oldVo.equals(vo)) {
                updateList.add(vo);
            } else if(oldVo == null) {
                insertList.add(vo);
            }
        }
        int result = busyGpsProductDao.batchInsertGpsProduct(insertList);
        // 新增条数为0且需要记录错误日志，则记录错误日志
        if(result == 0 && isNeedRecordErrorLog) {
            saveSyncErrorLog(insertList);
        }
        // 新增条数>0且无需记录错误日志(补偿错误情况)，则删除错误日志
        else if(result == 1 && !isNeedRecordErrorLog) {
            deleteSyncErrorLog(insertList);
        }

        result = busyGpsProductDao.batchUpdateGpsProduct(updateList);
        if(result == 0 && isNeedRecordErrorLog) {
            saveSyncErrorLog(updateList);
        } else if(result == 1 && !isNeedRecordErrorLog) {
            deleteSyncErrorLog(gpsProductList);
        }
    }

    @Override
    public int compensateGpsProductByProductIds(List<Long> productIdList) {
        int syncNum = 0;
        if(CollectionUtils.isEmpty(productIdList)) {
            return syncNum;
        }
        try {
            List<GpsProductVo> gpsProductList = busyProductDao.batchGetGpsProduct(productIdList);
            if(CollectionUtils.isNotEmpty(gpsProductList)) {
                for(GpsProductVo vo : gpsProductList) {
                    // 产品在GPS_PRODUCT表中不存在则插入,存在则更新
                    busyGpsProductDao.insertOrUpdateGpsProduct(vo);
                    syncNum++;
                }
            }
        } catch (Exception e) {
            LOG.error("通过productId同步GPS_PRODUCT表数据发生异常：", e);
        }
        return syncNum;
    }

}