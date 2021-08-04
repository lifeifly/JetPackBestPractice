package com.yele.jetpackbestpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;

import com.yele.architecture.model.DataBindingConfig;
import com.yele.architecture.ui.page.BaseActivity;
import com.yele.jetpackbestpractice.ui.event.ShareViewModel;
import com.yele.jetpackbestpractice.ui.helper.DrawerCoordinateHelper;
import com.yele.jetpackbestpractice.ui.state.MainActivityViewModel;

public class MainActivity extends BaseActivity {

    private MainActivityViewModel mState;
    private ShareViewModel mEvent;
    private boolean mIsListened = false;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MainActivityViewModel.class);
        mEvent = getAppScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        //TODO tip 1: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
        // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。

        return new DataBindingConfig(R.layout.activity_main, BR.state, mState).addBindingParams(BR.listener, new ListenerHandler());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvent.isToCloseActivityIfAllowed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                NavController nav = Navigation.findNavController(MainActivity.this, R.id.main_fragment_host);
                if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
                    nav.navigateUp();
                } else if (mState.isDrawerOpened.get()) {
                    mEvent.requestToOpenOrCloseDrawer(false);
                } else {
                    MainActivity.super.onBackPressed();
                }
            }
        });

        mEvent.isToOpenOrCloseDrawer().observe(this, aBoolean -> {

            //TODO yes：同 tip 1: 此处将 drawer 的 open 和 close 都放在 drawerBindingAdapter 中操作，规避了视图实例 null 安全的一致性问题，

            //因为 横屏布局 根本就没有 drawerLayout。此处如果用传统的视图实例 null 安全方式，会很容易因疏忽而造成空引用。

            //TODO 此外，此处为 drawerLayout 绑定的状态 "openDrawer"，使用 LiveData 而不是 ObservableField，主要是考虑到 ObservableField 具有 "防抖" 的特性，不适合该场景。

            //如果这么说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350

            mState.openDrawer.setValue(aBoolean);

            //TODO do not:（容易因疏忽 而埋下视图实例 null 安全的一致性隐患）

            /*if (mBinding.dl != null) {
                if (aBoolean && !mBinding.dl.isDrawerOpen(GravityCompat.START)) {
                    mBinding.dl.openDrawer(GravityCompat.START);
                } else {
                    mBinding.dl.closeDrawer(GravityCompat.START);
                }
            }*/
        });
        DrawerCoordinateHelper.getInstance().isEnableSwipeDrawer().observe(this, aBoolean -> {

            //TODO yes: 同 tip 1

            mState.allowDrawerOpen.setValue(aBoolean);

            // TODO do not:（容易因疏忽 而埋下视图实例 null 安全的一致性隐患）

            /*if (mBinding.dl != null) {
                mBinding.dl.setDrawerLockMode(aBoolean
                        ? DrawerLayout.LOCK_MODE_UNLOCKED
                        : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }*/
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!mIsListened) {
            // TODO tip 2：此处演示通过 UnPeekLiveData 来发送 生命周期安全的、确保消息同步一致性和可靠性的 通知。
            // fragment 内部的事情在 fragment 内部消化，不要试图在 Activity 中调用和操纵 Fragment 内部的东西。
            // 因为 fragment 端的处理后续可能会改变，并且可受用于更多的 Activity，而不单单是本 Activity。
            mEvent.requestToAddSlideListener(true);

            mIsListened = true;
        }
    }

    @Override
    public void onBackPressed() {
        mEvent.requestToCloseSlidePanelIfExpanded(true);
    }

    public class ListenerHandler extends DrawerLayout.SimpleDrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            mState.isDrawerOpened.set(true);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            mState.isDrawerOpened.set(false);
            mState.openDrawer.setValue(false);
        }
    }
}