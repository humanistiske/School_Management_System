package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;

import com.school.UserType;
import com.school.gui.add_IF.AddAdmin_IF;
import com.school.gui.add_IF.AddUserInfoPanel;
import com.school.users.Admin;

public class AdminDb 
{
	private static PreparedStatement insertUserDb, insertLoginDb, 
	insertAdminInfoDb, insertAdminAccountsDb;

	private static final String loginDb = "insert into logindb(userid, usertypeid, password)"
			+ "values(?, ?, ?)";

	private static final String userDb = "insert into userdb(userid, fname, lname, address, phone, alt_phone, email_id, photo, id_proof)"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String staffInfoDb = "insert into staffinfodb(userid, usertypeid, designation, highest_qualification, qualification_documents)"
			+ "values(?, ?, ?, ?, ?)";

	private static final String staffAccountsDb = "insert into staffaccountsdb("
			+ "userid, in_hand_salary, allowance, total_salary, salarystatusid, date_of_joining, contract_end_date)"
			+ "values(?, ?, ?, ?, ?, ?, ? )"; 

	public final boolean addAdmin()
	{	
		boolean result = false;

		try
		{
			insertUserDb = DatabaseConnect.con.prepareStatement(userDb);	
			insertUserDb.setInt(1, AddUserInfoPanel.getId());
			insertUserDb.setString(2, AddAdmin_IF.admin.getfName());
			insertUserDb.setString(3, AddAdmin_IF.admin.getlName());
			insertUserDb.setString(4, AddAdmin_IF.admin.getAddress());
			insertUserDb.setLong(5, AddAdmin_IF.admin.getPhone());
			insertUserDb.setLong(6, AddAdmin_IF.admin.getAltPhone());
			insertUserDb.setString(7, AddAdmin_IF.admin.getEmailid());
			insertUserDb.setBinaryStream(8, AddAdmin_IF.admin.getPhotoFileInputStream());
			insertUserDb.setBinaryStream(9, AddAdmin_IF.admin.getIdFileInputStream());
			insertUserDb.executeQuery();
			insertUserDb.close();

			insertLoginDb = DatabaseConnect.con.prepareStatement(loginDb);
			insertLoginDb.setInt(1, AddUserInfoPanel.getId());
			insertLoginDb.setString(2, UserType.ADMIN.toString());
			insertLoginDb.setString(3, AddAdmin_IF.admin.getPassword());
			insertLoginDb.executeQuery();
			insertLoginDb.close();

			insertAdminInfoDb = DatabaseConnect.con.prepareStatement(staffInfoDb);
			insertAdminInfoDb.setInt(1, AddUserInfoPanel.getId());
			insertAdminInfoDb.setString(2, Admin.getUserType().toString());
			insertAdminInfoDb.setString(3, AddAdmin_IF.admin.getDesignation());
			insertAdminInfoDb.setString(4, AddAdmin_IF.admin.getHighest_qualification());
			insertAdminInfoDb.setBinaryStream(5, AddAdmin_IF.admin.getQualification_documentInputStream());
			insertAdminInfoDb.executeQuery();
			insertAdminInfoDb.close();

			insertAdminAccountsDb = DatabaseConnect.con.prepareStatement(staffAccountsDb);
			insertAdminAccountsDb.setInt(1, AddUserInfoPanel.getId());
			insertAdminAccountsDb.setDouble(2, AddAdmin_IF.admin.getInHandSalary());
			insertAdminAccountsDb.setDouble(3, AddAdmin_IF.admin.getAllowance());
			insertAdminAccountsDb.setDouble(4, AddAdmin_IF.admin.getTotalSalary());
			insertAdminAccountsDb.setInt(5, 1);
			insertAdminAccountsDb.setDate(6, Date.valueOf(AddAdmin_IF.admin.getDateOfJoining()));
			insertAdminAccountsDb.setDate(7, Date.valueOf(AddAdmin_IF.admin.getContractEndDate()));
			insertAdminAccountsDb.executeQuery();
			insertAdminAccountsDb.close();

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

	public boolean addAdminSubjectsQuery(String query1)
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
}
