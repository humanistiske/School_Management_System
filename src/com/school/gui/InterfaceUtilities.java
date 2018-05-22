package com.school.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InterfaceUtilities 
{
	public static void unknownComponentError()
	{
		JOptionPane.showMessageDialog(null, "Oops unknown selection!!!");
	}
	
	public static boolean tfIsDigitVerifier(JTextField textField)
	{
		textField.addKeyListener(new KeyAdapter()
				{
					boolean res;
					public void keyPressed(KeyEvent e)
					{
						char ch = e.getKeyChar();
						if(!Character.isDigit(ch))
						{
							res = true;
						}
						else
						{
							res = false;
						}
					}
				});
		return false;
	}
}
