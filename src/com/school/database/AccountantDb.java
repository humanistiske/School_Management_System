package com.school.database;

import java.sql.Date;
import java.sql.PreparedStatement;

import com.school.UserType;
import com.school.gui.add_IF.AddAccountant_IF;
import com.school.gui.add_IF.AddUserInfoPanel;
import com.school.users.Accountant;

public class AccountantDb 
{
	private static PreparedStatement insertUserDb, insertLoginDb, 
	insertAccountantInfoDb, insertAccountantAccountsDb;

	private static final String loginDb = "insert into logindb(userid, usertypeid, password)"
			+ "values(?, ?, ?)";

	private static final String userDb = "insert into userdb(userid, fname, lname, address, phone, alt_phone, email_id, photo, id_proof)"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String staffInfoDb = "insert into staffinfodb(userid, usertypeid, designation, highest_qualification, qualification_documents)"
			+ "values(?, ?, ?, ?, ?)";

	private static final String staffAccountsDb = "insert into staffaccountsdb("
			+ "userid, in_hand_salary, allowance, total_salary, salarystatusid, date_of_joining, contract_end_date)"
			+ "values(?, ?, ?, ?, ?, ?, ? )"; 

	public final boolean addAccountant()
	{	
		boolean result = false;

		try
		{
			insertUserDb = DatabaseConnect.con.prepareStatement(userDb);	
			insertUserDb.setInt(1, AddUserInfoPanel.getId());
			insertUserDb.setString(2, AddAccountant_IF.accountant.getfName());
			insertUserDb.setString(3, AddAccountant_IF.accountant.getlName());
			insertUserDb.setString(4, AddAccountant_IF.accountant.getAddress());
			insertUserDb.setLong(5, AddAccountant_IF.accountant.getPhone());
			insertUserDb.setLong(6, AddAccountant_IF.accountant.getAltPhone());
			insertUserDb.setString(7, AddAccountant_IF.accountant.getEmailid());
			insertUserDb.setBinaryStream(8, AddAccountant_IF.accountant.getPhotoFileInputStream());
			insertUserDb.setBinaryStream(9, AddAccountant_IF.accountant.getIdFileInputStream());
			insertUserDb.executeQuery();
			insertUserDb.close();

			insertLoginDb = DatabaseConnect.con.prepareStatement(loginDb);
			insertLoginDb.setInt(1, AddUserInfoPanel.getId());
			insertLoginDb.setString(2, UserType.ACCOUNTANT.toString());
			insertLoginDb.setString(3, AddAccountant_IF.accountant.getPassword());
			insertLoginDb.executeQuery();
			insertLoginDb.close();

			insertAccountantInfoDb = DatabaseConnect.con.prepareStatement(staffInfoDb);
			insertAccountantInfoDb.setInt(1, AddUserInfoPanel.getId());
			insertAccountantInfoDb.setString(2, Accountant.getUserType().toString());
			insertAccountantInfoDb.setString(3, AddAccountant_IF.accountant.getDesignation());
			insertAccountantInfoDb.setString(4, AddAccountant_IF.accountant.getHighest_qualification());
			insertAccountantInfoDb.setBinaryStream(5, AddAccountant_IF.accountant.getQualification_documentInputStream());
			insertAccountantInfoDb.executeQuery();
			insertAccountantInfoDb.close();

			insertAccountantAccountsDb = DatabaseConnect.con.prepareStatement(staffAccountsDb);
			insertAccountantAccountsDb.setInt(1, AddUserInfoPanel.getId());
			insertAccountantAccountsDb.setDouble(2, AddAccountant_IF.accountant.getInHandSalary());
			insertAccountantAccountsDb.setDouble(3, AddAccountant_IF.accountant.getAllowance());
			insertAccountantAccountsDb.setDouble(4, AddAccountant_IF.accountant.getTotalSalary());
			insertAccountantAccountsDb.setInt(5, 1);
			insertAccountantAccountsDb.setDate(6, Date.valueOf(AddAccountant_IF.accountant.getDateOfJoining()));
			insertAccountantAccountsDb.setDate(7, Date.valueOf(AddAccountant_IF.accountant.getContractEndDate()));
			insertAccountantAccountsDb.executeQuery();
			insertAccountantAccountsDb.close();

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

	public boolean addAccountantSubjectsQuery(String query1)
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
