package com.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.GUIInterfaceUtilities;
import com.school.database.DbUtils;
import com.school.database.StudentDb;
import com.school.users.Student;

public class StudentPanel extends JPanel
{
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
		pButtons.add(bEdit);
		pStudentView.add(pButtons, BorderLayout.SOUTH);
		
		studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
				{
					@Override
					public void valueChanged(ListSelectionEvent e) 
					{
						selectStudent.setUserid(studentTable.getValueAt(studentTable.getSelectedRow(), 0).toString().substring(1));
					}
				});
		bEdit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(selectStudent.getUserid());
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
