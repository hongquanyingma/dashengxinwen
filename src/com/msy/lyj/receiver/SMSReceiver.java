package com.msy.lyj.receiver;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;


/**
 * 注册广播接受者
 * 
 * 接收短信的广播
 * @author Administrator
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		//通过意图获取bundle对象
		Bundle bundle = intent.getExtras();
		
		//通过bundle对象以键值对的形式获取值
		Object[] objects = (Object[]) bundle.get("pdus");
		
		for (int i = 0; i < objects.length; i++) {
			
			//把对象数组转化为字节数据，方便SmsMessage通过createFromPdu()方法来获取短信信息
			byte[] bytes = (byte[]) objects[i];
			SmsMessage smsMessage = SmsMessage.createFromPdu(bytes);
			
			//通过短信信息获短信内容
			String data = smsMessage.getMessageBody();
			
			//通过拆分字符串获取6位数字的验证码
			String Code = data.substring(6, 12);
			
			Log.i("myTag", "六位验证码信息是："+Code);
			
			getCodeSetCode(Code);
		}

		
	}
	
	/**
	 * 声明一个接口，添加观察者对象
	 * @author Administrator
	 *
	 */

	public interface IsetCodeClickListener{
		public void setCode(String Code);
	}
	
	//全局去定义这个接口对象
	
	static IsetCodeClickListener listener;
	
	
	//注册成为观察者
	public static void setIsetCodeClickListener(IsetCodeClickListener listener){
		
		SMSReceiver.listener = listener;
	}
    
	/***
	 * 判断观察者对象，是否有人已经注册短信，没有则注册
	 * @param Code
	 */
	
	public void getCodeSetCode(String Code){
		
		//判断这个短信观察者是否已经注册，如果注册，就设置短信
		if (listener != null) {
			listener.setCode(Code);
		}
	}
}
