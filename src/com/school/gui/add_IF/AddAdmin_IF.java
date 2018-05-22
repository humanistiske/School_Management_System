package com.school.gui.add_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.school.database.AdminDb;
import com.school.database.DatabaseConnect;
import com.school.gui.SystemWindowSetup;
import com.school.gui.add_IF.AddAdminInfoPanel.AdminAccountInfoPanel;
import com.school.gui.add_IF.AddAdminInfoPanel.AdminInfoPanel;
import com.school.gui.add_IF.AddUserInfoPanel.AddUserLoginInfoPanel;
import com.school.users.Admin;
import com.school.users.User;

public class AddAdmin_IF 
{
	private AddUserInfoPanel userInfoPanel = new AddUserInfoPanel();
	private AddUserLoginInfoPanel userLoginPanel = userInfoPanel.new AddUserLoginInfoPanel();
	private AddAdminInfoPanel addAdminInfoPanel = new AddAdminInfoPanel();
	private AdminAccountInfoPanel adminAccountInfoPanel = addAdminInfoPanel.new AdminAccountInfoPanel();
	private AdminInfoPanel adminInfoPanel = addAdminInfoPanel.new AdminInfoPanel();

	final JInternalFrame addAdmin_IF = new JInternalFrame("Add Admin", true, true, true, true);
	final JPanel pAddAdmin = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	public final static Admin admin = new Admin(AddUserInfoPanel.getId());
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddAdmin_IF.inputError = inputError;
	}

	public JInternalFrame addAdmin_IF()
	{
		panelCount = 0;

		pAddAdmin.setLayout(infoPanel);
		pAddAdmin.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pAddAdmin.add(userInfoPanel.createAddUserInfoPanel(), "UserInfo");
		pAddAdmin.add(adminInfoPanel.createAdminInfoPanel(), "AdminClassInfo");
		pAddAdmin.add(adminAccountInfoPanel.createAdminAccountPanel(), "AdminAccountInfo");
		pAddAdmin.add(userLoginPanel.createUserLoginInfoPanel(), "UserLoginInfo");

		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		AddAdmin_IF.pInputError.add(error);
		pInputError.setPreferredSize(new Dimension(300, 80));

		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bRefresh.setPreferredSize(bPrevious.getPreferredSize());
		bNext.setPreferredSize(bPrevious.getPreferredSize());
		bSubmit.setPreferredSize(bPrevious.getPreferredSize());
		pButtons.add(bPrevious);
		pButtons.add(bRefresh);
		pButtons.add(bSubmit);
		pButtons.add(bNext);

		addAdmin_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addAdmin_IF.setLayout(new BorderLayout());
		addAdmin_IF.add(pAddAdmin, BorderLayout.NORTH);
		addAdmin_IF.add(pInputError, BorderLayout.CENTER);
		addAdmin_IF.add(pButtons, BorderLayout.PAGE_END);
		addAdmin_IF.setVisible(true);

		bPrevious.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				panelCount--;		
				switch(panelCount)
				{
				case 0: 
					infoPanel.show(pAddAdmin, "UserInfo");
					break;
				case 1:
					infoPanel.show(pAddAdmin, "AdminClassInfo");
					break;
				case 2:
					infoPanel.show(pAddAdmin, "AdminAccountInfo");
					break;
				case 3:
					infoPanel.show(pAddAdmin, "UserLoginInfo");
					break;
				default:
					if(panelCount < 0)
						panelCount = 0;
					else if(panelCount > 3)
						panelCount = 3;
					break;
				}
			}
		});
		bNext.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				panelCount++;		
				switch(panelCount)
				{
				case 0: 
					infoPanel.show(pAddAdmin, "UserInfo");
					break;
				case 1:
					infoPanel.show(pAddAdmin, "AdminClassInfo");
					break;
				case 2:
					infoPanel.show(pAddAdmin, "AdminAccountInfo");
					break;
				case 3:
					infoPanel.show(pAddAdmin, "UserLoginInfo");
					break;
				default:
					if(panelCount < 0)
						panelCount = 0;
					else if(panelCount > 3)
						panelCount = 3;
					break;
				}
			}
		});
		bRefresh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				AddUserInfoPanel.setId(DatabaseConnect.getId("userdb"));
				AddUserInfoPanel.setUserId("AD" + AddUserInfoPanel.getId());
				admin.setUserid(AddUserInfoPanel.getUserId());
				userInfoPanel.tfUserId.setText(AddUserInfoPanel.getUserId());
				userLoginPanel.tfUserID.setText(AddUserInfoPanel.getUserId());
			}
		});
		bSubmit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					if(userInfoPanel.tfAddress.getText().length() == 0)
						throw new RuntimeException();

					setInputError(false);
				}
				catch(Exception e1)
				{
					setInputError(true);
					AddAdmin_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.red),
							BorderFactory.createEmptyBorder(0, 10, 0, 0)));
					AddAdmin_IF.error.setText("Address cannot be blank");
				}

				try
				{
					if(!((adminInfoPanel.tfHighestQualificationDoc.getText().length()) > 0))
					{

						throw new RuntimeException();
					}
					setInputError(false);
				}
				catch(Exception e2)
				{
					setInputError(true);
					AddAdmin_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.red),
							BorderFactory.createEmptyBorder(0, 10, 0, 0)));
					AddAdmin_IF.error.setText("Qualification documents not uploaded");
				}

				if(isInputError())
				{
					JOptionPane.showMessageDialog(null, "Oops something went wrong...");
				}
				else
				{
					try
					{
						User.setId(AddUserInfoPanel.getId());
						admin.setUserid(AddUserInfoPanel.getUserId());
						User.setUserType(AddUserInfoPanel.getUsertype());
						admin.setfName(userInfoPanel.tfFirstName.getText());
						admin.setlName(userInfoPanel.tfLastName.getText());
						admin.setFullName(userInfoPanel.tfFirstName.getText(), userInfoPanel.tfLastName.getText());
						admin.setAddress(userInfoPanel.tfAddress.getText());
						admin.setPhone(Long.parseLong(userInfoPanel.tfPhone.getText()));
						admin.setAltPhone(Long.parseLong(userInfoPanel.tfAltPhone.getText()));
						admin.setEmailid(userInfoPanel.tfEmail.getText());
						admin.setPhotoFileInputStream(userInfoPanel.photoFile);
						admin.setIdFileInputStream(userInfoPanel.idFile);

						admin.setDesignation(adminInfoPanel.cbDesignation.getSelectedItem().toString());
						admin.setHighest_qualification(adminInfoPanel.tfHighestQualification.getText());
						admin.setQualification_documentInputStream(adminInfoPanel.highestQualificationDoc);

						admin.setTotalSalary(Double.parseDouble(adminAccountInfoPanel.tfTotalSalary.getText()));
						admin.setAllowance(Double.parseDouble(adminAccountInfoPanel.tfAllowance.getText()));
						admin.setInHandSalary(Double.parseDouble(adminAccountInfoPanel.tfInhandSalary.getText()));
						admin.setDateOfJoining(LocalDate.parse(adminAccountInfoPanel.tfDateOfJoining.getText()));
						admin.setContractEndDate(LocalDate.parse(adminAccountInfoPanel.tfContractEndDate.getText()));

						admin.setPassword(String.valueOf(userLoginPanel.tfPassword.getPassword()));

						if(new AdminDb().addAdmin())
							JOptionPane.showMessageDialog(null, "Admin added successfully");
						else
							JOptionPane.showMessageDialog(null, "Oops make sure all fields are added");
					}
					catch(Exception error)
					{
						JOptionPane.showMessageDialog(null, "Oops some information is not filled");
						System.out.println(e);
					}
				}
			}
		});

		return addAdmin_IF;
	}

}

