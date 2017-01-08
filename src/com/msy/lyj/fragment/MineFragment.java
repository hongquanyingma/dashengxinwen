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

	

	private List<String> datas;// ����Դ

	private ImageView mMineHeadPhoto;

	private File saveUserHeadFile = new File("sdcard/userhead2.jpg");

	private ImageLoader imageLoader;// ͼƬ���ض���

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

		// ��ʼ���ؼ�����

		mMineNick = (TextView) findById(R.id.fragment_mine_nick);
		mMineAccount = (TextView) findById(R.id.fragment_mine_account);
		mMineHeadPhoto = (ImageView) findById(R.id.fragment_mine_headphoto);

	}

	@Override
	public void initDatas() {

		/**
		 *--------------�ӱ��ػ�ȡͼƬ������ֵ������ȥ˼��--->����ȥ����ȥ��������ȡ���ݵ�ʱ�䣬���ܹ���Чʹ�ã�ֵ����ȶ
		 * 
		 * �������ȡͼƬ�־û��ı��浽���ص�״̬
		 * 
		 * Ĭ��ͼƬ��ʧ�ܵ�ͼƬ
		 */

		imageLoader = ImageLoaderutils.getInstance(act);

		// �û����ݵĳ�ʼ������ѯ�û�����,ʵ��FindBmobUserInfoUtils����

		FindBmobUserInfoUtils.findUserInfos(
				MyApplication.userInfo.getObjectId(),
				new FindUserInfoListener() {

					@Override
					public void getUserInfo(User userInfo) {

						if (userInfo == null) {
							return;
						} else {
							LogI("userInfo�����е����ݣ�" + userInfo.toString());

							// ����ͷ��

							DisplayImageOptions options = ImageLoaderutils
									.getOpt(R.drawable.headpircture,
											R.drawable.beishang);
							
							BmobFile bmobFile2 = userInfo.getHeadpicture();
							String HeadPictureUrl = bmobFile2.getFileUrl();
							/***
							 * 
							 * ����һ����ʾͼƬ��uri ����������ʾͼƬ�Ŀؼ� ��������һЩͼƬ�Ĳ���
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
		 * // ͨ��ȫ�ֵ�Application�洢�����ݣ�����ȥ��ȡ�Ѿ���Application�д洢�����ݣ������ھֲ��õ����ݣ��洢��ȫ�־�̬
		 * // �����У���ֵ���ᳫ User userInfo = MyApplication.userInfo; if (userInfo !=
		 * null) {
		 * 
		 * LogI("userInfo�����е����ݣ�" + userInfo.toString());
		 * 
		 * mMineNick.setText(userInfo.getUsername());
		 * mMineAccount.setText(userInfo.getPassword()); }
		 */
		// ��ʼ������

		datas = new ArrayList<String>();

		datas.add("�Ż�");
		datas.add("�ղ�");
		datas.add("����");
		datas.add("����");
		datas.add("����");
		datas.add("����");


		mMineHeadPhoto.setOnClickListener(this);

	}

	@Override
	public void initViewOpras() {
		// TODO Auto-generated method stub

	}

	// MineFragment�д��ڵĵ���¼�����
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		// ����ͷ��ͨ��Intent��ͼ������ת�������ȥѡ��
		case R.id.fragment_mine_headphoto:

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
			intent.putExtra("outputX", 150);
			intent.putExtra("outputY", 150);

			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			intent.putExtra("noFaceDetection", true);

			// ��ŵ�λ��
			intent.putExtra("output", Uri.fromFile(saveUserHeadFile));

			startActivityForResult(intent, 888);

			/*
			 * Intent intent = new Intent("com.android.camera.action.CROP");
			 * intent.setDataAndType(Uri.parse("file://" + "/" +
			 * Environment.getExternalStorageDirectory().getPath() + "/" +
			 * "headphoto3.jpg"), "image/*"); //
			 * cropΪtrue�������ڿ�����intent��������ʾ��view���Լ��� intent.putExtra("crop",
			 * "true");
			 * 
			 * // aspectX aspectY �ǿ�ߵı��� intent.putExtra("aspectX", 1);
			 * intent.putExtra("aspectY", 1);
			 * 
			 * // outputX,outputY �Ǽ���ͼƬ�Ŀ�� intent.putExtra("outputX", 300);
			 * intent.putExtra("outputY", 300);
			 *//**
			 * �˷������ص�ͼƬֻ����СͼƬ��sumsang����Ϊ�߿�160px��ͼƬ��
			 * �ʽ�ͼƬ������Uri�У�����ʱ��Uriת��ΪBitmap���˷������ɽ��miuiϵͳ����return data������
			 */
			/*
			 * //intent.putExtra("return-data", true);
			 * 
			 * //uritempFileΪUri�������ʵ����uritempFile ;
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
			LogI("ͷ���ϴ��ѣ�����������������" + requestCode + "1");
			userHeadUpLoading();

		}

	}

	// ѡ��ͷ�������

	// �ϴ�������

	// �����û���Ϣ
	private void userHeadUpLoading() {

		// toastInfoShort("ͷ��ѡ�����");

		final ProgressDialog progressDialog = act.progressDialogShows(true,
				null, null);
		// �ϴ�ͼƬ��Bmob������

		// �����ļ�����
		bmobFile = new BmobFile(saveUserHeadFile);

		// �ϴ���һ�ļ�

		// bmobFile.uploadblock(new UploadFileListener() {
		//
		// @Override
		// public void done(BmobException ex) {
		//
		// if (ex == null) {
		// //bmobFile.getFileUrl()--���ص��ϴ��ļ���������ַ
		// toastInfoShort("�ϴ��ļ��ɹ�:" + bmobFile.getFileUrl());
		// }else {
		//
		// toastInfoShort("�ϴ��ļ�ʧ��:" + ex.getLocalizedMessage());
		// }
		//
		// }
		//
		// @Override
		// public void onProgress(Integer value) {
		// super.onProgress(value);
		//
		// //�����ϴ��İٷֱ�
		// }
		// });

		final User user = new User();
		/* User user = MyApplication.userInfo; */
		user.setHeadpicture(new BmobFile(saveUserHeadFile));

		user.getHeadpicture().upload(new UploadFileListener() {

			@Override
			public void done(BmobException ex) {

				// �ϴ��ɹ�
				if (ex == null) {

					progressDialog.dismiss();
					Toast.makeText(act, "ͷ���ϴ��ļ��ɹ�", 0).show();
					// ����Bmob�û���Ϣ��

					/***
					 * ���±��ObjectId
					 * 
					 * ���½���Ļص�����
					 * 
					 */
					if (MyApplication.userInfo == null) {
						LogI("ObjectIdΪ:"
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
										// Toast.makeText(act, "�����û��б�ɹ�",
										// 0).show();
										LogI("�����û��б�ɹ�");
										// �����û�ͷ���UI

										Bitmap bitmap = BitmapFactory
												.decodeFile(bmobFile
														.getLocalFile()
														.getAbsolutePath());

										mMineHeadPhoto.setImageBitmap(bitmap);

									} else {

										Toast.makeText(act, "�����û��б�ʧ��", 0)
												.show();
										LogI("�����û���ʧ�ܵ�ԭ��" + ex);
									}

								}
							});

				} else {

					progressDialog.dismiss();
					Toast.makeText(act, "ͷ���ϴ�ʧ��" + ex, 0).show();
					Log.i("myTag", ex + "!!!!!!!!!!!");
				}

			}
		});

	}
}
