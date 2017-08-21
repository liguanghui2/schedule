package com.yhd.gps.schedule.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleContext;
import com.yhd.schedule.sharding.core.ShardingDataProcessor;

/**
 * 
 * 规则执行job
 * @Author lipengcheng
 * @CreateTime 2017-2-27 下午02:54:44
 */
public class RuleExecuteJob extends GpsBaseJobExtend {
    private static final Log LOG = LogFactory.getLog(RuleExecuteJob.class);
    private static String kiraParamKey = "value";

    private ShardingDataProcessor ruleExecuteShardingDataProcessor;

    public void setRuleExecuteShardingDataProcessor(ShardingDataProcessor ruleExecuteShardingDataProcessor) {
        this.ruleExecuteShardingDataProcessor = ruleExecuteShardingDataProcessor;
    }

    @Override
    public void init() {
        jobName = "规则执行job";
        jobDesc = "规则执行job";
    }

    @Override
    public void doJobTask(JSONObject jsonObject) {
        Long start = System.currentTimeMillis();
        if(processParam(jsonObject)) {
            ruleExecuteShardingDataProcessor.process();
        }
        ScheduleContext.reset();
        LOG.debug("---###---[GPS]定时器报警规则执行定时任务执行结束:---总共耗费时间:" + (System.currentTimeMillis() - start) / 1000 + " 秒");
    }

    private boolean processParam(JSONObject jsonObject) {
        if(null == jsonObject) {
            return false;
        }
        try {
            // 取kira传入的参数
            String value = jsonObject.getString(kiraParamKey);
            String[] idStrs = value.split(",");
            // 如果无参数则不执行job
            if(idStrs.length == 0) {
                return false;
            }
            // 转换为list
            List<Long> ids = new ArrayList<Long>(idStrs.length);
            for(String idStr : idStrs) {
                ids.add(Long.valueOf(idStr));
            }
            // 设置规则id到线程上下文
            ScheduleContext.setValue(ScheduleConstants.RULE_EXECUTE_ID_LIST, ids);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}