package cd.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cd.CDException;
import cd.entity.Customer;
import cd.object.ObjectLayer;

public class CustomerManager {
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public CustomerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Customer customer ) 
            throws CDException
    {
        String               insertUserSql = "insert into User ( fname, lname, userName, password, email, phoneNumber ) values ( ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update User  set fname = ?, lname = ?, userName = ?, password = ?, email = ?, phoneNumber = ? where userId = ?";              
        
        String				 insertCustomerSql = "insert into Customer (userId, description) values ( ?, ? )";
        String				 updateCustomerSql = "update Customer set description = ? where userId = ?";
        
        PreparedStatement    stmt, stmt2;
        int                  inscnt;
        long                 userId;
        
        try {
            
            if( !customer.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            
            if( customer.getFirstName() != null )
                stmt.setString( 1, customer.getFirstName() );
            else 
                throw new CDException( "CustomerManager.save: can't save a User: fname undefined" );

            if( customer.getLastName() != null )
                stmt.setString( 2, customer.getLastName() );
            else
                throw new CDException( "CustomerManager.save: can't save a User: last name undefined" );

            if( customer.getUserName() != null )
                stmt.setString( 3, customer.getUserName() );
            else 
                throw new CDException( "CustomerManager.save: can't save a User: username undefined" );
            
            if( customer.getPassword() != null )
                stmt.setString( 4, customer.getPassword() );
            else
                throw new CDException( "CustomerManager.save: can't save a User: password undefined" );

            if( customer.getEmailAddress() != null )
                stmt.setString( 5,  customer.getEmailAddress() );
            else
                throw new CDException( "CustomerManager.save: can't save a User: email undefined" );
            
            if( customer.getPhoneNumber() != null )
                stmt.setString( 6, customer.getPhoneNumber() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( customer.isPersistent() ){
                stmt.setLong( 7, customer.getId() );
                stmt2 = (PreparedStatement) conn.prepareStatement( updateCustomerSql );
                stmt2.setLong(1, customer.getId());
                if(customer.getDescription() != null)
                	stmt2.setString(2, customer.getDescription());
                else
                    stmt.setNull(6, java.sql.Types.VARCHAR);
                
                stmt2.executeUpdate();
            }
            inscnt = stmt.executeUpdate();

            if( !customer.isPersistent() ) {
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
                                customer.setId( userId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertCustomerSql );
                                stmt.setLong(1,userId);
                                stmt.setString(2, customer.getDescription());
                                inscnt = stmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new CDException( "CustomerManager.save: failed to save a customer" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new CDException( "CustomerManager.save: failed to save an customer: " + e );
        }
    }

    public List<Customer> restore( Customer modelCustomer ) 
            throws CDException
    {
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address, age, Customer.customerId from User, Customer where User.userId = Customer.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Customer> customers = new ArrayList<Customer>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelCustomer != null ) {
            if( modelCustomer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and User.userId = '" + modelCustomer.getId() );
            if( modelCustomer.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelCustomer.getUserName() + "'" );
                
            if( modelCustomer.getFirstName() != null ) {
            	condition.append(" and ");
                condition.append( " fname = '" + modelCustomer.getFirstName() + "'" );
            }

                if( modelCustomer.getLastName() != null ) {
                    condition.append( " and " );
                    condition.append( " lname = '" + modelCustomer.getLastName() + "'" );
                }
            	
                if( modelCustomer.getPassword() != null ){
                    condition.append( " and " );
                    condition.append( " password = '" + modelCustomer.getPassword() + "'" );
                }

                if( modelCustomer.getEmailAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " email = '" + modelCustomer.getEmailAddress() + "'" );
                }

                if( modelCustomer.getPhoneNumber() != null ) {
                    condition.append( " and " );
                    condition.append( " address = '" + modelCustomer.getPhoneNumber() + "'" );
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

                    Customer customer = objectLayer.createCustomer( fname, lname, userName, password, email, phoneNumber );
                    customer.setId( userId );
                    customers.add( customer );

                }
                
                return customers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new CDException( "CustomerManager.restore: Could not restore persistent customer object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new CDException( "CustomerManager.restore: Could not restore persistent customer objects" );
    }
    
    public void delete( Customer customer ) 
            throws CDException
    {
        String               deleteUserSql = "delete t1 from User as t1 "
        								   + "where t1.userId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !customer.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );        
            stmt.setLong( 1, customer.getId() );          
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new CDException( "CustomerManager.delete: failed to delete this customer from User" );
            }
        }
        catch( SQLException e ) {
            throw new CDException( "CustomerManager.delete: failed to delete this Customer: " + e.getMessage() );
        }
    }
}
