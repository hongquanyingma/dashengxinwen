package com.msy.lyj.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.msy.lyj.R;
import com.msy.lyj.activity.DetailActivity;
import com.msy.lyj.activity.PingLun;
import com.msy.lyj.activity.RegisterActivity;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.bean.ActionInfo;
import com.msy.lyj.bean.User;
import com.msy.lyj.utils.Distance;
import com.msy.lyj.utils.FindBmobUserInfoUtils;
import com.msy.lyj.utils.FindBmobUserInfoUtils.FindUserInfoListener;
import com.msy.lyj.view.utils.MyImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import c.which;
import cn.bmob.push.a.a;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class HomeFragmentListViewAdapter extends BaseAdapter {

	private Context context;

	private List<ActionInfo> actionInfos;

	public HomeFragmentListViewAdapter(BaseActivity act,
			List<ActionInfo> actionInfos) {

		this.context = act;

		this.actionInfos = actionInfos;
	}

	@Override
	public int getCount() {
		return actionInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return actionInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View c, ViewGroup arg2) {

		final MyHolder myHolder;
		// ����View�߼���
		// Log.i("myTag", "***********");
		// �����Ż�
		if (c == null) {

			myHolder = new MyHolder();

			c = LayoutInflater.from(context).inflate(
					R.layout.homefragment_listview_item, null);

			myHolder.mHead = (MyImageView) c
					.findViewById(R.id.homefragment_listview_item_head_picture);
			myHolder.mName = (TextView) c
					.findViewById(R.id.homefragment_listview_item_name);
			myHolder.mTime = (TextView) c
					.findViewById(R.id.homefragment_listview_item_time);
			// myHolder.mRMB = (TextView)
			// c.findViewById(R.id.homefragment_listview_item_rmb);
			myHolder.mAddress = (TextView) c
					.findViewById(R.id.homefragment_listview_item_address);
			myHolder.mDesc = (TextView) c
					.findViewById(R.id.homefragment_listview_item_desc);
			myHolder.mGirdView = (GridView) c
					.findViewById(R.id.homefragment_listview_item_picture_gridview);
			myHolder.mLove = (ImageView) c
					.findViewById(R.id.homefragment_listview_item_love);

			myHolder.mPinglunTv = (EditText) c
					.findViewById(R.id.homefragment_listview_pinglun);
			myHolder.mPinglunBtn = (ImageView) c
					.findViewById(R.id.homefragment_listview_pinglunButton);

			c.setTag(myHolder);

		} else {

			myHolder = (MyHolder) c.getTag();

		}

		// System.currentTimeMillis();

		// �ı��ؼ�

		myHolder.mTime
				.setText(actionInfos.get(position).getActionCurrentTime());// ϵͳ��ǰʱ��
		// myHolder.mRMB.setText(actionInfos.get(position).getActionRMB());

		// ��ǰ���λ��
		myHolder.mAddress.setText(actionInfos.get(position).getActionSite());
		myHolder.mDesc.setText("  "+actionInfos.get(position).getActionDesc());

		// ��ǰλ�õľ���γ��
		// double locationLong = MyApplication.getLongitude();// ��ǰλ�õľ���
		// double locationLa = MyApplication.getLatitude();// ��ǰλ�õ�γ��

		// ��ľ��Ⱥ�γ��

		// double actionLong =
		// actionInfos.get(position).getActionPoint().getLongitude();// ��ľ���
		// double actionLa =
		// actionInfos.get(position).getActionPoint().getLatitude();// ���γ��

		// double distance = Distance.GetDistance(locationLong, locationLa,
		// actionLong, actionLa);

		// ��Double���ͱ�����λ��
		// double dou = distance;
		// double distancekm = (double)Math.round(dou*100)/100;
		// ������λС��
		// DecimalFormat df = new DecimalFormat("#.00");
		// String distancekm = df.format(distance / 1000.0);
		// myHolder.mAddress.setText("������" + distancekm + "km");
		// ���浽ȫ��
		// MyApplication.setDatas("distance", distancekm);

		boolean isPraiseflag = true;// �ж��ղ�,Ϊ��ʱ��ʾδ�ղ�

		// ��ʾ����ղ�״̬
		List<String> praiseActions = MyApplication.userInfo.getPraiseAction();
		if (praiseActions.contains(actionInfos.get(position).getObjectId())) {

			myHolder.mLove.setImageResource(R.drawable.loveon);
			isPraiseflag = false;

		} else {
			myHolder.mLove.setImageResource(R.drawable.loveoff);
			isPraiseflag = true;

		}

		final ActionPraiseViewHolder aHolder = new ActionPraiseViewHolder();

		aHolder.infos = actionInfos.get(position);
		aHolder.isPraiseflag = isPraiseflag;

		myHolder.mLove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// �ղز���
				praiseAction(aHolder, myHolder.mLove);

			}

			// �û��ղز���
			private void praiseAction(
					final ActionPraiseViewHolder actionHolder,
					final ImageView mLove) {

				if (actionHolder.isPraiseflag) {// δ�ղ�

					// ���·��������
					ActionInfo actionInfo = new ActionInfo();

					// ����ǰ�û���id���뵱ǰ��ĵ����ղ�������
					actionInfo.add("praiseUsers",
							MyApplication.userInfo.getObjectId());

					// ԭ�Ӽ������������ղ������ı����ŵ��ȶȣ���Bean���������Intager����
					actionInfo.increment("loveCount");

					// ���²���

					actionInfo.update(actionHolder.infos.getObjectId(),
							new UpdateListener() {

								@Override
								public void done(BmobException ex) {

									if (ex == null) {

										// ���±��ص�����
										mLove.setImageResource(R.drawable.loveon);
										actionHolder.infos.getPraiseUsers()
												.add(MyApplication.userInfo
														.getObjectId());

									}

								}
							});

					// ���·������е��û���
					User userInfo = new User();

					userInfo.add("PraiseAction",
							actionHolder.infos.getObjectId());
					userInfo.update(MyApplication.userInfo.getObjectId(),
							new UpdateListener() {

								@Override
								public void done(BmobException ex) {

									if (ex == null) {

										MyApplication.userInfo
												.getPraiseAction().add(
														actionHolder.infos
																.getObjectId());

									}

								}
							});

				} else {// ȡ���ղ�

					final ActionPraiseViewHolder actionHolder1 = aHolder;
					// ���»��
					ActionInfo actionInfo = new ActionInfo();

					ArrayList<String> removeCurrentUserId = new ArrayList<String>();
					removeCurrentUserId.add(MyApplication.userInfo
							.getObjectId());
					actionInfo.removeAll("praiseUsers", removeCurrentUserId);

					// ԭ���Եĸ����ղز���
					actionInfo.increment("loveCount", -1);

					actionInfo.update(actionHolder1.infos.getObjectId(),
							new UpdateListener() {

								@Override
								public void done(BmobException ex) {

									if (ex == null) {
										mLove.setImageResource(R.drawable.loveoff);
										actionHolder1.infos
												.remove(MyApplication.userInfo
														.getObjectId());
									} else {
										Log.i("myTag", "ȡ������ղ�ʧ��" + ex);
									}

								}
							});

					User userInfo = new User();

					ArrayList<String> removeCurrentActionId = new ArrayList<String>();
					removeCurrentActionId.add(actionHolder1.infos.getObjectId());
					userInfo.removeAll("PraiseAction", removeCurrentActionId);
					userInfo.update(MyApplication.userInfo.getObjectId(),
							new UpdateListener() {

								@Override
								public void done(BmobException ex) {

									if (ex == null) {

										MyApplication.userInfo
												.remove(actionHolder1.infos
														.getObjectId());

									} else {
										Log.i("myTag", "ȡ���û����ղ�ʧ��" + ex);
									}

								}
							});

				}

				actionHolder.isPraiseflag = !actionHolder.isPraiseflag;
			}
		});

		// ��ȡ�ͼƬ,ͨ��GridView

		// ��������ÿ���������Ƭ
		// int index = actionInfos.get(0).getActionPicture().size();

		// �û�ͷ��ؼ�
		List<BmobFile> bmobFiles = actionInfos.get(position).getActionPicture();

		FindBmobUserInfoUtils.findUserInfos(actionInfos.get(position)
				.getActionUserId(), new FindUserInfoListener() {

			@Override
			public void getUserInfo(User userInfo) {

				// Log.i("myTag", "User userInfo" + userInfo.toString());
				myHolder.mName.setText(userInfo.getUsername());

				BmobFile bmobFile = userInfo.getHeadpicture();

				// Toast.makeText(context, bmobFile.getFileUrl().length(),
				// 1).show();

				String headPicPath = bmobFile.getUrl().substring(
						bmobFile.getUrl().length() - 4 - 32,
						bmobFile.getUrl().length() - 4);

				// ��������

				File pathfile = new File("sdcard/headpic" + headPicPath
						+ ".jpg");

				if (pathfile.exists()) {
					// Log.i("!!!", "С����������");
					myHolder.mHead.setImageBitmap(BitmapFactory
							.decodeFile(pathfile.getAbsolutePath()));
				} else {

					bmobFile.download(pathfile, new DownloadFileListener() {

						@Override
						public void onProgress(Integer arg0, long arg1) {

						}

						@Override
						public void done(String pathfile, BmobException ex) {
							Log.i("myTAG", "�洢�ļ��ĵ�ַ111------" + pathfile);

							if (ex == null) {

								Bitmap bitmap = BitmapFactory
										.decodeFile(pathfile);

								myHolder.mHead.setImageBitmap(bitmap);
							}

						}
					});

				}

			}
		});

		myHolder.mGirdView.setAdapter(new HomeItemPicGridView(context,
				bmobFiles));
		
		
		//�������۵ļ���ʱ��
		myHolder.mPinglunTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Intent intent = new Intent(context, PingLun.class);
				intent.putExtra("actionObjectId", actionInfos.get(position).getObjectId());
				context.startActivity(intent);
				
			}
		});
		

		

		return c;

	}

	// �ղ�����Ķ�����Ϣ
	class ActionPraiseViewHolder {
		ActionInfo infos;
		boolean isPraiseflag;
	}

	class MyHolder {

		MyImageView mHead;
		TextView mName;
		TextView mTime;
		// TextView mRMB;
		TextView mAddress;

		TextView mDesc;
		GridView mGirdView;
		ImageView mLove;
		EditText mPinglunTv;
		ImageView mPinglunBtn;

	}

	/**
	 * 
	 * �����ݵ��滻ˢ�²�������HomeFragment�еĵ�ǰ�������
	 */
	public void updateDatas(List<ActionInfo> info) {

		this.actionInfos.clear();

		notifyDataSetChanged();

		this.actionInfos = info;

		notifyDataSetChanged();
	}

}
