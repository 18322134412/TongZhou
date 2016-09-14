package com.xpple.tongzhou.bean;

/**
 * GridView的每个item的数据对�?
 * 
 * @author len
 *
 */
public class ImageBean{
	/**
	 * 文件夹的第一张图片路�?
	 */
	private String topImagePath;
	/**
	 * 文件夹名
	 */
	private String folderName; 
	/**
	 * 文件夹中的图片数
	 */
	private int imageCounts;
	
	public String getTopImagePath() {
		return topImagePath;
	}
	public void setTopImagePath(String topImagePath) {
		this.topImagePath = topImagePath;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getImageCounts() {
		return imageCounts;
	}
	public void setImageCounts(int imageCounts) {
		this.imageCounts = imageCounts;
	}
	
	
	public ImageBean() {
		super();
	}
	public ImageBean(String topImagePath, String folderName, int imageCounts) {
		super();
		this.topImagePath = topImagePath;
		this.folderName = folderName;
		this.imageCounts = imageCounts;
	}
	@Override
	public String toString() {
		return "ImageBean [topImagePath=" + topImagePath + ", folderName="
				+ folderName + ", imageCounts=" + imageCounts + "]";
	}
	
	
}
