package com.school.gui.internalFrames;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.school.database.DatabaseConnect;
import com.school.database.StudentDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.internalFrames.AddStudentInfoPanel.StudentAccountInfoPanel;
import com.school.gui.internalFrames.AddStudentInfoPanel.StudentInfoPanel;
import com.school.gui.internalFrames.AddUserInfoPanel.AddUserLoginInfoPanel;
import com.school.users.Student;
import com.school.users.User;

public final class AddStudent_IF 
{	 
	private AddUserInfoPanel userInfoPanel = new AddUserInfoPanel();
	private AddUserLoginInfoPanel userLoginPanel = userInfoPanel.new AddUserLoginInfoPanel();
	private AddStudentInfoPanel addStudentInfoPanel = new AddStudentInfoPanel();
	private StudentAccountInfoPanel studentAccountInfoPanel = addStudentInfoPanel.new StudentAccountInfoPanel();
	private StudentInfoPanel studentInfoPanel = addStudentInfoPanel.new StudentInfoPanel();
	
	final JInternalFrame addStudent_IF = new JInternalFrame("Add Student", true, true, true, true);
	final JPanel pAddStudent = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	public final static Student student = new Student(AddUserInfoPanel.getId());
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddStudent_IF.inputError = inputError;
	}

	public JInternalFrame addStudent_IF()
	{
		panelCount = 0;
		
		pAddStudent.setLayout(infoPanel);
		pAddStudent.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pAddStudent.add(userInfoPanel.createAddUserInfoPanel(), "UserInfo");
		pAddStudent.add(studentInfoPanel.createStudentInfoPanel(), "StudentClassInfo");
		pAddStudent.add(studentAccountInfoPanel.createStudentAccountPanel(), "StudentAccountInfo");
		pAddStudent.add(userLoginPanel.createUserLoginInfoPanel(), "UserLoginInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		AddStudent_IF.pInputError.add(error);
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
		
		addStudent_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addStudent_IF.setLayout(new BorderLayout());
		addStudent_IF.add(pAddStudent, BorderLayout.NORTH);
		addStudent_IF.add(pInputError, BorderLayout.CENTER);
		addStudent_IF.add(pButtons, BorderLayout.PAGE_END);
		addStudent_IF.setVisible(true);
		
		bPrevious.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						panelCount--;		
						switch(panelCount)
						{
							case 0: 
								infoPanel.show(pAddStudent, "UserInfo");
								break;
							case 1:
								infoPanel.show(pAddStudent, "StudentClassInfo");
								break;
							case 2:
								infoPanel.show(pAddStudent, "StudentAccountInfo");
								break;
							case 3:
								infoPanel.show(pAddStudent, "UserLoginInfo");
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
								infoPanel.show(pAddStudent, "UserInfo");
								break;
							case 1:
								infoPanel.show(pAddStudent, "StudentClassInfo");
								break;
							case 2:
								infoPanel.show(pAddStudent, "StudentAccountInfo");
								break;
							case 3:
								infoPanel.show(pAddStudent, "UserLoginInfo");
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
						AddUserInfoPanel.setUserId("S" + AddUserInfoPanel.getId());
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
							AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							AddStudent_IF.error.setText("Address cannot be blank");
						}
						
						try
						{
							if(!(Integer.parseInt(studentInfoPanel.tfTotalSubjects.getText()) > 0))
							{
								throw new RuntimeException();
							}
							setInputError(false);
						}
						catch(Exception e1)
						{
							setInputError(true);
							AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							AddStudent_IF.error.setText("No subjects selected");
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
								student.setUserid(AddUserInfoPanel.getUserId());
								User.setUserType(AddUserInfoPanel.getUsertype());
								student.setfName(userInfoPanel.tfFirstName.getText());
								student.setlName(userInfoPanel.tfLastName.getText());
								student.setFullName(userInfoPanel.tfFirstName.getText(), userInfoPanel.tfLastName.getText());
								student.setAddress(userInfoPanel.tfAddress.getText());
								student.setPhone(Long.parseLong(userInfoPanel.tfPhone.getText()));
								student.setAltPhone(Long.parseLong(userInfoPanel.tfAltPhone.getText()));
								student.setEmailid(userInfoPanel.tfEmail.getText());
								student.setPhotoFileInputStream(userInfoPanel.photoFile);
								student.setIdFileInputStream(userInfoPanel.idFile);
								
								student.setClassId(studentInfoPanel.cbClassId.getSelectedItem().toString());
								student.setTotal_subjects(Integer.parseInt(studentInfoPanel.tfTotalSubjects.getText()));
								student.setSubjectList(AddStudent_IF.student.getSubjectList());
								
								student.setTotalFees(Double.parseDouble(studentAccountInfoPanel.tfTotalFees.getText()));
								student.setPaidFees(Double.parseDouble(studentAccountInfoPanel.tfPaidFees.getText()));
								student.setOutstandingFees(Double.parseDouble(studentAccountInfoPanel.tfOutstandingFees.getText()));
								student.setLastPaymentDate(LocalDate.parse(studentAccountInfoPanel.tfLastPaymentDate.getText()));
								student.setDueDate(LocalDate.parse(studentAccountInfoPanel.tfDueDate.getText()));
								student.setPassword(Arrays.toString(userLoginPanel.tfPassword.getPassword()));
								
								if(new StudentDb().addStudent())
									JOptionPane.showMessageDialog(null, "Student added successfully");
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
		
		return addStudent_IF;
	}
}
