package com.yhd.gps.schedule.vo;

import java.util.List;

/**
 * 区间价格持久化保存类
 * @Author xuyong
 * @CreateTime 2015-6-10 上午11:44:46
 */
public class DateSectionPricePersistTuple<T> {

    private List<T> insertList;
    private List<T> updateList;

    public DateSectionPricePersistTuple(List<T> insertList, List<T> updateList) {
        super();
        this.insertList = insertList;
        this.updateList = updateList;
    }

    public void addInsertInfo(T t) {
        insertList.add(t);
    }

    public void addUpdateInfo(T t) {
        updateList.add(t);
    }

    public List<T> getInsertList() {
        return insertList;
    }

    public void setInsertList(List<T> insertList) {
        this.insertList = insertList;
    }

    public List<T> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<T> updateList) {
        this.updateList = updateList;
    }

}
