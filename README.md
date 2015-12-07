# Android_Banner_ImageScan
本项目包含两大部分：
1.图片浏览

2.自动轮播Banner
    无限循环轮播(头->尾->头->尾...)，可以设置自动翻页的时间，手指触碰则暂停翻页，离开自动开始翻页，
    并且提供多种翻页特效。使用起来非常简单(和ListView相似度很高)，网络图片会自动缓存到sdCard上等。

    效果图：
    ![image](https://github.com/yukun314/Android_Banner_ImageScan/raw/master/preview/banner.gif)


使用了三个开源项目:
    1.Android_Universal_Image_Loader 异步加载图片。原项目地址：https://github.com/nostra13/Android-Universal-Image-Loader
    2.GestureImageView 可缩放的ImageView。原项目地址：https://github.com/jasonpolites/gesture-imageview
    3.自动轮播Banner，项目地址记不得了。Banner轮播就是在此项目上做了些修改