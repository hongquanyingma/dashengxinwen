package com.msy.lyj.utils;

import android.util.Log;

import com.msy.lyj.bean.User;

import cn.bmob.sms.exception.BmobException;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.QueryListener;

/**
 * 
 * ȥ��������ѯ�û�����,ʹ���˹۲������ģʽ
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
					Log.i("myTag", "�û���Ϣ��ȡ�ɹ�");
					
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
		            //���playerName����Ϣ
		        	userInfo.getPlayerName();
		            //������ݵ�objectId��Ϣ
		        	userInfo.getObjectId();
		            //���createdAt���ݴ���ʱ�䣨ע���ǣ�createdAt������createAt��
		        	userInfo.getCreatedAt();
		        }else{
		            Log.i("bmob","ʧ�ܣ�"+e.getMessage()+","+e.getErrorCode());
		        }
		    }*/
		
		

}
