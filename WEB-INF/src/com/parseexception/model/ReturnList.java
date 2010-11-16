package com.parseexception.model;

import java.util.*;

public class ReturnList<T> {
	public long creationTime; 
	public List<T> list;
	public boolean bMore = false;
	
	public ReturnList()
	{
		Date curTime = new Date();
		creationTime = curTime.getTime();
		list = new Vector<T>();
	}
}
