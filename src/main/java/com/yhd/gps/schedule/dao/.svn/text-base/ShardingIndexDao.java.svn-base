package com.yhd.gps.schedule.dao;

import java.util.List;

import com.yhd.gps.schedule.vo.ShardingIndexVo;

/**
 * 
 * 数据分片DAO
 * @Author liuxiangrong
 * @CreateTime 2016-1-29 下午01:48:26
 */
public interface ShardingIndexDao {

    /**
     * 获取所有的分片信息
     */
    public List<ShardingIndexVo> queryAllShardingByTableName(String tableName);

    /**
     * 更新分片状态为占用
     */
    public int updateShardingUnValid(ShardingIndexVo vo);
    
    /**
     * 更新宕机的机器释放的切片为占用状态
     */
    public int updateCrashShardingUnValid(ShardingIndexVo vo);

    /**
     * 更新分片状态为可用
     */
    public int updateShardingValid(ShardingIndexVo vo);

    /**
     * 根据活动IP列表更新分片表中不活动IP的占用状态
     */
    public int updateShardingValidByActiveIps(List<String> activeIps);

    /**
     * 获取所有的分片信息
     */
    public List<ShardingIndexVo> queryAllSharding();

    /**
     * 根据Id获取分片信息
     * @param id
     * @return
     */
    public List<ShardingIndexVo> queryShardingById(Long id);

    /**
     * 新增分片
     * @param shardingIndexVo
     * @return
     */
    public Long insertSharding(ShardingIndexVo shardingIndexVo);

    /**
     * 批量新增分片
     * @param shardingIndexVos
     * @return
     */
    public int batchInsertSharding(List<ShardingIndexVo> shardingIndexVos);

    /**
     * 批量根据id更新分片状态为可用
     * @param ids
     * @return
     */
    public int batchUpdateShardingValidById(List<Long> ids);
    /**
     * 用于查询京东图书机器无宕机的机器数量
     * @param tableName
     * @return
     */
    public Integer queryCrashNumByTableName(String tableName);
    
    /**
     * 查询所有宕机后机器释放的切片
     * @param tableName
     * @return
     */
    public List<ShardingIndexVo> queryAllCrashShardingByTableName(String tableName);
    
    /**
     * 根据切片ID删除切片
     */
    public int deleteShardingById(Long id);
}