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

	private ImageView mHomeBack, mHomeSearch;// �ؼ�����
	private XListView mHomeFragmentXListView;
	private EditText mHomeEditText;
	private View v;
	
	private List<ActionInfo> actionInfos;//����Դ
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
		 * ��ʼ������Դ��ListView�������
		 * 
		 */
		
		actionInfos = new ArrayList<ActionInfo>();
		
		
		
		actionInfos = (List<ActionInfo>) MyApplication.getDatas(true, "ActionInfos");
		
		if (actionInfos == null) {
			
			actionInfos = new ArrayList<ActionInfo>();
		}
	
        for (ActionInfo actionInfo : actionInfos) {
			
//			LogI("��ȫ���õ������ݿ�����Ϣ"+actionInfo.toString());
		}
		
		
		 adapter = new HomeFragmentListViewAdapter(act,actionInfos);
		
		mHomeFragmentXListView.setAdapter(adapter);
		
		//��ѯ���ݿ��е�ActionInfo�е�������Ϣ
				FindActionInfoUtils.findAllActionInfos(1, null, 0, 0, new FindAllActionInfosListener() {
					
					@Override
					public void getActionInfo(List<ActionInfo> info,BmobException ex) {
						
						if (ex == null) {
							
							//Log.i("myTag", "��ѯ�ɹ�!!!"+info.toString());
							
							//�Ѵӷ�������ȡ�����ݱ��浽ȫ��Map��
							MyApplication.setDatas("ActionInfos", info);
							
							//����Adapter��ʵ��ˢ��
							adapter.updateDatas(info);
							
						}else {
							
							LogI("�ӷ�������ȡ����ʧ�ܵ�ԭ��"+ex.getErrorCode()+","+ex.getLocalizedMessage());
							
						}
						
					}
				});
		
		/**
		 * 
		 * ��ȡȫ�ֵ����ݣ������Ϊ��,��Ҫȥnew ����������ˢ�²������Խ���������
		 */

		
		
		
		
		
		
		
	
		
	}

	
	@Override
	public void initViewOpras() {
		
		//��������ˢ�ºͼ��صļ����¼�
		
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
						
						//�ӷ����� �л�ȡ���ݣ������б�
						adapter.updateDatas(info);
                        
						mHomeFragmentXListView.stopRefresh();
						
					}
				});
				
				
				
			}
			
			@Override
			public void onLoadMore() {
				
				//����n���ӷ������л�ȡ������
				mHomeFragmentXListView.stopLoadMore();
			}
		});
		
		
		
		
	}

	/***
	 * 
	 * ��Fragment�в���ȥ��OnClick����ȥʵ�ּ���
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
//			// �˳���ϵͳ
//			/**
//			 * 
//			 * getActivity��onAttach()�������ܹ��õ������Ļ���
//			 * 
//			 * Fragment�൱��Activity��һ���ؼ�
//			 */
//			AlertDialog.Builder builder = new Builder(getActivity());
//
//			builder.setCancelable(false);
//			builder.setTitle("��ʾ");
//			builder.setMessage("����������������");
//			builder.setNegativeButton("ȡ��", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//
//					// mHomeBack.setImageAlpha(1);
//				}
//			});
//
//			builder.setPositiveButton("ȷ��", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					/**
//					 * ����Ϊ0����ʾ�����˳�������Ϊ1����ʾ�������˳�
//					 */
//
//					System.exit(0);
//
//				}
//			});
//
//			// ���þ���Ի����λ�ú�͸����
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
			
			//������
//		  case R.id.fragment_home_searchinput:
//			
//			break;
//			
//			//������ť
//          case R.id.fragment_home_search:
//			
//			break;
			
	
		

	}
}
