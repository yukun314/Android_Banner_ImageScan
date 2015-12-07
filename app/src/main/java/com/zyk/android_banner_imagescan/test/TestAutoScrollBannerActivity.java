/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zyk.android_banner_imagescan.AutoScrollBanner;
import com.zyk.android_banner_imagescan.R;
import com.zyk.android_banner_imagescan.transforms.Transformer;
import com.zyk.android_banner_imagescan.widget.AutoScrollBannerAdapter;
import com.zyk.android_banner_imagescan.widget.AutoScrollBannerImageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoScrollBanner 使用的Demo
 */
public class TestAutoScrollBannerActivity extends AppCompatActivity {

    private AutoScrollBanner mAutoScrollBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_auto_scroll_banner);
        init();
    }

    private void init(){
        mAutoScrollBanner = (AutoScrollBanner) findViewById(R.id.activity_main_autoscrollbanner);
        List<Object> list = new ArrayList<Object>();
        list.add(R.drawable.test);
        list.add("http://img2.3lian.com/2014/f2/37/d/40.jpg");
        list.add(R.drawable.test1);
        list.add("http://d.3987.com/sqmy_131219/001.jpg");
        list.add(R.drawable.test2);
        list.add("http://img2.3lian.com/2014/f2/37/d/39.jpg");
//        list.add("http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg");
//        list.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        AutoScrollBannerImageAdapter adapter = new AutoScrollBannerImageAdapter(TestAutoScrollBannerActivity.this, list);
        mAutoScrollBanner.setAdapter(adapter);
        mAutoScrollBanner.startTurning(2000);
        mAutoScrollBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
//        mAutoScrollBanner.setPageIndicatorSize(10);//设置指示点的大小
        mAutoScrollBanner.setPageTransformer(Transformer.DefaultTransformer);//翻页动画
        mAutoScrollBanner.setBackgroundColor(Color.rgb(100, 100, 100));
        mAutoScrollBanner.setOnItemClickListener(new AutoScrollBanner.OnItemClickListener() {
            @Override
            public void onItemClick(AutoScrollBannerAdapter parent, View view,
                                    int position, long id) {
                System.out.println("data:"+(parent.getItem(position)));
                System.out.println("onItemClick parent:"+parent+"  view:"+view+"   positeion:"+position+"   id:"+id);
            }
        });
        mAutoScrollBanner.setOnPageChangeListener(new AutoScrollBanner.OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
//				System.out.println("onPageSelected index:"+index);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
//				System.out.println("onPageScrolled arg0:"+arg0+"   arg1:"+arg1+"   arg2:"+arg2);
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
//				System.out.println("onPageScrollStateChanged arg0:"+arg0);
            }
        });
    }
}
