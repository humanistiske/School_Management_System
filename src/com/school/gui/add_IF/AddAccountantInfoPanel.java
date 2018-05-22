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

public class AddAccountantInfoPanel 
{
	private final PaymentAmountFilter numberError = new PaymentAmountFilter();
	protected static int qualificationDocValue = 0;
	
	protected class AccountantInfoPanel
	{	
		final SubjectSelectDb subjectDb = new SubjectSelectDb();
		
		final JLabel lDesignation = new JLabel("Designation: ");
		final JLabel lHighestQualification = new JLabel("Highest Qualification: ");
		final JLabel lHighestQualificationDoc = new JLabel("Highest Qualification Document: ");
		final JLabel lAccountantSubjectDist = new JLabel("Teaching Subjects: ");
		final JLabel lClassId = new JLabel("Class ID: ");
		final JLabel lTotalSubjects = new JLabel("Total Subjects: ");
		final JComboBox cbDesignation = new JComboBox(new StaffDesignation().getStaffDesignations().toArray());
		final JTextField tfHighestQualification = new JTextField(15);
		final JTextField tfHighestQualificationDoc = new JTextField(15);
		final JButton bBrowse = new JButton("Browse");
		final TitledBorder border = new TitledBorder("Accountant Employee Information");
        final JPanel pAddAccountantInfo = new JPanel();
        
        protected File highestQualificationDoc = new File("");
        
		public JPanel createAccountantInfoPanel()
		{
			pAddAccountantInfo.setBackground(Color.WHITE);
			pAddAccountantInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddAccountantInfo.setBorder(border);
			cbDesignation.setSelectedIndex(1);
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
								AddAccountantInfoPanel.qualificationDocValue = qualificationDocValue;
								highestQualificationDoc = file.getSelectedFile();
								tfHighestQualificationDoc.setText(highestQualificationDoc.toString());
							}
						}
					});

			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddAccountantInfo.setLayout(gridBag);

			
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddAccountantInfo.add(lDesignation, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddAccountantInfo.add(cbDesignation, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddAccountantInfo.add(lHighestQualification, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddAccountantInfo.add(tfHighestQualification, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddAccountantInfo.add(lHighestQualificationDoc, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddAccountantInfo.add(tfHighestQualificationDoc, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pAddAccountantInfo.add(bBrowse, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			
			return pAddAccountantInfo;
		}
	}
	
	protected class AccountantAccountInfoPanel
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
		final JPanel pAddAccountantAccountInfo = new JPanel();
		final TitledBorder border = new TitledBorder("Accountant Account Information");
		
		public JPanel createAccountantAccountPanel()
		{
			pAddAccountantAccountInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddAccountantAccountInfo.setBorder(border);
			pAddAccountantAccountInfo.setBackground(Color.WHITE);
			
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
			pAddAccountantAccountInfo.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddAccountantAccountInfo.add(lTotalSalary, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddAccountantAccountInfo.add(tfTotalSalary, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddAccountantAccountInfo.add(lInhandSalary, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddAccountantAccountInfo.add(tfInhandSalary, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddAccountantAccountInfo.add(lAllowance, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddAccountantAccountInfo.add(tfAllowance, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pAddAccountantAccountInfo.add(lDateOfJoining, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddAccountantAccountInfo.add(tfDateOfJoining, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddAccountantAccountInfo.add(lContractEndDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddAccountantAccountInfo.add(cbContractEndMonths, gbc);
			gbc.gridx = 4;
			gbc.gridy = 4;
			pAddAccountantAccountInfo.add(tfContractEndDate, gbc);
			
			return pAddAccountantAccountInfo;
		}
	}
}
