package com.school.gui.InputValidators;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorLog 
{
	public final JPanel pInputError = new JPanel();
	public final JLabel error = new JLabel();
	private boolean inputError = false;

	public boolean isInputError() 
	{
		return inputError;
	}

	public void setInputError(boolean tmpInputError) 
	{
		inputError = tmpInputError;
	}
}
