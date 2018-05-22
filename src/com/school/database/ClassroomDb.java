package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.school.gui.add_IF.AddClassroom_IF;

public class ClassroomDb 
{
	public static final List<Integer> listClassroom = new ArrayList<>();
	
	private static PreparedStatement insertClassroomDb, selectClassroomInfoDb, searchClassroomInfoDb,
							selectUnoccupiedClassroomDb, getClassroomInfoDb;

	private static final String classroomDb = "insert into classroom(classroomid, capacity, occupancy)"
			+ "values(?, ?, ?)";
	private static final String getClassroomInfo = "select * from classroom where classroomid = ?";
	private static final String selectClassroomInfo = "select classroom.classroomid, classroom.capacity, class.classid, classroom.occupancy, classroom.occupied_till\r\n" + 
			"from classroom\r\n" + 
			"left outer join class on class.classroomid = classroom.classroomid\r\n" +  
			"order by classroom.classroomid"; 
	private static final String searchClassroomInfo = "select classroom.classroomid, classroom.capacity, class.classid, classroom.occupancy, classroom.occupied_till\r\n" + 
			"from classroom\r\n" + 
			"left outer join class on class.classroomid = classroom.classroomid\r\n" + 
			"where classroom.classroomid = ?\r\n" + 
			"order by classroom.classroomid"; 
	private static final String selectUnoccupiedClassroom = "select * \r\n" + 
			"from classroom \r\n" + 
			"where (upper(occupancy) = upper('unoccupied')) \r\n" + 
			"    or (upper(occupancy) = upper('occupied') and OCCUPIED_TILL < to_date(?, 'yyyy-mm-dd'))";
	private static final String selectUnoccupiedClassroom1 = "select * \r\n" + 
			"from classroom \r\n" + 
			"where upper(occupancy) = upper('unoccupied') \r\n";
	
	public final boolean addClassroom()
	{	
		boolean result = false;

		try
		{
			insertClassroomDb = DatabaseConnect.con.prepareStatement(classroomDb);	
			insertClassroomDb.setInt(1, AddClassroom_IF.classroom.getClassroomid());
			insertClassroomDb.setInt(2, AddClassroom_IF.classroom.getCapacity());
			insertClassroomDb.setString(3, AddClassroom_IF.classroom.getOccupancy());
			insertClassroomDb.executeQuery();
			insertClassroomDb.close();

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
	
	public ResultSet selectClassroomInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectClassroomInfoDb = DatabaseConnect.con.prepareStatement(selectClassroomInfo);
			rs = selectClassroomInfoDb.executeQuery();
			searchClassroomInfoDb.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet searchClassroomInfoDb(int classroomId)
	{
		ResultSet rs = null;
		try
		{
			searchClassroomInfoDb = DatabaseConnect.con.prepareStatement(searchClassroomInfo);
			searchClassroomInfoDb.setInt(1, classroomId);
			rs = searchClassroomInfoDb.executeQuery();
			searchClassroomInfoDb.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getClassroomInfoDb(int classroomId)
	{
		ResultSet rs = null;
		try
		{
			getClassroomInfoDb = DatabaseConnect.con.prepareStatement(getClassroomInfo);
			getClassroomInfoDb.setInt(1, classroomId);
			rs = getClassroomInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public static List<Integer> getUnoccupiedClassroomList(LocalDate date)
	{
		List<Integer> listClassroom = new ArrayList<>();
		
		try 
		{
			selectUnoccupiedClassroomDb = DatabaseConnect.con.prepareStatement(selectUnoccupiedClassroom,
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
	    			ResultSet.CONCUR_UPDATABLE,
	    			ResultSet.HOLD_CURSORS_OVER_COMMIT);
			selectUnoccupiedClassroomDb.setDate(1, Date.valueOf(date));
			ResultSet rs = selectUnoccupiedClassroomDb.executeQuery();
			
			rs.beforeFirst();
			while(rs.next())
			{
				listClassroom.add(rs.getInt("classroomid"));
			}
			selectUnoccupiedClassroomDb.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return listClassroom;
	}
	
	public static List<Integer> getUnoccupiedClassroomList()
	{
		List<Integer> listClassroom = new ArrayList<>();
		
		try 
		{
			selectUnoccupiedClassroomDb = DatabaseConnect.con.prepareStatement(selectUnoccupiedClassroom1);
			ResultSet rs = selectUnoccupiedClassroomDb.executeQuery();
			
			while(rs.next())
			{
				listClassroom.add(rs.getInt("classroomid"));
			}
			selectUnoccupiedClassroomDb.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return listClassroom;
	}
}
