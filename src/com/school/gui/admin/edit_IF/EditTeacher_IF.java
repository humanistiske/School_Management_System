package com.school.gui.admin.edit_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.school.database.TeacherDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.admin.edit_IF.EditTeacherInfoPanel.TeacherInfoPanel;
import com.school.gui.admin.edit_IF.EditUserInfoPanel.EditUserLoginInfoPanel;
import com.school.users.Teacher;

public class EditTeacher_IF 
{
	private EditUserInfoPanel userInfoPanel = new EditUserInfoPanel();
	private EditUserLoginInfoPanel userLoginPanel = userInfoPanel.new EditUserLoginInfoPanel();
	private EditTeacherInfoPanel addTeacherInfoPanel = new EditTeacherInfoPanel();
	private TeacherInfoPanel teacherInfoPanel = addTeacherInfoPanel.new TeacherInfoPanel();
	
	final JInternalFrame addTeacher_IF = new JInternalFrame("Edit Teacher", true, true, true, true);
	final JPanel pEditTeacher = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Update");
	final JPanel pButtons = new JPanel();
	public static int id;
	public static Teacher teacher;
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		EditTeacher_IF.inputError = inputError;
	}

	public JInternalFrame editTeacher_IF()
	{
		//user, class and login info
		try
		{
			//filling retrieved data info
			ResultSet rs = new  TeacherDb().getTeacherInfo(teacher.getId());
			while(rs.next())
			{
				teacher.setfName(rs.getString("fname"));
				teacher.setlName(rs.getString("lname"));
				teacher.setPhone(rs.getLong("phone"));
				teacher.setAltPhone(rs.getLong("alt_phone"));
				teacher.setEmailid(rs.getString("email_id"));
				teacher.setAddress(rs.getString("address"));
				teacher.setPassword(rs.getString("password"));
				teacher.setDesignation(rs.getString("designation"));
				teacher.setHighest_qualification(rs.getString("highest_qualification"));

				EditUserInfoPanel.user = teacher;
				EditUserInfoPanel.user.setUserType(teacher.getUserType());
				EditUserInfoPanel.user.setId(teacher.getId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			ResultSet rs = new TeacherDb().getTeacherSubjectInfo(teacher.getId());
			Map<Integer, String> retrievedSubjectList = new HashMap<>();
			Map<Integer, String> retrievedClassSubjectList = new HashMap<>();
			
			while(rs.next())
			{
				retrievedSubjectList.put(rs.getInt("subjectid"), rs.getString("subject_name"));
				retrievedClassSubjectList.put(rs.getInt("subjectid"), rs.getString("classid"));
			}
			teacher.setSubjectList(retrievedSubjectList);
			teacher.setClassSubjectList(retrievedClassSubjectList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		pEditTeacher.setLayout(infoPanel);
		pEditTeacher.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pEditTeacher.add(userInfoPanel.createEditUserInfoPanel(), "UserInfo");
		pEditTeacher.add(teacherInfoPanel.createTeacherInfoPanel(), "TeacherClassInfo");		
		pEditTeacher.add(userLoginPanel.createUserLoginInfoPanel(), "UserLoginInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		EditTeacher_IF.pInputError.add(error);
		pInputError.setPreferredSize(new Dimension(300, 80));
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bNext.setPreferredSize(bPrevious.getPreferredSize());
		bSubmit.setPreferredSize(bPrevious.getPreferredSize());
		pButtons.add(bPrevious);
		pButtons.add(bSubmit);
		pButtons.add(bNext);
		
		addTeacher_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addTeacher_IF.setLayout(new BorderLayout());
		addTeacher_IF.add(pEditTeacher, BorderLayout.NORTH);
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
								infoPanel.show(pEditTeacher, "UserInfo");
								break;
							case 1:
								infoPanel.show(pEditTeacher, "TeacherClassInfo");
								break;
							case 2:
								infoPanel.show(pEditTeacher, "UserLoginInfo");
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
								infoPanel.show(pEditTeacher, "UserInfo");
								break;
							case 1:
								infoPanel.show(pEditTeacher, "TeacherClassInfo");
								break;
							case 2:
								infoPanel.show(pEditTeacher, "UserLoginInfo");
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
							EditTeacher_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							EditTeacher_IF.error.setText("Address cannot be blank");
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
							EditTeacher_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
									BorderFactory.createLineBorder(Color.red),
									BorderFactory.createEmptyBorder(0, 10, 0, 0)));
							EditTeacher_IF.error.setText("Qualification documents not uploaded");
						}

						if(isInputError())
						{
							JOptionPane.showMessageDialog(null, "Oops something went wrong...");
						}
						else
						{
							try
							{
								teacher.setId(teacher.getId());
								teacher.setUserid(teacher.getUserid());
								teacher.setUserType(teacher.getUserType());
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
								teacher.setClassSubjectList(teacherInfoPanel.classSubjectList);
								
								teacher.setPassword(userLoginPanel.tfPassword.getPassword().toString());
								
								if(new TeacherDb().updateTeacher())
									JOptionPane.showMessageDialog(null, "Teacher information updated successfully");
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


