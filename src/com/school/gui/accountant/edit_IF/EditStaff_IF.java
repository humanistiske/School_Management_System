package com.school.gui.accountant.edit_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.school.accounts.staff.StaffAccount;
import com.school.database.StaffAccountDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.accountant.edit_IF.EditStaffInfoPanel.TeacherAccountInfoPanel;

public final class EditStaff_IF 
{	 
	private EditStaffInfoPanel addStaffInfoPanel = new EditStaffInfoPanel();
	private TeacherAccountInfoPanel staffAccountInfoPanel = addStaffInfoPanel.new TeacherAccountInfoPanel();
	final JInternalFrame editTeacher_IF = new JInternalFrame("Edit Staff", true, true, true, true);
	final JPanel pEditStaff = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bSubmit = new JButton("Update");
	final JPanel pButtons = new JPanel();
	public static StaffAccount staff;
	public static int id;
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		EditStaff_IF.inputError = inputError;
	}

	public JInternalFrame editStaff_IF()
	{	
		pEditStaff.setLayout(infoPanel);
		pEditStaff.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pEditStaff.add(staffAccountInfoPanel.createTeacherAccountPanel(), "TeacherAccountInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		EditStaff_IF.pInputError.add(error);
		pInputError.setPreferredSize(new Dimension(300, 80));
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		pButtons.add(bSubmit);
		
		editTeacher_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		editTeacher_IF.setLayout(new BorderLayout());
		final JPanel pTeacherAccount = new JPanel();
		pTeacherAccount.setLayout(new BorderLayout());
		pTeacherAccount.add(pEditStaff, BorderLayout.CENTER);
		pTeacherAccount.add(pInputError, BorderLayout.SOUTH);
		editTeacher_IF.add(pTeacherAccount, BorderLayout.CENTER);
		editTeacher_IF.add(pButtons, BorderLayout.PAGE_END);
		editTeacher_IF.setVisible(true);
		
		bSubmit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(isInputError())
						{
							JOptionPane.showMessageDialog(null, "Oops something went wrong...");
						}
						else
						{
							try
							{
									
								if(new StaffAccountDb().updateStaffAccount())
									JOptionPane.showMessageDialog(null, "Staff account updated successfully");
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
		
		return editTeacher_IF;
	}
}
