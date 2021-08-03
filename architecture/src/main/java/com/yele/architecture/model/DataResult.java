package com.yele.architecture.model;

import java.time.temporal.Temporal;

/**
 * TODO: 专用于数据层返回结果给 domain 层或 ViewModel 用，原因如下：
 * <p>
 * liveData 是专用于页面开发的、用于解决生命周期安全问题的组件，
 * 有时候数据并非一定是通过 liveData 来分发给页面，也可能是通过别的组件去通知给非页面的东西，
 * 这时候 repo 方法中内定通过 liveData 分发就不太合适，不如一开始就规定不在数据层通过 liveData 返回结果。
 * <p>
 */
public class DataResult<T> {
    private T mEntity;
    private ResponseStatus mResponseStatus;

    public DataResult(T entity, ResponseStatus responseStatus) {
        this.mEntity = entity;
        this.mResponseStatus = responseStatus;
    }

    public T getEntity() {
        return mEntity;
    }

    public ResponseStatus getResponseStatus() {
        return mResponseStatus;
    }

    public interface Result<T> {
        void onResult(DataResult<T> dataResult);
    }
}
