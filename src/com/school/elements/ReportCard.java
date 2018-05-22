package com.school.elements;

import java.io.File;

public class ReportCard 
{
	private int userid;
	private int examid;
	private int exam_marks;
	private char grade;
	private File answerSheet;
	
	public ReportCard(int userid, int examid)
	{
		this.userid = userid;
		this.examid = examid;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getExamid() {
		return examid;
	}
	public void setExamid(int examid) {
		this.examid = examid;
	}
	public int getExam_marks() {
		return exam_marks;
	}
	public void setExam_marks(int exam_marks) {
		this.exam_marks = exam_marks;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}
	public File getAnswerSheet() {
		return answerSheet;
	}
	public void setAnswerSheet(File answerSheet) {
		this.answerSheet = answerSheet;
	}	
}
