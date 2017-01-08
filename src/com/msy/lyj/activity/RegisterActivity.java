package com.msy.lyj.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.msy.lyj.R;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.User;
import com.msy.lyj.receiver.SMSReceiver;
import com.msy.lyj.receiver.SMSReceiver.IsetCodeClickListener;

/**
 * 注册界面
 * 
 * 
 */
public class RegisterActivity extends BaseActivity implements BaseInterface {

	private EditText mEtPhonenumber, mEtSMSPhoneCode;// 输入手机号码,手机验证码
	private String mStrPhonenumber, mStrSMSPhoneCode;
	private Button mBtnPhoneCode;// 获取验证码按钮
	private EditText mEtUsername, mEtPassword, mEtPassword2;// 用户名，密码，确认密码
	private String mStrUsername, mStrPassword, mStrPassword2;

	private User user;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_register);
		initViews();
		initDatas();
		initViewOpras();
	}

	@Override
	public void initViews() {

		mEtPhonenumber = (EditText) findById(R.id.act_register_phoneNumber);
		mEtSMSPhoneCode = (EditText) findById(R.id.act_Register_SmsCode);
		mBtnPhoneCode = (Button) findById(R.id.act_register_code_btn);
		mEtUsername = (EditText) findById(R.id.act_register_username);
		mEtPassword = (EditText) findById(R.id.act_register_Et_pwd);
		mEtPassword2 = (EditText) findById(R.id.act_register_Et_pwd2);

	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initViewOpras() {

		// 注册成为观察者

		SMSReceiver.setIsetCodeClickListener(new IsetCodeClickListener() {

			@Override
			public void setCode(String Code) {

				// 把验证码设置到输入验证码位置处
				if (Code != null) {
					mEtSMSPhoneCode.setText(Code);
				}
			}
		});

	}

	public void getCodeClick(View v) {

		/**
		 * 
		 * 当输入手机号判断验证码时的判断操作
		 */
		mStrPhonenumber = getTvContent(mEtPhonenumber);

		if (mStrPhonenumber.length() != 11 || mStrPhonenumber.equals("")
				|| TextUtils.isEmpty(mStrPhonenumber)) {
			toastInfoShort("请输入正确的手机号");
			return;
		}

		// 设置一个标记，进行正则判断
		// boolean flag = false;

		// 正则判断手机号
		/*
		 * Pattern p = Pattern
		 * .compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))//d{8}$");
		 * Matcher m = p.matcher(mStrPhonenumber); m.matches();
		 */

		// 倒计时的操作，匿名内部类

		mBtnPhoneCode.setClickable(false);// 设置按钮不可点击

		CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

			@Override
			public void onTick(long millisInFuture) {

				mBtnPhoneCode.setText((millisInFuture / 1000) + "s");

			}

			@Override
			public void onFinish() {

				mBtnPhoneCode.setClickable(true);
				mBtnPhoneCode.setText("获取验证码");
			}
		};

		countDownTimer.start();// 开启倒计时

		BmobSMS.requestSMSCode(this, mStrPhonenumber, "驴友聚",
				new RequestSMSCodeListener() {

					@Override
					public void done(Integer smsId, BmobException ex) {
						// TODO Auto-generated method stub
						if (ex == null) {// 验证码发送成功
							Log.i("MyTag", "短信id：" + smsId);// 用于查询本次短信发送详情
						} else {
							Log.i("MyTag", "短信发送失败：" + ex.getLocalizedMessage());// 用于查询失败的状况
						}
					}
				});

		/*
		 * sendTime的格式为 yyyy-MM-dd
		 * HH:mm:ss,如果sendTime的格式不正确或者是这个时间是过去的时间，那么短信会立即发送; SimpleDateFormat
		 * format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String sendTime
		 * = format.format(new Date());
		 */
		/***
		 * 
		 * 通过requestSMSCode方式给绑定手机号的该用户发送指定短信模板的短信验证码
		 */

	}

	/***
	 * 返回上一页
	 * 
	 * @param v
	 */
	public void onRegisterBackListener(View v) {
		finish();
	}

	/***
	 * 
	 * 注册功能的实现
	 * 
	 * @param v
	 */
	public void onRegisterregisterClick(View v) {

		mStrUsername = getTvContent(mEtUsername);
		mStrPassword = getTvContent(mEtPassword);
		mStrPassword2 = getTvContent(mEtPassword2);
		mStrSMSPhoneCode = getTvContent(mEtSMSPhoneCode);

		if (!mStrPassword.equals(mStrPassword2)) {
			toastInfoShort("两次密码不一样");
			return;
		}

		/**
		 * 
		 * 验证验证码
		 * 
		 * 通过verifySmsCode方式可验证该短信验证码：
		 * 这个方法是通过验证验证码信息才能进行注册的方式，把UI页面的验证码数据发给bmob服务器
		 * ，在回调回来的方法中，只有验证正确，才会能够注册,在服务器列表中 显示数据
		 */

		Log.i("myTag", mStrPhonenumber + "验证码：" + mStrSMSPhoneCode);
		BmobSMS.verifySmsCode(this, mStrPhonenumber, mStrSMSPhoneCode,
				new VerifySMSCodeListener() {

					@Override
					public void done(BmobException ex) {
						// TODO Auto-generated method stub
						if (ex == null) {// 短信验证码已验证成功
							Log.i("bmob", "验证通过");

							user = new User();
							user.setUsername(mStrUsername);
							user.setPassword(mStrPassword);
							user.setTelphone(mStrPhonenumber);

							// 注意：不能用save方法进行注册(当用BmobUser时不能用Save方法)

							user.save(new SaveListener<String>() {

								@Override
								public void done(String str,
										cn.bmob.v3.exception.BmobException e) {
									if (e == null) {

										toastInfoShort("注册成功");
										
										/*
										 * 
										 * 把注册成功的数据存储到全局的Application中
										 * 
										 * 方便全局调用
										 * 
										 */
										MyApplication.userInfo = user;

										// 关闭此页面
										RegisterActivity.this.finish();

									} else {
										Log.i("maTag", "注册失败：" + e);

									}

								}
							});

						} else {
							Log.i("bmob", "验证失败：code =" + ex.getErrorCode()
									+ ",msg = " + ex.getLocalizedMessage());
						}
					}
				});

	}
	
	/***
	 * 当注册页面销毁时，释放监听资源
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSReceiver.setIsetCodeClickListener(null);
		
	}
}
