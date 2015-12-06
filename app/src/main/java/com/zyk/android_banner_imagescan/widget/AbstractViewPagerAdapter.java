/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.widget;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/** 
 * �����PagerAdapterʵ����,��װ������ΪView�Ĺ�������. 
 * @author zyk
 */  
public abstract class AbstractViewPagerAdapter<T> extends PagerAdapter {  
    protected List<T> mData;  
    private SparseArray<View> mViews;  
  
    public AbstractViewPagerAdapter(List<T> data) {  
        mData = data;  
        mViews = new SparseArray<View>(data.size());  
    }  
  
    @Override  
    public int getCount() {  
        return mData.size();  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
        return view == object;  
    }  
  
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
        View view = mViews.get(position);  
        if (view == null) {  
            view = getView(position);  
            mViews.put(position, view);  
        }  
        container.addView(view);  
        return view;  
    }  
  
    /**
     * ����View�ĳ�ʼ�� �͸�ֵ
     */
    public abstract View getView(int position);  
  
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
        container.removeView(mViews.get(position));  
    }  
  
    public T getItem(int position) {  
        return mData.get(position);  
    }  
    
    
}  