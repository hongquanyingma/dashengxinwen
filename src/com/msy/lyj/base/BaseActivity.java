package com.msy.lyj.base;

import com.msy.lyj.R;
import com.msy.lyj.runman.CustomProgressDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 
 * �����Ը��ߣ���Activity�и��ӳ��õķ�����ʹ���Ǳ�д������ӵļ��
 * 
 * @author Administrator
 * 
 */
public class BaseActivity extends FragmentActivity {

	private static final String TAG = "myTag";

	
	public BaseActivity act;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		act = this;
	}
	/**
	 * 
	 * Toast,һ�����ݣ�����3��
	 * 
	 * @param info
	 *            ��ҪToast������
	 */
	public void toastInfoShort(String info) {

		Toast.makeText(this, info, 0).show();
	}

	/**
	 * 
	 * Toast,һ�����ݣ�����5��
	 * 
	 * @param str
	 *            ��ҪToast������
	 */
	public void toastInfoLong(String str) {

		Toast.makeText(this, str, 1).show();
	}

	/****
	 * 
	 * �����Ϣ��LogCat
	 * 
	 * @param str
	 *            ��Ҫ�������Ϣ
	 */
	public void LogI(String str) {

		Log.i(TAG, str);
	}

	public void LogE(String str) {

		Log.e(TAG, str);
	}

	/***
	 * ͨ��ָ����id  �ҵ��ؼ��Ķ���
	 * @param id
	 * @return   ���ؿؼ��Ķ�����Ϣ
	 */
	public View findById(int id) {

		return findViewById(id);
	}
	
	/***
	 * ��ȡTextView������������Text��Ϣ
	 * @param textView  �ؼ��Ķ���
	 * @return   ���ش˿ؼ�����Ϣ
	 */
	public String getTvContent(TextView textView){
		
		if (textView != null) {
			
			String str = textView.getText().toString().trim();
			
			if (str == null) {
				str = "";
				return str;
			}
			
			return str;
		}
		
		return "";
	}
	
	
	/**
	 * ������ʾһ���������Ի���,ע�⣬��show,��������Dialog�Ŀ��
	 * @param flag �˶Ի����Ƿ����ȡ��
	 * @param title �˶Ի���ı���
 	 * @param message �˶Ի��������
	 * @return  �Ի���Ķ���
	 */
	
	
	public CustomProgressDialog progressDialogShows(boolean flag,CharSequence title,CharSequence message){
		
		CustomProgressDialog dialog = new CustomProgressDialog(act,null,R.anim.frame2);
		
       dialog.setCancelable(flag);
//       dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       dialog.setTitle(title);
       dialog.setMessage(message);
       dialog.show();
       WindowManager.LayoutParams layoutParams= dialog.getWindow().getAttributes();
       layoutParams.alpha = 1.0f;
       layoutParams.width = 300;
       layoutParams.height =300;
      dialog.getWindow().setGravity(Gravity.AXIS_CLIP);
      dialog.getWindow().setGravity(Gravity.CENTER);
       dialog.getWindow().setAttributes(layoutParams);
       
       dialog.setView(dialog.getWindow().getCurrentFocus(), 0,0,0,0);
      
       
		return dialog;
	}
	
	

	
}
