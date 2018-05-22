package com.school.gui.add_IF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import com.school.SystemUtils;
import com.school.database.ClassDb;
import com.school.database.SubjectSelectDb;
import com.school.gui.InputValidators.DateVerifier;
import com.school.gui.InputValidators.PaymentAmountFilter;
import com.school.gui.InputValidators.JCompInputVerifier;
import com.school.elements.*;

public class AddTeacherInfoPanel 
{
	private final PaymentAmountFilter numberError = new PaymentAmountFilter();
	protected static int qualificationDocValue = 0;
	
	protected class TeacherInfoPanel
	{	
		final SubjectSelectDb subjectDb = new SubjectSelectDb();
		
		final JLabel lDesignation = new JLabel("Designation: ");
		final JLabel lHighestQualification = new JLabel("Highest Qualification: ");
		final JLabel lHighestQualificationDoc = new JLabel("Highest Qualification Document: ");
		final JLabel lTeacherSubjectDist = new JLabel("Teaching Subjects: ");
		final JLabel lClassId = new JLabel("Class ID: ");
		final JLabel lTotalSubjects = new JLabel("Total Subjects: ");
		final JComboBox cbDesignation = new JComboBox(new StaffDesignation().getStaffDesignations().toArray());
		final JTextField tfHighestQualification = new JTextField(15);
		final JTextField tfHighestQualificationDoc = new JTextField(15);
		final JButton bBrowse = new JButton("Browse");
		final JComboBox cbClassId = new JComboBox(new ClassDb().getClassList().toArray());
		final JList jlSubjectSelection = new JList();
		final JList jlSubjectSelected = new JList();
		final JButton bAddSubjects = new JButton(">>");
		final JButton bRemoveSubjects = new JButton("<<");
		final JPanel pButtons = new JPanel();
		final JTextField tfTotalSubjects = new JTextField(15);
		final TitledBorder border = new TitledBorder("Teacher Subject Distribution");
		protected final Map<Integer, String> selectedSubjectList = new HashMap<Integer, String>();
		protected final Map<Integer, String> classSubjectList = new HashMap<Integer, String>();
		final DefaultListModel<String> listSubjectModel = new DefaultListModel<>();
		final DefaultListModel<String> listSelectedSubjectModel = new DefaultListModel<>();
        final JList<String> jlAvailableSubjectList = new JList<>(listSubjectModel);
        final JList<String> jlSelectedSubjectList = new JList<>(listSelectedSubjectModel);
        final JPanel pAddTeacherInfo = new JPanel();
        
        protected File highestQualificationDoc = new File("");
        
		public JPanel createTeacherInfoPanel()
		{
			pAddTeacherInfo.setBackground(Color.WHITE);
			pAddTeacherInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddTeacherInfo.setBorder(border);
			cbClassId.setSelectedIndex(-1);
			tfTotalSubjects.setEditable(false);
			tfHighestQualificationDoc.setEditable(false);
			
			//setting names for textField for inputVerifiers
			cbClassId.setName("Class ID");
			tfHighestQualification.setName("Highest Qualification");
			
			JScrollPane scPaneList = new JScrollPane(jlAvailableSubjectList);
			scPaneList.setPreferredSize(new Dimension(180, 140));
			scPaneList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneSelected = new JScrollPane(jlSelectedSubjectList);
			scPaneSelected.setPreferredSize(new Dimension(180, 140));
			scPaneSelected.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			cbClassId.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							listSubjectModel.clear();
							
							for(String subject : subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()).values())
					        {
								
								listSubjectModel.addElement(subject);
					        }
						}
					});
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
								AddTeacherInfoPanel.qualificationDocValue = qualificationDocValue;
								highestQualificationDoc = file.getSelectedFile();
								tfHighestQualificationDoc.setText(highestQualificationDoc.toString());
							}
						}
					});
			bAddSubjects.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							for(String subject : jlAvailableSubjectList.getSelectedValuesList())
							{
								if(selectedSubjectList.containsValue(subject) && 
										classSubjectList.containsKey(SystemUtils.getKeyFromValue
												(subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()), subject))) {}
								else
								{
									//puts subject into subject list for display
									selectedSubjectList.put(
											(Integer)SystemUtils.getKeyFromValue(
													subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()), subject), subject);
									listSelectedSubjectModel.clear();
									//puts subject into subject list for class subject dist
									classSubjectList.put(
											(Integer)SystemUtils.getKeyFromValue(
													subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()), subject), 
											cbClassId.getSelectedItem().toString());
									
									for(String selectedSubject : selectedSubjectList.values())
							        {
										listSelectedSubjectModel.addElement(selectedSubject);
							        }
								}
							}
							AddTeacher_IF.teacher.setClassSubjectList(classSubjectList);
							tfTotalSubjects.setText(String.valueOf(selectedSubjectList.size()));
						}
					});
			bRemoveSubjects.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					for(String subject : jlAvailableSubjectList.getSelectedValuesList())
					{
						if(selectedSubjectList.containsValue(subject))
						{
							selectedSubjectList.remove(
									(Integer)SystemUtils.getKeyFromValue(
											subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()), subject), subject);
							listSelectedSubjectModel.clear();
							//puts subject into subject list for class subject dist
							classSubjectList.remove(
									(Integer)SystemUtils.getKeyFromValue(
											subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()), subject), 
									cbClassId.getSelectedItem().toString());
							
							for(String selectedSubject : selectedSubjectList.values())
					        {
								listSelectedSubjectModel.addElement(selectedSubject);
					        }
						}
						else {}
					}
					AddTeacher_IF.teacher.setClassSubjectList(classSubjectList);
					tfTotalSubjects.setText(String.valueOf(selectedSubjectList.size()));
				}
			});
			

			
			pButtons.setLayout(new GridLayout(2, 0));
			pButtons.add(bAddSubjects);
			pButtons.add(bRemoveSubjects);
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddTeacherInfo.setLayout(gridBag);

			
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddTeacherInfo.add(lDesignation, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddTeacherInfo.add(cbDesignation, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddTeacherInfo.add(lHighestQualification, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddTeacherInfo.add(tfHighestQualification, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddTeacherInfo.add(lHighestQualificationDoc, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddTeacherInfo.add(tfHighestQualificationDoc, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pAddTeacherInfo.add(bBrowse, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pAddTeacherInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddTeacherInfo.add(cbClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddTeacherInfo.add(lTeacherSubjectDist, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddTeacherInfo.add(scPaneList, gbc);
			gbc.gridx = 4;
			gbc.gridy = 4;
			pAddTeacherInfo.add(pButtons, gbc);
			gbc.gridx = 5;
			gbc.gridy = 4;
			pAddTeacherInfo.add(scPaneSelected, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			pAddTeacherInfo.add(lTotalSubjects, gbc);
			gbc.gridx = 3;
			gbc.gridy = 5;
			pAddTeacherInfo.add(tfTotalSubjects, gbc);
			
			return pAddTeacherInfo;
		}
	}
	
	protected class TeacherAccountInfoPanel
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
		final JPanel pAddTeacherAccountInfo = new JPanel();
		final TitledBorder border = new TitledBorder("Teacher Account Information");
		
		public JPanel createTeacherAccountPanel()
		{
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
			pAddTeacherAccountInfo.add(lDateOfJoining, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddTeacherAccountInfo.add(tfDateOfJoining, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddTeacherAccountInfo.add(lContractEndDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddTeacherAccountInfo.add(cbContractEndMonths, gbc);
			gbc.gridx = 4;
			gbc.gridy = 4;
			pAddTeacherAccountInfo.add(tfContractEndDate, gbc);
			
			return pAddTeacherAccountInfo;
		}
	}
}
