package com.school.elements;

public class Subject 
{
	private int subjectId;
	private String subjectName;
	private String classId;
	
	public Subject(int subjectId) 
	{
		this.subjectId = subjectId;
	}

	public Subject(int subjectId, String subjectName) 
	{
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}
	
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId + 1;
	}
	
	public int getSubjectId() 
	{
		return subjectId;
	}
	
	@Override
	public String toString() 
	{
		return subjectName;
	}
}
