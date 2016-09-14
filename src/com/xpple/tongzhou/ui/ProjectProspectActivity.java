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
	private WheelView Wheel; // �����
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
        
        initPublishProjectHead("��Ŀǰ��", "����",
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
		 //��ʼ������
	    initDatas();
	     //orientation���������
	    popView = getLayoutInflater().inflate(R.layout.pop_wheel,null);
		popup = new PopupWindow(popView, -1,
				LayoutParams.WRAP_CONTENT, true);
		popup.setFocusable(true);
		//��ӵ���������������Ҫ�趨,�����ʶ���,�����Ū��һ��,�������۷�Χ
		// popup.setAnimationStyle(R.style.popwin_anim_style);
		// ʵ����һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// ����SelectPicPopupWindow��������ı���
		popup.setBackgroundDrawable(dw);
			
		// menu�˵���ý��� ���û�л�ý���menu�˵��еĿؼ��¼��޷���Ӧ
		popup.setFocusable(true);
		popup.update();
		//findViewById(R.id.main)����Ҫ���Ǹ����浯��
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
	        	Date    curDate    =   new    Date(System.currentTimeMillis());//��ȡ��ǰʱ��
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
     * ����
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
			// ���change�¼�
			Wheel.addChangingListener(this);
			//����Ĭ�ϵ�ѡ�е�
			Wheel.setCurrentItem(3);
			//��ʾ����Ŀ
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
				ShowToast("����д��������!!!");
				return false;
			}
			return true;
}
}