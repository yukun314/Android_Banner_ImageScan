# Android_Banner_ImageScan
##本项目包含两大部分：
###1.图片浏览
基于ViewPager实现的图片浏览器。解决了ViewPager与图片放大的事件冲突。可以浏览资源Id的图片、Bitmap的图片、网络图片(异步加载)。图片可放缩(双击放大、两手指滑动可实现放缩)。可以根据自己的需要添加显示在最上面和最下面的Menu菜单，也可以设置Menu菜单的显示方式(单击时显示或一直显示)。和下面的轮播Banner一样也提供了16种图片切换动画，当然也可以自定义的啦。<br/>
效果图:由于各种原因gif的效果和真实的效果差别也挺大的<br/>
![](https://github.com/yukun314/Android_Banner_ImageScan/raw/master/preview/imagescan.gif)
<br/>
```Java
mImageViewScan = (ImageViewScan) findViewById(R.id.activity_image_scan);
List<Object> list = new ArrayList<Object>();
list.add(R.drawable.test);
list.add("http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg");
//......
mImageViewScan.setData(list);
mImageViewScan.setPageTransformer(Transformer.DefaultTransformer);//设置切换动画
//......
View menuTop = LayoutInflater.from(this).inflate(R.layout.menu_top,null);
//......
mImageViewScan.setMenuTop(menuTop);//设置显示在上面的Menu
mImageViewScan.setMenuBottmp(menuBottom);//设置显示在下面的Menu
//......
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <com.zyk.android_banner_imagescan.ImageViewScan
        android:id="@+id/activity_image_scan"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</RelativeLayout>
```
###2.自动轮播Banner
无限循环轮播(头->尾->头->尾...)，可以设置自动翻页的时间，手指触碰则暂停翻页，离开自动开始翻页，并且提供多种翻页特效(16种，自己去尝试下吧，也可以自定义哦)。使用起来非常简单(和ListView相似度很高)，网络图片会自动缓存到sdCard上等。<br>
效果图:<br/>
![](https://github.com/yukun314/Android_Banner_ImageScan/raw/master/preview/banner.gif )
<br>
```Java
List<Object> list = new ArrayList<Object>();
list.add(R.drawable.test);
list.add("http://img2.3lian.com/2014/f2/37/d/40.jpg");
//这里list可以为Bitmap类型的图片、资源中的图片id、网络图片的URL
mAutoScrollBanner = (AutoScrollBanner) findViewById(R.id.activity_main_autoscrollbanner);
AutoScrollBannerImageAdapter adapter = new AutoScrollBannerImageAdapter(TestAutoScrollBannerActivity.this, list);
mAutoScrollBanner.setAdapter(adapter);
mAutoScrollBanner.startTurning(2000);//设置动画的时间
mAutoScrollBanner.setPageTransformer(Transformer.DefaultTransformer);//翻页动画
//其他的设置以及setOnItemClickListener、setOnPageChangeListener事件监听等就看test中的例子
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.zyk.android_banner_imagescan.AutoScrollBanner
        android:id="@+id/activity_main_autoscrollbanner"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>

</RelativeLayout>
```

    看到这里有没有感觉这两个东东使用起来也太简单了吧，没错就是这么简单...
        
###Thanks:
    1. Android_Universal_Image_Loader 异步加载图片。[项目地址](https://github.com/nostra13/Android-Universal-Image-Loader)
    2. GestureImageView 可缩放的ImageView。[项目地址](https://github.com/jasonpolites/gesture-imageview)
    3. 自动轮播Banner，项目地址记不得了(抱歉)。Banner轮播就是在此项目上做了些修改
