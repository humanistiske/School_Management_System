package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.school.gui.add_IF.AddSubject_IF;
import com.school.gui.admin.edit_IF.EditSubject_IF;

public class SubjectDb 
{
	private static PreparedStatement insertClassSubjectDist, insertSubjectDb,
									selectSubjectInfoDb, searchSubjectInfoDb,
									getSubjectInfoDb, updateSubjectInfoDb, updateClassSubjectDistDb;
	
	private static final String subject = "insert into subject(subjectid, subject_name)"
			+ "values(?, ?)";
	private static final String classSubjectDist = "insert into classsubjectdist(classid, subjectid)"
			+ "values(?, ?)";
	private static final String updateSubjectInfo = "update subject set subject_name = ? where subjectid = ?";
	private static final String updateClassSubjectDist = "update classsubjectdist set classid = ? where subjectid = ?";
	private static final String getSubjectInfo = "select subject.subjectid, subject.subject_name, classsubjectdist.classid\r\n" + 
			"from subject\r\n" + 
			"left outer join classsubjectdist on classsubjectdist.subjectid = subject.subjectid\r\n" + 
			"where subject.subjectid = ?";
	private static final String selectSubjectInfo = "select subject.subjectid, subject.subject_name, teachersubjectdist.classid,\r\n" + 
			"			concat(concat(userdb.fname, ' '), userdb.lname) as teachername,\r\n" + 
			"			count(studentsubjectdist.userid) as students\r\n" + 
			"			from subject\r\n" + 
			"			left outer join teachersubjectdist on teachersubjectdist.SUBJECTID = subject.SUBJECTID\r\n" + 
			"			left outer join userdb on userdb.userid = teachersubjectdist.userid\r\n" + 
			"			left outer join studentsubjectdist on subject.subjectid = studentsubjectdist.SUBJECTID \r\n" + 
			"group by subject.subjectid, subject.subject_name, teachersubjectdist.classid, concat(concat(userdb.fname, ' '), userdb.lname)";
	private static final String searchSubjectInfo = "select subject.subjectid, subject.subject_name, teachersubjectdist.classid,\r\n" + 
			"			concat(concat(userdb.fname, ' '), userdb.lname) as teachername,\r\n" + 
			"			count(studentsubjectdist.userid) as students\r\n" + 
			"			from subject\r\n" + 
			"			left outer join teachersubjectdist on teachersubjectdist.SUBJECTID = subject.SUBJECTID\r\n" + 
			"			left outer join userdb on userdb.userid = teachersubjectdist.userid\r\n" + 
			"			left outer join studentsubjectdist on subject.subjectid = studentsubjectdist.SUBJECTID \r\n" +
			"where subjectid = ? or upper(subject_name) = upper(?)\r\n" + 
			"group by subject.subjectid, subject.subject_name, teachersubjectdist.classid, concat(concat(userdb.fname, ' '), userdb.lname)";
	
	public final boolean addSubject()
	{	
		boolean result = false;

		try
		{
			insertSubjectDb = DatabaseConnect.con.prepareStatement(subject);	
			insertSubjectDb.setInt(1, AddSubject_IF.subject.getSubjectId());
			insertSubjectDb.setString(2, AddSubject_IF.subject.getSubjectName());
			insertSubjectDb.executeQuery();
			insertSubjectDb.close();

			insertClassSubjectDist = DatabaseConnect.con.prepareStatement(classSubjectDist);
			insertClassSubjectDist.setString(1, AddSubject_IF.subject.getClassId());
			insertClassSubjectDist.setInt(2, AddSubject_IF.subject.getSubjectId());
			insertClassSubjectDist.executeQuery();
			insertClassSubjectDist.close();
			
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
	
	public final boolean updateSubject()
	{	
		boolean result = false;

		try
		{
			updateSubjectInfoDb = DatabaseConnect.con.prepareStatement(updateSubjectInfo);	
			updateSubjectInfoDb.setString(1, EditSubject_IF.subject.getSubjectName());
			updateSubjectInfoDb.setInt(2, EditSubject_IF.subject.getSubjectId());
			updateSubjectInfoDb.executeQuery();
			updateSubjectInfoDb.close();

			updateClassSubjectDistDb = DatabaseConnect.con.prepareStatement(updateClassSubjectDist);
			updateClassSubjectDistDb.setString(1, EditSubject_IF.subject.getClassId());
			updateClassSubjectDistDb.setInt(2, EditSubject_IF.subject.getSubjectId());
			updateClassSubjectDistDb.executeQuery();
			updateClassSubjectDistDb.close();
			
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
	
	public ResultSet selectSubjectInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectSubjectInfoDb = DatabaseConnect.con.prepareStatement(selectSubjectInfo);
			rs = selectSubjectInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return rs; 
	}
	
	public ResultSet searchSubjectInfoDb(int subjectId, String subjectName)
	{
		ResultSet rs = null;
		try
		{
			searchSubjectInfoDb = DatabaseConnect.con.prepareStatement(searchSubjectInfo);
			searchSubjectInfoDb.setInt(1, subjectId);
			searchSubjectInfoDb.setString(2, subjectName);
			rs = searchSubjectInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Oops subject not found");
			System.out.println(e);
		}
		return rs;
	}
	public ResultSet getSubjectInfoDb(int subjectid)
	{
		ResultSet rs = null;
		try
		{
			getSubjectInfoDb = DatabaseConnect.con.prepareStatement(getSubjectInfo);
			getSubjectInfoDb.setInt(1, subjectid);
			rs = getSubjectInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return rs; 
	}
}

