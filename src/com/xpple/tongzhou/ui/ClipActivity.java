package com.xpple.tongzhou.ui;

import java.io.File;

import com.xpple.tongzhou.R;
import com.xpple.tongzhou.util.ImageTools;
import com.xpple.tongzhou.view.ClipImageLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClipActivity extends Activity{
	private ClipImageLayout mClipImageLayout;
	private String path;
	private ProgressDialog loadingDialog;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clipimage);
		 textView=(TextView)findViewById(R.id.tv_return);
		 textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
				openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(openAlbumIntent, 2);
			}
		});
		//这步必须要加
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);

		path=getIntent().getStringExtra("path");
		initView();
		((Button)findViewById(R.id.id_action_clip)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadingDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = mClipImageLayout.clip();
						String path= Environment.getExternalStorageDirectory()+"/ClipHeadPhoto/cache/"+System.currentTimeMillis()+ ".png";
						ImageTools.savePhotoToSDCard(bitmap,path);
						loadingDialog.dismiss();
						Intent intent = new Intent();
						intent.putExtra("path",path);
						setResult(RESULT_OK, intent);
						finish();
					}
				}).start();
			}
		});
	}
	public void initView() {
		loadingDialog=new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
		if(TextUtils.isEmpty(path)||!(new File(path).exists())){
			Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
			return;
		}
		Bitmap bitmap=ImageTools.convertToBitmap(path, 600,600);
		if(bitmap==null){
			Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
			return;
		}

		mClipImageLayout.setBitmap(bitmap);
	}
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==Activity.RESULT_OK) {
			Uri uri = null;
			if (data==null) {
				return;
			} 
			uri = data.getData();
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, proj, null, null,null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			path = cursor.getString(column_index);// 图片在的路径
			initView();
		}
	}
	
}
