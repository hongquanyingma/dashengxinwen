package com.msy.lyj.activity;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.msy.lyj.R;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;
import com.msy.lyj.bean.User;
import com.msy.lyj.utils.FindActionInfoUtils;
import com.msy.lyj.utils.FindActionInfoUtils.FindAllActionInfosListener;

public class LoginActivity extends BaseActivity implements BaseInterface {

	private EditText mEtusername, mEtpassword;
	private TextView mTvSignup;
	private String mUsername;
	private String mPassword;
	
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_login);
		initViews();
		initDatas();
		initViewOpras();
	}

	@Override
	public void initViews() {

		mEtusername = (EditText) findById(R.id.login_et_username);
		mEtpassword = (EditText) findById(R.id.login_et_password);
		mTvSignup = (TextView) findById(R.id.act_login_signup);
   
	}

	@Override
	public void initDatas() {
		
		/**
		 * 
		 * �����¼ҳ�棬�Զ�ֱ�Ӽ��أ������sharedPerferences�İ�xml���У�
		 * û�д����ݣ���ֱ����һ������¼ʱ��ֱ�Ӵ洢
		 * 
		 */
		sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
		
		if (sharedPreferences != null) {
			
			String user = sharedPreferences.getString("username", "");
			String pwd  = sharedPreferences.getString("password", "");
			
			mEtusername.setText(user);
			mEtpassword.setText(pwd);
			
			
		}
		
		
		

	}

	@Override
	public void initViewOpras() {
		
		

	}

	public void actRegisterClick(View v) {

		mTvSignup.setTextColor(Color.GRAY);
		startActivity(new Intent(act, RegisterActivity.class));
	}

	// ��¼����
	public void onLoginClick(View v) {

		if (mEtusername.length() < 1 || mEtpassword.length() < 1) {
			toastInfoShort("�˻��������벻��Ϊ��");
			return;
		}

		// ��ȡBmob�������е�����
		getBmobDatas();

	}

	private void getBmobDatas() {
		
		final ProgressDialog dialog = act.progressDialogShows(true, "", "");
		
		BmobQuery<User> query = new BmobQuery<User>();
		// ��ѯplayerName�С���Ŀ��������
		// query.addWhereEqualTo("Username", "��ʥ");
		// query.addWhereEqualTo("Password", pwd);
		// ����50�����ݣ����������������䣬Ĭ�Ϸ���10������
		// query.setLimit(50);
		// ִ�в�ѯ����
		query.findObjects(new FindListener<User>() {

			

			@Override
			public void done(List<User> users, BmobException e) {

				int j = 5;

				
				String username = getTvContent(mEtusername);
				String pwd = getTvContent(mEtpassword);
				
				

				if (e == null) {
					// toastInfoShort("��ѯ�ɹ�����"+users.size()+"�����ݡ�");
					for (int i = 0; i < users.size(); i++) {

						mUsername = users.get(i).getUsername();
						mPassword = users.get(i).getPassword();

						if (mUsername.equals(username) && mPassword.equals(pwd)) {
							
							
							Intent intent = new Intent(act, HomeActivity.class);
							
							MyApplication.userInfo = users.get(i);
							
//							LogI("��½ʱ�򱣴浽ȫ�ֵ���Ϣ"+users.get(i));
							
							//���û��ֶ���������ݵ�MyApplication�ľ�̬Bean���С�
							if (!username.equals("")&&!pwd.equals("")) {
								/*User user2 = new User();
								user2.setUsername(username);
								user2.setPassword(pwd);*/
								
								//�����ݱ��浽Application��̬��
								
								
								//���յ�������û����������ݴ洢��SharedPerferences��
							 
							 Editor editor = sharedPreferences.edit();
							 
							 editor.putString("username", username);
							 
							 editor.putString("password", pwd);
							 
							 //������Ҫ�ύ
							 editor.commit();
								
								
								
							}
							
							dialog.dismiss();
							finish();
							startActivity(intent);
							return;
						} else {
							j = 6;
						}

					}

					if (j == 6) {
						toastInfoShort("�û��������������");
					}

					// if (mUsername.equals("")&&mPassword.equals("")) {
					// return;
					// }

				} else {
					Log.i("bmob",
							"ʧ�ܣ�" + e.getMessage() + "," + e.getErrorCode());
				}

			}
		});
	}

	/***
	 * 
	 * ���Բ������Ѷ�����ȫ�ֵľ�̬�����е������ó�����
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		mTvSignup.setTextColor(Color.WHITE);
		User user = MyApplication.userInfo;

		if (user != null) {
			mEtusername.setText(user.getUsername());
			mEtpassword.setText(user.getPassword());
		}

	}
}
