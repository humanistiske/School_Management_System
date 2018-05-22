package com.school;

public enum UserType 
{
	STUDENT("S"),
	TEACHER("T"),
	ADMIN("AD"),
	ACCOUNTANT("AC");
  
	private String userType;
  
	UserType(String tmpUserType)
	{
		userType = tmpUserType;
	}
	
	@Override
	public String toString()
	{
		return this.userType;
	}
	
	public static String[] getUsers() 
	{
	    UserType[] userType = values();
	    String[] names = new String[userType.length];

	    for (int i = 0; i < userType.length; i++) {
	        names[i] = userType[i].name();
	    }

	    return names;
	}
}
