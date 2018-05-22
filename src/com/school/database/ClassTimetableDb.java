package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.school.gui.add_IF.AddClassTimetable_IF;

public class ClassTimetableDb 
{
	private static PreparedStatement selectClassTimetableInfoDb, searchClassTimetableInfoDb,
								insertClassTimetableInfoDb, updateClassTimetableInfoDb,
								getClassTimetableInfoDb;
	private static final String selectClassTimetableInfo = "select classsubjectdist.classid, subject.subject_name, classtimetable.start_time, classtimetable.end_time \r\n" + 
			"from classtimetable\r\n" + 
			"left outer join subject on (subject.subjectid = classtimetable.subjectid)\r\n" + 
			"left outer join classsubjectdist on (classsubjectdist.subjectid = classtimetable.subjectid)\r\n" + 
			"order by classsubjectdist.CLASSID\r\n";
	private static final String searchClassTimetableInfo = "select classsubjectdist.classid, subject.subject_name, classtimetable.start_time, classtimetable.end_time \r\n" + 
			"from classtimetable\r\n" + 
			"left outer join subject on (subject.subjectid = classtimetable.subjectid)\r\n" + 
			"left outer join classsubjectdist on (classsubjectdist.subjectid = classtimetable.subjectid)\r\n" + 
			"where classtimetable.subjectid = ? or classsubjectdist.CLASSID = ?\r\n" + 
			"order by classsubjectdist.CLASSID";
	private static final String getClassTimetableInfo = "select classtimetable.start_time, classtimetable.end_time,\r\n" + 
			"    subject.subject_name\r\n" + 
			"from classtimetable \r\n" + 
			"left outer join subject on subject.subjectid = classtimetable.subjectid\r\n" + 
			"left outer join classsubjectdist on classsubjectdist.subjectid = subject.subjectid\r\n" + 
			"where classsubjectdist.classid  = ?";
	private static final String insertClassTimetableInfo = "insert into classtimetable"
			+ "(subjectid, start_time, end_time) values(?, ?, ?)";
	private static final String updateClassTimetableInfo = "update classtimetable set "
			+ "start_time  = ?, end_time = ? where subjectid = ?";
	
	public ResultSet selectClassTimetableInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectClassTimetableInfoDb = DatabaseConnect.con.prepareStatement(selectClassTimetableInfo);
			rs = selectClassTimetableInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return rs; 
	}

	public ResultSet searchClassTimetableInfoDb(int subjectid, String classid)
	{
		ResultSet rs = null;
		try
		{
			searchClassTimetableInfoDb = DatabaseConnect.con.prepareStatement(searchClassTimetableInfo);
			searchClassTimetableInfoDb.setInt(1, subjectid);
			searchClassTimetableInfoDb.setString(2, classid);
			rs = searchClassTimetableInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getClassTimetableInfoDb(String classid)
	{
		ResultSet rs = null;
		try
		{
			getClassTimetableInfoDb = DatabaseConnect.con.prepareStatement(getClassTimetableInfo);
			getClassTimetableInfoDb.setString(1, classid);
			rs = getClassTimetableInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public boolean addClassTimetableInfoDb()
	{
		boolean result = false;
		
		try
		{
			insertClassTimetableInfoDb = DatabaseConnect.con.prepareStatement(insertClassTimetableInfo);
			int counter = 0;
			for(Integer subject : AddClassTimetable_IF.classTimetable.getSubjectList().keySet())
			{
				insertClassTimetableInfoDb.setInt(1, subject);
				insertClassTimetableInfoDb.setString(2, 
						AddClassTimetable_IF.classTimetable.getListStartTime().get(counter).toString());
				insertClassTimetableInfoDb.setString(3, 
						AddClassTimetable_IF.classTimetable.getListEndTime().get(counter).toString());
				insertClassTimetableInfoDb.addBatch();
				
				counter++;
			}
			insertClassTimetableInfoDb.executeBatch();
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
	
	public boolean updateClassTimetableInfoDb()
	{
		boolean result = false;
		
		try
		{
			updateClassTimetableInfoDb = DatabaseConnect.con.prepareStatement(updateClassTimetableInfo);
			int counter = 0;
			for(Integer subject : AddClassTimetable_IF.classTimetable.getSubjectList().keySet())
			{
				updateClassTimetableInfoDb.setString(1, 
						AddClassTimetable_IF.classTimetable.getListStartTime().get(counter).toString());
				updateClassTimetableInfoDb.setString(2, 
						AddClassTimetable_IF.classTimetable.getListEndTime().get(counter).toString());
				updateClassTimetableInfoDb.setInt(3, subject);
				updateClassTimetableInfoDb.addBatch();
				
				counter++;
			}
			updateClassTimetableInfoDb.executeBatch();
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
}
