package com.school.gui.add_IF;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import com.school.SystemUtils;
import com.school.database.SubjectSelectDb;
import com.school.elements.StaffDesignation;
import com.school.gui.InputValidators.DateVerifier;
import com.school.gui.InputValidators.JCompInputVerifier;
import com.school.gui.InputValidators.PaymentAmountFilter;

public class AddAdminInfoPanel 
{
	private final PaymentAmountFilter numberError = new PaymentAmountFilter();
	protected static int qualificationDocValue = 0;
	
	protected class AdminInfoPanel
	{	
		final SubjectSelectDb subjectDb = new SubjectSelectDb();
		
		final JLabel lDesignation = new JLabel("Designation: ");
		final JLabel lHighestQualification = new JLabel("Highest Qualification: ");
		final JLabel lHighestQualificationDoc = new JLabel("Highest Qualification Document: ");
		final JLabel lAdminSubjectDist = new JLabel("Teaching Subjects: ");
		final JLabel lClassId = new JLabel("Class ID: ");
		final JLabel lTotalSubjects = new JLabel("Total Subjects: ");
		final JComboBox cbDesignation = new JComboBox(new StaffDesignation().getStaffDesignations().toArray());
		final JTextField tfHighestQualification = new JTextField(15);
		final JTextField tfHighestQualificationDoc = new JTextField(15);
		final JButton bBrowse = new JButton("Browse");
		final TitledBorder border = new TitledBorder("Admin Employee Information");
        final JPanel pAddAdminInfo = new JPanel();
        
        protected File highestQualificationDoc = new File("");
        
		public JPanel createAdminInfoPanel()
		{
			pAddAdminInfo.setBackground(Color.WHITE);
			pAddAdminInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddAdminInfo.setBorder(border);
			cbDesignation.setSelectedIndex(2);
			tfHighestQualificationDoc.setEditable(false);
			
			//setting names for textField for inputVerifiers
			tfHighestQualification.setName("Highest Qualification");
			
			bBrowse.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							JFileChooser file = new JFileChooser();
							file.setAcceptAllFileFilterUsed(false);
							FileFilter filter = new FileNameExtensionFilter("Images and documents Only", SystemUtils.DOC_FILE_FORMATS);
							file.setFileFilter(filter);
							int qualificationDocValue = file.showOpenDialog(null);
							if(qualificationDocValue == JFileChooser.APPROVE_OPTION)
							{
								AddAdminInfoPanel.qualificationDocValue = qualificationDocValue;
								highestQualificationDoc = file.getSelectedFile();
								tfHighestQualificationDoc.setText(highestQualificationDoc.toString());
							}
						}
					});

			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddAdminInfo.setLayout(gridBag);

			
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddAdminInfo.add(lDesignation, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddAdminInfo.add(cbDesignation, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddAdminInfo.add(lHighestQualification, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddAdminInfo.add(tfHighestQualification, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddAdminInfo.add(lHighestQualificationDoc, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddAdminInfo.add(tfHighestQualificationDoc, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pAddAdminInfo.add(bBrowse, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			
			return pAddAdminInfo;
		}
	}
	
	protected class AdminAccountInfoPanel
	{
		private final Integer[] CONTRACT_MONTHS = {6, 12, 18, 24, 30, 36}; 
		
		final JLabel lTotalSalary = new JLabel("Total Salary: ");
		final JLabel lInhandSalary = new JLabel("In Hand Amount: ");
		final JLabel lAllowance = new JLabel("Allowance: ");
		final JLabel lDateOfJoining = new JLabel("Date Of Joining: ");
		final JLabel lContractEndDate = new JLabel("Contract End Date: ");
		final JTextField tfTotalSalary = new JTextField(15);
		final JTextField tfInhandSalary = new JTextField(15);
		final JTextField tfAllowance = new JTextField(15);
		final JTextField tfDateOfJoining = new JTextField(15);
		final JComboBox cbContractEndMonths = new JComboBox(CONTRACT_MONTHS);
		final JTextField tfContractEndDate = new JTextField(15);
		final JPanel pAddAdminAccountInfo = new JPanel();
		final TitledBorder border = new TitledBorder("Admin Account Information");
		
		public JPanel createAdminAccountPanel()
		{
			pAddAdminAccountInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddAdminAccountInfo.setBorder(border);
			pAddAdminAccountInfo.setBackground(Color.WHITE);
			
			tfTotalSalary.setName("Total Salary");
			tfInhandSalary.setName("In Hand Amount");
			tfAllowance.setName("Allowance");
			tfDateOfJoining.setName("Date Of Joining");
			tfContractEndDate.setName("Contract End Date");
			tfTotalSalary.setInputVerifier(new JCompInputVerifier());
			tfInhandSalary.setInputVerifier(new JCompInputVerifier());
			tfAllowance.setInputVerifier(new JCompInputVerifier());
			tfDateOfJoining.setInputVerifier(new DateVerifier());
			tfContractEndDate.setInputVerifier(new DateVerifier());
			((AbstractDocument)tfTotalSalary.getDocument()).setDocumentFilter(numberError);
			((AbstractDocument)tfInhandSalary.getDocument()).setDocumentFilter(numberError);
			((AbstractDocument)tfAllowance.getDocument()).setDocumentFilter(numberError);
			
			tfContractEndDate.setEditable(false);
			tfDateOfJoining.setText(LocalDate.now().toString());
			tfDateOfJoining.setEditable(false);
			
			cbContractEndMonths.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							LocalDate date = LocalDate.now();
							date = date.plusMonths((int)cbContractEndMonths.getSelectedItem());
							tfContractEndDate.setText(date.toString());
						}
					});
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddAdminAccountInfo.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddAdminAccountInfo.add(lTotalSalary, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddAdminAccountInfo.add(tfTotalSalary, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddAdminAccountInfo.add(lInhandSalary, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddAdminAccountInfo.add(tfInhandSalary, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddAdminAccountInfo.add(lAllowance, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddAdminAccountInfo.add(tfAllowance, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pAddAdminAccountInfo.add(lDateOfJoining, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddAdminAccountInfo.add(tfDateOfJoining, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddAdminAccountInfo.add(lContractEndDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddAdminAccountInfo.add(cbContractEndMonths, gbc);
			gbc.gridx = 4;
			gbc.gridy = 4;
			pAddAdminAccountInfo.add(tfContractEndDate, gbc);
			
			return pAddAdminAccountInfo;
		}
	}
}
