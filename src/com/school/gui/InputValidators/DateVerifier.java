package com.school.gui.InputValidators;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.school.gui.add_IF.AddStudent_IF;

public class DateVerifier extends InputVerifier
{	
	public boolean validate(String input)
	{
		try
		{
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(input, format);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	@Override
	public boolean verify(JComponent comp) 
	{
		String input = ((JTextField)comp).getText();
		if(validate(input))
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("");
			
			return true;
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText(comp.getName() + " is invalid");
			
			return false;
		}
	}
}
 

