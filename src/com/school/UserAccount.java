package com.school;

public enum UserAccount
{
	ACCOUNTS("ACC");
  
	private String userAccount;
  
	UserAccount(String tmpUserAccount)
	{
		userAccount = tmpUserAccount;
	}
	
	@Override
	public String toString()
	{
		return this.userAccount;
	}
}