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
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.school.SystemUtils;
import com.school.database.ClassDb;
import com.school.database.ClassTimetableDb;
import com.school.database.SubjectSelectDb;
import com.school.gui.SystemWindowSetup;
import com.school.gui.InputValidators.JCompInputVerifier;
import com.school.elements.ClassTimetable;

public class AddClassTimetable_IF
{
	private static final int LECTURE_DURATION = 30;
	
	private final SubjectSelectDb subjectDb = new SubjectSelectDb();
	private AddClassTimetableInfoPanel addClassTimetableInfoPanel = new AddClassTimetableInfoPanel();

	final JInternalFrame addClassTimetable_IF = new JInternalFrame("Add Class Timetable", true, true, true, true);
	final JPanel pAddClassTimetable = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bUpdate = new JButton("Update");
	final JButton bSave = new JButton("Add");
	final JButton bClear = new JButton("Clear");
	final JPanel pButtons = new JPanel();
	final JPanel pErrorLogButtons = new JPanel(); 
	public final static ClassTimetable classTimetable = new ClassTimetable(0);
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddClassTimetable_IF.inputError = inputError;
	}

	public JInternalFrame addClassTimetable_IF()
	{
		panelCount = 0;
		
		pErrorLogButtons.setLayout(new BorderLayout());
		pAddClassTimetable.setLayout(infoPanel);
		
		pAddClassTimetable.add(addClassTimetableInfoPanel.createAddClassTimetableInfoPanel(), "ClassTimetable");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		error.setForeground(Color.RED);
		AddClassTimetable_IF.pInputError.add(error);
		pInputError.setPreferredSize(new Dimension(300, 10));
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		pButtons.add(bUpdate);
		pButtons.add(bSave);
		pButtons.add(bClear);
		
		addClassTimetable_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addClassTimetable_IF.setLayout(new BoxLayout(addClassTimetable_IF.getContentPane(), BoxLayout.Y_AXIS));
		addClassTimetable_IF.add(pAddClassTimetable);
		pErrorLogButtons.add(pInputError, BorderLayout.CENTER);
		pErrorLogButtons.add(pButtons, BorderLayout.PAGE_END);
		addClassTimetable_IF.add(pErrorLogButtons);
		addClassTimetable_IF.setVisible(true);

		return addClassTimetable_IF;
	}
	
	public class AddClassTimetableInfoPanel extends JPanel
	{
		final JPanel pClassTimetableInfo = new JPanel();
		
		final JLabel lClassId = new JLabel("Class ID: ");
		final JPanel pClassId = new JPanel();
		final JComboBox cbClassId = new JComboBox(new ClassDb().getClassList().toArray());
		final JLabel lSubjectSelection = new JLabel("Subject Order: ");
		final JList jlSubjectSelection = new JList();
		final JList jlSubjectSelected = new JList();
		final JList jlSubjectStartTime = new JList();
		final JList jlSubjectEndTime = new JList();
		final JButton bAddSubjects = new JButton(">>");
		final JButton bRemoveSubjects = new JButton("<<");
		final JPanel pButtons = new JPanel();
		final TitledBorder border = new TitledBorder("Class Time Table");
		protected final Map<Integer, String> selectedSubjectList = new HashMap<Integer, String>();
		final DefaultListModel<String> listSubjectModel = new DefaultListModel<>();
		final DefaultListModel<String> listSelectedSubjectModel = new DefaultListModel<>();
        final JList<String> jlAvailableSubjectList = new JList<>(listSubjectModel);
        final JList<String> jlSelectedSubjectList = new JList<>(listSelectedSubjectModel);
        final JList<String> jlSubjectsOrder = new JList<>(listSelectedSubjectModel);
        final JLabel lSelectTime = new JLabel("Select Time: "); 
        final SpinnerModel hourModel = new SpinnerNumberModel(1, 1, 12, 1);
		final SpinnerModel minuteModel = new SpinnerNumberModel(1, 0, 60, 1);
		final JSpinner sStartTimeHour = new JSpinner(hourModel);
		final JSpinner sStartTimeMinute = new JSpinner(minuteModel);
		final String[] meridiemArray = {"AM", "PM"}; 
		final JComboBox cbTime = new JComboBox(meridiemArray);
		final JPanel pTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JPanel pSelectedSubjectTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
		protected final List<LocalTime> listStartTime = new ArrayList<>();
		protected final List<LocalTime> listEndTime = new ArrayList<>();
        final DefaultListModel<String> listSubjectStartTimeModel = new DefaultListModel<>();
		final DefaultListModel<String> listSubjectEndTimeModel = new DefaultListModel<>();
        final JList<String> jlSubjectStartTimeList = new JList<>(listSubjectStartTimeModel);
        final JList<String> jlSubjectEndTimeList = new JList<>(listSubjectEndTimeModel);
        final JLabel lSelectedSubjectTime = new JLabel("Subject Time: ");
        
        
		public JPanel createAddClassTimetableInfoPanel() 
		{
			pClassId.setLayout(new FlowLayout(FlowLayout.LEFT));
			pClassId.setBackground(Color.WHITE);
			cbClassId.setPreferredSize(new Dimension(50, 25));
			pClassId.add(cbClassId);
			pClassTimetableInfo.setBackground(Color.WHITE);
			pClassTimetableInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pClassTimetableInfo.setBorder(border);
			cbClassId.setSelectedIndex(-1);
			sStartTimeHour.setPreferredSize(new Dimension(40, 20));
			sStartTimeMinute.setPreferredSize(sStartTimeHour.getPreferredSize());
			pTime.setBackground(Color.white);
			pTime.add(sStartTimeHour);
			pTime.add(sStartTimeMinute);
			pTime.add(cbTime);

			((JTextField)cbClassId.getEditor().getEditorComponent()).setInputVerifier(new JCompInputVerifier());;
			//setting names for textField for inputVerifiers
			cbClassId.setName("Class ID");

			JScrollPane scPaneList = new JScrollPane(jlAvailableSubjectList);
			scPaneList.setPreferredSize(new Dimension(180, 80));
			scPaneList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneSelected = new JScrollPane(jlSelectedSubjectList);
			scPaneSelected.setPreferredSize(new Dimension(180, 80));
			scPaneSelected.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneStartTime = new JScrollPane(jlSubjectStartTimeList);
			scPaneStartTime.setPreferredSize(new Dimension(180, 140));
			scPaneStartTime.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneEndTime = new JScrollPane(jlSubjectEndTimeList);
			scPaneEndTime.setPreferredSize(new Dimension(180, 140));
			scPaneEndTime.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scPaneSubjectsOrder = new JScrollPane(jlSubjectsOrder);
			scPaneSubjectsOrder.setPreferredSize(new Dimension(180, 140));
			scPaneSubjectsOrder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			pSelectedSubjectTime.setBackground(Color.white);
			pSelectedSubjectTime.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
									BorderFactory.createEmptyBorder(0, 0, 0, 10)));
			pSelectedSubjectTime.add(scPaneSubjectsOrder);
			pSelectedSubjectTime.add(scPaneStartTime);
			pSelectedSubjectTime.add(new JLabel("To"));
			pSelectedSubjectTime.add(scPaneEndTime);

			cbClassId.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					listSubjectModel.clear();
					AddClassTimetable_IF.classTimetable.setClassId(cbClassId.getSelectedItem().toString());
					for(String subject : subjectDb.getSubjectList(AddClassTimetable_IF.classTimetable.getClassId()).values())
					{
						listSubjectModel.addElement(subject);
					}

					try
					{
						ResultSet rs = new ClassTimetableDb().getClassTimetableInfoDb(cbClassId.getSelectedItem().toString()) ;
						
						selectedSubjectList.clear();
						listStartTime.clear();
						listEndTime.clear();
						
						while(rs.next())
						{
							String subject = rs.getString("subject_name");
							selectedSubjectList.put(
									(Integer)SystemUtils.getKeyFromValue(
											subjectDb.getSubjectList(AddClassTimetable_IF.classTimetable.getClassId()), 
											subject), subject);
							
							LocalTime startTime = LocalTime.parse(rs.getString("start_time"));
							listStartTime.add(startTime);
							LocalTime endTime = LocalTime.parse(rs.getString("end_time"));
							listEndTime.add(endTime);
							
						}

						listSelectedSubjectModel.clear();
						for(String selectedSubject : selectedSubjectList.values())
						{
							listSelectedSubjectModel.addElement(selectedSubject);
						}
						
						setSubjectTimeModel();
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
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
											subjectDb.getSubjectList(AddClassTimetable_IF.classTimetable.getClassId()), subject), subject);
							
							setSubjectTime();
							listSelectedSubjectModel.clear();
							for(String selectedSubject : selectedSubjectList.values())
							{
								listSelectedSubjectModel.addElement(selectedSubject);
							}
							
							setSubjectTimeModel();
						}
					}
					AddClassTimetable_IF.classTimetable.setSubjectList(selectedSubjectList);
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
											subjectDb.getSubjectList(AddClassTimetable_IF.classTimetable.getClassId()), subject), subject);
							listStartTime.remove(listStartTime.size()-1);
							listEndTime.remove(listEndTime.size()-1);
	
							listSelectedSubjectModel.clear();
							for(String selectedSubject : selectedSubjectList.values())
							{
								listSelectedSubjectModel.addElement(selectedSubject);
							}
							
							setSubjectTimeModel();
						}
						else {}
					}
					AddClassTimetable_IF.classTimetable.setSubjectList(selectedSubjectList);
				}
			});
			bUpdate.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							classTimetable.setClassId(cbClassId.getSelectedItem().toString());
							classTimetable.setSubjectList(selectedSubjectList);
							classTimetable.setListStartTime(listStartTime);
							classTimetable.setListEndTime(listEndTime);
							
							if(new ClassTimetableDb().updateClassTimetableInfoDb())
								JOptionPane.showMessageDialog(null, "Class timetable is updated");
							else
								JOptionPane.showMessageDialog(null, "Oops something went wrong");
						}
					});
			bSave.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							classTimetable.setClassId(cbClassId.getSelectedItem().toString());
							classTimetable.setSubjectList(selectedSubjectList);
							classTimetable.setListStartTime(listStartTime);
							classTimetable.setListEndTime(listEndTime);
							
							if(new ClassTimetableDb().addClassTimetableInfoDb())
								JOptionPane.showMessageDialog(null, "Class timetable is set");
							else
								JOptionPane.showMessageDialog(null, "Oops something went wrong");
						}
					});
			bClear.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							listSelectedSubjectModel.clear();
							listSubjectStartTimeModel.clear();
							listSubjectEndTimeModel.clear();
							listStartTime.clear();
							listEndTime.clear();
						}
					});
			sStartTimeHour.addChangeListener(new ChangeListener()
					{
						@Override
						public void stateChanged(ChangeEvent e) 
						{
							refreshSubjectTime();
							setSubjectTimeModel();
						}
					});
			sStartTimeMinute.addChangeListener(new ChangeListener()
					{
						@Override
						public void stateChanged(ChangeEvent e) 
						{
							refreshSubjectTime();
							setSubjectTimeModel();
							
						}
					});

			pButtons.setBackground(Color.white);
			pButtons.setLayout(new BorderLayout());
			pButtons.add(bAddSubjects, BorderLayout.NORTH);
			pButtons.add(bRemoveSubjects, BorderLayout.SOUTH);

			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pClassTimetableInfo.setLayout(gridBag);

			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pClassTimetableInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			gbc.weightx = 0;
			gbc.weighty = 0;
			pClassTimetableInfo.add(pClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0;
			gbc.weighty = 0;
			pClassTimetableInfo.add(lSubjectSelection, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			gbc.weightx = 0.17;
			gbc.weighty = 0.17;
			pClassTimetableInfo.add(scPaneList, gbc);
			gbc.gridx = 4;
			gbc.gridy = 1;
			gbc.weightx = 0;
			gbc.weighty = 0;
			pClassTimetableInfo.add(pButtons, gbc);
			gbc.gridx = 5;
			gbc.gridy = 1;
			gbc.weightx = 0.3;
			gbc.weighty = 0.3;
			pClassTimetableInfo.add(scPaneSelected, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.weightx = 0;
			gbc.weighty = 0;
			pClassTimetableInfo.add(lSelectTime, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pClassTimetableInfo.add(pTime, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pClassTimetableInfo.add(lSelectedSubjectTime, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.gridwidth = 3;
			pClassTimetableInfo.add(pSelectedSubjectTime, gbc);
			
			return pClassTimetableInfo;
		}
		
		public void setSubjectTime()
		{
			LocalTime startTime = LocalTime.of(Integer.valueOf(sStartTimeHour.getValue().toString()),
					Integer.valueOf(sStartTimeMinute.getValue().toString()), 0);
			listStartTime.add(startTime);
			LocalTime endTime = startTime.plusMinutes(LECTURE_DURATION);
			listEndTime.add(endTime);
			
			for(int counter=1; counter<listStartTime.size(); counter++)
			{
				listStartTime.set(counter, listStartTime.get(counter-1).plusMinutes(LECTURE_DURATION));
				listEndTime.set(counter, listEndTime.get(counter-1).plusMinutes(LECTURE_DURATION));
			}
			
			for(int counter=0; counter<listStartTime.size(); counter++)
			{
				if(counter == 4)
				{
					listStartTime.set(counter, listStartTime.get(counter).plusMinutes(LECTURE_DURATION));
					listEndTime.set(counter, listEndTime.get(counter).plusMinutes(LECTURE_DURATION));
				}
				else if(counter>4)
				{
					listStartTime.set(counter, listStartTime.get(counter-1).plusMinutes(LECTURE_DURATION));
					listEndTime.set(counter, listEndTime.get(counter-1).plusMinutes(LECTURE_DURATION));
				}
			}
		}
		
		public void refreshSubjectTime()
		{
			LocalTime startTime = LocalTime.of(Integer.valueOf(sStartTimeHour.getValue().toString()),
					Integer.valueOf(sStartTimeMinute.getValue().toString()), 0);
			listStartTime.set(0, startTime);
			LocalTime endTime = startTime.plusMinutes(LECTURE_DURATION);
			listEndTime.set(0, endTime);
			
			for(int counter=1; counter<listStartTime.size(); counter++)
			{
				listStartTime.set(counter, listStartTime.get(counter-1).plusMinutes(LECTURE_DURATION));
				listEndTime.set(counter, listEndTime.get(counter-1).plusMinutes(LECTURE_DURATION));
			}
			
			for(int counter=0; counter<listStartTime.size(); counter++)
			{
				if(counter == 4)
				{
					listStartTime.set(counter, listStartTime.get(counter).plusMinutes(LECTURE_DURATION));
					listEndTime.set(counter, listEndTime.get(counter).plusMinutes(LECTURE_DURATION));
				}
				else if(counter>4)
				{
					listStartTime.set(counter, listStartTime.get(counter-1).plusMinutes(LECTURE_DURATION));
					listEndTime.set(counter, listEndTime.get(counter-1).plusMinutes(LECTURE_DURATION));
				}
			}
		}
		
		public void setSubjectTimeModel()
		{
			listSubjectStartTimeModel.clear();
			for(LocalTime time : listStartTime)
			{
				listSubjectStartTimeModel.addElement(time + cbTime.getSelectedItem().toString());
			}
			listSubjectEndTimeModel.clear();
			for(LocalTime time : listEndTime)
			{
				listSubjectEndTimeModel.addElement(time + cbTime.getSelectedItem().toString());
			}
		}
	}
}
