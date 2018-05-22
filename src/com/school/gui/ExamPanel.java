package com.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.GUIInterfaceUtilities;
import com.school.database.ExamDb;
import com.school.database.DbUtils;
import com.school.elements.Exam;

public class ExamPanel extends JPanel
{
	final JPanel pExamView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable examTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final Exam selectExam = new Exam(0);

	public JPanel createExamPanel()
	{
		pExamView.setBackground(Color.white);
		pExamView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		pExamView.add(pSearch, BorderLayout.NORTH);
		examTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		examTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		examTable.setModel(DbUtils.viewExamTableModel(new ExamDb().selectExamInfoDb()));
		pExamView.add(new JScrollPane(examTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		pButtons.add(bEdit);
		pExamView.add(pButtons, BorderLayout.SOUTH);

		examTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				selectExam.setExamid(Integer.parseInt(examTable.getValueAt(examTable.getSelectedRow(), 0).toString()));
			}
		});
		bEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(selectExam.getExamid());
			}
		});
		bSearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(tfSearch.getText().matches("[0-9]+"))
				{
					Integer id = Integer.parseInt(tfSearch.getText());
					examTable.setModel(DbUtils.viewExamTableModel(new ExamDb().searchExamInfoDb(id)));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Oops invalid input");
				}
			}
		});

		return pExamView;
	}
}

