package com.school.gui.admin.edit_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.school.database.StudentDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.admin.edit_IF.EditStudentInfoPanel.StudentInfoPanel;
import com.school.gui.admin.edit_IF.EditUserInfoPanel.EditUserLoginInfoPanel;
import com.school.users.Student;

public final class EditStudent_IF 
{	 
	private EditUserInfoPanel userInfoPanel = new EditUserInfoPanel();
	private EditUserLoginInfoPanel userLoginPanel = userInfoPanel.new EditUserLoginInfoPanel();
	private EditStudentInfoPanel addStudentInfoPanel = new EditStudentInfoPanel();
	private StudentInfoPanel studentInfoPanel = addStudentInfoPanel.new StudentInfoPanel();
	
	final JInternalFrame editStudent_IF = new JInternalFrame("Edit Student", true, true, true, true);
	final JPanel pEditStudent = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Update");
	final JPanel pButtons = new JPanel();
	public static Student student;
	public static int id;
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		EditStudent_IF.inputError = inputError;
	}

	public JInternalFrame editStudent_IF()
	{
		//user, class and login info
		try
		{
			//filling retrieved data info
			ResultSet rs = new  StudentDb().getStudentInfo(student.getId());
			while(rs.next())
			{
				student.setfName(rs.getString("fname"));
				student.setlName(rs.getString("lname"));
				student.setPhone(rs.getLong("phone"));
				student.setAltPhone(rs.getLong("alt_phone"));
				student.setEmailid(rs.getString("email_id"));
				student.setAddress(rs.getString("address"));
				student.setPassword(rs.getString("password"));
				student.setTotal_subjects(rs.getInt("total_subjects"));
				student.setClassId(rs.getString("classid"));
				
				EditUserInfoPanel.user = student;
				EditUserInfoPanel.user.setUserType(student.getUserType());
				EditUserInfoPanel.user.setId(student.getId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			ResultSet rs = new  StudentDb().getStudentSubjectInfo(student.getId());
			Map<Integer, String> retrievedSubjectList = new HashMap<>();
			
			while(rs.next())
			{
				retrievedSubjectList.put(rs.getInt("subjectid"), rs.getString("subject_name"));
			}
			student.setSubjectList(retrievedSubjectList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		pEditStudent.setLayout(infoPanel);
		pEditStudent.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pEditStudent.add(userInfoPanel.createEditUserInfoPanel(), "UserInfo");
		pEditStudent.add(studentInfoPanel.createStudentInfoPanel(), "StudentClassInfo");
		pEditStudent.add(userLoginPanel.createUserLoginInfoPanel(), "UserLoginInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		EditStudent_IF.pInputError.add(error);
		pInputError.setPreferredSize(new Dimension(300, 80));
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bNext.setPreferredSize(bPrevious.getPreferredSize());
		bSubmit.setPreferredSize(bPrevious.getPreferredSize());
		pButtons.add(bPrevious);
		pButtons.add(bSubmit);
		pButtons.add(bNext);
		
		editStudent_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		editStudent_IF.setLayout(new BorderLayout());
		editStudent_IF.add(pEditStudent, BorderLayout.NORTH);
		editStudent_IF.add(pInputError, BorderLayout.CENTER);
		editStudent_IF.add(pButtons, BorderLayout.PAGE_END);
		editStudent_IF.setVisible(true);
		
		bPrevious.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						panelCount--;		
						switch(panelCount)
						{
							case 0: 
								infoPanel.show(pEditStudent, "UserInfo");
								break;
							case 1:
								infoPanel.show(pEditStudent, "StudentClassInfo");
								break;
							case 2:
								infoPanel.show(pEditStudent, "UserLoginInfo");
								break;
							default:
								if(panelCount < 0)
									panelCount = 0;
								else if(panelCount > 2)
									panelCount = 2;
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
								infoPanel.show(pEditStudent, "UserInfo");
								break;
							case 1:
								infoPanel.show(pEditStudent, "StudentClassInfo");
								break;
							case 2:
								infoPanel.show(pEditStudent, "UserLoginInfo");
								break;
							default:
								if(panelCount < 0)
									panelCount = 0;
								else if(panelCount > 2)
									panelCount = 2;
								break;
						}
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
							EditStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							EditStudent_IF.error.setText("Address cannot be blank");
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
							EditStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							EditStudent_IF.error.setText("No subjects selected");
						}

						if(isInputError())
						{
							JOptionPane.showMessageDialog(null, "Oops something went wrong...");
						}
						else
						{
							try
							{
								student.setId(student.getId());
								student.setUserid(student.getUserid());
								student.setUserType(student.getUserType());
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
								student.setSubjectList(EditStudent_IF.student.getSubjectList());

								student.setPassword(Arrays.toString(userLoginPanel.tfPassword.getPassword()));
								
								if(new StudentDb().updateStudent())
									JOptionPane.showMessageDialog(null, "Student updated successfully");
								else
									JOptionPane.showMessageDialog(null, "Oops make sure all fields are added");
							}
							catch(Exception error)
							{
								JOptionPane.showMessageDialog(null, "Oops some information is not filled");
								error.printStackTrace();
							}
						}
					}
				});
		
		return editStudent_IF;
	}
}
