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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.database.AttendanceDb;
import com.school.elements.Attendance;
import com.school.gui.LoginPanel;

public class AttendancePanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pAttendanceView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable attendanceTable = new JTable();
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
		//add edit button only if logged in user is admin
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pAttendanceView.add(pSearch, BorderLayout.NORTH);
		}
		else if(LoginPanel.getUserLogin().getUserType().equals(UserType.TEACHER)) 
		{
			pAttendanceView.add(pSearch, BorderLayout.NORTH);
		}
		attendanceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		attendanceTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.STUDENT))
		{
			attendanceTable.setModel(DbUtils.viewAttendanceTableModel(new AttendanceDb().
					searchAttendanceInfoDb(LoginPanel.getUserLogin().getId(), "", "")));
		}
		else
		{
			System.out.println(LoginPanel.getUserLogin().getUserType());
			attendanceTable.setModel(DbUtils.viewAttendanceTableModel(new AttendanceDb().selectAttendanceInfoDb()));	
		}
		pAttendanceView.add(new JScrollPane(attendanceTable), BorderLayout.CENTER);

		attendanceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectAttendance.setUserid(Integer.parseInt(attendanceTable.getValueAt(attendanceTable.getSelectedRow(), 0).toString().substring(1)));
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

