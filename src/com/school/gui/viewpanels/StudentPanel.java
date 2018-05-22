package com.school.gui.viewpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.database.StudentDb;
import com.school.gui.LoginPanel;
import com.school.gui.MainWindowSetup;
import com.school.gui.admin.edit_IF.EditStudent_IF;
import com.school.gui.admin.edit_IF.EditUserInfoPanel;
import com.school.users.Student;

public class StudentPanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pStudentView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable studentTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final Student selectStudent = new Student(0);
	
	public JPanel createStudentPanel()
	{
		pStudentView.setBackground(Color.white);
		pStudentView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pStudentView.add(pSearch, BorderLayout.NORTH);
		studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		studentTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		studentTable.setModel(DbUtils.viewStudentTableModel(new StudentDb().selectStudentInfoDb()));
		pStudentView.add(new JScrollPane(studentTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		//add edit button only if logged in user is admin
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pButtons.add(bEdit);
		}
		pStudentView.add(pButtons, BorderLayout.SOUTH);
		
		studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
				{
					@Override
					public void valueChanged(ListSelectionEvent e) 
					{
						selectStudent.setUserid(studentTable.getValueAt(studentTable.getSelectedRow(), 0).toString());
						selectStudent.setId(Integer.parseInt(studentTable.getValueAt(studentTable.getSelectedRow(), 0).toString().substring(1)));
					}
				});
		bEdit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						EditStudent_IF editStudent = new EditStudent_IF();
						EditStudent_IF.student = new Student(selectStudent.getUserid(), selectStudent.getId(), UserType.STUDENT); 
						EditStudent_IF.id = selectStudent.getId();
						
						MainWindowSetup.JDPsystemSetup.add(editStudent.editStudent_IF(), JLayeredPane.POPUP_LAYER);
					}
				});
		bSearch.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(tfSearch.getText().matches("[0-9]+"))
						{
							Integer id = Integer.parseInt(tfSearch.getText());
							studentTable.setModel(DbUtils.viewStudentTableModel(new StudentDb().searchStudentInfoDb(id, "", "")));
						}
						else if(tfSearch.getText().contains(" "))
						{
							String fName = tfSearch.getText().substring(0, tfSearch.getText().indexOf(" "));
							String lName = tfSearch.getText().substring(tfSearch.getText().indexOf(" "));
							studentTable.setModel(DbUtils.viewStudentTableModel(new StudentDb().searchStudentInfoDb(0, fName, lName)));
						}
						else
						{
							String fName = tfSearch.getText();
							String lName = tfSearch.getText();
							studentTable.setModel(DbUtils.viewStudentTableModel(new StudentDb().searchStudentInfoDb(0, fName, lName)));
						}
					}
				});
		
		return pStudentView;
	}
}
