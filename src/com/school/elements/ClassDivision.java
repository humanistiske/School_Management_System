package com.school.elements;

public enum ClassDivision 
{
	A("A"),
	B("B"),
	C("C");
	
	private String division;
  
	ClassDivision(String tmpDivision)
	{
		division = tmpDivision;
	}
	
	@Override
	public String toString()
	{
		return this.division;
	}
}
