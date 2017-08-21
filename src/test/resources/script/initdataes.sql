INSERT INTO BS_MISC_CONFIG (ID, ITEM_KEY, ITEM_VALUE, ENABLED, IS_DELETED) VALUES
(1, 'BS_SH_TO_BJ_SYNC_ENABLE_KEY', 'true', 1, 0),
(2, 'BS_STRANGE_PRICE_JOB_RULE_KEY', '1,300,0.3,1000,100', 1, 0),
(3, 'BS_STRANGE_PRICE_JOB_TO_USER_MAIL_KEY', 'fwl@yhd.cn', 1, 0),
(4, 'BS_ADMIN_WHITE_IP_LIST_KEY', '127.0.0.1', 1, 0),
(5, 'BS_OFF_SELL_RULE_KEY', '1000,0.45,0.30,650', 1, 0),
(6, 'BS_IDC_BJ_IP_LIST_KEY', '192.168.16.151', 1, 0),
(8, 'BS_PRICE_JUMPER_NOTICE_ENABLE_KEY', 'true', 1, 0),
(9, 'BS_ASYNC_FLUSH_STOCK_CACHE_ENABLE_KEY', 'false', 1, 0),
(11, 'BS_GROUPON_WORK_MODE_KEY', '11', 1, 0),
(13, 'BS_INVOKE_SLAVE_GSS_ENABLE_KEY', 'true', 1, 0),
(18, 'BS_SH_TO_BJ_DELAY_MILLI_SECONDS_KEY', '1000', 1, 0),
(19, 'BS_COMBINE_PRICE_NEW_COMPUTE_ENABLE_KEY', 'true', 1, 0),
(20, 'BS_ADMIN_MAIL_LIST_KEY', 'yaohaiqing@yhd.cn', 1, 0),
(21, 'BS_USER_MAIL_LIST_KEY', 'yaohaiqing@yhd.cn', 1, 0),
(22, 'BS_ENABLE_MONITOR_METHOD_LIST_KEY', 'ALL', 1, 0),
(23, 'BS_UPDATE_PARENT_SOLD_NUM_ENABLE_KEY', 'true', 1, 0),
(24, 'BS_PM_AREA_STOCK_WORK_MODE_KEY', '1', 1, 0),
(25, 'BS_ENABLE_SERIES_CUSTOM_DEFAULT_KEY', 'true', 1, 0),
(26, 'BS_ACTIVITY_STOCK_ENABLE_KEY', 'true', 1, 0),
(28, 'BS_LEVEL_PRICE_ENABLE_KEY', 'true', 1, 0),
(29, 'BS_GPS_HISTORY_PRICE_CHANGE_MSG_SHARDING_COUNT', '8', 1, 0);

INSERT INTO BS_PRICE_RULE_TYPE (ID, RULE_TYPE_CODE, RULE_TYPE_NAME, RULE_TYPE_WEIGHT) VALUES
(1, 0, '普通价格促销', 0),
(2, 1, '清仓价格促销', 30),
(3, 2, '团购价格促销', 40),
(4, 3, '早夜市价格促销', 20),
(5, 4, '店中店运营平台价格促销', 10);

INSERT INTO sharding_index (id, table_name, sharding_index,is_valid,ip,update_time) VALUES
(0, 'history_price_change_msg', 0,0,'192.168.30.3',now()),
(1, 'history_price_change_msg', 1,0,'192.168.30.2',now()),
(2, 'history_price_change_msg', 2,0,'192.168.30.1',now()),
(3, 'history_price_change_msg', 3,0,'192.168.30.4',now()),
(4, 'history_price_change_msg', 4,1,'192.168.30.1',now()),
(5, 'history_price_change_msg', 5,1,'192.168.30.2',now()),
(6, 'history_price_change_msg', 6,1,'192.168.30.4',now()),
(7, 'history_price_change_msg', 7,1,'192.168.30.5',now()),
(8, 'PM_PRICE_CHANGE_MSG', 0,1,'192.168.30.3',now()),
(9, 'PM_PRICE_CHANGE_MSG', 1,1,'192.168.30.2',now()),
(10, 'PM_PRICE_CHANGE_MSG', 2,1,'192.168.30.1',now()),
(11, 'PM_PRICE_CHANGE_MSG', 3,1,'192.168.30.4',now()),
(12, 'PM_PRICE_CHANGE_MSG', 4,1,'192.168.30.1',now()),
(13, 'PM_PRICE_CHANGE_MSG', 5,1,'192.168.30.2',now()),
(14, 'PM_PRICE_CHANGE_MSG', 6,1,'192.168.30.4',now()),
(15, 'PM_PRICE_CHANGE_MSG', 7,1,'192.168.30.5',now()),
(16, 'PROMOTION_PRICE_CHANGE_MSG', 0,1,'192.168.30.3',now()),
(17, 'PROMOTION_PRICE_CHANGE_MSG', 1,1,'192.168.30.2',now()),
(18, 'PROMOTION_PRICE_CHANGE_MSG', 2,1,'192.168.30.1',now()),
(19, 'PROMOTION_PRICE_CHANGE_MSG', 3,1,'192.168.30.4',now()),
(20, 'PROMOTION_PRICE_CHANGE_MSG', 4,1,'192.168.30.1',now()),
(21, 'PROMOTION_PRICE_CHANGE_MSG', 5,1,'192.168.30.2',now()),
(22, 'PROMOTION_PRICE_CHANGE_MSG', 6,1,'192.168.30.4',now()),
(23, 'PROMOTION_PRICE_CHANGE_MSG', 7,1,'192.168.30.5',now());