package com.school.elements;

import java.time.LocalDate;

public class Classroom 
{
	private int classroomid;
	private int capacity;
	private String occupancy;
	private LocalDate occupiedTill;
	
	public Classroom(final int tmpClassroomid)
	{
		classroomid = tmpClassroomid;
	}
	
	public int getClassroomid() {
		return classroomid;
	}

	public void setClassroomid(int classroomid) {
		this.classroomid = classroomid + 1;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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
