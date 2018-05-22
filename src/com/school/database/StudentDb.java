package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

import com.school.SubjectReport;
import com.school.UserType;
import com.school.gui.accountant.edit_IF.EditStudent_IF;
import com.school.gui.add_IF.AddStudent_IF;
import com.school.gui.add_IF.AddUserInfoPanel;


public class StudentDb 
{
	private static PreparedStatement insertUserDb, insertLoginDb, 
										insertStudentInfoDb, insertStudentAccountsDb, insertStudentSubjectDist,
										selectStudentInfoDb, searchStudentInfoDb,
										getStudentInfoDb, getStudentSubjectInfoDb, getStudentAccountInfoDb, 
										updateLoginInfoDb, updateUserInfoDb, updateStudentInfoDb, 
										updateStudentSubjectDistDb, updateStudentAccountInfoDb;
	
	private static final String loginDb = "insert into logindb(userid, usertypeid, password)"
			+ "values(?, ?, ?)";
	
	private static final String userDb = "insert into userdb(userid, fname, lname, address, phone, alt_phone, email_id, photo, id_proof)"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String studentInfoDb = "insert into studentinfodb(userid, classid, total_subjects)"
			+ "values(?, ?, ?)";
	
	private static final String studentSubjectDist = "insert into studentsubjectdist("
			+ "userid, classid, subjectid, subject_report)"
			+ "values(?, ?, ?, ?)";
	
	private static final String updateLoginInfo = "update logindb set password = ? where userid = ?";
	
	private static final String updateUserInfo = "update userdb set "
			+ "fname = ?, lname = ?, address = ?, phone = ?, alt_phone = ?, email_id = ?, "
			+ "photo = ?, id_proof = ? where userid = ?";
	
	private static final String updateStudentInfo = "update studentinfodb set classid = ?, total_subjects = ?"
			+ "where userid = ?";
	
	private static final String updateStudentSubjectDist = "update studentsubjectdist set "
			+ "classid = ?, subjectid = ?, subject_report = ? ) "
			+ "where userid = ?";
	
	private static final String updateStudentAccountInfo = "update studentaccountsdb set "
			+ "classid = ?, total_fees = ?,  paid_fees = ?, outstanding_fees = ?, last_payment_date = ?, due_date = ? "
			+ "where userid = ?";
	
	private static final String studentAccountsDb = "insert into studentaccountsdb("
			+ "userid, classid, total_fees, paid_fees, outstanding_fees, last_payment_date, due_date)"
			+ "values(?, ?, ?, ?, ?, ?, ?)"; 

	private static final String getStudentInfo = "select logindb.userid, logindb.usertypeid, logindb.password, " 
			+ "userdb.fname, userdb.lname, userdb.address, userdb.phone, "
			+ "userdb.alt_phone, userdb.email_id, userdb.photo, userdb.id_proof, "
			+ "studentinfodb.classid, studentinfodb.total_subjects "
			+ "from logindb "
			+ "left outer join userdb on logindb.userid = userdb.userid "
			+ "left outer join studentinfodb on studentinfodb.userid = logindb.userid "
			+ "where userdb.userid = ?";
	
	private static final String getStudentAccountInfo = "select * from studentaccountsdb where userid = ?";
	
	private static final String getStudentSubjectInfo = "select studentsubjectdist.subjectid, subject.subject_name\r\n" + 
			"from studentsubjectdist \r\n" + 
			"left outer join subject on subject.subjectid = studentsubjectdist.subjectid\r\n" + 
			"where studentsubjectdist.userid = ?";
	
	private static final String selectStudentInfo = "select concat('S', userdb.userid), concat(concat(userdb.fname, ' '), userdb.lname), "
			+ "studentinfodb.classid, studentinfodb.total_subjects \r\n" + 
			"from userdb \r\n" + 
			"inner join studentinfodb on userdb.userid = studentinfodb.userid\r\n" + 
			"order by userdb.userid";
	
	private static final String searchStudentInfo = "select userdb.userid, concat(concat(userdb.fname, ' '), userdb.lname), studentinfodb.classid, studentinfodb.total_subjects \r\n" + 
			"from userdb \r\n" + 
			"inner join studentinfodb on userdb.userid = studentinfodb.userid\r\n" + 
			"where userdb.userid = ? or upper(userdb.fname) = upper(?) or upper(userdb.lname) = upper(?)\r\n" + 
			"order by userdb.userid\r\n";
	
	public final boolean addStudent()
	{	
		boolean result = false;

		try
		{
			insertUserDb = DatabaseConnect.con.prepareStatement(userDb);	
			insertUserDb.setInt(1, AddUserInfoPanel.getId());
			insertUserDb.setString(2, AddStudent_IF.student.getfName());
			insertUserDb.setString(3, AddStudent_IF.student.getlName());
			insertUserDb.setString(4, AddStudent_IF.student.getAddress());
			insertUserDb.setLong(5, AddStudent_IF.student.getPhone());
			insertUserDb.setLong(6, AddStudent_IF.student.getAltPhone());
			insertUserDb.setString(7, AddStudent_IF.student.getEmailid());
			insertUserDb.setBinaryStream(8, AddStudent_IF.student.getPhotoFileInputStream());
			insertUserDb.setBinaryStream(9, AddStudent_IF.student.getIdFileInputStream());
			insertUserDb.executeQuery();
			insertUserDb.close();
			
			insertLoginDb = DatabaseConnect.con.prepareStatement(loginDb);
			insertLoginDb.setInt(1, AddUserInfoPanel.getId());
			insertLoginDb.setString(2, UserType.STUDENT.toString());
			insertLoginDb.setString(3, AddStudent_IF.student.getPassword());
			insertLoginDb.executeQuery();
			insertLoginDb.close();
			
			insertStudentInfoDb = DatabaseConnect.con.prepareStatement(studentInfoDb);
			insertStudentInfoDb.setInt(1, AddUserInfoPanel.getId());
			insertStudentInfoDb.setString(2, AddStudent_IF.student.getClassId());
			insertStudentInfoDb.setInt(3, AddStudent_IF.student.getTotal_subjects());
			insertStudentInfoDb.executeQuery();
			insertStudentInfoDb.close();
			
			insertStudentAccountsDb = DatabaseConnect.con.prepareStatement(studentAccountsDb);
			insertStudentAccountsDb.setInt(1, AddUserInfoPanel.getId());
			insertStudentAccountsDb.setString(2, AddStudent_IF.student.getClassId());
			insertStudentAccountsDb.setDouble(3, AddStudent_IF.student.getTotalFees());
			insertStudentAccountsDb.setDouble(4, AddStudent_IF.student.getPaidFees());
			insertStudentAccountsDb.setDouble(5, AddStudent_IF.student.getOutstandingFees());
			insertStudentAccountsDb.setDate(6, Date.valueOf(AddStudent_IF.student.getLastPaymentDate()));
			insertStudentAccountsDb.setDate(7, Date.valueOf(AddStudent_IF.student.getDueDate()));
			insertStudentAccountsDb.executeQuery();
			insertStudentAccountsDb.close();
		
			insertStudentSubjectDist = DatabaseConnect.con.prepareStatement(studentSubjectDist);
			if(AddStudent_IF.student.getTotal_subjects() > 0)
			{
				for(final Integer subject : AddStudent_IF.student.getSubjectList().keySet())
				{
					insertStudentSubjectDist.setInt(1, AddUserInfoPanel.getId());
					insertStudentSubjectDist.setString(2, AddStudent_IF.student.getClassId());
					insertStudentSubjectDist.setInt(3, subject);
					insertStudentSubjectDist.setInt(4, SubjectReport.PURSUING.getSubjectStatus());
					
					insertStudentSubjectDist.addBatch();
				}
				insertStudentSubjectDist.executeBatch();	
			}
			
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = false;
		}	
		
		return result;
	}
	
	public final boolean updateStudent()
	{	
		boolean result = false;

		try
		{
			updateLoginInfoDb = DatabaseConnect.con.prepareStatement(updateLoginInfo);	
			updateLoginInfoDb.setString(1, EditStudent_IF.student.getPassword());
			updateLoginInfoDb.setInt(2, EditStudent_IF.student.getId());
			updateLoginInfoDb.executeQuery();
			updateLoginInfoDb.close();
			
			updateUserInfoDb = DatabaseConnect.con.prepareStatement(updateUserInfo);
			updateUserInfoDb.setString(1, EditStudent_IF.student.getfName());
			updateUserInfoDb.setString(2, EditStudent_IF.student.getlName());
			updateUserInfoDb.setString(3, EditStudent_IF.student.getAddress());
			updateUserInfoDb.setLong(4, EditStudent_IF.student.getPhone());
			updateUserInfoDb.setLong(5, EditStudent_IF.student.getAltPhone());
			updateUserInfoDb.setString(6, EditStudent_IF.student.getEmailid());
			updateUserInfoDb.setBinaryStream(7, EditStudent_IF.student.getPhotoFileInputStream());
			updateUserInfoDb.setBinaryStream(8, EditStudent_IF.student.getIdFileInputStream());
			updateUserInfoDb.setInt(9, EditStudent_IF.student.getId());
			updateUserInfoDb.executeQuery();
			updateUserInfoDb.close();
			
			updateStudentInfoDb = DatabaseConnect.con.prepareStatement(updateStudentInfo);
			updateStudentInfoDb.setString(1, EditStudent_IF.student.getClassId());
			updateStudentInfoDb.setInt(2, EditStudent_IF.student.getTotal_subjects());
			updateStudentInfoDb.setInt(3, EditStudent_IF.student.getId());
			updateStudentInfoDb.executeQuery();
			updateStudentInfoDb.close();
			
			updateStudentSubjectDistDb = DatabaseConnect.con.prepareStatement(updateStudentSubjectDist);
			if(AddStudent_IF.student.getTotal_subjects() > 0)
			{
				for(final Integer subject : AddStudent_IF.student.getSubjectList().keySet())
				{
					updateStudentSubjectDistDb.setString(1, EditStudent_IF.student.getClassId());
					updateStudentSubjectDistDb.setInt(2, subject);
					updateStudentSubjectDistDb.setInt(3, SubjectReport.PURSUING.getSubjectStatus());
					updateStudentSubjectDistDb.setInt(4, EditStudent_IF.student.getId());
					
					updateStudentSubjectDistDb.addBatch();
				}
				updateStudentSubjectDistDb.executeBatch();	
			}
			
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = false;
		}	
		
		return result;
	}
	
	public final boolean updateStudentAccount()
	{	
		boolean result = false;

		try
		{
			updateStudentAccountInfoDb.setString(1, AddStudent_IF.student.getClassId());
			updateStudentAccountInfoDb.setDouble(2, AddStudent_IF.student.getTotalFees());
			updateStudentAccountInfoDb.setDouble(3, AddStudent_IF.student.getPaidFees());
			updateStudentAccountInfoDb.setDouble(4, AddStudent_IF.student.getOutstandingFees());
			updateStudentAccountInfoDb.setDate(5, Date.valueOf(AddStudent_IF.student.getLastPaymentDate()));
			updateStudentAccountInfoDb.setDate(6, Date.valueOf(AddStudent_IF.student.getDueDate()));
			updateStudentAccountInfoDb.setInt(7, AddUserInfoPanel.getId());
			insertStudentAccountsDb.executeQuery();
			insertStudentAccountsDb.close();
			
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = false;
		}	
		
		return result;
	}	
	
	public final boolean updateStudentAccountDb()
	{	
		boolean result = false;

		try
		{
			updateStudentAccountInfoDb = DatabaseConnect.con.prepareStatement(updateStudentAccountInfo);	
			updateStudentAccountInfoDb.setString(1, EditStudent_IF.student.getClassId());
			updateStudentAccountInfoDb.setDouble(2, EditStudent_IF.student.getTotalFees());
			updateStudentAccountInfoDb.setDouble(3, EditStudent_IF.student.getPaidFees());
			updateStudentAccountInfoDb.setDouble(4, EditStudent_IF.student.getOutstandingFees());
			updateStudentAccountInfoDb.setDate(5, Date.valueOf(EditStudent_IF.student.getLastPaymentDate()));
			updateStudentAccountInfoDb.setDate(6, Date.valueOf(EditStudent_IF.student.getDueDate()));
			updateStudentAccountInfoDb.setInt(7, EditStudent_IF.student.getId());
			updateStudentAccountInfoDb.executeQuery();
			updateStudentAccountInfoDb.close();
			
			DatabaseConnect.con.commit();
			
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = false;
		}	
		
		return result;
	}	
	
	public boolean addStudentSubjectsQuery(String query1)
	{
		try
		{
			DatabaseConnect.stmt.addBatch(query1);
			DatabaseConnect.stmt.executeBatch();
			DatabaseConnect.con.commit();
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}
	
	public ResultSet selectStudentInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectStudentInfoDb = DatabaseConnect.con.prepareStatement(selectStudentInfo);
			rs = selectStudentInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet searchStudentInfoDb(int id, String fName, String lName)
	{
		ResultSet rs = null;
		try
		{
			searchStudentInfoDb = DatabaseConnect.con.prepareStatement(searchStudentInfo);
			searchStudentInfoDb.setInt(1, id);
			searchStudentInfoDb.setString(2, fName);
			searchStudentInfoDb.setString(3, lName);	
			rs = searchStudentInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Oops student not found");
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getStudentInfo(int userid)
	{
		ResultSet rs = null;
		try
		{
			getStudentInfoDb = DatabaseConnect.con.prepareStatement(getStudentInfo);
			getStudentInfoDb.setInt(1, userid);
			rs = getStudentInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getStudentSubjectInfo(int userid)
	{
		ResultSet rs = null;
		try
		{
			getStudentSubjectInfoDb = DatabaseConnect.con.prepareStatement(getStudentSubjectInfo);
			getStudentSubjectInfoDb.setInt(1, userid);
			rs = getStudentSubjectInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getStudentAccountInfo(int userid)
	{
		ResultSet rs = null;
		try
		{
			getStudentAccountInfoDb = DatabaseConnect.con.prepareStatement(getStudentAccountInfo);
			getStudentAccountInfoDb.setInt(1, userid);
			rs = getStudentAccountInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
}

