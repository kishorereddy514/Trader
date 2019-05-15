package com.qa.bean;

import com.qa.base.TestBase;

public class Users {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Users() {

	}

	public Users(Boolean legacy) {
		this.userName = legacy ? TestBase.LEGACY_SYSTEM_USER_NAME : TestBase.NEW_SYSTEM_USER_NAME;
		this.password = legacy ? TestBase.LEGACY_SYSTEM_PASSWORD : TestBase.NEW_SYSTEM_PASSWORD;
	}

}
