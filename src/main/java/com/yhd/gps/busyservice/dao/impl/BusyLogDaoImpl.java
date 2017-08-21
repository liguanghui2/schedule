package com.yhd.gps.busyservice.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.busyservice.dao.BusyLogDao;
import com.yhd.gps.promotion.vo.GPSPromotionLogVo;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.vo.BusyProductPromRuleLogVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public class BusyLogDaoImpl extends ScheduleBaseDao implements BusyLogDao {

    @Override
    public int batchInsertProductPromRuleLogs(final List<BusyProductPromRuleLogVo> logs) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(logs)) {
            try {
                return batchInsert("insertProductPromRuleLogVo", logs);
            } catch (Exception e) {
                result = 0;
                throw new BusyException("批量插入productPromRule变更日志失败!", e.getCause());
            }
        }
        return result;
    }

    @Override
    public List<Long> batchInsertGPSPromotionLogs(final List<GPSPromotionLogVo> logs) {
        final List<Long> list = new ArrayList<Long>();
        if(CollectionUtils.isNotEmpty(logs)) {
            getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(GPSPromotionLogVo prom : logs) {
                        Long id = (Long) sqlMapExecutor.insert("bs_insertGpsPromotionLog", prom);
                        list.add(id);
                    }
                    batch++;
                    // 每500条批量提交一次
                    if(batch == 500) {
                        sqlMapExecutor.executeBatch();
                        batch = 0;
                    }
                    sqlMapExecutor.executeBatch();
                    return list;
                }
            });
        }
        return list;
    }

}