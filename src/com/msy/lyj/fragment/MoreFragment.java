package com.msy.lyj.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import c.system;
import cn.bmob.v3.exception.BmobException;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.List;

import com.msy.lyj.R;
import com.msy.lyj.activity.FindActionInfosActivity;
import com.msy.lyj.adapter.MyMoreGridViewAdapter;
import com.msy.lyj.application.MyApplication;
import com.msy.lyj.base.BaseFragment;
import com.msy.lyj.base.BaseInterface;
import com.msy.lyj.bean.ActionInfo;
import com.msy.lyj.utils.FindActionInfoUtils;
import com.msy.lyj.utils.FindActionInfoUtils.FindAllActionInfosListener;

public class MoreFragment extends BaseFragment implements BaseInterface,
		OnClickListener {

	// ��ѣ��ȶȣ������İ�ť
	private Button hotBtn, nearBtn;
	private WebView mMoreFragmentWebView;
	private TextView mTopTv;
	private ImageView mBack;// �Ϸ��ķ��ذ�ť

	// private String[] datas = {"����","���","�Ͼ�","֣��","ƽ��ɽ","�ܿ�","����","�Ϻ�"};
	// GridView����
	private GridView moreGridView;
	public String[] names = { "����", "����", "DIY", "����", "����", "����", "��ʷ", "����",
			"�Ƽ�", "����" };

	public int[] imgs = { R.drawable.more_zhoubian, R.drawable.more_shaoer,
			R.drawable.more_diy, R.drawable.more_jianshen,
			R.drawable.more_jishi, R.drawable.more_yanchu,
			R.drawable.more_zhanlan, R.drawable.more_shalong,
			R.drawable.more_pincha, R.drawable.more_juhui };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_more, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initViews();
		initDatas();
		initViewOpras();

	}

	@Override
	public void initViews() {

		// ��ѣ��ȶȣ������İ�ť
		// freeBtn = (Button) act.findViewById(R.id.frag_more_free_btn);
		hotBtn = (Button) act.findViewById(R.id.frag_more_hot_btn);
		nearBtn = (Button) act.findViewById(R.id.frag_more_near_btn);
		// GridView
		moreGridView = (GridView) act.findViewById(R.id.frag_more_gridview);
		mMoreFragmentWebView = (WebView) act
				.findById(R.id.more_fragment_webview);
		mTopTv = (TextView) act.findViewById(R.id.more_fragment_top_tv);

		mBack = (ImageView) findById(R.id.more_fragment_top_back);
	}

	@Override
	public void initDatas() {

		// ����ѣ��ȶȣ������İ�ť��ӵ��ʱ��
		// freeBtn.setOnClickListener(this);
		hotBtn.setOnClickListener(this);
		nearBtn.setOnClickListener(this);
		mBack.setOnClickListener(this);

		// GridView����
		moreGridView.setAdapter(new MyMoreGridViewAdapter(act, names, imgs));

	}

	@Override
	public void initViewOpras() {

		moreGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				final String actionType = names[position];
				FindActionInfoUtils.findAllActionInfos(4, actionType, 0, 0,
						new FindAllActionInfosListener() {

							@Override
							public void getActionInfo(List<ActionInfo> info,
									BmobException ex) {

								if (ex == null) {
									Intent intent = new Intent(act,
											FindActionInfosActivity.class);
									MyApplication.setDatas("actionInfos", info);
									MyApplication.setDatas("actionType",
											actionType);
									startActivity(intent);
								}

							}
						});

			}
		});

		/**
		 * Ĭ����ȥ���������������ҳ������
		 */

		if (mMoreFragmentWebView == null) {
			return;
		}
		mMoreFragmentWebView.loadUrl("http://www.baidu.com");

		// ����֧��js
		mMoreFragmentWebView.getSettings().setJavaScriptEnabled(true);

		/**
		 * Client:����webViewȥ�������֪ͨ,���������¼�
		 */
		mMoreFragmentWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("myTag", "url:" + url);
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

		});

		/**
		 * ChromeClient:����webViewȥ����Js���¼�,��վ�ı���,���صĽ���
		 */
		mMoreFragmentWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				// MainActivity.this.setTitle(title);
				mTopTv.setText(title);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// //��ѵļ���
		// case R.id.frag_more_free_btn:
		//
		// FindActionInfoUtils.findAllActionInfos(6, null, 0, 0, new
		// FindAllActionInfosListener() {
		//
		// @Override
		// public void getActionInfo(List<ActionInfo> info, BmobException ex) {
		//
		//
		// if (ex == null) {
		//
		// Intent intent = new Intent(act, FindActionInfosActivity.class);
		// MyApplication.setDatas("actionInfos", info);
		// MyApplication.setDatas("actionType", "���");
		// startActivity(intent);
		//
		// }
		//
		// }
		// });

		// break;

		// ���ŵļ���
		case R.id.frag_more_hot_btn:

			FindActionInfoUtils.findAllActionInfos(7, null, 0, 0,
					new FindAllActionInfosListener() {

						@Override
						public void getActionInfo(List<ActionInfo> info,
								BmobException ex) {

							if (ex == null) {
								Intent intent = new Intent(act,
										FindActionInfosActivity.class);
								MyApplication.setDatas("actionInfos", info);
								MyApplication.setDatas("actionType", "�ȵ�");
								startActivity(intent);
							}

						}
					});

			break;

		// �����ļ���
		case R.id.frag_more_near_btn:

			FindActionInfoUtils.findAllActionInfos(8, null, 0, 0,
					new FindAllActionInfosListener() {

						@Override
						public void getActionInfo(List<ActionInfo> info,
								BmobException ex) {
							if (ex == null) {

								Intent intent = new Intent(act,
										FindActionInfosActivity.class);
								MyApplication.setDatas("actionInfos", info);
								MyApplication.setDatas("actionType", "����");
								startActivity(intent);

							}

						}
					});

			break;

		case R.id.more_fragment_top_back:

			AlertDialog.Builder builder = new Builder(act);

			builder.setCancelable(false);
			builder.setTitle("��ܰ��ʾ");
			builder.setMessage("��ȷ��Ҫ�뿪����,����");

			builder.setPositiveButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
                                 
							
						}
					});

			builder.setNegativeButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							System.exit(0);
						}
					});
			// ���þ���Ի����λ�ú�͸����
			AlertDialog alertDialog = builder.create();

			Window window = alertDialog.getWindow();
			window.setGravity(Gravity.CENTER);
			WindowManager.LayoutParams wp = window.getAttributes();
			wp.alpha = 0.6f;
			window.setAttributes(wp);

			alertDialog.show();

			break;
		}

	}
}
