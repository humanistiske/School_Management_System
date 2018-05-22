package com.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.school.SystemUtils;

public class NavigationPanel 
{
	public static JPanel getNavigationPanel()
	{
		final JPanel pNavigationPanel = new JPanel();
		
		DefaultMutableTreeNode tnSchoolWebspace = new DefaultMutableTreeNode("School Webspace");
		DefaultMutableTreeNode tnStudent = new DefaultMutableTreeNode("Student");
		DefaultMutableTreeNode tnTeacher = new DefaultMutableTreeNode("Teacher");
		DefaultMutableTreeNode tnClass = new DefaultMutableTreeNode("Class");
		DefaultMutableTreeNode tnSubject = new DefaultMutableTreeNode("Subject");
		DefaultMutableTreeNode tnClassTimetable = new DefaultMutableTreeNode("Class Timetable");
		DefaultMutableTreeNode tnAttendance = new DefaultMutableTreeNode("Attendance");
		DefaultMutableTreeNode tnExam = new DefaultMutableTreeNode("Exam");
		DefaultMutableTreeNode tnClassroom = new DefaultMutableTreeNode("Classroom");
		DefaultMutableTreeNode tnPayment = new DefaultMutableTreeNode("Payment");
		DefaultMutableTreeNode tnReportCard = new DefaultMutableTreeNode("Report Card");
		tnSchoolWebspace.add(tnStudent);
		tnSchoolWebspace.add(tnTeacher);
		tnSchoolWebspace.add(tnClass);
		tnSchoolWebspace.add(tnSubject);
		tnSchoolWebspace.add(tnClassTimetable);
		tnSchoolWebspace.add(tnAttendance);
		tnSchoolWebspace.add(tnExam);
		tnSchoolWebspace.add(tnClassroom);
		tnSchoolWebspace.add(tnPayment);
		tnSchoolWebspace.add(tnReportCard);
	
		JTree jtNavigation = new JTree(tnSchoolWebspace);
		jtNavigation.getSelectionModel().addTreeSelectionListener(new NavigationSelector());
		jtNavigation.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		pNavigationPanel.setBackground(Color.WHITE);
		pNavigationPanel.setLayout(new BorderLayout());
		pNavigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
		pNavigationPanel.add(jtNavigation, BorderLayout.WEST);
		pNavigationPanel.setBounds(0, 0, 200, SystemUtils.WINDOW_HEIGHT);
        pNavigationPanel.setVisible(true);
		
		return pNavigationPanel;
	}
}
