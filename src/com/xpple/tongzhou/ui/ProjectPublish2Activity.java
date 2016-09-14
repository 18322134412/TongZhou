package com.xpple.tongzhou.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;


import com.xpple.tongzhou.R;
import com.xpple.tongzhou.bean.PublishProject;
import com.xpple.tongzhou.view.GetEditText;
import com.xpple.tongzhou.view.GetEditText.onRightButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;

public class ProjectPublish2Activity extends BaseActivity {
	public static final int PROSPECT_RESULT_CODE = 9;
	public static final int ACTIVITY_RETURN=8;//头标签的下一步
	private PublishProject mPublishProject=new PublishProject();
	private EditText Introduction;
	private EditText Competitive;
	private GetEditText Prospect;
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_project2);
        
        Intent myIntent=getIntent();
        Bundle myBundle=myIntent.getExtras();
        mPublishProject=(PublishProject)myBundle.getSerializable("mPublishProject");
        

        Introduction=(EditText)findViewById(R.id.Introduction);
        Competitive=(EditText)findViewById(R.id.Competitive);
        Prospect=(GetEditText)findViewById(R.id.Prospect);
        

        //填充已经输入的数据
        if (mPublishProject.getIntroduction()!=null) {
			Introduction.setText(mPublishProject.getIntroduction());
		}
        if (mPublishProject.getCompetitive()!=null) {
        	Competitive.setText(mPublishProject.getCompetitive());
		}
        if (mPublishProject.getProspectTime()!=null) {
        	String prospectTime=mPublishProject.getProspectTime();
        	String prospectValue=mPublishProject.getProspectValue();
        	Prospect.setText(prospectTime+"年价值:"+prospectValue+"万美元");	  ;
		}
        //初始化标题
        initPublishProjectHead("发布项目2/5", "下一步",
    			new onLeftImageButtonClickListener() {
					
					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						mPublishProject.setIntroduction(Introduction.getText().toString().trim());
				        mPublishProject.setCompetitive(Competitive.getText().toString().trim());


				        Intent myintent=new Intent();
		        		Bundle mybundle=new Bundle();
		        		mybundle.putSerializable("mPublishProject", mPublishProject);
		        		myintent.putExtras(mybundle);
		                setResult(ProjectPublish1Activity.ACTIVITY_RETURN, myintent);
		                finish();
					}
				},
    			new onRightImageButtonClickListener() {
					
					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						if (!check()){
			        		   return;
			        		}
							// TODO Auto-generated method stub
			        		Intent intent=new Intent(ProjectPublish2Activity.this, ProjectPublish3Activity.class);
			        		Bundle bundle=new Bundle();
			        		bundle.putSerializable("mPublishProject", mPublishProject);
			        		intent.putExtras(bundle);
							startActivityForResult(intent, ACTIVITY_RETURN);
					}
				});
        Prospect.setOnRightButtonClickListener(new onRightButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ProjectPublish2Activity.this, ProjectProspectActivity.class);
				startActivityForResult(intent, PROSPECT_RESULT_CODE);
			}
		});
	 }
	
	private Boolean check() {
			if (Introduction.getText().toString().trim().length()==0||
				Competitive.getText().toString().trim().length()==0||
				Prospect.getText().toString().trim().length()==0) {
				ShowToast("请填写完整数据!!!");
				return false;
			}
	        mPublishProject.setIntroduction(Introduction.getText().toString().trim());
	        mPublishProject.setCompetitive(Competitive.getText().toString().trim());
			return true;
  }
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {
            switch (resultCode) {
                case PROSPECT_RESULT_CODE:
                		if(data.getStringExtra("isok").equals("true"))
                		{
                			String prospectTime=data.getStringExtra("time");
                    		String prospectValue=data.getStringExtra("value");
                    		String prospectDescript=data.getStringExtra("descript");
                    		mPublishProject.setProspectTime(prospectTime);
    						mPublishProject.setProspectValue(prospectValue);
    						mPublishProject.setProspectDescript(prospectDescript);
                    		Prospect.setText(prospectTime+"年价值:"+prospectValue+"万美元");	
                		}
                    break;
                case ACTIVITY_RETURN:
                	mPublishProject=(PublishProject)data.getSerializableExtra("mPublishProject");
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
