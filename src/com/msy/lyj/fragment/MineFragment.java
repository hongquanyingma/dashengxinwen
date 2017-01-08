package com.msy.lyj.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.msy.lyj.R;
import com.msy.lyj.adapter.MineFragmentGridViewAdapter;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseFragment;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.User;
import com.msy.lyj.utils.FindBmobUserInfoUtils;
import com.msy.lyj.utils.FindBmobUserInfoUtils.FindUserInfoListener;
import com.msy.lyj.utils.ImageLoaderutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class MineFragment extends BaseFragment implements BaseInterface,
		OnClickListener {

	private TextView mMineNick, mMineAccount;

	

	private List<String> datas;// 数据源

	private ImageView mMineHeadPhoto;

	private File saveUserHeadFile = new File("sdcard/userhead2.jpg");

	private ImageLoader imageLoader;// 图片加载对象

	private BmobFile bmobFile;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_mine, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initViews();
		initDatas();
		initViewOpras();
	}

	@Override
	public void initViews() {

		// 初始化控件对象

		mMineNick = (TextView) findById(R.id.fragment_mine_nick);
		mMineAccount = (TextView) findById(R.id.fragment_mine_account);
		mMineHeadPhoto = (ImageView) findById(R.id.fragment_mine_headphoto);

	}

	@Override
	public void initDatas() {

		/**
		 *--------------从本地获取图片操作，值得我们去思考--->这样去除了去服务器获取数据的时间，更能够有效使用，值得商榷
		 * 
		 * 从网络获取图片持久化的保存到本地的状态
		 * 
		 * 默认图片，失败的图片
		 */

		imageLoader = ImageLoaderutils.getInstance(act);

		// 用户数据的初始化，查询用户数据,实现FindBmobUserInfoUtils监听

		FindBmobUserInfoUtils.findUserInfos(
				MyApplication.userInfo.getObjectId(),
				new FindUserInfoListener() {

					@Override
					public void getUserInfo(User userInfo) {

						if (userInfo == null) {
							return;
						} else {
							LogI("userInfo对象中的数据：" + userInfo.toString());

							// 设置头像

							DisplayImageOptions options = ImageLoaderutils
									.getOpt(R.drawable.headpircture,
											R.drawable.beishang);
							
							BmobFile bmobFile2 = userInfo.getHeadpicture();
							String HeadPictureUrl = bmobFile2.getFileUrl();
							/***
							 * 
							 * 参数一：显示图片的uri 参数二：显示图片的控件 参数三：一些图片的操作
							 */
							imageLoader
									.displayImage(HeadPictureUrl, mMineHeadPhoto,
											options);

							mMineNick.setText(userInfo.getUsername());
							mMineAccount.setText(userInfo.getTelphone());
						}

					}
				});

		DisplayImageOptions displayImageOptions = ImageLoaderutils.getOpt(
				R.drawable.headpircture, R.drawable.beishang);

		/*
		 * // 通过全局的Application存储的数据，，，去获取已经在Application中存储的数据，这种在局部用的数据，存储在全局静态
		 * // 变量中，很值得提倡 User userInfo = MyApplication.userInfo; if (userInfo !=
		 * null) {
		 * 
		 * LogI("userInfo对象中的数据：" + userInfo.toString());
		 * 
		 * mMineNick.setText(userInfo.getUsername());
		 * mMineAccount.setText(userInfo.getPassword()); }
		 */
		// 初始化数据

		datas = new ArrayList<String>();

		datas.add("优惠");
		datas.add("收藏");
		datas.add("发起");
		datas.add("订阅");
		datas.add("集合");
		datas.add("设置");


		mMineHeadPhoto.setOnClickListener(this);

	}

	@Override
	public void initViewOpras() {
		// TODO Auto-generated method stub

	}

	// MineFragment中存在的点击事件操作
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		// 更换头像，通过Intent意图对像跳转到相册中去选择
		case R.id.fragment_mine_headphoto:

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
			intent.putExtra("outputX", 150);
			intent.putExtra("outputY", 150);

			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			intent.putExtra("noFaceDetection", true);

			// 存放的位置
			intent.putExtra("output", Uri.fromFile(saveUserHeadFile));

			startActivityForResult(intent, 888);

			/*
			 * Intent intent = new Intent("com.android.camera.action.CROP");
			 * intent.setDataAndType(Uri.parse("file://" + "/" +
			 * Environment.getExternalStorageDirectory().getPath() + "/" +
			 * "headphoto3.jpg"), "image/*"); //
			 * crop为true是设置在开启的intent中设置显示的view可以剪裁 intent.putExtra("crop",
			 * "true");
			 * 
			 * // aspectX aspectY 是宽高的比例 intent.putExtra("aspectX", 1);
			 * intent.putExtra("aspectY", 1);
			 * 
			 * // outputX,outputY 是剪裁图片的宽高 intent.putExtra("outputX", 300);
			 * intent.putExtra("outputY", 300);
			 *//**
			 * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
			 * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
			 */
			/*
			 * //intent.putExtra("return-data", true);
			 * 
			 * //uritempFile为Uri类变量，实例化uritempFile ;
			 * intent.putExtra(MediaStore.EXTRA_OUTPUT,
			 * 
			 * Uri.parse("file://" + "/" +
			 * Environment.getExternalStorageDirectory().getPath() + "/" +
			 * "headphoto3.jpg")); intent.putExtra("outputFormat",
			 * Bitmap.CompressFormat.JPEG.toString());
			 * 
			 * startActivityForResult(intent, 888);
			 */

			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 888) {
			LogI("头像上传把！！！！！！！！！" + requestCode + "1");
			userHeadUpLoading();

		}

	}

	// 选完头像回来了

	// 上传服务器

	// 更新用户信息
	private void userHeadUpLoading() {

		// toastInfoShort("头像选择完毕");

		final ProgressDialog progressDialog = act.progressDialogShows(true,
				null, null);
		// 上传图片到Bmob服务器

		// 创建文件对象
		bmobFile = new BmobFile(saveUserHeadFile);

		// 上传单一文件

		// bmobFile.uploadblock(new UploadFileListener() {
		//
		// @Override
		// public void done(BmobException ex) {
		//
		// if (ex == null) {
		// //bmobFile.getFileUrl()--返回的上传文件的完整地址
		// toastInfoShort("上传文件成功:" + bmobFile.getFileUrl());
		// }else {
		//
		// toastInfoShort("上传文件失败:" + ex.getLocalizedMessage());
		// }
		//
		// }
		//
		// @Override
		// public void onProgress(Integer value) {
		// super.onProgress(value);
		//
		// //返回上传的百分比
		// }
		// });

		final User user = new User();
		/* User user = MyApplication.userInfo; */
		user.setHeadpicture(new BmobFile(saveUserHeadFile));

		user.getHeadpicture().upload(new UploadFileListener() {

			@Override
			public void done(BmobException ex) {

				// 上传成功
				if (ex == null) {

					progressDialog.dismiss();
					Toast.makeText(act, "头像上传文件成功", 0).show();
					// 更新Bmob用户信息表

					/***
					 * 更新表的ObjectId
					 * 
					 * 更新结果的回调方法
					 * 
					 */
					if (MyApplication.userInfo == null) {
						LogI("ObjectId为:"
								+ MyApplication.userInfo.getObjectId());
						return;
					}
					if (MyApplication.userInfo != null) {
						LogI("ObjectId:" + MyApplication.userInfo.getObjectId());
					}
					user.update(MyApplication.userInfo.getObjectId(),
							new UpdateListener() {

								@Override
								public void done(BmobException ex) {

									if (ex == null) {
										// Toast.makeText(act, "更新用户列表成功",
										// 0).show();
										LogI("更新用户列表成功");
										// 更新用户头像的UI

										Bitmap bitmap = BitmapFactory
												.decodeFile(bmobFile
														.getLocalFile()
														.getAbsolutePath());

										mMineHeadPhoto.setImageBitmap(bitmap);

									} else {

										Toast.makeText(act, "更新用户列表失败", 0)
												.show();
										LogI("更新用户表失败的原因" + ex);
									}

								}
							});

				} else {

					progressDialog.dismiss();
					Toast.makeText(act, "头像上传失败" + ex, 0).show();
					Log.i("myTag", ex + "!!!!!!!!!!!");
				}

			}
		});

	}
}
