package com.yele.jetpackbestpractice.ui.page;

import androidx.fragment.app.Fragment;

import com.yele.architecture.model.DataBindingConfig;
import com.yele.architecture.ui.page.BaseFragment;
import com.yele.jetpackbestpractice.R;

/**
 * description : <p>
 * 应用描述
 * </p>
 *
 * @author : flyli
 * @since :2021/8/4 23
 */
public class MainFragment extends BaseFragment {
    private MainFragmentViewModel mState;
    private ShareViewModel mEvent;


    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(MainFragmentViewModel.class);
        mEvent = getApplicationScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_main,BR.);
    }
}
