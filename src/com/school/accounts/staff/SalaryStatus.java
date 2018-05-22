package com.school.accounts.staff;

public enum SalaryStatus
{
	UNPAID(1),
	PAID(2);
	
	private int salaryStatus;
	
	SalaryStatus(int salaryStatus)
	{
		this.salaryStatus = salaryStatus;
	}
	
	public int getSalaryStatus()
	{
		return this.salaryStatus;
	}
}	

