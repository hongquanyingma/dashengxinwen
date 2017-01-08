package com.msy.lyj.bean;


import cn.bmob.v3.BmobObject;

public class Messages extends BmobObject{

	private String ActionUser;
	private String ActionObjectId;
	private String ActionMessage;
	public String getActionUser() {
		return ActionUser;
	}
	public void setActionUser(String actionUser) {
		ActionUser = actionUser;
	}
	public String getActionObjectId() {
		return ActionObjectId;
	}
	public void setActionObjectId(String actionObjectId) {
		ActionObjectId = actionObjectId;
	}
	public String getActionMessage() {
		return ActionMessage;
	}
	public void setActionMessage(String actionMessage) {
		ActionMessage = actionMessage;
	}
	@Override
	public String toString() {
		return "Messages [ActionUser=" + ActionUser + ", ActionObjectId="
				+ ActionObjectId + ", ActionMessage=" + ActionMessage + "]";
	}
	public Messages(String actionUser, String actionObjectId,
			String actionMessage) {
		super();
		ActionUser = actionUser;
		ActionObjectId = actionObjectId;
		ActionMessage = actionMessage;
	}
	public Messages() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Messages(String tableName) {
		super(tableName);
		// TODO Auto-generated constructor stub
	}

	
	
}
