/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller {
	private int mScrollDuration = 1200;// �����ٶ�,ֵԽ�󻬶�Խ��������̫���ʹ3dЧ��������

	public ViewPagerScroller(Context context) {
		super(context);
	}

	public ViewPagerScroller(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}

	public ViewPagerScroller(Context context, Interpolator interpolator,
			boolean flywheel) {
		super(context, interpolator, flywheel);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		super.startScroll(startX, startY, dx, dy, mScrollDuration);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy) {
		super.startScroll(startX, startY, dx, dy, mScrollDuration);
	}

	public int getScrollDuration() {
		return mScrollDuration;
	}

	public void setScrollDuration(int scrollDuration) {
		this.mScrollDuration = scrollDuration;
	}
	
}