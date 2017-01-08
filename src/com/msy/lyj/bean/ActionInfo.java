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
 * ����Դ����ӻ��Ϣ�������
 * 
 * @author Administrator
 *
 */
public class ActionInfo extends BmobObject {

	private String ActionCurrentTime;// ������ĵ�ǰ��ʱ��

	public String getActionCurrentTime() {
		return ActionCurrentTime;
	}

	public void setActionCurrentTime(String actionCurrentTime) {
		ActionCurrentTime = actionCurrentTime;
	}

	private String ActionUserId;// �������û�id ������ѯ�û��� ϵͳ�Զ���ȡ���
	
	private String ActionDetails;// � ���� �û�����
	private String ActionCity;// ����ڵĳ��� �û�ѡ��/����
	private String ActionSite;// ����ϵĵص� �û�ѡ��/����
	private String ActionRMB; // ��۸� �û�����
	private String ActionDesc;// ���� �û�����
	private String ActionClass;// ����� �û�ѡ��
	private String ActionTime;// ���ʼ��ʱ�� �û�ѡ��
	private String ActionName;// ����� �û�2����
	private BmobGeoPoint ActionPoint;// ��ľ�γ��
	// private BDLocation Location; //���γ�� �û�ѡ��/shuru1
	// private List<UserMessage> meaaages; //�û��������� //ϵͳ¼��
	private List<String> paymentUserId; // ���븶����û�ID ϵͳ¼��
	private List<String> startUserId;// ���ʼ������ֳ����û�ID ϵͳ¼��
	private Boolean flag;// ��ǰ�Ļ״̬���Ƿ�¼�룩 ϵͳ¼��
	private List<BmobFile> ActionPicture;
	private Integer loveCount;//����û��ղصĴ�����Integer�������ͣ���Ҫ��int���ͣ�

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
