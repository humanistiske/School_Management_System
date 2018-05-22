package com.school.gui.InputValidators;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.school.gui.add_IF.AddClassroom_IF;

public class ClassroomCapacityFilter extends DocumentFilter
{
	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
			String text, AttributeSet attr) throws BadLocationException 
	{	
		int previousLength = fb.getDocument().getLength();
		int number = 0;
		
		try
		{
			number = Integer.parseInt(text);
		}
		catch(Exception e) {}
		
		if(previousLength > 1)
		{
			AddClassroom_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddClassroom_IF.error.setText("Class capacity should be between 0 - 100");

			fb.insertString(offset, text.replaceAll(".", ""), attr);
		}
		else if(number > 99 | number < 1)
		{
			AddClassroom_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.red),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddClassroom_IF.error.setText("Class capacity should be between 0 - 100");
			AddClassroom_IF.setInputError(true);

			fb.insertString(offset, text.replaceAll(".", ""), attr);
		}	
		else
		{
			AddClassroom_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.green),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
			AddClassroom_IF.error.setText("");
			AddClassroom_IF.setInputError(false);

			fb.insertString(offset, text.replaceAll("[^0-9]", ""), attr);
		}
	}
}
