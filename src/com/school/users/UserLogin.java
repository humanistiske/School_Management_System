package com.school.users;

import com.school.UserType;

public class UserLogin 
{
	private String userid;
	private static UserType userType;
	private static int id;
	private static String password;

	public UserLogin(UserType userType, int id) {
		super();
		this.id = id;
		this.userType = userType;
	}	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public static UserType getUserType() {
		return userType;
	}
	public static void setUserType(UserType userType) {
		UserLogin.userType = userType;
	}
	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		UserLogin.id = id;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		UserLogin.password = password;
	}
}
