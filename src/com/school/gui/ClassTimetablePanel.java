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
import com.school.database.ClassTimetableDb;
import com.school.database.DbUtils;
import com.school.elements.ClassTimetable;

public class ClassTimetablePanel extends JPanel
{
	final JPanel pClassTimetableView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable classTimetableTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final ClassTimetable selectClassTimetable = new ClassTimetable(0);

	public JPanel createClassTimetablePanel()
	{
		pClassTimetableView.setBackground(Color.white);
		pClassTimetableView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pClassTimetableView.add(pSearch, BorderLayout.NORTH);
		classTimetableTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		classTimetableTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		classTimetableTable.setModel(DbUtils.viewClassTimetableTableModel(new ClassTimetableDb().selectClassTimetableInfoDb()));
		pClassTimetableView.add(new JScrollPane(classTimetableTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pClassTimetableView.add(pButtons, BorderLayout.SOUTH);

		classTimetableTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectClassTimetable.setClassId(classTimetableTable.getValueAt(classTimetableTable.getSelectedRow(), 0).toString());
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectClassTimetable.getClassId());
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
					classTimetableTable.setModel(DbUtils.viewClassTimetableTableModel(new ClassTimetableDb().searchClassTimetableInfoDb(id, "")));
				}
				else
				{
					String classid = tfSearch.getText();
					classTimetableTable.setModel(DbUtils.viewClassTimetableTableModel(new ClassTimetableDb().searchClassTimetableInfoDb(0, classid)));
				}
			}
		});

		return pClassTimetableView;
	}
}

