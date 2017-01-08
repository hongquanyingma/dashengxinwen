package com.msy.lyj.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.msy.lyj.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class HomeItemPicGridView extends BaseAdapter  {
	
	
	private Context context;
	
	private List<BmobFile> bmobFiles;
	

	public HomeItemPicGridView(Context context, List<BmobFile> bmobFiles) {
		super();
		Log.i("myTag", "tiaoguo0");
		this.context = context;
		this.bmobFiles = bmobFiles;
	}

	@Override
	public int getCount() {
		return bmobFiles.size();
	}

	@Override
	public Object getItem(int position) {
		return bmobFiles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.act_home_list_item_pic_gridview, null);
			
		}
//		Log.i("myTag", "tiaoguo1");//
		final ImageView imageView = (ImageView) v.findViewById(R.id.act_home_fragment_item_picture_gridview_iv);
		
//		Log.i("myTag", "tiaoguo2");//
		
		
		/**
		 * 
		 * 
		 * 三级缓存    ：File判断
		 */
		
		//http://bmob-cdn-7513.b0.upaiyun.com/2016/11/16/f5e51bbbf31c492caf6554ea4da923d3.jpg
		String sanjihuancunFile = bmobFiles.get(position).getFileUrl().substring(
				bmobFiles.get(position).getFileUrl().length()-4-32, 
				bmobFiles.get(position).getFileUrl().length()-4);
		final File headPicFile = new File("sdcard/pic"+sanjihuancunFile+".jpg");
		
		if (headPicFile.exists()) {
			
			 imageView.setImageBitmap(BitmapFactory.decodeFile(headPicFile.getAbsolutePath()));
			
		}else {
			
			
			bmobFiles.get(position).download(new DownloadFileListener() {
				
				private Bitmap bitmap;

				@Override
				public void onProgress(Integer arg0, long arg1) {
					
				}
				
				@Override
				public void done(String path, BmobException ex) {
					
					Log.i("myTAG", "存储文件的地址------"+path);
					
					if (ex == null){
						 
						Bitmap bitmap = BitmapFactory.decodeFile(path);
					    imageView.setImageBitmap(bitmap);
					    try {
							bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(headPicFile));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} 
					}
					
				}
			});
			
		}
		
		
		
		
		
		return v;
	}

}
