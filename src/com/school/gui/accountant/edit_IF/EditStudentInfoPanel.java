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

import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.UserType;
import com.school.accounts.staff.SalaryStatus;
import com.school.accounts.staff.StaffAccount;
import com.school.database.ClassDb;
import com.school.database.StudentDb;
import com.school.database.SubjectSelectDb;
import com.school.database.TeacherDb;
import com.school.gui.admin.edit_IF.EditStudent_IF;
import com.school.users.Student;

public class EditStudentInfoPanel 
{
	protected class StudentAccountInfoPanel
	{
		private final Integer[] DUE_DATE_MONTHS = {0, 1, 2, 3, 4, 5}; 
		
		final JPanel pUpdateAccount = new JPanel();
		final JPanel pSearch = new JPanel();
		public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JButton bSearch = new JButton("Search");
		final JLabel lClassid = new JLabel("Class ID: ");
		final JLabel lTotalFees = new JLabel("Total Fees: ");
		final JLabel lPaidFees = new JLabel("Paid Amount: ");
		final JLabel lOutstandingFees = new JLabel("Outstanding Amount: ");
		final JLabel lDueDate = new JLabel("Due Date: ");
		final JLabel lLastPaymentDate = new JLabel("Last Payment Date: ");
		final JComboBox cbClassId = new JComboBox(new ClassDb().getClassList().toArray());
		final JTextField tfTotalFees = new JTextField(15);
		final JTextField tfPaidFees = new JTextField(15);
		final JTextField tfOutstandingFees = new JTextField(15);
		final JTextField tfDueDate = new JTextField(15);
		final JComboBox cbDueDateMonths = new JComboBox(DUE_DATE_MONTHS);
		final JTextField tfLastPaymentDate = new JTextField(15);
		final JPanel pAddStudentAccountInfo = new JPanel();
		final TitledBorder border = new TitledBorder("Student Account Information");
		
		public JPanel createStudentAccountPanel()
		{
			pSearch.setPreferredSize(new Dimension(800, 40));
			pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
			pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
			pSearch.add(tfSearch);
			pSearch.add(bSearch);
			
			pAddStudentAccountInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddStudentAccountInfo.setBorder(border);
			pAddStudentAccountInfo.setBackground(Color.WHITE);
			
			tfTotalFees.setName("Total Fees");
			tfPaidFees.setName("Paid Fees");
			tfOutstandingFees.setName("Outstanding Fees");
			tfDueDate.setName("Due Date");
			tfLastPaymentDate.setName("Last Payment Date");
			
			tfLastPaymentDate.setEditable(false);
			tfLastPaymentDate.setText(LocalDate.now().toString());
			tfDueDate.setEditable(false);
			
			bSearch.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					EditStudent_IF.student = new Student(Integer.parseInt(tfSearch.getText()));
					EditStudent_IF.student.setUserType(UserType.STUDENT);
			
					try
					{
						ResultSet rs = new StudentDb().getStudentAccountInfo(EditStudent_IF.student.getId());
						
						while(rs.next())
						{
							EditStudent_IF.student.setClassId(rs.getString("classid"));
							EditStudent_IF.student.setTotalFees(rs.getDouble("total_fees"));
							EditStudent_IF.student.setPaidFees(rs.getDouble("paid_fees"));
							EditStudent_IF.student.setOutstandingFees(rs.getDouble("outstanding_fees"));
							EditStudent_IF.student.setDueDate(LocalDate.now());
							EditStudent_IF.student.setLastPaymentDate(LocalDate.now());
						}
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
					
					cbClassId.setSelectedItem(String.valueOf(EditStudent_IF.student.getClassId()));
					tfTotalFees.setText(String.valueOf(EditStudent_IF.student.getTotalFees()));
					tfPaidFees.setText(String.valueOf(EditStudent_IF.student.getPaidFees()));
					tfOutstandingFees.setText(String.valueOf(EditStudent_IF.student.getOutstandingFees()));
					tfDueDate.setText(String.valueOf(EditStudent_IF.student.getDueDate()));
					tfLastPaymentDate.setText(String.valueOf(EditStudent_IF.student.getLastPaymentDate()));
				}		
			});
			
			
			
			cbDueDateMonths.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							LocalDate date = LocalDate.now();
							date = date.plusMonths((int)cbDueDateMonths.getSelectedItem());
							tfDueDate.setText(date.toString());
						}
					});
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddStudentAccountInfo.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddStudentAccountInfo.add(lClassid, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddStudentAccountInfo.add(cbClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddStudentAccountInfo.add(lTotalFees, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddStudentAccountInfo.add(tfTotalFees, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddStudentAccountInfo.add(lPaidFees, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddStudentAccountInfo.add(tfPaidFees, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pAddStudentAccountInfo.add(lOutstandingFees, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddStudentAccountInfo.add(tfOutstandingFees, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddStudentAccountInfo.add(lLastPaymentDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddStudentAccountInfo.add(tfLastPaymentDate, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			pAddStudentAccountInfo.add(lDueDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 5;
			pAddStudentAccountInfo.add(cbDueDateMonths, gbc);
			gbc.gridx = 4;
			gbc.gridy = 5;
			pAddStudentAccountInfo.add(tfDueDate, gbc);
			
			pUpdateAccount.setLayout(new BorderLayout());
			pUpdateAccount.add(pSearch, BorderLayout.NORTH);
			pUpdateAccount.add(pAddStudentAccountInfo, BorderLayout.CENTER);
			
			return pUpdateAccount;
		}
	}
}
