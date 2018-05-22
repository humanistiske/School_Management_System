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
import com.school.database.AttendanceDb;
import com.school.database.DbUtils;
import com.school.elements.Attendance;

public class AttendancePanel extends JPanel
{
	final JPanel pAttendanceView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable attendanceTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final Attendance selectAttendance = new Attendance();

	public JPanel createAttendancePanel()
	{
		pAttendanceView.setBackground(Color.white);
		pAttendanceView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pAttendanceView.add(pSearch, BorderLayout.NORTH);
		attendanceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		attendanceTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		attendanceTable.setModel(DbUtils.viewAttendanceTableModel(new AttendanceDb().selectAttendanceInfoDb()));	
		pAttendanceView.add(new JScrollPane(attendanceTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pAttendanceView.add(pButtons, BorderLayout.SOUTH);

		attendanceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectAttendance.setUserid(Integer.parseInt(attendanceTable.getValueAt(attendanceTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectAttendance.getUserid());
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
					attendanceTable.setModel(DbUtils.viewAttendanceTableModel(new AttendanceDb().searchAttendanceInfoDb(id, "", "")));
				}
				else if(tfSearch.getText().contains(" "))
				{
					String fName = tfSearch.getText().substring(0, tfSearch.getText().indexOf(" "));
					String lName = tfSearch.getText().substring(tfSearch.getText().indexOf(" "));
					attendanceTable.setModel(DbUtils.viewAttendanceTableModel(new AttendanceDb().searchAttendanceInfoDb(0, fName, lName)));
				}
				else
				{
					String fName = tfSearch.getText();
					String lName = tfSearch.getText();
					attendanceTable.setModel(DbUtils.viewAttendanceTableModel(new AttendanceDb().searchAttendanceInfoDb(0, fName, lName)));
				}
			}
		});

		return pAttendanceView;
	}
}

