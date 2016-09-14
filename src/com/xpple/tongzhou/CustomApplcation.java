package com.xpple.tongzhou;


import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xpple.tongzhou.CustomApplcation;
import android.app.Application;
import android.content.Context;

/**
 * 自定义全局Applcation类
 * @ClassName: CustomApplcation
 */
public class CustomApplcation extends Application {

	public static CustomApplcation mInstance;
	
	private int id;
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		initImageLoader(getApplicationContext());
	}
	public static CustomApplcation getInstance() {
		return mInstance;
	}
	public static void initImageLoader(Context context) {
		// 获取缓存图片目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"qebb/imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPriority(Thread.MAX_PRIORITY - 1)
		.diskCache(new UnlimitedDiscCache(cacheDir))
		.tasksProcessingOrder(QueueProcessingType.FIFO)// 设置图片下载和显示的工作队列排序
		.denyCacheImageMultipleSizesInMemory() 
		//		.memoryCacheExtraOptions(400, 800)
		.threadPoolSize(2)
		.diskCacheSize(40 * 1024 * 1024)
		//		.memoryCacheSize(1 * 1024 * 1024)  
		//		.memoryCache(new FIFOLimitedMemoryCache())  
		//		.memoryCacheSize(3 * 1024 * 1024)  
		//		.memoryCacheExtraOptions(480, 800) // default = device screen dimensions  
		//		.diskCacheExtraOptions(320, 480, null) 
		.imageDownloader(new BaseImageDownloader(context, 10 * 1000, 30 * 1000)) .build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
