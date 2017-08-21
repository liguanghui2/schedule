package com.yhd.gps.schedule.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yhd.gps.schedule.common.ScheduleBaseDao;
import com.yhd.gps.schedule.dao.GoldCoinPriceChangeHistoryDao;
import com.yhd.gps.schedule.database.BusyDbContext;
import com.yhd.gps.schedule.vo.HistoryGoldCoinPriceChangeMsgVo;

@SuppressWarnings("deprecation")
public class GoldCoinPriceChangeHistoryDaoImpl extends ScheduleBaseDao implements GoldCoinPriceChangeHistoryDao {

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public Integer batchInsertGoldCoinHistoryPriceChangeMsg(final List<HistoryGoldCoinPriceChangeMsgVo> goldCoinPriceChangeMsgList) {

        try {
            BusyDbContext.switchToMasterDB();
            return (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                @Override
                public Object doInSqlMapClient(SqlMapExecutor sqlMapExecutor) throws SQLException {
                    int rows = 0;
                    sqlMapExecutor.startBatch();
                    int batch = 0;
                    for(HistoryGoldCoinPriceChangeMsgVo priceChangeMsgVo : goldCoinPriceChangeMsgList) {
                        sqlMapExecutor.insert("insertGoldCoinHistoryPriceChangeMsg", priceChangeMsgVo);
                        // 每500条批量提交一次
                        if(++batch == 500) {
                            rows += sqlMapExecutor.executeBatch();
                            sqlMapExecutor.startBatch();
                            batch = 0;
                        }
                    }
                    rows += sqlMapExecutor.executeBatch();
                    return rows;
                }
            });
        } finally {
            BusyDbContext.reset();
        }
    }
}
