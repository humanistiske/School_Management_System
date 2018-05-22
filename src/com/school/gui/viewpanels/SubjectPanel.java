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
import com.school.database.SubjectDb;
import com.school.database.StudentDb;
import com.school.elements.Subject;
import com.school.gui.LoginPanel;
import com.school.gui.MainWindowSetup;
import com.school.gui.admin.edit_IF.EditSubject_IF;
import com.school.gui.admin.edit_IF.EditTeacher_IF;
import com.school.users.Teacher;

public class SubjectPanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pSubjectView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable subjectTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	Subject selectSubject = new Subject(0);

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
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pButtons.add(bEdit);
		}
		pSubjectView.add(pButtons, BorderLayout.SOUTH);

		subjectTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectSubject = new Subject(Integer.parseInt(subjectTable.getValueAt(subjectTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				EditSubject_IF editSubject = new EditSubject_IF();
				EditSubject_IF.subject = new Subject(selectSubject.getSubjectId());
				
				MainWindowSetup.JDPsystemSetup.add(editSubject.editSubject_IF(), JLayeredPane.POPUP_LAYER);
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

