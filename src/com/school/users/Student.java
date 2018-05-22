package com.school.users;

import java.util.Map;
import java.time.*;
import com.school.*;

public class Student extends User 
{	
	public Student(int userid) 
	{
		super(userid);
	}
	
  	public Student(final String userid, final int id, final UserType userType)
  	{
  		super(id);
  	}

	private int total_subjects;
	private String classId;
	private Map<Integer, String> subjectList;
	private double totalFees, paidFees, outstandingFees;

	public double getTotalFees() 
	{
		return totalFees;
	}

	public void setTotalFees(double totalFees) 
	{
		this.totalFees = totalFees;
	}

	private LocalDate lastPaymentDate, dueDate; 
 
	public int getTotal_subjects() 
	{
		return total_subjects;
	}

	public void setTotal_subjects(int total_subjects) 
	{
		this.total_subjects = total_subjects;
	}

	public String getClassId() 
	{
		return classId;
	}

	public void setClassId(String classId) 
	{
		this.classId = classId;
	}

	public Map<Integer, String> getSubjectList() 
	{
		return subjectList;
	}

	public void setSubjectList(Map<Integer, String> subjectList) 
	{
		this.subjectList = subjectList;
	}

	public double getPaidFees() 
	{
		return paidFees;
	}

	public void setPaidFees(double paidFees) 
	{
		this.paidFees = paidFees;
	}

	public double getOutstandingFees() 
	{
		return outstandingFees;
	}

	public void setOutstandingFees(double outstandingFees) 
	{
		this.outstandingFees = outstandingFees;
	}

	public LocalDate getLastPaymentDate() 
	{
		return lastPaymentDate;
	}

	public void setLastPaymentDate(LocalDate lastPaymentDate) 
	{
		this.lastPaymentDate = lastPaymentDate;
	}

	public LocalDate getDueDate() 
	{
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) 
	{
		this.dueDate = dueDate;
	}

	public static UserType getUserType() 
	{
		return UserType.STUDENT;
	}
}
