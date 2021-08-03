package com.yele.architecture.ui.page;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.yele.architecture.model.DataBindingConfig;

public abstract class DataBindingActivity extends AppCompatActivity {
    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    public DataBindingActivity() {
    }

    protected abstract void initViewModel();

    protected abstract DataBindingConfig getDataBindingConfig();

    protected ViewDataBinding getBinding() {
        if (this.isDebug() && this.mBinding != null && this.mTvStrictModeTip == null) {
            this.mTvStrictModeTip = new TextView(this.getApplicationContext());
            this.mTvStrictModeTip.setAlpha(0.4F);
            this.mTvStrictModeTip.setTextSize(14.0F);
            this.mTvStrictModeTip.setBackgroundColor(-1);
            this.mTvStrictModeTip.setText("严格模式下ViewBinding不对外层暴露");
            ((ViewGroup) this.mBinding.getRoot()).addView(this.mTvStrictModeTip);
        }
        return this.mBinding;
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initViewModel();
        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
        ViewDataBinding binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        binding.setVariable(dataBindingConfig.getVmFieldId(), dataBindingConfig.getStateViewModel());
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        int i = 0;
        for (int length = bindingParams.size(); i < length; ++i) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        this.mBinding = binding;
    }

    public boolean isDebug() {
        return this.getApplicationContext().getApplicationInfo() != null && (this.getApplicationContext().getApplicationInfo().flags & 2) != 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding.unbind();
        this.mBinding = null;
    }
}
