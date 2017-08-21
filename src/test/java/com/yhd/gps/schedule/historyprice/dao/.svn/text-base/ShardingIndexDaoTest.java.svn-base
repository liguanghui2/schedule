package com.yhd.gps.schedule.historyprice.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByName;

import com.alibaba.fastjson.JSON;
import com.yhd.gps.BaseSpringWithUnitilsTest;
import com.yhd.gps.schedule.dao.ShardingIndexDao;
import com.yhd.gps.schedule.vo.ShardingIndexVo;

/**
 * ShardingIndexDaoTest
 * @Author liuxiangrong
 * @CreateTime Mon Feb 01 22:41:57 CST 2016
 */
public class ShardingIndexDaoTest extends BaseSpringWithUnitilsTest {

	@SpringBeanByName
    private ShardingIndexDao shardingIndexDao;

    @Test
    @TestDataSource("public")
    public void queryAllShardingByTableName() {
        List<ShardingIndexVo> list = shardingIndexDao.queryAllShardingByTableName("history_price_change_msg");
        assertNotNull(list);
        assertEquals(list.size(), 8);
        String jsonArray = JSON.toJSONString(list);
        System.out.println("\n\n-----queryAllSharding=" + jsonArray.toString() + "\n\n");
    }

    @Test
    @TestDataSource("public")
    public void updateShardingUnValid() {
        ShardingIndexVo data = new ShardingIndexVo();
        data.setIp("192.168.30.5");
        data.setId(4L);
        int r = shardingIndexDao.updateShardingUnValid(data);
        System.out.println("updateShardingStatus--" + r + "--");
        //
        List<ShardingIndexVo> list = shardingIndexDao.queryAllShardingByTableName("history_price_change_msg");
        assertNotNull(list);
        for(ShardingIndexVo vo : list) {
            if(vo.getId() == 4) {
                assertEquals(vo.getIsValid(), 0);
                assertEquals(vo.getIp(), "192.168.30.5");
                String jsonArray = JSON.toJSONString(vo);
                System.out.println("\n\n-----updateShardingStatus=" + jsonArray.toString() + "\n\n");
                return;
            }
        }
    }

    @Test
    @TestDataSource("public")
    public void updateShardingValid() {
        ShardingIndexVo data = new ShardingIndexVo();
        data.setIp("192.168.30.5");
        data.setId(4L);
        int r = shardingIndexDao.updateShardingUnValid(data);
        System.out.println("updateShardingStatus--" + r + "--");
        //
        List<ShardingIndexVo> list = shardingIndexDao.queryAllShardingByTableName("history_price_change_msg");
        assertNotNull(list);
        for(ShardingIndexVo vo : list) {
            if(vo.getId() == 4) {
                assertEquals(vo.getIsValid(), 0);
                assertEquals(vo.getIp(), "192.168.30.5");
                break;
            }
        }

        shardingIndexDao.updateShardingValid(data);
        list = shardingIndexDao.queryAllShardingByTableName("history_price_change_msg");
        assertNotNull(list);
        for(ShardingIndexVo vo : list) {
            if(vo.getId() == 4) {
                assertEquals(vo.getIsValid(), 1);
                assertEquals(vo.getIp(), "");
                return;
            }
        }
        assertTrue(false);
    }

    @Test
    @TestDataSource("public")
    public void updateShardingValidByActiveIps() {
        // 192.168.30.4故障，192.168.30.1-192.168.30.5测试的IP
        List<String> ipList = new ArrayList<String>();
        ipList.add("192.168.30.1");
        ipList.add("192.168.30.2");
        ipList.add("192.168.30.5");
        ipList.add("192.168.30.3");
        int r = shardingIndexDao.updateShardingValidByActiveIps(ipList);
        System.out.println("updateShardingStatusByIplist--" + r + "--");

        List<ShardingIndexVo> list = shardingIndexDao.queryAllShardingByTableName("history_price_change_msg");
        assertNotNull(list);
        for(ShardingIndexVo vo : list) {
            if(vo.getId() == 3 && vo.getIp().equals("192.168.30.4")) {
                assertEquals(vo.getIsValid(), 1);
                String jsonArray = JSON.toJSONString(vo);
                System.out.println("\n\n-----updateShardingStatusByIplist=" + jsonArray.toString() + "\n\n");
                return;
            }
        }
    }
}