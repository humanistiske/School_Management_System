package com.school;

public enum SchoolEntities 
{
	CLASS("CL"),
	CLASSROOM("CR"),
	SUBJECT("SUB");

	private String classEntity;
  
	SchoolEntities(String tmpClassEntity)
	{
		classEntity = tmpClassEntity;
	}
	
	@Override
	public String toString()
	{
		return this.classEntity;
	}
}
