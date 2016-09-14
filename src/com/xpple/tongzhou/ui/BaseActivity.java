package com.xpple.tongzhou.ui;

import com.xpple.tongzhou.R;
import com.xpple.tongzhou.view.HeaderLayout;
import com.xpple.tongzhou.view.HeaderLayout.HeaderStyle;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.appcompat.R.integer;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

/** 基类
  */
public class BaseActivity extends FragmentActivity {

	protected HeaderLayout mHeaderLayout;
	
	protected int mScreenWidth;
	protected int mScreenHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
	}

	Toast mToast;

	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});
			
		}
	}

	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast == null) {
					mToast = Toast.makeText(BaseActivity.this.getApplicationContext(), resId,
							Toast.LENGTH_LONG);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
	}

	
	//曹林	
	public void initPublishProjectHead(String titleName, String rightText,
			onLeftImageButtonClickListener listener1,
			onRightImageButtonClickListener listener2) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,R.drawable.base_action_bar_back_bg_n,listener1);
		mHeaderLayout.setTitleAndRightButton(titleName, R.color.transparent,rightText,listener2);
		mHeaderLayout.SetHeaderBg(R.drawable.bg_actionbar);
	}

}
