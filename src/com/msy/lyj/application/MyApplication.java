package com.msy.lyj.application;

import java.util.HashMap;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.msy.lyj.bean.User;

import android.app.Application;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

/**
 * 
 * 全局的数据缓存
 * 
 * @author 马圣毅
 *
 *         全局的初始化操作
 */
public class MyApplication extends Application {

	// 缓存全局的数据，做初始化的操作
	public static User userInfo;

	private static Map<String, Object> datas = new HashMap<String, Object>();

	private LocationClient mLocationClient = null;// 地图定位     
	
	private static double Longitude;//经度
	private static double Latitude;//纬度
	
	private static String MyAddress;
	
	public static double getLongitude(){
		double longitude = Longitude;
		return longitude;
	}
	public static double getLatitude(){
		double latitude = Latitude;
		return latitude;
	}
	
	public static String getAddress() {
		String str = MyAddress;
		return str;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();

		// 第一：默认初始化
		Bmob.initialize(this, "b882a89164d8f2f43995d8506016ea25");

		// 此短信SDK可单独使用，调用如下方法完成应用的初始化：
		BmobSMS.initialize(this, "b882a89164d8f2f43995d8506016ea25");

		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		

		
	
		
		// 第一步，初始化LocationClient类
		mLocationClient = new LocationClient(getApplicationContext()); //
		// 声明LocationClient类
		// 第二步:注册. 实现BDLocationListener接口
		mLocationClient.registerLocationListener(new BDLocationListener() {


			@Override
			public void onReceiveLocation(BDLocation location) {
				String address = location.getAddrStr();
				Longitude = location.getLongitude();
				Latitude = location.getLatitude();
				MyAddress = address;
//				 Log.i("myTag", "address:" + address);
//				 System.out.println("address=="+address);

			}
		});
		// 配置定位的参数
		initLocation();
		// 第三步:开启定位服务
		mLocationClient.start();
		
		
		
	}

	/**
	 * 全局存储数据
	 * 
	 * 
	 * @param key
	 *            数据的key值
	 * @param value
	 * @return
	 */

	public static Object setDatas(String key, Object value) {

		return datas.put(key, value);
	}

	/**
	 * 
	 * 如果不加boolean ,添加新数据，会覆盖原来的数据
	 * 
	 * 
	 * @param isDelete
	 *            如果删除，返回原来的数据
	 * @param key
	 * @return
	 */
	public static Object getDatas(boolean isDelete, String key) {

		if (isDelete) {

			return datas.remove(key);
		}

		return datas.get(key);

	}
	
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
}
