package com.school.gui;

import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JSplitPane;

import com.school.SystemUtils;

public class SystemWindowSetup 
{		    
	public final static int IF_WIDTH = SystemUtils.WINDOW_WIDTH + 90;
	public final static int IF_HEIGHT = SystemUtils.WINDOW_HEIGHT - 220;
	
	private final static JDesktopPane systemInterface = new JDesktopPane();
	private final static JSplitPane systemPanels = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	 
	public static JDesktopPane createSystemSetup()
	{
		systemPanels.setBounds(0, 0, IF_WIDTH, IF_HEIGHT);
		systemPanels.setOneTouchExpandable(true);
	    systemPanels.setLeftComponent(NavigationPanel.getNavigationPanel());
	    systemPanels.setRightComponent(InterfacePanel.createInterfacePanel());
	    systemPanels.setDividerLocation(150);
	    systemInterface.setPreferredSize(new Dimension(IF_WIDTH, IF_HEIGHT));
	    systemInterface.add(systemPanels);
	    
		return systemInterface;
	}
}
