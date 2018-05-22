package com.school.elements;

import java.util.ArrayList;
import java.util.List;

public class StaffDesignation 
{
	private final List<String> staffDesignations = new ArrayList<>();
	
	public List<String> getStaffDesignations()
	{
		staffDesignations.add("Teacher");
		staffDesignations.add("Accountant");
		staffDesignations.add("Admin");
		staffDesignations.add("SubAdmin");
		
		return this.staffDesignations;
	}
}
