package com.msy.lyj.utils;

import android.util.Log;

import com.msy.lyj.bean.User;

import cn.bmob.sms.exception.BmobException;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.QueryListener;

/**
 * 
 * 去服务器查询用户数据,使用了观察者设计模式
 * @author Administrator
 *
 */
public class FindBmobUserInfoUtils {
	
	
	public static void findUserInfos(String userObjectId, final FindUserInfoListener listener){
		BmobQuery<User> query = new BmobQuery<User>();
		
		query.getObject(userObjectId, new QueryListener<User>() {
			
			@Override
			public void done(User userInfo, cn.bmob.v3.exception.BmobException ex) {
				
				if (ex == null) {
					Log.i("myTag", "用户信息获取成功");
					
					listener.getUserInfo(userInfo);
				}
				
			}
		});
	}
		
	public interface FindUserInfoListener{
		
		void getUserInfo(User userInfo);
	}
	
		   /* @Override
		    public void done(User userInfo, BmobException e) {
		        if(e==null){
		            //获得playerName的信息
		        	userInfo.getPlayerName();
		            //获得数据的objectId信息
		        	userInfo.getObjectId();
		            //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
		        	userInfo.getCreatedAt();
		        }else{
		            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
		        }
		    }*/
		
		

}
