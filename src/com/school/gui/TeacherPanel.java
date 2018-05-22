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
import com.school.database.TeacherDb;
import com.school.users.Student;
import com.school.users.Teacher;

public class TeacherPanel extends JPanel
{
	final JPanel pTeacherView = new JPanel();
	
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable teacherTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final Teacher selectTeacher = new Teacher(0);

		
	public JPanel createTeacherPanel()
	{
		pTeacherView.setBackground(Color.white);
		pTeacherView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pTeacherView.add(pSearch, BorderLayout.NORTH);
		teacherTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		teacherTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		teacherTable.setModel(DbUtils.viewTeacherTableModel(new TeacherDb().selectTeacherInfoDb()));
		pTeacherView.add(new JScrollPane(teacherTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pTeacherView.add(pButtons, BorderLayout.SOUTH);

		teacherTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectTeacher.setUserid(teacherTable.getValueAt(teacherTable.getSelectedRow(), 0).toString().substring(1));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectTeacher.getUserid());
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
					teacherTable.setModel(DbUtils.viewTeacherTableModel(new TeacherDb().searchTeacherInfoDb(id, "", "")));
				}
				else if(tfSearch.getText().contains(" "))
				{
					String fName = tfSearch.getText().substring(0, tfSearch.getText().indexOf(" "));
					String lName = tfSearch.getText().substring(tfSearch.getText().indexOf(" "));
					teacherTable.setModel(DbUtils.viewTeacherTableModel(new TeacherDb().searchTeacherInfoDb(0, fName, lName)));
				}
				else
				{
					String fName = tfSearch.getText();
					String lName = tfSearch.getText();
					teacherTable.setModel(DbUtils.viewTeacherTableModel(new TeacherDb().searchTeacherInfoDb(0, fName, lName)));
				}
			}
		});

		return pTeacherView;
	}
}
