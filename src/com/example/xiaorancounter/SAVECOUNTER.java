package com.example.xiaorancounter;


import java.io.Serializable;
import java.util.Date;
// this basically is a class of the counter object, I create the getters and setters in order to access
//the information for each counter

/**
 * @author  xiaoran1
 */
@SuppressWarnings("serial")
public class SAVECOUNTER implements Serializable
{
	/**
	 * @uml.property  name="name_value"
	 */
	private String name_value;
	/**
	 * @uml.property  name="count_value"
	 */
	private int count_value;
	/**
	 * @uml.property  name="date_value"
	 */
	private Date date_value;
	
	public SAVECOUNTER(String name, int counts, Date date) 
	{
		this.name_value = name;
		this.count_value = counts;
		this.date_value = date;
	}
	
	/**
	 * @return
	 * @uml.property  name="name_value"
	 */
	public String getName_value()
	{
	
		return name_value;
	}


	
	/**
	 * @param name_value
	 * @uml.property  name="name_value"
	 */
	public void setName_value(String name_value)
	{
	
		this.name_value = name_value;
	}


	
	/**
	 * @return
	 * @uml.property  name="count_value"
	 */
	public int getCount_value()
	{
	
		return count_value;
	}


	
	/**
	 * @param count_value
	 * @uml.property  name="count_value"
	 */
	public void setCount_value(int count_value)
	{
	
		this.count_value = count_value;
	}


	
	/**
	 * @return
	 * @uml.property  name="date_value"
	 */
	public Date getDate_value()
	{
	
		return date_value;
	}


	
	/**
	 * @param date_value
	 * @uml.property  name="date_value"
	 */
	public void setDate_value(Date date_value)
	{
	
		this.date_value = date_value;
	}

}
