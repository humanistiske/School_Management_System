package com.school;

public enum SchoolElements 
{
	CLASS("CL"),
	CLASSROOM("CR"),
	SUBJECT("SUB");

	private String classEntity;
  
	SchoolElements(String tmpClassEntity)
	{
		classEntity = tmpClassEntity;
	}
	
	@Override
	public String toString()
	{
		return this.classEntity;
	}
}
