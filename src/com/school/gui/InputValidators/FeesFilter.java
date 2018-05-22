package com.school.gui.InputValidators;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddStudent_IF;

public class FeesFilter extends DocumentFilter 
{
	private static final int MAX_AMOUNT = 10;
	
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
						String str, AttributeSet attr) throws BadLocationException 
	{	
        String text = fb.getDocument().getText(0,
                fb.getDocument().getLength());
        text += str;
        if ((fb.getDocument().getLength() + str.length() - length) <= MAX_AMOUNT
                && text.matches("^[0-9]+[.]?[0-9]{0,1}$")) 
        {
            super.replace(fb, offset, length, str, attr);
            AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
            AddStudent_IF.error.setText("");
            
			fb.insertString(offset, text.replaceAll("^[0-9]+[.]?[0-9]{0,1}$", ""), attr);
		}
		else
		{
			AddStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddStudent_IF.error.setText("Invalid input or amount exceeds limit");
			AddStudent_IF.setInputError(true);
			
			fb.insertString(offset, text.replaceAll(".", ""), attr);
		}	
	} 
}
 



