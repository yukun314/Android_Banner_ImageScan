/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.util;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class Setting {
	private static Setting mSetting = null;

	public static Setting getInstance(){
		if(mSetting == null){
			mSetting = new Setting();
		}
		return mSetting;
	}

	private DisplayImageOptions options;
	private ImageLoaderConfiguration config;

	public DisplayImageOptions getOptions(){
		if(options == null){
			options = new DisplayImageOptions.Builder()
					//		.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
					//		.showImageForEmptyUri(R.drawable.ic_error) // 设置图片Uri为空或是错误的时候显示的图片
					//		.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
					.resetViewBeforeLoading(false) // default 设置图片在加载前是否重置、复位
					.delayBeforeLoading(100) // 下载前的延迟时间
					.cacheInMemory(false) // default 设置下载的图片是否缓存在内存中
					.cacheOnDisk(true) // default 设置下载的图片是否缓存在SD卡中
							// .preProcessor(...)
							// .postProcessor(...)
							// .extraForDownloader(...)
					.considerExifParams(false) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //
							// default 设置图片以如何的编码方式显示
					.bitmapConfig(Config.ARGB_4444) // default 设置图片的解码类型
							// .decodingOptions(...) // 图片的解码设置
							// .displayer(new SimpleBitmapDisplayer()) // default
							// 还可以设置圆角图片new RoundedBitmapDisplayer(20)
					.handler(new Handler()) // default
					.build();
		}
		return options;
	}

	public ImageLoaderConfiguration getConfig(Context context){
		if(config == null){
			ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
					context);
			// builder.memoryCacheExtraOptions(480, 800); // default = device screen
			// dimensions 内存缓存文件的最大长宽
			// .diskCacheExtraOptions(480, 800, null) // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
			builder.threadPoolSize(3); // default 线程池内加载的数量
			builder.threadPriority(Thread.NORM_PRIORITY - 2); // default 设置当前线程的优先级
			builder.tasksProcessingOrder(QueueProcessingType.FIFO); // default
			builder.denyCacheImageMultipleSizesInMemory();
			builder.memoryCache(new LruMemoryCache(2 * 1024 * 1024)); // 可以通过自己的内存缓存实现
			builder.memoryCacheSize(2 * 1024 * 1024); // 内存缓存的最大值
			builder.memoryCacheSizePercentage(13); // default
			File file = getSDPath();
			if (file != null) {
				builder.diskCache(new UnlimitedDiskCache(file)); // default
				// 可以自定义缓存路径
			}
			builder.diskCacheSize(50 * 1024 * 1024); // 50 Mb sd卡(本地)缓存的最大值
			builder.diskCacheFileCount(100); // 可以缓存的文件数量
			// default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
			builder.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
			builder.imageDownloader(new BaseImageDownloader(context)); // default
			builder.imageDownloader(new BaseImageDownloader(context, 5 * 1000,
					30 * 1000)); // connectTimeout (5 s), readTimeout (30 s)超时时间
//			builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple()); // default
			builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple()); // default
			builder.writeDebugLogs(); // 打印debug log
			config = builder.build(); // 开始构建
		}
		return config;
	}

	private File getSDPath() {
		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
//			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//		}
		String path = Paths.cardDirectory();
//		System.out.println("path:"+path);
		sdDir = new File(path, "filmTelevision/cache");
		if (!sdDir.exists()) {
			sdDir.mkdirs();
		}
//		System.out.println("sdDir:"+sdDir.getAbsolutePath());
		return sdDir;
	}
}
