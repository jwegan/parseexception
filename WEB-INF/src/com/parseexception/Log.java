package com.parseexception;

import java.io.*;

public class Log {
	
	public static int FATAL_ERROR = 0;
	public static int NONFATAL_ERROR = 1;
	public static int INFO = 2;
	public static int DEBUG = 3;
	public static int MAX_LEVEL = 3;
	
	private static int debugLevel = 3;
	private static PrintStream out;
	
	public static void log(Exception e, int level)
	{
		if(level > debugLevel)
			return;
		
		try
		{
			out.println(e.getMessage());
			e.printStackTrace(out);
			out.flush();
		}catch(Exception e2)
		{
			// Do nothing
		}
	}
	
	public static void log(String s, int level)
	{
		if(level > debugLevel)
			return;
		
		try{
			out.println(s);
			out.flush();
		}catch(Exception e)
		{
			// Do nothing
		}
	}
	
	public static void initLogger(String fileName, int level)
	{
		debugLevel = level;
		
		try
		{
			if(fileName == null)
			{
				out = System.err;
			}else
			{
				out = new PrintStream(fileName);
			}
		}catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void finalize()
	{
		out.close();
	}

}
