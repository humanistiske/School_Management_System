package com.school.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DbUtils 
{
	public static TableModel viewStudentTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Student ID");
			columnNames.add("Full Name");
			columnNames.add("Class ID");
			columnNames.add("Total Subjects");
			
			//variable to store all rows in vector
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewTeacherTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Teacher ID");
			columnNames.add("Full Name");
			columnNames.add("Class Count");
			columnNames.add("Subject Count");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames); 
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewClassTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Class ID");
			columnNames.add("Classroom");
			columnNames.add("Total Students");
			columnNames.add("Total Subjects");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewSubjectTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Subject ID");
			columnNames.add("Subject Name");
			columnNames.add("Class ID");
			columnNames.add("Teacher Name");
			columnNames.add("Total Students");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));	
				}
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewClassroomTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Classroom ID");
			columnNames.add("Classroom Capacity");
			columnNames.add("Class ID");
			columnNames.add("Occupancy");
			columnNames.add("Occupied Till");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewClassTimetableTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Class ID");
			columnNames.add("Subject Name");
			columnNames.add("Start Time");
			columnNames.add("End Time");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewAttendanceTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("User ID");
			columnNames.add("Full Name");
			columnNames.add("Class ID");
			columnNames.add("Subject ID");
			columnNames.add("Subject Name");
			columnNames.add("Date");
			columnNames.add("Attendance Status");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel addAttendanceTableModel(ResultSet rs)
	{
		DefaultTableModel addAttendance = new DefaultTableModel()
		{
			public Class<?> getColumnClass(int column)
			{
				switch(column)
				{
					case 0:
						return String.class;
					case 1:
						return String.class;
					case 2:
						return Boolean.class;
					default:
						return String.class;
				}
			}
		};
		
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Student ID");
			columnNames.add("Student Name");
			columnNames.add("Present");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			rs.beforeFirst();
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					if(rs.getObject(i).equals("PRESENT"))
					{
						newRow.addElement(true);
					}
					else if(rs.getObject(i).equals("ABSENT"))
					{
						newRow.addElement(false);
					}
					else
					{
						newRow.addElement(rs.getObject(i));
					}
				}	
				rows.addElement(newRow);
			}
			
			addAttendance.setDataVector(rows, columnNames);
			return addAttendance;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static TableModel viewExamTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("Exam ID");
			columnNames.add("ClassSubject ID");
			columnNames.add("Subject Name");
			columnNames.add("Classroom");
			columnNames.add("Duration");
			columnNames.add("Date & Time");
			columnNames.add("Total Marks");
			columnNames.add("Status");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewReportCardTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("User ID");
			columnNames.add("Exam ID");
			columnNames.add("Student Marks");
			columnNames.add("Grade");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewStaffAccountTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("In Hand Salary");
			columnNames.add("Allowance");
			columnNames.add("Total Salary");
			columnNames.add("Last Payment Date");
			columnNames.add("Salary Status");
			columnNames.add("Contract End Date");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static TableModel viewStudentAccountTableModel(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("User ID");
			columnNames.add("Class ID");
			columnNames.add("Paid Fees");
			columnNames.add("Outstanding Fees");
			columnNames.add("Total Fees");
			columnNames.add("Last Payment Date");
			columnNames.add("Due Date");
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			while(rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();
				for(int i=1; i<=columns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}	
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows, columnNames);
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}
