package com.msy.lyj.activity;

import com.msy.lyj.R;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.utils.NetConnectionUtils;

import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification.Action;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.provider.*;
/***
 * 欢迎页面Activity
 * 
 * 欢迎动画
 * 
 * 判断是否联网（根据实际情况，自己考虑）
 * 
 * 加载数据，因为需要登陆后才能让它获取数据，所以不采取现在获取数据的操作
 * 
 * 
 * @author Administrator
 *
 */
public class WelcomeActivity extends BaseActivity implements BaseInterface {

	private ImageView mIvWelcome;
	private AlphaAnimation mAlphaAnimation;//获取渐变动画对象
	private boolean mNetConnectionflag = false;//默认没有网络
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//代码设置没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_welcome);
		
		initViews();
		initDatas();
		initViewOpras();
	}

	@Override
	public void initViews() {
		
		mIvWelcome = (ImageView) findById(R.id.act_welcome_iv);
	}

	@Override
	public void initDatas() {
		
		//获取动画对象
		mAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(act, R.anim.alpha_wel);
	}

	@Override
	public void initViewOpras() {
		//设置动画的监听操作
		
		mAlphaAnimation.setAnimationListener(new AnimationListener() {
			
			
			/**
			 * 动画开启时回调的方法
			 */
			@Override
			public void onAnimationStart(Animation arg0) {
				
				//获取数据或者判断当前的网络状态（建议使用工具类）
				
				mNetConnectionflag = NetConnectionUtils.NetIsConnection(act);
				
				
			}
			
			/**
			 * 动画重复时回调的方法
			 */
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			/***
			 * 动画结束时回调的方法 
			 */
			@Override
			public void onAnimationEnd(Animation arg0) {
				if (mNetConnectionflag) {//有网络，跳转登录页面
					
					Intent intent = new Intent(act,LoginActivity.class);
					startActivity(intent);
					act.finish();
					
				}else {//没有网络，需要去设置网络
					
					
					makeDialogBuilder();
					
				}
				
			}

		});
		
		//开启动画
		mIvWelcome.startAnimation(mAlphaAnimation);
	}

	


	@Override
	protected void onRestart() {
		super.onRestart();
		
		new Thread(){
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
		mNetConnectionflag = NetConnectionUtils.NetIsConnection(act);
		
		if (mNetConnectionflag) {
			Intent intent = new Intent(act,LoginActivity.class);
			startActivity(intent);
			act.finish();
		}else {

			makeDialogBuilder();
		}
	}
	
	protected void makeDialogBuilder() {
		
		toastInfoShort("您已经进入了没有网络的二次元状态");
		AlertDialog.Builder builder = new Builder(act);
		
		
		builder.setCancelable(false);
		builder.setTitle("温馨提示");
		builder.setMessage("网络连接不可用，请重新设置");
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				/**
				 * 参数为0，表示正常退出，参数为1，表示不正常退出
				 */
				System.exit(0);
			}
		});
		
		builder.setPositiveButton("设置", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		
		//设置警告对话框的位置和透明度
		AlertDialog alertDialog = builder.create();

	   Window window = alertDialog.getWindow();
	   window.setGravity(Gravity.CENTER);
	   WindowManager.LayoutParams wp = window.getAttributes();
	   wp.alpha = 0.6f;
	   window.setAttributes(wp);
	   
	   alertDialog.show();
		
		
	}

}
