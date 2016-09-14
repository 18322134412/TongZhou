package com.xpple.tongzhou.view;

import java.net.ContentHandler;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @author kingofglory email: kingofglory@yeah.net blog: http:www.google.com
 * @date 2015-8-12 TODO
 */
public class GetEditText extends EditText {
	private Drawable mRightDrawable;
	private Paint mPaint; 
	private onRightButtonClickListener mRightButtonClickListener;
	public interface onRightButtonClickListener {
		void onClick();
	}
	public void setOnRightButtonClickListener(
			onRightButtonClickListener listener) {
		mRightButtonClickListener = listener;
	}
	public GetEditText(Context context) {
		super(context);
		init();
	}

	public GetEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GetEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		  
		init();
	}
//	 @Override  
//    public void onDraw(Canvas canvas)  
//    {  
//        super.onDraw(canvas);  
//          
//        //	      画底线  
//        canvas.drawLine(0,this.getHeight()-1,  this.getWidth()-1, this.getHeight()-1, mPaint);  
//    }  
	private void init() {
//		mPaint = new Paint();  
//        mPaint.setStyle(Paint.Style.STROKE);  
//        mPaint.setColor(Color.BLUE);
		// getCompoundDrawables:
		// Returns drawables for the left, top, right, and bottom borders.
		Drawable[] drawables = this.getCompoundDrawables();

		// 取得right位置的Drawable
		// 即我们在布局文件中设置的android:drawableRight
		mRightDrawable = drawables[2];
	}

	/**
	 * 当手指抬起的位置在clean的图标的区域 我们将此视为进行清除操作 getWidth():得到控件的宽�?
	 * event.getX():抬起时的坐标(改坐标是相对于控件本身�?�言�?)
	 * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距�?
	 * getPaddingRight():clean的图标右边缘至控件右边缘的距�? 于是: getWidth() -
	 * getTotalPaddingRight()表示: 控件左边到clean的图标左边缘的区�? getWidth() -
	 * getPaddingRight()表示: 控件左边到clean的图标右边缘的区�? �?以这两�?�之间的区域刚好是clean的图标的区域
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:

//			boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
//					&& (event.getX() < (getWidth() - getPaddingRight()));
			boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()));

			if (isClean) {
				if (mRightButtonClickListener!=null) {
					mRightButtonClickListener.onClick();
				}
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}	
}
