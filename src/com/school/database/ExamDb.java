package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.school.gui.LoginPanel;
import com.school.gui.add_IF.AddClass_IF;
import com.school.gui.add_IF.AddExam_IF;
import com.school.gui.admin.edit_IF.EditExam_IF;

public class ExamDb 
{
	private static PreparedStatement selectExamInfoDb, searchExamInfoDb, insertExamInfoDb, insertExamEnrollInfoDb,
									getExamInfoDb, updateExamInfoDb,
									selectExamStudentInfoDb, getExamListDb, getExamHallticketDb;
	
	private static final String insertExamInfo = "insert into exam("
			+ "examid, classid, subjectid, exam_location, date_, duration, marks, exam_status_id, time)"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String insertExamEnrollInfo = "insert all into examenrolldb(examid, exam_status_id, userid)\r\n" + 
			"    select exam.examid, exam.exam_status_id, studentsubjectdist.userid \r\n" + 
			"    from exam\r\n" + 
			"    left outer join studentsubjectdist on studentsubjectdist.subjectid = exam.subjectid and studentsubjectdist.subject_report = 1\r\n" + 
			"where exam.examid = ?";
	private static final String selectExamStudentInfo = "select userid from examenrolldb where examid = ?";
	private static final String getExamList = "select examid from exam where exam_status_id = 1";
	private static final String updateExamInfo = "update exam \r\n" + 
			"	set classid = ?, subjectid = ?, exam_location = ?, date_ = ?, duration = ?, "
			+ "marks = ?, exam_status_id = ?, time = ? "
			+ "where examid = ?";
	private static final String getExamInfo = "select exam.examid, exam.classid, exam.subjectid, \r\n" + 
			"        exam.exam_location, exam.date_, exam.duration, \r\n" + 
			"        exam.marks, exam.exam_status_id, exam.time, \r\n" + 
			"        subject.subject_name\r\n" + 
			"from exam \r\n" + 
			"left outer join subject on subject.subjectid = exam.subjectid " + 
			"where examid = ?";
	private static final String selectExamInfo = "select exam.examid, \r\n" + 
			"        concat(exam.classid, exam.subjectid) as classsubject, \r\n" + 
			"        subject.subject_name, \r\n" + 
			"        exam.exam_location, \r\n" + 
			"        concat(concat(exam.date_, ' '), exam.time) as datetime, \r\n" + 
			"        exam.duration, \r\n" + 
			"        exam.marks, \r\n" + 
			"        examstatus.exam_status_desc\r\n" + 
			"from exam\r\n" + 
			"left outer join subject on(subject.subjectid = exam.SUBJECTID)\r\n" + 
			"left outer join examstatus on(exam.exam_status_id = examstatus.exam_status_id)"
			+ "order by exam.examid";
	private static final String searchExamInfo = "select exam.examid, \r\n" + 
			"        concat(exam.classid, exam.subjectid) as classsubject, \r\n" + 
			"        subject.subject_name, \r\n" + 
			"        exam.exam_location, \r\n" + 
			"        concat(concat(exam.date_, ' '), exam.time) as datetime, \r\n" + 
			"        exam.duration, \r\n" + 
			"        exam.marks, \r\n" + 
			"        examstatus.exam_status_desc\r\n" + 
			"from exam\r\n" + 
			"left outer join subject on(subject.subjectid = exam.SUBJECTID)\r\n" + 
			"left outer join examstatus on(exam.exam_status_id = examstatus.exam_status_id)"
			+ "where examid = ?";
	private static final String getExamHallticket = "select subject.subject_name, "
			+ "exam.date_, exam.time, exam.exam_location, exam.marks \n" + 
			"from exam\n" + 
			"left outer join subject on exam.subjectid = subject.subjectid\n" + 
			"left outer join examenrolldb on examenrolldb.examid = exam.examid\n" + 
			"where examenrolldb.userid = " + LoginPanel.getUserLogin().getId() +   
			"and exam.exam_status_id = 1";
	
	public final boolean addExam()
	{	
		boolean result = false;

		try
		{
			insertExamInfoDb = DatabaseConnect.con.prepareStatement(insertExamInfo);	
			insertExamInfoDb.setInt(1, AddExam_IF.exam.getExamid());
			insertExamInfoDb.setString(2, AddExam_IF.exam.getClassId());
			insertExamInfoDb.setInt(3, AddExam_IF.exam.getSubject().getSubjectId());
			insertExamInfoDb.setInt(4, AddExam_IF.exam.getExamLocation());
			insertExamInfoDb.setDate(5, Date.valueOf(AddExam_IF.exam.getDate()));
			insertExamInfoDb.setDouble(6, AddExam_IF.exam.getDuration());
			insertExamInfoDb.setInt(7, AddExam_IF.exam.getMarks());
			insertExamInfoDb.setInt(8, AddExam_IF.exam.getExamStaus().getExamId());
			insertExamInfoDb.setString(9, String.valueOf(AddExam_IF.exam.getTime()));
			insertExamInfoDb.executeQuery();
			insertExamInfoDb.close();
			
			insertExamEnrollInfoDb = DatabaseConnect.con.prepareStatement(insertExamEnrollInfo);	
			insertExamEnrollInfoDb.setInt(1, AddExam_IF.exam.getExamid());
			insertExamEnrollInfoDb.executeQuery();
			insertExamEnrollInfoDb.close();
			
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			System.out.print(e);
			result = false;
		}	

		return result;
	}
	
	public final boolean updateExam()
	{	
		boolean result = false;

		try
		{
			updateExamInfoDb = DatabaseConnect.con.prepareStatement(updateExamInfo);	
			updateExamInfoDb.setString(1, EditExam_IF.exam.getClassId());
			updateExamInfoDb.setInt(2, EditExam_IF.exam.getSubject().getSubjectId());
			updateExamInfoDb.setInt(3, EditExam_IF.exam.getExamLocation());
			updateExamInfoDb.setDate(4, Date.valueOf(EditExam_IF.exam.getDate()));
			updateExamInfoDb.setDouble(5, EditExam_IF.exam.getDuration());
			updateExamInfoDb.setInt(6, EditExam_IF.exam.getMarks());
			updateExamInfoDb.setInt(7, EditExam_IF.exam.getExamStaus().getExamId());
			updateExamInfoDb.setString(8, String.valueOf(EditExam_IF.exam.getTime()));
			updateExamInfoDb.setInt(9, EditExam_IF.exam.getExamid());
			updateExamInfoDb.executeQuery();
			updateExamInfoDb.close();
			
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			System.out.print(e);
			result = false;
		}	

		return result;
	}
	
	public ResultSet selectExamInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectExamInfoDb = DatabaseConnect.con.prepareStatement(selectExamInfo);
			rs = selectExamInfoDb.executeQuery();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return rs; 
	}

	public List<Integer> getExamList()
	{
		ResultSet rs = null;
		List<Integer> examList = new ArrayList<>();
		
		try
		{
			getExamListDb = DatabaseConnect.con.prepareStatement(getExamList);
			rs = getExamListDb.executeQuery();
			
			while(rs.next())
			{
			  examList.add(rs.getInt("examid"));
			}			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return examList;
	}
	
	public List<Integer> getExamStudentList(int examid)
	{
		ResultSet rs = null;
		List<Integer> studentList = new ArrayList<>();
		
		try
		{
			selectExamStudentInfoDb = DatabaseConnect.con.prepareStatement(selectExamStudentInfo);
			selectExamStudentInfoDb.setInt(1, examid);
			rs = selectExamStudentInfoDb.executeQuery();
			
			while(rs.next())
			{
			  studentList.add(rs.getInt("userid"));
			}			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return studentList; 
	}
	
	public ResultSet searchExamInfoDb(int examId)
	{
		ResultSet rs = null;
		try
		{
			searchExamInfoDb = DatabaseConnect.con.prepareStatement(searchExamInfo);
			searchExamInfoDb.setInt(1, examId);
			rs = searchExamInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getExamHallticket()
	{
		ResultSet rs = null;
		try
		{
			getExamHallticketDb = DatabaseConnect.con.prepareStatement(getExamHallticket);
			rs = getExamHallticketDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getExamInfoDb(int examId)
	{
		ResultSet rs = null;
		try
		{
			getExamInfoDb = DatabaseConnect.con.prepareStatement(getExamInfo);
			getExamInfoDb.setInt(1, examId);
			rs = getExamInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
}
