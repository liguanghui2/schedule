package com.yhd.gps.schedule.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.busyservice.service.PromRuleSoldNumResetService;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.gps.schedule.dao.DataProcessScannerDao;
import com.yhd.gps.schedule.vo.DataProcessScannerVo;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataPageExecCommand;
import com.yihaodian.busy.common.BusyConstants;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-11 下午04:22:41
 */
public class PromRuleSoldNumResetCommand extends ShardingDataPageExecCommand<Integer, List<DataProcessScannerVo>> {

    private DataProcessScannerDao dataProcessScannerDao;
    private PromRuleSoldNumResetService promRuleSoldNumResetService;

    public PromRuleSoldNumResetCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType,
                                       Integer pageSize, DataProcessScannerDao dataProcessScannerDao,
                                       PromRuleSoldNumResetService promRuleSoldNumResetService) {
        super(shardingIndex, finishSignal, bussinessType, pageSize);
        this.dataProcessScannerDao = dataProcessScannerDao;
        this.promRuleSoldNumResetService = promRuleSoldNumResetService;
    }

    @Override
    public List<DataProcessScannerVo> fetchBussinessDataes(int shardingIndex, Integer pageSize, Integer currentPage) {
        return dataProcessScannerDao.getDataProcessScanner(
                BusyConstants.DATA_PROCESS_SCANNER_BUSINESS_TYPE_PROM_RULE_RESET_SOLD_NUM, shardingIndex, pageSize);
    }

    @Override
    public ExecResult<List<Integer>> doWork(List<DataProcessScannerVo> dataProcessScannerVos) {
        if(CollectionUtils.isEmpty(dataProcessScannerVos)) {
            Set<Long> dataIds = Collections.emptySet();
            List<Integer> result = Collections.emptyList();
            return new ExecResult<List<Integer>>(result, dataIds);
        }

        Set<Long> scannerIds = new HashSet<Long>(dataProcessScannerVos.size(), 1F);

        Integer result = promRuleSoldNumResetService.resetPromRuleSoldNum(dataProcessScannerVos, scannerIds);
        return new ExecResult<List<Integer>>(Arrays.asList(result), scannerIds);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        // 下次处理时间:次日凌晨00:00:00
        Date nextProcessTime = ScheduleDateUtils.addDays(ScheduleDateUtils.parseDate(new Date()), 1);
        return dataProcessScannerDao.batchUpdateDataProcessScannerNextProcessTimeByIds(ids, nextProcessTime);
    }

}