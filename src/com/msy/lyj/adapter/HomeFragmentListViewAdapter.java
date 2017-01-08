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
		// 测试View走几次
		// Log.i("myTag", "***********");
		// 二级优化
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

		// 文本控件

		myHolder.mTime
				.setText(actionInfos.get(position).getActionCurrentTime());// 系统当前时间
		// myHolder.mRMB.setText(actionInfos.get(position).getActionRMB());

		// 当前活动的位置
		myHolder.mAddress.setText(actionInfos.get(position).getActionSite());
		myHolder.mDesc.setText("  "+actionInfos.get(position).getActionDesc());

		// 当前位置的经度纬度
		// double locationLong = MyApplication.getLongitude();// 当前位置的经度
		// double locationLa = MyApplication.getLatitude();// 当前位置的纬度

		// 活动的经度和纬度

		// double actionLong =
		// actionInfos.get(position).getActionPoint().getLongitude();// 活动的经度
		// double actionLa =
		// actionInfos.get(position).getActionPoint().getLatitude();// 活动的纬度

		// double distance = Distance.GetDistance(locationLong, locationLa,
		// actionLong, actionLa);

		// 把Double类型保留两位数
		// double dou = distance;
		// double distancekm = (double)Math.round(dou*100)/100;
		// 保留两位小数
		// DecimalFormat df = new DecimalFormat("#.00");
		// String distancekm = df.format(distance / 1000.0);
		// myHolder.mAddress.setText("距离我" + distancekm + "km");
		// 保存到全局
		// MyApplication.setDatas("distance", distancekm);

		boolean isPraiseflag = true;// 判断收藏,为真时表示未收藏

		// 显示活动的收藏状态
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

				// 收藏操作
				praiseAction(aHolder, myHolder.mLove);

			}

			// 用户收藏操作
			private void praiseAction(
					final ActionPraiseViewHolder actionHolder,
					final ImageView mLove) {

				if (actionHolder.isPraiseflag) {// 未收藏

					// 更新服务器活动表
					ActionInfo actionInfo = new ActionInfo();

					// 将当前用户的id加入当前活动的点赞收藏属性中
					actionInfo.add("praiseUsers",
							MyApplication.userInfo.getObjectId());

					// 原子计数器，根据收藏数，改变新闻的热度，在Bean类里面添加Intager类型
					actionInfo.increment("loveCount");

					// 更新操作

					actionInfo.update(actionHolder.infos.getObjectId(),
							new UpdateListener() {

								@Override
								public void done(BmobException ex) {

									if (ex == null) {

										// 更新本地的数据
										mLove.setImageResource(R.drawable.loveon);
										actionHolder.infos.getPraiseUsers()
												.add(MyApplication.userInfo
														.getObjectId());

									}

								}
							});

					// 更新服务器中的用户表
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

				} else {// 取消收藏

					final ActionPraiseViewHolder actionHolder1 = aHolder;
					// 更新活动表
					ActionInfo actionInfo = new ActionInfo();

					ArrayList<String> removeCurrentUserId = new ArrayList<String>();
					removeCurrentUserId.add(MyApplication.userInfo
							.getObjectId());
					actionInfo.removeAll("praiseUsers", removeCurrentUserId);

					// 原子性的更改收藏操作
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
										Log.i("myTag", "取消活动表收藏失败" + ex);
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
										Log.i("myTag", "取消用户表收藏失败" + ex);
									}

								}
							});

				}

				actionHolder.isPraiseflag = !actionHolder.isPraiseflag;
			}
		});

		// 获取活动图片,通过GridView

		// 多个活动，，每个活动多张照片
		// int index = actionInfos.get(0).getActionPicture().size();

		// 用户头像控件
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

				// 三级缓存

				File pathfile = new File("sdcard/headpic" + headPicPath
						+ ".jpg");

				if (pathfile.exists()) {
					// Log.i("!!!", "小样，快快出来");
					myHolder.mHead.setImageBitmap(BitmapFactory
							.decodeFile(pathfile.getAbsolutePath()));
				} else {

					bmobFile.download(pathfile, new DownloadFileListener() {

						@Override
						public void onProgress(Integer arg0, long arg1) {

						}

						@Override
						public void done(String pathfile, BmobException ex) {
							Log.i("myTAG", "存储文件的地址111------" + pathfile);

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
		
		
		//设置评论的监听时间
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

	// 收藏所需的对象信息
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
	 * 做数据的替换刷新操作，供HomeFragment中的当前对象调用
	 */
	public void updateDatas(List<ActionInfo> info) {

		this.actionInfos.clear();

		notifyDataSetChanged();

		this.actionInfos = info;

		notifyDataSetChanged();
	}

}
