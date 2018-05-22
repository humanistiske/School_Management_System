package com.school;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public final class GUIInterfaceUtilities 
{
	public static final int TEXTFIELD_SIZE = 15;
	
	public static void unknownComponentError()
	{
		JOptionPane.showMessageDialog(null, "Oops unknown selection!!!");
	}
	
	public static BufferedImage getScreenShot(Component component)
	{
		BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
		component.paint(image.getGraphics());
		return image;
	}
	
	public static void saveScreenShot(Component component, String filename)
	{
		try
		{
			BufferedImage image = getScreenShot(component);
			ImageIO.write(image, "png", new File(filename));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
