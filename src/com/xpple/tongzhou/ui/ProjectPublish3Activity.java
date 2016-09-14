package com.xpple.tongzhou.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


import com.xpple.tongzhou.R;
import com.xpple.tongzhou.adapt.ArrayWheelAdapter;
import com.xpple.tongzhou.bean.PublishProject;
import com.xpple.tongzhou.view.GetEditText;
import com.xpple.tongzhou.view.GetEditText.onRightButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;
import com.xpple.tongzhou.view.WheelView;
import com.xpple.tongzhou.view.WheelView.OnWheelChangedListener;

public class ProjectPublish3Activity extends BaseActivity implements OnWheelChangedListener {
	private TextView btnCancel;
	private TextView btnConfirm;
	private WheelView Wheel; // 左滚轮
	private String[] mPhaseDatas; 
	private String[] mStageDatas;
	private int WheelPoint; // 当前选择
	private PopupWindow popup;
	private View popView;
	private String mCurrentDateString;
	private final int PROJECT_PHASE_POINT=0;
	private final int FINANCING_STAGE_POINT=1;

	
	
	public static final int ACTIVITY_RETURN=8;//头标签的下一步
	private PublishProject mPublishProject=new PublishProject();
	private EditText TeamNumber;
	private EditText TotalNumber;
	private GetEditText ProjectPhase;
	private GetEditText FinancingStage;
	
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_project3);
        
        Intent myIntent=getIntent();
        Bundle myBundle=myIntent.getExtras();
        mPublishProject=(PublishProject)myBundle.get("mPublishProject");
        
        TeamNumber=(EditText)findViewById(R.id.TeamNumber);
        TotalNumber=(EditText)findViewById(R.id.TotalNumber);
        ProjectPhase=(GetEditText)findViewById(R.id.ProjectPhase);
        FinancingStage=(GetEditText)findViewById(R.id.FinancingStage);
        
      //填充已经输入的数据
        if (mPublishProject.getTeamNumber()!=0) {
        	TeamNumber.setText(mPublishProject.getTeamNumber()+"");
        	
		}
        if (mPublishProject.getTotalNumber()!=0) {
        	TotalNumber.setText(mPublishProject.getTotalNumber()+"");
		}
        if (mPublishProject.getProjectPhase()!=null) {
        	ProjectPhase.setText(mPublishProject.getProjectPhase());	  
		}
        if (mPublishProject.getFinancingStage()!=null) {
        	FinancingStage.setText(mPublishProject.getFinancingStage());	  
		}
        
        initPublishProjectHead("发布项目3/5", "下一步",
    			new onLeftImageButtonClickListener() {
					
					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
						if (!TeamNumber.getText().toString().trim().equals("")) {
							mPublishProject.setTeamNumber(Integer.parseInt(TeamNumber.getText().toString().trim()));
						}
						if (!TotalNumber.getText().toString().trim().equals("")) {
							mPublishProject.setTotalNumber(Integer.parseInt(TotalNumber.getText().toString().trim()));
						}
				        mPublishProject.setProjectPhase(ProjectPhase.getText().toString().trim());
				        mPublishProject.setFinancingStage(FinancingStage.getText().toString().trim());


				        Intent myintent=new Intent();
		        		Bundle mybundle=new Bundle();
		        		mybundle.putSerializable("mPublishProject", mPublishProject);
		        		myintent.putExtras(mybundle);
		                setResult(ProjectPublish2Activity.ACTIVITY_RETURN, myintent);
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
	        		Intent intent=new Intent(ProjectPublish3Activity.this, ProjectPublish4Activity.class);
	        		Bundle bundle=new Bundle();
	        		bundle.putSerializable("mPublishProject", mPublishProject);
	        		intent.putExtras(bundle);
					startActivityForResult(intent, ACTIVITY_RETURN);
					}
				});
        ProjectPhase.setOnRightButtonClickListener(new onRightButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				WheelPoint=PROJECT_PHASE_POINT;
				showPopWindow();
			}
		});
        FinancingStage.setOnRightButtonClickListener(new onRightButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				WheelPoint=FINANCING_STAGE_POINT;
				showPopWindow();
			}
		});
 }
    private Boolean check() {
		if (TeamNumber.getText().toString().trim().length()==0||
			TotalNumber.getText().toString().trim().length()==0||
			ProjectPhase.getText().toString().trim().length()==0||
			FinancingStage.getText().toString().trim().length()==0) {
			ShowToast("请填写完整数据!!!");
			return false;
		}
        mPublishProject.setTeamNumber(Integer.parseInt(TeamNumber.getText().toString().trim()));
        mPublishProject.setTotalNumber(Integer.parseInt(TotalNumber.getText().toString().trim()));
        mPublishProject.setProjectPhase(ProjectPhase.getText().toString().trim());
        mPublishProject.setFinancingStage(FinancingStage.getText().toString().trim());
		return true;
	}
	 private void showPopWindow(){   	
			 //初始化数据
		    initDatas();
		     //orientation弹出框设计
		    popView = getLayoutInflater().inflate(R.layout.pop_wheel,null);
			popup = new PopupWindow(popView, -1,
					LayoutParams.WRAP_CONTENT, true);
			popup.setFocusable(true);
			//添加弹出动画，根据需要设定,具体问度娘,我随便弄了一个,不在讨论范围
			// popup.setAnimationStyle(R.style.popwin_anim_style);
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0xb0000000);
			// 设置SelectPicPopupWindow弹出窗体的背景
			popup.setBackgroundDrawable(dw);
				
			// menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
			popup.setFocusable(true);
			popup.update();
			//findViewById(R.id.main)是你要从那个界面弹出
			popup.showAtLocation(findViewById(R.id.mProject3), Gravity.BOTTOM,
					0, 0);
			initContentView();
			}
	 private void initDatas() {
			if (WheelPoint==PROJECT_PHASE_POINT) {
				if (mPhaseDatas==null) {
					ArrayList<String> list = new ArrayList<String>();
			        try {
			            JSONArray ProjectPhase = new JSONArray(getResources().getString(R.string.ProjectPhase));
			            //ShowToast(chineseOrientation.getJSONObject(0).getString("name"));
			            for (int i = 0; i <  ProjectPhase.length(); i++) {
			                JSONObject jsonObject =  ProjectPhase.getJSONObject(i);
			                list.add(jsonObject.getString("name"));
			            }

			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        Collections.sort(list, comparator);
				    int size=list.size();
				    mPhaseDatas=list.toArray(new String[size]);
				}
			}else {
				if (mStageDatas==null) {
					ArrayList<String> list = new ArrayList<String>();
			        try {
			            JSONArray ProjectStage = new JSONArray(getResources().getString(R.string.FinancingStage));
			            //ShowToast(chineseOrientation.getJSONObject(0).getString("name"));
			            for (int i = 0; i <  ProjectStage.length(); i++) {
			                JSONObject jsonObject =  ProjectStage.getJSONObject(i);
			                list.add(jsonObject.getString("name"));
			            }

			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        Collections.sort(list, comparator);
				    int size=list.size();
				    mStageDatas=list.toArray(new String[size]);
				}
			}
			
		}
	 /**
     * 排序
     */
	Comparator<String> comparator = new Comparator<String>() {
	        @Override
	        public int compare(String a, String b) {
	        	return a.compareTo(b);
	        }
	    };
    @Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
    	if (WheelPoint==PROJECT_PHASE_POINT) {
    		mCurrentDateString =  mPhaseDatas[newValue];
		}else {
			mCurrentDateString =  mStageDatas[newValue];
		}
	}
	 protected void initContentView() {
		    Wheel = (WheelView) popView.findViewById(R.id.wheel1);
			WheelView wheelView = (WheelView) popView.findViewById(R.id.wheel2);
			wheelView.setVisibility(View.GONE);
			btnCancel = (TextView) popView.findViewById(R.id.cancel);
			btnConfirm = (TextView) popView.findViewById(R.id.confirm);
			if (WheelPoint==PROJECT_PHASE_POINT) {
				Wheel.setViewAdapter(new ArrayWheelAdapter<String>(
						getApplicationContext(), mPhaseDatas));
			}else {
				Wheel.setViewAdapter(new ArrayWheelAdapter<String>(
						getApplicationContext(), mStageDatas));
			}
			// 添加change事件
			Wheel.addChangingListener(this);
			//设置默认的选中的
			Wheel.setCurrentItem(3);
			//显示的条目
			Wheel.setVisibleItems(7);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (popup != null) {
						popup.dismiss();
					}
				}
			});
			btnConfirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (WheelPoint==PROJECT_PHASE_POINT) {
						ProjectPhase.setText(mCurrentDateString);
					}else {
						FinancingStage.setText(mCurrentDateString);
					}

					if (popup != null) {
						popup.dismiss();
					}

				}
			});

		}
}
