package com.parseexception;

import com.parseexception.model.ObjectCache;

import java.util.*;

public class IPInfo {
	private int ipaddr;
	private int pageviews = 0;
	private Date timeperiod_start;
	private boolean isSpammer = false;
	
	private static int convertIp(String ip)
	{
		String parts[] = ip.split(".");
		int ret = 0;
		for(int i = 0; i < 4 && i < parts.length; i++)
		{
			ret = (ret << 8) | Integer.parseInt(parts[i]);
		}
		
		return ret;
	}
	
	private IPInfo(String ip)
	{
		ipaddr = convertIp(ip);
		timeperiod_start = new Date();
	}
	
	public static IPInfo getIPInfo(String ip)
	{
		ObjectCache cache = ObjectCache.getInstance();
		int ipint = convertIp(ip);
		IPInfo ipinfo = (IPInfo) cache.lookupObject(ipint, "IPInfo");
		if(ipinfo == null)
		{
			ipinfo = new IPInfo(ip);
			cache.addObject(ipinfo, ipint, "IPInfo");
		}
		return ipinfo;
	}
	
	public int getIP()
	{
		return ipaddr;
	}
	
	public void incrementPV()
	{
		pageviews++;
	}
	
	public int getPV()
	{
		return pageviews;
	}
	
	public void reset()
	{
		timeperiod_start = new Date();
		pageviews = 0;
	}
	
	public long getRequestPeriodMs()
	{
		Date curTime = new Date();
		return curTime.getTime() - timeperiod_start.getTime();
	}
	
	public void setIsSpammer(boolean b)
	{
		isSpammer = b;
	}
	
	public boolean getIsSpammer()
	{
		return isSpammer;
	}
}
