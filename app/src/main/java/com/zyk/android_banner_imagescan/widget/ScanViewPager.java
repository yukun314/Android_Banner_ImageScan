/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.widget;

import com.polites.android.GestureImageViewTouchListener.OnMovingListener;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScanViewPager extends ViewPager implements OnMovingListener {
	
public ScanViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScanViewPager(Context context) {
		super(context);
	}

	/**  ��ǰ�ӿؼ��Ƿ����϶�״̬  */ 
	private boolean mChildIsBeingDragged=false;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(mChildIsBeingDragged)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public void startDrag() {
		mChildIsBeingDragged=true;
	}

	@Override
	public void stopDrag() {
		mChildIsBeingDragged=false;
	}

}
