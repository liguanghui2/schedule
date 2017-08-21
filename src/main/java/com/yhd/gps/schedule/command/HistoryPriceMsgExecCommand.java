package com.yhd.gps.schedule.command;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.busyservice.dao.HistoryPriceChangeMsgDao;
import com.yhd.gps.schedule.historyprice.DateSectionPriceInfoHandler;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-3-9 下午08:11:44
 */
public class HistoryPriceMsgExecCommand extends ShardingDataExecCommand<Integer, List<Long>> {

    private HistoryPriceChangeMsgDao historyPriceChangeMsgDao;

    /**
     * 历史价格处理类
     */
    private DateSectionPriceInfoHandler dateSectionPriceInfoHandler;

    public HistoryPriceMsgExecCommand(final int shardingIndex, CountDownLatch finishSignal, final String bussinessType,
                                      final HistoryPriceChangeMsgDao historyPriceChangeMsgDao,
                                      final DateSectionPriceInfoHandler dateSectionPriceInfoHandler) {
        super(shardingIndex, finishSignal, bussinessType);
        this.historyPriceChangeMsgDao = historyPriceChangeMsgDao;
        this.dateSectionPriceInfoHandler = dateSectionPriceInfoHandler;
    }

    @Override
    public List<Long> fetchBussinessDataes(int shardingIndex) {
        // 查询从当前日期倒推1个月的数据
        Date endDate = new Date();
        Date startDate = ScheduleDateUtils.getBefore1MonthDate(endDate);
        // 自定义时间
        Date customStartDate = MiscConfigUtils.getCustomStartDate4RegionMonthLevel();
        // 如果自定义时间比倒退一个月时间还早的话,以自定义时间为准取数据(字典表key:CUSTOM_START_DATE_FOR_REGION_MONTH_LEVEL)
        if(customStartDate != null && customStartDate.compareTo(startDate) < 0) {
            startDate = customStartDate;
        }
        return historyPriceChangeMsgDao.queryUnDealPmIdsBySharding(shardingIndex, startDate, endDate);
    }

    @Override
    public ExecResult<Integer> doWork(final List<Long> pmIds) {
        if(CollectionUtils.isEmpty(pmIds)) {
            Set<Long> dataIds = Collections.emptySet();
            return new ExecResult<Integer>(0, dataIds);
        }
        Set<Long> dealedMsgIds = new HashSet<Long>();

        // 批量处理计算商品的价格
        int rows = dateSectionPriceInfoHandler.computeDateSectionPriceInfo(pmIds, shardingIndex, dealedMsgIds);

        return new ExecResult<Integer>(rows, dealedMsgIds);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        return historyPriceChangeMsgDao.batchUpdateHistoryPriceChangeMsg2Dealed(ids);
    }

}
