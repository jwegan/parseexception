package com.parseexception.model;

import java.sql.*;
import java.util.*;

public class DBConnectionPool {
	private Driver jdbcDriver;
	private static Vector<ConnectionHandle> freeConn;
	private static Vector<Connection> allocatedConn;
	private String username;
	private String password;
	private String db_url;
	private int curSize;
	private int maxPoolSize;
	
	public DBConnectionPool(String uname, String pword, String url, int maxConn)
	{
		username = uname;
		password = pword;
		db_url = url;
		maxPoolSize = maxConn;
		curSize = 0;
		freeConn = new Vector<ConnectionHandle>(maxConn);
		allocatedConn = new Vector<Connection>(maxConn);
		
		try
		{
			jdbcDriver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(jdbcDriver);
		}catch(Exception e)
		{
			System.err.print(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * Adds a connection back to the pool once it is done
	 */
	public synchronized void freeConnection(Connection c)
	{
		allocatedConn.remove(c);
		freeConn.add(new ConnectionHandle(c));
		notifyAll();
	}
	
	/*
	 * Returns a connection. Will block until one becomes available
	 */
	public synchronized Connection getConnection()
	{
		Connection conn = null;
		while((conn = getConnectionFromPool()) == null)
		{
			if(conn == null && allocatedConn.size() == 0 && freeConn.size() == 0)
				break;
			
			try
			{
				wait();
			}catch(Exception e)
			{
				// Do nothing
			}
		}
		
		return conn;
	}
	
	/*
	 * Determines if there is a free connection
	 */
	private synchronized Connection getConnectionFromPool()
	{
		// Get a connection from the pool if there is one
		Connection conn = null;
		while(freeConn.size() > 0)
		{
			ConnectionHandle connHandle = freeConn.remove(freeConn.size() - 1);
			conn = connHandle.getConnection();
			try
			{
				if(conn.isClosed() || !connHandle.isValid())
				{
					curSize--;
					System.err.println("Removed bad connection");
					conn = null;
				}
				
			}catch(Exception e)
			{
				curSize--;
				System.err.print(e.getMessage());
				e.printStackTrace();
				conn = null;
			}
			
			if(conn != null)
				break;
		}
		
		// Else create a new connection if allowed
		if(conn == null && curSize < maxPoolSize)
		{
			conn = createConnection();
			if(conn != null)
			{
				curSize++;
			}
		}
		
		// Add it to the allocated list
		if(conn != null)
		{
			allocatedConn.add(conn);
		}
		
		return conn;
	}
	
	/*
	 * On an exception, this is called to remove any connections that may
	 * have gone bad
	 */
	public synchronized void garbageCollect(Statement stmt)
	{
		for(int i = 0; i < allocatedConn.size(); i++)
		{
			Connection conn = allocatedConn.elementAt(i);
			try
			{
				if(conn.isClosed() || (stmt != null && conn.equals(stmt.getConnection())))
				{
					conn.close();
					allocatedConn.remove(i--);
					curSize--;
					System.err.println("Bad connection found.");
				}
			}catch(Exception e)
			{
				allocatedConn.remove(i--);
				curSize--;
				System.err.print(e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
	
	
	/*
	 * Creates a connection
	 */
	private synchronized Connection createConnection()
	{
		StringBuilder str = new StringBuilder(db_url);
		str.append("?user=");
		str.append(username);
		str.append("&password=");
		str.append(password);
		str.append("&autoReconnect=true");
		
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection(str.toString());
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		}catch(Exception e)
		{
			System.err.println(str.toString());
			System.err.print(e.getMessage());
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
