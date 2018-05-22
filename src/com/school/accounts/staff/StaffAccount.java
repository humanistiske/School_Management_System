package com.school.accounts.staff;

import java.time.LocalDate;

import com.school.*;
import com.school.users.User;

public class StaffAccount extends User 
{	
	private double inHandSalary, allowance, totalSalary;
	private LocalDate lastSalaryPaymentDate, dateOfJoining, contractEndDate;
	private int salaryStatusId;

	public StaffAccount(final int userid) 
	{
		super(userid);
	}

	public double getInHandSalary() {
		return inHandSalary;
	}

	public void setInHandSalary(double inHandSalary) {
		this.inHandSalary = inHandSalary;
	}

	public double getAllowance() {
		return allowance;
	}

	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public LocalDate getLastSalaryPaymentDate() {
		return lastSalaryPaymentDate;
	}

	public void setLastSalaryPaymentDate(LocalDate lastSalaryPaymentDate) {
		this.lastSalaryPaymentDate = lastSalaryPaymentDate;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public LocalDate getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(LocalDate contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public int getSalaryStatusId() {
		return salaryStatusId;
	}

	public void setSalaryStatusId(int salaryStatusId) {
		this.salaryStatusId = salaryStatusId;
	}

	public static UserType getUserType() 
	{
		return UserType.TEACHER;
	}
}
