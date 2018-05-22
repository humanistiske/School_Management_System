package com.school.gui.InputValidators;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddClass_IF;


public class ClassIdFilter extends DocumentFilter
{
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
			String text, AttributeSet attr) throws BadLocationException 
	{	
		int previousLength = fb.getDocument().getLength();
		if(previousLength > 1)
		{
			if(Integer.parseInt(text) > 10 | Integer.parseInt(text) < 1)
			{
				AddClass_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.red),
						BorderFactory.createEmptyBorder(0, 10, 0, 0)));
				AddClass_IF.error.setText("Class ID should be between 0 - 11");
				AddClass_IF.setInputError(true);

				fb.insertString(offset, text.replaceAll(".", ""), attr);
		
			}
		}
		else
		{
			AddClass_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddClass_IF.error.setText("");

			fb.insertString(offset, text.replaceAll("[^0-9]", ""), attr);
		}	
	} 
}
