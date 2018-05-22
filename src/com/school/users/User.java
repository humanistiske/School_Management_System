package com.school.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.school.*;

public abstract class User 
{
	//common attributes of all users 
	private String userid;
	private static UserType userType;
	private static int id;
	private String fName;
	private String lName;
	private String fullName;
	private String address;
  	private String emailid;
  	private long phone;
  	private long altPhone;
  	private String password;
	private File photoFile;
	private FileInputStream photoFileInputStream;
  	private File idFile;
	private FileInputStream idFileInputStream;
  	
	//List saving all UserTypes to match them further
  	public static final List<UserType> listUserTypes = new ArrayList<>(Arrays.asList(UserType.values()));
  	//List saving all UserFeatures
  	public static final List<UserFeature> listUserFeatures = new ArrayList<>(Arrays.asList(UserFeature.values()));

  	protected User(final int userid)
  	{
  		super();
  		this.setId(userid);
  	}
  	
  	User(final String userid, final int id, final UserType userType)
  	{
  		super();
  		this.userid = userid;
  		this.id = id;
  		this.userType = userType;
  	}
  	
	public void setUserid(String userid) 
	{
		this.userid = userid;
	}
	
	public String getUserid() 
	{
		return this.userid;
	}

	public static int getId() 
	{
		return id;
	}

	public static void setId(final int tmpId) 
	{
		id = tmpId;
	}

	public static void setUserType(final UserType tmpUserType) 
	{
		userType = tmpUserType;
	}
	
	public static UserType getUserType() 
	{
		return userType;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getFullName() 
	{
		return fullName;
	}

	public void setFullName(String fName, String lName) 
	{
		this.fullName = fName.concat(" " + lName);
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getEmailid() 
	{
		return emailid;
	}

	public void setEmailid(String emailid) 
	{
		this.emailid = emailid;
	}

	public long getPhone() 
	{
		return phone;
	}

	public void setPhone(long phone) 
	{
		this.phone = phone;
	}

	public long getAltPhone() 
	{
		return altPhone;
	}

	public void setAltPhone(long altPhone) 
	{
		this.altPhone = altPhone;
	}

  	public String getPassword() 
  	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
	
  	public File getPhotoFile() 
  	{
		return photoFile;
	}

	public void setPhotoFile(File photoFile) 
	{
		this.photoFile = photoFile;
	}

	public File getIdFile() 
	{
		return idFile;
	}

	public void setIdFile(File idFile) 
	{
		this.idFile = idFile;
	}
	
  	public FileInputStream getPhotoFileInputStream() 
  	{
		return photoFileInputStream;
	}

	public void setPhotoFileInputStream(File photoFile) 
	{
		try 
		{
			this.photoFileInputStream = new FileInputStream(photoFile);
		} 
		catch (FileNotFoundException e) 
		{	
			System.out.println(e);
		}
	}

	public FileInputStream getIdFileInputStream() 
	{
		return idFileInputStream;
	}

	public void setIdFileInputStream(File idFile) 
	{
		try 
		{
			this.idFileInputStream = new FileInputStream(idFile);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e);
		}
	}
	
  	public static final boolean checkUserType(final String tmpUserid)
  	{
  		boolean res = true;
  		//removes letters from userid
  		String tmpId = tmpUserid.replaceAll("[^0-9]", "");
  		//removes numbers from userid
  		String tmpUserType = tmpUserid.replace(tmpId, "");
  		
  		//check if entered userType matches list of all userTypes
  		for(final UserType userType : listUserTypes)
  		{
  			if(tmpUserType.equals(userType.toString()))
  			{
  				setId(Integer.parseInt(tmpId));
  				setUserType(userType);
  				res = false;
  				break;
  			}
  			else
  				res = true;
  		}
  		
  		return res;
  	}
}
