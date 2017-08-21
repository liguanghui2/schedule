package com.yhd.gps.schedule.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.dao.PriceBoardDataRecordDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.PriceBoardDailyDataVo;
import com.yhd.gps.schedule.vo.PriceBoardMonthlyDataVo;
import com.yhd.gps.schedule.vo.PriceBoardWeeklyDataVo;

/**
 * @author sunfei
 * @CreateTime 2017-6-30 下午02:36:36
 */
@SuppressWarnings("deprecation")
public class PriceBoardDataRecordDaoImpl extends ScheduleBaseDao implements PriceBoardDataRecordDao {
    private Logger logger = Logger.getLogger(PriceBoardDataRecordDaoImpl.class);

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public int batchInsertPriceBoardDailyData(final List<PriceBoardDailyDataVo> priceBoardDailyDataVos) {
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(PriceBoardDailyDataVo priceBoardDailyDataVo : priceBoardDailyDataVos) {
                        sqlMapExecutor.insert("insertPriceBoardDailyData", priceBoardDailyDataVo);
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
            logger.error("PriceBoardDataRecordDao执行insertPriceBoardDailyData发生异常", e);
            result = 0;
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public int batchInsertPriceBoardWeeklyData(final List<PriceBoardWeeklyDataVo> priceBoardWeeklyDataVos) {
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(PriceBoardWeeklyDataVo priceBoardWeeklyDataVo : priceBoardWeeklyDataVos) {
                        sqlMapExecutor.insert("insertPriceBoardWeeklyData", priceBoardWeeklyDataVo);
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
            logger.error("PriceBoardDataRecordDao执行insertPriceBoardWeeklyData发生异常", e);
            result = 0;
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public int batchInsertPriceBoardMonthlyData(final List<PriceBoardMonthlyDataVo> priceBoardMonthlyDataVos) {
        int result = 1;
        try {
            BusyDbContext.switchToMasterDB();
            this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(PriceBoardMonthlyDataVo priceBoardMonthlyDataVo : priceBoardMonthlyDataVos) {
                        sqlMapExecutor.insert("insertPriceBoardMonthlyData", priceBoardMonthlyDataVo);
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
            logger.error("PriceBoardDataRecordDao执行insertPriceBoardMonthlyData发生异常", e);
            result = 0;
        } finally {
            BusyDbContext.reset();
        }
        return result;
    }

    @Override
    public int deleteWeekPriceBoardByWeekNum(Integer weekNum) {
        Map<String, Object> params = new HashMap<String, Object>(1, 1F);
        params.put("weekNum", weekNum);
        try {
            BusyDbContext.switchToMasterDB();
            return delete("deleteWeekPriceBoardByWeekNum", params);
        } finally {
            BusyDbContext.reset();
        }
    }

}
