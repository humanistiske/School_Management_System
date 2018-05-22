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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.accounts.student.StudentAccount;
import com.school.database.StudentAccountDb;
import com.school.gui.LoginPanel;

public class StudentAccountPanel extends JPanel
{
	private static final int TEMP_ID = 1; 
	public static int panelCount = 0;
	final JPanel pStudentAccountView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable studentAccountTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final StudentAccount selectStudentAccount = new StudentAccount(0);

	public JPanel createStudentAccountPanel()
	{
		pStudentAccountView.setBackground(Color.white);
		pStudentAccountView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ACCOUNTANT))
		{
			pStudentAccountView.add(pSearch, BorderLayout.NORTH);
		}
		studentAccountTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		studentAccountTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		studentAccountTable.setModel(DbUtils.viewStudentAccountTableModel(
				new StudentAccountDb().searchStudentAccountInfoDb(LoginPanel.getUserLogin().getId())));
		pStudentAccountView.add(new JScrollPane(studentAccountTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pStudentAccountView.add(pButtons, BorderLayout.SOUTH);

		studentAccountTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectStudentAccount.setUserId(Integer.parseInt(studentAccountTable.getValueAt(studentAccountTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectStudentAccount.getUserId());
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
					studentAccountTable.setModel(DbUtils.viewStudentAccountTableModel(new StudentAccountDb().searchStudentAccountInfoDb(id)));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Oops invalid input");
				}
			}
		});

		return pStudentAccountView;
	}
}
