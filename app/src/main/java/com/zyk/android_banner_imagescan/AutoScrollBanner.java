/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zyk.android_banner_imagescan.transforms.Transformer;
import com.zyk.android_banner_imagescan.widget.AutoScrollBannerAdapter;
import com.zyk.android_banner_imagescan.widget.LoopViewPager;
import com.zyk.android_banner_imagescan.widget.ViewPagerScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by zyk on 2015/12/6.
 */
public class AutoScrollBanner extends LinearLayout{
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private LoopViewPager viewPager;
    private AutoScrollBannerAdapter mAdapter;
    private RelativeLayout bg;
    private int[] page_indicatorId ;//下方的小圆圈
    private PageChangeListener pageChangeListener;
    private ViewGroup loPageTurningPoint;
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    //指示点的大小
    private int side = 0;

    private OnPageChangeListener mPageChangeListener = null;

    /**
     * The listener that receives notifications when an item is clicked.
     */
    OnItemClickListener mOnItemClickListener = null;

    private Handler timeHandler = new Handler();
    private Runnable adSwitchTask = new Runnable() {
        @Override
        public void run() {
            if (viewPager != null && turning) {
                int page = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem(page);
                timeHandler.postDelayed(adSwitchTask, autoTurningTime);
            }
        }
    };

    public AutoScrollBanner(Context context) {
        super(context);
        init(context);
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public AutoScrollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

//	public AutoScrollBanner(Context context, AttributeSet attrs,
//			int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		init(context);
//	}

//	public AutoScrollBanner(Context context, AttributeSet attrs,
//			int defStyleAttr, int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//		init(context);
//	}

    private long starTime = 0;
    private int mLastMotionX, mLastMotionY;
    private boolean isMoved;
    //移动的阈值
    private final int TOUCH_SLOP = 20;
    //超过时间间隔将不能触发自定义的itemClick事件
    private final int TIME_SLOP = 1000;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 开始翻页
            if (canTurn)startTurning(autoTurningTime);
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn)stopTurning();
        }

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                starTime = System.currentTimeMillis();
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
//			System.out.println("time:"+(System.currentTimeMillis() - starTime <= TIME_SLOP)+"   isMoved:"+
//		(!isMoved)+"  mOnItemClickListener:"+(mOnItemClickListener != null)+"   mAdapter:"+(mAdapter != null));
                if(System.currentTimeMillis() - starTime <= TIME_SLOP && !isMoved && mOnItemClickListener != null && mAdapter != null){
                    int position = viewPager.getCurrentItem();
                    mOnItemClickListener.onItemClick(mAdapter, viewPager,position ,mAdapter.getItemId(position));
                    return true;
                }
                isMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if( isMoved ) break;
                if( Math.abs(mLastMotionX-x) > TOUCH_SLOP
                        || Math.abs(mLastMotionY-y) > TOUCH_SLOP ) {
                    //移动超过阈值，则表示移动了
                    isMoved = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void stopTurning() {
        turning = false;
        timeHandler.removeCallbacks(adSwitchTask);
    }

    public void startTurning(long autoTurningTime) {
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        timeHandler.postDelayed(adSwitchTask, autoTurningTime);
    }

    public void setAdapter(AutoScrollBannerAdapter adapter){
        mAdapter = adapter;
        viewPager.setAdapter(adapter);
        viewPager.setBoundaryCaching(true);
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
    }

    public void setPageIndicator(int[] page_indicatorId){
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        PagerAdapter adapter = viewPager.getAdapter();
        int total = 0;
        if(adapter != null){
            total = adapter.getCount();
        }
        for (int count = 0; count < total; count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            if(side > 0){
                LayoutParams lp = new LayoutParams(side,side);
//    			lp.height = side;
//    			lp.width = side;
                pointView.setLayoutParams(lp);
            }
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty())
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
        pageChangeListener = new PageChangeListener(mPointViews,
                page_indicatorId);
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    //设置指示点的大小
    public void setPageIndicatorSize(int side){
        this.side = side;
        for(ImageView iv:mPointViews){
            iv.setLayoutParams(new LayoutParams(side,side));
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener){
        this.mPageChangeListener = listener;
    }

    /**
     * 设置底部指示器是否可见
     */
    public void setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public void setPageTransformer(ViewPager.PageTransformer transformer) {
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
                            (ViewPager.PageTransformer) Class.forName(
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
     * @param background The Drawable to use as the background, or null to remove the background
     */
    public void setBackground(Drawable background){
        //setBackground Add in API Level 16
//        bg.setBackground(background);
        bg.setBackgroundDrawable(background);
    }

    /**
     * Sets the background color for this view.
     */
    public void setBackgroundColor(int color){
        bg.setBackgroundColor(color);
    }

    /**
     * Set the background to a given resource. The resource should refer to a
     * Drawable object or 0 to remove the background.
     */
    public void setBackgroundResource(int resid){
        bg.setBackgroundResource(resid);
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

//	private void init(Context context){
////		int layout = getResources().getIdentifier("include_viewpager", "layout", context.getPackageName());
//		View hView = LayoutInflater.from(context).inflate(
//                R.layout.include_viewpager,null);
////		View hView = LayoutInflater.from(context).inflate(layout,null);
//        viewPager = (LoopViewPager) hView.findViewById(R.id.loopViewPager);
//        loPageTurningPoint = (ViewGroup) hView
//                .findViewById(R.id.loPageTurningPoint);
//        bg = (RelativeLayout) hView.findViewById(R.id.include_viewpager_bg);
//		initViewPagerScroll();
//		this.addView(hView);
//	}

    private void init(Context context){
//		View hView = LayoutInflater.from(context).inflate(
//                R.layout.include_viewpager,null);
//        viewPager = (LoopViewPager) hView.findViewById(R.id.loopViewPager);
//        loPageTurningPoint = (ViewGroup) hView
//                .findViewById(R.id.loPageTurningPoint);
//        bg = (RelativeLayout) hView.findViewById(R.id.include_viewpager_bg);

        bg = new RelativeLayout(context);
        bg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        viewPager = new LoopViewPager(context);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        bg.addView(viewPager);

        LinearLayout ll = new LinearLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.bottomMargin=15;
        ll.setLayoutParams(lp);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        loPageTurningPoint = ll;
        bg.addView(loPageTurningPoint);

        initViewPagerScroll();
        this.addView(bg);
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

    public interface OnPageChangeListener{
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
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param parent The AdapterView where the click happened.
         * @param view The view within the AdapterView that was clicked (this
         *            will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id The row id of the item that was clicked.
         */
        void onItemClick(AutoScrollBannerAdapter parent, View view, int position, long id);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        private ArrayList<ImageView> pointViews;
        private int[] page_indicatorId;
        public PageChangeListener(ArrayList<ImageView> pointViews,int page_indicatorId[]){
            this.pointViews=pointViews;
            this.page_indicatorId = page_indicatorId;
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
            if(mPageChangeListener != null){
                mPageChangeListener.onPageScrollStateChanged(arg0);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if(mPageChangeListener != null){
                mPageChangeListener.onPageScrolled(arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int index) {
            for (int i = 0; i < pointViews.size(); i++) {
                pointViews.get(index).setImageResource(page_indicatorId[1]);
                if (index != i) {
                    pointViews.get(i).setImageResource(page_indicatorId[0]);
                }
            }
            if(mPageChangeListener != null){
                mPageChangeListener.onPageSelected(index);
            }
        }
    }
}
