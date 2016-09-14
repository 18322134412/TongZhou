package com.xpple.tongzhou;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;


public class ImageDownLoader {


	public static void showLocationImage(String path,
			final ImageView imageView,final int loadingImg){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(loadingImg)
		.showImageForEmptyUri(loadingImg)
		.showImageOnFail(loadingImg)
		.cacheInMemory(true)
		.cacheOnDisk(false)
		//		.considerExifParams(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示  
		////			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
		.bitmapConfig(Bitmap.Config.RGB_565)//设置 图片的解码类�?//
		.resetViewBeforeLoading(true)
		//		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示  
		//		.resetViewBeforeLoading(true)
		//			.bitmapConfig(Bitmap.Config.RGB_565)//设置 图片的解码类�?//
		//设置图片在下载前是否重置，复�?
		.build();
		//
		String imageUrl = Scheme.FILE.wrap(path);
		//String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options);

	}
}