package com.school.webspace;

import com.school.database.DatabaseConnect;
import com.school.gui.*;

public class Main 
{
	public static void main(String[] args) 
	{
		systemSetup();
	}
	
	public static void systemSetup()
	{
		DatabaseConnect.connectDatabase();
		new LoginWindowSetup().createLoginFrame();
		/*MainWindowSetup.createMainWindowFrame();*/
	}
}
