package com.msy.lyj.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.msy.lyj.R;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseActivity;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.Messages;

public class PingLun extends BaseActivity implements BaseInterface,
		OnClickListener {

	private EditText et;
	private Button btn;
	private ImageView iv;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_message);

		initViews();
		initDatas();
		initViewOpras();

	}

	@Override
	public void initViews() {

		iv = (ImageView) findById(R.id.act_message_back);
		et = (EditText) findById(R.id.act_message_et);
		btn = (Button) findById(R.id.act_message_btn);
	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initViewOpras() {

		iv.setOnClickListener(this);
		et.setOnClickListener(this);
		btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.act_message_back:

			this.finish();

			break;

		case R.id.act_message_et:

			break;

		case R.id.act_message_btn:

			String str = et.getText().toString().trim();
			if (str.equals("")) {
				toastInfoShort("请输入评论");
				return;
			}

			Intent intent = getIntent();
			String actionObjectId = intent.getStringExtra("actionObjectId");

			LogI("当前活动的id" + actionObjectId);
			LogI("----------------");
			String username = MyApplication.userInfo.getUsername();
			LogI("当前登录的用户" + username);

			Messages mm = new Messages();

			mm.setActionMessage(str);

			mm.setActionUser(username);
			mm.setActionObjectId(actionObjectId);

			// 注意：不能用save方法进行注册(当用BmobUser时不能用Save方法)

			mm.save(new SaveListener<String>() {

				@Override
				public void done(String arg0, BmobException e) {

					if (e == null) {

						Toast.makeText(PingLun.this, "评论成功", 0).show();
						et.setText("");
						finish();
						Log.i("myTag", "保存成功");

					} else {
						Log.i("maTag", "评论失败：" + e);

					}

				}
			});
			
			
			break;
		}

	}

}
