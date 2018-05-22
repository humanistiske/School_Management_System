package com.school.gui.internalFrames;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.school.database.ExamDb;
import com.school.database.SubjectSelectDb;
import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.database.ClassroomDb;
import com.school.database.DatabaseConnect;
import com.school.elements.Exam;
import com.school.elements.Exam.ExamStatus;
import com.school.elements.Subject;
import com.school.gui.SystemWindowSetup;
import com.school.gui.InputValidators.DateVerifier;

public class AddExam_IF 
{
	public final static Exam exam = new Exam(DatabaseConnect.getId("exam")); 
	public final AddExamInfoPanel addExamInfoPanel = new AddExamInfoPanel();
	public final JPanel pExamInfo = new JPanel();
	final JInternalFrame addExam_IF = new JInternalFrame("Add Exam", true, true, true, true);
	
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	int panelCount = 0;
	
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddExam_IF.inputError = inputError;
	}
	
	public JInternalFrame addExam_IF()
	{
		final JPanel errorLogButtonsPanel = new JPanel();
		errorLogButtonsPanel.setLayout(new BorderLayout());
		pExamInfo.setLayout(infoPanel);
		pExamInfo.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pExamInfo.add(addExamInfoPanel.createAddExamInfoPanel(), "ExamInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		pInputError.setPreferredSize(new Dimension(300, 60));
		error.setForeground(Color.RED);
		AddExam_IF.pInputError.add(error);
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bSubmit.setPreferredSize(bRefresh.getPreferredSize());
		pButtons.add(bRefresh);
		pButtons.add(bSubmit);

		addExam_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addExam_IF.setLayout(new BorderLayout());
		addExam_IF.add(pExamInfo, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pInputError, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pButtons, BorderLayout.SOUTH);
		addExam_IF.add(errorLogButtonsPanel, BorderLayout.SOUTH);
		
		addExam_IF.setVisible(true);

		bRefresh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				exam.setExamid(DatabaseConnect.getId("exam"));
				addExamInfoPanel.tfExamId.setText(String.valueOf(exam.getExamid()));
			}
		});
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
					exam.setExamid(Integer.parseInt(addExamInfoPanel.tfExamId.getText()));
					exam.setDate(LocalDate.parse(addExamInfoPanel.tfDate.getText()));
					exam.setExamLocation(Integer.parseInt(addExamInfoPanel.cbClassrooms.getSelectedItem().toString()));
					exam.setDuration(Double.parseDouble(addExamInfoPanel.sDurationHour.getValue().toString() 
									+ "." + addExamInfoPanel.sDurationMinute.getValue().toString()));
					exam.setMarks(Integer.parseInt(addExamInfoPanel.tfMarks.getText()));
					exam.setExamStaus(ExamStatus.valueOf(addExamInfoPanel.tfExamStatus.getText()));
					if(addExamInfoPanel.cbTime.getSelectedItem().toString().equalsIgnoreCase("AM"))
					{
						String minute = addExamInfoPanel.sStartTimeMinute.getValue().toString();
						String hour = addExamInfoPanel.sStartTimeHour.getValue().toString();
						if(Integer.parseInt(minute) <= 9)
						{
							minute = "0" + minute;
						}
						if(Integer.parseInt(hour) <= 9)
						{
							hour = "0" + hour;
						}
						exam.setTime(hour + ":" + minute + ":00 AM");
					}
					else
					{
						exam.setTime(Integer.parseInt(addExamInfoPanel.sStartTimeHour.getValue().toString()
								+ addExamInfoPanel.sStartTimeHour.getValue().toString()) 
								+ ":" 
								+ addExamInfoPanel.sStartTimeMinute.getValue().toString() + ":00 PM");
					}
					
					try
					{
						if(new ExamDb().addExam())
							JOptionPane.showMessageDialog(null, "Exam added successfully");
						else
							JOptionPane.showMessageDialog(null, "Oops make sure all fields are correct");
					}
					catch(Exception e1)
					{
						System.out.println(e1);
					}
				}
			}
		});
		
		return addExam_IF;
	}
	
	protected class AddExamInfoPanel 
	{
		final JPanel pExamInfo = new JPanel();
		final JLabel lExamId = new JLabel("Exam ID: ");
		final JLabel lClassId = new JLabel("Class ID: ");
		final JLabel lSubjectName = new JLabel("Subject Name: ");
		final JLabel lDateTime = new JLabel("Date & Time: ");
		final JLabel lExamLocation = new JLabel("Exam Location: ");
		final JLabel lDuration = new JLabel("Exam Duration: ");
		final JLabel lMarks = new JLabel("Marks: ");
		final JLabel lExamStatus = new JLabel("Exam Status: ");
		final JTextField tfExamId = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JComboBox cbClassId = new JComboBox(DatabaseConnect.getClassList("class").toArray());
		Map<Integer, String> listSubjects = new HashMap<>(); 
		final JComboBox cbSubjects = new JComboBox(listSubjects.values().toArray());
		final Subject subject = new Subject(0);
		final JTextField tfDate = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JPanel pStartTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final SpinnerModel hourModel = new SpinnerNumberModel(1, 1, 12, 1);
		final SpinnerModel minuteModel = new SpinnerNumberModel(1, 0, 60, 1);
		final JSpinner sStartTimeHour = new JSpinner(hourModel);
		final JSpinner sStartTimeMinute = new JSpinner(minuteModel);
		final String[] meridiemArray = {"AM", "PM"}; 
		final JComboBox cbTime = new JComboBox(meridiemArray);
		List<Integer> listClassrooms = new ArrayList<>();
		final JComboBox cbClassrooms = new JComboBox(listClassrooms.toArray());
		final JPanel pDuration = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final SpinnerModel durationHourModel = new SpinnerNumberModel(1, 1, 4, 1);
		final SpinnerModel durationMinuteModel = new SpinnerNumberModel(1, 0, 60, 1);
		final JSpinner sDurationHour = new JSpinner(durationHourModel);
		final JSpinner sDurationMinute = new JSpinner(durationMinuteModel);
		final JTextField tfMarks = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JTextField tfExamStatus = new JTextField("ACTIVE");
		final TitledBorder border = new TitledBorder("Exam Information");
		
		public JPanel createAddExamInfoPanel() 
		{
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pExamInfo.setBorder(border);
			pExamInfo.setBackground(Color.WHITE);
			
			//setting names for fields to retrieve them for input verifier
			tfExamId.setName("Exam ID");
			tfDate.setName("Date");
			tfMarks.setName("Marks");
			sDurationHour.setToolTipText("Hours");
			sDurationMinute.setToolTipText("Minutes");
			
			tfDate.setInputVerifier(new DateVerifier());
			pStartTime.setBackground(Color.WHITE);
			pStartTime.add(sStartTimeHour);
			pStartTime.add(sStartTimeMinute);
			pStartTime.add(cbTime);
			pDuration.setBackground(Color.white);
			pDuration.add(sDurationHour);
			pDuration.add(sDurationMinute);
			tfExamId.setText(String.valueOf(exam.getExamid() + 1));
			tfExamId.setEditable(false);
			tfExamStatus.setEditable(false);
			cbClassId.setSelectedIndex(-1);
			cbSubjects.setSelectedIndex(-1);
			cbClassrooms.setSelectedIndex(-1);

			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run() 
				{
					tfDate.requestFocusInWindow();
				}
			});
			cbClassId.addItemListener(new ItemListener()
					{
						@Override
						public void itemStateChanged(ItemEvent e) 
						{
							listSubjects = new SubjectSelectDb().getSubjectList(cbClassId.getSelectedItem().toString());	
							cbSubjects.removeAllItems();
							for(String subject : listSubjects.values())
							{
								cbSubjects.addItem(subject);
							}
							exam.setClassId(cbClassId.getSelectedItem().toString());
						}
					});
			cbSubjects.addItemListener(new ItemListener()
					{
						@Override
						public void itemStateChanged(ItemEvent e) 
						{
							String subjectName = cbSubjects.getSelectedItem().toString();
							int subjectid = (Integer)SystemUtils.getKeyFromValue(listSubjects, subjectName);
							Subject subject = new Subject(subjectid, subjectName);
							exam.setSubject(subject);
						}
					});
			sStartTimeHour.addChangeListener(new ChangeListener()
					{
						@Override
						public void stateChanged(ChangeEvent arg0) 
						{
							try
							{
								listClassrooms = ClassroomDb.getUnoccupiedClassroomList(LocalDate.parse(tfDate.getText()));
								cbClassrooms.removeAllItems();
								for(Integer classroom : listClassrooms)
								{
									cbClassrooms.addItem(classroom);
								}
							}
							catch(Exception e)
							{
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Date format should be yyyy-mm-dd");
							}
						}
					});
										
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pExamInfo.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 20, 4, 5);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pExamInfo.add(lExamId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pExamInfo.add(tfExamId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pExamInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pExamInfo.add(cbClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pExamInfo.add(lSubjectName, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pExamInfo.add(cbSubjects, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pExamInfo.add(lDateTime, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pExamInfo.add(tfDate, gbc);
			gbc.gridx = 4;
			gbc.gridy = 3;
			pExamInfo.add(pStartTime, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pExamInfo.add(lExamLocation, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pExamInfo.add(cbClassrooms, gbc);
			gbc.gridx = 0;
			gbc.gridy = 5;
			pExamInfo.add(lDuration, gbc);
			gbc.gridx = 3;
			gbc.gridy = 5;
			pExamInfo.add(pDuration, gbc);
			gbc.gridx = 0;
			gbc.gridy = 6;
			pExamInfo.add(lMarks, gbc);
			gbc.gridx = 3;
			gbc.gridy = 6;
			pExamInfo.add(tfMarks, gbc);
			gbc.gridx = 0;
			gbc.gridy = 7;
			pExamInfo.add(lExamStatus, gbc);
			gbc.gridx = 3;
			gbc.gridy = 7;
			pExamInfo.add(tfExamStatus, gbc);
			
			return pExamInfo;
		}
	}
}