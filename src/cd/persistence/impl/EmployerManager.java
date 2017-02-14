package cd.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cd.CDException;
import cd.entity.Employer;
import cd.entity.Employee;
import cd.object.ObjectLayer;

public class EmployerManager {
	
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public EmployerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Employer employer ) 
            throws CDException
    {
        String insertUserSql = "insert into User ( fname, lname, userName, password, email, phoneNumber ) values ( ?, ?, ?, ?, ?, ? )";              
        String updateUserSql = "update User  set fname = ?, lname = ?, userName = ?, password = ?, email = ?, phoneNumber = ? where userId = ?";              
        String insertEmployerSql = "insert into Employer (userId) values ( ? )";

        PreparedStatement stmt;
        int inscnt;
        long userId;
        
        try {
            
            if (!employer.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            
            if( employer.getFirstName() != null )
                stmt.setString( 1, employer.getFirstName() );
            else 
                throw new CDException( "EmployerManager.save: can't save a User: fname undefined" );

            if( employer.getLastName() != null )
                stmt.setString( 2, employer.getLastName() );
            else
                throw new CDException( "EmployerManager.save: can't save a User: last name undefined" );

            if( employer.getUserName() != null )
                stmt.setString( 3, employer.getUserName() );
            else 
                throw new CDException( "EmployerManager.save: can't save a User: username undefined" );
            
            if( employer.getPassword() != null )
                stmt.setString( 4, employer.getPassword() );
            else
                throw new CDException( "EmployerManager.save: can't save a User: password undefined" );

            if( employer.getEmailAddress() != null )
                stmt.setString( 5,  employer.getEmailAddress() );
            else
                throw new CDException( "EmployerManager.save: can't save a User: email undefined" );
            
            if( employer.getPhoneNumber() != null )
                stmt.setString( 6, employer.getPhoneNumber() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( employer.isPersistent() )
                stmt.setLong( 7, employer.getId() );

            inscnt = stmt.executeUpdate();

            if( !employer.isPersistent() ) {
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
                                employer.setId( userId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertEmployerSql );
                                stmt.setLong(1,userId);
                                inscnt = stmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new CDException( "EmployerManager.save: failed to save a employer" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new CDException( "EmployerManager.save: failed to save an employer: " + e );
        }
    }

    public List<Employer> restore( Employer modelEmployer ) 
            throws CDException
    {
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address, age, Employer.employerId from User, Employer where User.userId = Employer.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Employer> employers = new ArrayList<Employer>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelEmployer != null ) {
            if( modelEmployer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and User.userId = '" + modelEmployer.getId() );
            if( modelEmployer.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelEmployer.getUserName() + "'" );
                
            if( modelEmployer.getFirstName() != null ) {
            	condition.append(" and ");
                condition.append( " fname = '" + modelEmployer.getFirstName() + "'" );
            }

                if( modelEmployer.getLastName() != null ) {
                    condition.append( " and " );
                    condition.append( " lname = '" + modelEmployer.getLastName() + "'" );
                }
            	
                if( modelEmployer.getPassword() != null ){
                    condition.append( " and " );
                    condition.append( " password = '" + modelEmployer.getPassword() + "'" );
                }

                if( modelEmployer.getEmailAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " email = '" + modelEmployer.getEmailAddress() + "'" );
                }

                if( modelEmployer.getPhoneNumber() != null ) {
                    condition.append( " and " );
                    condition.append( " address = '" + modelEmployer.getPhoneNumber() + "'" );
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

                    Employer employer = objectLayer.createEmployer( fname, lname, userName, password, email, phoneNumber );
                    employer.setId( userId );
                    employers.add( employer );

                }
                
                return employers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new CDException( "EmployerManager.restore: Could not restore persistent employer object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new CDException( "EmployerManager.restore: Could not restore persistent employer objects" );
    }
    
    public void delete( Employer employer ) 
            throws CDException
    {
        String               deleteUserSql = "delete t1 from User as t1 "
        								   + "where t1.userId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !employer.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );        
            stmt.setLong( 1, employer.getId() );          
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new CDException( "EmployerManager.delete: failed to delete this employer from User" );
            }
        }
        catch( SQLException e ) {
            throw new CDException( "EmployerManager.delete: failed to delete this Employer: " + e.getMessage() );
        }
    }
    
    public Employer restoreEmployerFromEmployee(Employee employee) throws CDException {
    	String restoreSQL = "select from EmployerEmployee where employeeId = ?";
    	PreparedStatement statement;
    	Employer employer = objectLayer.createEmployer();
    	
    	if (!employee.isPersistent()) throw new CDException("EmployerManager.restore: Cannot restore employer from non-persistent employee.");
    	
    	try {
    		statement = (PreparedStatement) conn.prepareStatement(restoreSQL);
    		statement.setLong(1, employee.getId());
    		statement.execute();
    		ResultSet result = statement.getResultSet();
    		while (result.next()) {
    			employer.setId(result.getLong(1));
    			employer = objectLayer.findEmployer(employer).get(0);
    			return employer;
    		} // while
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw new CDException("EmployerManager.restore: Cannot restore employer from EmployerEmployee: " + e);
    	} // try-catch
    	
    	return employer;
	} // restoreEmployerFromEmployee
}
