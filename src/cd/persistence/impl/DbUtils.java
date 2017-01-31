package cd.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import cd.CDException;


/**
 * This class performs database operations.
 */
public class DbUtils {

    // private static Logger log = SessionManager.getLog();

    /**
     * Disables the auto commit for the current connection  
     * @param conn  the open connection
     */
    public static void disableAutoCommit( Connection conn ) 
            throws CDException 
    {
        try {
            conn.setAutoCommit(false);
        } 
        catch( SQLException ex ) {
            // log.error( "DbUtils.disableAutoCommit: SQLException on setting auto-commit false ", ex );
            throw new CDException( "DbUtils.disableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    /**
     * Enable the auto commit for the current connection  
     * @param conn  the open connection
     */
    public static void enableAutoCommit( Connection conn ) 
            throws CDException 
    {
        try {
            conn.setAutoCommit(true);
        } 
        catch( SQLException ex ) {
            // log.error( "DbUtils.enableAutoCommit: SQLException on setting auto-commit true ", ex );
            throw new CDException( "DbUtils.enableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    /**
     * Commit the transactions for the current connection  
     * @param conn  the open connection
     */
    public static void commit(Connection conn) 
            throws CDException 
    {
        try {
            conn.commit();
        } catch (SQLException ex) {
            // log.error("DbUtils.commit: SQLException on commit ", ex);
            throw new CDException( "DbUtils.commit: SQLException on commit " + ex.getMessage() );
        }
    }

    /**
     * rollback the transactions for the current connection  
     * @param conn  the open connection
     */
    public static void rollback(Connection conn) 
            throws CDException 
    {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            // log.error( "DbUtils.rollback: SQLException on rollback ", ex );
            throw new CDException( "DbUtils.rollback: Unable to rollback transaction. " + ex.getMessage() );
        }
    }

    /**
     * Establish a JDBC connection to the database.
     * @return  a database connection object
     * @throws  GVException
     */
    public static Connection connect() 
            throws CDException 
    {
    	/*
    	try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
        try {
            Class.forName(DbAccessConfig.DB_DRIVE_NAME);
        } 
        catch (ClassNotFoundException ex) {
            // log.error( "DbUtils.connect:  unable to find JDBC Driver", ex );
            throw new CDException( "DbUtils.connect: Unable to find Driver " + ex.getMessage() );
        }
        
        try {
    		Properties prop = new Properties();
    		prop.setProperty("user", DbAccessConfig.DB_CONNECTION_USERNAME);
    		prop.setProperty("password", DbAccessConfig.DB_CONNECTION_PWD);
    		prop.setProperty("useSSL", "false");
    		prop.setProperty("autoReconnect", "true");
            return DriverManager.getConnection( DbAccessConfig.DB_CONNECTION_URL,
                                                prop );
        } 
        catch (SQLException ex) {
            // log.error( "DbUtils.connect: Unable to connect to database", ex );
            throw new CDException( "DbUtils.connect: Unable to connect to database " + ex.getMessage() );
        }
    }
}