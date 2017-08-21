package com.yhd.gps.schedule.dao;

import java.util.Date;
import java.util.List;

import com.yhd.gps.schedule.vo.DataProcessScannerVo;

public interface DataProcessScannerDao {

    /**
     * 根据业务类型和切片分页查数据处理扫描表
     * @param businessType
     * @param shardingIndex
     * @param pageSize
     * @return
     */
    public List<DataProcessScannerVo> getDataProcessScanner(Integer businessType, int shardingIndex, Integer pageSize);

    /**
     * 批量更新下次执行时间
     * @param ids
     * @param nextProcessTime
     * @return
     */
    public int batchUpdateDataProcessScannerNextProcessTimeByIds(List<Long> ids, Date nextProcessTime);

    /**
     * 批量根据主键id删除记录
     * @param ids
     * @return
     */
    public int batchDeleteDataProcessScannerByIds(List<Long> ids);

    /**
     * 新增扫描表
     * @param scannerVo
     * @return
     */
    public Long insertDataProcessScanner(DataProcessScannerVo scannerVo);

    /**
     * 批量新增扫描表
     * @param scannerVos
     * @return
     */
    public int batchInsertDataProcessScanner(List<DataProcessScannerVo> scannerVos);

    /**
     * 根据业务类型和refId查扫描表
     * @param businessType
     * @param refId
     * @return
     */
    public DataProcessScannerVo getDataProcessScannerByRefId(Integer businessType, Long refId);

    /**
     * 批量根据业务类型和refIds查扫描表
     * @param businessType
     * @param refIds
     * @return
     */
    public List<DataProcessScannerVo> batchGetDataProcessScannerByRefIds(Integer businessType, List<Long> refIds);
}
