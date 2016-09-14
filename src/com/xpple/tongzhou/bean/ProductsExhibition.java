package com.xpple.tongzhou.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ProductsExhibition extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String belongId;
	BmobFile ImagePicture;
	public String getbelongId() {
	    return belongId;
    }
	public BmobFile getImagePicture() {
	    return ImagePicture;
    }
	public void setbelongId(String belongId) {
    	this.belongId = belongId;
}
    public void setImagePicture(BmobFile ImagePicture) {
	    	this.ImagePicture = ImagePicture;
	}
}
