package com.msy.lyj.adapter;

import java.util.List;

import com.msy.lyj.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PublishSpinnerAdapter extends BaseAdapter {

	
	Context context;
	List<String> datas;
	
	private int[] initImgs = {R.drawable.more_zhoubian,R.drawable.more_shaoer,R.drawable.more_diy,R.drawable.more_jianshen,R.drawable.more_jishi
	              , R.drawable.more_yanchu,R.drawable.more_zhanlan,R.drawable.more_shalong,R.drawable.more_pincha,R.drawable.more_juhui		
	};
	
   public PublishSpinnerAdapter(Context context, List<String> datas) {
	super();
	this.context = context;
	this.datas = datas;
 }


	@Override
	public int getCount() {
		return datas.size();

	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.act_publish_spinner_item, null);
		}
		
		 TextView  spinnerItemTv = (TextView) v.findViewById(R.id.act_publish_spinner_item_tv);
		 spinnerItemTv.setText(datas.get(position));
		
		
		return v;
	}

}
