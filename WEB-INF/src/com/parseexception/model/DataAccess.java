package com.parseexception.model;

import java.sql.*;
import java.text.DateFormat;
import java.util.*;

import com.parseexception.*;

public class DataAccess {
	public static int poolSize = 30;
	public static DBConnectionPool pool;
	static
	{
		pool = new DBConnectionPool(Config.db_user, Config.db_password, 
									Config.db_url, Config.db_maxconn);
	}
	
	/*
	 * Method Name: query
	 * Description: Used to execute a query, the ResultSet should be closed
	 * 				when finished.
	 */
	public static Statement getStatement(){
        // Setup connection if not up
        Connection conn = pool.getConnection();
        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            stmt.setEscapeProcessing(true);
        }
        catch(Exception e)
        {
        	Log.log(e, Log.FATAL_ERROR);
    		DataAccess.pool.garbageCollect(stmt);
        }

        return stmt;
	}
	
	/*
	 * Method Name: query
	 * Arguments:
	 * 		List<PreparedStatement> pvec - A container to hold a single preparedstatement
	 * 		String query - The query to execute (in prepared statement form)
	 * 		List<Object> args - A list of arguments to insert into the prepared statement
	 * 
	 * Description: Used to execute a query, the ResultSet should be closed
	 * 				when finished.
	 */	
	public static PreparedStatement query(StatementHandle hndl, 
										  String query, 
										  List<Object> args){
        // Setup connection if not up
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;
		try{
			stmt = hndl.getPreparedStatement(conn, query, null);

			for(int i = 0; i < args.size(); i++){
				Object o = args.get(i);
				
				if(o instanceof Integer){
					int arg = ((Integer) o).intValue();
					stmt.setInt(i+1, arg);
				}else if(o instanceof Float){
					float arg = ((Float) o).floatValue();
					stmt.setFloat(i+1, arg);
				}else if(o instanceof Double){
					double arg = ((Double) o).doubleValue();
					stmt.setDouble(i+1, arg);
				}else if(o instanceof Boolean){
					boolean arg = ((Boolean) o).booleanValue();
					stmt.setBoolean(i+1, arg);
				}else if(o instanceof String){
					stmt.setString(i+1, (String) o);
				}else if(o instanceof NullValue){
					stmt.setNull(i+1, ((NullValue) o).type);
				}
			}
		}catch(Exception e){
			Log.log(e, Log.NONFATAL_ERROR);
		}
		
		return stmt;
	}
	
	/*
	 * queryReturnResults
	 */
	public static List<Column> queryReturnColumns(StatementHandle hndl, String query, 
										   List<Object> args, List<Column> results)
	{
		PreparedStatement stmt = null;
		
		try
		{
			stmt = query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				for(int i = 0; i < results.size(); i++)
				{
					Column col = results.get(i);
					if(col.getType() == Column.BOOLEAN)
					{
						col.setBool(rs.getBoolean(col.getColumnName()));
					}else if(col.getType() == Column.INTEGER)
					{
						col.setInt(rs.getInt(col.getColumnName()));
					}else if(col.getType() == Column.STRING)
					{
						String str = rs.getString(col.getColumnName());
						col.setString((str == null)? "" : str);
					}else if(col.getType() == Column.TIMESTAMP)
					{
						Timestamp ts = rs.getTimestamp(col.getColumnName());
						col.setTimestamp((ts == null)? new java.util.Date() : ts);
					}
				}
			}
			
			rs.close();
			pool.freeConnection(stmt.getConnection());
		}catch(Exception e)
		{
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return results;
	}
	
	/*
	 * Method: queryReturnString
	 * Description: Wrapper to extract a string from a queries result
	 */
	public static String queryReturnString(StatementHandle hndl, String query, 
										   List<Object> args, String colname){
		String ret = "";
		PreparedStatement stmt = null;
		
		try{
			stmt = query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				ret = rs.getString(colname);
				if(ret == null)
					ret = "";
			}
			rs.close();
			pool.freeConnection(stmt.getConnection());
		}catch(Exception e){
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}	

	/*
	 * Method: queryReturnInt
	 * Description: Wrapper to extract a int from a queries result
	 */	
	public static int queryReturnInt(StatementHandle hndl, String query, 
								     List<Object> args, String colname){
		int ret = 0;
		PreparedStatement stmt = null;
		
		try{
			stmt = query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				ret = rs.getInt(colname);
			}
			rs.close();
			pool.freeConnection(stmt.getConnection());
		}catch(Exception e){
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}		
	
	/*
	 * Method: queryReturnBool
	 * Description: Wrapper to extract a boolean from a queries result
	 */	
	public static boolean queryReturnBool(StatementHandle hndl, String query, 
										  List<Object> args, String colname){
		boolean ret = false;
		PreparedStatement stmt = null;
		
		try{
			stmt = query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				ret = rs.getBoolean(colname);
			}
			rs.close();
			pool.freeConnection(stmt.getConnection());
		}catch(Exception e){
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}	
	
	/*
	 * Method: queryReturnTimestamp
	 * Description: Wrapper to extract a timestamp from a queries result
	 */
	public static String queryReturnTimestamp(StatementHandle hndl, String query,
											  List<Object> args, String colname){
		String ret = "";
		PreparedStatement stmt = null;
		
		try{
			stmt = query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			Timestamp ts;
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, 
															  	  DateFormat.LONG,
															  	  Locale.US);
			if(rs.next()){
				ts = rs.getTimestamp(colname);
				ret = formatter.format(ts);
			}
			rs.close();
			pool.freeConnection(stmt.getConnection());
		}catch(Exception e){
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}

	/*
	 * Method: createEntry
	 * Arguments:
	 * 		StatementHandle hndl - A container to hold a single prepared statement
	 * 		String query - The insertion statement to execute (in prepared statement form)
	 * 		String colname - The name of the primary key column
	 * 
	 * Description: Inserts an entry into a table and returns the primary key
	 * Note: Add and 
	 */
	public static int createEntry(StatementHandle hndl, String query, String colname, List<Object> args){
		int ret = -1;
		
		Connection conn = pool.getConnection();
		PreparedStatement stmt = null;
		try{
			String keys[] = new String[1];
			keys[0] = colname;
			stmt = hndl.getPreparedStatement(conn, query, keys);
			
			for(int i = 0; i < args.size(); i++){
				Object o = args.get(i);
				
				if(o instanceof Integer){
					int arg = ((Integer) o).intValue();
					stmt.setInt(i+1, arg);
				}else if(o instanceof Float){
					float arg = ((Float) o).floatValue();
					stmt.setFloat(i+1, arg);
				}else if(o instanceof Double){
					double arg = ((Double) o).doubleValue();
					stmt.setDouble(i+1, arg);
				}else if(o instanceof Boolean){
					boolean arg = ((Boolean) o).booleanValue();
					stmt.setBoolean(i+1, arg);
				}else if(o instanceof String){
					stmt.setString(i+1, (String) o);
				}else if(o instanceof NullValue){
					stmt.setNull(i+1, ((NullValue) o).type);
				}
			}
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			
			if(!rs.first()){
				System.err.println("Error executing: " + query);
			}else{
				ret = rs.getInt(1);
			}
			
			rs.close();
			pool.freeConnection(conn);
		}catch(Exception e){
			Log.log(e, Log.FATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}
	
	/* 
	 * Method Name: update
	 * Description: Used for performing an update
	 */
	public static int update(String sql){
        // Setup connection if not up
        Connection conn = pool.getConnection();
        int results = 0;
        Statement stmt = null;
        
        try
        {
            stmt = conn.createStatement();
            stmt.setEscapeProcessing(true);
            
            if(!stmt.execute(sql))
            {
               results = stmt.getUpdateCount();
            }
            
            stmt.close();
            pool.freeConnection(conn);
        }
        catch(Exception e)
        {
        	Log.log(e, Log.FATAL_ERROR);
        	DataAccess.pool.garbageCollect(stmt);
        }

        return results;
	}
	
	/* 
	 * Method Name: update
	 * Arguments:
	 * 		StatementHandle hndl - A container for holding a single prepared statement
	 * 		String query - The update query to execute
	 * 		List<Object> args - A list of arguments to insert into the prepared statement
	 * Description: Used for performing an update
	 */
	public static boolean update(StatementHandle hndl, String query, List<Object> args){
        // Setup connection if not up
        Connection conn = pool.getConnection();
		boolean ret = false;
		PreparedStatement stmt = null;
		
		try{
			stmt = hndl.getPreparedStatement(conn, query, null);
			
			for(int i = 0; i < args.size(); i++){
				Object o = args.get(i);
				
				if(o instanceof Integer){
					int arg = ((Integer) o).intValue();
					stmt.setInt(i+1, arg);
				}else if(o instanceof Float){
					float arg = ((Float) o).floatValue();
					stmt.setFloat(i+1, arg);
				}else if(o instanceof Double){
					double arg = ((Double) o).doubleValue();
					stmt.setDouble(i+1, arg);
				}else if(o instanceof Boolean){
					boolean arg = ((Boolean) o).booleanValue();
					stmt.setBoolean(i+1, arg);
				}else if(o instanceof String){
					stmt.setString(i+1, (String) o);
				}else if(o instanceof NullValue){
					stmt.setNull(i+1, ((NullValue) o).type);
				}
			}
			
			ret = (stmt.executeUpdate() == 1)? true : false;
			pool.freeConnection(stmt.getConnection());
		}catch(Exception e){
			Log.log(e, Log.FATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}
}