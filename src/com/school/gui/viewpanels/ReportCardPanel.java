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
import com.school.database.ReportCardDb;
import com.school.elements.ReportCard;
import com.school.gui.LoginPanel;

public class ReportCardPanel extends JPanel
{
	public static int panelCount = 0;
	final JPanel pReportCardView = new JPanel();
	final JPanel pSearch = new JPanel();
	public final JTextField tfSearch = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
	final JButton bSearch = new JButton("Search");
	final JTable reportCardTable = new JTable();
	final JPanel pButtons = new JPanel();
	final JButton bEdit = new JButton("Edit");
	final ReportCard selectReportCard = new ReportCard(0, 0);
	
	public JPanel createReportCardPanel()
	{
		pReportCardView.setBackground(Color.white);
		pReportCardView.setLayout(new BorderLayout());
		pSearch.setPreferredSize(new Dimension(800, 40));
		pSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSearch.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		pSearch.add(tfSearch);
		pSearch.add(bSearch);
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pReportCardView.add(pSearch, BorderLayout.NORTH);
		}
		else if(LoginPanel.getUserLogin().getUserType().equals(UserType.TEACHER))
		{
			pReportCardView.add(pSearch, BorderLayout.NORTH);
		}
		reportCardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		reportCardTable.getTableHeader().setBackground(Color.decode("#d1d8e0"));
		reportCardTable.setModel(DbUtils.viewReportCardTableModel(new ReportCardDb().selectReportCardInfoDb()));
		pReportCardView.add(new JScrollPane(reportCardTable), BorderLayout.CENTER);
		pButtons.setBackground(Color.white);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		bEdit.setPreferredSize(new Dimension(60, 25));
		if(LoginPanel.getUserLogin().getUserType().equals(UserType.ADMIN))
		{
			pButtons.add(bEdit);
		}
		pReportCardView.add(pButtons, BorderLayout.SOUTH);
		
		reportCardTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
				{
					@Override
					public void valueChanged(ListSelectionEvent e) 
					{
						selectReportCard.setUserid(Integer.parseInt(reportCardTable.getValueAt(reportCardTable.getSelectedRow(), 0).toString().substring(1)));
					}
				});
		bEdit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						System.out.println(selectReportCard.getUserid());
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
							reportCardTable.setModel(DbUtils.viewReportCardTableModel(new ReportCardDb().searchReportCardInfoDb(id)));
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Oops invalid input");
						}
					}
				});
		
		return pReportCardView;
	}
}
