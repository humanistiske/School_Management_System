package com.school.gui.InputValidators;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddStudent_IF;

public class EmailFilter extends DocumentFilter 
{
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN =
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailFilter() 
	{
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
				
	public boolean validate(final String hex) 
	{
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public void insertString(DocumentFilter.FilterBypass fb, int offset, int length,
			String str, AttributeSet attr) throws BadLocationException 
	{	
		if(validate(str))
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
	        AddStudent_IF.error.setText("");
	        super.insertString(fb, offset, str, attr);
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("Invalid input");
			AddStudent_IF.setInputError(true);
		}
	} 
	
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
			String str, AttributeSet attr) throws BadLocationException 
	{	
		if(validate(str))
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
	        AddStudent_IF.error.setText("");
	        super.replace(fb, offset, length, str, attr);
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("Invalid input");
			AddStudent_IF.setInputError(true);
		}
	}	 
}
