package com.xpple.tongzhou.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Partners extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String belongId;
	private String Role;
    private String Position;
    private String Salay;
    private float Equity;
    public String getbelongId() {
	    return belongId;
    }
    public String getRole() {
	    return Role;
    }
    public String getPosition() {
	    return Position;
    }
    public String getSalay() {
	    return Salay;
    }
    public float getEquity() {
	    return Equity;
    }
    public void setbelongId(String belongId) {
    	this.belongId = belongId;
    }
    public void setRole(String Role) {
    	this.Role = Role;
    }
    public void setPosition(String Position) {
    	this.Position = Position;
    }
    public void setSalay(String Salay) {
    	this.Salay= Salay;
    }
    public void setEquity(float Equity) {
    	this.Equity = Equity;
    }
}
