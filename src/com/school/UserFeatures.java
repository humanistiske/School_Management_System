package com.school;

public enum UserFeatures 
{
	Calendar("CAL"),
	Chat("CH"),
	Email("EM");
  
	private String userFeature;
  
	UserFeatures(String tmpUserFeature)
	{
		userFeature = tmpUserFeature;
	}
	
	@Override
	public String toString()
	{
		return this.userFeature;
	}
	
}
