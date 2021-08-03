package com.yele.architecture.model.manager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.yele.architecture.broadcast.NetworkStateReceive;
import com.yele.architecture.utils.Utils;

import org.jetbrains.annotations.NotNull;

public class NetworkStateManager implements DefaultLifecycleObserver {
    private static final NetworkStateManager S_MANAGER = new NetworkStateManager();

    private final NetworkStateReceive mNetworkStateReceive = new NetworkStateReceive();

    private NetworkStateManager() {
    }

    public static NetworkStateManager getInstance() {
        return S_MANAGER;
    }

    @Override
    public void onResume(@NonNull @NotNull LifecycleOwner owner) {
        //动态注册广播,只在注册的上下文有效时可以接收
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        Utils.getApp().getApplicationContext().registerReceiver(mNetworkStateReceive, intentFilter);
    }

    @Override
    public void onPause(@NonNull @NotNull LifecycleOwner owner) {
        Utils.getApp().getApplicationContext().unregisterReceiver(mNetworkStateReceive);
    }
}
