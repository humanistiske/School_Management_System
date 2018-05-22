package com.school.elements;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ClassTimetable 
{
	private String classId;
	private int subjectId;
	private LocalTime startTime;
	private LocalTime endTime;
	private Map<Integer, String> subjectList;
	private List<LocalTime> listStartTime;
	private List<LocalTime> listEndTime;
	
	public ClassTimetable(int subjectid)
	{
		this.subjectId = subjectid;
	}
	
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectid) {
		this.subjectId = subjectid;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classid) {
		this.classId = classid;
	}
	public Map<Integer, String> getSubjectList() 
	{
		return subjectList;
	}

	public List<LocalTime> getListStartTime() {
		return listStartTime;
	}

	public void setListStartTime(List<LocalTime> listStartTime) {
		this.listStartTime = listStartTime;
	}

	public List<LocalTime> getListEndTime() {
		return listEndTime;
	}

	public void setListEndTime(List<LocalTime> listEndTime) {
		this.listEndTime = listEndTime;
	}

	public void setSubjectList(Map<Integer, String> subjectList) 
	{
		this.subjectList = subjectList;
	}
}
