package com.yhd.gps.busyservice.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.busyservice.dao.JDBookSyncLogDao;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.JDBookSyncLogVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * 
 * 京东图书同步
 * @Author lipengcheng
 * @CreateTime 2016-12-1 下午05:18:37
 */
public class JDBookSyncLogDaoImpl extends ScheduleBaseDao implements JDBookSyncLogDao {

    @Override
    public int batchInsertJDBookSyncLogVoList(final List<JDBookSyncLogVo> jdBookSyncLogVos) {
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(JDBookSyncLogVo logVo : jdBookSyncLogVos) {
                        sqlMapExecutor.insert("insertJDBookSyncLogVo", logVo);
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