package com.yhd.gps.busyservice.service;

import java.util.List;
import java.util.Set;

import com.yhd.gps.schedule.vo.DataProcessScannerVo;

public interface PromRuleSoldNumResetService {

    /**
     * <pre>
     * 重置product_prom_rule表中soldNum(SAM && 促销类型是6)
     * 1、查价格促销；
     * 2、无促销记录则删除扫描表的促销记录，无效或过期促销不处理，有效促销更新soldNum。
     * 3、更新扫描表下一次执行时间为第二天00:00:00
     * </pre>
     * @param dataProcessScannerVos 扫描记录
     * @param scannerIds 需要更新的扫描记录
     * @return
     */
    public Integer resetPromRuleSoldNum(List<DataProcessScannerVo> dataProcessScannerVos, Set<Long> scannerIds);

}
