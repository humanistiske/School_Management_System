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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.GUIInterfaceUtilities;
import com.school.accounts.staff.StaffAccount;
import com.school.database.DbUtils;
import com.school.database.StaffAccountDb;

public class StaffAccountPanel extends JPanel
{
	private static final int TEMP_ID = 2; 
	
	final JPanel pStaffAccountView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable staffAccountTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final StaffAccount selectStaffAccount = new StaffAccount(0);

	public JPanel createStaffAccountPanel()
	{
		pStaffAccountView.setBackground(Color.white);
		pStaffAccountView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pStaffAccountView.add(pSearch, BorderLayout.NORTH);
		staffAccountTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		staffAccountTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		staffAccountTable.setModel(DbUtils.viewStaffAccountTableModel(new StaffAccountDb().searchStaffAccountInfoDb(TEMP_ID)));
		pStaffAccountView.add(new JScrollPane(staffAccountTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pStaffAccountView.add(pButtons, BorderLayout.SOUTH);

		staffAccountTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectStaffAccount.setId(Integer.parseInt(staffAccountTable.getValueAt(staffAccountTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectStaffAccount.getUserid());
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
					staffAccountTable.setModel(DbUtils.viewStaffAccountTableModel(new StaffAccountDb().searchStaffAccountInfoDb(id)));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Oops invalid input");
				}
			}
		});

		return pStaffAccountView;
	}
}
