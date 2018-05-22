package com.school;

public enum UserFeature 
{
/*	Calendar("CAL"),
	Chat("CH"),*/
	EMAIL("EM");
  
	private String userFeature;
  
	UserFeature(String tmpUserFeature)
	{
		userFeature = tmpUserFeature;
	}
	
	@Override
	public String toString()
	{
		return this.userFeature;
	}
	
}
