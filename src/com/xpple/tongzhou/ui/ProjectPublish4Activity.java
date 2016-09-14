package com.xpple.tongzhou.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xpple.tongzhou.R;
import com.xpple.tongzhou.bean.Partners;
import com.xpple.tongzhou.bean.PublishProject;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;

public class ProjectPublish4Activity extends BaseActivity {
	private PublishProject mPublishProject=new PublishProject();
	private ArrayList<Partners> SummonPartners=new ArrayList<Partners>();
	private Button[] roleButtons=new Button[6];
	
	public static final int ACTIVITY_RETURN=8;//头标签的下一步
	public static final int SUMMON_PARTNERS_RETURN=10;
	
	private EditText CallDeclaration;
	private Button Summon;
	
	private String ptrString="";
	
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.publish_project4);
	        
	        Intent myIntent=getIntent();
	        Bundle myBundle=myIntent.getExtras();
	        mPublishProject=(PublishProject)myBundle.get("mPublishProject");
	        
	        roleButtons[0]=(Button)findViewById(R.id.RoleSkill);
	        roleButtons[1]=(Button)findViewById(R.id.RoleSale);
	        roleButtons[2]=(Button)findViewById(R.id.RoleOperate);
	        roleButtons[3]=(Button)findViewById(R.id.RoleDesign);
	        roleButtons[4]=(Button)findViewById(R.id.RoleProduct);
	        roleButtons[5]=(Button)findViewById(R.id.RoleOther);
	        
	        initSummonList();
	       
	        CallDeclaration=(EditText)findViewById(R.id.CallDeclaration);
	        Summon=(Button)findViewById(R.id.Summon);
	        
	        initPublishProjectHead("发布项目4/5", "下一步",
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
					mPublishProject.setCallDeclaration(CallDeclaration.getText().toString().trim());
					
	        		Intent intent=new Intent(ProjectPublish4Activity.this, ProjectPublish5Activity.class);
	        		Bundle bundle=new Bundle();
	        		bundle.putSerializable("mPublishProject", mPublishProject);
	        		bundle.putSerializable("mSummonPartners", SummonPartners);
	        		intent.putExtras(bundle);
	        		
					startActivityForResult(intent, ACTIVITY_RETURN);
					}
				});
	        Summon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ProjectPublish4Activity.this, SummonPartnersActivity.class);
					intent.putExtra("ptrString", getPtrString());
					intent.putExtra("action", "add");
					startActivityForResult(intent, SUMMON_PARTNERS_RETURN);
				}
			});
	    }
	 private String getPtrString() {
		 ptrString="";
		for (Partners element : SummonPartners) {
			ptrString+=element.getRole();
		}
		return ptrString;
	}
	 private void initSummonList() {
		// TODO Auto-generated method stub
		 for (int i = 0; i < SummonPartners.size(); i++) {
	        	roleButtons[i].setText(SummonPartners.get(i).getRole());
	        	roleButtons[i].setVisibility(View.VISIBLE);
	        	roleButtons[i].setTag(SummonPartners.get(i));
	        	roleButtons[i].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(ProjectPublish4Activity.this, SummonPartnersActivity.class);
						intent.putExtra("action", "alter");
						intent.putExtra("ptrString", getPtrString());
						intent.putExtra("currPartner", (Partners)(((Button)arg0).getTag()));
						startActivityForResult(intent, SUMMON_PARTNERS_RETURN);
					}
				});
			}
	}
	@Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
	        try {
	            switch (resultCode) {
	                case SUMMON_PARTNERS_RETURN:
	                	Partners mypartners=(Partners)data.getSerializableExtra("myPartners");
	                	String action=data.getStringExtra("action");
	                    if (action.equals("alter")) {
	                    	Partners currPartners=(Partners)data.getSerializableExtra("currPartners");
	                    	
	                    	//ShowToast(mypartners.getRole());
	                    	for (int i = 0; i < SummonPartners.size(); i++) {
								if (SummonPartners.get(i).getRole().equals(currPartners.getRole())) {
									ShowToast(mypartners.getRole());
			                    	SummonPartners.remove(i);
			                    	SummonPartners.add(i, mypartners);
								}
							}
						}else {

		                	SummonPartners.add(mypartners);
						}
	                	initSummonList();
	                	break;
	                default:
	                    break;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        super.onActivityResult(requestCode, resultCode, data);
	    }
}

