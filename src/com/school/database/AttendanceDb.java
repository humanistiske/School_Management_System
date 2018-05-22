package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Map;

import com.school.gui.add_IF.AddAttendance_IF;

public class AttendanceDb 
{
	private static PreparedStatement selectAttendanceInfoDb, searchAttendanceInfoDb, 
									addAttendanceInfoDb, addNewAttendanceInfoDb, updateNewAttendanceInfoDb;
	private static final String selectAttendanceInfo = "select concat('S', classattendancedb.userid),\r\n" + 
			"        concat(concat(userdb.fname, ' '), userdb.lname) as fullname,\r\n" + 
			"        classattendancedb.classid,\r\n" + 
			"        classattendancedb.subjectid,\r\n" + 
			"        subject.subject_name,\r\n" + 
			"        classattendancedb.date_,\r\n" + 
			"        classattendancedb.attendance_status\r\n" + 
			"from classattendancedb\r\n" + 
			"    left outer join userdb on (userdb.userid = classattendancedb.userid)\r\n" + 
			"    left outer join subject on (classattendancedb.subjectid = subject.subjectid)";
	private static final String searchAttendanceInfo = "select concat('S', classattendancedb.userid),\r\n" + 
			"        concat(concat(userdb.fname, ' '), userdb.lname) as fullname,\r\n" + 
			"        classattendancedb.classid,\r\n" + 
			"        classattendancedb.subjectid,\r\n" + 
			"        subject.subject_name,\r\n" + 
			"        classattendancedb.date_,\r\n" + 
			"        classattendancedb.attendance_status\r\n" + 
			"from classattendancedb\r\n" + 
			"    left outer join userdb on (userdb.userid = classattendancedb.userid)\r\n" + 
			"    left outer join subject on (classattendancedb.subjectid = subject.subjectid)\r\n" + 
			"where classattendancedb.userid = ? or upper(userdb.fname) = upper(?) or upper(userdb.lname) = upper(?)";
	private static String addAttendanceInfo = "select concat('S', classattendancedb.userid), \r\n" + 
			"        concat(concat(userdb.fname, ' '), userdb.lname),\r\n" + 
			"        classattendancedb.date_, classattendancedb.attendance_status\r\n" + 
			"from classattendancedb \r\n" + 
			"left outer join userdb on userdb.userid = classattendancedb.userid\r\n" + 
			"where classattendancedb.subjectid = ? and classattendancedb.date_ = to_date('" + AddAttendance_IF.attendance.getDate() + "', 'yyyy-mm-dd')";
	private static final String addNewAttendanceInfo = "insert all \r\n" + 
			"into classattendancedb(userid, classid, subjectid, date_) \r\n" +
			"select studentsubjectdist.userid, studentsubjectdist.classid, "
			+ "studentsubjectdist.subjectid, "
			+ "to_date('" + AddAttendance_IF.attendance.getDate() + "', 'yyyy-mm-dd') \r\n" +
			"from studentsubjectdist \r\n" + 
			"where studentsubjectdist.classid = ? and studentsubjectdist.subjectid=?";
	private static final String updateNewAttendanceInfo = "update classattendancedb \r\n" + 
			"set ATTENDANCE_STATUS = ?\r\n" + 
			"where userid = ? and date_ = to_date('" + AddAttendance_IF.attendance.getDate() + "', 'yyyy-mm-dd')";

	
	public ResultSet selectAttendanceInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectAttendanceInfoDb = DatabaseConnect.con.prepareStatement(selectAttendanceInfo);
			rs = selectAttendanceInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return rs; 
	}

	public ResultSet searchAttendanceInfoDb(int userId, String fName, String lName)
	{
		ResultSet rs = null;
		try
		{
			searchAttendanceInfoDb = DatabaseConnect.con.prepareStatement(searchAttendanceInfo);
			searchAttendanceInfoDb.setInt(1, userId);
			searchAttendanceInfoDb.setString(2, fName);
			searchAttendanceInfoDb.setString(3, lName);
			rs = searchAttendanceInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return rs;
	}

	public ResultSet addAttendanceInfoDb(int subjectid, LocalDate date)
	{
		ResultSet rs = null;

		try
		{
			addAttendanceInfo = "select concat('S', classattendancedb.userid), \r\n" + 
					"        concat(concat(userdb.fname, ' '), userdb.lname),\r\n" + 
					"        classattendancedb.date_, classattendancedb.attendance_status\r\n" + 
					"from classattendancedb \r\n" + 
					"left outer join userdb on userdb.userid = classattendancedb.userid\r\n" + 
					"where classattendancedb.subjectid = ? and classattendancedb.date_ = to_date('" + AddAttendance_IF.attendance.getDate() + "', 'yyyy-mm-dd')";
			
			addAttendanceInfoDb = DatabaseConnect.con.prepareStatement(addAttendanceInfo,
						    			ResultSet.TYPE_SCROLL_SENSITIVE, 
						    			ResultSet.CONCUR_READ_ONLY,
						    			ResultSet.HOLD_CURSORS_OVER_COMMIT);
			addAttendanceInfoDb.setInt(1, subjectid);
			
			rs = addAttendanceInfoDb.executeQuery();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public boolean addNewAttendanceInfoDb()
	{
		boolean result = false;
		
		try
		{
			addNewAttendanceInfoDb = DatabaseConnect.con.prepareStatement(addNewAttendanceInfo);
			addNewAttendanceInfoDb.setString(1, AddAttendance_IF.attendance.getClassId());
			addNewAttendanceInfoDb.setInt(2, AddAttendance_IF.attendance.getSubjectid());
			
			addNewAttendanceInfoDb.executeQuery();
			addNewAttendanceInfoDb.close();
			
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateNewAttendanceInfo()
	{
		boolean result = false;

		try
		{
			updateNewAttendanceInfoDb = DatabaseConnect.con.prepareStatement(updateNewAttendanceInfo);
			for(Map.Entry<Integer, Boolean> student : AddAttendance_IF.getAttendanceStatusList().entrySet())
			{
				if(student.getValue() == true)
				{
					updateNewAttendanceInfoDb.setString(1, "PRESENT");
					System.out.println("present");
				}
				else
				{
					updateNewAttendanceInfoDb.setString(1, "ABSENT");	
				}
				updateNewAttendanceInfoDb.setInt(2, student.getKey());
				
				updateNewAttendanceInfoDb.addBatch();
			}
			updateNewAttendanceInfoDb.executeBatch();
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}
