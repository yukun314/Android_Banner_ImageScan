/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.transforms;

import android.view.View;

public class FlipHorizontalTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		final float rotation = 180f * position;

		view.setAlpha(rotation > 90f || rotation < -90f ? 0 : 1);
		view.setPivotX(view.getWidth() * 0.5f);
		view.setPivotY(view.getHeight() * 0.5f);
		view.setRotationY(rotation);
	}

}
