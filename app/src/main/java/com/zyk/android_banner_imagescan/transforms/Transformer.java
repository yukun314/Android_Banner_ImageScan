/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.transforms;

public enum Transformer {
    DefaultTransformer("DefaultTransformer"), AccordionTransformer(
            "AccordionTransformer"), BackgroundToForegroundTransformer(
            "BackgroundToForegroundTransformer"), CubeInTransformer(
            "CubeInTransformer"), CubeOutTransformer("CubeOutTransformer"), DepthPageTransformer(
            "DepthPageTransformer"), FlipHorizontalTransformer(
            "FlipHorizontalTransformer"), FlipVerticalTransformer(
            "FlipVerticalTransformer"), ForegroundToBackgroundTransformer(
            "ForegroundToBackgroundTransformer"), RotateDownTransformer(
            "RotateDownTransformer"), RotateUpTransformer(
            "RotateUpTransformer"), StackTransformer("StackTransformer"), TabletTransformer(
            "TabletTransformer"), ZoomInTransformer("ZoomInTransformer"), ZoomOutSlideTransformer(
            "ZoomOutSlideTransformer"), ZoomOutTranformer(
            "ZoomOutTranformer");

    private final String className;

    // ������Ĭ��Ҳֻ����private, �Ӷ���֤���캯��ֻ�����ڲ�ʹ��
    Transformer(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
