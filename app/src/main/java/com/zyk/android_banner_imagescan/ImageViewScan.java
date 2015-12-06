/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan;
import java.lang.reflect.Field;
import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.polites.android.GestureImageView;
import com.zyk.android_banner_imagescan.transforms.Transformer;
import com.zyk.android_banner_imagescan.util.Setting;
import com.zyk.android_banner_imagescan.widget.AbstractViewPagerAdapter;
import com.zyk.android_banner_imagescan.widget.AutoScrollBannerAdapter;
import com.zyk.android_banner_imagescan.widget.ScanViewPager;
import com.zyk.android_banner_imagescan.widget.ViewPagerScroller;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

/**
 * 异步图片浏览
 *
 * @author zyk
 *
 */
public class ImageViewScan extends LinearLayout {

    private ScanViewPager viewPager;
    private MyAdapter mAdapter;
    private FrameLayout bg;
    private RelativeLayout mMenuTop, mMenuBottom;
    private AnimationSet mMenuAnimationIn, mMenuAnimationOut;
    private Context mContext;
    // 上下的menu裁断是否点击才显示
    private boolean isHide = false;

    private Bitmap[] pics;
    private Bitmap mCurrentBitmap;
    private int mCurrentPosition = 0;
    private int mTotal=0;

    private OnPageChangeListener mPageChangeListener = null;

    /**
     * The listener that receives notifications when an item is clicked.
     */
    OnItemClickListener mOnItemClickListener = null;

    public ImageViewScan(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public ImageViewScan(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    // public ImageViewScan(Context context, AttributeSet attrs,
    // int defStyleAttr) {
    // super(context, attrs, defStyleAttr);
    // mContext = context;
    // init(context);
    // }

    @SuppressWarnings("deprecation")
    public void setData(List<Object> list) {
        mTotal = list.size();
        pics = new Bitmap[mTotal];
        mAdapter = new MyAdapter(list);
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new PageChangeListener());
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mPageChangeListener = listener;
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public void setPageTransformer(PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
    }

    /**
     * 自定义翻页动画效果，使用已存在的效果
     *
     * @param transformer
     * @return
     */
    public void setPageTransformer(Transformer transformer) {
        try {
            viewPager
                    .setPageTransformer(
                            true,
                            (PageTransformer) Class.forName(
                                    "com.zyk.android_banner_imagescan.transforms."
                                            + transformer.getClassName())
                                    .newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the background to a given Drawable, or remove the background. If the
     * background has padding, this View's padding is set to the background's
     * padding. However, when a background is removed, this View's padding isn't
     * touched. If setting the padding is desired, please use setPadding(int,
     * int, int, int).
     *
     * @param background
     *            The Drawable to use as the background, or null to remove the
     *            background
     */
    public void setBackground(Drawable background) {
        bg.setBackground(background);
    }

    /**
     * Sets the background color for this view.
     */
    public void setBackgroundColor(int color) {
        bg.setBackgroundColor(color);
    }

    /**
     * Set the background to a given resource. The resource should refer to a
     * Drawable object or 0 to remove the background.
     */
    public void setBackgroundResource(int resid) {
        bg.setBackgroundResource(resid);
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener
     *            The callback that will be invoked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 当前viewPager的Item Id
     * @return
     */
    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * viewPager item的总数
     * @return
     */
    public int getItemNum(){
        return mTotal;
    }

    /**
     * 当前viewPager显示的Bitmap对象
     * @return
     */
    public Bitmap getCurrentBitmap() {
        return mCurrentBitmap;
    }

    /**
     * 设置显示在上部的Menu
     * @param topMenu
     */
    public void setMenuTop(View topMenu){
        if(mMenuTop != null){
            mMenuTop.removeAllViews();
            mMenuTop.addView(topMenu);
        }
    }

    /**
     * 设置显示在下部的Menu
     * @param bottomMenu
     */
    public void setMenuBottmp(View bottomMenu){
        if(mMenuBottom != null){
            mMenuBottom.removeAllViews();
            mMenuBottom.addView(bottomMenu);
        }
    }

    private void init() {
        bg = new FrameLayout(mContext);
        bg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        viewPager = new ScanViewPager(mContext);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        bg.addView(viewPager);

        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
//    	rl.setBackgroundColor(Color.argb(150,123,123,123));
        bg.addView(rl);

        float density = getDensity();
//    	System.out.println("density:"+density);
        int height = Math.round(50*density);
        mMenuTop = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mMenuTop.setLayoutParams(lp);
//		mMenuTop.setBackgroundColor(Color.argb(120,234,123,211));
        rl.addView(mMenuTop);

        mMenuBottom = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,height);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mMenuBottom.setLayoutParams(lp1);
//		mMenuBottom.setBackgroundColor(Color.argb(120,123,123,211));
        rl.addView(mMenuBottom);

        this.addView(bg);
        initViewPagerScroll();
        ImageLoader.getInstance().init(Setting.getInstance().getConfig(mContext));
    }

    private float getDensity(){
        if(mContext instanceof Activity){
            DisplayMetrics metric = new DisplayMetrics();
            ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5/2.0/3.0）
        }
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 设置 动画的时间
     * */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    viewPager.getContext());
            scroller.setScrollDuration(1000);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public interface OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0);

        public void onPageScrolled(int arg0, float arg1, int arg2);

        public void onPageSelected(int index);
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to
         * access the data associated with the selected item.
         *
         * @param parent
         *            The AdapterView where the click happened.
         * @param view
         *            The view within the AdapterView that was clicked (this
         *            will be a view provided by the adapter)
         * @param position
         *            The position of the view in the adapter.
         * @param id
         *            The row id of the item that was clicked.
         */
        void onItemClick(AutoScrollBannerAdapter parent, View view,
                         int position, long id);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (mPageChangeListener != null) {
                mPageChangeListener.onPageScrollStateChanged(arg0);
            }
            if (mMenuTop.getVisibility() == View.VISIBLE && isHide) {
                if (mMenuAnimationIn == null) {
                    initAnimationIn();
                }
                mMenuTop.startAnimation(mMenuAnimationIn);
                mMenuBottom.startAnimation(mMenuAnimationIn);
                mMenuTop.setVisibility(View.GONE);
                mMenuBottom.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (mPageChangeListener != null) {
                mPageChangeListener.onPageScrolled(arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPosition=position;
            mCurrentBitmap=pics[mCurrentPosition];
            if (mPageChangeListener != null) {
                mPageChangeListener.onPageSelected(position);
            }
        }

    }

    private void initAnimationIn() {
        mMenuAnimationIn = new AnimationSet(true);
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        TranslateAnimation animation1 = new TranslateAnimation(1.0f, 1.0f,
                0.0f, 1.0f);
        mMenuAnimationIn.addAnimation(animation);
        mMenuAnimationIn.addAnimation(animation1);
        mMenuAnimationIn.setDuration(300);
    }

    private void initAnimationOut() {
        mMenuAnimationOut = new AnimationSet(true);
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        TranslateAnimation animation1 = new TranslateAnimation(1.0f, 1.0f,
                1.0f, 0.0f);
        mMenuAnimationOut.addAnimation(animation);
        mMenuAnimationOut.addAnimation(animation1);
        mMenuAnimationOut.setDuration(300);
    }

    private void onSingleTap() {
        if (isHide) {
            if (mMenuTop.getVisibility() == View.VISIBLE) {
                if (mMenuAnimationIn == null) {
                    initAnimationIn();
                }
                mMenuTop.startAnimation(mMenuAnimationIn);
                mMenuBottom.startAnimation(mMenuAnimationIn);
                mMenuTop.setVisibility(View.GONE);
                mMenuBottom.setVisibility(View.GONE);
            } else {
                if (mMenuAnimationOut == null) {
                    initAnimationOut();
                }
                mMenuBottom.startAnimation(mMenuAnimationOut);
                mMenuTop.startAnimation(mMenuAnimationOut);
                mMenuBottom.setVisibility(View.VISIBLE);
                mMenuTop.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * true Menu菜单是否单击时才显示
     */
    public void setHide(boolean isHide) {
        this.isHide = isHide;
    }

    private class MyAdapter extends AbstractViewPagerAdapter<Object> {

        public MyAdapter(List<Object> data) {
            super(data);
        }

        @Override
        public View getView(int position) {
            FrameLayout fl = new FrameLayout(mContext);
            fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

            GestureImageView iv = new GestureImageView(mContext);
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            iv.setScaleType(ScaleType.CENTER_INSIDE);
            fl.addView(iv);

            RelativeLayout rl = new RelativeLayout(mContext);
            rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
            fl.addView(rl);

            TextView mImageInfo = new TextView(mContext);
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.CENTER_IN_PARENT);
            mImageInfo.setLayoutParams(lp2);
            mImageInfo.setTextColor(Color.WHITE);
            mImageInfo.setTextSize(8*getDensity());
            rl.addView(mImageInfo);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSingleTap();
                }
            });
            iv.setOnMovingListener(viewPager);
            Object data = mData.get(position);
            try {
                if (data instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) data;
                    iv.setImageBitmap(bitmap);
                    pics[position]=bitmap;
                    if(position==mCurrentPosition){
                        mCurrentBitmap=bitmap;
                    }
                } else if (data instanceof Integer) {
                    int id = (Integer) data;
                    iv.setImageResource(id);
                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
                    pics[position]=bitmap;
                    if(position==mCurrentPosition){
                        mCurrentBitmap=bitmap;
                    }
                } else if (data instanceof String) {
                    ImageLoader.getInstance().displayImage((String) data, iv,Setting.getInstance().getOptions(),new MyImageLoadingListener(position,mImageInfo));
                } else {
                    throw new IllegalStateException(this.getClass().getName()
                            + " data 只能是 Bitmap Integer String 之一："
                            + data.getClass());
                }
            } catch (IllegalStateException e) {
                ImageLoader.getInstance().init(Setting.getInstance().getConfig(mContext));
                ImageLoader.getInstance().displayImage((String) data, iv,Setting.getInstance().getOptions(),new MyImageLoadingListener(position,mImageInfo));
            }
            return fl;
        }

    }

    private class MyImageLoadingListener implements ImageLoadingListener{
        private int position ;
        private TextView messageView;
        MyImageLoadingListener(int position,TextView message){
            this.position = position;
            this.messageView = message;
        }



        @Override
        public void onLoadingStarted(String imageUri, View view) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText("正在加载,请稍等...");
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason) {
            String message ="";
            switch (failReason.getType()) {
                case IO_ERROR:
                    message = "I/O错误";
                    break;
                case DECODING_ERROR:
                    message = "图片解析错误";
                    break;
                case NETWORK_DENIED:
                    message = "无法加载图片";
                    break;
                case OUT_OF_MEMORY:
                    message = "内存溢出";
                    break;
                case UNKNOWN:
                    message = "未知错误";
                    break;
            }
            messageView.setText("失败！"+message);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            pics[position]=loadedImage;
            if(position==mCurrentPosition){
                mCurrentBitmap=loadedImage;
            }

            messageView.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            messageView.setVisibility(View.GONE);
        }

    }
}
