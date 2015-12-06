/*
 * Copyright (c) 2015.
 * 创建:朱玉坤
 * 时间:2015-12-06
 */

package com.zyk.android_banner_imagescan.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import android.os.Environment;

public class Paths {

	public static String cardDirectory() {
		File file = null;
		String str = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(str))
			file = Environment.getExternalStorageDirectory(); 
		file = checkPresetDirectory(file, null);
		return file.getPath();
	}

	public static File checkPresetDirectory(File file, String path) {
		if(path == null)
			path = "";
		else if(path.length() > 0 && path.charAt(0) != '/')
			path = "/" + path;
		if(file == null || !file.exists()){
			try {
				if(Environment.MEDIA_MOUNTED.equals(Environment.class.getMethod("getInternalStorageState").invoke(null))){
					file = (File) Environment.class.getMethod("getInternalStorageDirectory").invoke(null);
					file = new File(file.getPath() + path);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		if(file == null || !file.exists())
			file = new File("/mnt/emmc" + path);
		if(file == null || !file.exists())
			file = new File("/storage/sdcard1" + path);
		if(file == null || !file.exists())
			file = new File("/mnt/extsdcard" + path);
		return file;
	}
}
