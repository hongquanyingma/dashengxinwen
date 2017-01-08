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
 * ��ӭҳ��Activity
 * 
 * ��ӭ����
 * 
 * �ж��Ƿ�����������ʵ��������Լ����ǣ�
 * 
 * �������ݣ���Ϊ��Ҫ��½�����������ȡ���ݣ����Բ���ȡ���ڻ�ȡ���ݵĲ���
 * 
 * 
 * @author Administrator
 *
 */
public class WelcomeActivity extends BaseActivity implements BaseInterface {

	private ImageView mIvWelcome;
	private AlphaAnimation mAlphaAnimation;//��ȡ���䶯������
	private boolean mNetConnectionflag = false;//Ĭ��û������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//��������û�б���
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
		
		//��ȡ��������
		mAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(act, R.anim.alpha_wel);
	}

	@Override
	public void initViewOpras() {
		//���ö����ļ�������
		
		mAlphaAnimation.setAnimationListener(new AnimationListener() {
			
			
			/**
			 * ��������ʱ�ص��ķ���
			 */
			@Override
			public void onAnimationStart(Animation arg0) {
				
				//��ȡ���ݻ����жϵ�ǰ������״̬������ʹ�ù����ࣩ
				
				mNetConnectionflag = NetConnectionUtils.NetIsConnection(act);
				
				
			}
			
			/**
			 * �����ظ�ʱ�ص��ķ���
			 */
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			/***
			 * ��������ʱ�ص��ķ��� 
			 */
			@Override
			public void onAnimationEnd(Animation arg0) {
				if (mNetConnectionflag) {//�����磬��ת��¼ҳ��
					
					Intent intent = new Intent(act,LoginActivity.class);
					startActivity(intent);
					act.finish();
					
				}else {//û�����磬��Ҫȥ��������
					
					
					makeDialogBuilder();
					
				}
				
			}

		});
		
		//��������
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
		
		toastInfoShort("���Ѿ�������û������Ķ���Ԫ״̬");
		AlertDialog.Builder builder = new Builder(act);
		
		
		builder.setCancelable(false);
		builder.setTitle("��ܰ��ʾ");
		builder.setMessage("�������Ӳ����ã�����������");
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				/**
				 * ����Ϊ0����ʾ�����˳�������Ϊ1����ʾ�������˳�
				 */
				System.exit(0);
			}
		});
		
		builder.setPositiveButton("����", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		
		//���þ���Ի����λ�ú�͸����
		AlertDialog alertDialog = builder.create();

	   Window window = alertDialog.getWindow();
	   window.setGravity(Gravity.CENTER);
	   WindowManager.LayoutParams wp = window.getAttributes();
	   wp.alpha = 0.6f;
	   window.setAttributes(wp);
	   
	   alertDialog.show();
		
		
	}

}
