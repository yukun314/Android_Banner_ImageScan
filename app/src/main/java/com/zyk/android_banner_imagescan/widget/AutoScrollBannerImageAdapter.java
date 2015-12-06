/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.widget;

import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 *  当图片的URL不正确时会导致当前页会被其他正确的图片填充 而整体有图片重复
 * @author zyk
 *
 */
public class AutoScrollBannerImageAdapter extends AutoScrollBannerAdapter{
	private List<?> list;
	//	private ViewHolder holder;
	private final Context context;

	/**
	 * @param context
	 * @param list 图片的网络地址或Bitmap类型或R.drawable.xxx
	 */
	public AutoScrollBannerImageAdapter(Context context,List<?> list){
		this.context = context;
		this.list = list;
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup container) {
//		if(convertView == null){
////			int layout = context.getResources().getIdentifier("acitivity_image_adapter_item", "layout", context.getPackageName());
//			convertView = LayoutInflater.from(context).inflate(R.layout.acitivity_image_adapter_item,null);
////			convertView = LayoutInflater.from(context).inflate(layout,null);
//			holder = new ViewHolder();
//			holder.image = (ImageView) convertView.findViewById(R.id.activity_image_adapter_image);
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		Object data = list.get(position);
//		try{
//			if(data instanceof Bitmap){
//				holder.image.setImageBitmap((Bitmap)data);
//			}else if(data instanceof Integer){
//				holder.image.setImageResource((Integer)data);
//			}else if(data instanceof String){
//				ImageLoader.getInstance().displayImage((String)data,holder.image);
//			}else{
//				throw new IllegalStateException(this.getClass().getName() +
//                        " data 只能是 Bitmap Integer String 之一：" +data.getClass());
//			}
//		}catch(IllegalStateException e){
//			ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
//			ImageLoader.getInstance().displayImage((String)data,holder.image);
//		}
//		return convertView;
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		if(convertView == null){
			ImageView iv = new ImageView(context);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			convertView = iv;
		}
		Object data = list.get(position);
		try{
			if(data instanceof Bitmap){
				((ImageView)convertView).setImageBitmap((Bitmap)data);
			}else if(data instanceof Integer){
				((ImageView)convertView).setImageResource((Integer)data);
			}else if(data instanceof String){
				ImageLoader.getInstance().displayImage((String)data,((ImageView)convertView));
			}else{
				throw new IllegalStateException(this.getClass().getName() +
						" data 只能是 Bitmap Integer String 之一：" +data.getClass());
			}
		}catch(IllegalStateException e){
			ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
			ImageLoader.getInstance().displayImage((String)data,((ImageView)convertView));
		}
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return list.size();
	}

//	private class ViewHolder{
//		ImageView image;
//	}
}


