package com.school.gui.InputValidators;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddStudent_IF;

public class JTextAreaInputVerifier extends DocumentFilter 
{
	public void insertString(DocumentFilter.FilterBypass fb, int offset, int length,
			String text, AttributeSet attr) throws BadLocationException 
	{	
		int previousLength = fb.getDocument().getLength();
		System.out.println(previousLength);
		if(!(previousLength >= 0))
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("This field cannot be empty");
			AddStudent_IF.setInputError(true);
		}
		else
		{
			super.insertString(fb, offset, text, attr);
		}
	} 
	
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
						String text, AttributeSet attr) throws BadLocationException 
	{	
		int previousLength = fb.getDocument().getLength();
		if(!(previousLength >= 0))
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("This field cannot be empty");
			AddStudent_IF.setInputError(true);
		}
		else
		{
			super.replace(fb, offset, length, text, attr);
		}
	} 
}
