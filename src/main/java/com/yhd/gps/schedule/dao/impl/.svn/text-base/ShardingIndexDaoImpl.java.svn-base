package com.yhd.gps.schedule.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.dao.ShardingIndexDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.ShardingIndexVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * 分片表DAO
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午01:49:56
 */
public class ShardingIndexDaoImpl extends ScheduleBaseDao implements ShardingIndexDao {

    /**
     * 获取所有的分片信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ShardingIndexVo> queryAllShardingByTableName(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("tableName", tableName);
        return queryForList("queryAllShardingByTableName", params);
    }

    /**
     * 更新分片状态为占用
     */
    @Override
    public int updateShardingUnValid(ShardingIndexVo vo) {
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("ip", vo.getIp());
        params.put("id", vo.getId());
        try {
            BusyDbContext.switchToMasterDB();
            return update("updateShardingUnValid", params);
        } finally {
            BusyDbContext.reset();
        }
    }

    @Override
    public int updateShardingValid(ShardingIndexVo vo) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("id", vo.getId());
        try {
            BusyDbContext.switchToMasterDB();
            return update("updateShardingValid", params);
        } finally {
            BusyDbContext.reset();
        }

    }

    /**
     * 根据活动IP列表更新分片表中不活动IP的占用状态
     */
    @Override
    public int updateShardingValidByActiveIps(List<String> activeIps) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("activeIps", activeIps);
        try {
            BusyDbContext.switchToMasterDB();
            return update("updateShardingValidByActiveIps", params);
        } finally {
            BusyDbContext.reset();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShardingIndexVo> queryAllSharding() {
        return queryForList("queryAllSharding");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShardingIndexVo> queryShardingById(Long id) {
        List<ShardingIndexVo> result = Collections.emptyList();
        if(id != null && id > 0) {
            Map<String, Object> param = new HashMap<String, Object>(1, 1F);
            param.put("id", id);
            result = queryForList("queryShardingById", param);
        }
        return result;
    }

    @Override
    public Long insertSharding(ShardingIndexVo shardingIndexVo) {
        Long result = 0L;
        try {
            BusyDbContext.switchToMasterDB();
            if(shardingIndexVo != null) {
                result = insert("insertSharding", shardingIndexVo);
            }
            return result;
        } finally {
            BusyDbContext.reset();
        }
    }

    @Override
    public int batchUpdateShardingValidById(List<Long> ids) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(ids)) {
            Map<String, Object> param = new HashMap<String, Object>(1, 1F);
            param.put("ids", ids);
            try {
                BusyDbContext.switchToMasterDB();
                result = update("batchUpdateShardingValidById", param);
            } finally {
                BusyDbContext.reset();
            }
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "deprecation" })
    @Override
    public int batchInsertSharding(final List<ShardingIndexVo> shardingIndexVos) {
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(ShardingIndexVo shardingIndexVo : shardingIndexVos) {
                        sqlMapExecutor.insert("insertSharding", shardingIndexVo);
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

    @Override
    public Integer queryCrashNumByTableName(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("tableName", tableName);
        return (Integer) queryForObject("queryCrashNumByTableName", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShardingIndexVo> queryAllCrashShardingByTableName(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("tableName", tableName);
        return queryForList("queryAllCrashShardingByTableName", params);
    }

    @Override
    public int updateCrashShardingUnValid(ShardingIndexVo vo) {
        Map<String, Object> params = new HashMap<String, Object>(2, 1F);
        params.put("ip", vo.getIp());
        params.put("id", vo.getId());
        try {
            BusyDbContext.switchToMasterDB();
            return update("updateCrashShardingUnValid", params);
        } finally {
            BusyDbContext.reset();
        }
    }

    @Override
    public int deleteShardingById(Long id) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("id", id);
        try {
            BusyDbContext.switchToMasterDB();
            return delete("deleteShardingById", params);
        } finally {
            BusyDbContext.reset();
        }
    }
}