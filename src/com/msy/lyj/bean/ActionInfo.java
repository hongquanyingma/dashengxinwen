package com.msy.lyj.bean;

import java.util.List;

import android.graphics.Bitmap;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;

/**
 * 
 * 
 * 数据源，添加活动信息的类对象
 * 
 * @author Administrator
 *
 */
public class ActionInfo extends BmobObject {

	private String ActionCurrentTime;// 发布活动的当前的时间

	public String getActionCurrentTime() {
		return ActionCurrentTime;
	}

	public void setActionCurrentTime(String actionCurrentTime) {
		ActionCurrentTime = actionCurrentTime;
	}

	private String ActionUserId;// 活动发起的用户id 用来查询用户表 系统自动获取添加
	
	private String ActionDetails;// 活动 详情 用户输入
	private String ActionCity;// 活动所在的城市 用户选择/输入
	private String ActionSite;// 活动集合的地点 用户选择/输入
	private String ActionRMB; // 活动价格 用户输入
	private String ActionDesc;// 活动简介 用户输入
	private String ActionClass;// 活动类型 用户选择
	private String ActionTime;// 活动开始的时间 用户选择
	private String ActionName;// 活动名称 用户2输入
	private BmobGeoPoint ActionPoint;// 活动的经纬度
	// private BDLocation Location; //活动经纬度 用户选择/shuru1
	// private List<UserMessage> meaaages; //用户评价内容 //系统录入
	private List<String> paymentUserId; // 参与付款的用户ID 系统录入
	private List<String> startUserId;// 活动开始。到活动现场的用户ID 系统录入
	private Boolean flag;// 当前的活动状态（是否录入） 系统录入
	private List<BmobFile> ActionPicture;
	private Integer loveCount;//活动被用户收藏的次数，Integer对象类型（不要用int类型）

	private List<String> praiseUsers;
	
	
	public ActionInfo(String actionCurrentTime, String actionUserId, String actionDetails, String actionCity,
			String actionSite, String actionRMB, String actionDesc, String actionClass, String actionTime,
			String actionName, BmobGeoPoint actionPoint, List<String> paymentUserId, List<String> startUserId,
			Boolean flag, List<BmobFile> actionPicture, Integer loveCount, List<String> praiseUsers) {
		super();
		ActionCurrentTime = actionCurrentTime;
		ActionUserId = actionUserId;
		ActionDetails = actionDetails;
		ActionCity = actionCity;
		ActionSite = actionSite;
		ActionRMB = actionRMB;
		ActionDesc = actionDesc;
		ActionClass = actionClass;
		ActionTime = actionTime;
		ActionName = actionName;
		ActionPoint = actionPoint;
		this.paymentUserId = paymentUserId;
		this.startUserId = startUserId;
		this.flag = flag;
		ActionPicture = actionPicture;
		this.loveCount = loveCount;
		this.praiseUsers = praiseUsers;
	}

	public List<String> getPraiseUsers() {
		return praiseUsers;
	}

	public void setPraiseUsers(List<String> praiseUsers) {
		this.praiseUsers = praiseUsers;
	}

	public String getActionUserId() {
		return ActionUserId;
	}

	public void setActionUserId(String actionUserId) {
		ActionUserId = actionUserId;
	}

	public String getActionDetails() {
		return ActionDetails;
	}

	public void setActionDetails(String actionDetails) {
		ActionDetails = actionDetails;
	}

	public String getActionCity() {
		return ActionCity;
	}

	public void setActionCity(String actionCity) {
		ActionCity = actionCity;
	}

	public String getActionSite() {
		return ActionSite;
	}

	public void setActionSite(String actionSite) {
		ActionSite = actionSite;
	}

	public String getActionRMB() {
		return ActionRMB;
	}

	public void setActionRMB(String actionRMB) {
		ActionRMB = actionRMB;
	}

	public String getActionDesc() {
		return ActionDesc;
	}

	public void setActionDesc(String actionDesc) {
		ActionDesc = actionDesc;
	}

	public String getActionClass() {
		return ActionClass;
	}

	public void setActionClass(String actionClass) {
		ActionClass = actionClass;
	}

	public String getActionTime() {
		return ActionTime;
	}

	public void setActionTime(String actionTime) {
		ActionTime = actionTime;
	}

	public String getActionName() {
		return ActionName;
	}

	public void setActionName(String actionName) {
		ActionName = actionName;
	}

	public BmobGeoPoint getActionPoint() {
		return ActionPoint;
	}

	public void setActionPoint(BmobGeoPoint actionPoint) {
		ActionPoint = actionPoint;
	}

	public List<String> getPaymentUserId() {
		return paymentUserId;
	}

	public void setPaymentUserId(List<String> paymentUserId) {
		this.paymentUserId = paymentUserId;
	}

	public List<String> getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(List<String> startUserId) {
		this.startUserId = startUserId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<BmobFile> getActionPicture() {
		return ActionPicture;
	}

	public void setActionPicture(List<BmobFile> actionPicture) {
		ActionPicture = actionPicture;
	}

	public Integer getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(Integer loveCount) {
		this.loveCount = loveCount;
	}

	@Override
	public String toString() {
		return "ActionInfo [ActionCurrentTime=" + ActionCurrentTime + ", ActionUserId=" + ActionUserId
				+ ", ActionDetails=" + ActionDetails + ", ActionCity=" + ActionCity + ", ActionSite=" + ActionSite
				+ ", ActionRMB=" + ActionRMB + ", ActionDesc=" + ActionDesc + ", ActionClass=" + ActionClass
				+ ", ActionTime=" + ActionTime + ", ActionName=" + ActionName + ", ActionPoint=" + ActionPoint
				+ ", paymentUserId=" + paymentUserId + ", startUserId=" + startUserId + ", flag=" + flag
				+ ", ActionPicture=" + ActionPicture + ", loveCount=" + loveCount + ", praiseUsers=" + praiseUsers
				+ "]";
	}

	public ActionInfo(String actionCurrentTime, String actionUserId, String actionDetails, String actionCity,
			String actionSite, String actionRMB, String actionDesc, String actionClass, String actionTime,
			String actionName, BmobGeoPoint actionPoint, List<String> paymentUserId, List<String> startUserId,
			Boolean flag, List<BmobFile> actionPicture, Integer loveCount) {
		super();
		ActionCurrentTime = actionCurrentTime;
		ActionUserId = actionUserId;
		ActionDetails = actionDetails;
		ActionCity = actionCity;
		ActionSite = actionSite;
		ActionRMB = actionRMB;
		ActionDesc = actionDesc;
		ActionClass = actionClass;
		ActionTime = actionTime;
		ActionName = actionName;
		ActionPoint = actionPoint;
		this.paymentUserId = paymentUserId;
		this.startUserId = startUserId;
		this.flag = flag;
		ActionPicture = actionPicture;
		this.loveCount = loveCount;
	}

	public ActionInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionInfo(String tableName) {
		super(tableName);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
