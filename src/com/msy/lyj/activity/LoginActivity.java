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
		 * 进入登录页面，自动直接加载，如果在sharedPerferences的包xml表中，
		 * 没有此数据，就直接下一步，登录时候直接存储
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

	// 登录操作
	public void onLoginClick(View v) {

		if (mEtusername.length() < 1 || mEtpassword.length() < 1) {
			toastInfoShort("账户名或密码不能为空");
			return;
		}

		// 获取Bmob服务器中的数据
		getBmobDatas();

	}

	private void getBmobDatas() {
		
		final ProgressDialog dialog = act.progressDialogShows(true, "", "");
		
		BmobQuery<User> query = new BmobQuery<User>();
		// 查询playerName叫“比目”的数据
		// query.addWhereEqualTo("Username", "大圣");
		// query.addWhereEqualTo("Password", pwd);
		// 返回50条数据，如果不加上这条语句，默认返回10条数据
		// query.setLimit(50);
		// 执行查询方法
		query.findObjects(new FindListener<User>() {

			

			@Override
			public void done(List<User> users, BmobException e) {

				int j = 5;

				
				String username = getTvContent(mEtusername);
				String pwd = getTvContent(mEtpassword);
				
				

				if (e == null) {
					// toastInfoShort("查询成功：共"+users.size()+"条数据。");
					for (int i = 0; i < users.size(); i++) {

						mUsername = users.get(i).getUsername();
						mPassword = users.get(i).getPassword();

						if (mUsername.equals(username) && mPassword.equals(pwd)) {
							
							
							Intent intent = new Intent(act, HomeActivity.class);
							
							MyApplication.userInfo = users.get(i);
							
//							LogI("登陆时候保存到全局的信息"+users.get(i));
							
							//把用户手动输入的数据到MyApplication的静态Bean类中。
							if (!username.equals("")&&!pwd.equals("")) {
								/*User user2 = new User();
								user2.setUsername(username);
								user2.setPassword(pwd);*/
								
								//把数据保存到Application静态中
								
								
								//把收到输入的用户、密码数据存储到SharedPerferences中
							 
							 Editor editor = sharedPreferences.edit();
							 
							 editor.putString("username", username);
							 
							 editor.putString("password", pwd);
							 
							 //事物需要提交
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
						toastInfoShort("用户名或者密码错误");
					}

					// if (mUsername.equals("")&&mPassword.equals("")) {
					// return;
					// }

				} else {
					Log.i("bmob",
							"失败：" + e.getMessage() + "," + e.getErrorCode());
				}

			}
		});
	}

	/***
	 * 
	 * 回显操作（把定义在全局的静态变量中的数据拿出来）
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
