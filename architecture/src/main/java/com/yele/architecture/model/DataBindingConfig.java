package com.yele.architecture.model;

import android.content.Intent;
import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

/**
 * 布局参数和数据绑定的集成
 */
public class DataBindingConfig {
    private final int layout;
    private final int vmFieldId;
    private final ViewModel stateViewModel;
    private SparseArray bindingParams=new SparseArray();

    public DataBindingConfig(int layout, int vmFieldId, ViewModel stateViewModel) {
        this.layout = layout;
        this.vmFieldId = vmFieldId;
        this.stateViewModel = stateViewModel;
    }

    public DataBindingConfig addBindingParams(Integer variableId,Object object){
        if (this.bindingParams.get(variableId)==null){
            this.bindingParams.put(variableId,object);
        }
        return this;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmFieldId() {
        return vmFieldId;
    }

    public ViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }
}
