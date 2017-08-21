package com.yhd.gps.busyservice.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.backendprice.vo.GpsProductVo;
import com.yhd.gps.busyservice.dao.BusyGpsProductDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;

public class BusyGpsProductDaoImpl extends ScheduleBaseDao implements BusyGpsProductDao {

    private static final Logger log = Logger.getLogger(BusyGpsProductDaoImpl.class);

    @Override
    public GpsProductVo queryGpsProductById(Long id) {
        GpsProductVo result = null;
        if(id != null) {
            result = (GpsProductVo) queryForObject("bs_queryGpsProductById", id);
        }
        return result;
    }

    @Override
    public int updateGpsProductById(GpsProductVo vo) {
        int result = 0;
        if(vo != null && vo.getId() != null) {
            try {
                BusyDbContext.switchToMasterDB();
                result = update("bs_updateGpsProductById", vo);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @Override
    public int deleteGpsProductByIds(List<Long> ids) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ids)) {
            try {
                BusyDbContext.switchToMasterDB();
                result = delete("bs_deleteGpsProductByIds", ids);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @Override
    public Long insertGpsProduct(GpsProductVo vo) {
        Long result = 0L;
        if(vo != null && vo.getId() != null) {
            try {
                BusyDbContext.switchToMasterDB();
                getSqlMapClientTemplate().insert("bs_insertGpsProduct", vo);
            } finally {
                BusyDbContext.reset();
            }
            result = vo.getId();
        }
        return result;
    }

    @Override
    public Long insertOrUpdateGpsProduct(GpsProductVo vo) {
        Long result = 0L;
        if(vo != null && vo.getId() != null) {
            try {
                BusyDbContext.switchToMasterDB();
                getSqlMapClientTemplate().insert("bs_insertOrUpdateGpsProduct", vo);
            } finally {
                BusyDbContext.reset();
            }
            result = vo.getId();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getProductIdNotInGpsPmPrice() {
        return queryForList("gps_getProductIdNotInGpsPmPrice");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getProductIdNotInGpsPromRule() {
        return queryForList("gps_getProductIdNotInGpsPromRule");
    }

    @Override
    public int batchInsertGpsProduct(final List<GpsProductVo> gpsProductList) {
        if(CollectionUtils.isEmpty(gpsProductList)) {
            return 1;
        }
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(GpsProductVo gpsProduct : gpsProductList) {
                        sqlMapExecutor.update("bs_insertGpsProduct", gpsProduct);
                        batch++;
                        // 每500条批量提交一次
                        if(batch == 500) {
                            sqlMapExecutor.executeBatch();
                            batch = 0;
                        }
                    }
                    sqlMapExecutor.executeBatch();
                    return null;
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        } finally {
            BusyDbContext.reset();
        }
        return 1;
    }

    @Override
    public int batchUpdateGpsProduct(final List<GpsProductVo> gpsProductList) {
        if(CollectionUtils.isEmpty(gpsProductList)) {
            return 1;
        }
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(GpsProductVo gpsProduct : gpsProductList) {
                        sqlMapExecutor.update("bs_updateGpsProductById", gpsProduct);
                        batch++;
                        // 每500条批量提交一次
                        if(batch == 500) {
                            sqlMapExecutor.executeBatch();
                            batch = 0;
                        }
                    }
                    sqlMapExecutor.executeBatch();
                    return null;
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        } finally {
            BusyDbContext.reset();
        }
        return 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GpsProductVo> batchGetGpsProductByIds(List<Long> productIds) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("ids", productIds);
        return queryForList("gps_batchGetGpsProductIdsByIds", params);
    }
}
