package com.msy.lyj.activity;

import java.util.ArrayList;
import java.util.List;

import com.msy.lyj.R;
import com.msy.lyj.adapter.HomeFragmentListViewAdapter;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FindActionInfosActivity extends BaseActivity implements BaseInterface ,OnClickListener{

	private TextView findInfoTv;
	private ListView findInfoLv;
	private ImageView findBack;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_find_action_infos);
		initViews();
		initDatas();
		initViewOpras();
	}
	@Override
	public void initViews() {
		
		
		findInfoTv = (TextView) findViewById(R.id.act_find_infos_tv);
		findInfoLv = (ListView) findViewById(R.id.act_find_infos_lv);
		findBack = (ImageView) findViewById(R.id.act_find_infos_back_btn);
		
		
	}

	@Override
	public void initDatas() {
		
		//从全局获取数据
		
		        
				String actionType =(String) MyApplication.getDatas(true, "actionType");
				findInfoTv.setText(actionType);
				List<ActionInfo> actionInfos = (List<ActionInfo>) MyApplication.getDatas(true, "actionInfos");
				
				if (actionInfos == null) {
					actionInfos = new ArrayList<ActionInfo>();
				}
				
				findInfoLv.setAdapter(new HomeFragmentListViewAdapter(act, actionInfos));
				TextView emptyView = new TextView(this);  
				emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
				emptyView.setText("对不起，您查询的内容不存在");
				emptyView.setVisibility(View.GONE);  
				((ViewGroup)findInfoLv.getParent()).addView(emptyView);  
				findInfoLv.setEmptyView(emptyView);  
				
				
				
				findBack.setOnClickListener(this);
		
	}

	@Override
	public void initViewOpras() {
		
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.act_find_infos_back_btn:
			
			finish();
			
			
			break;

		default:
			break;
		}
		
	}

}
