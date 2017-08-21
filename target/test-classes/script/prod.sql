DROP TABLE MERCHANT IF EXISTS;
CREATE TABLE MERCHANT (
  ID                    BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  MERCHANT_COMPANY_NAME VARCHAR(100) DEFAULT NULL,
  IS_YIHAODIAN          INTEGER,
  MERCHANT_MODEL        INTEGER DEFAULT 0,
  MERCHANT_GROUPID      BIGINT,
  DEPARTMENT_ID         BIGINT,
  PRIMARY KEY (ID)
);

DROP TABLE MERCHANT_AREA IF EXISTS;
CREATE TABLE MERCHANT_AREA
(
  ID          BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  MERCHANT_ID BIGINT DEFAULT NULL,
  PROVINCE_ID BIGINT DEFAULT NULL,
  CITY_ID     BIGINT DEFAULT NULL,
  COUNTY_ID   BIGINT DEFAULT NULL,
  AREA_ID     BIGINT DEFAULT NULL,
  COVER_LEVEL INTEGER,
  ENABLED     INTEGER,
  ROWNUM      INTEGER,
  PRIMARY KEY (ID)
);

DROP TABLE MERCHANT_SITE IF EXISTS;
CREATE TABLE MERCHANT_SITE
(
  ID               BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  MC_SITE_ID       BIGINT DEFAULT NULL,
  MERCHANT_ID      BIGINT DEFAULT NULL,
  MERCHANT_TYPE_ID BIGINT DEFAULT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE MERCHANT_WORD_DETAIL IF EXISTS;
CREATE TABLE MERCHANT_WORD_DETAIL
(
  ID          BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  DETAIL_CODE VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE CATEGORY IF EXISTS;
CREATE TABLE CATEGORY
(
  ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CATEGORY_CODE      VARCHAR(10),
  CATEGORY_NAME      VARCHAR(180),
  CATEGORY_PARENT_ID BIGINT DEFAULT NULL
);

DROP TABLE PRODUCT IF EXISTS;
CREATE TABLE PRODUCT
(
  ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  PRODUCT_CODE       VARCHAR(10) DEFAULT NULL,
  PRODUCT_CNAME      VARCHAR(1000) DEFAULT NULL,
  PRODUCT_TYPE       INTEGER,
  PRODUCT_STATUS     INTEGER,
  PRODUCT_LIST_PRICE DECIMAL(11, 2),
  	
  SHOPPINGCOUNT      INTEGER,
  IS_DELETED         INTEGER,
  SALES_TAX         INTEGER,
  CATEGORY_ID		 BIGINT,
  PRIMARY KEY (ID)
);

DROP TABLE VIRTUAL_PRODUCT IF EXISTS;
CREATE TABLE VIRTUAL_PRODUCT
(
  PRODUCT_ID         INTEGER,
  VIRTUAL_TYPE       INTEGER,
  CREATE_OPERATOR_ID INTEGER,
  CREATE_TIME        DATE,
  UPDATE_OPERATOR_ID INTEGER,
  UPDATE_TIME        DATE,
  PRIMARY KEY (PRODUCT_ID)
);

DROP TABLE PM_INFO IF EXISTS;
CREATE TABLE PM_INFO
(
  ID                BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  PRODUCT_ID        BIGINT DEFAULT NULL,
  MERCHANT_ID       BIGINT DEFAULT NULL,
  PRODUCT_SALE_TYPE INTEGER DEFAULT 0,
  CAN_SALE          INTEGER DEFAULT 0,
  CAN_SHOW          INTEGER DEFAULT 1,
  IS_STOCK_SUPPORTSHARE INTEGER DEFAULT 0,
  THIRDPARTY_SUPPORT INTEGER DEFAULT 0,
  PGM_INFO_ID INTEGER DEFAULT 0,
  UPDATE_TIME DATE,
  PM_INFO_TYPE 	INTEGER,
  SITE_ID INTEGER,
  OUTER_ID VARCHAR(100),
  ROWNUM      INTEGER,
  PRIMARY KEY (ID)
);

DROP TABLE PRODUCT_COMBINE IF EXISTS;
CREATE TABLE PRODUCT_COMBINE
(
  ID                        BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  PRODUCT_ID                BIGINT DEFAULT NULL,
  SINGLE_PRODUCT_ID         BIGINT DEFAULT NULL,
  SINGLE_PRODUCT_NUM        INTEGER,
  IS_MAIN_PRODUCT           INTEGER,
  PM_INFO_ID                BIGINT DEFAULT NULL,
  SINGLE_PM_INFO_ID         BIGINT DEFAULT NULL,
  IS_NEW_PRODUCT_COMBINE    INTEGER,
  SINGLE_PRODUCT_TYPE       INTEGER,
  SERIES_VIRTUAL_PRODUCT_ID BIGINT DEFAULT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE PRODUCT_COMBINE_REBATE_RULE IF EXISTS;
CREATE TABLE PRODUCT_COMBINE_REBATE_RULE
(
  ID                     BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  PRODUCT_COMBINE_ID     BIGINT DEFAULT NULL,
  MERCHANT_ID            BIGINT DEFAULT NULL,
  DISCOUNT               DECIMAL(11, 2),
  COMBINE_PRICE          DECIMAL(11, 2),
  PRICE_CALCULATION_TYPE INTEGER,
  PM_INFO_ID             BIGINT DEFAULT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE PRODUCT_SERIAL IF EXISTS;
CREATE TABLE PRODUCT_SERIAL
(
  ID              BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  PRODUCT_ID      BIGINT DEFAULT NULL,
  SUB_PRODUCT_ID  BIGINT DEFAULT NULL,
  IS_MAIN_PRODUCT INTEGER,
  PRIMARY KEY (ID)
);

DROP TABLE DIM_PROD_MRCHNT_PRICE_AVG IF EXISTS;
CREATE TABLE DIM_PROD_MRCHNT_PRICE_AVG
(
  PROD_ID    BIGINT DEFAULT NULL,
  MRCHNT_ID  BIGINT DEFAULT NULL,
  AVG_PRICE  DOUBLE,
  PM_INFO_ID BIGINT DEFAULT NULL
);

DROP TABLE DUAL IF EXISTS;
CREATE TABLE DUAL
(
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL
);
    
DROP TABLE PM_INFO_EXT IF EXISTS;
CREATE TABLE PM_INFO_EXT(
	ID 	BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) NOT NULL, 	
	PM_INFO_ID 	BIGINT,
	CATEGORY_ID 	BIGINT,
	IS_STOCK_SUPPORTSHARE 	INTEGER,
	IS_SPECIAL_ONFORMAT_SHARESTOCK 	INTEGER,
	IS_SUPPORT_ONEKEY_GO 	INTEGER,
	IS_SPECIAL_ONFORMAT_ONEKEYGO 	INTEGER,
	IS_DELETED 	BIGINT,
	IS_SUPPORT_SUBSIDY 	INTEGER,
	SUBSIDY_AMOUNT 	DECIMAL(11, 2),
	STOCK_SHARE_TYPE 	INTEGER,
	STOCK_SHARE_SET 	VARCHAR(3000),
	PRIMARY KEY (ID)
);

DROP TABLE PRODUCT_COST_REFERENCE_RULE IF EXISTS;
CREATE TABLE PRODUCT_COST_REFERENCE_RULE(
	ID 	BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) NOT NULL, 	
	PRODUCT_ID 	BIGINT,
	SOURCE_MERCHANT_ID 	BIGINT,
	REFERENCE_MERCHANT_ID 	BIGINT,
	STATE 	INTEGER,
	IS_ONLY_CHECK_SHARE 	INTEGER,
	OPERATOR_ID 	BIGINT,
	UPDATE_TIME 	DATE,
	PRIMARY KEY (ID)
);

DROP TABLE CATEGORY IF EXISTS;
CREATE TABLE CATEGORY(
	ID	BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) NOT NULL, 	
	CATEGORY_CODE 	VARCHAR(10),
	CATEGORY_NAME 	VARCHAR(180),
	CATEGORY_PARENT_ID 	BIGINT,
	CATEGORY_KEYWORD 	VARCHAR(400),
	CATEGORY_SEARCH_NAME 	VARCHAR(220),	
	IS_DELETE         INTEGER,
	PRIMARY KEY (ID)
);

DROP TABLE CATEGORY_COST_REFERENCE_RULE IF EXISTS;
CREATE TABLE CATEGORY_COST_REFERENCE_RULE(
	ID 	BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) NOT NULL, 	
	CATEGORY_ID 	BIGINT,
	SOURCE_MERCHANT_ID 	BIGINT,
	REFERENCE_MERCHANT_ID 	BIGINT,
	STATE 	INTEGER,
	IS_ONLY_CHECK_SHARE 	INTEGER,
	PRIMARY KEY (ID)
);

DROP TABLE RPT_TODAY_PRODUCT_PRICE IF EXISTS;
CREATE TABLE RPT_TODAY_PRODUCT_PRICE(
	MERCHANT_ID 	BIGINT,
	PRODUCT_ID 	BIGINT,
	AVG_IN_PRICE DECIMAL(11, 2),
	IS_DAIXIAO 	INTEGER,
	IS_UPDT 	INTEGER,
	PRIMARY KEY (MERCHANT_ID, PRODUCT_ID)
);

DROP TABLE PM_SUPPLIER IF EXISTS;
CREATE TABLE PM_SUPPLIER(
	ID 	BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) NOT NULL, 	
	PRODUCT_ID	BIGINT,
	MERCHANT_ID	BIGINT,
	IS_DELETED	INTEGER,
	IS_DEFAULT	INTEGER,
	CATEGORY_ID 	BIGINT,
	IN_PRICE 	DECIMAL(11, 2),
	PRIMARY KEY (ID)
);

DROP TABLE VD_PM_INFO IF EXISTS;
CREATE TABLE VD_PM_INFO 
( 
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) NOT NULL, 	
  PM_INFO_ID BIGINT not null, 
  VD_MERCHANT_ID BIGINT not null, 
  YHD_PM_INFO_STATUS smallint default 0, 
  SOURCE smallint,
  UPDATE_TIME datetime, 
  CREATE_TIME datetime, 
  CREATE_USER_ID bigint, 
  UPDATE_USER_ID bigint
);

DROP TABLE CATEGORY_LEVEL IF EXISTS;
CREATE TABLE CATEGORY_LEVEL
(
  ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) NOT NULL,
  CATEGORY_ID		 BIGINT,
  LEVEL3 BIGINT,
  PRIMARY KEY (ID)
);