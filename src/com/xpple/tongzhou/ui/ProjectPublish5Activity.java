package com.xpple.tongzhou.ui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.R.integer;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.xpple.tongzhou.R;
import com.xpple.tongzhou.adapt.PublishProjectImagesAdater;
import com.xpple.tongzhou.bean.City;
import com.xpple.tongzhou.bean.Partners;
import com.xpple.tongzhou.bean.ProductsExhibition;
import com.xpple.tongzhou.bean.PublishProject;
import com.xpple.tongzhou.ui.SelectCityActivity.ListAdapter;
import com.xpple.tongzhou.view.HeaderLayout.onLeftImageButtonClickListener;
import com.xpple.tongzhou.view.HeaderLayout.onRightImageButtonClickListener;
import com.xpple.tongzhou.view.MyPopupWindow;

public class ProjectPublish5Activity extends BaseActivity {
    private static int MAX_NUMBER=9;
	/**拍照的照片存储位�? */
	private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory()
			+ "/qebb/Camera");
	private static  File mCurrentPhotoFile;// 照相机拍照得到的图片
	private PublishProject mPublishProject=new PublishProject();
	private ArrayList<Partners> mSummonPartners=new ArrayList<Partners>();
	
	private ImageButton AddAndroid;
	private ImageButton AddApple;
	private ImageButton AddComputer;
	
	private MyPopupWindow myPopwindow;
	
	private PopupWindow progressBar;
	
	private PopupWindow popWindow;
	private LayoutInflater layoutInflater;
	private TextView photograph,albums;
	private LinearLayout cancel;
	
	private ArrayList<String> imageArray=new ArrayList<String>(); 
	private GridView myGridView;
	private PublishProjectImagesAdater imagesAdapt;
	private ProductsExhibition myProductsExhibition=new ProductsExhibition();	
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.publish_project5);
	        
	        Bmob.initialize(getApplicationContext(),"5fa691ac6ed1922b178d0a8e557e4898");
	        
	        Intent myIntent=getIntent();
	        Bundle myBundle=myIntent.getExtras();
	        mPublishProject=(PublishProject)myBundle.get("mPublishProject");
	        mSummonPartners=(ArrayList<Partners>)myBundle.get("mSummonPartners");
	        
	        AddAndroid=(ImageButton)findViewById(R.id.AddAndroid);
	        AddApple=(ImageButton)findViewById(R.id.AddApple);
	        AddComputer=(ImageButton)findViewById(R.id.AddComputer);
	        //产品展示图片GridView控件
	        myGridView=(GridView)findViewById(R.id.gridview_child);
	        imageArray.add("");
	        imagesAdapt=new PublishProjectImagesAdater(this, imageArray, myGridView);
	        myGridView.setAdapter(imagesAdapt);
	        imagesAdapt.notifyDataSetChanged();
	        myGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if (arg2==imageArray.size()-1) {
						showPopupWindow( myGridView);
					}
				}
			});
	        //initImageviews(imageArray);
	        
	        AddAndroid.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myPopwindow = new MyPopupWindow(ProjectPublish5Activity.this, new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {	
							String lj=myPopwindow.getEditText();
							if (!lj.equals("")) {
								AddAndroid.setImageResource(R.drawable.publish_project5_11);
								mPublishProject.setProductAndroid(lj);
							}else {
								AddAndroid.setImageResource(R.drawable.publish_project5_1);
								mPublishProject.setProductAndroid(null);
							}
							myPopwindow.dismiss();
						}
					});
					if (mPublishProject.getProductAndroid()!=null) {
						myPopwindow.setEditText(mPublishProject.getProductAndroid());
					}
					myPopwindow.showAtLocation(AddAndroid,Gravity.BOTTOM, 0, 0);
				}
			});
	        AddApple.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myPopwindow = new MyPopupWindow(ProjectPublish5Activity.this, new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {	
							String lj=myPopwindow.getEditText();
							if (!lj.equals("")) {
								AddApple.setImageResource(R.drawable.publish_project5_22);
								mPublishProject.setProductApple(lj);
							}else {
								AddApple.setImageResource(R.drawable.publish_project5_2);
								mPublishProject.setProductApple(null);
							}
							myPopwindow.dismiss();
						}
					});
					if (mPublishProject.getProductApple()!=null) {
						myPopwindow.setEditText(mPublishProject.getProductApple());
					}
					myPopwindow.showAtLocation(AddAndroid,Gravity.BOTTOM, 0, 0);
				}
			});
            AddComputer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					myPopwindow = new MyPopupWindow(ProjectPublish5Activity.this, new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {	
							String lj=myPopwindow.getEditText();
							if (!lj.equals("")) {
								AddComputer.setImageResource(R.drawable.publish_project5_33);
								mPublishProject.setProductCumputer(lj);
							}else {
								AddComputer.setImageResource(R.drawable.publish_project5_3);
								mPublishProject.setProductCumputer(null);
							}
							myPopwindow.dismiss();
						}
					});
					if (mPublishProject.getProductCumputer()!=null) {
						myPopwindow.setEditText(mPublishProject.getProductCumputer());
					}
					myPopwindow.showAtLocation(AddAndroid,Gravity.BOTTOM, 0, 0);
				}
			});
	        initPublishProjectHead("发布项目5/5", "发布",
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
                            if (!check()) {
								return;
							}
                            handler2.sendEmptyMessage(1);
                            new Thread() {
                                @Override
                                public void run() {
                                	handler2.sendEmptyMessage(1);
                                	summit();
                                }
                            }.start();
						}
					});
	 }
	 private void summit() {
			// TODO Auto-generated method stub
		 BmobFile bmobFile=mPublishProject.getBmobFile();
			bmobFile.uploadblock(ProjectPublish5Activity.this, new UploadFileListener() {

			    @Override
			    public void onSuccess() {
			        // TODO Auto-generated method stub
			        mPublishProject.save(ProjectPublish5Activity.this, new SaveListener() {

			            @Override
			            public void onSuccess() {
			                //ShowToast("添加数据成功，返回objectId为："+mPublishProject.getObjectId());
			                String belongString=mPublishProject.getObjectId();
			                	 //mSummonPartners=(ArrayList<Partners>)myBundle.get("mSummonPartners");
			                for (Partners ele : mSummonPartners) {
									ele.setbelongId(belongString);
									ele.save(ProjectPublish5Activity.this, new SaveListener() {
										
										@Override
										public void onSuccess() {
											// TODO Auto-generated method stub
											//ShowToast("添加数据成功，返回objectId为：");
										}
										
										@Override
										public void onFailure(int arg0, String arg1) {
											// TODO Auto-generated method stub
											Log.e("aaaaaaaaa", arg0+":"+arg1);
										}
								    });
							  }
				             myProductsExhibition.setbelongId(belongString);
					         int number=imageArray.size()-1;
					         String[] array=new String[number];
					         for (int i = 0; i <number; i++) {
					        	 array[i]=imageArray.get(i);
							 }
					         Bmob.uploadBatch(ProjectPublish5Activity.this, array,new UploadBatchListener() {
								
								@Override
								public void onSuccess(List<BmobFile> arg0, List<String> arg1) {
									// TODO Auto-generated method stub
									if (arg1.size()==imageArray.size()-1) {
										for (int i = 0; i < imageArray.size()-1; i++) {        
											  myProductsExhibition.setImagePicture(arg0.get(i));
											  myProductsExhibition.save(ProjectPublish5Activity.this,new SaveListener() {
													@Override
													public void onSuccess() {
														// TODO Auto-generated method stub
													}
													@Override
													public void onFailure(int arg0, String arg1) {
														// TODO Auto-generated method stub
														 Message message=new Message();  
										                    Bundle bundle=new Bundle();  
										                    bundle.putString("bug", arg0+":"+arg1);  
										                    message.setData(bundle);//bundle传值，耗时，效率低  
										                    message.what=4;//标志是哪个线程传数据  
										                    handler2.sendMessage(message);//发送message信息  
													}
												});
											  handler2.sendEmptyMessage(2);
										}
									}
								}
							
							@Override
							public void onProgress(int arg0, int arg1, int arg2, int arg3) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onError(int arg0, String arg1) {
								// TODO Auto-generated method stub
								 Message message=new Message();  
				                    Bundle bundle=new Bundle();  
				                    bundle.putString("bug", arg0+":"+arg1);  
				                    message.setData(bundle);//bundle传值，耗时，效率低  
				                    message.what=4;//标志是哪个线程传数据  
				                    handler2.sendMessage(message);//发送message信息  
							}
						});
			            }

			            @Override
			            public void onFailure(int code, String arg0) {
			                // TODO Auto-generated method stub
			            	 Message message=new Message();  
			                    Bundle bundle=new Bundle();  
			                    bundle.putString("bug",  code+":"+arg0);  
			                    message.setData(bundle);//bundle传值，耗时，效率低  
			                    message.what=4;//标志是哪个线程传数据  
			                    handler2.sendMessage(message);//发送message信息  
			            }
			        });
			    }

			    @Override
			    public void onProgress(Integer arg0) {
			        // TODO Auto-generated method stub
			    }

			    @Override
			    public void onFailure(int arg0, String arg1) {
			        // TODO Auto-generated method stub
			    	Log.e("aaaaaaaaa", arg0+":"+arg1);
			    }

			});

		}
	protected boolean check() {
		// TODO Auto-generated method stub
		 if (mPublishProject.getProductAndroid()!=null||
				 mPublishProject.getProductApple()!=null||
				 mPublishProject.getProductCumputer()!=null) {
		      return true;
		 }
		ShowToast("至少输入一个产品链接");
		return false;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
	        try {
		        switch (resultCode) {
			        case Activity.RESULT_OK:
			        	if (requestCode==2) {
			        		String path=mCurrentPhotoFile.getAbsolutePath();
			        		imageArray.add(imageArray.size()-1, path);
							//myGridView.setAdapter(imagesAdapt);
							imagesAdapt.notifyDataSetChanged();
						}
			        case 10:
			        	ArrayList<String> mylist= data.getStringArrayListExtra("images");
			        	imageArray.addAll(imageArray.size()-1, mylist);
			        	imagesAdapt.notifyDataSetChanged();
			        	break;
			        default:
						break;
	        }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	/** 
	 * 获取原图片存储路�? 
	 * @return 
	 */  
	private String getPhotopath() {  
		// 文件夹路�?  
		if(!PHOTO_DIR.exists()){
			PHOTO_DIR.mkdirs();
		}
		mCurrentPhotoFile = new File(PHOTO_DIR,getPhotoFileName());
		String pathUrl = mCurrentPhotoFile.getAbsolutePath();  
		try {
			mCurrentPhotoFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathUrl;  
	}  
	@SuppressLint("SimpleDateFormat")
	private  String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return "/"+dateFormat.format(date) + ".jpg";
	}
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
	 private void showProgressWindow(View parent){
	    	
			if (progressBar == null) {
				layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = layoutInflater.inflate(R.layout.progress_bar,null);
				progressBar = new PopupWindow(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
			}	
			progressBar.setFocusable(true);
			progressBar.showAtLocation(parent, Gravity.CENTER, 0, 0);
		}
	 public void initPop(View view){
			photograph = (TextView) view.findViewById(R.id.photograph);//拍照
			albums = (TextView) view.findViewById(R.id.albums);//相册
			cancel= (LinearLayout) view.findViewById(R.id.cancel);//取消
			photograph.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					popWindow.dismiss();
					Uri imageUri = null;
					mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					imageUri = Uri.fromFile(mCurrentPhotoFile);
					openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, 2);
				}
			});
			albums.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					popWindow.dismiss();
					Intent myIntent = new Intent(ProjectPublish5Activity.this,ShowImageActivity.class);
					startActivityForResult(myIntent, 1);
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					popWindow.dismiss();
					
				}
			});
			
		}
	 Handler handler2 = new Handler() {
	        public void handleMessage(android.os.Message msg) {

	            switch (msg.what) {
	                case 1:
	                	//ShowToast("正在上传");
	                	showProgressWindow(myGridView);
	                	break;
	                case 2:
	                	progressBar.dismiss();
	                	ShowToast("项目发布成功");
	                	break;
	                case 3:
	                	progressBar.dismiss();
	                	ShowToast("发布失败");
	                	break;
	                case 4:
	                	ShowToast(msg.getData().getString("bug"));
	                	break;
	                default:
	                	ShowToast(msg.what+"");
	                    break;
	            }
	        }
	    };
}