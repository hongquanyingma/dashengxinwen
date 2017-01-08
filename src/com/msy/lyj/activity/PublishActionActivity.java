package com.msy.lyj.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import com.baidu.mapapi.search.core.PoiInfo;
import com.msy.lyj.R;
import com.msy.lyj.adapter.PublishSpinnerAdapter;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.baidumap.BaiduActivity;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;

public class PublishActionActivity extends BaseActivity implements BaseInterface, OnClickListener {

	private ImageView mPublishInsertPicture;// 点击添加图片
	private LinearLayout mShowTime;// 显示时间
	private DatePickerDialog mDatePickerDialog;// 日期选择对话框
	private TextView mPublishTime;// 显示时间信息的控件
	private Spinner mPublishSpinner;// Spinner控件
	private String mStrSpinnerType;// 活动选择的类型值
	private EditText mActionName, mActionDesc, mActionDetails, mActionRMB;// 键盘输入活动的名称、简介、详情
	private String mStrActionName, mStrActionDesc, mStrActionDetails, mStrActionRMB;// 手机输入获取的值信息
	private Button mPublishBtn;// 发布活动的点击按钮

	// 数据源    
	private List<String> datas;

	private String mStrDate;// 用户选择的日期
	private String mStrTime;// 用户选择的时间
	private TimePickerDialog mTimePickerDialog;// 时间选择对话框

	private LinearLayout mAddPicLin1, mAddPicLin2;
	private File savePublishPictureFile = new File("sdcard/actionpic.jpg");// 相片存储的位置

	private List<Bitmap> mBitmaps = new ArrayList<Bitmap>();;// 存储选择添加到活动中的相片的集合

	private LinearLayout mPublishSelectAddress;// 获取选择地点的控件

	private TextView mPublishSelectAddressTv;// 地图显示在发布活动中的信息控件
	private ActionInfo mActionInfo;// 包裹活动的bean类
	private String mStrActionAddress; // 选择活动的地点
	private String mStrActionCity; // 活动所在城市
	private PoiInfo mcurrentpoiInfo;

	private String[] filePublishPicPath;// 存储选择上传图片的url

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_public);

		initViews();
		initDatas();
		initViewOpras();
	}

	@Override
	public void initViews() {
		// 获取添加图片的控件
		mPublishInsertPicture = (ImageView) findById(R.id.act_public_action_iv_addpicture);

		// 获取放置图片的控件
		mAddPicLin1 = (LinearLayout) findById(R.id.act_publish_gather_send_img_add_lin1);
		mAddPicLin2 = (LinearLayout) findById(R.id.act_publish_gather_send_img_add_lin2);

		// 获取选择地点的控件对象
		mPublishSelectAddress = (LinearLayout) findById(R.id.act_public_action_lin_address);

		// 获取显示地点的文本对象
		mPublishSelectAddressTv = (TextView) findById(R.id.act_publish_SelectAddressTv);

		mPublishSelectAddress.setOnClickListener(this);

		// 设置添加图片的点击操作
		mPublishInsertPicture.setOnClickListener(this);

		// 获取活动的信息
		mActionName = (EditText) findById(R.id.act_publish_action_name);
		
		//获取活动简介
		mActionDesc = (EditText) findById(R.id.act_publish_action_desc);
		
		mActionDetails = (EditText) findById(R.id.act_publish_action_details);
		//mActionRMB = (EditText) findById(R.id.act_publish_action_rmb);
		mPublishBtn = (Button) findById(R.id.act_publish_action_button);

		// 时间的文本控件
		mPublishTime = (TextView) findById(R.id.act_publish_tv_time);

		// Spinner控件获取
		mPublishSpinner = (Spinner) findById(R.id.act_publish_spinner);

		// 获取时间的控件
		mShowTime = (LinearLayout) findById(R.id.act_public_action_lin_time);

	}

	@Override
	public void initDatas() {

		// 获取当前时间的方法
		final Calendar calendar = Calendar.getInstance();

		// 创建时间、时期选择对话框对象
		mDatePickerDialog = new DatePickerDialog(act, new OnDateSetListener() {

			// 用户最终选择时间的回调方法
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				if (calendar.get(Calendar.YEAR) > year) {

					toastInfoShort("时间选择有误");
					return;
				}

				if (calendar.get(Calendar.YEAR) == year) {

					if (calendar.get(Calendar.MONTH) > monthOfYear) {

						toastInfoShort("时间选择有误");
						return;
					} else if (calendar.get(Calendar.MONTH) == monthOfYear) {

						if (calendar.get(Calendar.DAY_OF_MONTH) > dayOfMonth) {

							toastInfoShort("时间选择有误");
							return;
						}

					}

				}

				mStrDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
				if (mTimePickerDialog != null) {
					mTimePickerDialog.show();
				}
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		mTimePickerDialog = new TimePickerDialog(act, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hour, int minute) {

				if (calendar.get(Calendar.HOUR_OF_DAY) > hour) {

					toastInfoShort("时间选择有误");
					return;
				} else if (calendar.get(Calendar.HOUR_OF_DAY) == hour) {

				}

				if (minute < 10) {
					mStrTime = mStrDate + "  " + hour + ":" + "0" + minute;
				} else {
					mStrTime = mStrDate + "  " + hour + ":" + minute;
				}

				// 显示时间到时间控件上
				mPublishTime.setText(mStrTime);
			}
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

		mShowTime.setOnClickListener(this);

		datas = new ArrayList<String>();
		datas.add("生活");
		datas.add("人文");
		datas.add("DIY");
		datas.add("健康");
		datas.add("经济");
		datas.add("体育");
		datas.add("历史");
		datas.add("娱乐");
		datas.add("科技");
		datas.add("国际");
		
		// Spinner的适配操作

		mPublishSpinner.setAdapter(new PublishSpinnerAdapter(this, datas));

		// 发布按钮的注册点击事件操作
		mPublishBtn.setOnClickListener(this);
	}

	@Override
	public void initViewOpras() {

		// mPublishSpinner的回调监听事件
		mPublishSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View item, int position, long flag) {

				// 获取用户点击选取的活动类型
				mStrSpinnerType = datas.get(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.act_public_action_lin_time:
			// 展示用户选择的时间和日期
			showTimes();
			break;

		case R.id.act_public_action_iv_addpicture:

			// 跳转到相册
			jumpPhoto();

			break;
		case R.id.act_public_action_lin_address:

			// 跳转的百度Map
			jumpBaiDuMap();

			break;

		case R.id.act_publish_action_button:

			// 发布活动
			publishAction();
			

			break;
		}

	}

	/**
	 * 
	 * 发布活动
	 */
	private void publishAction() {

		LogI("能进到发布活动点击事件");

		mActionInfo = new ActionInfo();

		mStrActionName = getTvContent(mActionName);
		//新闻简介
		mStrActionDesc = getTvContent(mActionDesc);
		
		mStrActionDetails = getTvContent(mActionDetails);
		mStrActionRMB = getTvContent(mActionRMB);

		if (mStrActionName.equals("")) {
			toastInfoShort("请输入活动名称");
			return;
		}

		if (mStrActionDesc.equals("")) {
			toastInfoShort("请输入活动简介");
			return;
		}

		if (mStrActionDetails.length() < 6) {

			toastInfoShort("活动详情最少输入六个数");
			return;
		}

		if (mStrActionAddress == null) {

			toastInfoShort("请选择活动地点");
			return;
		}

		if (mStrTime == null) {
			toastInfoShort("请选择活动开始时间");
			return;
		}
		LogI("选择图片的个数" + mBitmaps.size());

		Log.i("myTag", mBitmaps.size() + "");
		if (mBitmaps.size() < 1) {
			toastInfoShort("最少选择一张活动照片");
			return;
		}

		// 批量上传图片
		// 上传照片路径的url数组的集合
		filePublishPicPath = new String[mBitmaps.size()];

		// 把选择的图片上传到本地
		// 默认存储在sdcard下
		// File filepath = new
		// File(Environment.getExternalStorageDirectory().getAbsolutePath());
		for (int i = 0; i < mBitmaps.size(); i++) {

			File filepath = new File("sdcard/action/upload");

			/**
			 * mkdir:创建同级文件夹 mkdirs:创建分级文件夹
			 */
			if (!filepath.exists()) {

				filepath.mkdirs();
			}

			File filepic = new File(filepath, "action-" + i + ".jpg");
			try {
				mBitmaps.get(i).compress(CompressFormat.JPEG, 100, new FileOutputStream(filepic));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			filePublishPicPath[i] = filepic.getAbsolutePath();

		}

		/***
		 * 
		 * 上传图片到服务器的文件夹中
		 * 
		 * 
		 */

		final ProgressDialog progressDialog = progressDialogShows(false, "图片上传中", null);
		BmobFile.uploadBatch(filePublishPicPath, new UploadBatchListener() {

			int numPic = 0;

			@Override
			public void onSuccess(List<BmobFile> actionPics, List<String> urls) {

				progressDialog.setMessage("正在上传第" + numPic + "张图片");

				// 1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
				// 2、urls-上传文件的完整url地址
				/*
				 * if (urls.size() == filePublishPicPath.length) {//
				 * 如果数量相等，则代表文件全部上传完成 // do something progressDialog.dismiss();
				 * toastInfoShort("图片上传成功"); }
				 */

				if (numPic < mBitmaps.size()) {
					return;
				}

				progressDialog.dismiss();

				toastInfoShort("上传图片完成");
				// 上传完成

				// 把Bean对象上传到数据库表中
				// 设置活动属性

				// 获取当前的时间
				Calendar calendar = Calendar.getInstance();

				int years = calendar.get(Calendar.YEAR);
				
                int months= calendar.get(Calendar.MONTH);
                
                int days = calendar.get(Calendar.DAY_OF_MONTH);
				
				
				int hours = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				
				String date = years+"-"+months+"-"+days;

				String mCurrentTime = null;
				if (minute < 10) {
					mCurrentTime = date +"  "+ hours + ":0" + minute;
				} else {
					mCurrentTime =date +"  "+hours + ":" + minute;

				}
				//Log.i("myTag", "当前的时间" + mCurrentTime);

				mActionInfo.setActionCurrentTime(mCurrentTime);// 设置活动发布时的当前时间
				mActionInfo.setActionName(mStrActionName); // 设置活动名称
				mActionInfo.setActionClass(mStrSpinnerType); // 设置活动类别
				mActionInfo.setActionDesc(mStrActionDesc); // 设置活动简介
				mActionInfo.setActionDetails(mStrActionDetails);// 设置活动详情
				mActionInfo.setActionSite(mStrActionAddress); // 设置活动地址
				mActionInfo.setActionCity(mStrActionCity); // 设置活动所在城市
				//mActionInfo.setActionRMB(mStrActionRMB); // 设置活动话费金额
				mActionInfo.setActionTime(mStrTime); // 设置活动开始时间
				mActionInfo.setActionPicture(actionPics);// 设置上传多张BmobFile类型的图片
				mActionInfo.setActionUserId(MyApplication.userInfo.getObjectId()); // 设置上传活动的用户ID
				mActionInfo.setActionPoint(
						new BmobGeoPoint(mcurrentpoiInfo.location.longitude, mcurrentpoiInfo.location.latitude));

				// 保存到数据库表中
				mActionInfo.save(new SaveListener<String>() {

					@Override
					public void done(String objectId, BmobException ex) {
						if (ex == null) {
							toastInfoShort("创建数据成功：" + objectId);
							finish();

						} else {

							Log.i("bmob", "失败：" + ex.getMessage() + "," + ex.getErrorCode());
						}
					}
				});

			}

			@Override
			public void onError(int statuscode, String errormsg) {
				progressDialog.dismiss();
				toastInfoShort("错误码" + statuscode + ",错误描述：" + errormsg);
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
				// 1、curIndex--表示当前第几个文件正在上传
				// 2、curPercent--表示当前上传文件的进度值（百分比）
				// 3、total--表示总的上传文件数
				// 4、totalPercent--表示总的上传进度（百分比）

				numPic = curIndex;

			}
		});

		// 上传Bean类到数据库

	}

	// 跳转的百度Map
	/****
	 * 
	 * 
	 * 获取到连接BaiduMap的key值： 8uN4574koUdOZ1fG9u6Cc62eWwoYHjW6
	 */
	private void jumpBaiDuMap() {

		// 跳转到地图搜搜索页面
		act.startActivity(new Intent(act, BaiduActivity.class));

	}

	// 跳转到相册
	private void jumpPhoto() {

		// 点击用户头像，跳到相册
		Intent intent = new Intent(Intent.ACTION_PICK);

		// 类型
		intent.setType("image/*");

		// 加一个裁剪效果 (第二个属性改为true,不能实现剪切的效果)
		intent.putExtra("crop", "circleCrop");

		// 裁剪的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// 裁剪的像素点
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);

		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);

		// 存放的位置
		intent.putExtra("output", Uri.fromFile(savePublishPictureFile));

		startActivityForResult(intent, 666);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 666) {
			LogI("头像上传把！！！！！！！！！" + requestCode);

			// 上传到服务器的操作前的准备（把照片选择完之后发布在活动中）
			upLoadServiceMachine();

		}

	}

	private void upLoadServiceMachine() {
		// 获取资源文件，，，此处模拟去操作去相册选择，，供以后使用
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.more_juhui);

		// 去相册拍完照之后返回的照片
		Bitmap bitmap = BitmapFactory.decodeFile(savePublishPictureFile.getAbsolutePath());

		mBitmaps.add(bitmap);

		if (mBitmaps.size() < 4) {
			// 每一次移除上次的错误
			mAddPicLin1.removeAllViews();
			for (int i = 0; i < mBitmaps.size(); i++) {

				Bitmap currentBitmap = mBitmaps.get(i);
				ImageView imageView = new ImageView(act);

				// MarginLayoutParams marginLayoutParams = new
				// MarginLayoutParams(imageView.getLayoutParams());
				// marginLayoutParams.setMargins(10, 10, 10, 10);
				// imageView.setLayoutParams(marginLayoutParams);

				WindowManager manager = getWindowManager();

				int width1 = manager.getDefaultDisplay().getWidth() / 5;
				int height1 = width1 * 2 / 3;

				//
				// imageView.getLayoutParams().width = width1;
				// imageView.getLayoutParams().height = height1;

				imageView.setLayoutParams(new LayoutParams(width1, height1));

				imageView.setImageBitmap(currentBitmap);
				mAddPicLin1.addView(imageView);
			}

			mAddPicLin1.addView(mPublishInsertPicture);

		} else if (mBitmaps.size() == 4) {

			mAddPicLin1.removeView(mPublishInsertPicture);

			Bitmap currentBitmap2 = mBitmaps.get(3);
			ImageView imageView2 = new ImageView(act);

			WindowManager manager = getWindowManager();
			int width1 = manager.getDefaultDisplay().getWidth() / 5;

			int height1 = width1 * 2 / 3;

			imageView2.setLayoutParams(new LayoutParams(width1, height1));

			imageView2.setImageBitmap(currentBitmap2);
			mAddPicLin1.addView(imageView2);
			mAddPicLin2.addView(mPublishInsertPicture);

		} else if (4 < mBitmaps.size() && mBitmaps.size() <= 7) {

			mAddPicLin2.removeAllViews();
			for (int i = 4; i < mBitmaps.size(); i++) {

				Bitmap currentBitmap2 = mBitmaps.get(i);
				ImageView imageView2 = new ImageView(act);

				WindowManager manager = getWindowManager();
				int width1 = manager.getDefaultDisplay().getWidth() / 5;

				int height1 = width1 * 2 / 3;

				imageView2.setLayoutParams(new LayoutParams(width1, height1));

				imageView2.setImageBitmap(currentBitmap2);
				mAddPicLin2.addView(imageView2);
			}

			mAddPicLin2.addView(mPublishInsertPicture);
		} else if (mBitmaps.size() == 8) {

			mAddPicLin2.removeView(mPublishInsertPicture);

			Bitmap currentBitmap2 = mBitmaps.get(3);
			ImageView imageView2 = new ImageView(act);

			WindowManager manager = getWindowManager();
			int width1 = manager.getDefaultDisplay().getWidth() / 5;

			int height1 = width1 * 2 / 3;

			imageView2.setLayoutParams(new LayoutParams(width1, height1));

			imageView2.setImageBitmap(currentBitmap2);
			mAddPicLin2.addView(imageView2);
		}

		// 点击用户头像，跳到相册
		/*
		 * Intent intent = new Intent(Intent.ACTION_PICK);
		 * 
		 * // 类型 intent.setType("image/*");
		 * 
		 * // 加一个裁剪效果 (第二个属性改为true,不能实现剪切的效果) intent.putExtra("crop",
		 * "circleCrop");
		 * 
		 * // 裁剪的比例 intent.putExtra("aspectX", 1); intent.putExtra("aspectY",
		 * 1);
		 * 
		 * // 裁剪的像素点 intent.putExtra("outputX", 300); intent.putExtra("outputY",
		 * 300);
		 * 
		 * intent.putExtra("scale", true); intent.putExtra("return-data", true);
		 * intent.putExtra("noFaceDetection", true);
		 * 
		 * // 存放的位置 intent.putExtra("output", Uri.fromFile(saveUserHeadFile ));
		 * 
		 * startActivityForResult(intent, 666);
		 */
	}

	// 返回，销毁此页面
	public void fragmenthomebackClick(View v) {

		act.finish();

	}

	private void showTimes() {
		LogI("跳过来了");
		if (mDatePickerDialog != null) {
			mDatePickerDialog.show();
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		mStrActionAddress = (String) MyApplication.getDatas(false, "pointInfoname");
		// String address = (String) MyApplication.getDatas(true,
		// "pointInfoaddress");
		mStrActionCity = (String) MyApplication.getDatas(false, "pointInfonamecity");

		mcurrentpoiInfo = (PoiInfo) MyApplication.getDatas(false, "currentpoiInfo");

		if (mStrActionAddress == null) {
			return;
		}

		mPublishSelectAddressTv.setText(mStrActionAddress);

	}
}
