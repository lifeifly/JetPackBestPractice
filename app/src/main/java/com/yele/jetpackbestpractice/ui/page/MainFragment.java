package com.yele.jetpackbestpractice.ui.page;

import androidx.fragment.app.Fragment;
<<<<<<<HEAD
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.yele.architecture.model.DataBindingConfig;
import com.yele.architecture.ui.page.BaseFragment;
import com.yele.jetpackbestpractice.BR;
import com.yele.jetpackbestpractice.R;
import com.yele.jetpackbestpractice.ui.event.ShareViewModel;
import com.yele.jetpackbestpractice.ui.state.MainFragmentViewModel;

public class MainFragment extends BaseFragment {
    //TODO tip 1：每个页面都要单独配备一个 state-ViewModel，职责仅限于 "状态托管和恢复"，
    //event-ViewModel 则是用于在 "跨页面通信" 的场景下，承担 "唯一可信源"，
    private MainFragmentViewModel mState;
    private ShareViewModel mEvent;
    private PlayListAdapter mAdapter;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(MainFragmentViewModel.class);
        mEvent = getApplicationScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        return new DataBindingConfig(R.layout.fragment_main, BR.vm,mState).addBindingParams(BR.click,new ClickProxy())
                .addBindingParams(BR.adapter,);
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    /**
     * 点击修改全局ViewModel控制抽屉开关和进行登录、跳转页面
     */
    public class ClickProxy {
        public void openMenu() {
            //修改全局ViewModel是否打开抽屉的的状态值
            // TODO tip 8：此处演示通过 UnPeekLiveData 来发送 生命周期安全的、确保消息同步一致性和可靠性的 通知。

            // 如果这么说还不理解的话，详见 https://xiaozhuanlan.com/topic/0168753249
            // --------
            // 与此同时，此处传达的另一个思想是 最少知道原则，
            // Activity 内部的事情在 Activity 内部消化，不要试图在 fragment 中调用和操纵 Activity 内部的东西。
            // 因为 Activity 端的处理后续可能会改变，并且可受用于更多的 fragment，而不单单是本 fragment。

            mEvent.requestToOpenOrCloseDrawer(true);
        }

        public void login() {
            nav().navigate(R.id.action_mainFragment_to_loginFragment);
        }

        public void search() {
            nav().navigate(R.id.action_mainFragment_to_searchFragment);
        }
    }
}