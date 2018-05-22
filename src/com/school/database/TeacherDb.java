package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import java.sql.Date;

import com.school.SubjectReport;
import com.school.UserType;
import com.school.gui.accountant.edit_IF.EditStaff_IF;
import com.school.gui.add_IF.AddStudent_IF;
import com.school.gui.add_IF.AddTeacher_IF;
import com.school.gui.add_IF.AddUserInfoPanel;
import com.school.gui.admin.edit_IF.EditTeacher_IF;
import com.school.users.Teacher;

public class TeacherDb 
{
	private static PreparedStatement insertUserDb, insertLoginDb, 
									insertTeacherInfoDb, insertTeacherAccountsDb, insertTeacherSubjectDist,
									selectTeacherInfoDb, searchTeacherInfoDb,
									getTeacherInfoDb, getTeacherSubjectInfoDb, getTeacherAccountInfoDb, 
									updateLoginInfoDb, updateUserInfoDb, updateTeacherInfoDb, 
									updateTeacherSubjectDistDb;

	private static final String loginDb = "insert into logindb(userid, usertypeid, password)"
			+ "values(?, ?, ?)";

	private static final String userDb = "insert into userdb(userid, fname, lname, address, phone, alt_phone, email_id, photo, id_proof)"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String staffInfoDb = "insert into staffinfodb(userid, usertypeid, designation, highest_qualification, qualification_documents)"
			+ "values(?, ?, ?, ?, ?)";

	private static final String staffAccountsDb = "insert into staffaccountsdb("
			+ "userid, in_hand_salary, allowance, total_salary, salarystatusid, date_of_joining, contract_end_date)"
			+ "values(?, ?, ?, ?, ?, ?, ? )"; 

	private static final String teacherSubjectDist = "insert into teachersubjectdist("
			+ "userid, classid, subjectid)"
			+ "values(?, ?, ?)";
	
	private static final String updateLoginInfo = "update logindb set password = ? where userid = ?";
	
	private static final String updateUserInfo = "update userdb set "
			+ "fname = ?, lname = ?, address = ?, phone = ?, alt_phone = ?, email_id = ?, "
			+ "photo = ?, id_proof = ? where userid = ?";
	
	private static final String updateStaffInfo = "update staffinfodb set designation = ?, highest_qualification = ?"
			+ "where userid = ?";
	
	private static final String updateTeacherSubjectDist = "update studentsubjectdist set "
			+ "subjectid = ?, classid = ? "
			+ "where userid = ?";

	private static final String getTeacherInfo = "select logindb.userid, logindb.usertypeid, logindb.password, \r\n" + 
			"        userdb.fname, userdb.lname, userdb.address, userdb.phone,\r\n" + 
			"        userdb.alt_phone, userdb.email_id, userdb.photo, userdb.id_proof, \r\n" + 
			"        staffinfodb.designation, staffinfodb.highest_qualification, staffinfodb.qualification_documents\r\n" + 
			"from logindb \r\n" + 
			"left outer join userdb on logindb.userid = userdb.userid \r\n" + 
			"left outer join staffinfodb on staffinfodb.userid = logindb.userid \r\n" + 
			"where userdb.userid = ?";
	
	private static final String getTeacherSubjectInfo = "select teachersubjectdist.subjectid, teachersubjectdist.classid, subject.subject_name\r\n" + 
			"from teachersubjectdist \r\n" + 
			"left outer join subject on subject.subjectid = teachersubjectdist.subjectid\r\n" + 
			"where teachersubjectdist.userid = ?";
	
	private static final String getTeacherAccountInfo = "select in_hand_salary, allowance, total_salary, last_salary_payment_date, \r\n" + 
			"        date_of_joining, contract_end_date, salarystatusid\r\n" +   
			"from staffaccountsdb \r\n" + 
			"where userid = ?";
	
	private static final String selectTeacherInfo = "select concat('T', teachersubjectdist.userid),\r\n" + 
			"        concat(concat(userdb.fname, ' '), userdb.lname), \r\n" + 
			"        count(unique teachersubjectdist.subjectid), \r\n" + 
			"        count(teachersubjectdist.classid)\r\n" + 
			"from teachersubjectdist, userdb\r\n" + 
			"where teachersubjectdist.USERID = userdb.userid \r\n" + 
			"group by teachersubjectdist.userid, concat(concat(userdb.fname, ' '), userdb.lname)\r\n";
	
	private static final String searchTeacherInfo = "select teachersubjectdist.userid,\r\n" + 
			"        concat(concat(userdb.fname, ' '), userdb.lname), \r\n" + 
			"        count(unique teachersubjectdist.subjectid), \r\n" + 
			"        count(teachersubjectdist.classid)\r\n" + 
			"from teachersubjectdist\r\n" + 
			"inner join userdb\r\n" + 
			"on userdb.userid = teachersubjectdist.userid \r\n" + 
			"where userdb.userid = ? or upper(userdb.fname) = upper(?) or upper(userdb.lname) = upper(?)\r\n" + 
			"group by teachersubjectdist.userid, concat(concat(userdb.fname, ' '), userdb.lname)\r\n" + 
			"\r\n";

	public final boolean addTeacher()
	{	
		boolean result = false;

		try
		{
			insertUserDb = DatabaseConnect.con.prepareStatement(userDb);	
			insertUserDb.setInt(1, AddUserInfoPanel.getId());
			insertUserDb.setString(2, AddTeacher_IF.teacher.getfName());
			insertUserDb.setString(3, AddTeacher_IF.teacher.getlName());
			insertUserDb.setString(4, AddTeacher_IF.teacher.getAddress());
			insertUserDb.setLong(5, AddTeacher_IF.teacher.getPhone());
			insertUserDb.setLong(6, AddTeacher_IF.teacher.getAltPhone());
			insertUserDb.setString(7, AddTeacher_IF.teacher.getEmailid());
			insertUserDb.setBinaryStream(8, AddTeacher_IF.teacher.getPhotoFileInputStream());
			insertUserDb.setBinaryStream(9, AddTeacher_IF.teacher.getIdFileInputStream());
			insertUserDb.executeQuery();
			insertUserDb.close();

			insertLoginDb = DatabaseConnect.con.prepareStatement(loginDb);
			insertLoginDb.setInt(1, AddUserInfoPanel.getId());
			insertLoginDb.setString(2, UserType.TEACHER.toString());
			insertLoginDb.setString(3, AddTeacher_IF.teacher.getPassword());
			insertLoginDb.executeQuery();
			insertLoginDb.close();

			insertTeacherInfoDb = DatabaseConnect.con.prepareStatement(staffInfoDb);
			insertTeacherInfoDb.setInt(1, AddUserInfoPanel.getId());
			insertTeacherInfoDb.setString(2, Teacher.getUserType().toString());
			insertTeacherInfoDb.setString(3, AddTeacher_IF.teacher.getDesignation());
			insertTeacherInfoDb.setString(4, AddTeacher_IF.teacher.getHighest_qualification());
			insertTeacherInfoDb.setBinaryStream(5, AddTeacher_IF.teacher.getQualification_documentInputStream());
			insertTeacherInfoDb.executeQuery();
			insertTeacherInfoDb.close();

			insertTeacherAccountsDb = DatabaseConnect.con.prepareStatement(staffAccountsDb);
			insertTeacherAccountsDb.setInt(1, AddUserInfoPanel.getId());
			insertTeacherAccountsDb.setDouble(2, AddTeacher_IF.teacher.getInHandSalary());
			insertTeacherAccountsDb.setDouble(3, AddTeacher_IF.teacher.getAllowance());
			insertTeacherAccountsDb.setDouble(4, AddTeacher_IF.teacher.getTotalSalary());
			insertTeacherAccountsDb.setInt(5, 1);
			insertTeacherAccountsDb.setDate(6, Date.valueOf(AddTeacher_IF.teacher.getDateOfJoining()));
			insertTeacherAccountsDb.setDate(7, Date.valueOf(AddTeacher_IF.teacher.getContractEndDate()));
			insertTeacherAccountsDb.executeQuery();
			insertTeacherAccountsDb.close();

			insertTeacherSubjectDist = DatabaseConnect.con.prepareStatement(teacherSubjectDist);
			for(final Integer subject : AddTeacher_IF.teacher.getClassSubjectList().keySet())
			{
				insertTeacherSubjectDist.setInt(1, AddUserInfoPanel.getId());
				insertTeacherSubjectDist.setString(2, AddTeacher_IF.teacher.getClassSubjectList().get(subject));
				insertTeacherSubjectDist.setInt(3, subject);
				
				insertTeacherSubjectDist.addBatch();
			}
			insertTeacherSubjectDist.executeBatch();

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

	
	public final boolean updateTeacher()
	{	
		boolean result = false;

		try
		{
			updateLoginInfoDb = DatabaseConnect.con.prepareStatement(updateLoginInfo);	
			updateLoginInfoDb.setString(1, EditTeacher_IF.teacher.getPassword());
			updateLoginInfoDb.setInt(2, EditTeacher_IF.teacher.getId());
			updateLoginInfoDb.executeQuery();
			updateLoginInfoDb.close();
			
			updateUserInfoDb = DatabaseConnect.con.prepareStatement(updateUserInfo);
			updateUserInfoDb.setString(1, EditTeacher_IF.teacher.getfName());
			updateUserInfoDb.setString(2, EditTeacher_IF.teacher.getlName());
			updateUserInfoDb.setString(3, EditTeacher_IF.teacher.getAddress());
			updateUserInfoDb.setLong(4, EditTeacher_IF.teacher.getPhone());
			updateUserInfoDb.setLong(5, EditTeacher_IF.teacher.getAltPhone());
			updateUserInfoDb.setString(6, EditTeacher_IF.teacher.getEmailid());
			updateUserInfoDb.setBlob(7, EditTeacher_IF.teacher.getPhotoFileInputStream());
			updateUserInfoDb.setBlob(8, EditTeacher_IF.teacher.getIdFileInputStream());
			updateUserInfoDb.setInt(9, EditTeacher_IF.teacher.getId());
			updateUserInfoDb.executeQuery();
			updateUserInfoDb.close();
			
			updateTeacherInfoDb = DatabaseConnect.con.prepareStatement(updateStaffInfo);
			updateTeacherInfoDb.setString(1, EditTeacher_IF.teacher.getDesignation());
			updateTeacherInfoDb.setString(2, EditTeacher_IF.teacher.getHighest_qualification());
			updateTeacherInfoDb.setInt(3, EditTeacher_IF.teacher.getId());
			/*updateTeacherInfoDb.setBinaryStream(3, EditTeacher_IF.teacher.getId());*/
			updateTeacherInfoDb.executeQuery();
			updateTeacherInfoDb.close();
			
			updateTeacherSubjectDistDb = DatabaseConnect.con.prepareStatement(updateTeacherSubjectDist);
			if(EditTeacher_IF.teacher.getClassSubjectList().size() > 0)
			{
				for(final Integer subject : EditTeacher_IF.teacher.getSubjectList().keySet())
				{
					updateTeacherSubjectDistDb.setInt(1, subject);
					updateTeacherSubjectDistDb.setString(2, EditTeacher_IF.teacher.getClassSubjectList().get(subject));
					updateTeacherSubjectDistDb.setInt(3, EditTeacher_IF.teacher.getId());
					
					updateTeacherSubjectDistDb.addBatch();
				}
				updateTeacherSubjectDistDb.executeBatch();	
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
	
	public boolean addTeacherSubjectsQuery(String query1)
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
	
	public ResultSet selectTeacherInfoDb()
	{
		ResultSet rs = null;
		try
		{
			selectTeacherInfoDb = DatabaseConnect.con.prepareStatement(selectTeacherInfo);
			rs = selectTeacherInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return rs; 
	}
	
	public ResultSet searchTeacherInfoDb(int id, String fName, String lName)
	{
		ResultSet rs = null;
		try
		{
			searchTeacherInfoDb = DatabaseConnect.con.prepareStatement(searchTeacherInfo);
			searchTeacherInfoDb.setInt(1, id);
			searchTeacherInfoDb.setString(2, fName);
			searchTeacherInfoDb.setString(3, lName);	
			rs = searchTeacherInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Oops teacher not found");
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet getTeacherSubjectInfo(int userid)
	{
		ResultSet rs = null;
		try
		{
			getTeacherSubjectInfoDb = DatabaseConnect.con.prepareStatement(getTeacherSubjectInfo);
			getTeacherSubjectInfoDb.setInt(1, userid);
			rs = getTeacherSubjectInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getTeacherInfo(int userid)
	{
		ResultSet rs = null;
		try
		{
			getTeacherInfoDb = DatabaseConnect.con.prepareStatement(getTeacherInfo);
			getTeacherInfoDb.setInt(1, userid);
			rs = getTeacherInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getTeacherAccountInfo(int userid)
	{
		ResultSet rs = null;
		try
		{
			getTeacherAccountInfoDb = DatabaseConnect.con.prepareStatement(getTeacherAccountInfo);
			getTeacherAccountInfoDb.setInt(1, userid);
			rs = getTeacherAccountInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
}

