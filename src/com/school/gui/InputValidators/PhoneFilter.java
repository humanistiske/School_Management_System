package com.school.gui.InputValidators;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddStudent_IF;

public class PhoneFilter extends DocumentFilter 
{
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
						String text, AttributeSet attr) throws BadLocationException 
	{	
		int previousLength = fb.getDocument().getLength();
		if(previousLength > 10)
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("Phone no. cannot be more than 10 digits");
			AddStudent_IF.setInputError(true);
			
			fb.insertString(offset, text.replaceAll(".", ""), attr);
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("");
			
			fb.insertString(offset, text.replaceAll("[^0-9]", ""), attr);
		}	
	} 
}
