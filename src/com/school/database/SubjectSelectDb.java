package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SubjectSelectDb 
{
	private static final Map<Integer, String> subjects = new HashMap<Integer, String>();
	private static PreparedStatement selectSubject, selectTeacherSubject;
	private static final String classSubjectDist = "select unique subject.subject_name, classsubjectdist.subjectid   \r\n" + 
			"from subject\r\n" + 
			"inner join classsubjectdist on subject.SUBJECTID = classsubjectdist.SUBJECTID\r\n" + 
			"inner join studentinfodb on studentinfodb.CLASSID = classsubjectdist.CLASSID\r\n" + 
			"and studentinfodb.classid = ?";
	private static final String selectClassSubject = "select unique subject.subject_name, classsubjectdist.subjectid \r\n" +  
			"from subject \r\n" +
			"inner join classsubjectdist on subject.SUBJECTID = classsubjectdist.SUBJECTID\r\n" + 
			"where classsubjectdist.classid = ?";
	private static final String teacherSubjectDist = "select unique subject.subject_name, "
					+ "teachersubjectdist.userid, classsubjectdist.classid, teachersubjectdist.subjectid\r\n" + 
					"from subject\r\n" + 
					"inner join teachersubjectdist on subject.SUBJECTID = teachersubjectdist.SUBJECTID\r\n" + 
					"inner join classsubjectdist on classsubjectdist.classid = teachersubjectdist.classid\r\n" + 
					"where classsubjectdist.classid = ?\r\n" + 
					"and teachersubjectdist.userid = ?";
	
	public final Map<Integer, String> getSubjectList(final String classId)
	{
		try 
		{
			subjects.clear();
			selectSubject = DatabaseConnect.con.prepareStatement(selectClassSubject);
			selectSubject.setString(1,  classId);
			ResultSet rs = selectSubject.executeQuery();
			
			while(rs.next())
			{
				subjects.put(rs.getInt("subjectid"), rs.getString("subject_name"));
			}
			
			selectSubject.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return subjects;
	}
	
	public final Map<Integer, String> selectTeacherSubject(String classid, int teacherid)
	{
		try
		{
			subjects.clear();
			selectTeacherSubject = DatabaseConnect.con.prepareStatement(teacherSubjectDist);
			selectTeacherSubject.setString(1, classid);
			selectTeacherSubject.setInt(2, teacherid);
			ResultSet rs = selectTeacherSubject.executeQuery();
			
			while(rs.next())
			{
				subjects.put(rs.getInt("subjectid"), rs.getString("subject_name"));
			}
			
			selectTeacherSubject.close();
			return subjects;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}
