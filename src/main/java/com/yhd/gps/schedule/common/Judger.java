package com.yhd.gps.schedule.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.schedule.vo.LPCompareResultVo;

/**
 * 
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2016-8-15 下午01:24:05
 */
public final class Judger {

    public static final Log LOG = LogFactory.getLog(Judger.class);

    /**
     * 是否lp比对包含错误
     * @param resultVo
     * @return
     */
    public static boolean isLPCompareHasError(LPCompareResultVo resultVo) {
        if(null == resultVo) {
            return false;
        }

        return StringUtils.isNotEmpty(resultVo.getErrorCode());
    }
}
