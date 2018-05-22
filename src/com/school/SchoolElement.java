package com.school;

public enum SchoolElement 
{
	CLASS("CL"),
	CLASSROOM("CR"),
	SUBJECT("SUB"),
	ATTENDANCE("ATD"),
	CLASSTIMETABLE("CTT"),
	EXAM("EXA"),
	REPORTCARD("RC");

	private String classEntity;
  
	SchoolElement(String tmpClassEntity)
	{
		classEntity = tmpClassEntity;
	}
	
	@Override
	public String toString()
	{
		return this.classEntity;
	}
}
