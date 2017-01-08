package com.msy.lyj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import cn.bmob.v3.exception.BmobException;

import com.msy.lyj.R;
import com.msy.lyj.R.id;
import com.msy.lyj.activity.DetailActivity;
import com.msy.lyj.adapter.HomeFragmentListViewAdapter;
import com.msy.lyj.adapter.MyPagerFragmentAdapter;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseFragment;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;
import com.msy.lyj.utils.FindActionInfoUtils;
import com.msy.lyj.utils.FindActionInfoUtils.FindAllActionInfosListener;
import com.msy.lyj.view.utils.XListView;
import com.msy.lyj.view.utils.XListView.IXListViewListener;



public class HomeFragment extends BaseFragment implements BaseInterface,
		android.view.View.OnClickListener {

	private ImageView mHomeBack, mHomeSearch;// 控件对象
	private XListView mHomeFragmentXListView;
	private EditText mHomeEditText;
	private View v;
	
	private List<ActionInfo> actionInfos;//数据源
	private HomeFragmentListViewAdapter adapter;
 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_home, null);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews();
		initDatas();
		initViewOpras();
	}

	@Override
	public void initViews() {

		mHomeFragmentXListView = (XListView) findById(R.id.fragment_home_listview);
		
		
		
		
//		mHomeBack.setOnClickListener(this);
//		mHomeEditText.setOnClickListener(this);
//		mHomeSearch.setOnClickListener(this);

		
	}


	@Override
	public void initDatas() {
 
		/***
		 * 
		 * 初始化数据源，ListView适配操作
		 * 
		 */
		
		actionInfos = new ArrayList<ActionInfo>();
		
		
		
		actionInfos = (List<ActionInfo>) MyApplication.getDatas(true, "ActionInfos");
		
		if (actionInfos == null) {
			
			actionInfos = new ArrayList<ActionInfo>();
		}
	
        for (ActionInfo actionInfo : actionInfos) {
			
//			LogI("从全局拿到的数据库活动的信息"+actionInfo.toString());
		}
		
		
		 adapter = new HomeFragmentListViewAdapter(act,actionInfos);
		
		mHomeFragmentXListView.setAdapter(adapter);
		
		//查询数据库中的ActionInfo中的所有信息
				FindActionInfoUtils.findAllActionInfos(1, null, 0, 0, new FindAllActionInfosListener() {
					
					@Override
					public void getActionInfo(List<ActionInfo> info,BmobException ex) {
						
						if (ex == null) {
							
							//Log.i("myTag", "查询成功!!!"+info.toString());
							
							//把从服务器获取的数据保存到全局Map中
							MyApplication.setDatas("ActionInfos", info);
							
							//更新Adapter，实现刷新
							adapter.updateDatas(info);
							
						}else {
							
							LogI("从服务器读取数据失败的原因"+ex.getErrorCode()+","+ex.getLocalizedMessage());
							
						}
						
					}
				});
		
		/**
		 * 
		 * 获取全局的数据，如果它为空,需要去new ，，，，用刷新操作可以解决这个问题
		 */

		
		
		
		
		
		
		
	
		
	}

	
	@Override
	public void initViewOpras() {
		
		//设置下拉刷新和加载的监听事件
		
		mHomeFragmentXListView.setPullLoadEnable(true);
		mHomeFragmentXListView.setPullRefreshEnable(true);
		
		
		
		
		
		
		
		
		mHomeFragmentXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ActionInfo object=(ActionInfo) parent.getItemAtPosition(position);
				if(object==null){
					
					return;
				}
				
				//toastInfoShort(object+"111");
				Intent intent = new Intent(act,DetailActivity.class) ;
				intent.putExtra("actionitem", object);
				startActivity(intent);
			}
		});
		
	    
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		mHomeFragmentXListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				
				
		FindActionInfoUtils.findAllActionInfos(1, null, 0, 0, new FindAllActionInfosListener() {
					
					@Override
	            	public void getActionInfo(List<ActionInfo> info, BmobException ex) {
						
						//从服务器 中获取数据，更新列表
						adapter.updateDatas(info);
                        
						mHomeFragmentXListView.stopRefresh();
						
					}
				});
				
				
				
			}
			
			@Override
			public void onLoadMore() {
				
				//忽略n条从服务器中获取的数据
				mHomeFragmentXListView.stopLoadMore();
			}
		});
		
		
		
		
	}

	/***
	 * 
	 * 在Fragment中不能去用OnClick方法去实现监听
	 * 
	 */
	// public void fragmenthomebackClick(View v) {
	//
	//
	//
	// }

	@Override
	public void onClick(View v) {

//		switch (v.getId()) {
//		case R.id.fragment_home_back:
//
//			// mHomeBack.setImageAlpha(3);
//
//			// 退出本系统
//			/**
//			 * 
//			 * getActivity和onAttach()方法都能够得到上下文环境
//			 * 
//			 * Fragment相当于Activity的一个控件
//			 */
//			AlertDialog.Builder builder = new Builder(getActivity());
//
//			builder.setCancelable(false);
//			builder.setTitle("提示");
//			builder.setMessage("忍心抛弃我吗，主人");
//			builder.setNegativeButton("取消", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//
//					// mHomeBack.setImageAlpha(1);
//				}
//			});
//
//			builder.setPositiveButton("确定", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					/**
//					 * 参数为0，表示正常退出，参数为1，表示不正常退出
//					 */
//
//					System.exit(0);
//
//				}
//			});
//
//			// 设置警告对话框的位置和透明度
//			AlertDialog alertDialog = builder.create();
//
//			Window window = alertDialog.getWindow();
//			window.setGravity(Gravity.CENTER);
//			WindowManager.LayoutParams wp = window.getAttributes();
//			wp.alpha = 0.6f;
//			window.setAttributes(wp);
//			alertDialog.show();
//
//			break;
			
			//搜索框
//		  case R.id.fragment_home_searchinput:
//			
//			break;
//			
//			//搜索按钮
//          case R.id.fragment_home_search:
//			
//			break;
			
	
		

	}
}
