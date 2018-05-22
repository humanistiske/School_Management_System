package com.school.gui.InputValidators;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.school.gui.add_IF.AddStudent_IF;

public class JCompInputVerifier extends InputVerifier
{
	@Override
	public boolean verify(JComponent comp) 
	{
		String input = ((JTextField)comp).getText();
		if(input.isEmpty())
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText(comp.getName() + " cannot be blank");
			AddStudent_IF.setInputError(true);
			
			return false;
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("");
			
			return true;
		}
	}	
}
