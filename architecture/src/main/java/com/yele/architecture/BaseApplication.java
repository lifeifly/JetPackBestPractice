package com.yele.architecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * 在Application中创建一个全局共享的ViewModel作为所以需要的页面的唯一可信数据来源
 */
public class BaseApplication extends Application implements ViewModelStoreOwner {
    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppViewModelStore = new ViewModelStore();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
