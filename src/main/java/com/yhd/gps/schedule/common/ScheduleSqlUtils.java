package com.yhd.gps.schedule.common;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * sql处理工具类
 * @Author lipengcheng
 * @CreateTime 2017-3-3 上午10:15:56
 */
public class ScheduleSqlUtils {

    /**
     * 验证字符串防止sql注入, 采用正则表达式将包含有 单引号(')，分号(;) 和 注释符号(--)的语句给替换掉来防止SQL注入
     * @param sql 要过滤的值
     * @return
     */
    public static String validateSql(String sql) {
        if(StringUtils.isEmpty(sql)) {
            return null;
        }
        return sql.replaceAll(".*([';]+|(--)+).*", " ");
    }
}
