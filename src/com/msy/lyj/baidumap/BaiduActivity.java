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

	private MapView mMapView;// 展示地图数据的View
	private BaiduMap mBaiduMap;// 百度地图的控制器
	private PoiSearch mPoiSearch;// 检索对象
	private EditText mEtInput;//输入要搜索的地点
	private ImageView mIvSearch;//搜索的控件
	
	private TextView mMarketextViewName;//弹出覆盖物点击事件的地点名称
	private TextView mMarketextViewDesc;//弹出覆盖物点击事件的地点详情
	private  Button   mMarkerBtnPosititity;//确认按钮
	private  Button   mMarkerBtnNativitity;//取消按钮
	
	
	
	
	private List<PoiInfo> poiInfos;//所有检索到的
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_baidumap_search);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 获取控件
		mEtInput = (EditText) findViewById(R.id.act_main_et);
		mIvSearch = (ImageView) findViewById(R.id.act_main_iv);
		
		
		// 第一步，创建POI检索实例
		mPoiSearch = PoiSearch.newInstance();
		// 第二步，创建POI检索监听者；当检索到数据时自动回调此方法
		// 第三步，设置POI检索监听者；重写回调方法
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
						// Log.i("myTag", "没有检索到结果");
						// }

						if (result == null) {
							return;
						}

						poiInfos = result.getAllPoi();//获取检索到的所有覆盖物
						
						mBaiduMap.clear();
						// 创建PoiOverlay
						PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
						// 设置overlay可以处理标注点击事件
						mBaiduMap.setOnMarkerClickListener(overlay);
						// 设置PoiOverlay数据
						overlay.setData(result);
						// 添加PoiOverlay到地图中
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
	 * 搜索地点
	 * 
	 * @param v
	 */
	public void onSearchPoiClick(View v) {
		// 第四步，发起检索请求；
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(mEtInput.getText().toString().trim())
				.keyword(mEtInput.getText().toString().trim()).pageNum(0));// 注意:页数

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	// 第一步，构造自定义 PoiOverlay 类；
	private class MyPoiOverlay extends PoiOverlay {
		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			
			final PoiInfo currentpoiInfo = poiInfos.get(index);
			final View view = getLayoutInflater().inflate(R.layout.act_badidu_marker, null);
			//获取点击覆盖物的名称和详细信息
			mMarketextViewName = (TextView) view.findViewById(R.id.act_baidu_marker_tv_name);
			mMarketextViewDesc = (TextView) view.findViewById(R.id.act_baidu_marker_tv_desc);
			
			//获取确认、取消的监控
			mMarkerBtnPosititity= (Button) view.findViewById(R.id.act_baidu_marker_btn1);
			mMarkerBtnNativitity = (Button) view. findViewById(R.id.act_baidu_marker_btn2);
			
			
			if (currentpoiInfo == null) {
				return false;
			}
			
			//设置到控件上
			mMarketextViewName.setText(currentpoiInfo.name);
			mMarketextViewDesc.setText(currentpoiInfo.address);
			
			//确认的点击操作
			mMarkerBtnPosititity.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//把数据存储到全局，回显到发布页面
					MyApplication.setDatas("pointInfoname", currentpoiInfo.name);
					MyApplication.setDatas("pointInfoaddress", currentpoiInfo.address);
					MyApplication.setDatas("pointInfocity", currentpoiInfo.city);
					MyApplication.setDatas("currentpoiInfo", currentpoiInfo);
					//关闭该Activity
					finish();
					
					
					
				}
			});
			
			//取消的点击操作
			mMarkerBtnNativitity.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
			         // v.callOnClick();
					
					//隐藏布局
					//view.setVisibility(View.GONE);
					mBaiduMap.hideInfoWindow();
				}
			});
			
			//创建InfoWindow展示的view  
			/*Button button = new Button(getApplicationContext());  
			button.setBackgroundResource(R.drawable.popup);  */
			//定义用于显示该InfoWindow的坐标点  
			//LatLng pt = new LatLng(39.86923, 116.397428);  
			//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量 
			InfoWindow mInfoWindow = new InfoWindow(view , currentpoiInfo.location, -27);  
			//显示InfoWindow  
			mBaiduMap.showInfoWindow(mInfoWindow);
			
			
			
			return true;
		}
	}
}
