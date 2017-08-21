package com.yhd.gps.schedule.common;

import java.util.ArrayList;
import java.util.List;

import com.yihaodian.busy.common.BusyConstants;

/**
 * 
 * 历史价格常量定义
 * @Author liuxiangrong
 * @CreateTime 2016-3-2 上午10:34:46
 */
public class ScheduleConstants {

    public static List<Long> ALL_CHANNEL_ID_LIST = new ArrayList<Long>(2);

    static {
        ALL_CHANNEL_ID_LIST.add(BusyConstants.CHANNEL_1STORE);
        ALL_CHANNEL_ID_LIST.add(BusyConstants.CHANNEL_1STORE_WIRELESS);
    }

    public static final String TRUE = "true";
    // pss客户端超时时间
    public static final long PSS_TIME_OUT = 3000;
    public static final String GROUP_NAME = "backend";

    // 消息类型 1:pm_price改价 2.价格促销改价 3:限购数量已售完 4：促销时间开始 5：促销时间截止
    public static List<Integer> MSG_TYPE_PROMOTION_LIST = new ArrayList<Integer>(4);
    static {
        MSG_TYPE_PROMOTION_LIST.add(BusyConstants.PRICE_MSG_TYPE_PRODUCT_PROM_RULE);
        MSG_TYPE_PROMOTION_LIST.add(BusyConstants.PRICE_MSG_TYPE_PRODUCT_PROM_RULE_SOLD_OUT);
        MSG_TYPE_PROMOTION_LIST.add(BusyConstants.PRICE_MSG_TYPE_PRODUCT_PROM_RULE_START);
        MSG_TYPE_PROMOTION_LIST.add(BusyConstants.PRICE_MSG_TYPE_PRODUCT_PROM_RULE_END);
    }

    // 历史价格数据已处理
    public static final int HISTORY_PRICE_CHANGE_MSG_DEALED = 1;

    // 历史价格数据未处理
    public static final int HISTORY_PRICE_CHANGE_MSG_UN_DEALED = 0;

    // 基准价及促销
    public static final int PRICE_CHANGE_MSG_TYPE_NORMAL = 1;

    // LandingPage促销
    public static final int PRICE_CHANGE_MSG_TYPE_LP = 2;

    public static final int SHARDING_INDEX_VALID = 1;

    public static final int SHARDING_INDEX_UNVALID = 0;

    // GPS_PRODUCT最后同步时间
    public static final String GPS_PRODUCT_SYNC_TIME = "GPS_PRODUCT_SYNC_TIME";

    public static final Integer DB_MAX_COUNT = 500;

    // 价格消息清理清理任务配置key
    public static final String PRICE_CHANGE_MSG_CLEAN_DELAY_DAYS = "PRICE_CHANGE_MSG_CLEAN_DELAY_DAYS";

    public static final Integer PRICE_CHANGE_MSG_CLEAN_DELAY_DAYS_DEFAULT = -3;

    // 自定义打标参照的商品价格变化的开始时间（3个月之内的开始时间）
    public static final String CUSTOM_START_DATE_FOR_REGION_MONTH_LEVEL = "CUSTOM_START_DATE_FOR_REGION_MONTH_LEVEL";
    // 自定义某段时间内最低价
    public static final String CUSTOM_DATE_FOR_MIN_PRICE = "CUSTOM_DATE_FOR_MIN_PRICE4SCHEDULE";

    // 自定义补偿丢失数据日期
    public static final String COMPENSATE_LOSE_DATA_TIME = "COMPENSATE_LOSE_DATA_TIME";

    // 自定义补偿丢失数据的商家
    public static final String COMPENSATE_LOSE_DATA_BY_MERCHANT4SCHEDULE = "COMPENSATE_LOSE_DATA_BY_MERCHANT4SCHEDULE";
    // 补偿丢失数据的定时器任务开关
    public static final String COMPENSATE_LOSE_DATA_SWITCH_BY_MERCHANT4SCHEDULE = "COMPENSATE_LOSE_DATA_SWITCH_SCHEDULE";

    // 价格促销状态
    public static final int PRODUCT_PROM_RULE_STATUS_IS_VALID = 1;
    // 价格促销重置已售数量的分片数量
    public static final String SHARDING_COUNT_TABLE_NAME_PRODUCT_PROM_RULE = "PRODUCT_PROM_RULE";
    public static final String SHARDING_COUNT_TABLE_NAME_GPS_PROMOTION = "GPS_PROMOTION";

    // LandingPage促销类型 1：分时段促销；0：普通促销
    public static final Integer LANDING_PAGE_PROMOTION_TYPE_IS_PERIOD = 1;
    public static final Integer LANDING_PAGE_PROMOTION_TYPE_NOT_PERIOD = 0;

    // LandingPage促销限购类型0：限总量；1：限每日总量；-1：不限购
    public static final Integer LANDING_PAGE_PROMOTION_LIMIT_TOTAL = 0;
    public static final Integer LANDING_PAGE_PROMOTION_LIMIT_PER_DAY = 1;
    public static final Integer LANDING_PAGE_PROMOTION_LIMIT_NONE = -1;

    /**
     * LandingPage比对错误码:
     * 
     * <pre>
     * lp-error_data        错误数据，
     * lp-error_prom_type   促销类型不匹配，
     * lp-error_limit_type  限购类型不匹配，
     * lp-error_prom_price  促销价格不匹配，
     * lp-error_limit_stock 限购数量不匹配，
     * lp-error_prom_point  促销积分不匹配，
     * lp-error_sold_num    已售数量不匹配，
     * lp-error_start_time  活动开始时间不匹配，
     * lp-error_end_time    活动截止时间不匹配。
     * </pre>
     */
    public static final String LP_COMPARE_ERROR_CODE_ERROR_DATA = "lp-error_data";
    public static final String LP_COMPARE_ERROR_CODE_PROMOTION_TYPE_NOT_MATCH = "lp-error_prom_type";
    public static final String LP_COMPARE_ERROR_CODE_LIMIT_TYPE_NOT_MATCH = "lp-error_limit_type";
    public static final String LP_COMPARE_ERROR_CODE_PROMOTION_PRICE_NOT_MATCH = "lp-error_prom_price";
    public static final String LP_COMPARE_ERROR_CODE_LIMIT_STOCK_NOT_MATCH = "lp-error_limit_stock";
    public static final String LP_COMPARE_ERROR_CODE_PROMOTION_POINT_NOT_MATCH = "lp-error_prom_point";
    public static final String LP_COMPARE_ERROR_CODE_SOLD_NUM_NOT_MATCH = "lp-error_sold_num";
    public static final String LP_COMPARE_ERROR_CODE_START_TIME_NOT_MATCH = "lp-error_start_time";
    public static final String LP_COMPARE_ERROR_CODE_END_TIME_NOT_MATCH = "lp-error_end_time";

    // lp分时段表上下文前缀
    public static final String LP_PROMOTION_PERIOD_TIME_PREFIX = "lp_period_time_";

    // lp已经比对标记
    public static final Long LP_HAS_COMPATED_FLAG = 1L;

    // 预警邮件收件人
    public static final String BS_USER_MAIL_LIST_KEY_4_SCHEDULE = "BS_USER_MAIL_LIST_KEY_4_SCHEDULE";
    // 管理员邮件收件人
    public static final String BS_ADMIN_MAIL_LIST_KEY = "BS_ADMIN_MAIL_LIST_KEY";
    // 超过多少条数据发送邮件
    public static final int LP_COMPARE_SEND_MAIL_THRESHOLD = 200;
    // 邮件发送参数阈值
    public static final int SEND_MAIL_THRESHOLD = 500;
    // 京东图书同步业务类型
    public static final String JD_BOOK_SYNC_BUSSINESSTYPE = "JD_BOOK_SYNC";

    // 定时器消费者id
    public final static String GPS_SCHEDULE_JUMPER_CONSUMER_ID = "gps_schedule_consumer";

    // 京东价格同步的Topic
    public static String JD_PM_PRICE_SYNC_TOPIC = "JD_PM_PRICE_SYNC";

    /**
     * 单次处理的最大消息数量，open-api要求10个
     */
    public static final int MAX_MSG_SIZE = 10;

    /**
     * open-api接口返回正常
     */
    public static final int OPEN_API_SUCCESS = 1;

    /**
     * 京东接口返回正常
     */
    public static final String JD_SUCCESS = "0";

    // 促销对应的渠道信息
    public static final String PRODUCT_PROM_RULE_CHANEL_MAP = "product_prom_rule_chanel_map";

    // 促销对应的区域信息
    public static final String PRODUCT_PROM_RULE_AREA_MAP = "product_prom_rule_area_map";

    // 京东日百同步商家
    public static final String JD_DAILY_GOODS_SYNC_MERCHANTID_LIST = "JD_DAILY_GOODS_SYNC_MERCHANTID_LIST";
    // 京东图书同步商家
    public static final String JD_BOOK_SYNC_MERCHANTID_LIST = "JD_BOOK_SYNC_MERCHANTID_LIST";
    // 规则执行上下文key-规则ids
    public static final String RULE_EXECUTE_ID_LIST = "RULE_EXECUTE_ID_LIST";
    // 规则执行的dao后缀，用于动态获得执行sql的bean
    public static final String RULE_EXECUTE_DAO_SUFFIX = "RuleExecuteDao";
    // 定时器报警规则1:执行sql
    public static final int GPS_SCHEDULE_WARNIN_RULE_TYPE_EXECUTE_SQL = 1;
    // 报警规则切片表tableName
    public static final String SHARDING_NAME_RULE_EXECUTE = "RULE_EXECUTE";

    // ========================档期状态=============================
    public static final Integer PRICE_SCHEDULE_STATUS_NEW = 1; // 档期状态：新建
    public static final Integer PRICE_SCHEDULE_STATUS_AI_RULE_SET = 2;// 档期状态：AI规则已设置
    public static final Integer PRICE_SCHEDULE_STATUS_MESSAGE_SENDED = 3; // 档期状态:已发送档期消息
    public static final Integer PRICE_SCHEDULE_STATUS_MODE_PRICE_COMPUTED = 4;// 档期状态:众数价计算完成
    public static final Integer PRICE_SCHEDULE_STATUS_PRICE_COLLECTED = 5;// 档期状态:价格已汇总
    public static final Integer PRICE_SCHEDULE_STATUS_PRICE_EFFECT = 6;// 档期状态:生效中
    public static final Integer PRICE_SCHEDULE_STATUS_STOP = 7; // 档期状态:已停止
    // ========================建议价类型=============================

    public static final Integer MODE_PRICE = 5; // 180天众数价
    public static final Integer AVG_PRICE = 6; // 移动平均价加权

    // =========================建议价来源============================
    // 建议价来源(1:PIS 2:AI 3:GPS)
    public static final Integer PIS = 1; // 1:PIS
    public static final Integer AI = 2; // 2:AI
    public static final Integer GPS = 3; // 3:GPS

    public static final Integer MAX_NUMBER = 100;// 查询众数价及加权移动平均价接口支持最大入参数量
    public static final Integer NO_DATA = 0; // 远程接口没有数据返回
    public static final Integer STATUS_SUCCESS = 1;// 查询状态正常
    public static final Integer STATUS_FAIL = 0;// 查询失败
    public static final Long PAGE_SIZE = 500L;// 查询定价接口每页查询最大数量
    public static final Long CURRENT_PAGE = 1L;// 当前从第几页开始查

    // PIS获取外站价格的站点ID siteId：猫超南京：1162，苏宁南京：1099
    public static final Integer TMALL_NANJING_SITE = 1162;
    public static final Integer SUNING_NANJING_SITE = 1099;

    // =========================商品定价表商品状态============================
    public static final Integer PRICE_STATUS_CHECK_PENDING = 0;
    public static final Integer PRICE_STATUS_CHECK_PASS = 1;
    public static final Integer PRICE_STATUS_CHECK_NOT_PASS = 2; // 审核不通过
    public static final Integer PRICE_STATUS_GROSS_PROFIT_DOING = 3;
    public static final Integer PRICE_STATUS_ACTIVE = 4;
    public static final Integer PRICE_STATUS_NOT_ACTIVE = 5;

    // =========================城市精选创建促销返回状态码============================
    /** 数据校验错误 */
    public final static Integer RESULT_FAIL_DATA_ERROR = 0;
    /** 出现异常错误 */
    public final static Integer RESULT_FAIL_EXCEPTION = 1;
    /** 防呆失败 */
    public final static Integer RESULT_FAIL_DAD_DEBTS = 2;
    /** 促销保存成功并生效 */
    public final static Integer RESULT_SUCCESS_VALID = 3;
    /** 发生负毛利 */
    public final static Integer RESULT_IN_OPERATING = 4;
    /** 活动价为空 */
    public final static Integer RESULT_FAIL_NO_ACTIVE_PRICE = 5;
    // ==========================产品类型=======================================

    public final static Integer SERIES_PRODUCTS = 1; // 系列虚品
    public final static Integer COMBINE_PRODUCT = 3; // 组合品
    public final static Integer CITY_PICK_PRODUCT = 99; // 城市精选预定的商品

    // ============================城市精选导出自动定价审核常量====================
    public static final int MAX_ITEM_EVERY_EXCEL = 6000;

    // 档期商品定价导出列序号
    public final static int SCHEDULE_CODE = 0; // 定价档期编码
    public final static int SCHEDULE_NAME = 1; // 定价档期名称
    public final static int SCHEDULE_TIME = 2; // 定价档期时间段
    public final static int MERCHANT = 3; // 城市精选商家
    public final static int PRODUCT_CODE = 4; // 产品编码
    public final static int PRODUCT_NAME = 5; // 产品名称
    public final static int PRODUCT_CATEGORY = 6; // 产品类别
    public final static int SUGGES_PRICE = 7; // 建议价
    public final static int ACTIVE_PRICE = 8; // 促销价
    public final static int CURRENT_PROM_ID = 9; // 当前生效促销Id
    public final static int PROM_NAME = 10; // 促销名称

    // ==========================城市精选邮件类型=================================
    public static final int PRICE_SCHEDULE_CONFIG_EMAIL_TYPE = 1;// 定价档期配置邮件类型
    public static final int SCHEDULE_TO_AUDIT_EMAIL_TYPE = 2;// 档期待审核邮件类型
    public static final int AUTO_PRICE_CONFIRM_EMAIL_TYPE = 3;// 自动定价审核邮件类型

    // ==========================城市精选产品来源=================================
    public static final int AUTO_CONFIRM_PRICE_SCHEDULE_SOURCE_TYPE = 1;

    /** 城市精选定价策略 */
    public static final Integer CITY_PICK_CONFIRMED_PRICE = 22;

    /** 城市精选商品数据来源 */
    public static final Integer PRODUCT_SOURCE_TYPE_AUTO = 1; // 自动定价档期
    public static final Integer PRODUCT_SOURCE_TYPE_SELECT = 2; // 选品档期导入
    public static final Integer PRODUCT_SOURCE_TYPE_EXCEL = 3; // excel导入

    public static final Integer SCHEDULE_STATUS_CREATED = 1; // 新建定价档期
    public static final Integer SCHEDULE_STATUS_AI_SETTED = 2; // 手工AI配置完成
    public static final Integer SCHEDULE_STATUS_MESSAGE_SENDED = 3; // 已发送档期消息
    public static final Integer SCHEDULE_STATUS_PRICE_COMPUTED = 4; // 众数价计算完成
    public static final Integer SCHEDULE_STATUS_PRICE_COLLECTED = 5; // 价格已汇总
    public static final Integer SCHEDULE_STATUS_ACTIVE = 6; // 生效中
    public static final Integer SCHEDULE_STATUS_STOP = 7; // 停用

    // 城市精选商品建议价表建议价类型
    public static final int PRICE_TYPE_TMALL_PAGE_PRICE = 1; // 天猫页面价
    public static final int PRICE_TYPE_TMALL_FLOOR_PRICE = 2; // 天猫底价
    public static final int PRICE_TYPE_SUNING_PAGE_PRICE = 3; // 苏宁页面价
    public static final int PRICE_TYPE_SUNING_FLOOR_PRICE = 4; // 苏宁底价
    public static final int PRICE_TYPE_180_MODE_PRICE = 5; // 180天众数价
    public static final int PRICE_TYPE_MOBILE_AVG_PRICE = 6; // 移动平均价加权
    public static final int PRICE_TYPE_AI_MODEL_PRICE = 7; // AI模型分析

    public static final Integer MAX_SIZE = 100;

    // 城市精选敏感品
    public static final Integer SENSITIVE_TYPE_CITY_PICK = 1;
    //接口查询默认大小
    public static final int DEFAULT_QUERY_MAX_SIZE = 200;
    
    /** 售价看板数据处理业务类型 **/
    public static final String PRICE_BOARD_DAILY = "priceBoardDaily";//日售价看板
    public static final String PRICE_BOARD_WEEKLY = "priceBoardWeekly";//周售价看板
    public static final String PRICE_BOARD_MONTHLY = "priceBoardMonthly";//月售价看板
    public static final String PRICE_BOARD_QUARTERLY = "priceBoardQuarterly";//季度(90天)售价看板
    public static final String PRICE_BOARD_SEMESTERTLY = "priceBoardSemesterly";//半年(180天)售价看板
    
    /** 月售价看板表中统计类型*/
    public static final int PRICE_BOARD_STATISTICS_TYPE_MONTH = 1;//统计1个月数据
    public static final int PRICE_BOARD_STATISTICS_TYPE_QUARTERLY = 3;//统计3个月数据
    public static final int PRICE_BOARD_STATISTICS_TYPE_SEMESTERTLY = 6;//统计6个月数据

    /**
     * 获取字典表中配置的城市精选商家对应的关联商家的key
     * @param merchantId
     * @return
     */
    public static String buildCityPickRelevanceMerchantKey(Long merchantId) {
        StringBuilder str = new StringBuilder("CITY_PICK_RELEVANCE_MERCHANT_KEY_PREFIX_");
        if(null == merchantId) {
            str.append("null");
        } else {
            str.append(merchantId);
        }
        return str.toString();
    }
    
    /**
     * 生产环境
     */
    public final static String IS_PRODUCTION = "production";
    
    /**
     * 指定的切片ID（非生产环境测试用）
     */
    public static final String SPECIFIED_SHARDING_INDEX = "SPECIFIED_SHARDING_INDEX"; 
    
    /**
     * 上一次清理pm_price表数据的时间
     */
    public static final String CLEAN_PM_PRICE_DATA_LAST_TIME = "CLEAN_PM_PRICE_DATA_LAST_TIME";
}
