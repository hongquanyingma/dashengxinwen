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
 * 查询用户发布活动的工具类
 * 
 * @author Administrator
 *
 */
public class FindActionInfoUtils {

	/**
	 * 查询活动的信息
	 * 
	 * @param type 类型,1、默认查询10条数据，正常从服务器获取数据/下拉刷新
	 *                 2、使用参数3，查询count条数据,忽略skip条数据/加载更多
	 *                 3、结合参数2，查询一条数据(参数2，用户id)
	 *                 4、结合参数2，查询多条数据(参数2，用户选择的type)
	 *                 5、结合参数2，查询包含指定关键字的活动(参数2，用户输入的关键字)
	 *                 6、结合参数2，查询money为0的活动(参数2，用户输入的money)
	 *                 7、结合参数2，查询热门的活动
	 *                 8、结合参数2，查询附近的活动
	 *                 
	 * @param data
	 * @param skip  跳过多少条数据
	 * @param count  需要查询的数据总条数
	 * @param listener  设置监听，回显数据
	 */
	public static void findAllActionInfos(int type, String data, int skip, int count,
			final FindAllActionInfosListener listener) {

		BmobQuery<ActionInfo> bmobQuery = new BmobQuery<ActionInfo>();

		
		//设置当前查找的类型
		switch (type) {
		case 1:
			//正常查询
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
			//热门
			// 根据score字段升序显示数据
			//query.order("score");
			// 根据score字段降序显示数据
			//query.order("-score");
			
			//根据用户收藏数进行查找
			// 多个排序字段可以用（，）号分隔
			bmobQuery.order("-loveCount,createdAt");
			break;
		case 8:
			//附近
			bmobQuery.addWhereNear("ActionPoint", new BmobGeoPoint(MyApplication.getLongitude(), MyApplication.getLatitude()));
			
			
			
			
			break;
		}
		
		
		bmobQuery.findObjects(new FindListener<ActionInfo>() {
			
			@Override
			public void done(List<ActionInfo> actionInfos, BmobException ex) {
				
				if (ex == null) {
					//Log.i("myTag", "查询成功"+actionInfos.toString());
					listener.getActionInfo(actionInfos, ex);
				}
				
				
				
			}
		});

	}

	public interface FindAllActionInfosListener {

		void getActionInfo(List<ActionInfo> info,BmobException ex);

	}
}