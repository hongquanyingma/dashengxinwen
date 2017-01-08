package com.msy.lyj.utils;

import java.util.List;

import com.msy.lyj.application.MyApplication;
import com.msy.lyj.bean.ActionInfo;

import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/***
 * 
 * ��ѯ�û�������Ĺ�����
 * 
 * @author Administrator
 *
 */
public class FindActionInfoUtils {

	/**
	 * ��ѯ�����Ϣ
	 * 
	 * @param type ����,1��Ĭ�ϲ�ѯ10�����ݣ������ӷ�������ȡ����/����ˢ��
	 *                 2��ʹ�ò���3����ѯcount������,����skip������/���ظ���
	 *                 3����ϲ���2����ѯһ������(����2���û�id)
	 *                 4����ϲ���2����ѯ��������(����2���û�ѡ���type)
	 *                 5����ϲ���2����ѯ����ָ���ؼ��ֵĻ(����2���û�����Ĺؼ���)
	 *                 6����ϲ���2����ѯmoneyΪ0�Ļ(����2���û������money)
	 *                 7����ϲ���2����ѯ���ŵĻ
	 *                 8����ϲ���2����ѯ�����Ļ
	 *                 
	 * @param data
	 * @param skip  ��������������
	 * @param count  ��Ҫ��ѯ������������
	 * @param listener  ���ü�������������
	 */
	public static void findAllActionInfos(int type, String data, int skip, int count,
			final FindAllActionInfosListener listener) {

		BmobQuery<ActionInfo> bmobQuery = new BmobQuery<ActionInfo>();

		
		//���õ�ǰ���ҵ�����
		switch (type) {
		case 1:
			//������ѯ
			bmobQuery.setLimit(10);
			break;

		case 2:
			bmobQuery.setSkip(skip);
			break;
			
		case 3:
			bmobQuery.addWhereEqualTo("objectId", data);
			break;
		case 4:
			bmobQuery.addWhereEqualTo("ActionClass", data);
			break;
		case 5:
			bmobQuery.addWhereContains("ActionName", (String)data);
			break;
//		case 6:
//			bmobQuery.addWhereEqualTo("ActionRMB", "0");
//			
//			break;
		case 7:
			//����
			// ����score�ֶ�������ʾ����
			//query.order("score");
			// ����score�ֶν�����ʾ����
			//query.order("-score");
			
			//�����û��ղ������в���
			// ��������ֶο����ã������ŷָ�
			bmobQuery.order("-loveCount,createdAt");
			break;
		case 8:
			//����
			bmobQuery.addWhereNear("ActionPoint", new BmobGeoPoint(MyApplication.getLongitude(), MyApplication.getLatitude()));
			
			
			
			
			break;
		}
		
		
		bmobQuery.findObjects(new FindListener<ActionInfo>() {
			
			@Override
			public void done(List<ActionInfo> actionInfos, BmobException ex) {
				
				if (ex == null) {
					//Log.i("myTag", "��ѯ�ɹ�"+actionInfos.toString());
					listener.getActionInfo(actionInfos, ex);
				}
				
				
				
			}
		});

	}

	public interface FindAllActionInfosListener {

		void getActionInfo(List<ActionInfo> info,BmobException ex);

	}
}