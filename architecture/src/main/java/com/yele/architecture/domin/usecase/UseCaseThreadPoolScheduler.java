package com.yele.architecture.domin.usecase;

import android.os.Handler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {
    public static final int POOL_SIZE = 2;
    public static final int MAX_POOL_SIZE = 4 * 2;
    public static final int FIXED_POOL_SIZE = 4;
    public static final int TIMEOUT = 30;
    final ThreadPoolExecutor mThreadPoolExecutor;
    private final Handler mHandler = new Handler();

    public UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(FIXED_POOL_SIZE, FIXED_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends UseCase.ResponseValues> void notifyResponse(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(() -> {
            if (null != useCaseCallback){
                useCaseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <V extends UseCase.ResponseValues> void onError(UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(useCaseCallback::onError);
    }
}
