package com.school.gui;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.school.GUIInterfaceUtilities;
import com.school.UserType;
import com.school.gui.viewpanels.AttendancePanel;
import com.school.gui.viewpanels.ClassPanel;
import com.school.gui.viewpanels.ClassTimetablePanel;
import com.school.gui.viewpanels.ClassroomPanel;
import com.school.gui.viewpanels.ExamPanel;
import com.school.gui.viewpanels.StaffAccountPanel;
import com.school.gui.viewpanels.StudentAccountPanel;
import com.school.gui.viewpanels.StudentPanel;
import com.school.gui.viewpanels.SubjectPanel;
import com.school.gui.viewpanels.TeacherPanel;

public class NavigationSelector implements TreeSelectionListener 
{
	public StudentPanel pStudentPanel = new StudentPanel();
	public TeacherPanel pTeacherPanel = new TeacherPanel();
	public ClassPanel pClassPanel = new ClassPanel();
	public SubjectPanel pSubjectPanel = new SubjectPanel();
	public ClassroomPanel pClassroomPanel = new ClassroomPanel();
	public ClassTimetablePanel pClassTimetablePanel = new ClassTimetablePanel();
	public AttendancePanel pAttendancePanel = new AttendancePanel();
	public ExamPanel pExamPanel = new ExamPanel();
	public ReportCardPanel pReportCardPanel = new ReportCardPanel();
	public com.school.gui.viewpanels.ReportCardPanel pReportCardPanel1 = 
								new com.school.gui.viewpanels.ReportCardPanel();
	public StaffAccountPanel pStaffAccountPanel = new StaffAccountPanel();
	public StudentAccountPanel pStudentAccountPanel = new StudentAccountPanel();
	
	@Override
	public void valueChanged(TreeSelectionEvent e) 
	{
		Object obj = e.getNewLeadSelectionPath().getLastPathComponent();
		
		switch(obj.toString())
		{
			case "Student":
				if(StudentPanel.panelCount < 1)
				{
					StudentPanel.panelCount++;
					InterfacePanel.tpInterface.add("Student", pStudentPanel.createStudentPanel());
					break;
				}
				else
				{
					break;
				}
				
			case "Teacher":
				if(TeacherPanel.panelCount < 1)
				{
					TeacherPanel.panelCount++;
					InterfacePanel.tpInterface.add("Teacher", pTeacherPanel.createTeacherPanel());
					break;
				}
				else
				{
					break;
				}
				
			case "Class":	
				if(ClassPanel.panelCount < 1)
				{
					ClassPanel.panelCount++;
					InterfacePanel.tpInterface.add("Class", pClassPanel.createClassPanel());
					break;
				}
				else
				{
					break;
				}
				
			case "Subject":
				if(SubjectPanel.panelCount < 1)
				{
					SubjectPanel.panelCount++;
					InterfacePanel.tpInterface.add("Subject", pSubjectPanel.createSubjectPanel());
					break;
				}
				else
				{
					break;
				}
				
			case "Classroom":
				if(ClassroomPanel.panelCount < 1)
				{
					ClassroomPanel.panelCount++;
					InterfacePanel.tpInterface.add("Classroom", pClassroomPanel.createClassroomPanel());
					break;
				}
				else
				{
					break;
				}
			
			case "Class Timetable":
				if(ClassTimetablePanel.panelCount < 1)
				{
					ClassTimetablePanel.panelCount++;
					InterfacePanel.tpInterface.add("Class Timetable", pClassTimetablePanel.createClassTimetablePanel());
					break;	
				}
				else
				{
					break;
				}
				
			case "Attendance":
				if(AttendancePanel.panelCount < 1)
				{
					AttendancePanel.panelCount++;
					InterfacePanel.tpInterface.add("Attendance", pAttendancePanel.createAttendancePanel());
					break;	
				}
				else
				{
					break;
				}
			
			case "Report Card":
				if(ReportCardPanel.panelCount < 1)
				{
					if(LoginPanel.getUserLogin().getUserType().equals(UserType.STUDENT))
					{
						ReportCardPanel.panelCount++;
						InterfacePanel.tpInterface.add("Report Card", pReportCardPanel.createReportCardPanel());
						break;
					}
					else
					{
						ReportCardPanel.panelCount++;
						InterfacePanel.tpInterface.add("Report Card", pReportCardPanel1.createReportCardPanel());
						break;
					}	
				}
				else
				{
					break;
				}
				
			case "Exam":
				if(ExamPanel.panelCount < 1)
				{
					ExamPanel.panelCount++;
					InterfacePanel.tpInterface.add("Exam", pExamPanel.createExamPanel());
					break;	
				}
				else
				{
					break;
				}
				
			case "Payment":
				if(StaffAccountPanel.panelCount < 1)
				{
					if(LoginPanel.getUserLogin().getUserType().equals(UserType.STUDENT))
					{
						StudentAccountPanel.panelCount++;
						InterfacePanel.tpInterface.add("Payment", pStudentAccountPanel.createStudentAccountPanel());
						break;
					}
					else
					{
						StaffAccountPanel.panelCount++;
						InterfacePanel.tpInterface.add("Payment", pStaffAccountPanel.createStaffAccountPanel());
						break;
					}
				}
				else
				{
					break;
				}
				
			default:
				GUIInterfaceUtilities.unknownComponentError();
				break;
		}
	}

}
