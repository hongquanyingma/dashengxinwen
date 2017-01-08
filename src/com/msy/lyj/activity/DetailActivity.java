package com.msy.lyj.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import com.msy.lyj.R;
import com.msy.lyj.adapter.DetailsListViewAdapter;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;
import com.msy.lyj.bean.Messages;
import com.msy.lyj.picutils.GalleryAdapter;
import com.msy.lyj.picutils.GalleryView;
import com.msy.lyj.utils.FindMessageInfoUtils;
import com.msy.lyj.utils.FindMessageInfoUtils.FindAllMessagesListener;

public class DetailActivity extends BaseActivity implements BaseInterface {

	private TextView mDetails, mType;
	private TextView mAuthor, mTime, mAddress;
	private ImageView back;
	// private GalleryView mGalleryView;
	private ImageView miv;
	private GalleryView galleryFlow;
	private Bitmap[] ivpictures;
	private ListView mListView;
	private ActionInfo actionInfo;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.act_detail);

		initViews();
		initDatas();
		initViewOpras();

	}

	@Override
	public void initViews() {

		mAuthor = (TextView) findById(R.id.details_listview_item_name);
		mDetails = (TextView) findById(R.id.details_details);
		mTime = (TextView) findById(R.id.details_listview_item_time);
		mAddress = (TextView) findById(R.id.details_listview_item_address);
		back = (ImageView) findById(R.id.act_details_back);
		mType = (TextView) findById(R.id.details_listview_item_type);
		// mGalleryView = (GalleryView) findById(R.id.act_details_gallery_view);
		// miv = (ImageView) findById(R.id.picture);
		mListView = (ListView) findById(R.id.act_details_lv);

		galleryFlow = (GalleryView) findViewById(R.id.act_details_gallery_view);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	}

	@Override
	public void initDatas() {
		
		
		
		Intent intent = getIntent();
		//String objectId = intent.getStringExtra("objectId");
		actionInfo=(ActionInfo) intent.getSerializableExtra("actionitem");
		
		//查找Person表里面id为6b6c11c537的数据
	
		//System.out.println("为空？10"+actionInfo.toString());
		mAuthor.setText(actionInfo.getActionName());
		mDetails.setText("       "+actionInfo.getActionDetails());
		mTime.setText("日期："+actionInfo.getActionCurrentTime());
		mAddress.setText("地址："+actionInfo.getActionSite());
		mType.setText("新闻类别:"+actionInfo.getActionClass());
		List<BmobFile> files = actionInfo.getActionPicture();
		 ivpictures = new Bitmap[files.size()];
		for (int i = 0; i < files.size(); i++) {
			

			String sanjihuancunFile = files.get(i).getFileUrl().substring(
					files.get(i).getFileUrl().length()-4-32, 
					files.get(i).getFileUrl().length()-4);
			String PicFile = new File("sdcard/pic"+sanjihuancunFile+".jpg").getAbsolutePath();
			
			
			Bitmap bitmap = BitmapFactory.decodeFile(PicFile);
			
			
			
			ivpictures[i] = bitmap;
		}

		
		
		
		
		
		
	 FindMessageInfoUtils.findAllMessage(new FindAllMessagesListener() {
		
		@Override
		public void getMessagesInfo(List<Messages> info, BmobException ex) {
			
			
			
       if (ex == null) {
				
    	    
    	        for (int i = 0; i <info.size(); i++) {
//    	        	LogI(info.get(i).getActionObjectId()+"111111111");
//	        		LogI(actionInfo.getObjectId()+"222222222");
    	        	
    	        	if (info.get(i).getActionObjectId().equals(actionInfo.getObjectId())) {
						
    	        		
    	        		
    	        		
    	        		mListView.setAdapter(new DetailsListViewAdapter(act,info));
    	        		
					}
					
				}
    	   
			

				
			}else {
				toastInfoShort("评论获取失败"+ex);
			}
			
			
		}
	});
		
		
		
		
		
				
		
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public void initViewOpras() {

		GalleryAdapter adapter = new GalleryAdapter(DetailActivity.this,
				ivpictures);
		adapter.createReflectedImages();
		galleryFlow.setAdapter(adapter);

	}

}
