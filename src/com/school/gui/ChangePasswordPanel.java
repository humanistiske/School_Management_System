package com.school.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.school.GUIInterfaceUtilities;
import com.school.database.UserLoginDb;
import com.school.gui.InputValidators.JCompInputVerifier;
import com.school.gui.admin.edit_IF.EditStudent_IF;

public class ChangePasswordPanel 
{
	final JLabel lUserID = new JLabel("User ID: ");
	final JLabel lPassword = new JLabel("Password: ");
	final JLabel lConfirmPassword = new JLabel("Confirm Password: ");
	public static final JTextField tfUserID = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	public static final JPasswordField tfPassword = new JPasswordField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JPasswordField tfConfirmPassword = new JPasswordField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bUpdate = new JButton("Change Password");
	final JPanel pUserLoginInfo = new JPanel();
	
	public boolean confirmPassword(char[] password)
	{
		if(tfConfirmPassword.getPassword().equals(tfPassword.getPassword()))
		{
			return true;
		}
		else
		{
			EditStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			EditStudent_IF.error.setText("Passwords do not match");
			
			return false;
		}
	}
	
	public JPanel changePasswordPanel()
	{
		pUserLoginInfo.setBackground(Color.WHITE);
		pUserLoginInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		tfUserID.setText(LoginPanel.getUserLogin().getUserType().toString() + 
				LoginPanel.getUserLogin().getId());
		
		tfUserID.setEditable(false);
		tfPassword.setName("Password");
		tfPassword.setInputVerifier(new JCompInputVerifier());
		
		//Login Panel that will contain above components
		final GridBagLayout gridBag = new GridBagLayout();
		final GridBagConstraints gbc = new GridBagConstraints();
		pUserLoginInfo.setLayout(gridBag);
		
		tfConfirmPassword.getDocument().addDocumentListener(new DocumentListener()
				{
					@Override
					public void changedUpdate(DocumentEvent arg0) 
					{
						confirmPassword(tfConfirmPassword.getPassword());
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) 
					{
						confirmPassword(tfConfirmPassword.getPassword());	
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) 
					{
						confirmPassword(tfConfirmPassword.getPassword());
					}
				});
		bUpdate.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(new UserLoginDb().updateLogin(String.valueOf(tfPassword.getPassword())))
						{
							JOptionPane.showMessageDialog(null, "Password changed successfully");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Oops something went wrong!!!");
						}
					}
				});
	
		gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
		gbc.fill = GridBagConstraints.HORIZONTAL;		
		gbc.gridx = 0;
		gbc.gridy = 0;
		pUserLoginInfo.add(lUserID, gbc);
		gbc.gridx = 3;
		gbc.gridy = 0;
		pUserLoginInfo.add(tfUserID, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		pUserLoginInfo.add(lPassword, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		pUserLoginInfo.add(tfPassword, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		pUserLoginInfo.add(lConfirmPassword, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		pUserLoginInfo.add(tfConfirmPassword, gbc);
		gbc.gridx = 3;
		gbc.gridy = 3;
		pUserLoginInfo.add(bUpdate, gbc);
		
		return pUserLoginInfo;
	}
}
