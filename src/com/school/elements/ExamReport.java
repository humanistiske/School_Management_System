package com.school.elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExamReport 
{
	private int marks;
	private String grade;
	private File answer_sheet;
	private FileInputStream answer_SheetInputStream;
	
	public ExamReport(int marks, String grade, File answer_sheet) {
		super();
		this.marks = marks;
		this.grade = grade;
		this.answer_sheet = answer_sheet;
		setAnswerSheetFileInputStream(this.answer_sheet);
	}
	
	public FileInputStream getAnswerSheetFileInputStream() 
	{
		return answer_SheetInputStream;
	}

	public void setAnswerSheetFileInputStream(File answer_sheet) 
	{
		try 
		{
			this.answer_SheetInputStream = new FileInputStream(answer_sheet);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e);
		}
	}
	
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public File getAnswer_sheet() {
		return answer_sheet;
	}
	public void setAnswer_sheet(File answer_sheet) {
		this.answer_sheet = answer_sheet;
	}
}
