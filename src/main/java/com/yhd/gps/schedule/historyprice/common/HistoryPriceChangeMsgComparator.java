package com.yhd.gps.schedule.historyprice.common;

import java.util.Comparator;
import java.util.Date;

import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;

/**
 * 按照时间比较历史价格类
 * @Author liuxiangrong
 * @CreateTime 2016-2-2 下午02:52:43
 */
public class HistoryPriceChangeMsgComparator implements Comparator<SimpleHistoryPriceChangeMsgVo> {

    /**
     * 按照时间比较历史价格
     */
    @Override
    public int compare(SimpleHistoryPriceChangeMsgVo o1, SimpleHistoryPriceChangeMsgVo o2) {
        if(o1 == null) {
            return 1;
        } else if(o2 == null) {
            return 0;
        }
        Date date1 = o1.getCreateTime();
        if(date1 == null) {
            return 1;
        }
        Date date2 = o2.getCreateTime();
        if(date2 == null) {
            return 0;
        }

        return date1.compareTo(date2);
    }
}