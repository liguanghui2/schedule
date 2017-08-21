package com.yhd.gps.schedule.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.DataProcessScannerVo;
import com.yihaodian.busy.exception.BusyException;

public class DataProcessScannerDaoImpl extends ScheduleBaseDao implements DataProcessScannerDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<DataProcessScannerVo> getDataProcessScanner(Integer businessType, int shardingIndex, Integer pageSize) {
        List<DataProcessScannerVo> result = Collections.emptyList();
        if(businessType != null && pageSize != null && pageSize > 0) {
            Map<String, Object> params = new HashMap<String, Object>(3, 1F);
            params.put("businessType", businessType);
            params.put("shardingIndex", shardingIndex);
            params.put("pageSize", pageSize);
            result = queryForList("getDataProcessScanner", params);
        }
        return result;
    }

    @Override
    public int batchUpdateDataProcessScannerNextProcessTimeByIds(List<Long> ids, Date nextProcessTime) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ids)) {
            Map<String, Object> param = new HashMap<String, Object>(2, 1F);
            param.put("ids", ids);
            param.put("nextProcessTime", nextProcessTime);
            try {
                BusyDbContext.switchToMasterDB();
                result = update("batchUpdateDataProcessScannerNextProcessTimeByIds", param);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @Override
    public int batchDeleteDataProcessScannerByIds(List<Long> ids) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ids)) {
            Map<String, Object> param = new HashMap<String, Object>(1, 1F);
            param.put("ids", ids);
            try {
                BusyDbContext.switchToMasterDB();
                result = delete("batchDeleteDataProcessScannerByIds", param);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @Override
    public Long insertDataProcessScanner(DataProcessScannerVo scannerVo) {
        Long result = 0L;
        if(scannerVo != null && scannerVo.getBusinessType() != null && scannerVo.getRefId() != null) {
            try {
                BusyDbContext.switchToMasterDB();
                result = insert("insertDataProcessScanner", scannerVo);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @Override
    public DataProcessScannerVo getDataProcessScannerByRefId(Integer businessType, Long refId) {
        DataProcessScannerVo scannerVo = null;
        if(businessType != null && refId != null && refId > 0) {
            Map<String, Object> param = new HashMap<String, Object>(2, 1F);
            param.put("businessType", businessType);
            param.put("refId", refId);
            scannerVo = (DataProcessScannerVo) queryForObject("getDataProcessScannerByRefId", param);
        }
        return scannerVo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DataProcessScannerVo> batchGetDataProcessScannerByRefIds(Integer businessType, List<Long> refIds) {
        List<DataProcessScannerVo> result = Collections.emptyList();
        if(businessType != null && CollectionUtils.isNotEmpty(refIds)) {
            Map<String, Object> param = new HashMap<String, Object>(2, 1F);
            param.put("businessType", businessType);
            param.put("refIds", refIds);
            result = queryForList("batchGetDataProcessScannerByRefIds", param);
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "deprecation" })
    @Override
    public int batchInsertDataProcessScanner(final List<DataProcessScannerVo> scannerVos) {
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(DataProcessScannerVo scannerVo : scannerVos) {
                        sqlMapExecutor.insert("insertDataProcessScanner", scannerVo);
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
            result = 0;
            throw new BusyException("批量插入失败!操作回滚", e.getCause());
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }
}