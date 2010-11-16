package com.parseexception.model;

import java.io.Serializable;
import java.util.*;

public class Column implements Serializable {
	private static final long serialVersionUID = -3290389927110775026L;
	
	public static transient int INTEGER = 0;
	public static transient int STRING = 1;
	public static transient int BOOLEAN = 2;
	public static transient int TIMESTAMP = 3;
	
	private String column_name;
	private int column_type;
	
	private int int_value;
	private String str_value;
	private boolean bool_value;
	private Date date_value;

	public Column(String colname, int type)
	{
		column_name = colname;
		column_type = type;
	}
	
	public String getColumnName()
	{
		return column_name;
	}
	
	public int getType()
	{
		return column_type;
 	}
	
	public int getInt()
	{
		return int_value;
	}
	
	public void setInt(int i)
	{
		int_value = i;
	}
	
	public String getString()
	{
		return str_value;
	}
	
	public void setString(String s)
	{
		str_value = s;
	}
	
	public boolean getBool()
	{
		return bool_value;
	}
	
	public void setBool(boolean b)
	{
		bool_value = b;
	}
	
	public void setTimestamp(Date d)
	{
		date_value = d;
	}
	
	public String getTimestamp()
	{
		Date curTime = new Date();
		long diff = curTime.getTime() - date_value.getTime();
		
		long value = 0;
		String unit = "";
		
		if(diff < 1000)
		{
			value = diff;
			unit = "millisecond";
		}else if(diff < (60 * 1000))
		{
			value =  diff / 1000;
			unit = "second";
		}else if(diff < (60 * 60 * 1000))
		{
			value = diff / (60 * 1000);
			unit = "minute";
		}else if(diff < (24 * 60 * 60 * 1000))
		{
			value = diff / (60 * 60 * 1000);
			unit = "hour";
		}else if(diff < (365 * 24 * 60 * 60 * 1000))
		{
			value = diff / (24 * 60 * 60 * 1000);
			unit = "day";
		}else
		{
			value = diff / (365 * 24 * 60 * 60 *1000);
			unit = "year";
		}
		
		StringBuilder retstr = new StringBuilder();
		retstr.append(value);
		retstr.append(' ');
		retstr.append(unit);
		if(value > 1)
			retstr.append('s');
		retstr.append(" ago");
		return retstr.toString();
	}
}
