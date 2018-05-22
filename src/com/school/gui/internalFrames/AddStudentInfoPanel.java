package com.school.gui.internalFrames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

import com.school.SystemUtils;
import com.school.database.ClassDb;
import com.school.database.SubjectSelectDb;
import com.school.gui.InputValidators.DateVerifier;
import com.school.gui.InputValidators.PaymentAmountFilter;
import com.school.gui.InputValidators.JCompInputVerifier;

public class AddStudentInfoPanel 
{
	private final PaymentAmountFilter feesError = new PaymentAmountFilter();

	protected class StudentInfoPanel
	{
		private final SubjectSelectDb subjectDb = new SubjectSelectDb();
		
		final JLabel lClassId = new JLabel("Class ID: ");
		final JLabel lTotalSubjects = new JLabel("Total Subjects: ");
		final JLabel lSubjectSelection = new JLabel("Select Subjects: ");
		final JComboBox cbClassId = new JComboBox(new ClassDb().getClassList().toArray()); 
		final JList jlSubjectSelection = new JList();
		final JList jlSubjectSelected = new JList();
		final JTextField tfTotalSubjects = new JTextField(15);
		final JButton bAddSubjects = new JButton(">>");
		final JButton bRemoveSubjects = new JButton("<<");
		final JPanel pButtons = new JPanel();
		final TitledBorder border = new TitledBorder("Student Class Information");
		protected final Map<Integer, String> selectedSubjectList = new HashMap<Integer, String>();
		final DefaultListModel<String> listSubjectModel = new DefaultListModel<>();
		final DefaultListModel<String> listSelectedSubjectModel = new DefaultListModel<>();
        final JList<String> jlAvailableSubjectList = new JList<>(listSubjectModel);
        final JList<String> jlSelectedSubjectList = new JList<>(listSelectedSubjectModel);
        final JPanel pAddStudentInfo = new JPanel();
        
		public JPanel createStudentInfoPanel()
		{
			pAddStudentInfo.setBackground(Color.WHITE);
			pAddStudentInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pAddStudentInfo.setBorder(border);
			cbClassId.setSelectedIndex(-1);
			
			tfTotalSubjects.setEditable(false);
			((JTextField)cbClassId.getEditor().getEditorComponent()).setInputVerifier(new JCompInputVerifier());;
			
			
			//setting names for textField for inputVerifiers
			cbClassId.setName("Class ID");
			tfTotalSubjects.setName("Total Subjects");  
			
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
							AddStudent_IF.student.setClassId(cbClassId.getSelectedItem().toString());
							for(String subject : subjectDb.getSubjectList(AddStudent_IF.student.getClassId()).values())
					        {
								listSubjectModel.addElement(subject);
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
								if(selectedSubjectList.containsValue(subject)) {}
								else
								{
									selectedSubjectList.put(
											(Integer)SystemUtils.getKeyFromValue(
													subjectDb.getSubjectList(AddStudent_IF.student.getClassId()), subject), subject);
									listSelectedSubjectModel.clear();
									for(String selectedSubject : selectedSubjectList.values())
							        {
										listSelectedSubjectModel.addElement(selectedSubject);
							        }
								}
							}
							AddStudent_IF.student.setSubjectList(selectedSubjectList);
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
											subjectDb.getSubjectList(AddStudent_IF.student.getClassId()), subject), subject);
							listSelectedSubjectModel.clear();
							for(String selectedSubject : selectedSubjectList.values())
					        {
								listSelectedSubjectModel.addElement(selectedSubject);
					        }
						}
						else {}
					}
					AddStudent_IF.student.setSubjectList(selectedSubjectList);
					tfTotalSubjects.setText(String.valueOf(selectedSubjectList.size()));
				}
			});
			

			
			pButtons.setLayout(new GridLayout(2, 0));
			pButtons.add(bAddSubjects);
			pButtons.add(bRemoveSubjects);
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pAddStudentInfo.setLayout(gridBag);

			
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pAddStudentInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddStudentInfo.add(cbClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddStudentInfo.add(lTotalSubjects, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddStudentInfo.add(tfTotalSubjects, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddStudentInfo.add(lSubjectSelection, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddStudentInfo.add(scPaneList, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pAddStudentInfo.add(pButtons, gbc);
			gbc.gridx = 5;
			gbc.gridy = 2;
			pAddStudentInfo.add(scPaneSelected, gbc);
			
			return pAddStudentInfo;
		}
	}
	
	protected class StudentAccountInfoPanel
	{
		private final Integer[] DUE_DATE_MONTHS = {0, 1, 2, 3, 4, 5}; 
		
		final JLabel lTotalFees = new JLabel("Total Fees: ");
		final JLabel lPaidFees = new JLabel("Paid Amount: ");
		final JLabel lOutstandingFees = new JLabel("Outstanding Amount: ");
		final JLabel lDueDate = new JLabel("Due Date: ");
		final JLabel lLastPaymentDate = new JLabel("Last Payment Date: ");
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
			tfTotalFees.setInputVerifier(new JCompInputVerifier());
			tfPaidFees.setInputVerifier(new JCompInputVerifier());
			tfOutstandingFees.setInputVerifier(new JCompInputVerifier());
			tfDueDate.setInputVerifier(new DateVerifier());
			tfLastPaymentDate.setInputVerifier(new DateVerifier());
			((AbstractDocument)tfTotalFees.getDocument()).setDocumentFilter(feesError);
			((AbstractDocument)tfPaidFees.getDocument()).setDocumentFilter(feesError);
			((AbstractDocument)tfOutstandingFees.getDocument()).setDocumentFilter(feesError);
			
			tfLastPaymentDate.setEditable(false);
			tfLastPaymentDate.setText(LocalDate.now().toString());
			tfDueDate.setEditable(false);
			
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
			pAddStudentAccountInfo.add(lTotalFees, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pAddStudentAccountInfo.add(tfTotalFees, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pAddStudentAccountInfo.add(lPaidFees, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pAddStudentAccountInfo.add(tfPaidFees, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pAddStudentAccountInfo.add(lOutstandingFees, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pAddStudentAccountInfo.add(tfOutstandingFees, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pAddStudentAccountInfo.add(lLastPaymentDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pAddStudentAccountInfo.add(tfLastPaymentDate, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pAddStudentAccountInfo.add(lDueDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pAddStudentAccountInfo.add(cbDueDateMonths, gbc);
			gbc.gridx = 4;
			gbc.gridy = 4;
			pAddStudentAccountInfo.add(tfDueDate, gbc);
			
			return pAddStudentAccountInfo;
		}
	}
}
