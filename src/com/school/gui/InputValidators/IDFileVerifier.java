package com.school.gui.InputValidators;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.school.gui.add_IF.AddStudent_IF;

public class IDFileVerifier extends InputVerifier
{
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String FILE_PATH_PATTERN =
								"([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";

	public IDFileVerifier() 
	{
		pattern = Pattern.compile(FILE_PATH_PATTERN);
	}
				
	public boolean validate(final String hex) 
	{
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	
	
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
			
			return false;
		}
		else
		{
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
}
