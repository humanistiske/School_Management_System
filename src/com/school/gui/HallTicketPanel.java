package com.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.school.DbUtils;
import com.school.GUIInterfaceUtilities;
import com.school.database.ExamDb;

public class HallTicketPanel 
{
	final JPanel pExamHallticket = new JPanel();
	final JTable examTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JLabel lHeader = new JLabel("School Leaderhead");
	final JButton bHallTicket = new JButton("Save File");

	public JPanel createExamHallticketPanel()
	{
		lHeader.setFont(new Font("Arial", Font.BOLD, 30));
		lHeader.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 10));
		examTable.setShowGrid(false);
		pExamHallticket.setBackground(Color.white);
		pExamHallticket.setLayout(new BorderLayout());
		examTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		examTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		examTable.setModel(DbUtils.viewExamHallticketTableModel(new ExamDb().getExamHallticket()));
		pExamHallticket.add(lHeader, BorderLayout.NORTH);
		pExamHallticket.add(new JScrollPane(examTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.add(bHallTicket);
		pExamHallticket.add(pButtons, BorderLayout.SOUTH);

		bHallTicket.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify a file to save");   
					 
					int userSelection = fileChooser.showSaveDialog(pExamHallticket);
					 
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						GUIInterfaceUtilities.saveScreenShot(pExamHallticket, fileChooser.getSelectedFile() + ".png");
					}
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});		

		return pExamHallticket;
	}
}
