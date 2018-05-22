package com.school.elements;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam 
{
	private int examid;
	private Subject subject;
	private String classSubjectId;
	private String classId;
	private String subjectName;
	private int examLocation;
	private LocalDate date;
	private String time;
	private double duration;
	private int marks;
	private ExamStatus examStaus;
	
	public Exam(int examid)
	{
		super();
		this.examid = examid;
	}
	
	public int getExamid() {
		return examid;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public void setExamid(int examid) {
		this.examid = examid;
	}

	public String getClassSubjectId() {
		return classSubjectId;
	}

	public void setClassSubjectId(String classSubjectId) {
		this.classSubjectId = classSubjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getExamLocation() {
		return examLocation;
	}

	public void setExamLocation(int examLocation) {
		this.examLocation = examLocation;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public ExamStatus getExamStaus() {
		return examStaus;
	}

	public void setExamStaus(ExamStatus examStaus) {
		this.examStaus = examStaus;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public enum ExamStatus
	{
		ACTIVE(1),
		INACTIVE(2),
		PASSED(3),
		AWATINGRESULT(4),
		FAILED(5);
		
		private int examid;
		
		ExamStatus(int examid)
		{
			this.examid = examid;
		}
		
		public static ExamStatus forInt(int id) {
			for (ExamStatus examStatus : values()) {
				if (examStatus.examid == id) {
					return examStatus;
				}
			}
			throw new IllegalArgumentException("Invalid exam id: " + id);
		}
		
		public int getExamId()
		{
			return this.examid;
		}
	}
}
