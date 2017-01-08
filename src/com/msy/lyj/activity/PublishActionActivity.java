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

	private ImageView mPublishInsertPicture;// ������ͼƬ
	private LinearLayout mShowTime;// ��ʾʱ��
	private DatePickerDialog mDatePickerDialog;// ����ѡ��Ի���
	private TextView mPublishTime;// ��ʾʱ����Ϣ�Ŀؼ�
	private Spinner mPublishSpinner;// Spinner�ؼ�
	private String mStrSpinnerType;// �ѡ�������ֵ
	private EditText mActionName, mActionDesc, mActionDetails, mActionRMB;// �������������ơ���顢����
	private String mStrActionName, mStrActionDesc, mStrActionDetails, mStrActionRMB;// �ֻ������ȡ��ֵ��Ϣ
	private Button mPublishBtn;// ������ĵ����ť

	// ����Դ    
	private List<String> datas;

	private String mStrDate;// �û�ѡ�������
	private String mStrTime;// �û�ѡ���ʱ��
	private TimePickerDialog mTimePickerDialog;// ʱ��ѡ��Ի���

	private LinearLayout mAddPicLin1, mAddPicLin2;
	private File savePublishPictureFile = new File("sdcard/actionpic.jpg");// ��Ƭ�洢��λ��

	private List<Bitmap> mBitmaps = new ArrayList<Bitmap>();;// �洢ѡ����ӵ���е���Ƭ�ļ���

	private LinearLayout mPublishSelectAddress;// ��ȡѡ��ص�Ŀؼ�

	private TextView mPublishSelectAddressTv;// ��ͼ��ʾ�ڷ�����е���Ϣ�ؼ�
	private ActionInfo mActionInfo;// �������bean��
	private String mStrActionAddress; // ѡ���ĵص�
	private String mStrActionCity; // ����ڳ���
	private PoiInfo mcurrentpoiInfo;

	private String[] filePublishPicPath;// �洢ѡ���ϴ�ͼƬ��url

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
		// ��ȡ���ͼƬ�Ŀؼ�
		mPublishInsertPicture = (ImageView) findById(R.id.act_public_action_iv_addpicture);

		// ��ȡ����ͼƬ�Ŀؼ�
		mAddPicLin1 = (LinearLayout) findById(R.id.act_publish_gather_send_img_add_lin1);
		mAddPicLin2 = (LinearLayout) findById(R.id.act_publish_gather_send_img_add_lin2);

		// ��ȡѡ��ص�Ŀؼ�����
		mPublishSelectAddress = (LinearLayout) findById(R.id.act_public_action_lin_address);

		// ��ȡ��ʾ�ص���ı�����
		mPublishSelectAddressTv = (TextView) findById(R.id.act_publish_SelectAddressTv);

		mPublishSelectAddress.setOnClickListener(this);

		// �������ͼƬ�ĵ������
		mPublishInsertPicture.setOnClickListener(this);

		// ��ȡ�����Ϣ
		mActionName = (EditText) findById(R.id.act_publish_action_name);
		
		//��ȡ����
		mActionDesc = (EditText) findById(R.id.act_publish_action_desc);
		
		mActionDetails = (EditText) findById(R.id.act_publish_action_details);
		//mActionRMB = (EditText) findById(R.id.act_publish_action_rmb);
		mPublishBtn = (Button) findById(R.id.act_publish_action_button);

		// ʱ����ı��ؼ�
		mPublishTime = (TextView) findById(R.id.act_publish_tv_time);

		// Spinner�ؼ���ȡ
		mPublishSpinner = (Spinner) findById(R.id.act_publish_spinner);

		// ��ȡʱ��Ŀؼ�
		mShowTime = (LinearLayout) findById(R.id.act_public_action_lin_time);

	}

	@Override
	public void initDatas() {

		// ��ȡ��ǰʱ��ķ���
		final Calendar calendar = Calendar.getInstance();

		// ����ʱ�䡢ʱ��ѡ��Ի������
		mDatePickerDialog = new DatePickerDialog(act, new OnDateSetListener() {

			// �û�����ѡ��ʱ��Ļص�����
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				if (calendar.get(Calendar.YEAR) > year) {

					toastInfoShort("ʱ��ѡ������");
					return;
				}

				if (calendar.get(Calendar.YEAR) == year) {

					if (calendar.get(Calendar.MONTH) > monthOfYear) {

						toastInfoShort("ʱ��ѡ������");
						return;
					} else if (calendar.get(Calendar.MONTH) == monthOfYear) {

						if (calendar.get(Calendar.DAY_OF_MONTH) > dayOfMonth) {

							toastInfoShort("ʱ��ѡ������");
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

					toastInfoShort("ʱ��ѡ������");
					return;
				} else if (calendar.get(Calendar.HOUR_OF_DAY) == hour) {

				}

				if (minute < 10) {
					mStrTime = mStrDate + "  " + hour + ":" + "0" + minute;
				} else {
					mStrTime = mStrDate + "  " + hour + ":" + minute;
				}

				// ��ʾʱ�䵽ʱ��ؼ���
				mPublishTime.setText(mStrTime);
			}
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

		mShowTime.setOnClickListener(this);

		datas = new ArrayList<String>();
		datas.add("����");
		datas.add("����");
		datas.add("DIY");
		datas.add("����");
		datas.add("����");
		datas.add("����");
		datas.add("��ʷ");
		datas.add("����");
		datas.add("�Ƽ�");
		datas.add("����");
		
		// Spinner���������

		mPublishSpinner.setAdapter(new PublishSpinnerAdapter(this, datas));

		// ������ť��ע�����¼�����
		mPublishBtn.setOnClickListener(this);
	}

	@Override
	public void initViewOpras() {

		// mPublishSpinner�Ļص������¼�
		mPublishSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View item, int position, long flag) {

				// ��ȡ�û����ѡȡ�Ļ����
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
			// չʾ�û�ѡ���ʱ�������
			showTimes();
			break;

		case R.id.act_public_action_iv_addpicture:

			// ��ת�����
			jumpPhoto();

			break;
		case R.id.act_public_action_lin_address:

			// ��ת�İٶ�Map
			jumpBaiDuMap();

			break;

		case R.id.act_publish_action_button:

			// �����
			publishAction();
			

			break;
		}

	}

	/**
	 * 
	 * �����
	 */
	private void publishAction() {

		LogI("�ܽ������������¼�");

		mActionInfo = new ActionInfo();

		mStrActionName = getTvContent(mActionName);
		//���ż��
		mStrActionDesc = getTvContent(mActionDesc);
		
		mStrActionDetails = getTvContent(mActionDetails);
		mStrActionRMB = getTvContent(mActionRMB);

		if (mStrActionName.equals("")) {
			toastInfoShort("����������");
			return;
		}

		if (mStrActionDesc.equals("")) {
			toastInfoShort("���������");
			return;
		}

		if (mStrActionDetails.length() < 6) {

			toastInfoShort("�������������������");
			return;
		}

		if (mStrActionAddress == null) {

			toastInfoShort("��ѡ���ص�");
			return;
		}

		if (mStrTime == null) {
			toastInfoShort("��ѡ����ʼʱ��");
			return;
		}
		LogI("ѡ��ͼƬ�ĸ���" + mBitmaps.size());

		Log.i("myTag", mBitmaps.size() + "");
		if (mBitmaps.size() < 1) {
			toastInfoShort("����ѡ��һ�Ż��Ƭ");
			return;
		}

		// �����ϴ�ͼƬ
		// �ϴ���Ƭ·����url����ļ���
		filePublishPicPath = new String[mBitmaps.size()];

		// ��ѡ���ͼƬ�ϴ�������
		// Ĭ�ϴ洢��sdcard��
		// File filepath = new
		// File(Environment.getExternalStorageDirectory().getAbsolutePath());
		for (int i = 0; i < mBitmaps.size(); i++) {

			File filepath = new File("sdcard/action/upload");

			/**
			 * mkdir:����ͬ���ļ��� mkdirs:�����ּ��ļ���
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
		 * �ϴ�ͼƬ�����������ļ�����
		 * 
		 * 
		 */

		final ProgressDialog progressDialog = progressDialogShows(false, "ͼƬ�ϴ���", null);
		BmobFile.uploadBatch(filePublishPicPath, new UploadBatchListener() {

			int numPic = 0;

			@Override
			public void onSuccess(List<BmobFile> actionPics, List<String> urls) {

				progressDialog.setMessage("�����ϴ���" + numPic + "��ͼƬ");

				// 1��files-�ϴ���ɺ��BmobFile���ϣ���Ϊ�˷����Ҷ����ϴ�������ݽ��в�������������Խ����ļ����浽����
				// 2��urls-�ϴ��ļ�������url��ַ
				/*
				 * if (urls.size() == filePublishPicPath.length) {//
				 * ���������ȣ�������ļ�ȫ���ϴ���� // do something progressDialog.dismiss();
				 * toastInfoShort("ͼƬ�ϴ��ɹ�"); }
				 */

				if (numPic < mBitmaps.size()) {
					return;
				}

				progressDialog.dismiss();

				toastInfoShort("�ϴ�ͼƬ���");
				// �ϴ����

				// ��Bean�����ϴ������ݿ����
				// ���û����

				// ��ȡ��ǰ��ʱ��
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
				//Log.i("myTag", "��ǰ��ʱ��" + mCurrentTime);

				mActionInfo.setActionCurrentTime(mCurrentTime);// ���û����ʱ�ĵ�ǰʱ��
				mActionInfo.setActionName(mStrActionName); // ���û����
				mActionInfo.setActionClass(mStrSpinnerType); // ���û���
				mActionInfo.setActionDesc(mStrActionDesc); // ���û���
				mActionInfo.setActionDetails(mStrActionDetails);// ���û����
				mActionInfo.setActionSite(mStrActionAddress); // ���û��ַ
				mActionInfo.setActionCity(mStrActionCity); // ���û���ڳ���
				//mActionInfo.setActionRMB(mStrActionRMB); // ���û���ѽ��
				mActionInfo.setActionTime(mStrTime); // ���û��ʼʱ��
				mActionInfo.setActionPicture(actionPics);// �����ϴ�����BmobFile���͵�ͼƬ
				mActionInfo.setActionUserId(MyApplication.userInfo.getObjectId()); // �����ϴ�����û�ID
				mActionInfo.setActionPoint(
						new BmobGeoPoint(mcurrentpoiInfo.location.longitude, mcurrentpoiInfo.location.latitude));

				// ���浽���ݿ����
				mActionInfo.save(new SaveListener<String>() {

					@Override
					public void done(String objectId, BmobException ex) {
						if (ex == null) {
							toastInfoShort("�������ݳɹ���" + objectId);
							finish();

						} else {

							Log.i("bmob", "ʧ�ܣ�" + ex.getMessage() + "," + ex.getErrorCode());
						}
					}
				});

			}

			@Override
			public void onError(int statuscode, String errormsg) {
				progressDialog.dismiss();
				toastInfoShort("������" + statuscode + ",����������" + errormsg);
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
				// 1��curIndex--��ʾ��ǰ�ڼ����ļ������ϴ�
				// 2��curPercent--��ʾ��ǰ�ϴ��ļ��Ľ���ֵ���ٷֱȣ�
				// 3��total--��ʾ�ܵ��ϴ��ļ���
				// 4��totalPercent--��ʾ�ܵ��ϴ����ȣ��ٷֱȣ�

				numPic = curIndex;

			}
		});

		// �ϴ�Bean�ൽ���ݿ�

	}

	// ��ת�İٶ�Map
	/****
	 * 
	 * 
	 * ��ȡ������BaiduMap��keyֵ�� 8uN4574koUdOZ1fG9u6Cc62eWwoYHjW6
	 */
	private void jumpBaiDuMap() {

		// ��ת����ͼ������ҳ��
		act.startActivity(new Intent(act, BaiduActivity.class));

	}

	// ��ת�����
	private void jumpPhoto() {

		// ����û�ͷ���������
		Intent intent = new Intent(Intent.ACTION_PICK);

		// ����
		intent.setType("image/*");

		// ��һ���ü�Ч�� (�ڶ������Ը�Ϊtrue,����ʵ�ּ��е�Ч��)
		intent.putExtra("crop", "circleCrop");

		// �ü��ı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// �ü������ص�
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);

		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);

		// ��ŵ�λ��
		intent.putExtra("output", Uri.fromFile(savePublishPictureFile));

		startActivityForResult(intent, 666);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 666) {
			LogI("ͷ���ϴ��ѣ�����������������" + requestCode);

			// �ϴ����������Ĳ���ǰ��׼��������Ƭѡ����֮�󷢲��ڻ�У�
			upLoadServiceMachine();

		}

	}

	private void upLoadServiceMachine() {
		// ��ȡ��Դ�ļ��������˴�ģ��ȥ����ȥ���ѡ�񣬣����Ժ�ʹ��
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.more_juhui);

		// ȥ���������֮�󷵻ص���Ƭ
		Bitmap bitmap = BitmapFactory.decodeFile(savePublishPictureFile.getAbsolutePath());

		mBitmaps.add(bitmap);

		if (mBitmaps.size() < 4) {
			// ÿһ���Ƴ��ϴεĴ���
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

		// ����û�ͷ���������
		/*
		 * Intent intent = new Intent(Intent.ACTION_PICK);
		 * 
		 * // ���� intent.setType("image/*");
		 * 
		 * // ��һ���ü�Ч�� (�ڶ������Ը�Ϊtrue,����ʵ�ּ��е�Ч��) intent.putExtra("crop",
		 * "circleCrop");
		 * 
		 * // �ü��ı��� intent.putExtra("aspectX", 1); intent.putExtra("aspectY",
		 * 1);
		 * 
		 * // �ü������ص� intent.putExtra("outputX", 300); intent.putExtra("outputY",
		 * 300);
		 * 
		 * intent.putExtra("scale", true); intent.putExtra("return-data", true);
		 * intent.putExtra("noFaceDetection", true);
		 * 
		 * // ��ŵ�λ�� intent.putExtra("output", Uri.fromFile(saveUserHeadFile ));
		 * 
		 * startActivityForResult(intent, 666);
		 */
	}

	// ���أ����ٴ�ҳ��
	public void fragmenthomebackClick(View v) {

		act.finish();

	}

	private void showTimes() {
		LogI("��������");
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
