package com.school.accounts.student;

import java.time.LocalDate;

public class StudentAccount 
{
	private int userid;
	private String classid;
	private double paidFees;
	private double outstandingFees;
	private double totalFees;
	private LocalDate lastPaymentDate;
	private LocalDate dueDate;
	
	public StudentAccount(int userid)
	{
		this.userid = userid;
	}
	
	public void setUserId(int userid)
	{
		this.userid = userid;
	}
	public int getUserId()
	{
		return this.userid;
	}
}
