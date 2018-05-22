package com.school.database;

import com.school.users.User;

public class UserLogin 
{
	public static final boolean login(final String userid, final String password)
	{
		boolean res = true;
		try
		{
			if(!User.checkUserType(userid)) //If res == false
			{	
				final String query = "select * from logindb where userid = " + User.getId() + " and password = " + password;
				DatabaseConnect.rs = DatabaseConnect.stmt.executeQuery(query);
				
				while(DatabaseConnect.rs.next())
				{
					System.out.println(DatabaseConnect.rs.getInt(1));
					res = false;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return res;
	}
}
