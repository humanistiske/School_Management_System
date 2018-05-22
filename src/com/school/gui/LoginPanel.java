package com.school.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.database.*;
import com.school.users.UserLogin;

public class LoginPanel extends JPanel 
{
	private static UserLogin userLogin;
	final JPanel pUserId = new JPanel();
	final JPanel pPassword = new JPanel();
	final JPanel pButtons = new JPanel();
	final JLabel lUserId = new JLabel("User ID: ");
	public final JTextField tfUserId = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JLabel lPassword = new JLabel("Password: ");
	public final JPasswordField tfPassword = new JPasswordField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton btLogin = new JButton("Login");
	final JButton btReset = new JButton("Reset");
	private final static UserLoginDb userLoginDb = new UserLoginDb();
	
	public final static UserLogin getUserLogin() {
		return userLogin;
	}
	
	public final static UserLoginDb getUserLoginDb()
	{
		return userLoginDb;
	}
	
	public LoginPanel()
	{
		//User ID Panel
		pUserId.setLayout(new BorderLayout(5, 5));
		pUserId.add(lUserId, BorderLayout.WEST);
		pUserId.add(tfUserId, BorderLayout.EAST);

		//User Password Panel
		pPassword.setLayout(new BorderLayout(5, 5));
		pPassword.add(lPassword, BorderLayout.WEST);
		pPassword.add(tfPassword, BorderLayout.EAST);
		
		//Buttons Panel	
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
		pButtons.add(btLogin);
		pButtons.add(btReset);
		
		btLogin.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						String userInitial = tfUserId.getText().replaceAll("[0-9]+", "");
						UserType userType = null;
						
						switch(userInitial.toUpperCase())
						{
							case "S":
								userType = UserType.STUDENT;
								break;
							case "T":
								userType = UserType.TEACHER;
								break;
							case "AC":
								userType = UserType.ACCOUNTANT;
								break;	
							case "AD":
								userType = UserType.ADMIN;
								break;
							default:
								break;
						}
						
						userLogin = new UserLogin(userType, 
								Integer.parseInt(tfUserId.getText().replaceAll("[a-zA-Z]+", "")));
						
						if(userLoginDb.login(String.valueOf(UserLogin.getId()), UserLogin.getId(), 
								String.valueOf(tfPassword.getPassword())))
						{
							LoginWindowSetup.loginWindow.dispose();	
							new MainWindowSetup().createMainWindowFrame();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Oops incorrect userid or password!!!");
						}
					}
				});
		btReset.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						tfUserId.setText("");
						tfPassword.setText("");
					}
				});
		
		//Login Panel that will contain above components
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gridBag);

		gbc.insets = new Insets(4, 4, 4, 4);	//for padding in gridbag layout
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		this.add(pUserId, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(pPassword, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.add(pButtons, gbc);	
		this.setVisible(true);
	}
}
