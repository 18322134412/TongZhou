package com.xpple.tongzhou.view;

import com.xpple.tongzhou.R;

import android.R.color;
import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;



/**
 * http://blog.csdn.net/lmj623565791/article/details/41967509
 * 
 * @author zhy
 * 
 */
public class RoundImageView extends ImageView
{
	/**
	 * 图片的类型，圆形or圆角
	 */
	private int type;
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND = 1;
	private static final int BODER_RADIUS_DEFAULT = 0;
	/**
	 * 圆角的大�?
	 */
	private int mBorderRadius;
	private int mDrawableRadius;
	/**
	 * 圆角的半�?
	 */
	private int mRadius;
    private Paint mBorderPaint;
	private Paint mBitmapPaint;
	private BitmapShader mBitmapShader;
	private int mDrawableWidth;
	private int mDrawableHeight;
	private int mHeight;
	private int mWidth;
	//private int mPaddingWidth;
	/**
	 * 3x3 矩阵，主要用于缩小放�?
	 */
	private Matrix mMatrix;
	
	private RectF mBorderRoundRect=new RectF();
	private RectF mDrawableRoundRect=new RectF();
	/**
	 * 图片的默认边框宽度和边框颜色
	 */
	private static final int DEFAULT_BORDER_WIDTH = 10;
    private static final int DEFAULT_BORDER_COLOR =Color.WHITE;
    
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

	public RoundImageView(Context context, AttributeSet attrs)
	{

		super(context, attrs);
		mMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBorderPaint = new Paint();

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);

		mBorderRadius = a.getDimensionPixelSize(
				R.styleable.RoundImageView_borderRadius, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
								BODER_RADIUS_DEFAULT, getResources()
										.getDisplayMetrics()));// 默认�?10dp
		type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 默认为Circle
		mBorderWidth = (int)a.getDimension(R.styleable.RoundImageView_borderWidth,DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.RoundImageView_borderColor, DEFAULT_BORDER_COLOR);
		
		a.recycle();
	}

	public RoundImageView(Context context)
	{
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 如果类型是圆形，则强制改变view的宽高一致，以小值为�?
		 */
		if (type == TYPE_CIRCLE)
		{
			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			mDrawableWidth=mWidth-mBorderWidth*2;
			mRadius = mDrawableWidth / 2;
			setMeasuredDimension(mWidth, mWidth);
		}else {
			mWidth=getWidth();
			mHeight=getHeight();
		
			mDrawableHeight=mHeight-mBorderWidth*2;
			mDrawableWidth=mWidth-mBorderWidth*2;
		}  
	}

	/**
	 * 初始化BitmapShader
	 */
	private void setUpShader()
	{
		Drawable drawable = getDrawable();
		if (drawable == null)
		{
			return;
		}

		Bitmap bmp = drawableToBitamp(drawable);
		mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.00f;
		if (type == TYPE_CIRCLE)
		{
			int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
			scale = mDrawableWidth * 1.00f / bSize;

		} else if (type == TYPE_ROUND)
		{
			if (!(bmp.getWidth() ==mDrawableWidth && bmp.getHeight() == mDrawableHeight))
			{
				// 如果图片的宽或�?�高与view的宽高不匹配，计算出�?要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；�?以我们这里取大�?�；
				scale = Math.max(mDrawableWidth * 1.00f / bmp.getWidth(),
						mDrawableHeight * 1.00f / bmp.getHeight());
			}
		}
		// shader的变换矩阵，我们这里主要用于放大或�?�缩�?
		mMatrix.setScale(scale, scale);
		mMatrix.postTranslate(mBorderWidth, mBorderWidth);
		mBitmapPaint.setAntiAlias(true);
		// 设置变换矩阵
		mBitmapPaint.setShader(mBitmapShader);

		mBitmapShader.setLocalMatrix(mMatrix);
		mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        
        if(type==TYPE_CIRCLE){
        	mBorderRoundRect.set(0, 0, getWidth(), getHeight());
             mBorderRadius = (int)Math.min((mBorderRoundRect.height() - mBorderWidth) / 2, (mBorderRoundRect.width() - mBorderWidth) / 2);

             mDrawableRoundRect.set(mBorderWidth, mBorderWidth, mBorderRoundRect.width() - mBorderWidth, mBorderRoundRect.height() - mBorderWidth);
             mDrawableRadius =(int) Math.min(mDrawableRoundRect.height() / 2, mDrawableRoundRect.width() / 2);
        }
        else {
        	mBorderRoundRect.set(mBorderWidth/2,mBorderWidth/2, mWidth-mBorderWidth/2, mHeight-mBorderWidth/2);
            mDrawableRoundRect.set(mBorderWidth, mBorderWidth, mWidth-mBorderWidth, mHeight-mBorderWidth );
		}
        
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Log.e("TAG", "onDraw "+mWidth+"  "+mHeight+"  "+mDrawableHeight+"  "+mDrawableWidth);
		if (getDrawable() == null)
		{
			return;
		}
		setUpShader();

		if (type == TYPE_ROUND)
		{	
			canvas.drawRoundRect(mDrawableRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);
			canvas.drawRoundRect(mBorderRoundRect, mBorderRadius, mBorderRadius,
					mBorderPaint);
		} else
		{
			//canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mBitmapPaint);
		    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mBorderPaint);
		}
	}

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitamp(Drawable drawable)
	{
		if (drawable instanceof BitmapDrawable)
		{
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
		return bitmap;
	}

	


}