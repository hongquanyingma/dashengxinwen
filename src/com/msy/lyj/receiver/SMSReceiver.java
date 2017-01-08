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
 * ע��㲥������
 * 
 * ���ն��ŵĹ㲥
 * @author Administrator
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		//ͨ����ͼ��ȡbundle����
		Bundle bundle = intent.getExtras();
		
		//ͨ��bundle�����Լ�ֵ�Ե���ʽ��ȡֵ
		Object[] objects = (Object[]) bundle.get("pdus");
		
		for (int i = 0; i < objects.length; i++) {
			
			//�Ѷ�������ת��Ϊ�ֽ����ݣ�����SmsMessageͨ��createFromPdu()��������ȡ������Ϣ
			byte[] bytes = (byte[]) objects[i];
			SmsMessage smsMessage = SmsMessage.createFromPdu(bytes);
			
			//ͨ��������Ϣ���������
			String data = smsMessage.getMessageBody();
			
			//ͨ������ַ�����ȡ6λ���ֵ���֤��
			String Code = data.substring(6, 12);
			
			Log.i("myTag", "��λ��֤����Ϣ�ǣ�"+Code);
			
			getCodeSetCode(Code);
		}

		
	}
	
	/**
	 * ����һ���ӿڣ���ӹ۲��߶���
	 * @author Administrator
	 *
	 */

	public interface IsetCodeClickListener{
		public void setCode(String Code);
	}
	
	//ȫ��ȥ��������ӿڶ���
	
	static IsetCodeClickListener listener;
	
	
	//ע���Ϊ�۲���
	public static void setIsetCodeClickListener(IsetCodeClickListener listener){
		
		SMSReceiver.listener = listener;
	}
    
	/***
	 * �жϹ۲��߶����Ƿ������Ѿ�ע����ţ�û����ע��
	 * @param Code
	 */
	
	public void getCodeSetCode(String Code){
		
		//�ж�������Ź۲����Ƿ��Ѿ�ע�ᣬ���ע�ᣬ�����ö���
		if (listener != null) {
			listener.setCode(Code);
		}
	}
}
