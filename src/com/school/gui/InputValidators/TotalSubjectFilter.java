package com.school.gui.InputValidators;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddStudent_IF;

public class TotalSubjectFilter extends DocumentFilter 
{
	private static final int MAX_AMOUNT = 3;
	
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
						String str, AttributeSet attr) throws BadLocationException 
	{	
        String text = fb.getDocument().getText(0,
                fb.getDocument().getLength());
        text += str;
        if ((fb.getDocument().getLength() + str.length() - length) <= MAX_AMOUNT
                && text.matches("^[0-9]")) 
        {
            super.replace(fb, offset, length, str, attr);
            AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
            AddStudent_IF.error.setText("");
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("Invalid input");
			AddStudent_IF.setInputError(true);
			
			fb.insertString(offset, text.replaceAll(".", ""), attr);
		}	
	} 
}