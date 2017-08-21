package com.yhd.gps.schedule.common;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * GPS 定时器基础DAO类
 * @Author liuxiangrong
 * @CreateTime 2016-1-31 上午07:54:16
 */
public class ScheduleBaseDao extends SqlMapClientDaoSupport implements BeanFactoryAware {
    /**
     * 为避免重名改为独特的名字
     */
    private final static String SQL_MAP_CLIENT_BEAN_NAME = "gps_schedule_sqlMapClient";

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // 给dao注入 sqlMapClient bean
        SqlMapClient sqlMapClient = (SqlMapClient) beanFactory.getBean(SQL_MAP_CLIENT_BEAN_NAME);
        setSqlMapClient(sqlMapClient);
    }

    /**
     * 根据sql查询出一个对象 直接从DB中查询获得数据,数据不进行cache
     * 
     * @param statementName sql声明语句名称
     * @return 返回一条查询数据
     * @throws org.springframework.dao.DataAccessException 抛出DB异常
     */
    protected Object queryForObject(String statementName) throws DataAccessException {
        return queryForObject(statementName, null);
    }

    protected Object queryForObject(final String statementName, final Object parameterObject) throws DataAccessException {
        return this.getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
    }

    /**
     * 根据sql查询出一组数据 直接从DB中查询获得数据,数据不进行cache
     * 
     * @param statementName sql声明语句名称
     * @return 返回一组查询数据
     * @throws org.springframework.dao.DataAccessException 抛出DB异常
     */
    @SuppressWarnings("rawtypes")
    protected List queryForList(String statementName) throws DataAccessException {
        return queryForList(statementName, null);
    }

    @SuppressWarnings("rawtypes")
    protected List queryForList(final String statementName, final Object parameterObject) throws DataAccessException {
        return this.getSqlMapClientTemplate().queryForList(statementName, parameterObject);
    }

    /**
     * 向DB中插入数据
     * 
     * @param statementName sql声明语句名称
     * @param parameterObject 需要插入的数据
     * @return 返回新创建记录的主键Id
     */
    protected Long insert(String statementName, Object parameterObject) throws DataAccessException {
        Long result = null;
        Object obj;
        if(null != parameterObject) {
            obj = this.getSqlMapClientTemplate().insert(statementName, parameterObject);
        } else {
            obj = this.getSqlMapClientTemplate().insert(statementName);
        }
        if(obj != null) {
            result = (Long) obj;
        }
        return result;
    }

    protected <T> int batchInsert(final String statementName, final List<T> parameters) {
        return (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int rows = 0;
                int batch = 0;
                executor.startBatch();
                for(T parameter : parameters) {
                    executor.insert(statementName, parameter);
                    // 每500条批量提交一次
                    if(++batch == 500) {
                        rows += executor.executeBatch();
                        executor.startBatch();
                        batch = 0;
                    }
                }
                rows += executor.executeBatch();
                return rows;
            }
        });
    }

    /**
     * 向DB中更新一条数据
     * 
     * @param statementName sql声明语句名称
     * @param parameterObject 需要更新的数据
     * @return 返回更新操作的状态值 1:成功 0:失败
     */
    protected int update(String statementName, Object parameterObject) throws DataAccessException {
        int result = 0;
        if(null != parameterObject) {
            result = this.getSqlMapClientTemplate().update(statementName, parameterObject);
        } else {
            result = this.getSqlMapClientTemplate().update(statementName);
        }
        return result;
    }

    protected <T> int batchUpdate(final String statementName, final List<T> parameters) {
        return (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int rows = 0;
                int batch = 0;
                executor.startBatch();
                for(T parameter : parameters) {
                    executor.update(statementName, parameter);
                    // 每500条批量提交一次
                    if(++batch == 500) {
                        rows += executor.executeBatch();
                        executor.startBatch();
                        batch = 0;
                    }
                }
                rows += executor.executeBatch();
                return rows;
            }
        });
    }

    protected int delete(String statementName, Object parameterObject) throws DataAccessException {
        int result = this.getSqlMapClientTemplate().delete(statementName, parameterObject);
        return result;
    }
}
