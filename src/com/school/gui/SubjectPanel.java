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
import com.school.database.SubjectDb;
import com.school.database.DbUtils;
import com.school.database.StudentDb;
import com.school.elements.Subject;

public class SubjectPanel extends JPanel
{
	final JPanel pSubjectView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable subjectTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final Subject selectSubject = new Subject(0);

	public JPanel createSubjectPanel()
	{
		pSubjectView.setBackground(Color.white);
		pSubjectView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pSubjectView.add(pSearch, BorderLayout.NORTH);
		subjectTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		subjectTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		subjectTable.setModel(DbUtils.viewSubjectTableModel(new SubjectDb().selectSubjectInfoDb()));
		pSubjectView.add(new JScrollPane(subjectTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pSubjectView.add(pButtons, BorderLayout.SOUTH);

		subjectTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectSubject.setSubjectId(Integer.parseInt(subjectTable.getValueAt(subjectTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectSubject.getSubjectId());
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
					subjectTable.setModel(DbUtils.viewSubjectTableModel(new SubjectDb().searchSubjectInfoDb(id, "")));
				}
				else
				{
					String subjectName = tfSearch.getText();
					subjectTable.setModel(DbUtils.viewSubjectTableModel(new SubjectDb().searchSubjectInfoDb(0, subjectName)));
				}			
			}
		});

		return pSubjectView;
	}
}

