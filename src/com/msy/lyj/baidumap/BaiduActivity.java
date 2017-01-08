package com.msy.lyj.baidumap;

import java.util.List;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.msy.lyj.R;
import com.msy.lyj.application.MyApplication;

/**
 * key: xigSO7pZWA5p84LjOd51FmNK1ZZpVwkH
 * 
 */
public class BaiduActivity extends Activity {

	private MapView mMapView;// չʾ��ͼ���ݵ�View
	private BaiduMap mBaiduMap;// �ٶȵ�ͼ�Ŀ�����
	private PoiSearch mPoiSearch;// ��������
	private EditText mEtInput;//����Ҫ�����ĵص�
	private ImageView mIvSearch;//�����Ŀؼ�
	
	private TextView mMarketextViewName;//�������������¼��ĵص�����
	private TextView mMarketextViewDesc;//�������������¼��ĵص�����
	private  Button   mMarkerBtnPosititity;//ȷ�ϰ�ť
	private  Button   mMarkerBtnNativitity;//ȡ����ť
	
	
	
	
	private List<PoiInfo> poiInfos;//���м�������
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_baidumap_search);
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// ��ȡ�ؼ�
		mEtInput = (EditText) findViewById(R.id.act_main_et);
		mIvSearch = (ImageView) findViewById(R.id.act_main_iv);
		
		
		// ��һ��������POI����ʵ��
		mPoiSearch = PoiSearch.newInstance();
		// �ڶ���������POI���������ߣ�������������ʱ�Զ��ص��˷���
		// ������������POI���������ߣ���д�ص�����
		mPoiSearch
				.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

					@Override
					public void onGetPoiResult(PoiResult result) {
						// if (result != null) {
						// List<PoiInfo> resultPois = result.getAllPoi();
						// PoiInfo poiInfo = resultPois.get(0);
						// StringBuffer sb = new StringBuffer();
						// sb.append("address:" + poiInfo.address).append(
						// ",phone:" + poiInfo.phoneNum);
						// Log.i("myTag", sb.toString());
						// } else {
						// Log.i("myTag", "û�м��������");
						// }

						if (result == null) {
							return;
						}

						poiInfos = result.getAllPoi();//��ȡ�����������и�����
						
						mBaiduMap.clear();
						// ����PoiOverlay
						PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
						// ����overlay���Դ����ע����¼�
						mBaiduMap.setOnMarkerClickListener(overlay);
						// ����PoiOverlay����
						overlay.setData(result);
						// ���PoiOverlay����ͼ��
						overlay.addToMap();
						overlay.zoomToSpan();
						return;

					}

					@Override
					public void onGetPoiIndoorResult(PoiIndoorResult arg0) {

					}

					@Override
					public void onGetPoiDetailResult(PoiDetailResult arg0) {

					}
				});

		
		
		
	}

	/**
	 * �����ص�
	 * 
	 * @param v
	 */
	public void onSearchPoiClick(View v) {
		// ���Ĳ��������������
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(mEtInput.getText().toString().trim())
				.keyword(mEtInput.getText().toString().trim()).pageNum(0));// ע��:ҳ��

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}

	// ��һ���������Զ��� PoiOverlay �ࣻ
	private class MyPoiOverlay extends PoiOverlay {
		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			
			final PoiInfo currentpoiInfo = poiInfos.get(index);
			final View view = getLayoutInflater().inflate(R.layout.act_badidu_marker, null);
			//��ȡ�������������ƺ���ϸ��Ϣ
			mMarketextViewName = (TextView) view.findViewById(R.id.act_baidu_marker_tv_name);
			mMarketextViewDesc = (TextView) view.findViewById(R.id.act_baidu_marker_tv_desc);
			
			//��ȡȷ�ϡ�ȡ���ļ��
			mMarkerBtnPosititity= (Button) view.findViewById(R.id.act_baidu_marker_btn1);
			mMarkerBtnNativitity = (Button) view. findViewById(R.id.act_baidu_marker_btn2);
			
			
			if (currentpoiInfo == null) {
				return false;
			}
			
			//���õ��ؼ���
			mMarketextViewName.setText(currentpoiInfo.name);
			mMarketextViewDesc.setText(currentpoiInfo.address);
			
			//ȷ�ϵĵ������
			mMarkerBtnPosititity.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//�����ݴ洢��ȫ�֣����Ե�����ҳ��
					MyApplication.setDatas("pointInfoname", currentpoiInfo.name);
					MyApplication.setDatas("pointInfoaddress", currentpoiInfo.address);
					MyApplication.setDatas("pointInfocity", currentpoiInfo.city);
					MyApplication.setDatas("currentpoiInfo", currentpoiInfo);
					//�رո�Activity
					finish();
					
					
					
				}
			});
			
			//ȡ���ĵ������
			mMarkerBtnNativitity.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
			         // v.callOnClick();
					
					//���ز���
					//view.setVisibility(View.GONE);
					mBaiduMap.hideInfoWindow();
				}
			});
			
			//����InfoWindowչʾ��view  
			/*Button button = new Button(getApplicationContext());  
			button.setBackgroundResource(R.drawable.popup);  */
			//����������ʾ��InfoWindow�������  
			//LatLng pt = new LatLng(39.86923, 116.397428);  
			//����InfoWindow , ���� view�� �������꣬ y ��ƫ���� 
			InfoWindow mInfoWindow = new InfoWindow(view , currentpoiInfo.location, -27);  
			//��ʾInfoWindow  
			mBaiduMap.showInfoWindow(mInfoWindow);
			
			
			
			return true;
		}
	}
}
