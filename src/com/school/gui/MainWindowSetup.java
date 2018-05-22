package com.school.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.school.gui.ToolbarPanel;
import com.school.SystemUtils;

public class MainWindowSetup 
{
	private JFrame mainWindow = new JFrame("School Management System");
	public final static ToolbarPanel pToolbarPanel = new ToolbarPanel(); 
	public final static JLayeredPane JDPsystemSetup = SystemWindowSetup.createSystemSetup();
	final static JPanel pSystemSetup = pSystemSetup();
	
	public JFrame createMainWindowFrame()
	{
		mainWindow.setSize(SystemUtils.WINDOW_HEIGHT, SystemUtils.WINDOW_WIDTH);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setJMenuBar(getMenuBar());
		mainWindow.setLayout(new BorderLayout());
		
		switch(LoginPanel.getUserLogin().getUserType())
		{
			case STUDENT:
				mainWindow.add(pToolbarPanel.createStudentToolbarPanel(), BorderLayout.PAGE_START);
				break;
			case TEACHER:
				mainWindow.add(pToolbarPanel.createTeacherToolbarPanel(), BorderLayout.PAGE_START);
				break;
			case ADMIN:
				mainWindow.add(pToolbarPanel.createAdminToolbarPanel(), BorderLayout.PAGE_START);
				break;
			case ACCOUNTANT:
				mainWindow.add(pToolbarPanel.createAccountantToolbarPanel(), BorderLayout.PAGE_START);
				break;
			default:
				break;
		}
		
		mainWindow.add(pSystemSetup, BorderLayout.CENTER);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
		
		return mainWindow;
	}
	
	public static JPanel pSystemSetup()
	{
		final JPanel pSystemSetup = new JPanel();
		pSystemSetup.add(JDPsystemSetup);
		
		return pSystemSetup;
	}
	
	public JMenuBar getMenuBar()
	{
		final JMenuBar menuBar = new JMenuBar();
		
		final JMenu mFile = new JMenu("File");
		final JMenuItem miChangePassword = new JMenuItem("Change Password");
		mFile.add(miChangePassword);
		final JMenuItem miLogout = new JMenuItem("Logout");
		
		miChangePassword.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						JDialog dChangePassword = new JDialog(mainWindow, "Change Password", true);
						dChangePassword.setLayout(new GridLayout(1, 0));
						dChangePassword.add(new ChangePasswordPanel().changePasswordPanel());
						dChangePassword.pack();
						dChangePassword.setLocationRelativeTo(null);
						dChangePassword.setVisible(true);
					}
				});
		miLogout.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						mainWindow.dispose();
						new LoginWindowSetup().createLoginFrame();
						mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
					}
				});
		
		mFile.add(miLogout);
		menuBar.add(mFile);
		
		final JMenu mHelp = new JMenu("Help");
		final JMenuItem miViewProfile = new JMenuItem("View My Profile");
		final JMenuItem miAbout = new JMenuItem("About");
		
		miViewProfile.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						JDialog dProfile = new JDialog(mainWindow, "My Profile", true);
						dProfile.setLayout(new GridLayout(1, 0));
						dProfile.add(new ViewProfilePanel().viewUserProfilePanel());
						dProfile.pack();
						dProfile.setLocationRelativeTo(null);
						dProfile.setVisible(true);
					}
				});
		miAbout.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						JOptionPane.showMessageDialog(mainWindow, 
													"School Management System: Version 1.0\n"
													+ "Author: Ganesh Chawkekar \n"
													+ "Java 1.8", 
													"About", JOptionPane.INFORMATION_MESSAGE);
					}
				});
		
		mHelp.add(miViewProfile);
		mHelp.add(miAbout);
		menuBar.add(mHelp);
		
		return menuBar;
	}
}
