package db_handler;

import java.sql.*;
import org.spongepowered.api.command.CommandSource;

/*
 * Handler:
 * The Handler class takes care of all SQLite functionality
 */

public class DBHandler {
	
	   private static final String DB_URL = "jdbc:sqlite:database/gym_plugin.db";

	   /**************************** Initiating Functions ********************************/	

	   // Connect to the Database
	   public static boolean connect() {
		   Connection conn = null;
		   try {
			   System.out.println("GymPlugin: Connecting to a selected database...");
			   conn = DriverManager.getConnection(DB_URL);
			   System.out.println("GymPlugin: Connected database successfully...");
			   return true;
		   } catch(SQLException se) {
			   se.printStackTrace();
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   } finally {
			    try{
			    	if(conn!=null)
			    	conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
		   }
		   
		   // Failed to connect
		   return false;
	   } // End of connect()
	   
	   public static void createNewDatabase() {
		   try (Connection conn = DriverManager.getConnection(DB_URL)) {
	    	   if (conn != null) {
	    		   DatabaseMetaData meta = conn.getMetaData();
	    		   System.out.println("GymPlugin: The driver name is " + meta.getDriverName());
	    		   System.out.println("GymPlugin: A new database has been created.");
	    	   }
		   } catch (SQLException e) {
			   System.out.println(e.getMessage());
		   }
	   } // End of createNewDatabase()
	   
	   // Create all the tables through the amazing process of hard coding everything
	   public static void createTables()
	   {
		   // Regions Table
		   String sql = "CREATE TABLE IF NOT EXISTS regions (\n"
				   + " id integer PRIMARY KEY,\n"
				   + " region text NOT NULL, \n"
				   + ");";	
		   
		   executeSQLStatement(sql);
		   
		   // Gyms Table
		   sql = "CREATE TABLE IF NOT EXISTS gyms (\n"
				   + " id integer PRIMARY KEY,\n"
				   + " gym text NOT NULL, \n"
				   + " color text DEFAULT 'white', \n"
				   + " regionid integer NOT NULL, \n"
				   + " FOREIGN KEY (regionid) REFERENCES regions(id), \n"
				   + ");";	
		   
		   executeSQLStatement(sql);
		   
		   // Players Table
		   sql = "CREATE TABLE IF NOT EXISTS players (\n"
				   + " id integer PRIMARY KEY,\n"
				   + " wins integer DEFAULT 0,\n"
				   + " losses integer DEFAULT 0,\n"
				   + " name text NOT NULL, \n"
				   + ");";	
		   
		   executeSQLStatement(sql);
		   
		   // GymLeader Table
		   sql = "CREATE TABLE IF NOT EXISTS gymleader (\n"
				   + " id integer PRIMARY KEY,\n"
				   + " playerid text NOT NULL, \n"
				   + " gymid integer NOT NULL, \n"
				   + " FOREIGN KEY (playerid) REFERENCES players(id), \n"
				   + " FOREIGN KEY (gymid) REFERENCES gyms(id), \n"
				   + ");";	
		   
		   executeSQLStatement(sql);
	   } // End of createTables()
	   
	   /***************************** End of Initiating **********************************/	
	   
	   /***************************** Database Functions *********************************/	
	   
	   public void insertPlayer(String name, CommandSource src)
	   {
		   String sql = "INSERT INTO players(name) VALUES(?)";
		   
		   try (Connection conn = DriverManager.getConnection(DB_URL);
	        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        	// set parameter
	            pstmt.setString(1, name);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	   }
	   
	   public void assignGymLeader(String name, String gym, CommandSource src)
	   {
		   
	   }
	   
	   public void removeGymLeader(String name, String gym, CommandSource src)
	   {
		   //String id = getId(name);
		   
//		   String sql = "DELETE FROM gymleader WHERE id = ?";
//		   
//	        try (Connection conn = DriverManager.getConnection(DB_URL);
//	        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
//	        	// set parameter
//	            pstmt.setInt(1, id);
//	            pstmt.executeUpdate();
//	        } catch (SQLException e) {
//	            System.out.println(e.getMessage());
//	        }
	   }
	   
	   public void delete(String table, int id, CommandSource src)
	   {
		   String sql = "DELETE FROM " + table + " WHERE id = ?";
		   
	        try (Connection conn = DriverManager.getConnection(DB_URL);
	        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        	// set parameter
	            pstmt.setInt(1, id);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	   }
	   
	   /***************************** End of Database ************************************/	 

	   
	   private static void executeSQLStatement(String sql)
	   {
		   try (Connection conn = DriverManager.getConnection(DB_URL); 
				   Statement stmt = conn.createStatement()) {
			   stmt.execute(sql);
		   } catch (SQLException e) {
			   System.out.println(e.getMessage());
		   }
	   }
}
