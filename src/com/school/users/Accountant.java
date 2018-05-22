package com.school.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

import com.school.UserType;

public class Accountant extends User 
{	
	private String designation;
	private String highest_qualification;
	private File qualification_document;
	private double inHandSalary, allowance, totalSalary;
	private LocalDate lastSalaryPaymentDate, dateOfJoining, contractEndDate;
	private int salaryStatusId;
	private FileInputStream qualification_documentInputStream;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getHighest_qualification() {
		return highest_qualification;
	}

	public void setHighest_qualification(String highest_qualification) {
		this.highest_qualification = highest_qualification;
	}

	public File getQualification_document() {
		return qualification_document;
	}

	public void setQualification_document(File qualification_document) {
		this.qualification_document = qualification_document;
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

	public Accountant(final int userid) 
	{
		super(userid);
	}
	
	public static UserType getUserType() 
	{
		return UserType.ACCOUNTANT;
	}
		
	public FileInputStream getQualification_documentInputStream() 
	{
		return qualification_documentInputStream;
	}

	public void setQualification_documentInputStream(File file) 
	{
		try 
		{
			this.qualification_documentInputStream = new FileInputStream(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

}
