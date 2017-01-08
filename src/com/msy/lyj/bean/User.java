package com.msy.lyj.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

public class User extends BmobObject {

	private String Username;
	private String Password;
	private String Telphone;
	private BmobFile Headpicture;
	private List<String> PraiseAction;

	public User(String tableName) {
		super(tableName);
	}

	public User(String username, String password, String telphone, BmobFile headpicture, List<String> praiseAction) {
		super();
		Username = username;
		Password = password;
		Telphone = telphone;
		Headpicture = headpicture;
		PraiseAction = praiseAction;
	}

	public List<String> getPraiseAction() {
		
		if (PraiseAction == null) {
			PraiseAction = new ArrayList<String>();
		}
		return PraiseAction;
	}

	public void setPraiseAction(List<String> praiseAction) {
		PraiseAction = praiseAction;
	}

	public User(String username, String password, String telphone, BmobFile headpicture) {
		super();
		Username = username;
		Password = password;
		Telphone = telphone;
		Headpicture = headpicture;
	}

	public BmobFile getHeadpicture() {
		return Headpicture;
	}

	public void setHeadpicture(BmobFile headpicture) {
		Headpicture = headpicture;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getTelphone() {
		return Telphone;
	}

	public void setTelphone(String telphone) {
		Telphone = telphone;
	}

	@Override
	public String toString() {
		return "User [Username=" + Username + ", Password=" + Password + ", Telphone=" + Telphone + ", Headpicture="
				+ Headpicture + ", PraiseAction=" + PraiseAction + "]";
	}

	public User(String username, String password, String telphone) {
		super();
		Username = username;
		Password = password;
		Telphone = telphone;
	}

	public User() {
		super();
	}

}
