package com.school.elements;

import java.time.LocalDate;

public class Class 
{
	private String classId;
	private int classCapacity;
	private int classSize;
	private int classroomid;
	private String occupancy;
	private LocalDate occupiedTill;
	
	public Class(final String tmpClassId)
	{
		this.classId = tmpClassId;
	}
	
	public String getClassId() {
		return classId;
	}
	
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public int getClassCapacity() {
		return classCapacity;
	}
	
	public void setClassCapacity(int classCapacity) {
		this.classCapacity = classCapacity;
	}
	
	public int getClassSize() {
		return classSize;
	}
	
	public void setClassSize(int classSize) {
		this.classSize = classSize;
	}
	
	public void setClassroomId(final int tmpClassroomId)
	{
		this.classroomid = tmpClassroomId;
	}
	
	public int getClassroomId()
	{
		return this.classroomid;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	public LocalDate getOccupiedTill() {
		return occupiedTill;
	}

	public void setOccupiedTill(LocalDate occupiedTill) {
		this.occupiedTill = occupiedTill;
	}
}
