package com.yele.architecture.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

public class CommonViewPagerAdapter extends PagerAdapter {

    private final int count;
    private final boolean enableDestroyItem;
    private final String[] title;

    public CommonViewPagerAdapter(boolean enableDestroyItem, String[] title) {
        this.enableDestroyItem = enableDestroyItem;
        this.title = title;
        this.count = title.length;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        return container.getChildAt(position);
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        if (enableDestroyItem) {
            container.removeView((View) object);
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
