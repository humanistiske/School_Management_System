package com.school.gui.accountant.edit_IF;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.UserType;
import com.school.accounts.staff.SalaryStatus;
import com.school.accounts.staff.StaffAccount;
import com.school.database.ClassDb;
import com.school.database.SubjectSelectDb;
import com.school.database.TeacherDb;
import com.school.gui.InputValidators.DateVerifier;
import com.school.gui.InputValidators.JCompInputVerifier;
import com.school.users.Teacher;

public class EditStaffInfoPanel 
{
	protected class TeacherAccountInfoPanel
	{
		private final Integer[] CONTRACT_MONTHS = {6, 12, 18, 24, 30, 36}; 
		
		final JPanel pUpdateAccount = new JPanel();
		final JPanel pSearch = new JPanel();
		public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JButton bSearch = new JButton("Search");
		final JLabel lTotalSalary = new JLabel("Total Salary: ");
		final JLabel lInhandSalary = new JLabel("In Hand Amount: ");
		final JLabel lAllowance = new JLabel("Allowance: ");
		final JLabel lSalaryStatus = new JLabel("Salary Status: ");
		final JLabel lDateOfJoining = new JLabel("Date Of Joining: ");
		final JLabel lContractEndDate = new JLabel("Contract End Date: ");
		final JTextField tfTotalSalary = new JTextField(15);
		final JTextField tfInhandSalary = new JTextField(15);
		final JTextField tfAllowance = new JTextField(15);
		final JComboBox cbSalaryStatus = new JComboBox(SalaryStatus.values());
		final JTextField tfDateOfJoining = new JTextField(15);
		final JTextField tfContractEndDate = new JTextField(15);
		final JPanel pAddTeacherAccountInfo = new JPanel();
		final TitledBorder border = new TitledBorder("Staff Account Information");
		
		public JPanel createTeacherAccountPanel()
		{
			pSearch.setPreferredSize(new Dimension(800, 40));
			pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
			pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
			pSearch.add(tfSearch);
			pSearch.add(bSearch);
			
			pAddTeacherAccountInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddTeacherAccountInfo.setBorder(border);
			pAddTeacherAccountInfo.setBackground(Color.WHITE);
			
			tfTotalSalary.setName("Total Salary");
			tfInhandSalary.setName("In Hand Amount");
			tfAllowance.setName("Allowance");
			tfDateOfJoining.setName("Date Of Joining");
			tfContractEndDate.setName("Contract End Date");
			
			tfDateOfJoining.setEditable(false);
						
			bSearch.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							EditStaff_IF.staff = new StaffAccount(Integer.parseInt(tfSearch.getText()));
							EditStaff_IF.staff.setUserType(UserType.TEACHER);
					
							try
							{
								ResultSet rs = new TeacherDb().getTeacherAccountInfo(EditStaff_IF.staff.getId());
								
								while(rs.next())
								{
									EditStaff_IF.staff.setTotalSalary(rs.getDouble("total_salary"));
									EditStaff_IF.staff.setAllowance(rs.getDouble("allowance"));
									EditStaff_IF.staff.setInHandSalary(rs.getDouble("in_hand_salary"));
									EditStaff_IF.staff.setSalaryStatusId(rs.getInt("salarystatusid"));
									EditStaff_IF.staff.setDateOfJoining(LocalDate.parse(rs.getString("date_of_joining").substring(0, 10)));
									EditStaff_IF.staff.setContractEndDate(LocalDate.parse(rs.getString("contract_end_date").substring(0, 10)));
								}
							}
							catch(Exception e1)
							{
								e1.printStackTrace();
							}
							
							tfTotalSalary.setText(String.valueOf(EditStaff_IF.staff.getTotalSalary()));
							tfInhandSalary.setText(String.valueOf(EditStaff_IF.staff.getInHandSalary()));
							tfAllowance.setText(String.valueOf(EditStaff_IF.staff.getAllowance()));
							tfDateOfJoining.setText(String.valueOf(EditStaff_IF.staff.getDateOfJoining()));
							tfContractEndDate.setText(String.valueOf(EditStaff_IF.staff.getContractEndDate()));
							if(EditStaff_IF.staff.getSalaryStatusId() == 1)
								cbSalaryStatus.setSelectedItem(SalaryStatus.UNPAID);
							else
								cbSalaryStatus.setSelectedItem(SalaryStatus.PAID);
						}		
					});
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddTeacherAccountInfo.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddTeacherAccountInfo.add(lTotalSalary, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddTeacherAccountInfo.add(tfTotalSalary, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddTeacherAccountInfo.add(lInhandSalary, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddTeacherAccountInfo.add(tfInhandSalary, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddTeacherAccountInfo.add(lAllowance, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddTeacherAccountInfo.add(tfAllowance, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pAddTeacherAccountInfo.add(lSalaryStatus, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddTeacherAccountInfo.add(cbSalaryStatus, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddTeacherAccountInfo.add(lContractEndDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddTeacherAccountInfo.add(tfContractEndDate, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			pAddTeacherAccountInfo.add(lDateOfJoining, gbc);
			gbc.gridx = 3;
			gbc.gridy = 5;
			pAddTeacherAccountInfo.add(tfDateOfJoining, gbc);
			
			pUpdateAccount.setLayout(new BorderLayout());
			pUpdateAccount.add(pSearch, BorderLayout.NORTH);
			pUpdateAccount.add(pAddTeacherAccountInfo, BorderLayout.CENTER);
			
			return pUpdateAccount;
		}
	}
}
