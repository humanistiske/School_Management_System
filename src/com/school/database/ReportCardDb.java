package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportCardDb 
{
	private static PreparedStatement selectReportCardInfoDb, searchReportCardInfoDb;
	private static final String selectReportCardInfo = "select examreportdb.userid, examreportdb.examid, examreportdb.student_marks, examreportdb.grade\r\n" + 
			"from examreportdb";
	private static final String searchReportCardInfo = "select examreportdb.userid, examreportdb.examid, examreportdb.student_marks, examreportdb.grade\r\n" + 
			"from examreportdb\r\n" + 
			"where examreportdb.userid = ?";
	
	public ResultSet selectReportCardInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectReportCardInfoDb = DatabaseConnect.con.prepareStatement(selectReportCardInfo);
			rs = selectReportCardInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return rs; 
	}

	public ResultSet searchReportCardInfoDb(int id)
	{
		ResultSet rs = null;
		try
		{
			searchReportCardInfoDb = DatabaseConnect.con.prepareStatement(searchReportCardInfo);
			searchReportCardInfoDb.setInt(1, id);
			rs = searchReportCardInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
}
