package com.school.gui.admin.edit_IF;

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

public class EditTeacherInfoPanel 
{
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
		final JButton bEditSubjects = new JButton(">>");
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
        final JPanel pEditTeacherInfo = new JPanel();
        
        protected File highestQualificationDoc = new File("");
        
		public JPanel createTeacherInfoPanel()
		{
			pEditTeacherInfo.setBackground(Color.WHITE);
			pEditTeacherInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pEditTeacherInfo.setBorder(border);
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
			
			//filling up form with retrieved data
			tfHighestQualification.setText(EditTeacher_IF.teacher.getHighest_qualification());
			cbClassId.setSelectedItem("1A");
	
			listSelectedSubjectModel.clear();		
			for(String subject : EditTeacher_IF.teacher.getSubjectList().values())
			{
				selectedSubjectList.put(
						Integer.parseInt(SystemUtils.getKeyFromValue(EditTeacher_IF.teacher.getSubjectList(), subject).toString()),
						subject);
				listSelectedSubjectModel.addElement(subject);
			}
			listSubjectModel.clear();
			for(String subject : subjectDb.getSubjectList(cbClassId.getSelectedItem().toString()).values())
	        {
				listSubjectModel.addElement(subject);
	        }
			tfTotalSubjects.setText(String.valueOf(selectedSubjectList.size()));
			
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
								EditTeacherInfoPanel.qualificationDocValue = qualificationDocValue;
								highestQualificationDoc = file.getSelectedFile();
								tfHighestQualificationDoc.setText(highestQualificationDoc.toString());
							}
						}
					});
			bEditSubjects.addActionListener(new ActionListener()
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
							EditTeacher_IF.teacher.setClassSubjectList(classSubjectList);
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
					EditTeacher_IF.teacher.setClassSubjectList(classSubjectList);
					tfTotalSubjects.setText(String.valueOf(selectedSubjectList.size()));
				}
			});
		
			pButtons.setLayout(new GridLayout(2, 0));
			pButtons.add(bEditSubjects);
			pButtons.add(bRemoveSubjects);
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pEditTeacherInfo.setLayout(gridBag);
			
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pEditTeacherInfo.add(lDesignation, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pEditTeacherInfo.add(cbDesignation, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pEditTeacherInfo.add(lHighestQualification, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pEditTeacherInfo.add(tfHighestQualification, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pEditTeacherInfo.add(lHighestQualificationDoc, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pEditTeacherInfo.add(tfHighestQualificationDoc, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pEditTeacherInfo.add(bBrowse, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pEditTeacherInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pEditTeacherInfo.add(cbClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pEditTeacherInfo.add(lTeacherSubjectDist, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pEditTeacherInfo.add(scPaneList, gbc);
			gbc.gridx = 4;
			gbc.gridy = 4;
			pEditTeacherInfo.add(pButtons, gbc);
			gbc.gridx = 5;
			gbc.gridy = 4;
			pEditTeacherInfo.add(scPaneSelected, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			pEditTeacherInfo.add(lTotalSubjects, gbc);
			gbc.gridx = 3;
			gbc.gridy = 5;
			pEditTeacherInfo.add(tfTotalSubjects, gbc);
			
			return pEditTeacherInfo;
		}
	}
}
