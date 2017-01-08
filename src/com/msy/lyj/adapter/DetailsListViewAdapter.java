package com.msy.lyj.adapter;

import java.util.List;

import com.msy.lyj.R;
import com.msy.lyj.activity.DetailActivity;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.bean.Messages;
import com.msy.lyj.utils.FindMessageInfoUtils.FindAllMessagesListener;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DetailsListViewAdapter extends BaseAdapter {

	Context context;
	List<Messages> info;
	
	



	public DetailsListViewAdapter(BaseActivity act, List<Messages> info) {
		
		this.context = act;
		this.info  = info;
	}

	@Override
	public int getCount() {
		return info.size();
	}

	@Override
	public Object getItem(int position) {
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		
		
		if (v == null) {
			
			v = LayoutInflater.from(context).inflate(R.layout.act_details_lv_item, null);
		}
		
		
		TextView tvUser = (TextView) v.findViewById(R.id.act_details_item_username_tv);
		TextView tvMsg = (TextView) v.findViewById(R.id.act_details_item_message_tv);
		
		tvUser.setText("评论人：   "+info.get(position).getActionUser());
		tvMsg.setText("评论内容：  "+info.get(position).getActionMessage());
		
		
		
		return v;
	}

	
}
