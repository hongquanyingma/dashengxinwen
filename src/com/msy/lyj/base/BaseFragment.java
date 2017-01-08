package com.msy.lyj.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/***
 * Activity����Ƭ��
 * @author Administrator
 *
 */
public class BaseFragment extends Fragment{

    private static final String TAG = "myTag";

	public BaseActivity act;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		act = (BaseActivity) activity;
	}

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		
		
	}
	
	@Override
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	

	
	/**
	 * 
	 * Toast,һ�����ݣ�����3��
	 * 
	 * @param info
	 *            ��ҪToast������
	 */
	public void toastInfoShort(String info) {

		Toast.makeText(act, info, 0).show();
		
	}

	/**
	 * 
	 * Toast,һ�����ݣ�����5��
	 * 
	 * @param str
	 *            ��ҪToast������
	 */
	public void toastInfoLong(String str) {

		Toast.makeText(act, str, 1).show();
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

		return act.findById(id);
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
	 * ������ʾһ���������Ի���
	 * @param flag �˶Ի����Ƿ����ȡ��
	 * @param title �˶Ի���ı���
 	 * @param message �˶Ի��������
	 * @return  �Ի���Ķ���
	 */
	
	public ProgressDialog progressDialogShows(boolean flag,CharSequence title,CharSequence message){
		
       ProgressDialog dialog = new ProgressDialog(getActivity());
		
       dialog.setCancelable(flag);
       dialog.setTitle(title);
       dialog.setMessage(message);
       dialog.show();
		return dialog;
	}
	
	
}
