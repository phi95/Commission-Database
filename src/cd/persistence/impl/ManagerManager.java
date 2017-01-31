package cd.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cd.CDException;
import cd.entity.Manager;
import cd.object.ObjectLayer;

public class ManagerManager {
	
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ManagerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Manager manager ) 
            throws CDException
    {
        String               insertUserSql = "insert into User ( fname, lname, userName, password, email, phoneNumber ) values ( ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update User  set fname = ?, lname = ?, userName = ?, password = ?, email = ?, phoneNumber = ? where userId = ?";              
        
        String				 insertManagerSql = "insert into Manager (userId) values ( ? )";

        PreparedStatement    stmt;
        int                  inscnt;
        long                 userId;
        
        try {
            
            if( !manager.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            
            if( manager.getFirstName() != null )
                stmt.setString( 1, manager.getFirstName() );
            else 
                throw new CDException( "ManagerManager.save: can't save a User: fname undefined" );

            if( manager.getLastName() != null )
                stmt.setString( 2, manager.getLastName() );
            else
                throw new CDException( "ManagerManager.save: can't save a User: last name undefined" );

            if( manager.getUserName() != null )
                stmt.setString( 3, manager.getUserName() );
            else 
                throw new CDException( "ManagerManager.save: can't save a User: username undefined" );
            
            if( manager.getPassword() != null )
                stmt.setString( 4, manager.getPassword() );
            else
                throw new CDException( "ManagerManager.save: can't save a User: password undefined" );

            if( manager.getEmailAddress() != null )
                stmt.setString( 5,  manager.getEmailAddress() );
            else
                throw new CDException( "ManagerManager.save: can't save a User: email undefined" );
            
            if( manager.getPhoneNumber() != null )
                stmt.setString( 6, manager.getPhoneNumber() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( manager.isPersistent() )
                stmt.setLong( 7, manager.getId() );

            inscnt = stmt.executeUpdate();

            if( !manager.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            userId = r.getLong( 1 );
                            if( userId > 0 ){
                                manager.setId( userId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertManagerSql );
                                stmt.setLong(1,userId);
                                inscnt = stmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new CDException( "ManagerManager.save: failed to save a manager" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new CDException( "ManagerManager.save: failed to save an manager: " + e );
        }
    }

    public List<Manager> restore( Manager modelManager ) 
            throws CDException
    {
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address, age, Manager.managerId from User, Manager where User.userId = Manager.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Manager> managers = new ArrayList<Manager>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelManager != null ) {
            if( modelManager.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and User.userId = '" + modelManager.getId() );
            if( modelManager.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelManager.getUserName() + "'" );
                
            if( modelManager.getFirstName() != null ) {
            	condition.append(" and ");
                condition.append( " fname = '" + modelManager.getFirstName() + "'" );
            }

                if( modelManager.getLastName() != null ) {
                    condition.append( " and " );
                    condition.append( " lname = '" + modelManager.getLastName() + "'" );
                }
            	
                if( modelManager.getPassword() != null ){
                    condition.append( " and " );
                    condition.append( " password = '" + modelManager.getPassword() + "'" );
                }

                if( modelManager.getEmailAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " email = '" + modelManager.getEmailAddress() + "'" );
                }

                if( modelManager.getPhoneNumber() != null ) {
                    condition.append( " and " );
                    condition.append( " address = '" + modelManager.getPhoneNumber() + "'" );
                }

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   userId;
                String fname;
                String lname;
                String userName;
                String password;
                String email;
                String phoneNumber;
                
                while( rs.next() ) {

                    userId = rs.getLong( 1 );
                    fname = rs.getString( 2 );
                    lname = rs.getString( 3 );
                    userName = rs.getString( 4 );
                    password = rs.getString( 5 );
                    email = rs.getString( 6 );
                    phoneNumber = rs.getString( 7 );

                    Manager manager = objectLayer.createManager( fname, lname, userName, password, email, phoneNumber );
                    manager.setId( userId );
                    managers.add( manager );

                }
                
                return managers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new CDException( "ManagerManager.restore: Could not restore persistent manager object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new CDException( "ManagerManager.restore: Could not restore persistent manager objects" );
    }
    
    public void delete( Manager manager ) 
            throws CDException
    {
        String               deleteUserSql = "delete t1 from User as t1 "
        								   + "where t1.userId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !manager.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );        
            stmt.setLong( 1, manager.getId() );          
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new CDException( "ManagerManager.delete: failed to delete this manager from User" );
            }
        }
        catch( SQLException e ) {
            throw new CDException( "ManagerManager.delete: failed to delete this Manager: " + e.getMessage() );
        }
    }
}
