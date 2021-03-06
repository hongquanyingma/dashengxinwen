package com.msy.lyj.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;


/***
 * 
 * Viewpager的适配器
 * 
 * ViewPager+Fragment数据源混合操作
 *
 */
public class MyPagerFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	
	
	public MyPagerFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
