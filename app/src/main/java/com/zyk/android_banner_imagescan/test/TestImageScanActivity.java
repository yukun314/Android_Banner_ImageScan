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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyk.android_banner_imagescan.ImageViewScan;
import com.zyk.android_banner_imagescan.R;
import com.zyk.android_banner_imagescan.transforms.Transformer;
import com.zyk.android_banner_imagescan.widget.AutoScrollBannerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageViewScan 使用的Demo
 */
public class TestImageScanActivity extends AppCompatActivity {

    private ImageViewScan mImageViewScan;
    private TextView mShowInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_scan);
        init();
    }

    private void init() {
        mImageViewScan = (ImageViewScan) findViewById(R.id.activity_image_scan);
        List<Object> list = new ArrayList<Object>();
        list.add(R.drawable.test);
        list.add("http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg");
        list.add(R.drawable.test1);
        list.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        list.add(R.drawable.test2);
        mImageViewScan.setData(list);
        mImageViewScan.setPageTransformer(Transformer.DefaultTransformer);
        mImageViewScan.setBackgroundColor(Color.rgb(100, 100, 100));

        mImageViewScan.setOnItemClickListener(new ImageViewScan.OnItemClickListener() {
            @Override
            public void onItemClick(AutoScrollBannerAdapter parent, View view,
                                    int position, long id) {
                System.out.println("data:"+(parent.getItem(position)));
                System.out.println("onItemClick parent:"+parent+"  view:"+view+"   positeion:"+position+"   id:"+id);
            }
        });
        mImageViewScan.setOnPageChangeListener(new ImageViewScan.OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
//				System.out.println("onPageSelected index:"+index+"  :"+mImageViewScan.getCurrentPosition());
                mShowInfo.setText(" "+(mImageViewScan.getCurrentPosition()+1)+"/"+mImageViewScan.getItemNum()+" ");
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
        initMenu();
    }

    private void initMenu(){
        View menuTop = LayoutInflater.from(this).inflate(R.layout.menu_top,null);
        ImageView close = (ImageView) menuTop.findViewById(R.id.menu_top_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TestImageScanActivity.this.finish();
            }
        });
        mShowInfo = (TextView) menuTop.findViewById(R.id.menu_top_text);
        mShowInfo.setText(" "+(mImageViewScan.getCurrentPosition()+1)+"/"+mImageViewScan.getItemNum()+" ");
        mImageViewScan.setMenuTop(menuTop);

        View menuBottom = LayoutInflater.from(this).inflate(R.layout.menu_bottom,null);
        ImageView share = (ImageView) menuBottom.findViewById(R.id.scan_image_menu_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestImageScanActivity.this,"position:"+mImageViewScan.getCurrentPosition()+" bitmap:"+mImageViewScan.getCurrentBitmap(),Toast.LENGTH_SHORT).show();;
            }
        });

        ImageView download = (ImageView) menuBottom.findViewById(R.id.scan_image_menu_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestImageScanActivity.this,"position:"+mImageViewScan.getCurrentPosition()+" bitmap:"+mImageViewScan.getCurrentBitmap(),Toast.LENGTH_SHORT).show();;
            }
        });

        mImageViewScan.setMenuBottmp(menuBottom);
    }

}
