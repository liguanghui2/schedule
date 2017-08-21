DROP TABLE BS_PRICE_CHANGE_LOG IF EXISTS;
CREATE TABLE BS_PRICE_CHANGE_LOG (
  ID                         BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CREATE_TIME                DATE DEFAULT NULL,
  OP_TYPE                    VARCHAR(10) DEFAULT NULL,
  PRICE_ID                   BIGINT DEFAULT NULL,
  PRODUCT_ID                 BIGINT DEFAULT NULL,
  MERCHANT_ID                BIGINT DEFAULT NULL,
  IS_PROMOTE                 INTEGER DEFAULT NULL,
  PRODUCT_PROM_NON_MEM_PRICE DECIMAL(11, 2),
  PRODUCT_NON_MEMBER_PRICE   DECIMAL(11, 2),
  IN_PRICE                   DECIMAL(11, 2) DEFAULT NULL,
  PRODUCT_PROM_START_DATE    DATE,
  PRODUCT_PROM_END_DATE      DATE,
  EARNEST                    DECIMAL(11, 2) DEFAULT NULL,
  SPECIAL_PRICE_LIMIT_NUMBER INTEGER,
  USER_PRICE_LIMIT_NUMBER    INTEGER,
  CAN_VIP_DISCOUNT           INTEGER,
  PRICE_UPDATE_TIME          DATE,
  CLIENT_POOL_NAME           VARCHAR(200),
  CLIENT_VERSION             VARCHAR(20),
  CLIENT_IP                  VARCHAR(50),
  SERVER_IP                  VARCHAR(30),
  STATUS                     INTEGER,
  PM_INFO_ID                 BIGINT DEFAULT NULL,
  AVG_PRICE                  DECIMAL(11, 2),
  SHARE_COST                 DECIMAL(11, 2),
  PRICE_CREATE_TIME          DATETIME DEFAULT NULL,
  MINIMUM_SELLING_PRICE      DECIMAL(11, 2),
  HAS_EXT_PRICE_TYPE		 INTEGER,
  POST_TAX_RATE              DECIMAL(9,7) default 0,
  IS_BIND_PACKAGE            INTEGER,
  PER_PACK_NUM               INTEGER,
  SUB_PM_INFO_UNIT           VARCHAR(10),
  IS_LOCK_PRICE              INTEGER,
  COST_PRICE                 DECIMAL(13, 2),
  PRIMARY KEY (ID)
);

DROP TABLE BS_PERF_STATS_LOG IF EXISTS;
CREATE TABLE BS_PERF_STATS_LOG (
  ID           BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  LOG_DATE     VARCHAR(20),
  TAG_NAME     VARCHAR(100),
  AVG_TIME     DECIMAL(11, 2),
  MIN_TIME     DECIMAL(11, 2),
  MAX_TIME     DECIMAL(11, 2),
  STD_DEV_TIME DECIMAL(11, 2),
  COUNT_NUM    INTEGER,
  SERVER_IP    VARCHAR(30),
  CREATE_TIME  DATE,
  PRIMARY KEY (ID)
);

DROP TABLE PM_LEVEL_PRICE_CHANGE_LOG IF EXISTS;
CREATE TABLE PM_LEVEL_PRICE_CHANGE_LOG (
  id                      BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CREATE_TIME             DATE DEFAULT NULL,
  op_type                 VARCHAR(10) DEFAULT NULL,
  level_price_id          BIGINT DEFAULT NULL,
  pm_info_id              BIGINT DEFAULT NULL,
  product_id              BIGINT DEFAULT NULL,
  merchant_id             BIGINT DEFAULT NULL,
  level_type_id           BIGINT DEFAULT NULL,
  level_type              INTEGER DEFAULT NULL,
  level1_value            DECIMAL(13, 2) DEFAULT NULL,
  level2_value            DECIMAL(13, 2) DEFAULT NULL,
  level3_value            DECIMAL(13, 2) DEFAULT NULL,
  level4_value            DECIMAL(13, 2) DEFAULT NULL,
  level5_value            DECIMAL(13, 2) DEFAULT NULL,
  level6_value            DECIMAL(13, 2) DEFAULT NULL,
  level_value_type        INTEGER DEFAULT NULL,
  start_time              DATETIME DEFAULT NULL,
  end_time                DATETIME DEFAULT NULL,
  parent_price_id         BIGINT DEFAULT NULL,
  parent_price_id_type    INTEGER DEFAULT NULL,
  channel_id              BIGINT DEFAULT NULL,
  mutex_other             INTEGER DEFAULT NULL,
  enabled                 INTEGER DEFAULT NULL,
  level_price_create_time DATETIME DEFAULT NULL,
  create_operator_id      BIGINT DEFAULT NULL,
  create_operator_name    VARCHAR(50) DEFAULT NULL,
  update_operator_id      BIGINT DEFAULT NULL,
  update_operator_name    VARCHAR(50) DEFAULT NULL,
  level_price_update_time DATETIME DEFAULT NULL,
  client_pool_name        VARCHAR(200),
  client_version          VARCHAR(20),
  client_ip               VARCHAR(50),
  server_ip               VARCHAR(30),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS PM_BINDING_PRICE_CHANGE_LOG;
CREATE TABLE PM_BINDING_PRICE_CHANGE_LOG(
     id          BIGINT GENERATED BY DEFAULT AS IDENTITY (
     START WITH 1 ) NOT NULL,
     create_time          datetime,
     OP_TYPE              VARCHAR(10),
     price_id             bigint,
     main_pm_info_id      bigint,
     main_product_id      bigint,
     main_merchant_id     bigint,
     sub_pm_info_id       bigint,
     sub_product_id       bigint,
     sub_merchant_id      bigint,
     sub_binding_num      int,
     sub_binding_value    DECIMAL(13, 2) DEFAULT NULL,
     sub_binding_value_type int,
     binding_type         int,
     series_product_id    bigint,
     ref_main_id          bigint,
     ref_sub_id           bigint,
     channel_id           bigint,
     start_time           datetime,
     end_time             datetime,
     enabled              int,
     price_create_time    datetime,
     price_update_time    datetime,
     create_operator_id   bigint,
     create_operator_name varchar(50),
     update_operator_id   bigint,
     update_operator_name varchar(50),
     client_pool_name     varchar(200),
     client_version       varchar(20),
     client_ip            varchar(50),
     server_ip            varchar(30),
     primary key (id)
  );
  
  CREATE TABLE pm_area_price_log (
   id bigint GENERATED BY DEFAULT AS IDENTITY (
     START WITH 1 ) NOT NULL,
   create_time datetime,
   op_type varchar(10),
   area_price_id bigint,
   pm_info_id bigint,
   province_id bigint,
   city_id bigint,
   county_id bigint,
   area_id bigint,
   cover_level int,
   market_price decimal(11,2),
   base_price decimal(11,2),
   ref_price_id bigint,
   ref_price_id_type bigint,
   start_time datetime,
   end_time datetime,
   enabled int,
   channel_id bigint,
   area_price_create_time datetime,
   area_price_update_time datetime,
   create_operator_id bigint,
   create_operator_name varchar(50),
   update_operator_id bigint,
   update_operator_name varchar(50),
   client_pool_name varchar(200),
   client_version varchar(20),
   client_ip varchar(50),
   server_ip varchar(30),
   PRIMARY KEY (id)
);

DROP TABLE BS_PRICE_RULE_CHANGE_LOG IF EXISTS;
CREATE TABLE BS_PRICE_RULE_CHANGE_LOG (
  ID                         BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CREATE_TIME                DATETIME,
  OP_TYPE                    VARCHAR(10),
  RULE_ID                    BIGINT DEFAULT NULL,
  PRODUCT_ID                 BIGINT DEFAULT NULL,
  MERCHANT_ID                BIGINT DEFAULT NULL,
  IS_PROMOTION               INTEGER,
  PRODUCT_PROM_NON_MEM_PRICE DECIMAL(11, 2),
  SPECIAL_PRICE_LIMIT_NUMBER INTEGER,
  USER_PRICE_LIMIT_NUMBER    INTEGER,
  SOLD_NUM                   INTEGER,
  PAY_NUM                   INTEGER,
  PRODUCT_PROM_START_DATE    DATETIME DEFAULT NULL,
  PRODUCT_PROM_END_DATE      DATETIME DEFAULT NULL,
  BACK_OPERATOR_ID           BIGINT DEFAULT NULL,
  PROM_NAME                  VARCHAR(200),
  IS_VISUAL_SERIAL           INTEGER,
  PARENT_RULE_ID             BIGINT DEFAULT NULL,
  RULE_TYPE                  INTEGER,
  DISCOUNT                   DECIMAL(11, 2),
  RESTRICT_TYPE              INTEGER,
  RULE_UPDATE_TIME           DATE,
  RULE_CREATE_TIME           DATE,
  CLIENT_POOL_NAME           VARCHAR(200),
  CLIENT_VERSION             VARCHAR(20),
  CLIENT_IP                  VARCHAR(50),
  SERVER_IP                  VARCHAR(30),
  STATUS                     INTEGER,
  PM_INFO_ID                 BIGINT DEFAULT NULL,
  AVG_PRICE                  DECIMAL(11, 2),
  MUTEX_PROMOTION            INTEGER,
  LOCk_STATUS                INTEGER,
  RULE_STATUS                INTEGER,
  MIN_COUNT                  INTEGER,
  CHANNEL_ID				 BIGINT DEFAULT 1,
  PRICE_LOCK_TYPE            INTEGER,
  PRICE_CHANGE_REMIND        INTEGER,
  SHOPPING_BAG_SOLD_NUMBER	 BIGINT,
  PRIMARY KEY (ID)
);

CREATE TABLE gps_promotion_sold_log (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (
  	START WITH 1 ) NOT NULL,
   gps_promtion_id BIGINT,
   promotion_id BIGINT,
   pm_info_id BIGINT,
   sold_num BIGINT,
   total_sold_num BIGINT,
   old_sold_date datetime,
   cur_sold_date datetime,
   SO_CREATE_TIME datetime,
   limit_type int,
   promotion_type int,
   CREATE_TIME datetime,
   CLIENT_POOL_NAME varchar(200),
   CLIENT_VERSION varchar(20),
   CLIENT_IP varchar(50),
   SERVER_IP varchar(30),
   PRIMARY KEY (id)
 );

DROP TABLE VD_CPS_PM_COMMISSION_SNAP_LOG IF EXISTS;
CREATE TABLE VD_CPS_PM_COMMISSION_SNAP_LOG (
  ID  BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CREATE_TIME DATETIME  DEFAULT CURRENT_TIMESTAMP,
  OP_TYPE VARCHAR(10), 
  CLIENT_POOL_NAME VARCHAR(200),
  CLIENT_VERSION VARCHAR(20),
  CLIENT_IP VARCHAR(50),
  SERVER_IP VARCHAR(30),
  VD_CPS_PM_COMMISSION_SNAP_ID BIGINT,
  PM_INFO_ID BIGINT,
  COMMISSION  DECIMAL(11, 2),
  VERSION_ID BIGINT,
  COMMISSION_UPDATE_TIME DATETIME,
  COMMISSION_CREATE_TIME DATETIME, 
  PRIMARY KEY (ID)
);

DROP TABLE VD_CPS_MERC_CATEGORY_COMMISSION_SNAP_LOG IF EXISTS;
CREATE TABLE VD_CPS_MERC_CATEGORY_COMMISSION_SNAP_LOG (
  ID  BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP,
  OP_TYPE VARCHAR(10), 
  CLIENT_POOL_NAME VARCHAR(200),
  CLIENT_VERSION VARCHAR(20),
  CLIENT_IP VARCHAR(50),
  SERVER_IP VARCHAR(30),
  VD_CPS_MERC_CATEGORY_COMMISSION_SNAP_ID BIGINT,
  MERCHANT_ID BIGINT,
  CATEGORY_ID BIGINT,
  COMMISSION  DECIMAL(11, 2),
  VERSION_ID BIGINT,
  COMMISSION_UPDATE_TIME DATETIME,
  COMMISSION_CREATE_TIME DATETIME, 
  PRIMARY KEY (ID)
);

create table VD_PM_PRICE_RULE_LOG(
   ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) NOT NULL,
   CREATE_TIME DATE DEFAULT NULL,
   OP_TYPE VARCHAR(10) DEFAULT NULL,
   CLIENT_POOL_NAME           VARCHAR(200),
   CLIENT_VERSION             VARCHAR(20),
   CLIENT_IP                  VARCHAR(50),
   SERVER_IP                  VARCHAR(30),
   VD_PRICE_RULE_ID BIGINT,
   VD_PM_INFO_ID bigint,
   PM_INFO_ID bigint,
   RULE_TYPE smallint,
   CHANGE_JUDGE varchar(200),
   CHANGE_METHOD varchar(200),
   VD_PRICE_RULE_UPDATE_TIME datetime,
   VD_PRICE_RULE_CREATE_TIME datetime,
   VD_PRICE_RULE_UPDATE_USER_ID bigint,
   VD_PRICE_RULE_CREATE_USER_ID bigint,
   PRIMARY KEY (ID)
);

create table VD_PM_IN_PRICE_LOG(
   ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 ) NOT NULL,
   CREATE_TIME  DATE DEFAULT NULL,
   OP_TYPE  VARCHAR(10) DEFAULT NULL,
   CLIENT_POOL_NAME  varchar(200),
   CLIENT_VERSION  varchar(20),
   CLIENT_IP  varchar(50),
   SERVER_IP  varchar(30),
   IN_PRICE_ID bigint,
   PM_INFO_ID bigint,
   IN_PRICE decimal(11,2),
   PRODUCT_ID bigint,
   IN_PRICE_UPDATE_TIME datetime,
   IN_PRICE_CREATE_TIME datetime,
   IN_PRICE_UPDATE_OPERATOR_ID bigint,
   IN_PRICE_CREATE_OPERATOR_ID bigint,
   primary key(ID)
);

CREATE TABLE VD_CPS_CATEGORY_COMMISSION_LOG(
	ID  bigint GENERATED BY DEFAULT AS IDENTITY (
	        START WITH 1 ) NOT NULL,
	CREATE_TIME  timestamp,
	OP_TYPE  varchar(10),
	CLIENT_POOL_NAME  varchar(200),
	CLIENT_VERSION  varchar(20),
	CLIENT_IP  varchar(50),
	SERVER_IP  varchar(30),
	VD_CPS_CATEGORY_COMMISSION_ID bigint,
	CATEGORY_ID bigint,
	PREV_COMMISSION decimal(11,2),
	CURR_COMMISSION decimal(11,2),
	PREV_YHD_COMMISSION decimal(11,2),
	CURR_YHD_COMMISSION decimal(11,2),
	VERSION_ID bigint,
	COMMISSION_UPDATE_TIME datetime,
	COMMISSION_CREATE_TIME datetime,
);

DROP TABLE BS_LOCK_PRICE_CHANGE_LOG IF EXISTS;
CREATE TABLE BS_LOCK_PRICE_CHANGE_LOG (
  ID                         BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CREATE_TIME                DATE DEFAULT NULL,
  OP_TYPE                    VARCHAR(10) DEFAULT NULL,
  LOCK_PRICE_ID                   BIGINT DEFAULT NULL,
  PRODUCT_ID                 BIGINT DEFAULT NULL,
  MERCHANT_ID                BIGINT DEFAULT NULL,
  PM_INFO_ID                 BIGINT DEFAULT NULL,
  LOCK_PRICE_START_DATE      DATE,
  LOCK_PRICE_END_DATE		 DATE,
  LOCK_PRICE                 decimal(13,2) NOT NULL,
  REMINDER_RATIO			 decimal(13,2),
  IS_DELETE                  INTEGER DEFAULT 0,
  PRICE_CREATE_TIME			 DATE,
  PRICE_UPDATE_TIME          DATE,
  CLIENT_POOL_NAME           VARCHAR(200),
  CLIENT_VERSION             VARCHAR(20),
  CLIENT_IP                  VARCHAR(50),
  SERVER_IP                  VARCHAR(30),
  STATUS                     INTEGER,
  PRIMARY KEY (ID)
);