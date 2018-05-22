package com.school.gui.admin.edit_IF;

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
import java.time.LocalDate;

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
import javax.swing.text.AbstractDocument;

import com.school.GUIInterfaceUtilities;
import com.school.database.ClassDb;
import com.school.database.DatabaseConnect;
import com.school.elements.Class;
import com.school.elements.ClassDivision;
import com.school.gui.SystemWindowSetup;
import com.school.gui.InputValidators.ClassroomCapacityFilter;

public class EditClass_IF 
{
	public static Class classObj = new Class("00"); 
	public final EditClassInfoPanel editClassInfoPanel = new EditClassInfoPanel();
	public final JPanel pClassInfo = new JPanel();
	final JInternalFrame addClass_IF = new JInternalFrame("Edit Class", true, true, true, true);
	private static int currentClassroom; 
	
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bSubmit = new JButton("Update");
	final JPanel pButtons = new JPanel();
	int panelCount = 0;

	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		EditClass_IF.inputError = inputError;
	}
	
	public static int getCurrentClassroom() {
		return currentClassroom;
	}

	public static void setCurrentClassroom(int currentClassroom) {
		EditClass_IF.currentClassroom = currentClassroom;
	}

	public JInternalFrame editClass_IF()
	{
		try
		{
			ResultSet rs = new ClassDb().getClassInfo(classObj.getClassId());
			while(rs.next())
			{
				classObj.setClassCapacity(rs.getInt("class_capacity"));
				classObj.setClassSize(rs.getInt("class_size"));
				classObj.setClassroomId(rs.getInt("classroomid"));
			}
			setCurrentClassroom(classObj.getClassroomId());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		final JPanel errorLogButtonsPanel = new JPanel();
		errorLogButtonsPanel.setLayout(new BorderLayout());
		pClassInfo.setLayout(infoPanel);
		pClassInfo.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pClassInfo.add(editClassInfoPanel.createAddClassInfoPanel(), "ClassInfo");

		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		pInputError.setPreferredSize(new Dimension(300, 60));
		error.setForeground(Color.RED);
		EditClass_IF.pInputError.add(error);

		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		pButtons.add(bSubmit);

		addClass_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addClass_IF.setLayout(new BorderLayout());
		addClass_IF.add(pClassInfo, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pInputError, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pButtons, BorderLayout.SOUTH);
		addClass_IF.add(errorLogButtonsPanel, BorderLayout.SOUTH);
		addClass_IF.setVisible(true);
		
		bSubmit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(editClassInfoPanel.cbClassroomId.getSelectedIndex() == -1 | editClassInfoPanel.cbDivision.getSelectedIndex() == -1)
				{
					setInputError(true);
				}
				
				if(isInputError())
				{
					JOptionPane.showMessageDialog(null, "Oops make sure all fields are set");
				}
				else
				{
					classObj.setClassId((Integer)editClassInfoPanel.sStandard.getValue() + 
							editClassInfoPanel.cbDivision.getSelectedItem().toString());
					classObj.setClassCapacity(Integer.parseInt(editClassInfoPanel.tfClassCapacity.getText()));
					classObj.setClassroomId(Integer.parseInt(editClassInfoPanel.cbClassroomId.getSelectedItem().toString()));
					classObj.setOccupancy("OCCUPIED");
					classObj.setOccupiedTill(LocalDate.parse(editClassInfoPanel.tfOccupancy.getText()));

					try
					{
						if(new ClassDb().updateClass())
							JOptionPane.showMessageDialog(null, "Class updated successfully");
						else
							JOptionPane.showMessageDialog(null, "Oops class with similar values exist");
					}
					catch(Exception e1)
					{
						System.out.println(e1);
					}
				}
			}
		});

		return addClass_IF;
	}

	protected class EditClassInfoPanel 
	{
		final JPanel pClassInfo = new JPanel();
		final JLabel lClassId = new JLabel("Class ID: ");
		final JLabel lClassCapacity = new JLabel("Class Capacity: ");
		final JLabel lClassroomId = new JLabel("Classroom: ");
		final JLabel lOccupancy = new JLabel("Occupied For: ");
		
		final SpinnerModel standardValues = new SpinnerNumberModel(1, 1, 10, 1);
		final JSpinner sStandard = new JSpinner(standardValues);
		final JTextField tfClassCapacity = new JTextField();
		final ClassDivision[] divisionList = ClassDivision.values();
		final JComboBox cbDivision = new JComboBox(divisionList);
		final String[] occupancyList = {"Weeks", "Months", "Years"};
		final JComboBox cbClassroomId = new JComboBox(DatabaseConnect.getClassroomList("classroom").toArray());
		final JComboBox cbOccupancy = new JComboBox(occupancyList);
		final SpinnerModel occupancyValues = new SpinnerNumberModel(1, 1, 12, 1);
		final JSpinner sOccupancy = new JSpinner(occupancyValues);
		final JTextField tfOccupancy = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final TitledBorder border = new TitledBorder("Class Information");
		final ClassroomCapacityFilter classCapacityError = new ClassroomCapacityFilter();
		
		public JPanel createAddClassInfoPanel() 
		{
			tfOccupancy.setEditable(false);
			sOccupancy.setEditor(new JSpinner.DefaultEditor(sOccupancy));
			sStandard.setEditor(new JSpinner.DefaultEditor(sStandard));
			sStandard.setPreferredSize(new Dimension(150, 20));
			cbDivision.setPreferredSize(sStandard.getPreferredSize());
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pClassInfo.setBorder(border);
			pClassInfo.setBackground(Color.WHITE);
			cbClassroomId.setSelectedIndex(-1);
			cbDivision.setSelectedIndex(-1);
			cbOccupancy.setSelectedIndex(-1);
			
			//setting names for fields to retrieve them for input verifier
			sStandard.setName("Class ID");
			
			//setting up retrieved information into fields
			tfClassCapacity.setText(String.valueOf(classObj.getClassCapacity()));
			sStandard.setValue(Integer.parseInt(classObj.getClassId().replaceAll("[a-zA-Z]", "")));
			cbDivision.setSelectedItem(ClassDivision.valueOf(classObj.getClassId().replaceAll("[0-9]+", "")));

			sOccupancy.addChangeListener(new ChangeListener()
					{
						@Override
						public void stateChanged(ChangeEvent e) 
						{
							LocalDate occupiedTill = LocalDate.now(); 
							if(cbOccupancy.getSelectedItem().toString().equals("Weeks"))
							{
								occupiedTill = occupiedTill.plusWeeks(Integer.valueOf(sOccupancy.getValue().toString()));
							}
							else if(cbOccupancy.getSelectedItem().toString().equals("Months"))
							{
								occupiedTill = occupiedTill.plusMonths(Integer.valueOf(sOccupancy.getValue().toString()));
							}
							else if(cbOccupancy.getSelectedItem().toString().equals("Years"))
							{
								occupiedTill = occupiedTill.plusYears(Integer.valueOf(sOccupancy.getValue().toString()));
							}
							tfOccupancy.setText(occupiedTill.toString());
						}
					});
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run() 
				{
					tfClassCapacity.requestFocusInWindow();
				}
			});

			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pClassInfo.setLayout(gridBag);

			gbc.insets = new Insets(0, 20, 4, 0);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pClassInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pClassInfo.add(sStandard, gbc);
			gbc.gridx = 4;
			gbc.gridy = 0;
			pClassInfo.add(cbDivision, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pClassInfo.add(lClassroomId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pClassInfo.add(cbClassroomId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pClassInfo.add(lOccupancy, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pClassInfo.add(cbOccupancy, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			pClassInfo.add(sOccupancy, gbc);
			gbc.gridx = 5;
			gbc.gridy = 2;
			pClassInfo.add(tfOccupancy, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pClassInfo.add(lClassCapacity, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pClassInfo.add(tfClassCapacity, gbc);

			return pClassInfo;
		}
	}
}
