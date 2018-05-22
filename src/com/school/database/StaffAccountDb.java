package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.school.gui.accountant.edit_IF.EditStaff_IF;

public class StaffAccountDb
{
	private static PreparedStatement searchStaffAccountInfoDb, updateStaffAccountInfoDb;
	private static final String getStaffAccountInfo = "select staffaccountsdb.in_hand_salary, \r\n" + 
			"        staffaccountsdb.allowance, \r\n" + 
			"        staffaccountsdb.total_salary, \r\n" + 
			"        staffaccountsdb.last_salary_payment_date,\r\n" + 
			"        salarystatus.salary_status_desc,\r\n" + 
			"        staffaccountsdb.contract_end_date\r\n" + 
			"from staffaccountsdb\r\n" + 
			"left outer join salarystatus on(SALARYSTATUS.SALARYSTATUSID = staffaccountsdb.SALARYSTATUSID)\r\n" + 
			"where userid = ?";
	
	private static final String updateStaffAccountInfo = "update staffaccountsdb set "
			+ "in_hand_salary = ?, allowance = ?, total_salary = ?, salarystatusid = ?, "
			+ "last_salary_payment_date = to_date('" + LocalDate.now() + "', 'yyyy-mm-dd') "
					+ "where userid = ?";
	
	public ResultSet searchStaffAccountInfoDb(int id)
	{
		ResultSet rs = null;
		try
		{
			searchStaffAccountInfoDb = DatabaseConnect.con.prepareStatement(getStaffAccountInfo);
			searchStaffAccountInfoDb.setInt(1, id);
			rs = searchStaffAccountInfoDb.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rs;
	}
	
	
	public final boolean updateStaffAccount()
	{	
		boolean result = false;

		try
		{			
			updateStaffAccountInfoDb = DatabaseConnect.con.prepareStatement(updateStaffAccountInfo);
			updateStaffAccountInfoDb.setDouble(1, EditStaff_IF.staff.getInHandSalary());
			updateStaffAccountInfoDb.setDouble(2, EditStaff_IF.staff.getAllowance());
			updateStaffAccountInfoDb.setDouble(3, EditStaff_IF.staff.getTotalSalary());
			updateStaffAccountInfoDb.setInt(4, EditStaff_IF.staff.getSalaryStatusId());
			updateStaffAccountInfoDb.setInt(5, EditStaff_IF.staff.getId());
			updateStaffAccountInfoDb.executeQuery();
			updateStaffAccountInfoDb.close();

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
	
}
