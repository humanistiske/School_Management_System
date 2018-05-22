package com.school.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnect 
{
	protected static Connection con;
	protected static Statement stmt;
	protected static ResultSet rs;
	
	public static final void connectDatabase()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "school_webspace", "school_webspace");
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static final void closeConnection()
	{
		try 
		{
			con.close();
			stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static final int getId(String table)
	{
		int count = 0;
		try
		{
			rs = stmt.executeQuery("select count(*) from " + table);
			while(rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return count;
	}
	
	public static final int getClassroomId(String table)
	{
		int count = 0;
		try
		{
			rs = stmt.executeQuery("select count(*) from " + table);
			while(rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return count;
	}
	
	public static final List<Integer> getClassroomList(String table)
	{
		List<Integer> classroomList = new ArrayList<>();
		try
		{
			rs = stmt.executeQuery("select classroomid from " + table + " where upper(occupancy) = upper('unoccupied')");
			while(rs.next())
			{
				classroomList.add(rs.getInt("classroomid"));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return classroomList;
	}
	
	public static final List<String> getClassList(String table)
	{
		List<String> classList = new ArrayList<>();
		try
		{
			rs = stmt.executeQuery("select classid from " + table);
			while(rs.next())
			{
				classList.add(rs.getString("classid"));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return classList;
	}
}
