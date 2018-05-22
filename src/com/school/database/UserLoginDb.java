package com.school.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.school.gui.ChangePasswordPanel;
import com.school.users.User;

public class UserLoginDb 
{
	private static PreparedStatement selectUserLoginDb, viewUserProfileDb, updateLoginDb;
	
	private static final String selectUserLogin = "select * from logindb where userid = ?"
										/*+ UserLogin.getId()*/ + " and password = ?";
	private static final String viewUserProfile = "select * from userdb where userid = ?";
	
	
	
	public final boolean login(final String userid, final int id, final String password)
	{
		boolean result = false;
		ResultSet rs = null;
		
		try
		{
			selectUserLoginDb = DatabaseConnect.con.prepareStatement(selectUserLogin);
			
			if(User.checkUserType(userid)) //If res == false
			{	
				selectUserLoginDb.setInt(1, id);
				selectUserLoginDb.setString(2, password);
				rs = selectUserLoginDb.executeQuery();
				
				while(rs.next())
				{
					result = true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public final ResultSet viewProfile(final int id)
	{
		ResultSet rs = null;
		
		try
		{
			viewUserProfileDb = DatabaseConnect.con.prepareStatement(viewUserProfile);
			viewUserProfileDb.setInt(1, id);
			rs = viewUserProfileDb.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public final boolean updateLogin(String password)
	{	
		boolean result = false;

		try
		{			
			updateLoginDb = DatabaseConnect.con.prepareStatement("update logindb set "
					+ "password = ?"
					+ "where userid = " + Integer.parseInt(ChangePasswordPanel.tfUserID.getText().replaceAll("[a-zA-Z]+", "")));
			updateLoginDb.setString(1, password);
			updateLoginDb.executeQuery();
			updateLoginDb.close();

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
