package com.school;

public enum SubjectReport 
{
	PURSUING(1),
	PASSED(2),
	FAILED(3),
	EXEMPTED(4);
	
	private int subjectStatus;
	
	SubjectReport(final int subjectStatus)
	{
		this.subjectStatus = subjectStatus;
	}
	
	public int getSubjectStatus() 
	{
		return subjectStatus;
	}

}
