package com.yhd.schedule.sharding.exec;

import java.util.Random;
import java.util.Set;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-3-10 上午11:33:31
 */
public class ExecResult<R> {

    public R result;

    public Set<Long> dataIds;

    public ExecResult() {
        super();
    }

    public ExecResult(R result, Set<Long> dataIds) {
        this();
        this.result = result;
        this.dataIds = dataIds;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public Set<Long> getDataIds() {
        return dataIds;
    }

    public void setDataIds(Set<Long> dataIds) {
        this.dataIds = dataIds;
    }

    @Override
    public String toString() {
        return "ExecResult [result=" + result + ", dataIds=" + dataIds + "]";
    }

    public static void main(String[] args) {
        while(true) {
            System.out.println(new Random().nextInt(100));
        }
    }

}
