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
 * ע�����
 * 
 * 
 */
public class RegisterActivity extends BaseActivity implements BaseInterface {

	private EditText mEtPhonenumber, mEtSMSPhoneCode;// �����ֻ�����,�ֻ���֤��
	private String mStrPhonenumber, mStrSMSPhoneCode;
	private Button mBtnPhoneCode;// ��ȡ��֤�밴ť
	private EditText mEtUsername, mEtPassword, mEtPassword2;// �û��������룬ȷ������
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

		// ע���Ϊ�۲���

		SMSReceiver.setIsetCodeClickListener(new IsetCodeClickListener() {

			@Override
			public void setCode(String Code) {

				// ����֤�����õ�������֤��λ�ô�
				if (Code != null) {
					mEtSMSPhoneCode.setText(Code);
				}
			}
		});

	}

	public void getCodeClick(View v) {

		/**
		 * 
		 * �������ֻ����ж���֤��ʱ���жϲ���
		 */
		mStrPhonenumber = getTvContent(mEtPhonenumber);

		if (mStrPhonenumber.length() != 11 || mStrPhonenumber.equals("")
				|| TextUtils.isEmpty(mStrPhonenumber)) {
			toastInfoShort("��������ȷ���ֻ���");
			return;
		}

		// ����һ����ǣ����������ж�
		// boolean flag = false;

		// �����ж��ֻ���
		/*
		 * Pattern p = Pattern
		 * .compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))//d{8}$");
		 * Matcher m = p.matcher(mStrPhonenumber); m.matches();
		 */

		// ����ʱ�Ĳ����������ڲ���

		mBtnPhoneCode.setClickable(false);// ���ð�ť���ɵ��

		CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

			@Override
			public void onTick(long millisInFuture) {

				mBtnPhoneCode.setText((millisInFuture / 1000) + "s");

			}

			@Override
			public void onFinish() {

				mBtnPhoneCode.setClickable(true);
				mBtnPhoneCode.setText("��ȡ��֤��");
			}
		};

		countDownTimer.start();// ��������ʱ

		BmobSMS.requestSMSCode(this, mStrPhonenumber, "¿�Ѿ�",
				new RequestSMSCodeListener() {

					@Override
					public void done(Integer smsId, BmobException ex) {
						// TODO Auto-generated method stub
						if (ex == null) {// ��֤�뷢�ͳɹ�
							Log.i("MyTag", "����id��" + smsId);// ���ڲ�ѯ���ζ��ŷ�������
						} else {
							Log.i("MyTag", "���ŷ���ʧ�ܣ�" + ex.getLocalizedMessage());// ���ڲ�ѯʧ�ܵ�״��
						}
					}
				});

		/*
		 * sendTime�ĸ�ʽΪ yyyy-MM-dd
		 * HH:mm:ss,���sendTime�ĸ�ʽ����ȷ���������ʱ���ǹ�ȥ��ʱ�䣬��ô���Ż���������; SimpleDateFormat
		 * format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String sendTime
		 * = format.format(new Date());
		 */
		/***
		 * 
		 * ͨ��requestSMSCode��ʽ�����ֻ��ŵĸ��û�����ָ������ģ��Ķ�����֤��
		 */

	}

	/***
	 * ������һҳ
	 * 
	 * @param v
	 */
	public void onRegisterBackListener(View v) {
		finish();
	}

	/***
	 * 
	 * ע�Ṧ�ܵ�ʵ��
	 * 
	 * @param v
	 */
	public void onRegisterregisterClick(View v) {

		mStrUsername = getTvContent(mEtUsername);
		mStrPassword = getTvContent(mEtPassword);
		mStrPassword2 = getTvContent(mEtPassword2);
		mStrSMSPhoneCode = getTvContent(mEtSMSPhoneCode);

		if (!mStrPassword.equals(mStrPassword2)) {
			toastInfoShort("�������벻һ��");
			return;
		}

		/**
		 * 
		 * ��֤��֤��
		 * 
		 * ͨ��verifySmsCode��ʽ����֤�ö�����֤�룺
		 * ���������ͨ����֤��֤����Ϣ���ܽ���ע��ķ�ʽ����UIҳ�����֤�����ݷ���bmob������
		 * ���ڻص������ķ����У�ֻ����֤��ȷ���Ż��ܹ�ע��,�ڷ������б��� ��ʾ����
		 */

		Log.i("myTag", mStrPhonenumber + "��֤�룺" + mStrSMSPhoneCode);
		BmobSMS.verifySmsCode(this, mStrPhonenumber, mStrSMSPhoneCode,
				new VerifySMSCodeListener() {

					@Override
					public void done(BmobException ex) {
						// TODO Auto-generated method stub
						if (ex == null) {// ������֤������֤�ɹ�
							Log.i("bmob", "��֤ͨ��");

							user = new User();
							user.setUsername(mStrUsername);
							user.setPassword(mStrPassword);
							user.setTelphone(mStrPhonenumber);

							// ע�⣺������save��������ע��(����BmobUserʱ������Save����)

							user.save(new SaveListener<String>() {

								@Override
								public void done(String str,
										cn.bmob.v3.exception.BmobException e) {
									if (e == null) {

										toastInfoShort("ע��ɹ�");
										
										/*
										 * 
										 * ��ע��ɹ������ݴ洢��ȫ�ֵ�Application��
										 * 
										 * ����ȫ�ֵ���
										 * 
										 */
										MyApplication.userInfo = user;

										// �رմ�ҳ��
										RegisterActivity.this.finish();

									} else {
										Log.i("maTag", "ע��ʧ�ܣ�" + e);

									}

								}
							});

						} else {
							Log.i("bmob", "��֤ʧ�ܣ�code =" + ex.getErrorCode()
									+ ",msg = " + ex.getLocalizedMessage());
						}
					}
				});

	}
	
	/***
	 * ��ע��ҳ������ʱ���ͷż�����Դ
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSReceiver.setIsetCodeClickListener(null);
		
	}
}
