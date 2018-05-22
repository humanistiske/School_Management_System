package com.school.gui.internalFrames;

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

import com.school.database.AccountantDb;
import com.school.database.DatabaseConnect;
import com.school.gui.SystemWindowSetup;
import com.school.gui.internalFrames.AddAccountantInfoPanel.AccountantAccountInfoPanel;
import com.school.gui.internalFrames.AddAccountantInfoPanel.AccountantInfoPanel;
import com.school.gui.internalFrames.AddUserInfoPanel.AddUserLoginInfoPanel;
import com.school.users.Accountant;
import com.school.users.User;

public class AddAccountant_IF 
{
	private AddUserInfoPanel userInfoPanel = new AddUserInfoPanel();
	private AddUserLoginInfoPanel userLoginPanel = userInfoPanel.new AddUserLoginInfoPanel();
	private AddAccountantInfoPanel addAccountantInfoPanel = new AddAccountantInfoPanel();
	private AccountantAccountInfoPanel accountantAccountInfoPanel = addAccountantInfoPanel.new AccountantAccountInfoPanel();
	private AccountantInfoPanel accountantInfoPanel = addAccountantInfoPanel.new AccountantInfoPanel();

	final JInternalFrame addAccountant_IF = new JInternalFrame("Add Accountant", true, true, true, true);
	final JPanel pAddAccountant = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	public final static Accountant accountant = new Accountant(AddUserInfoPanel.getId());
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddAccountant_IF.inputError = inputError;
	}

	public JInternalFrame addAccountant_IF()
	{
		panelCount = 0;

		pAddAccountant.setLayout(infoPanel);
		pAddAccountant.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pAddAccountant.add(userInfoPanel.createAddUserInfoPanel(), "UserInfo");
		pAddAccountant.add(accountantInfoPanel.createAccountantInfoPanel(), "AccountantClassInfo");
		pAddAccountant.add(accountantAccountInfoPanel.createAccountantAccountPanel(), "AccountantAccountInfo");
		pAddAccountant.add(userLoginPanel.createUserLoginInfoPanel(), "UserLoginInfo");

		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		AddAccountant_IF.pInputError.add(error);
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

		addAccountant_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addAccountant_IF.setLayout(new BorderLayout());
		addAccountant_IF.add(pAddAccountant, BorderLayout.NORTH);
		addAccountant_IF.add(pInputError, BorderLayout.CENTER);
		addAccountant_IF.add(pButtons, BorderLayout.PAGE_END);
		addAccountant_IF.setVisible(true);

		bPrevious.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				panelCount--;		
				switch(panelCount)
				{
				case 0: 
					infoPanel.show(pAddAccountant, "UserInfo");
					break;
				case 1:
					infoPanel.show(pAddAccountant, "AccountantClassInfo");
					break;
				case 2:
					infoPanel.show(pAddAccountant, "AccountantAccountInfo");
					break;
				case 3:
					infoPanel.show(pAddAccountant, "UserLoginInfo");
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
					infoPanel.show(pAddAccountant, "UserInfo");
					break;
				case 1:
					infoPanel.show(pAddAccountant, "AccountantClassInfo");
					break;
				case 2:
					infoPanel.show(pAddAccountant, "AccountantAccountInfo");
					break;
				case 3:
					infoPanel.show(pAddAccountant, "UserLoginInfo");
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
				AddUserInfoPanel.setUserId("AC" + AddUserInfoPanel.getId());
				accountant.setUserid(AddUserInfoPanel.getUserId());
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
					AddAccountant_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.red),
							BorderFactory.createEmptyBorder(0, 10, 0, 0)));
					AddAccountant_IF.error.setText("Address cannot be blank");
				}

				try
				{
					if(!((accountantInfoPanel.tfHighestQualificationDoc.getText().length()) > 0))
					{

						throw new RuntimeException();
					}
					setInputError(false);
				}
				catch(Exception e2)
				{
					setInputError(true);
					AddAccountant_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.red),
							BorderFactory.createEmptyBorder(0, 10, 0, 0)));
					AddAccountant_IF.error.setText("Qualification documents not uploaded");
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
						accountant.setUserid(AddUserInfoPanel.getUserId());
						User.setUserType(AddUserInfoPanel.getUsertype());
						accountant.setfName(userInfoPanel.tfFirstName.getText());
						accountant.setlName(userInfoPanel.tfLastName.getText());
						accountant.setFullName(userInfoPanel.tfFirstName.getText(), userInfoPanel.tfLastName.getText());
						accountant.setAddress(userInfoPanel.tfAddress.getText());
						accountant.setPhone(Long.parseLong(userInfoPanel.tfPhone.getText()));
						accountant.setAltPhone(Long.parseLong(userInfoPanel.tfAltPhone.getText()));
						accountant.setEmailid(userInfoPanel.tfEmail.getText());
						accountant.setPhotoFileInputStream(userInfoPanel.photoFile);
						accountant.setIdFileInputStream(userInfoPanel.idFile);

						accountant.setDesignation(accountantInfoPanel.cbDesignation.getSelectedItem().toString());
						accountant.setHighest_qualification(accountantInfoPanel.tfHighestQualification.getText());
						accountant.setQualification_documentInputStream(accountantInfoPanel.highestQualificationDoc);

						accountant.setTotalSalary(Double.parseDouble(accountantAccountInfoPanel.tfTotalSalary.getText()));
						accountant.setAllowance(Double.parseDouble(accountantAccountInfoPanel.tfAllowance.getText()));
						accountant.setInHandSalary(Double.parseDouble(accountantAccountInfoPanel.tfInhandSalary.getText()));
						accountant.setDateOfJoining(LocalDate.parse(accountantAccountInfoPanel.tfDateOfJoining.getText()));
						accountant.setContractEndDate(LocalDate.parse(accountantAccountInfoPanel.tfContractEndDate.getText()));

						accountant.setPassword(userLoginPanel.tfPassword.getPassword().toString());

						if(new AccountantDb().addAccountant())
							JOptionPane.showMessageDialog(null, "Accountant added successfully");
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

		return addAccountant_IF;
	}

}

