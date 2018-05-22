package com.school.gui.add_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.school.DateLabelFormatter;
import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.database.AttendanceDb;
import com.school.database.ClassDb;
import com.school.database.SubjectSelectDb;
import com.school.elements.Attendance;
import com.school.elements.Subject;
import com.school.gui.SystemWindowSetup;

public class AddAttendance_IF 
{	
	public final AddAttendanceInfoPanel addAttendanceInfoPanel = new AddAttendanceInfoPanel();
	
	private Subject subject = new Subject(0);

	final JInternalFrame addAttendance_IF = new JInternalFrame("Add Attendance", true, true, true, true);
	final JPanel pAddAttendance = new JPanel();
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bUpdate = new JButton("Update");
	final JButton bClear = new JButton("Clear");
	final JPanel pButtons = new JPanel();
	final JPanel pErrorLogButtons = new JPanel(); 
	public final static Attendance attendance = new Attendance();
	final static Map<Integer, Boolean> attendanceStatusList = new HashMap<>();
	int panelCount = 0;
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddAttendance_IF.inputError = inputError;
	}

	public static Map<Integer, Boolean> getAttendanceStatusList() 
	{
		return attendanceStatusList;
	}

	public JInternalFrame addAttendance_IF()
	{
		panelCount = 0;
		
		pErrorLogButtons.setLayout(new BorderLayout());
		pAddAttendance.setLayout(infoPanel);
		pAddAttendance.add(addAttendanceInfoPanel.createAddAttendanceInfoPanel(), "Attendance");
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		pButtons.add(bUpdate);
		pButtons.add(bClear);
		
		addAttendance_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addAttendance_IF.setLayout(new BoxLayout(addAttendance_IF.getContentPane(), BoxLayout.Y_AXIS));
		addAttendance_IF.add(pAddAttendance, BorderLayout.CENTER);
		pErrorLogButtons.add(pButtons, BorderLayout.PAGE_END);
		addAttendance_IF.add(pErrorLogButtons);
		addAttendance_IF.setVisible(true);

		return addAttendance_IF;
	}
	
	public class AddAttendanceInfoPanel extends JPanel
	{
		final JPanel pAttendanceInfo = new JPanel();
		
		final JLabel lClassId = new JLabel("Class ID: ");
		final JComboBox cbClassId = new JComboBox(new ClassDb().getClassList().toArray());
		final JLabel lSubjectId = new JLabel("Subject ID: ");
		Map<Integer, String> listSubjects = new HashMap<>(); 
		final JComboBox cbSubjects = new JComboBox(listSubjects.values().toArray());
		final JLabel lDate = new JLabel("Date: ");
		public final JTextField tfDate = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		UtilDateModel model;
		JDatePanelImpl datePanel;
		JDatePickerImpl dpAttendance;
		final JButton bSearch = new JButton("Search");
		final JButton bAdd = new JButton("Add");
		final JTable jtStudentAttendance = new JTable();
        
		public JPanel createAddAttendanceInfoPanel() 
		{
			final JPanel pAddAttendance = new JPanel();
			pAddAttendance.setLayout(new BorderLayout());
			pAddAttendance.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

			model = new UtilDateModel();
			model.setValue(Date.valueOf(LocalDate.now()));
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			datePanel = new JDatePanelImpl(model, p);
			dpAttendance = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			
			final JPanel pClassSubject = new JPanel();
			pClassSubject.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
			cbClassId.setSelectedIndex(-1);
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pClassSubject.setLayout(gridBag);
			gbc.insets = new Insets(0, 20, 4, 5);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pClassSubject.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pClassSubject.add(cbClassId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pClassSubject.add(lSubjectId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pClassSubject.add(cbSubjects, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pClassSubject.add(lDate, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pClassSubject.add(dpAttendance, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pClassSubject.add(bSearch, gbc);
			gbc.gridx = 5;
			gbc.gridy = 2;
			pClassSubject.add(new JLabel(" OR "), gbc);
			gbc.gridx = 6;
			gbc.gridy = 2;
			pClassSubject.add(bAdd, gbc);
			
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
							attendance.setClassId(cbClassId.getSelectedItem().toString());
						}
					});
			cbSubjects.addItemListener(new ItemListener()
					{
						@Override
						public void itemStateChanged(ItemEvent e) 
						{
							subject = new Subject(Integer.parseInt(
									SystemUtils.getKeyFromValue(listSubjects, cbSubjects.getSelectedItem().toString()).toString()), 
									cbSubjects.getSelectedItem().toString());
							attendance.setSubjectid(subject.getSubjectId());
						}				
					});
			bSearch.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							
							LocalDate date = LocalDate.parse(dpAttendance.getJFormattedTextField().getText());
							attendance.setDate(date);
							jtStudentAttendance.setModel(DbUtils.addAttendanceTableModel(
									new AttendanceDb().addAttendanceInfoDb(attendance.getSubjectid(), attendance.getDate())));
							
						}
					});
			bAdd.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							LocalDate date = LocalDate.now();
							attendance.setDate(date);
							if(new AttendanceDb().addNewAttendanceInfoDb())
							{
								jtStudentAttendance.setModel(DbUtils.addAttendanceTableModel(
										new AttendanceDb().addAttendanceInfoDb(subject.getSubjectId(), attendance.getDate())));
							}
								
							else
								System.out.println("fdsa");
						}
					});
			bUpdate.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							attendanceStatusList.clear();
							java.util.Date date = (java.util.Date)dpAttendance.getModel().getValue();
							/*attendance.setDate(LocalDate.parse());*/
							int rows = jtStudentAttendance.getModel().getRowCount();
							for(int row=0; row<rows; row++)
							{
								int userid = 
										Integer.parseInt(jtStudentAttendance.getModel().getValueAt(row, 0).toString().substring(1));
								boolean attendance_status = 
										Boolean.parseBoolean(jtStudentAttendance.getModel().getValueAt(row, 3).toString());
								attendanceStatusList.put(userid, attendance_status);
							}
							
							System.out.println(attendanceStatusList);
							
							if(new AttendanceDb().updateNewAttendanceInfo())
							{
								JOptionPane.showMessageDialog(null, "Attendance has been updated");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Oops something went wrong");
							}
						}
					});
			
			
			jtStudentAttendance.getTableHeader().setBackground(Color.decode("#d1d8e0"));
			jtStudentAttendance.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
			
			pAddAttendance.add(pClassSubject, BorderLayout.NORTH);
			pAddAttendance.add(new JScrollPane(jtStudentAttendance), BorderLayout.CENTER);
			return pAddAttendance;
		}
	}
}
