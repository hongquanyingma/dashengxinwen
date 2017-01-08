package com.msy.lyj.adapter;

import java.util.List;

import com.msy.lyj.R;
import com.msy.lyj.base.BaseActivity;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/***
 * 
 * MineFragment中GirdView的适配操作类
 * 
 * @author Administrator
 * 
 */
public class MineFragmentGridViewAdapter extends BaseAdapter {

	private Context context;
	private List<String> datas;

	private int[] mImgs = { R.drawable.my_youhui, R.drawable.my_shoucang,

	R.drawable.my_send, R.drawable.my_order, R.drawable.my_gather,
			R.drawable.my_shezhi };

	public MineFragmentGridViewAdapter(List<String> datas, BaseActivity act) {

		this.context = act;

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
 
			
			v = LayoutInflater.from(context).inflate(
					R.layout.minefragment_girdview_item, null);
		}

		TextView mGirdViewItemTv = (TextView) v
				.findViewById(R.id.minefragment_girdview_item_tv);

		ImageView mGirdViewItemIv = (ImageView) v
				.findViewById(R.id.minefragment_girdview_item_iv);

		mGirdViewItemTv.setText(datas.get(position));

		mGirdViewItemIv.setImageResource(mImgs[position]);
		return v;
	}

}
