package com.msy.lyj.utils;

import java.util.List;

import com.msy.lyj.bean.Messages;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class FindMessageInfoUtils {
	
	
	public static void findAllMessage(final FindAllMessagesListener listener){
		
		
		BmobQuery<Messages> bmobQuery = new BmobQuery<Messages>();
		
		
		bmobQuery.findObjects(new FindListener<Messages>() {
			
			@Override
			public void done(List<Messages> msg, BmobException ex) {
				
				listener.getMessagesInfo(msg, ex);
				
			}
		});
		
		
		
	}
	
	
	public interface FindAllMessagesListener {

		void getMessagesInfo(List<Messages> info,BmobException ex);

	}

}
