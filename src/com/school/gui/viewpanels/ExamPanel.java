package com.school.gui.viewpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.database.ExamDb;
import com.school.elements.Exam;
import com.school.gui.ChangePasswordPanel;
import com.school.gui.HallTicketPanel;
import com.school.gui.LoginPanel;
import com.school.gui.MainWindowSetup;
import com.school.gui.admin.edit_IF.EditExam_IF;

public class ExamPanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pExamView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable examTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final JButton bHallTicket = new JButton("Download Hallticket");
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
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pButtons.add(bEdit);
		}
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.STUDENT))
		{
			pButtons.add(bHallTicket);
		}
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
				EditExam_IF editExam = new EditExam_IF();
				EditExam_IF.exam = new Exam(selectExam.getExamid());
				
				MainWindowSetup.JDPsystemSetup.add(editExam.editExam_IF(), JLayeredPane.POPUP_LAYER);
			}
		});
		bHallTicket.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(pExamView);  
						JDialog dHallTicket = new JDialog(frame, "Exam Hall Ticket", true);
						dHallTicket.setLayout(new GridLayout(1, 0));
						dHallTicket.add(new HallTicketPanel().createExamHallticketPanel());
						dHallTicket.pack();
						dHallTicket.setLocationRelativeTo(null);
						dHallTicket.setVisible(true);
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

