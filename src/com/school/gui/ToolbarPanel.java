package com.school.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JToolTip;

import com.school.Attendance;
import com.school.GUIInterfaceUtilities;
import com.school.SchoolElement;
import com.school.SystemUtils;
import com.school.UserAccount;
import com.school.UserFeature;
import com.school.UserType;
import com.school.database.DatabaseConnect;
import com.school.gui.accountant.edit_IF.EditStaff_IF;
import com.school.gui.accountant.edit_IF.EditStudent_IF;
import com.school.gui.add_IF.AddAccountant_IF;
import com.school.gui.add_IF.AddAdmin_IF;
import com.school.gui.add_IF.AddAttendance_IF;
import com.school.gui.add_IF.AddClassTimetable_IF;
import com.school.gui.add_IF.AddClass_IF;
import com.school.gui.add_IF.AddClassroom_IF;
import com.school.gui.add_IF.AddEmail_IF;
import com.school.gui.add_IF.AddExamReport_IF;
import com.school.gui.add_IF.AddExam_IF;
import com.school.gui.add_IF.AddStudent_IF;
import com.school.gui.add_IF.AddSubject_IF;
import com.school.gui.add_IF.AddTeacher_IF;
import com.school.gui.add_IF.AddUserInfoPanel;
import com.school.users.User;

public class ToolbarPanel 
{	
	List<ToolbarUserControls> userControls = new ArrayList<>();
	List<ToolbarAccountsControls> userAccountsControls = new ArrayList<>();
	List<ToolbarAttendanceControls> userAttendanceControls = new ArrayList<>();
	List<ToolbarUserFeatures> userFeatures = new ArrayList<>();
	List<ToolbarSchoolElements> schoolElements = new ArrayList<>();
	final Color lightColor = Color.decode("#dddddd");
	final Color darkColor = Color.decode("#666666");
	private final MainWindowSetup mainWindowSetup = new MainWindowSetup();
	
	public MainWindowSetup getMainWindowSetup() 
	{
		return mainWindowSetup;
	}

	protected static AddStudent_IF addStudent_IF;
	
	public JToolBar createAdminToolbarPanel()
	{
		final JToolBar pToolbarPanel = new JToolBar();
		pToolbarPanel.setBackground(Color.decode("#666666"));
		pToolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pToolbarPanel.setRollover(true);
		pToolbarPanel.add(createUserControlPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		pToolbarPanel.add(createSchoolElementsPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		pToolbarPanel.add(createUserFeaturesPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		
		return pToolbarPanel;
	}
	
	public JToolBar createStudentToolbarPanel()
	{
		final JToolBar pToolbarPanel = new JToolBar();
		pToolbarPanel.setBackground(Color.decode("#666666"));
		pToolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pToolbarPanel.setRollover(true);
		pToolbarPanel.add(createUserFeaturesPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		
		return pToolbarPanel;
	}
	
	public JToolBar createAccountantToolbarPanel()
	{
		final JToolBar pToolbarPanel = new JToolBar();
		pToolbarPanel.setBackground(Color.decode("#666666"));
		pToolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pToolbarPanel.setRollover(true);
		pToolbarPanel.add(createUserAccountsControlPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		pToolbarPanel.add(createUserFeaturesPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		
		return pToolbarPanel;
	}
	
	public JToolBar createTeacherToolbarPanel()
	{
		final JToolBar pToolbarPanel = new JToolBar();
		pToolbarPanel.setBackground(Color.decode("#666666"));
		pToolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pToolbarPanel.setRollover(true);
		pToolbarPanel.add(createAttendanceControlPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		pToolbarPanel.add(createUserFeaturesPanel());
		pToolbarPanel.add(new JSeparator(JSeparator.VERTICAL));
		
		return pToolbarPanel;
	}
	
	public JPanel createUserControlPanel()
	{
		final JPanel pToolbarUserControlPanel = new JPanel();
		pToolbarUserControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		try
		{
			for(final UserType userType : User.listUserTypes)
			{
				final Image image = ImageIO.read(getClass().getResource("/Img/" + 
						userType.toString() + ".png"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 50, ic.getIconWidth() - 50, Image.SCALE_SMOOTH)));
				final ToolbarUserControls pToolbarIcon = new ToolbarUserControls(userType);
				
				pToolbarIcon.add(imageLabel);
				pToolbarIcon.setToolTipText("Add " + userType.name().toLowerCase());
				pToolbarIcon.userType = userType;
				userControls.add(pToolbarIcon);
				
				pToolbarUserControlPanel.add(pToolbarIcon);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return pToolbarUserControlPanel;
	}
	
	private class ToolbarUserControls extends JPanel
	{
		private UserType userType;
		
		public UserType getUserType()
		{
			return this.userType;
		}
		
		ToolbarUserControls(final UserType tmpUserType)
		{
			this.userType = tmpUserType;
			this.setBackground(lightColor);
			setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			this.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					for(ToolbarUserControls userControl : userControls)
					{
						if(userControl.userType.equals(userType))
						{
							switch(userType)
							{
								case STUDENT:
									addStudent_IF = new AddStudent_IF();
									AddUserInfoPanel.setId(DatabaseConnect.getId("userdb"));
									AddUserInfoPanel.setUserId(userType.toString() + AddUserInfoPanel.getId());
									mainWindowSetup.JDPsystemSetup.add(addStudent_IF.addStudent_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								case TEACHER:
									AddTeacher_IF addTeacher = new AddTeacher_IF();
									AddUserInfoPanel.setId(DatabaseConnect.getId("userdb"));
									AddUserInfoPanel.setUserId(userType.toString() + AddUserInfoPanel.getId());
									mainWindowSetup.JDPsystemSetup.add(addTeacher.addTeacher_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								case ADMIN:
									AddAdmin_IF addAdmin = new AddAdmin_IF();
									AddUserInfoPanel.setId(DatabaseConnect.getId("userdb"));
									AddUserInfoPanel.setUserId(userType.toString() + AddUserInfoPanel.getId());
									mainWindowSetup.JDPsystemSetup.add(addAdmin.addAdmin_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								case ACCOUNTANT:
									AddAccountant_IF addAccountant = new AddAccountant_IF();
									AddUserInfoPanel.setId(DatabaseConnect.getId("userdb"));
									AddUserInfoPanel.setUserId(userType.toString() + AddUserInfoPanel.getId());
									mainWindowSetup.JDPsystemSetup.add(addAccountant.addAccountant_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								default:
									GUIInterfaceUtilities.unknownComponentError();
									break;
							}
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					setBackground(darkColor);
					setBorder(BorderFactory.createLineBorder(Color.decode("#3abefa")));
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					setBackground(lightColor);
					setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
		}
		
		@Override
		public JToolTip createToolTip()
		{
			JToolTip tip = super.createToolTip();
	        tip.setBackground(Color.decode("#f8f8ff"));
	        
	        return tip;
		}
	}
	
	public JPanel createUserFeaturesPanel()
	{
		final JPanel pToolbarUserFeaturesPanel = new JPanel();
		pToolbarUserFeaturesPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		try
		{
			for(final UserFeature userFeature : SystemUtils.listUserFeatures)
			{
				final Image image = ImageIO.read(getClass().getResource("/Img/" + 
											userFeature.toString() + ".png"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 50, ic.getIconWidth() - 50, Image.SCALE_SMOOTH)));
				final ToolbarUserFeatures pToolbarIcon = new ToolbarUserFeatures(userFeature);
						
				pToolbarIcon.add(imageLabel);
				pToolbarIcon.setToolTipText(userFeature.name().toLowerCase());
				pToolbarUserFeaturesPanel.add(pToolbarIcon);
				userFeatures.add(pToolbarIcon);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return pToolbarUserFeaturesPanel;
	}
	
	private class ToolbarUserFeatures extends JPanel
	{
		private UserFeature userFeature;
		
		public UserFeature getUserFeature()
		{
			return this.userFeature;
		}
		
		ToolbarUserFeatures(final UserFeature tmpUserFeature)
		{
			this.setBackground(lightColor);
			this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			this.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					/*for(ToolbarUserFeatures toolbarUserFeature : userFeatures)
					{
						if(toolbarUserFeature.userFeature.equals(userFeature))
						{
							switch(userFeature)
							{
								case EMAIL:
									
									break;	
									
								default:
									GUIInterfaceUtilities.unknownComponentError();
									break;
							}
						}
					}*/
					AddEmail_IF addEmail = new AddEmail_IF();
					mainWindowSetup.JDPsystemSetup.add(addEmail.addEmail_IF(), JLayeredPane.POPUP_LAYER);
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					setBackground(darkColor);
					setBorder(BorderFactory.createLineBorder(Color.decode("#3abefa")));
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					setBackground(lightColor);
					setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
		}
		
		@Override
		public JToolTip createToolTip()
		{
			JToolTip tip = super.createToolTip();
	        tip.setBackground(Color.decode("#f8f8ff"));
	        
	        return tip;
		}
	}	
	
	public JPanel createSchoolElementsPanel()
	{
		final JPanel pSchoolElementsPanel = new JPanel();
		pSchoolElementsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		try
		{
			for(final SchoolElement schoolElement : SystemUtils.listSchoolEntities)
			{
				final Image image = ImageIO.read(getClass().getResource("/Img/" + 
						schoolElement.toString() + ".png"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 50, ic.getIconWidth() - 50, Image.SCALE_SMOOTH)));
				final ToolbarSchoolElements pToolbarIcon = new ToolbarSchoolElements(schoolElement);
						
				pToolbarIcon.add(imageLabel);
				pToolbarIcon.setToolTipText("Add " + schoolElement.name().toLowerCase());
				pSchoolElementsPanel.add(pToolbarIcon);
				
				schoolElements.add(pToolbarIcon);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return pSchoolElementsPanel;
	}
	
	private class ToolbarSchoolElements extends JPanel
	{
		private SchoolElement schoolElement;
		
		public SchoolElement getSchoolElement()
		{
			return this.schoolElement;
		}
		
		ToolbarSchoolElements(final SchoolElement tmpSchoolElement)
		{
			this.schoolElement = tmpSchoolElement;
			this.setBackground(lightColor);
			this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			this.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					for(ToolbarSchoolElements toolbarSchoolElement : schoolElements)
					{
						if(toolbarSchoolElement.schoolElement.equals(schoolElement))
						{
							switch(schoolElement)
							{
								case CLASS:
									AddClass_IF addClass = new AddClass_IF();
									mainWindowSetup.JDPsystemSetup.add(addClass.addClass_IF(), JLayeredPane.POPUP_LAYER);
									break;
									
								case CLASSROOM:
									AddClassroom_IF addClassroom = new AddClassroom_IF();
									AddClassroom_IF.classroom.setClassroomid(DatabaseConnect.getClassroomId("classroom"));
									mainWindowSetup.JDPsystemSetup.add(addClassroom.addClassroom_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								case SUBJECT:
									AddSubject_IF addSubject = new AddSubject_IF();
									mainWindowSetup.JDPsystemSetup.add(addSubject.addSubject_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
								
								case ATTENDANCE:
									AddAttendance_IF addAttendance = new AddAttendance_IF();
									mainWindowSetup.JDPsystemSetup.add(addAttendance.addAttendance_IF(), JLayeredPane.POPUP_LAYER);
									
									break;	
									
								case CLASSTIMETABLE:
									AddClassTimetable_IF addClassTimetable = new AddClassTimetable_IF();
									mainWindowSetup.JDPsystemSetup.add(addClassTimetable.addClassTimetable_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								case EXAM:
									AddExam_IF addExam = new AddExam_IF();
									mainWindowSetup.JDPsystemSetup.add(addExam.addExam_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								case REPORTCARD:
									AddExamReport_IF addExamReport = new AddExamReport_IF();
									mainWindowSetup.JDPsystemSetup.add(addExamReport.addExamReport_IF(), JLayeredPane.POPUP_LAYER);
									
									break;
									
								default:
									GUIInterfaceUtilities.unknownComponentError();
									break;
							}
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					setBackground(darkColor);
					setBorder(BorderFactory.createLineBorder(Color.decode("#3abefa")));
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					setBackground(lightColor);
					setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
		}
		
		@Override
		public JToolTip createToolTip()
		{
			JToolTip tip = super.createToolTip();
	        tip.setBackground(Color.decode("#f8f8ff"));
	        
	        return tip;
		}
	}	

	public JPanel createUserAccountsControlPanel()
	{
		final JPanel pToolbarUserControlPanel = new JPanel();
		pToolbarUserControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		try
		{
			for(final UserAccount userAccount : UserAccount.values())
			{
				final BufferedImage image = ImageIO.read(new File("Img/" + 
											userAccount.toString() + ".png"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 50, ic.getIconWidth() - 50, Image.SCALE_SMOOTH)));
				final ToolbarAccountsControls pToolbarIcon = new ToolbarAccountsControls(userAccount);
				
				pToolbarIcon.add(imageLabel);
				pToolbarIcon.setToolTipText("Update " + userAccount.name().toLowerCase());
				pToolbarIcon.userAccount = userAccount;
				userAccountsControls.add(pToolbarIcon);
				
				pToolbarUserControlPanel.add(pToolbarIcon);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return pToolbarUserControlPanel;
	}
	
	private class ToolbarAccountsControls extends JPanel
	{
		private UserAccount userAccount;
		
		public UserAccount getUserAccount()
		{
			return this.userAccount;
		}
		
		ToolbarAccountsControls(final UserAccount tmpUserAccount)
		{
			this.userAccount = tmpUserAccount;
			this.setBackground(lightColor);
			setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			this.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					for(ToolbarAccountsControls userControl : userAccountsControls)
					{
						if(userControl.userAccount.equals(userAccount))
						{
							switch(userAccount)
							{
								case ACCOUNTS:
									String userTypes[] = UserType.getUsers();
									String userType = (String)JOptionPane.showInputDialog(
											null, 
											"Select user",
									        "User Account To Be Updated", 
									        JOptionPane.QUESTION_MESSAGE, 
									        null, 
									        userTypes, 
									        userTypes[1]);
									
									switch(userType)
									{
										default:
											EditStaff_IF editAccount = new EditStaff_IF();
											mainWindowSetup.JDPsystemSetup.add(editAccount.editStaff_IF(), JLayeredPane.POPUP_LAYER);
											
											break;
											
										case "STUDENT":
											EditStudent_IF editStudentAccount = new EditStudent_IF();
											mainWindowSetup.JDPsystemSetup.add(editStudentAccount.editStudent_IF(), JLayeredPane.POPUP_LAYER);
											
											break;
									}
									
									break;
									
								default:
									GUIInterfaceUtilities.unknownComponentError();
									break;
							}
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					setBackground(darkColor);
					setBorder(BorderFactory.createLineBorder(Color.decode("#3abefa")));
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					setBackground(lightColor);
					setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
		}
		
		@Override
		public JToolTip createToolTip()
		{
			JToolTip tip = super.createToolTip();
	        tip.setBackground(Color.decode("#f8f8ff"));
	        
	        return tip;
		}
	}

	public JPanel createAttendanceControlPanel()
	{
		final JPanel pToolbarUserControlPanel = new JPanel();
		pToolbarUserControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		try
		{
			for(final Attendance userAccount : Attendance.values())
			{
				final BufferedImage image = ImageIO.read(new File("Img/" + 
											userAccount.toString() + ".png"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 50, ic.getIconWidth() - 50, Image.SCALE_SMOOTH)));
				final ToolbarAttendanceControls pToolbarIcon = new ToolbarAttendanceControls(userAccount);
				
				pToolbarIcon.add(imageLabel);
				pToolbarIcon.setToolTipText("Update " + userAccount.name().toLowerCase());
				pToolbarIcon.userAccount = userAccount;
				userAttendanceControls.add(pToolbarIcon);
				
				pToolbarUserControlPanel.add(pToolbarIcon);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return pToolbarUserControlPanel;
	}
	
	private class ToolbarAttendanceControls extends JPanel
	{
		private Attendance userAccount;
		
		public Attendance getAttendance()
		{
			return this.userAccount;
		}
		
		ToolbarAttendanceControls(final Attendance tmpAttendance)
		{
			this.userAccount = tmpAttendance;
			this.setBackground(lightColor);
			setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			this.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					for(ToolbarAttendanceControls userControl : userAttendanceControls)
					{
						if(userControl.userAccount.equals(userAccount))
						{
							switch(userAccount)
							{
								case ATTENDANCE:
									AddAttendance_IF addAttendance = new AddAttendance_IF();
									mainWindowSetup.JDPsystemSetup.add(addAttendance.addAttendance_IF(), JLayeredPane.POPUP_LAYER);
									break;	
									
								default:
									GUIInterfaceUtilities.unknownComponentError();
									break;
							}
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					setBackground(darkColor);
					setBorder(BorderFactory.createLineBorder(Color.decode("#3abefa")));
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					setBackground(lightColor);
					setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
		}
		
		@Override
		public JToolTip createToolTip()
		{
			JToolTip tip = super.createToolTip();
	        tip.setBackground(Color.decode("#f8f8ff"));
	        
	        return tip;
		}
	}
}
