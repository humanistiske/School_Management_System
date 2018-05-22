package com.school.elements;

import java.time.LocalDate;

public class Attendance 
{
	private int userid;
	private String classId; 
	private int subjectid;
	private LocalDate date;
	private AttendanceStatus attendanceStatus;
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	
	public enum AttendanceStatus
	{
		PRESENT("PRESENT"),
		ABSENT("ABSENT");
	
		AttendanceStatus(String status)
		{
			this.status = status;
		}
		
		private String status;
		
		@Override
		public String toString()
		{
			return this.status;
		}
	}
}


