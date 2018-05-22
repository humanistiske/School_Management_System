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
import com.school.database.DatabaseConnect;
import com.school.database.TeacherDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.internalFrames.AddTeacherInfoPanel.TeacherAccountInfoPanel;
import com.school.gui.internalFrames.AddTeacherInfoPanel.TeacherInfoPanel;
import com.school.gui.internalFrames.AddUserInfoPanel.AddUserLoginInfoPanel;
import com.school.users.Teacher;
import com.school.users.User;

public class AddTeacher_IF 
{
	private AddUserInfoPanel userInfoPanel = new AddUserInfoPanel();
	private AddUserLoginInfoPanel userLoginPanel = userInfoPanel.new AddUserLoginInfoPanel();
	private AddTeacherInfoPanel addTeacherInfoPanel = new AddTeacherInfoPanel();
	private TeacherAccountInfoPanel teacherAccountInfoPanel = addTeacherInfoPanel.new TeacherAccountInfoPanel();
	private TeacherInfoPanel teacherInfoPanel = addTeacherInfoPanel.new TeacherInfoPanel();
	
	final JInternalFrame addTeacher_IF = new JInternalFrame("Add Teacher", true, true, true, true);
	final JPanel pAddTeacher = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	public final static Teacher teacher = new Teacher(AddUserInfoPanel.getId());
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddTeacher_IF.inputError = inputError;
	}

	public JInternalFrame addTeacher_IF()
	{
		panelCount = 0;
		
		pAddTeacher.setLayout(infoPanel);
		pAddTeacher.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pAddTeacher.add(userInfoPanel.createAddUserInfoPanel(), "UserInfo");
		pAddTeacher.add(teacherInfoPanel.createTeacherInfoPanel(), "TeacherClassInfo");
		pAddTeacher.add(teacherAccountInfoPanel.createTeacherAccountPanel(), "TeacherAccountInfo");
		pAddTeacher.add(userLoginPanel.createUserLoginInfoPanel(), "UserLoginInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		AddTeacher_IF.pInputError.add(error);
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
		
		addTeacher_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addTeacher_IF.setLayout(new BorderLayout());
		addTeacher_IF.add(pAddTeacher, BorderLayout.NORTH);
		addTeacher_IF.add(pInputError, BorderLayout.CENTER);
		addTeacher_IF.add(pButtons, BorderLayout.PAGE_END);
		addTeacher_IF.setVisible(true);
		
		bPrevious.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						panelCount--;		
						switch(panelCount)
						{
							case 0: 
								infoPanel.show(pAddTeacher, "UserInfo");
								break;
							case 1:
								infoPanel.show(pAddTeacher, "TeacherClassInfo");
								break;
							case 2:
								infoPanel.show(pAddTeacher, "TeacherAccountInfo");
								break;
							case 3:
								infoPanel.show(pAddTeacher, "UserLoginInfo");
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
								infoPanel.show(pAddTeacher, "UserInfo");
								break;
							case 1:
								infoPanel.show(pAddTeacher, "TeacherClassInfo");
								break;
							case 2:
								infoPanel.show(pAddTeacher, "TeacherAccountInfo");
								break;
							case 3:
								infoPanel.show(pAddTeacher, "UserLoginInfo");
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
						AddUserInfoPanel.setUserId("T" + AddUserInfoPanel.getId());
						teacher.setUserid(AddUserInfoPanel.getUserId());
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
							AddTeacher_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							AddTeacher_IF.error.setText("Address cannot be blank");
						}
						
						try
						{
							if(!((teacherInfoPanel.tfHighestQualificationDoc.getText().length()) > 0))
							{
								
								throw new RuntimeException();
							}
							setInputError(false);
						}
						catch(Exception e2)
						{
							setInputError(true);
							AddTeacher_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							AddTeacher_IF.error.setText("Qualification documents not uploaded");
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
								teacher.setUserid(AddUserInfoPanel.getUserId());
								User.setUserType(AddUserInfoPanel.getUsertype());
								teacher.setfName(userInfoPanel.tfFirstName.getText());
								teacher.setlName(userInfoPanel.tfLastName.getText());
								teacher.setFullName(userInfoPanel.tfFirstName.getText(), userInfoPanel.tfLastName.getText());
								teacher.setAddress(userInfoPanel.tfAddress.getText());
								teacher.setPhone(Long.parseLong(userInfoPanel.tfPhone.getText()));
								teacher.setAltPhone(Long.parseLong(userInfoPanel.tfAltPhone.getText()));
								teacher.setEmailid(userInfoPanel.tfEmail.getText());
								teacher.setPhotoFileInputStream(userInfoPanel.photoFile);
								teacher.setIdFileInputStream(userInfoPanel.idFile);

								teacher.setDesignation(teacherInfoPanel.cbDesignation.getSelectedItem().toString());
								teacher.setHighest_qualification(teacherInfoPanel.tfHighestQualification.getText());
								teacher.setQualification_documentInputStream(teacherInfoPanel.highestQualificationDoc);
								teacher.setSubjectList(teacherInfoPanel.selectedSubjectList);
								
								teacher.setTotalSalary(Double.parseDouble(teacherAccountInfoPanel.tfTotalSalary.getText()));
								teacher.setAllowance(Double.parseDouble(teacherAccountInfoPanel.tfAllowance.getText()));
								teacher.setInHandSalary(Double.parseDouble(teacherAccountInfoPanel.tfInhandSalary.getText()));
								teacher.setDateOfJoining(LocalDate.parse(teacherAccountInfoPanel.tfDateOfJoining.getText()));
								teacher.setContractEndDate(LocalDate.parse(teacherAccountInfoPanel.tfContractEndDate.getText()));
								
								teacher.setPassword(userLoginPanel.tfPassword.getPassword().toString());
								
								if(new TeacherDb().addTeacher())
									JOptionPane.showMessageDialog(null, "Teacher added successfully");
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
		
		return addTeacher_IF;
	}

}


