package com.school.gui.InputValidators;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PhotoFilter extends FileFilter 
{

	@Override
	public boolean accept(File f) 
	{
		String extension = f.getName();
		
		if(f.isDirectory())
		{
			return true;
		}
			
		
		if(extension.endsWith("jpg") || 
				extension.endsWith("jpeg") ||
				extension.endsWith("png") ||
				extension.endsWith("gif") ||
				extension.endsWith("tiff") ||
				extension.endsWith("tif"))
		{
			return true;
		}
		else
		{
			return false;
		}
			
	}

	@Override
	public String getDescription() 
	{
		return "Images Only";
	}
	
	
}
