package com.yele.jetpackbestpractice;


import com.yele.architecture.BaseApplication;
import com.yele.architecture.utils.Utils;

/**
 * 作为ShareViewModel的store
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化底层数据
        Utils.init(this);
    }
}
