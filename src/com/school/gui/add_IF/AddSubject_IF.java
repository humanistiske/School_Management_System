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

import com.school.database.SubjectDb;
import com.school.GUIInterfaceUtilities;
import com.school.database.DatabaseConnect;
import com.school.elements.Subject;
import com.school.gui.SystemWindowSetup;
import com.school.gui.InputValidators.JCompInputVerifier;

public class AddSubject_IF 
{
	public static Subject subject = new Subject(DatabaseConnect.getId("subject") + 1); 
	public final AddSubjectInfoPanel addSubjectInfoPanel = new AddSubjectInfoPanel();
	public final JPanel pSubjectInfo = new JPanel();
	final JInternalFrame addSubject_IF = new JInternalFrame("Add Subject", true, true, true, true);

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
		AddSubject_IF.inputError = inputError;
	}

	public JInternalFrame addSubject_IF()
	{
		final JPanel errorLogButtonsPanel = new JPanel();
		errorLogButtonsPanel.setLayout(new BorderLayout());
		pSubjectInfo.setLayout(infoPanel);
		pSubjectInfo.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pSubjectInfo.add(addSubjectInfoPanel.createAddSubjectInfoPanel(), "SubjectInfo");

		//setting up input error box
		pInputError.setBorder(BorderFactory.createLineBorder(Color.red));
		pInputError.setLayout(new BorderLayout());
		pInputError.setBackground(Color.white);
		pInputError.setPreferredSize(new Dimension(300, 60));
		error.setForeground(Color.RED);
		AddSubject_IF.pInputError.add(error);

		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bSubmit.setPreferredSize(bRefresh.getPreferredSize());
		pButtons.add(bRefresh);
		pButtons.add(bSubmit);

		addSubject_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addSubject_IF.setLayout(new BorderLayout());
		addSubject_IF.add(pSubjectInfo, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pInputError, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pButtons, BorderLayout.SOUTH);
		addSubject_IF.add(errorLogButtonsPanel, BorderLayout.SOUTH);

		addSubject_IF.setVisible(true);

		bRefresh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				subject.setSubjectId(DatabaseConnect.getId("subject"));
				addSubjectInfoPanel.tfSubjectId.setText(String.valueOf(subject.getSubjectId()));
			}
		});
		bSubmit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(isInputError())
				{
					JOptionPane.showMessageDialog(null, "Oops make sure all fields are set");
				}
				else
				{
					subject.setSubjectId(subject.getSubjectId());
					subject.setSubjectName(addSubjectInfoPanel.tfSubjectName.getText());
					subject.setClassId(addSubjectInfoPanel.cbClassId.getSelectedItem().toString());

					try
					{
						if(new SubjectDb().addSubject())
							JOptionPane.showMessageDialog(null, "Subject added successfully");
						else
							JOptionPane.showMessageDialog(null, "Oops subject with similar values exist");
					}
					catch(Exception e1)
					{
						System.out.println(e1);
					}
				}
			}
		});

		return addSubject_IF;
	}

	protected class AddSubjectInfoPanel 
	{
		final JPanel pSubjectInfo = new JPanel();
		final JLabel lSubjectId = new JLabel("Subject ID: ");
		final JLabel lSubjectName = new JLabel("Subject Name: ");
		final JLabel lClassId = new JLabel("Class ID: ");
		
		final JTextField tfSubjectId = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JTextField tfSubjectName = new JTextField();
		final Object[] classList = DatabaseConnect.getClassList("class").toArray();
		final JComboBox cbClassId = new JComboBox(classList);  
		final TitledBorder border = new TitledBorder("Subject Information");
		final JCompInputVerifier emptyError = new JCompInputVerifier();

		public JPanel createAddSubjectInfoPanel() 
		{
			tfSubjectId.setText(String.valueOf(subject.getSubjectId()));
			tfSubjectId.setEditable(false);
			tfSubjectName.setPreferredSize(tfSubjectId.getPreferredSize());
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pSubjectInfo.setBorder(border);
			pSubjectInfo.setBackground(Color.WHITE);
			cbClassId.setSelectedIndex(-1);
			
			tfSubjectName.setInputVerifier(emptyError);
			
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run() 
				{
					tfSubjectName.requestFocusInWindow();
				}
			});

			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pSubjectInfo.setLayout(gridBag);

			gbc.insets = new Insets(0, 20, 4, 0);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pSubjectInfo.add(lSubjectId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pSubjectInfo.add(tfSubjectId, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pSubjectInfo.add(lSubjectName, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pSubjectInfo.add(tfSubjectName, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pSubjectInfo.add(lClassId, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pSubjectInfo.add(cbClassId, gbc);

			return pSubjectInfo;
		}
	}
}