package com.parseexception.model;

import java.sql.*;
import java.util.*;

import com.parseexception.Log;

public class StatementHandle {
	private static Vector<StatementHandle> handleList;
	private HashMap<Connection, PreparedStatement> statementCache;
	
	static
	{
		handleList = new Vector<StatementHandle>(100);
	}
	
	public StatementHandle()
	{
		int size = (int) (DataAccess.poolSize / 0.75) + 1;
		statementCache = new HashMap<Connection, PreparedStatement>(size);
		handleList.add(this);
	}
	
	/*
	 * Delete any prepared statements for broken connections so the hash tables
	 * don't get cluttered.
	 */
	public static synchronized void garbageCollect()
	{
		for(int i = 0; i < handleList.size(); i++)
		{
			StatementHandle hndl = handleList.get(i);
			Set<Connection> keySet = hndl.statementCache.keySet();
			Iterator<Connection> iter = keySet.iterator();
			while(iter.hasNext())
			{
				Connection c = null;
				try
				{
					c = iter.next();
					if(c.isClosed())
					{
						hndl.statementCache.remove(c);
					}
				}catch(Exception e)
				{
					if(c != null)
						hndl.statementCache.remove(c);
				}
			}
		}
	}
	
	/*
	 * Method Name: getPreparedStatement
	 * Description: Returns a prepared statement for the given query
	 * Arguments:
	 * 		Connection conn - The connection to use
	 * 		String query - The query to execute
	 * 		String[] keys - The column names to return (null if n/a)
	 */
	public synchronized PreparedStatement getPreparedStatement(Connection conn, 
											 				   String query, 
											 				   String[] keys)
	{
		PreparedStatement stmt = null;
		if((stmt = statementCache.get(conn)) == null)
		{
			try
			{
				if(keys != null)
				{
					stmt = conn.prepareStatement(query, keys);
				}else{
					stmt = conn.prepareStatement(query);
				}
			}catch(Exception e)
			{
				Log.log(e, Log.FATAL_ERROR);
			}
			
			statementCache.put(conn, stmt);
		}
		
		return stmt;
	}
	
	public synchronized void clear()
	{
		statementCache.clear();
	}
}
