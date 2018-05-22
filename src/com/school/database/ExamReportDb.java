package com.school.database;

import java.sql.PreparedStatement;
import java.util.Map;

import com.school.elements.ExamReport;
import com.school.gui.add_IF.AddExamReport_IF;

public class ExamReportDb 
{
	private static PreparedStatement insertExamReportInfoDb;
	
	private static final String insertExamReportInfo = "insert into examreportdb("
			+ "userid, examid, student_marks, grade, answer_sheet)"
			+ "values(?, ?, ?, ?, ?)";
	
	public final boolean addExamReport()
	{	
		boolean result = false;

		try
		{
			insertExamReportInfoDb = DatabaseConnect.con.prepareStatement(insertExamReportInfo);
			for(Map.Entry<Integer, ExamReport> studentReport : AddExamReport_IF.selectedStudentReportList.entrySet())
			{
				insertExamReportInfoDb.setInt(1, studentReport.getKey());
				insertExamReportInfoDb.setInt(2, AddExamReport_IF.getExamid());
				insertExamReportInfoDb.setInt(3, studentReport.getValue().getMarks());
				insertExamReportInfoDb.setString(4, studentReport.getValue().getGrade());
				insertExamReportInfoDb.setBinaryStream(5, studentReport.getValue().getAnswerSheetFileInputStream());
				insertExamReportInfoDb.addBatch();
			}
			insertExamReportInfoDb.executeBatch();
			insertExamReportInfoDb.close();
			
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
