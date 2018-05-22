package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentAccountDb 
{
	private static PreparedStatement selectStudentAccountInfoDb, searchStudentAccountInfoDb;
	private static final String selectStudentAccountInfo = "select userid, classid, paid_fees, " 
        + "outstanding_fees, total_fees, " 
        + "last_payment_date, due_date "
        + "from studentaccountsdb";
	private static final String searchStudentAccountInfo = "select classid, paid_fees, \r\n" + 
			"outstanding_fees, total_fees, \r\n" + 
			"last_payment_date, due_date \r\n" + 
			"from studentaccountsdb\r\n" + 
			"where userid = ?"; 
	
	public ResultSet selectStudentAccountInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectStudentAccountInfoDb = DatabaseConnect.con.prepareStatement(selectStudentAccountInfo);
			rs = selectStudentAccountInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet searchStudentAccountInfoDb(int id)
	{
		ResultSet rs = null;
		try
		{
			searchStudentAccountInfoDb = DatabaseConnect.con.prepareStatement(searchStudentAccountInfo);
			searchStudentAccountInfoDb.setInt(1, id);
			rs = searchStudentAccountInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
}