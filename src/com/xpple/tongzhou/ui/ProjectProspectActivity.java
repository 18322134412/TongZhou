package com.xpple.tongzhou.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.xpple.tongzhou.view.GetEditText;
import com.xpple.tongzhou.view.GetEditText.onRightButtonClickListener;
import com.xpple.tongzhou.view.WheelView;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;
import com.xpple.tongzhou.view.WheelView.OnWheelChangedListener;

public class ProjectProspectActivity extends BaseActivity implements OnWheelChangedListener {	
	private TextView btnCancel;
	private TextView btnConfirm;
	private WheelView Wheel; // 左滚轮
	private String[] mDatas; 
	private PopupWindow popup;
	private View popView;
	private String mCurrentDateString;
	
	private GetEditText Estimate;
	private EditText Evaluate;
	private EditText Description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_prospect);
        
        Estimate=(GetEditText)findViewById(R.id.Estimate);
        Evaluate=(EditText)findViewById(R.id.Evaluate);
        Description=(EditText)findViewById(R.id.Description);
        
        initPublishProjectHead("项目前景", "保存",
    			new onLeftImageButtonClickListener() {
					
					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("isok", "false");
		                setResult(ProjectPublish2Activity.PROSPECT_RESULT_CODE, intent);
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
						Intent intent = new Intent();
						intent.putExtra("isok", "true");
		                intent.putExtra("time", Estimate.getText().toString().trim());
		                intent.putExtra("value", Evaluate.getText().toString().trim());
		                intent.putExtra("descript", Description.getText().toString().trim());
		                setResult(ProjectPublish2Activity.PROSPECT_RESULT_CODE, intent);
		                finish();
					}
				});
        Estimate.setOnRightButtonClickListener(new onRightButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				showPopWindow();
			}
		});
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
		popup.showAtLocation(findViewById(R.id.mprogramProspect), Gravity.BOTTOM,
				0, 0);
		initContentView();
		}
    private void initDatas() {
		if (mDatas==null) {
			ArrayList<String> list = new ArrayList<String>();
	        try {
//	            JSONArray ProjectPhase = new JSONArray(getResources().getString(R.string.ProjectPhase));
//	            //ShowToast(chineseOrientation.getJSONObject(0).getString("name"));
//	            for (int i = 0; i <  ProjectPhase.length(); i++) {
//	                JSONObject jsonObject =  ProjectPhase.getJSONObject(i);
//	                list.add(jsonObject.getString("name"));
//	            }
	        	Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
	        	int date=curDate.getYear();
	            for (int i = 0; i <  16; i++) {
                    list.add(2015+i+"");
                }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //Collections.sort(list, comparator);
		    int size=list.size();
		    mDatas=list.toArray(new String[size]);
		}
	}
    /**
     * 排序
     */
//	Comparator<String> comparator = new Comparator<String>() {
//	        @Override
//	        public int compare(String a, String b) {
//	        	return a.compareTo(b);
//	        }
//	    };
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		mCurrentDateString =  mDatas[newValue];
	}
	 protected void initContentView() {
		    Wheel = (WheelView) popView.findViewById(R.id.wheel1);
			WheelView wheelView = (WheelView) popView.findViewById(R.id.wheel2);
			wheelView.setVisibility(View.GONE);
			btnCancel = (TextView) popView.findViewById(R.id.cancel);
			btnConfirm = (TextView) popView.findViewById(R.id.confirm);
			Wheel.setViewAdapter(new ArrayWheelAdapter<String>(
					getApplicationContext(), mDatas));
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
					Estimate.setText(mCurrentDateString);
					if (popup != null) {
						popup.dismiss();
					}
	
				}
			});
	
		}
	 private Boolean check() {
			if (Evaluate.getText().toString().trim().length()==0||
				Estimate.getText().toString().trim().length()==0||
				Description.getText().toString().trim().length()==0) {
				ShowToast("请填写完整数据!!!");
				return false;
			}
			return true;
}
}