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
 * Activity的碎片化
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
	 * Toast,一段数据，持续3秒
	 * 
	 * @param info
	 *            需要Toast的数据
	 */
	public void toastInfoShort(String info) {

		Toast.makeText(act, info, 0).show();
		
	}

	/**
	 * 
	 * Toast,一段数据，持续5秒
	 * 
	 * @param str
	 *            需要Toast的数据
	 */
	public void toastInfoLong(String str) {

		Toast.makeText(act, str, 1).show();
	}

	/****
	 * 
	 * 输出信息到LogCat
	 * 
	 * @param str
	 *            需要输出的信息
	 */
	public void LogI(String str) {

		Log.i(TAG, str);
	}

	public void LogE(String str) {

		Log.e(TAG, str);
	}

	/***
	 * 通过指定的id  找到控件的对象
	 * @param id
	 * @return   返回控件的对象信息
	 */
	public View findById(int id) {

		return act.findById(id);
	} 
	
	/***
	 * 获取TextView类或者它子类的Text信息
	 * @param textView  控件的对象
	 * @return   返回此控件的信息
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
	 * 用来显示一个进度条对话框
	 * @param flag 此对话框是否可以取消
	 * @param title 此对话框的标题
 	 * @param message 此对话框的内容
	 * @return  对话框的对象
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
