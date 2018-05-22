package com.school.gui.viewpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.database.ClassroomDb;
import com.school.elements.Class;
import com.school.elements.Classroom;
import com.school.gui.LoginPanel;
import com.school.gui.MainWindowSetup;
import com.school.gui.admin.edit_IF.EditClass_IF;
import com.school.gui.admin.edit_IF.EditClassroom_IF;

public class ClassroomPanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pClassroomView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable classroomTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	Classroom selectClassroom = new Classroom(0);

	public JPanel createClassroomPanel()
	{
		pClassroomView.setBackground(Color.white);
		pClassroomView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pClassroomView.add(pSearch, BorderLayout.NORTH);	
		classroomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		classroomTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		classroomTable.setModel(DbUtils.viewClassroomTableModel(new ClassroomDb().selectClassroomInfoDb()));
		pClassroomView.add(new JScrollPane(classroomTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pButtons.add(bEdit);
		}
		pClassroomView.add(pButtons, BorderLayout.SOUTH);

		classroomTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectClassroom = new Classroom(Integer.parseInt(classroomTable.getValueAt(classroomTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				EditClassroom_IF editClassroom = new EditClassroom_IF();
				EditClassroom_IF.classroom = new Classroom(selectClassroom.getClassroomid());
				
				MainWindowSetup.JDPsystemSetup.add(editClassroom.editClassroom_IF(), JLayeredPane.POPUP_LAYER);
			}
		});
		bSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int classroomId = 0;
				if(tfSearch.getText().matches("[0-9]+"))
				{
					classroomId = Integer.parseInt(tfSearch.getText());	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Oops invalid input");
				}
				
				classroomTable.setModel(DbUtils.viewClassroomTableModel(new ClassroomDb().searchClassroomInfoDb(classroomId)));
			}
		});

		return pClassroomView;
	}
}

