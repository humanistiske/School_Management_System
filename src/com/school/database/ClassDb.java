package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.school.gui.add_IF.*;
import com.school.gui.admin.edit_IF.EditClass_IF;

public class ClassDb 
{
	private static PreparedStatement classDb, insertClassDb, updateClassroomDb, 
										selectClassInfoDb, searchClassInfoDb,
										getClassInfoDb, getClassroomInfoDb, updateClassInfoDb;
	
	private static final String selectClass = "select classid from class";
	private static final String insertClass = "insert into class(classid, class_capacity, class_size, classroomid)"
			+ "values(?, ?, ?, ?)";
	private static final String getClassInfo = "select * from class where classid = ?";
	private static final String getClassroomInfo = "select occupancy from classroom where classroomid = ?";
	private static final String updateClassInfo = "update class set classid = ?, classroomid = ?, class_capacity = ? where classid = ? ";
	private static final String updateClassroom = "update classroom set occupancy = ?, occupied_till = ? "
			+ "where classroomid = ?";
	private static final String selectClassInfo = "select class.classid, class.classroomid, "
			+ "count(unique studentinfodb.userid) as students, "
			+ "count(unique classsubjectdist.subjectid) as subjects\r\n" + 
			"from class \r\n" + 
			"    left outer join studentinfodb on (class.classid = studentinfodb.classid)\r\n" + 
			"    left outer join classsubjectdist on (class.classid = classsubjectdist.classid)\r\n" + 
			"group by class.classid, class.classroomid";
	private static final String searchClassInfo = "select class.classid, class.classroomid, "
			+ "count(unique studentinfodb.userid) as students, "
			+ "count(unique classsubjectdist.subjectid) as subjects\r\n" + 
			"from class \r\n" + 
			"    left outer join studentinfodb on (class.classid = studentinfodb.classid)\r\n" + 
			"    left outer join classsubjectdist on (class.classid = classsubjectdist.classid)\r\n" +
			"where class.classid = ?\r\n" + 
			"group by class.classid, class.classroomid";
	
	public final List<String> getClassList()
	{
		List<String> classes = new ArrayList<>();
		
		try 
		{
			classDb = DatabaseConnect.con.prepareStatement(selectClass);
			ResultSet rs = classDb.executeQuery(); 
			
			while(rs.next())
			{
				classes.add(rs.getString(1));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return classes;
	}

	public final boolean addClass()
	{	
		boolean result = false;

		try
		{
			insertClassDb = DatabaseConnect.con.prepareStatement(insertClass);	
			insertClassDb.setString(1, AddClass_IF.classObj.getClassId());
			insertClassDb.setInt(2, AddClass_IF.classObj.getClassCapacity());
			insertClassDb.setInt(3, AddClass_IF.classObj.getClassSize());
			insertClassDb.setInt(4, AddClass_IF.classObj.getClassroomId());
			insertClassDb.executeQuery();
			insertClassDb.close();

			updateClassroomDb = DatabaseConnect.con.prepareStatement(updateClassroom);
			updateClassroomDb.setString(1, AddClass_IF.classObj.getOccupancy());
			updateClassroomDb.setDate(2, Date.valueOf(AddClass_IF.classObj.getOccupiedTill()));
			updateClassroomDb.setInt(3, AddClass_IF.classObj.getClassroomId());
			updateClassroomDb.executeQuery();
			updateClassroomDb.close();
			
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
	
	public final boolean updateClass()
	{	
		boolean result = false;

		try
		{
			updateClassInfoDb = DatabaseConnect.con.prepareStatement(updateClassInfo);	
			updateClassInfoDb.setString(1, EditClass_IF.classObj.getClassId());
			updateClassInfoDb.setInt(2, EditClass_IF.classObj.getClassroomId());
			updateClassInfoDb.setInt(3, EditClass_IF.classObj.getClassCapacity());
			updateClassInfoDb.setString(4, EditClass_IF.classObj.getClassId());
			updateClassInfoDb.executeQuery();
			updateClassInfoDb.close();

			updateClassroomDb = DatabaseConnect.con.prepareStatement(updateClassroom);
			updateClassroomDb.setString(1, EditClass_IF.classObj.getOccupancy());
			updateClassroomDb.setDate(2, Date.valueOf(EditClass_IF.classObj.getOccupiedTill()));
			updateClassroomDb.setInt(3, EditClass_IF.classObj.getClassroomId());
			updateClassroomDb.executeQuery();
			updateClassroomDb.setString(1, "UNOCCUPIED");
			updateClassroomDb.setNull(2, java.sql.Types.DATE);
			updateClassroomDb.setInt(3, EditClass_IF.getCurrentClassroom());
			updateClassroomDb.executeQuery();
			updateClassroomDb.close();
			
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
	
	public ResultSet selectClassInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectClassInfoDb = DatabaseConnect.con.prepareStatement(selectClassInfo);
			rs = selectClassInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return rs; 
	}
	
	public ResultSet searchClassInfoDb(String classId)
	{
		ResultSet rs = null;
		try
		{
			searchClassInfoDb = DatabaseConnect.con.prepareStatement(searchClassInfo);
			searchClassInfoDb.setString(1, classId);
			rs = searchClassInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Oops class not found");
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getClassInfo(String classId)
	{
		ResultSet rs = null;
		try
		{
			getClassInfoDb = DatabaseConnect.con.prepareStatement(getClassInfo);
			getClassInfoDb.setString(1, classId);
			rs = getClassInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getClassroomInfo(String classroomId)
	{
		ResultSet rs = null;
		try
		{
			getClassroomInfoDb = DatabaseConnect.con.prepareStatement(getClassroomInfo);
			getClassroomInfoDb.setString(1, classroomId);
			rs = getClassroomInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
}
