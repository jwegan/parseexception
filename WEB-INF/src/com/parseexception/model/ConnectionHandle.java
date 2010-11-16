package com.parseexception.model;

import java.sql.*;
import java.util.Date;

public class ConnectionHandle {
	
	private Connection conn;
	private Date created;
	
	public ConnectionHandle(Connection c)
	{
		conn = c;
		created = new Date();
	}
	
	public Connection getConnection()
	{
		return conn;
	}
	
	public boolean isValid()
	{
		Date now = new Date();
		return (now.getTime() - created.getTime()) < (7 * 60 * 60 * 1000);
	}
}
