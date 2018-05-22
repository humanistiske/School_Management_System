package com.school.gui.add_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.database.DatabaseConnect;
import com.school.database.ExamDb;
import com.school.database.ExamReportDb;
import com.school.database.TeacherDb;
import com.school.elements.ExamReport;
import com.school.gui.SystemWindowSetup;
import com.school.gui.accountant.edit_IF.EditStaff_IF;
import com.school.gui.add_IF.AddTeacherInfoPanel.TeacherAccountInfoPanel;
import com.school.gui.add_IF.AddTeacherInfoPanel.TeacherInfoPanel;
import com.school.gui.add_IF.AddUserInfoPanel.AddUserLoginInfoPanel;
import com.school.users.Teacher;
import com.school.users.User;

public class AddExamReport_IF 
{
	ExamReportPanel studentReport;
	final JInternalFrame addExamReport_IF = new JInternalFrame("Add Exam Report", true, true, true, true);
	final JPanel pExamReport = new JPanel(); 
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bSubmit = new JButton("Submit");
	final JPanel pButtons = new JPanel();
	final ExamDb examDb = new ExamDb();
	public static final Map<Integer, ExamReport> selectedStudentReportList = new HashMap<Integer, ExamReport>();
	final List<Integer> listExams = examDb.getExamList();
	private static int examid;
	public static ExamReport examReport;
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddExamReport_IF.inputError = inputError;
	}
	
	public static int getExamid() {
		return examid;
	}

	public static void setExamid(int examid) {
		AddExamReport_IF.examid = examid;
	}

	public JInternalFrame addExamReport_IF()
	{
		studentReport = new ExamReportPanel();
		pExamReport.setLayout(infoPanel);
		pExamReport.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pExamReport.add(studentReport.createStudentReportPanel(), "StudentReport");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		AddTeacher_IF.pInputError.add(error);
		pInputError.setPreferredSize(new Dimension(300, 80));
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		pButtons.add(bSubmit);
		
		addExamReport_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addExamReport_IF.setLayout(new BorderLayout());
		addExamReport_IF.add(pExamReport, BorderLayout.CENTER);
		addExamReport_IF.add(pButtons, BorderLayout.PAGE_END);
		addExamReport_IF.setVisible(true);
	
		bSubmit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(isInputError())
						{
							JOptionPane.showMessageDialog(null, "Oops something went wrong...");
						}
						else
						{
							
						}
					}
				});
		
		return addExamReport_IF;
	}
	
	protected class ExamReportPanel
	{ 	
		final JLabel lExamid = new JLabel("Exam ID: ");
		final JLabel lUserid = new JLabel("Student ID: ");
		final JLabel lMarks = new JLabel("Marks: ");
		final JLabel lGrade = new JLabel("Grade: ");
		final JLabel lAnswerSheet = new JLabel("Answer Sheet: ");
		final JComboBox cbExamid = new JComboBox(listExams.toArray());
		protected final List<String> studentList = new ArrayList<>();
		final DefaultListModel<String> studentListModel = new DefaultListModel<>();
        final JList<String> jlStudentList = new JList<>(studentListModel);
        final JTextField tfMarks = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
        final JTextField tfGrade = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
        final JTextField tfAnswerSheet = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
        final JButton bBrowse = new JButton("Browse");
        final JButton bAdd = new JButton(">>");
        final JPanel pStudentReport = new JPanel(new FlowLayout(FlowLayout.LEFT));
		protected final List<String> listGrades = new ArrayList<>();
		protected final List<Integer> listMarks = new ArrayList<>();
		final DefaultListModel<String> selectedStudentListModel = new DefaultListModel<>();
        final DefaultListModel<String> listGradesModel = new DefaultListModel<>();
		final DefaultListModel<Integer> listMarksModel = new DefaultListModel<>();
        final JList<String> jlSelectedStudentList = new JList<>(selectedStudentListModel);
        final JList<String> jlGrades = new JList<>(listGradesModel);
        final JList<Integer> jlMarks = new JList<>(listMarksModel);
        final JLabel lSelectedSubjectTime = new JLabel("Student Marks: ");
        File answerSheetFile = new File("");
        final JPanel pStudentExamReport = new JPanel();
		
		final TitledBorder border = new TitledBorder("Student Exam Report");
		
		public JPanel createStudentReportPanel()
		{
			pStudentExamReport.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pStudentExamReport.setBorder(border);
			pStudentExamReport.setBackground(Color.WHITE);
			
			tfAnswerSheet.setEditable(false);
			tfGrade.setEditable(false);
			
			JScrollPane scPaneList = new JScrollPane(jlStudentList);
			scPaneList.setPreferredSize(new Dimension(180, 140));
			scPaneList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			JScrollPane scPaneUserid = new JScrollPane(jlSelectedStudentList);
			scPaneUserid.setPreferredSize(new Dimension(180, 80));
			scPaneUserid.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneMarks = new JScrollPane(jlMarks);
			scPaneMarks.setPreferredSize(new Dimension(180, 140));
			scPaneMarks.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneGrades = new JScrollPane(jlGrades);
			scPaneGrades.setPreferredSize(new Dimension(180, 140));
			scPaneGrades.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			cbExamid.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
					    studentListModel.clear();
					    for(Integer student : examDb.getExamStudentList(
					    		(Integer.parseInt(cbExamid.getSelectedItem().toString()))))
					    	{
					    		studentListModel.addElement("S" + student);
					    	}
					    setExamid(Integer.parseInt(cbExamid.getSelectedItem().toString()));
						}
					});
			bAdd.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							for(String student : jlStudentList.getSelectedValuesList())
							{
								int studentid = Integer.parseInt(student.substring(1));
								if(selectedStudentReportList.containsKey(studentid)) 
								{
									selectedStudentReportList.replace(studentid, 
											new ExamReport(Integer.parseInt(tfMarks.getText()),
														tfGrade.getText(),
														new File(tfAnswerSheet.getText())));
									
									int index = selectedStudentListModel.indexOf(student);
									listMarksModel.set(index, selectedStudentReportList.get(studentid).getMarks());
									listGradesModel.set(index, selectedStudentReportList.get(studentid).getGrade());
								}
								else
								{
									selectedStudentReportList.put(studentid, 
											new ExamReport(Integer.parseInt(tfMarks.getText()),
														tfGrade.getText(),
														new File(tfAnswerSheet.getText())));
									
									selectedStudentListModel.clear();
									listMarksModel.clear();
									listGradesModel.clear();
									for(Map.Entry<Integer, ExamReport> studentReport : selectedStudentReportList.entrySet())
									{
										selectedStudentListModel.addElement("S" + studentReport.getKey());
										listMarksModel.addElement(studentReport.getValue().getMarks());
										listGradesModel.addElement(studentReport.getValue().getGrade());
									}
								}
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
					FileFilter documentFilter = new FileNameExtensionFilter(
							"Images and documents only", SystemUtils.DOC_FILE_FORMATS);
					file.setFileFilter(documentFilter);
					int idFileValue = file.showOpenDialog(null);
					if(idFileValue == JFileChooser.APPROVE_OPTION)
					{
						AddUserInfoPanel.idFileValue = idFileValue;
						answerSheetFile = file.getSelectedFile();
						tfAnswerSheet.setText(answerSheetFile.toString());
					}
					
					if(Integer.parseInt(tfMarks.getText()) >= 90)
						tfGrade.setText("A+");
					else if(Integer.parseInt(tfMarks.getText()) >= 80 & Integer.parseInt(tfMarks.getText()) < 90)
						tfGrade.setText("A");
					else if(Integer.parseInt(tfMarks.getText()) >= 70 & Integer.parseInt(tfMarks.getText()) < 80)
						tfGrade.setText("B+");
					else if(Integer.parseInt(tfMarks.getText()) >= 60 & Integer.parseInt(tfMarks.getText()) < 70)
						tfGrade.setText("B");
					else if(Integer.parseInt(tfMarks.getText()) >= 50 & Integer.parseInt(tfMarks.getText()) < 60)
						tfGrade.setText("C+");
					else if(Integer.parseInt(tfMarks.getText()) >= 40 & Integer.parseInt(tfMarks.getText()) < 50)
						tfGrade.setText("C");
					else if(Integer.parseInt(tfMarks.getText()) >= 30 & Integer.parseInt(tfMarks.getText()) < 40)
						tfGrade.setText("D");
					else
						tfGrade.setText("F");
				}		
			});
			bSubmit.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							if(new ExamReportDb().addExamReport())
							{
								JOptionPane.showMessageDialog(null, "Exam report added successfully");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Oops something went wrong...");
							}
						}
					});
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pStudentExamReport.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.BOTH;
			pStudentExamReport.add(lExamid, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pStudentExamReport.add(cbExamid, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pStudentExamReport.add(lUserid, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pStudentExamReport.add(scPaneList, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pStudentExamReport.add(lMarks, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pStudentExamReport.add(tfMarks, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pStudentExamReport.add(lGrade, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pStudentExamReport.add(tfGrade, gbc);
			gbc.gridx = 6;
			gbc.gridy = 2;
			pStudentExamReport.add(lAnswerSheet, gbc);
			gbc.gridx = 6;
			gbc.gridy = 3;
			pStudentExamReport.add(tfAnswerSheet, gbc);
			gbc.gridx = 7;
			gbc.gridy = 3;
			pStudentExamReport.add(bBrowse, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pStudentExamReport.add(bAdd, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			pStudentExamReport.add(scPaneUserid, gbc);
			gbc.gridx = 3;
			gbc.gridy = 5;
			pStudentExamReport.add(scPaneMarks, gbc);
			gbc.gridx = 6;
			gbc.gridy = 5;
			pStudentExamReport.add(scPaneGrades, gbc);
	
			
			return pStudentExamReport;
		}
	}
}
