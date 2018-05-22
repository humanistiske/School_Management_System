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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import com.school.database.ClassroomDb;
import com.school.database.DatabaseConnect;
import com.school.elements.Classroom;
import com.school.gui.SystemWindowSetup;
import com.school.gui.InputValidators.ClassroomCapacityFilter;
import com.school.users.Accountant;

public class AddClassroom_IF 
{
	public final static Classroom classroom = new Classroom(DatabaseConnect.getClassroomId("classroom")); 
	public final AddClassroomInfoPanel addClassroomInfoPanel = new AddClassroomInfoPanel();
	public final JPanel pClassroomInfo = new JPanel();
	final JInternalFrame addClassroom_IF = new JInternalFrame("Add Classroom", true, true, true, true);
	
	public final static JPanel pInputError = new JPanel();
	public final static JLabel error = new JLabel();
	final CardLayout infoPanel = new CardLayout();
	final JButton bPrevious = new JButton("Previous");
	final JButton bNext = new JButton("Next");
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	public final static Accountant accountant = new Accountant(AddUserInfoPanel.getId());
	int panelCount = 0;
	
	private static boolean inputError = false;

	public static boolean isInputError() 
	{
		return inputError;
	}

	public static void setInputError(boolean inputError) 
	{
		AddClassroom_IF.inputError = inputError;
	}
	
	public JInternalFrame addClassroom_IF()
	{
		final JPanel errorLogButtonsPanel = new JPanel();
		errorLogButtonsPanel.setLayout(new BorderLayout());
		pClassroomInfo.setLayout(infoPanel);
		pClassroomInfo.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pClassroomInfo.add(addClassroomInfoPanel.createAddClassroomInfoPanel(), "ClassroomInfo");
		
		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		pInputError.setPreferredSize(new Dimension(300, 60));
		error.setForeground(Color.RED);
		AddClassroom_IF.pInputError.add(error);
		
		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bSubmit.setPreferredSize(bRefresh.getPreferredSize());
		pButtons.add(bRefresh);
		pButtons.add(bSubmit);

		addClassroom_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addClassroom_IF.setLayout(new BorderLayout());
		addClassroom_IF.add(pClassroomInfo, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pInputError, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pButtons, BorderLayout.SOUTH);
		addClassroom_IF.add(errorLogButtonsPanel, BorderLayout.SOUTH);
		
		addClassroom_IF.setVisible(true);

		bRefresh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				classroom.setClassroomid(DatabaseConnect.getClassroomId("classroom"));
				addClassroomInfoPanel.tfClassroomId.setText(String.valueOf(classroom.getClassroomid()));
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
					classroom.setClassroomid(DatabaseConnect.getClassroomId("classroom"));
					classroom.setCapacity(Integer.parseInt(addClassroomInfoPanel.tfClassroomCapacity.getText()));
					classroom.setOccupancy(addClassroomInfoPanel.cbClassroomOccupancy.getSelectedItem().toString());
					
					try
					{
						if(new ClassroomDb().addClassroom())
							JOptionPane.showMessageDialog(null, "Classroom added successfully");
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
		
		return addClassroom_IF;
	}
	
	protected class AddClassroomInfoPanel 
	{
		final JPanel pClassroomInfo = new JPanel();
		final JLabel lClassroomId = new JLabel("Classroom ID: ");
		final JLabel lClassroomCapacity = new JLabel("Classroom Capacity: ");
		final JLabel lClassroomOccupancy = new JLabel("Classroom Occupancy Status: ");
		final JTextField tfClassroomId = new JTextField("User ID");
		final String[] occupancyStatus = {"Occupied", "Unoccupied"};
		final JTextField tfClassroomCapacity = new JTextField(15);
		final JComboBox cbClassroomOccupancy = new JComboBox(occupancyStatus);
		final TitledBorder border = new TitledBorder("Classroom Information");
		final ClassroomCapacityFilter classCapacityError = new ClassroomCapacityFilter();
		
		public JPanel createAddClassroomInfoPanel() 
		{
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pClassroomInfo.setBorder(border);
			pClassroomInfo.setBackground(Color.WHITE);
			
			//setting names for fields to retrieve them for input verifier
			tfClassroomId.setName("Classroom ID");
			tfClassroomCapacity.setName("Classroom Capacity");
			cbClassroomOccupancy.setName("Classroom Occupancy");
			
			tfClassroomId.setText(String.valueOf(classroom.getClassroomid()));
			tfClassroomId.setEditable(false);
			cbClassroomOccupancy.setSelectedIndex(1);
			cbClassroomOccupancy.setEnabled(false);
			((AbstractDocument)tfClassroomCapacity.getDocument()).setDocumentFilter(classCapacityError);

			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run() 
				{
					tfClassroomCapacity.requestFocusInWindow();
				}
			});
			
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pClassroomInfo.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 20, 4, 20);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pClassroomInfo.add(lClassroomId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pClassroomInfo.add(tfClassroomId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pClassroomInfo.add(lClassroomOccupancy, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pClassroomInfo.add(cbClassroomOccupancy, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pClassroomInfo.add(lClassroomCapacity, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pClassroomInfo.add(tfClassroomCapacity, gbc);
						
			return pClassroomInfo;
		}
	}
}
