package com.yele.jetpackbestpractice.data.bean;

import com.yele.architecture.ui.page.BaseActivity;

import java.io.Serializable;

public class BaseArtistItem implements Serializable {
    private String name;
    public BaseArtistItem(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
