package com.msy.lyj.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.exception.BmobException;

import com.msy.lyj.R;
import com.msy.lyj.adapter.MyPagerFragmentAdapter;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;
import com.msy.lyj.fragment.HomeFragment;
import com.msy.lyj.fragment.MessageFragment;
import com.msy.lyj.fragment.MineFragment;
import com.msy.lyj.fragment.MoreFragment;
import com.msy.lyj.utils.FindActionInfoUtils;
import com.msy.lyj.utils.FindActionInfoUtils.FindAllActionInfosListener;

public class HomeActivity extends BaseActivity implements BaseInterface,
		OnClickListener {

	private ViewPager mHomeViewPager;// ViewPager����
	
	private ImageView mAddImageView;//��ҳ��Add��ť
	
	private List<Fragment> fragments;
	
	
	private DrawerLayout mHomeActDraw;
	
	private LinearLayout mln_center,mln_left;

	// ѡ���Դ

	private LinearLayout[] mLinears = new LinearLayout[4];
	private ImageView[] mIvs = new ImageView[4];
	private TextView[] mTvs = new TextView[4];

	// ѡ���id

	private int[] mLinearIds = { R.id.act_home_shouye_lin,
			R.id.act_home_xinxi_lin, R.id.act_home_wode_lin,
			R.id.act_home_gengduo_lin };

	private int[] mLinearIvIds = { R.id.act_home_shouye_lin_iv,
			R.id.act_home_xinxi_lin_iv, R.id.act_home_wode_lin_iv,
			R.id.act_home_gengduo_lin_iv };

	private int[] mLinearTvIds = { R.id.act_home_shouye_lin_tv,
			R.id.act_home_xinxi_lin_tv, R.id.act_home_wode_lin_tv,
			R.id.act_home_gengduo_lin_tv };

	// ͼƬ�е���Դ

	private int[] drawerOns = { R.drawable.tuijianon, R.drawable.redianon,
			R.drawable.wo, R.drawable.chazhaoon };

	private int[] drawerOffs = { R.drawable.tuijianoff, R.drawable.redianoff,
			R.drawable.wo0, R.drawable.chazhaooff};
	
	

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_homes);
		initViews();
		initDatas();
		initViewOpras();

	}

	@Override
	public void initViews() {

		for (int i = 0; i < 4; i++) {

			mLinears[i] = (LinearLayout) findById(mLinearIds[i]);
			mIvs[i] = (ImageView) findById(mLinearIvIds[i]);
			mTvs[i] = (TextView) findById(mLinearTvIds[i]);
			
			//ViewPager����
			
			mHomeViewPager = (ViewPager) findById(R.id.act_home_viewpager);
			
			//Add��ť
			mAddImageView = (ImageView) findById(R.id.act_home_jiahao);
			
			//��ȡDrawLayout
			mHomeActDraw = (DrawerLayout) findById(R.id.act_home_dl);
			//��ȡDrawLayout��������ͼ�ؼ�
			mln_center = (LinearLayout) this.findViewById(R.id.lin_center);
			mln_left = (LinearLayout) this.findViewById(R.id.lin_left);
			
			
			
			mAddImageView.setOnClickListener(this);
			
			mLinears[i].setOnClickListener(this);
		}
		
		
		
		
	}

	@Override
	public void initDatas() {
		
		
		/***
		 * �O ������¼�
		 */
		mHomeActDraw.setDrawerListener(new DrawerListener() {
			
			
			float fromX = 1.0f;
			float fromY = 1.0f;
			/***
			 * ״̬����ʱ�ص�
			 * 
			 * �ص��У�1���ص�����
			 *  2���ָ����ã�0
			 */
			@Override
			public void onDrawerStateChanged(int arg0) {
				
			}
			
			
			/***
			 * �����İٷֱ�
			 * 
			 */
			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				
				if (arg0 == mln_left) {
					
					//mln_center.setX(200*(arg1*100.0f)/100.0f);
					
					float toX = 1-(arg1*0.1f);
					ScaleAnimation scaleAnimation = new ScaleAnimation(
							fromX, 
							toX,
							fromY,
							toX,
							ScaleAnimation.RELATIVE_TO_SELF,1.0f,
							ScaleAnimation.RELATIVE_TO_SELF,1.0f);
					
					fromX = toX;
					fromY = toX;
					scaleAnimation.setDuration(2);
					scaleAnimation.setFillAfter(true);
					mln_center.startAnimation(scaleAnimation);
				}
				
			}
			
			@Override
			public void onDrawerOpened(View arg0) {
				
			}
			
			@Override
			public void onDrawerClosed(View arg0) {
				
			}
		});
		

		//fragment����Դ�ĳ�ʼ��
		
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new MessageFragment());
		fragments.add(new MineFragment());
		fragments.add(new MoreFragment());
		//Log.i("myTag", fragments.size()+"");
		
		
		mHomeViewPager.setAdapter(new MyPagerFragmentAdapter(getSupportFragmentManager(),fragments));
	}

	@Override
	public void initViewOpras() {
		
		mHomeViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int i) {
				sollckClick(i);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});

	}

	/***
	 * 
	 * ѡ��ĵ���¼�
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.act_home_shouye_lin:

			sollckClick(0);
			mHomeViewPager.setCurrentItem(0);
			break;

		case R.id.act_home_xinxi_lin:

			sollckClick(1);
			mHomeViewPager.setCurrentItem(1);
			break;

		case R.id.act_home_wode_lin:

			sollckClick(2);
			mHomeViewPager.setCurrentItem(2);
			break;

		case R.id.act_home_gengduo_lin:

			sollckClick(3);
			mHomeViewPager.setCurrentItem(3);
			break;
			
		case R.id.act_home_jiahao:
			
			startActivity(new Intent(this,PublishActionActivity.class));
			
			break;
		
		
		}

	}

	/**
	 * ѡ����л�����
	 * @param i �û�����Ĳ���
	 */
	private void sollckClick(int i) {
		for (int j = 0; j < 4; j++) {
			if (i == j) {
				mIvs[i].setImageResource(drawerOns[i]);
			}else {
				mIvs[j].setImageResource(drawerOffs[j]);
			}
		}
	}
	
	/**
	 * 
	 * ˫���˳�
	 */
	private boolean flag = false;
	@Override
	public void onBackPressed() {
		
		if (flag) {
			//�˳�
			
			//�˳��ķ���
			super.onBackPressed();
			
			
		}else {
			
			toastInfoShort("�ٰ�һ�η��ؼ��˳���Ӧ��");
			
			flag = true;
			
		     //�߳�һ��Ҫ����
			
			new Thread(){
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					flag = false;
				};
			}.start();
			
		}
		
	}
}
