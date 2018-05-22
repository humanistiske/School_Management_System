package com.school;

public enum Attendance 
{
	ATTENDANCE("ATD");
	

	private String attendance;
  
	Attendance(String tmpAttendance)
	{
		attendance = tmpAttendance;
	}
	
	@Override
	public String toString()
	{
		return this.attendance;
	}
}
