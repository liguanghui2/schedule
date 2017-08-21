package com.yhd.gps.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yhd.gps.busyservice.dao.BusyMiscConfigDao;
import com.yhd.gps.config.vo.BusyMiscConfigVo;
import com.yhd.gps.schedule.dao.ShardingIndexDao;
import com.yhd.gps.schedule.service.ShardingIndexService;
import com.yhd.gps.schedule.vo.ShardingIndexVo;
import com.yihaodian.busy.exception.BusyException;

public class ShardingIndexServiceImpl implements ShardingIndexService {

    private static final Log log = LogFactory.getLog(ShardingIndexServiceImpl.class);

    private ShardingIndexDao shardingIndexDao;
    private BusyMiscConfigDao busyMiscConfigDao;

    public void setShardingIndexDao(ShardingIndexDao shardingIndexDao) {
        this.shardingIndexDao = shardingIndexDao;
    }

    public void setBusyMiscConfigDao(BusyMiscConfigDao busyMiscConfigDao) {
        this.busyMiscConfigDao = busyMiscConfigDao;
    }

    @Override
    public int addShardingEx(String tableName, int addSize) throws Exception {
        int result = 0;
        if(StringUtils.isEmpty(tableName) || addSize <= 0) {
            return result;
        }
        // 根据表名查分片
        List<ShardingIndexVo> shardingVos = shardingIndexDao.queryAllShardingByTableName(tableName);
        if(CollectionUtils.isEmpty(shardingVos)) {
            throw new BusyException("待切片的数据表名不存在，不能扩容！");
        }
        // 原size
        int originalSize = shardingVos.size();
        if(originalSize + addSize > 127) {
            throw new BusyException("切片总量不能大于127");
        }
        // 查配置表

        BusyMiscConfigVo configVo = busyMiscConfigDao.getBusyMiscConfigVoByKey(tableName);
        if(configVo != null) {
            String itemValue = configVo.getItemValue();
            int shardingCount = 0;
            if(StringUtils.isNotEmpty(itemValue) && StringUtils.isNumeric(itemValue)) {
                shardingCount = Integer.valueOf(itemValue);
            }
            if(shardingCount != originalSize) {
                throw new BusyException("配置表里切片个数与切片表不相等!");
            }
            // 开始扩容
            configVo.setItemValue(String.valueOf(shardingCount + addSize));
            busyMiscConfigDao.updateBusyMiscConfigVo(configVo);
        }

        shardingVos = new ArrayList<ShardingIndexVo>(addSize);
        for(int i = originalSize; i < originalSize + addSize; i++) {
            ShardingIndexVo shardingVo = new ShardingIndexVo(tableName, i, 1, "");
            shardingVos.add(shardingVo);
        }
        result = shardingIndexDao.batchInsertSharding(shardingVos);
        return result;
    }
}
