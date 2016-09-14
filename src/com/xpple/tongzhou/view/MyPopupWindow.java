package com.xpple.tongzhou.view;


import java.util.Timer;
import java.util.TimerTask;

import com.xpple.tongzhou.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

@SuppressLint("ViewConstructor")
public class MyPopupWindow extends PopupWindow {
	private Button sendButton;
	private EditText ev;
	private View mView;
	public String getEditText() {
		return ev.getText().toString().trim();
	}
	public void setEditText(String str) {
		ev.setText(str);
	}
	public MyPopupWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.comment, null);
		sendButton = (Button) mView.findViewById(R.id.sendButton);
		sendButton.setOnClickListener(itemsOnClick);
		
		ev = (EditText) mView.findViewById(R.id.commentText);
		ev.setFocusable(true);
		ev.setFocusableInTouchMode(true);
		

		this.setContentView(mView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setInputMethodMode(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
		//this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) ev
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(ev, 0);
			}

		}, 200);
		mView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int height = mView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}
	public void setHint(String hint) {
		ev.setHint(hint);
	}

}
