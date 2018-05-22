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

import com.school.database.StudentDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.accountant.edit_IF.EditStudentInfoPanel.StudentAccountInfoPanel;
import com.school.users.Student;

public final class EditStudent_IF 
{	 
	private EditStudentInfoPanel addStudentInfoPanel = new EditStudentInfoPanel();
	private StudentAccountInfoPanel studentAccountInfoPanel = addStudentInfoPanel.new StudentAccountInfoPanel();
	final JInternalFrame editStudent_IF = new JInternalFrame("Edit Student", true, true, true, true);
	final JPanel pEditStudent = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bSubmit = new JButton("Update");
	final JPanel pButtons = new JPanel();
	public static Student student = new Student(0);
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
		pEditStudent.setLayout(infoPanel);
		pEditStudent.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pEditStudent.add(studentAccountInfoPanel.createStudentAccountPanel(), "StudentAccountInfo");
		
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
		pButtons.add(bSubmit);
		
		editStudent_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		editStudent_IF.setLayout(new BorderLayout());
		final JPanel pStudentAccount = new JPanel();
		pStudentAccount.setLayout(new BorderLayout());
		pStudentAccount.add(pEditStudent, BorderLayout.CENTER);
		pStudentAccount.add(pInputError, BorderLayout.SOUTH);
		editStudent_IF.add(pStudentAccount, BorderLayout.CENTER);
		editStudent_IF.add(pButtons, BorderLayout.PAGE_END);
		editStudent_IF.setVisible(true);
		
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
								student.setClassId(String.valueOf(studentAccountInfoPanel.cbClassId.getSelectedItem().toString()));
								student.setTotalFees(Double.parseDouble(studentAccountInfoPanel.tfTotalFees.getText()));
								student.setPaidFees(Double.parseDouble(studentAccountInfoPanel.tfPaidFees.getText()));
								student.setOutstandingFees(Double.parseDouble(studentAccountInfoPanel.tfOutstandingFees.getText()));
								student.setLastPaymentDate(LocalDate.parse(studentAccountInfoPanel.tfLastPaymentDate.getText()));
								student.setDueDate(LocalDate.parse(studentAccountInfoPanel.tfDueDate.getText()));
							
								if(new StudentDb().updateStudentAccountDb())
									JOptionPane.showMessageDialog(null, "Student account updated successfully");
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
		
		return editStudent_IF;
	}
}
