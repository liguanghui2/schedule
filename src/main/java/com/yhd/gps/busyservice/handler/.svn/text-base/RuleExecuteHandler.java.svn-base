package com.yhd.gps.busyservice.handler;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.GpsScheduleWarningRuleDao;
import com.yhd.gps.busyservice.dao.RuleExecuteDao;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.SpringBeanUtil;
import com.yhd.gps.schedule.enums.WarningRuleOpType;
import com.yhd.gps.schedule.vo.GpsScheduleWarningRuleVo;

/**
 * 
 * 规则执行处理器
 * @Author lipengcheng
 * @CreateTime 2017-3-3 下午03:28:37
 */
public class RuleExecuteHandler {
    private Log logger = LogFactory.getLog(RuleExecuteHandler.class);
    private int shardingIndex;
    private List<Long> ruleIds;
    private GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao;

    public RuleExecuteHandler(int shardingIndex, List<Long> ruleIds, GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao) {
        this.shardingIndex = shardingIndex;
        this.ruleIds = ruleIds;
        this.gpsScheduleWarningRuleDao = gpsScheduleWarningRuleDao;
    }

    public void exexute() {
        if(CollectionUtils.isEmpty(ruleIds) || shardingIndex < 0) {
            return;
        }
        // 根据ruleIds和shardingIndex查询规则表
        List<GpsScheduleWarningRuleVo> gpsScheduleWarningRuleVos = gpsScheduleWarningRuleDao
                .getGpsScheduleWarningRuleVo(shardingIndex, ruleIds);
        if(CollectionUtils.isEmpty(gpsScheduleWarningRuleVos)) {
            return;
        }
        for(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo : gpsScheduleWarningRuleVos) {
            // 单条规则交给核心方法处理
            processCore(gpsScheduleWarningRuleVo);
        }
    }

    /**
     * 处理规则核心方法
     * @param gpsScheduleWarningRuleVo
     */
    private void processCore(GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo) {
        // 判断规则是否有效
        if(null == gpsScheduleWarningRuleVo || !gpsScheduleWarningRuleVo.getIsValid()) {
            return;
        }
        String schema1 = gpsScheduleWarningRuleVo.getSchema1();
        String sql1 = gpsScheduleWarningRuleVo.getSql1();
        // 未配置schema和sql则直接返回
        if(StringUtils.isBlank(schema1) || StringUtils.isBlank(sql1)) {
            return;
        }
        try {
            // 获得dao，目前只支持gps库，需要查产品库可以配置
            RuleExecuteDao ruleExcuteDao = (RuleExecuteDao) SpringBeanUtil
                    .getBean(schema1 + ScheduleConstants.RULE_EXECUTE_DAO_SUFFIX);
            // 执行sql1
            int result = ruleExcuteDao.getRuleExecuteResult(sql1);

            // 判断是否报警
            if(isWarning(result, gpsScheduleWarningRuleVo.getThreshold(), gpsScheduleWarningRuleVo.getOpType())) {
                // 执行报警操作
                ProcessWarningHandler.handle(gpsScheduleWarningRuleVo);
            }
        } catch (Exception e) {
            logger.error("规则执行异常：id=" + gpsScheduleWarningRuleVo.getId(), e);
        }
    }

    /**
     * 是否报警
     * @param dbResult
     * @param threshold
     * @param opType
     * @return
     */
    private boolean isWarning(int dbResult, int threshold, int opType) {
        boolean result = false;
        WarningRuleOpType warningRuleOpType = WarningRuleOpType.getByValue(opType);
        if(null != warningRuleOpType) {
            switch(warningRuleOpType) {
            case WARNING_RULE_OP_TYPE_GREATER_THAN:
                result = (dbResult > threshold);
                break;
            case WARNING_RULE_OP_TYPE_EQUAL:
                result = (dbResult == threshold);
                break;
            case WARNING_RULE_OP_TYPE_LESS_THAN:
                result = (dbResult < threshold);
                break;
            case WARNING_RULE_OP_TYPE_GREATER_THAN_OR_EQUAL:
                result = (dbResult >= threshold);
                break;
            case WARNING_RULE_OP_TYPE_LESS_THAN_OR_EQUAL:
                result = (dbResult <= threshold);
                break;
            case WARNING_RULE_OP_TYPE_NOT_EQUAL:
                result = (dbResult != threshold);
                break;
            default:
                break;
            }
        }
        return result;
    }
}
