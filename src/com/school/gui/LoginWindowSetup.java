package com.school.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoginWindowSetup 
{
	JPanel pLoginPanel = new LoginPanel();
	static JFrame loginWindow = new JFrame("School Management System");
	
	public JFrame createLoginFrame()
	{
		final JMenuBar loginMenuBar = getMenuBar(); 
		loginWindow.setSize(600, 600);
		loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginWindow.setResizable(false);
		loginWindow.setJMenuBar(loginMenuBar);
		
		loginWindow.add(pLoginPanel);
		
		loginWindow.setLocationRelativeTo(null);
		loginWindow.setVisible(true);
		
		return loginWindow;
	}
	
	public JMenuBar getMenuBar()
	{
		final JMenuBar menuBar = new JMenuBar();
		
		final JMenu mFile = new JMenu("File");
		final JMenuItem miExit = new JMenuItem("Exit");
		mFile.add(miExit);
		miExit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						loginWindow.dispatchEvent(new WindowEvent(loginWindow, WindowEvent.WINDOW_CLOSING));
					}
				});
		menuBar.add(mFile);
		
		final JMenu mHelp = new JMenu("Help");
		final JMenuItem miAbout = new JMenuItem("About");
		
		miAbout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(loginWindow, 
											"School Management System: Version 1.0\n"
											+ "Author: Ganesh Chawkekar \n"
											+ "Java 1.8", 
											"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		mHelp.add(miAbout);
		menuBar.add(mHelp);
		
		return menuBar;
	}
}
