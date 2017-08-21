package com.yhd.gps.schedule.historyprice.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.busy.vo.SimpleHistoryPriceChangeMsgVo;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-3-29 下午02:47:26
 */
public class HistoryPriceChangeMsgFilter {

    /**
     * 
     * @param pmId2ChannelId2MsgsMap
     * @param startDate
     * @param isFilter  true:过滤团闪促销,保留基准价和普通促销
     * @return
     */
    public static final Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> filterMsgsByDateSection(Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> pmId2ChannelId2MsgsMap,
                                                                                                    Date startDate, boolean isFilter) {
        if(MapUtils.isEmpty(pmId2ChannelId2MsgsMap) || null == startDate) {
            return Collections.emptyMap();
        }
        Map<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> result = new HashMap<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>>(
            pmId2ChannelId2MsgsMap);

        for(Map.Entry<Long, Map<Long, List<SimpleHistoryPriceChangeMsgVo>>> entry : result.entrySet()) {
            Map<Long, List<SimpleHistoryPriceChangeMsgVo>> map = entry.getValue();
            if(MapUtils.isEmpty(map)) {
                continue;
            }

            for(Map.Entry<Long, List<SimpleHistoryPriceChangeMsgVo>> innerEntry : map.entrySet()) {
                List<SimpleHistoryPriceChangeMsgVo> msgs = innerEntry.getValue();
                if(CollectionUtils.isEmpty(msgs)) {
                    continue;
                }

                List<SimpleHistoryPriceChangeMsgVo> list = new ArrayList<SimpleHistoryPriceChangeMsgVo>(msgs.size());

                SimpleHistoryPriceChangeMsgVo lastest = null;
                for(SimpleHistoryPriceChangeMsgVo msg : msgs) {
                    Integer ruleType = msg.getRuleType();
                    // 需要过滤，过滤只有普通促销和基准价价格变化
                    if(isFilter && !(msg.getPromotionId() == null || (ruleType != null && ruleType == BusyConstants.RULE_TYPE_NORMAL))) {
                        continue;
                    }
                    int compare = msg.getCreateTime().compareTo(startDate);

                    if(compare < 0) {// 记录前一个历史价格
                        lastest = msg;
                    } else if(compare == 0) {// 如果能匹配到00:00:00的历史价，则无需前推取前一历史价
                        list.add(msg);
                    } else if(compare > 0) {
                        list.add(msg);
                    }
                }
                // 如果最近一段时间都没有历史价格数据，则往前取最近的一条
                if(CollectionUtils.isEmpty(list) && lastest != null) {
                    list.add(lastest);
                }
                msgs.clear();
                msgs.addAll(list);
            }
        }
        return result;
    }
}
