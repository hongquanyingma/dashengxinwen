package com.msy.lyj.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/***
 * 判断是否有网的工具类
 * @author Administrator  
 * 
 * 需要传递上下文，来调用getSystemService
 *
 */
public class NetConnectionUtils {

	public static boolean NetIsConnection(Context context){
		
		
		//Connection 网络服务对象
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//网络信息对象
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		//为null时，可能没有网。当不为null时，也可能没有网
		if (networkInfo == null) {
			
			return false;
		}
		
		return networkInfo.isConnected();
		
		
		
	}
}
