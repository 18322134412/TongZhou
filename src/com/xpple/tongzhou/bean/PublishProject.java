package com.xpple.tongzhou.bean;

import java.io.Serializable;

import com.xpple.tongzhou.view.GetEditText;

import android.R.string;
import android.widget.EditText;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class PublishProject extends BmobObject implements Serializable {
    /**
	 * ²ÜÁÖ
	 */
	private static final long serialVersionUID = 1L;
	private String programName;
    private BmobFile programfile;//ÎÄ¼þ
    private String areaName;
    private String orientationName;
    private String highLights;
    private String pictureWord;
    private String Introduction;
    private String Competitive;
    private String ProspectTime;
    private String ProspectValue;
    private String ProspectDescript;
    private int TeamNumber;
	private int TotalNumber;
	private String ProjectPhase;
	private String FinancingStage;
	private String ProductAndroid;
	private String ProductApple;
	private String ProductCumputer;
	private String CallDeclaration;
	public String getCallDeclaration() {
        return CallDeclaration;
    }
	public String getProductAndroid() {
        return ProductAndroid;
    }
	public String getProductApple() {
        return ProductApple;
    }
	public String getProductCumputer() {
        return ProductCumputer;
    }
	public int getTeamNumber() {
	    return TeamNumber;
    }
    public int getTotalNumber(){
    	return TotalNumber;
    }
    public String getProjectPhase() {
        return ProjectPhase;
    }
    public String getFinancingStage() {
        return FinancingStage;
    }
    public String getIntroduction() {
        return Introduction;
    }
    public String getCompetitive(){
    	return Competitive;
    }
    public String getProspectTime() {
        return ProspectTime;
    }
    public String getProspectValue() {
        return ProspectValue;
    }
    public String getProspectDescript() {
        return ProspectDescript;
    }
    public String getProgramName() {
        return programName;
    }
    public BmobFile getBmobFile(){
    	return programfile;
    }
    public String getAreaname() {
        return areaName;
    }
    public String getOrientationName() {
        return orientationName;
    }
    public String getHighlights() {
        return highLights;
    }
    public String getPictureWord() {
        return pictureWord;
    }
    public String getProgramfile() {
        return programName;
    }
    public void setProgramNmae(String programName) {
        this.programName = programName;
    }
    public void setProgramFile(BmobFile programfile){
    	this.programfile=programfile;
    }
    
    public void setProgramfile(String programName) {
        this.programName = programName;
    }
    public void setAreaname(String areaName) {
    	this.areaName = areaName;
    }
    public void setOrientationName(String orientationName) {
    	this.orientationName = orientationName;
    }
    public void setHighlights(String highLights) {
    	this.highLights = highLights;
    }
    public void setPictureWord(String pictureWord) {
    	this.pictureWord = pictureWord;
    }
    public void setIntroduction(String Introduction) {
    	this.Introduction = Introduction;
    }
    public void setCompetitive(String Competitive) {
    	this.Competitive = Competitive;
    }
    public void setProspectTime(String ProspectTime) {
    	this.ProspectTime = ProspectTime;
    }
    public void setProspectValue(String ProspectValue) {
    	this.ProspectValue = ProspectValue;
    }
    public void setProspectDescript(String ProspectDescript) {
    	this.ProspectDescript = ProspectDescript;
    }
	public void setTeamNumber(int TeamNumber) {
		// TODO Auto-generated method stub
		this.TeamNumber=TeamNumber;
	}
	public void setTotalNumber(int TotalNumber) {
		// TODO Auto-generated method stub
		this.TotalNumber=TotalNumber;
	}
	public void setProjectPhase(String ProjectPhase) {
		// TODO Auto-generated method stub
		this.ProjectPhase=ProjectPhase;
	}
	public void setFinancingStage(String FinancingStage) {
		// TODO Auto-generated method stub
		this.FinancingStage=FinancingStage;
	}
	 public void setProductAndroid(String ProductAndroid) {
	        this.ProductAndroid = ProductAndroid;
    }
	 public void setProductApple(String ProductApple) {
	        this.ProductApple = ProductApple;
    }
	 public void setProductCumputer(String ProductCumputer) {
	        this.ProductCumputer = ProductCumputer;
     }
	 public void setCallDeclaration(String CallDeclaration) {
	        this.CallDeclaration = CallDeclaration;
  }
}