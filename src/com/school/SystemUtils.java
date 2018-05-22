package com.school;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SystemUtils 
{
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 700;
	public static final String[] DOC_FILE_FORMATS = {"jpeg", "jpg", "png", "pdf", "doc", "docx"};
	
  	//List saving all UserFeatures
  	public static final List<UserFeature> listUserFeatures = new ArrayList<>(Arrays.asList(UserFeature.values()));
  	//All School related features
  	public static final List<SchoolElement> listSchoolEntities = new ArrayList<>(Arrays.asList(SchoolElement.values()));
  	
  	public static final Object getKeyFromValue(Map<Integer, String> map, Object value)
  	{
  		for(Object obj : map.keySet())
  		{
  			if(map.get(obj).equals(value))
  			{
  				return obj;
  			}
  		}
  		return null;
  	}
}
