package com.school.gui.InputValidators;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddStudent_IF;

public class EmailVerifier extends InputVerifier
{	
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN =
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailVerifier() 
	{
		pattern = Pattern.compile(EMAIL_PATTERN);
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
