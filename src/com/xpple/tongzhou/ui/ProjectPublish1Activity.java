package com.xpple.tongzhou.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


import cn.bmob.v3.datatype.BmobFile;

import com.xpple.tongzhou.R;
import com.xpple.tongzhou.adapt.ArrayWheelAdapter;
import com.xpple.tongzhou.bean.PublishProject;
import com.xpple.tongzhou.view.GetEditText;
import com.xpple.tongzhou.view.GetEditText.onRightButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;
import com.xpple.tongzhou.view.RoundImageView;
import com.xpple.tongzhou.view.WheelView;
import com.xpple.tongzhou.view.WheelView.OnWheelChangedListener;

public class ProjectPublish1Activity extends BaseActivity implements OnWheelChangedListener {
	public static final int FILE_RESULT_CODE = 1;
	public static final int SELECT_CITY_CODE = 2;
	public static final int ORIENTATION_NAME_CODE = 3;
	
	public static final int PHOTOZOOM = 4; // ���/����
	public static final int PHOTOTAKE = 5; // ���/����
	public static final int IMAGE_COMPLETE = 6; // ���
	public static final int CROPREQCODE = 7; // ��ȡ
	
	public static final int ACTIVITY_RETURN=8;//ͷ��ǩ����һ��
	//logoͼƬѡ�񲿷�
	private PopupWindow popWindow;
	private LayoutInflater layoutInflater;
	private TextView photograph,albums;
	private LinearLayout cancel;
	//logoͼƬ·��
	private String photoSavePath;//����·��
	private String photoSaveName;//ͼƬ��
	private String path;//ͼƬȫ·��
	private String totalImagePathString="";
	
	private PublishProject mPublishProject=new PublishProject();
	private TextView programName;
	private GetEditText areaname;
	private GetEditText orientationName;
	private EditText Highlights;
	private EditText PictureWord;
	private RoundImageView file;
	private BmobFile programfile=new BmobFile();
	
	//��Ŀ�������
	private TextView btnCancel;
	private TextView btnConfirm;
	private WheelView mOrientation; // �������
	private PopupWindow popup;
	private View menuCity;
	private String mCurrentOrientationName; // ��ǰѡ�����Ŀ����
	private String[] mOrientationDatas; // ��ŷ������ַ�������
	
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_project1);

        file=(RoundImageView)findViewById(R.id.addProgram);
        programName=(TextView)findViewById(R.id.programName);
        areaname=(GetEditText)findViewById(R.id.areaName);
        orientationName=(GetEditText)findViewById(R.id.orientationName);
        Highlights=(EditText)findViewById(R.id.Highlights);
        PictureWord=(EditText)findViewById(R.id.PictureWord);
        //����ͼƬλ��
        File mfile = new File(Environment.getExternalStorageDirectory(), "ClipHeadPhoto/cache");
		if (!mfile.exists())
			mfile.mkdirs();
		photoSavePath=Environment.getExternalStorageDirectory()+"/ClipHeadPhoto/cache/";
		photoSaveName =System.currentTimeMillis()+ ".png";
		
        //��ʼ��ͷ����
        initPublishProjectHead("������Ŀ1/5", "��һ��",
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
	        		if (!check()){
	        		   return;
	        		}
					// TODO Auto-generated method stub
	        		Intent intent=new Intent(ProjectPublish1Activity.this, ProjectPublish2Activity.class);
	        		Bundle bundle=new Bundle();
	        		bundle.putSerializable("mPublishProject", mPublishProject);
	        		intent.putExtras(bundle);
					startActivityForResult(intent, ACTIVITY_RETURN);
					}
				});
        file.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showPopupWindow(file);
			}
		});
        areaname.setOnRightButtonClickListener(new onRightButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ProjectPublish1Activity.this, SelectCityActivity.class);
				startActivityForResult(intent, SELECT_CITY_CODE);
			}
		});
        orientationName.setOnRightButtonClickListener(new onRightButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				showOrientationWindow();
			}
		});
        
    }
 @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
            switch (resultCode) {
                case SELECT_CITY_CODE:
                    areaname.setText(data.getStringExtra("lngCityName"));
                    break;
                case ACTIVITY_RETURN:
                	mPublishProject=(PublishProject)data.getSerializableExtra("mPublishProject");
                	ShowToast(mPublishProject.getFinancingStage());
                	break;
                case Activity.RESULT_OK:
            		Uri uri = null;
            		switch (requestCode) {
            			case PHOTOZOOM://���
            				if (data==null) {
            					return;
            				} 
            				uri = data.getData();
            				String[] proj = { MediaStore.Images.Media.DATA };
            				Cursor cursor = managedQuery(uri, proj, null, null,null);
            				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            				cursor.moveToFirst();
            				path = cursor.getString(column_index);// ͼƬ�ڵ�·��
            				Intent intent3=new Intent(ProjectPublish1Activity.this, ClipActivity.class);
            				intent3.putExtra("path", path);
            				startActivityForResult(intent3, IMAGE_COMPLETE);
            				break;
            			case PHOTOTAKE://����
            				path=photoSavePath+photoSaveName;
            				uri = Uri.fromFile(new File(path));
            				Intent intent2=new Intent(ProjectPublish1Activity.this, ClipActivity.class);
            				intent2.putExtra("path", path);
            				startActivityForResult(intent2, IMAGE_COMPLETE);
            				break;
            			case IMAGE_COMPLETE:
            				final String temppath = data.getStringExtra("path");
            				File mfile=new File(temppath);
            				if (mfile.length()>Math.pow(10, 21)) {
								ShowToast("�ļ���С��"+mfile.length()/Math.pow(10, 20)+"M �Ѿ�����2M");
								return;
							}
            				file.setImageBitmap(getLoacalBitmap(temppath));
            				programfile=new BmobFile(mfile);
            				mPublishProject.setProgramFile(programfile);
            				totalImagePathString=temppath;
            				break;
            			default:
            				break;
            		}
        			break;
                default:
                    break;
            }
     
        super.onActivityResult(requestCode, resultCode, data);
    }
	private Boolean check() {
			if (programName.getText().toString().trim().length()==0||
					areaname.getText().toString().trim().length()==0||
					orientationName.getText().toString().length()==0||
					Highlights.getText().toString().trim().length()==0||
					PictureWord.getText().toString().trim().length()==0
					) {
				ShowToast("����д��������!!!");
				return false;
			}
			if (totalImagePathString.length()==0) {
				ShowToast("�������Ŀ�ļ�!!!");
				return false;
			}
	        mPublishProject.setProgramNmae(programName.getText().toString().trim());
	        mPublishProject.setAreaname(areaname.getText().toString().trim());
	        mPublishProject.setOrientationName(orientationName.getText().toString().trim());
	        mPublishProject.setHighlights(Highlights.getText().toString().trim());
	        mPublishProject.setPictureWord(PictureWord.getText().toString().trim());
			return true;
		}

    protected void initOrientationView() {
		mOrientation = (WheelView) menuCity.findViewById(R.id.wheel1);
		WheelView noWheelView = (WheelView) menuCity.findViewById(R.id.wheel2);
		noWheelView.setVisibility(View.GONE);
		btnCancel = (TextView) menuCity.findViewById(R.id.cancel);
		btnConfirm = (TextView) menuCity.findViewById(R.id.confirm);

		mOrientation.setViewAdapter(new ArrayWheelAdapter<String>(
				getApplicationContext(), mOrientationDatas));
		// ���change�¼�
		mOrientation.addChangingListener(this);
		//����Ĭ�ϵ�ѡ�е�
				mOrientation.setCurrentItem(3);
		//��ʾ����Ŀ
		mOrientation.setVisibleItems(7);
		//һ��ʼ����Ҫ���ݵ�ǰѡ���ʡ����ʼ���е�
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
				orientationName.setText(mCurrentOrientationName);			
				if (popup != null) {
					popup.dismiss();
				}
			}
		});
	}
    @Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
    	mCurrentOrientationName =  mOrientationDatas[newValue];
	}
    private ArrayList<String> getOrientationList() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            JSONArray chineseOrientation = new JSONArray(getResources().getString(R.string.orientationName));
            //ShowToast(chineseOrientation.getJSONObject(0).getString("name"));
            for (int i = 0; i < chineseOrientation.length(); i++) {
                JSONObject jsonObject = chineseOrientation.getJSONObject(i);
                list.add(jsonObject.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }
    /**
     * ����
     */
    Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
        	return a.compareTo(b);
        }
    };
    private void showPopupWindow(View parent){
    	
		if (popWindow == null) {
			layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.pop_select_photo,null);
			popWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
			initPop(view);
		}	
		popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(true);
		popWindow.setBackgroundDrawable(new BitmapDrawable());	
		popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}
    private void showOrientationWindow(){
    	
	//��ʼ������
     ArrayList<String> list=getOrientationList();
     int size=list.size();
     //ShowToast(size+"");
     mOrientationDatas=list.toArray(new String[size]);
     //orientation���������
     menuCity = getLayoutInflater().inflate(R.layout.pop_wheel,null);
		popup = new PopupWindow(menuCity, -1,
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
		popup.showAtLocation(findViewById(R.id.mProject1), Gravity.BOTTOM,
				0, 0);
		initOrientationView();
	}
	public void initPop(View view){
		photograph = (TextView) view.findViewById(R.id.photograph);//����
		albums = (TextView) view.findViewById(R.id.albums);//���
		cancel= (LinearLayout) view.findViewById(R.id.cancel);//ȡ��
		photograph.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popWindow.dismiss();
				photoSaveName =String.valueOf(System.currentTimeMillis()) + ".png";
				Uri imageUri = null;
				Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				imageUri = Uri.fromFile(new File(photoSavePath,photoSaveName));
				openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(openCameraIntent, PHOTOTAKE);
			}
		});
		albums.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popWindow.dismiss();
				Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
				openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(openAlbumIntent, PHOTOZOOM);
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popWindow.dismiss();
				
			}
		});
	}
	/**
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
