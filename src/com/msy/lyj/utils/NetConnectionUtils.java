package com.msy.lyj.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/***
 * �ж��Ƿ������Ĺ�����
 * @author Administrator  
 * 
 * ��Ҫ���������ģ�������getSystemService
 *
 */
public class NetConnectionUtils {

	public static boolean NetIsConnection(Context context){
		
		
		//Connection ����������
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//������Ϣ����
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		//Ϊnullʱ������û����������Ϊnullʱ��Ҳ����û����
		if (networkInfo == null) {
			
			return false;
		}
		
		return networkInfo.isConnected();
		
		
		
	}
}
