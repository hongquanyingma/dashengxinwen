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
 * ȫ�ֵ����ݻ���
 * 
 * @author ��ʥ��
 *
 *         ȫ�ֵĳ�ʼ������
 */
public class MyApplication extends Application {

	// ����ȫ�ֵ����ݣ�����ʼ���Ĳ���
	public static User userInfo;

	private static Map<String, Object> datas = new HashMap<String, Object>();

	private LocationClient mLocationClient = null;// ��ͼ��λ     
	
	private static double Longitude;//����
	private static double Latitude;//γ��
	
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

		// ��һ��Ĭ�ϳ�ʼ��
		Bmob.initialize(this, "b882a89164d8f2f43995d8506016ea25");

		// �˶���SDK�ɵ���ʹ�ã��������·������Ӧ�õĳ�ʼ����
		BmobSMS.initialize(this, "b882a89164d8f2f43995d8506016ea25");

		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		

		
	
		
		// ��һ������ʼ��LocationClient��
		mLocationClient = new LocationClient(getApplicationContext()); //
		// ����LocationClient��
		// �ڶ���:ע��. ʵ��BDLocationListener�ӿ�
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
		// ���ö�λ�Ĳ���
		initLocation();
		// ������:������λ����
		mLocationClient.start();
		
		
		
	}

	/**
	 * ȫ�ִ洢����
	 * 
	 * 
	 * @param key
	 *            ���ݵ�keyֵ
	 * @param value
	 * @return
	 */

	public static Object setDatas(String key, Object value) {

		return datas.put(key, value);
	}

	/**
	 * 
	 * �������boolean ,��������ݣ��Ḳ��ԭ��������
	 * 
	 * 
	 * @param isDelete
	 *            ���ɾ��������ԭ��������
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
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 1000;
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}
}
