package com.xpple.tongzhou.ui;

import android.R.integer;
import android.R.string;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xpple.tongzhou.R;
import com.xpple.tongzhou.bean.Partners;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;

public class SummonPartnersActivity extends BaseActivity {
	
	private Button[] roleButtons=new Button[6];
	private Button[] positionButtons=new Button[3];
	private Button[] SalayButtons=new Button[3];
	private EditText Equity;
	
	private String ptrString;
	private String action;
	private String currRole="";
	private Partners myPartners=new Partners();
	private Partners currPartners=new Partners();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.summon_partners);
	        
	        Equity=(EditText)findViewById(R.id.Equity);
	        
	        roleButtons[0]=(Button)findViewById(R.id.RoleSkill);
	        roleButtons[1]=(Button)findViewById(R.id.RoleSale);
	        roleButtons[2]=(Button)findViewById(R.id.RoleOperate);
	        roleButtons[3]=(Button)findViewById(R.id.RoleDesign);
	        roleButtons[4]=(Button)findViewById(R.id.RoleProduct);
	        roleButtons[5]=(Button)findViewById(R.id.RoleOther);
	        
	        positionButtons[0]=(Button)findViewById(R.id.PositionFull);
	        positionButtons[1]=(Button)findViewById(R.id.PositionParttime);
	        positionButtons[2]=(Button)findViewById(R.id.PositionNegotiable);
	        
	        SalayButtons[0]=(Button)findViewById(R.id.SalaryMarket);
	        SalayButtons[1]=(Button)findViewById(R.id.SalarySecurity);
	        SalayButtons[2]=(Button)findViewById(R.id.SalaryNo);
	        
	        ptrString=getIntent().getStringExtra("ptrString");
	        action=getIntent().getStringExtra("action");
	        if (action.equals("alter")) {  
	        	
	        	myPartners=(Partners)getIntent().getSerializableExtra("currPartner");
	        	currPartners.setRole(myPartners.getRole());
	        	initButton (myPartners.getRole(),roleButtons);
	        	initButton (myPartners.getPosition(),positionButtons);
	        	initButton (myPartners.getSalay(),SalayButtons);
	        	Equity.setText(myPartners.getEquity()+"");
	        	currRole=myPartners.getRole();
			}
	        for(int i=0;i<roleButtons.length;i++){
	        	String string=roleButtons[i].getText().toString().trim();
	        	//&&!string.equals(currRole)
	        	if (ptrString.contains(string)&&!string.equals(currRole)) {
	        		roleButtons[i].setBackgroundResource(R.drawable.button_circle_grey_bg);
	        		roleButtons[i].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							// TODO Auto-generated method stub
							ShowToast("该角色已经添加!!!");
						}
					});
				}
	        	else {
	        		roleButtons[i].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							// TODO Auto-generated method stub
							setButtonsBg(roleButtons);
							view.setBackgroundResource(R.drawable.button_circle_red_bg);
							myPartners.setRole(((Button)view).getText().toString().trim());
						}
					});
				}
	        }
	        for(int i=0;i<positionButtons.length;i++){
	        	positionButtons[i].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						setButtonsBg(positionButtons);
						view.setBackgroundResource(R.drawable.button_circle_red_bg);
						myPartners.setPosition(((Button)view).getText().toString().trim());
					}
				});
	        }
	        for(int i=0;i<SalayButtons.length;i++){
	        	SalayButtons[i].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						setButtonsBg(SalayButtons);
						view.setBackgroundResource(R.drawable.button_circle_red_bg);
						myPartners.setSalay(((Button)view).getText().toString().trim());
					}
				});
	        }
	        
	        
	        initPublishProjectHead("召唤合作伙伴", "保存",
	    			new onLeftImageButtonClickListener() {
						
						@Override
						public void onClick() {
							// TODO Auto-generated method stub
							finish();
						}
					},
	    			new onRightImageButtonClickListener() {
						
						@Override
						public void onClick() {
							// TODO Auto-generated method stub
		        		if (!check()) {
							return;
						}
						// TODO Auto-generated method stub
		        		Intent intent=new Intent();
		        		intent.putExtra("action", action);
		        		if (action.equals("alter")) {
		        			intent.putExtra("currPartners", currPartners);
						}
		        		intent.putExtra("myPartners", myPartners);
		        		setResult(ProjectPublish4Activity.SUMMON_PARTNERS_RETURN, intent);
		        		finish();
						}
					});
	    }
	 protected boolean check() {
		// TODO Auto-generated method stub
		if (myPartners.getRole()==null||
				myPartners.getPosition()==null||
				myPartners.getSalay()==null) {
			ShowToast("请选择完整数据!!!");
			return false;
		}
		if (Equity.getText().toString().trim().equals("")) {
			ShowToast("请填写股权比例!!!");
			return false;
		}else {
			try {
				Float float1=Float.parseFloat(Equity.getText().toString().trim());
				if(float1>=1){
					ShowToast("填写的股权比例必须是小于1的小数!!!");
				}
				myPartners.setEquity(float1);
				
			} catch (Exception e) {
				// TODO: handle exception
				ShowToast("填写的股权比例必须是小数!!!");
				return false;
			}
		}
		return true;
	}
	public void setButtonsBg (Button[] buttons) {
		for(int i=0;i<buttons.length;i++){
	    String str=roleButtons[i].getText().toString().trim();
	    //||str.equals(currRole)
		if (!ptrString.contains(str)||str.equals(currRole)) {
			buttons[i].setBackgroundResource(R.drawable.button_circle_bg);
			}
		}
	}
	public void initButton (String str,Button[] buttons) {
		for(int i=0;i<buttons.length;i++){
		   if (buttons[i].getText().toString().trim().equals(str.trim())) {  
			 buttons[i].setBackgroundResource(R.drawable.button_circle_red_bg);
			}
		}
	}
}
