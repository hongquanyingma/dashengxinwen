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
 * 兼容性更高，简化Activity中复杂常用的方法，使我们编写代码更加的简洁
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
	 * Toast,一段数据，持续3秒
	 * 
	 * @param info
	 *            需要Toast的数据
	 */
	public void toastInfoShort(String info) {

		Toast.makeText(this, info, 0).show();
	}

	/**
	 * 
	 * Toast,一段数据，持续5秒
	 * 
	 * @param str
	 *            需要Toast的数据
	 */
	public void toastInfoLong(String str) {

		Toast.makeText(this, str, 1).show();
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

		return findViewById(id);
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
	 * 用来显示一个进度条对话框,注意，先show,才能设置Dialog的宽高
	 * @param flag 此对话框是否可以取消
	 * @param title 此对话框的标题
 	 * @param message 此对话框的内容
	 * @return  对话框的对象
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
