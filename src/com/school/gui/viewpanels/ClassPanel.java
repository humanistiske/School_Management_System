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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.database.ClassDb;
import com.school.elements.Class;
import com.school.gui.LoginPanel;
import com.school.gui.MainWindowSetup;
import com.school.gui.admin.edit_IF.EditClass_IF;
import com.school.gui.admin.edit_IF.EditTeacher_IF;
import com.school.users.Teacher;

public class ClassPanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pClassView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable classTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final Class selectClass = new Class("");

	public JPanel createClassPanel()
	{
		pClassView.setBackground(Color.white);
		pClassView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pClassView.add(pSearch, BorderLayout.NORTH);		
		classTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		classTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		classTable.setModel(DbUtils.viewClassTableModel(new ClassDb().selectClassInfoDb()));
		pClassView.add(new JScrollPane(classTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pButtons.add(bEdit);
		}		
		pClassView.add(pButtons, BorderLayout.SOUTH);

		classTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectClass.setClassId(classTable.getValueAt(classTable.getSelectedRow(), 0).toString());
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				EditClass_IF editClass = new EditClass_IF();
				EditClass_IF.classObj = new Class(selectClass.getClassId());
				
				MainWindowSetup.JDPsystemSetup.add(editClass.editClass_IF(), JLayeredPane.POPUP_LAYER);
			}
		});
		bSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				classTable.setModel(DbUtils.viewClassTableModel(new ClassDb().searchClassInfoDb(tfSearch.getText())));
			}
		});

		return pClassView;
	}
}

