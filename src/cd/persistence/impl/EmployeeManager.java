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
import cd.entity.Transaction;
import cd.entity.Employee;
import cd.object.ObjectLayer;

public class EmployeeManager {

    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public EmployeeManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Employee employee) 
            throws CDException
    {
        String               insertUserSql = "insert into User ( fname, lname, userName, password, email, phoneNumber ) values ( ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update User  set fname = ?, lname = ?, userName = ?, password = ?, email = ?, phoneNumber = ? where userId = ?";              
        
        String				 insertEmployeeSql = "insert into Employee (userId) values ( ? )";

        PreparedStatement    stmt;
        int                  inscnt;
        long                 userId;
        
        try {
            
            if( !employee.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            
            if( employee.getFirstName() != null )
                stmt.setString( 1, employee.getFirstName() );
            else 
                throw new CDException( "EmployeeManager.save: can't save a User: fname undefined" );

            if( employee.getLastName() != null )
                stmt.setString( 2, employee.getLastName() );
            else
                throw new CDException( "EmployeeManager.save: can't save a User: last name undefined" );

            if( employee.getUserName() != null )
                stmt.setString( 3, employee.getUserName() );
            else 
                throw new CDException( "EmployeeManager.save: can't save a User: username undefined" );
            
            if( employee.getPassword() != null )
                stmt.setString( 4, employee.getPassword() );
            else
                throw new CDException( "EmployeeManager.save: can't save a User: password undefined" );

            if( employee.getEmailAddress() != null )
                stmt.setString( 5,  employee.getEmailAddress() );
            else
                throw new CDException( "EmployeeManager.save: can't save a User: email undefined" );
            
            if( employee.getPhoneNumber() != null )
                stmt.setString( 6, employee.getPhoneNumber() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( employee.isPersistent() )
                stmt.setLong( 7, employee.getId() );

            inscnt = stmt.executeUpdate();

            if( !employee.isPersistent() ) {
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
                                employee.setId( userId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertEmployeeSql );
                                stmt.setLong(1,userId);
                                inscnt = stmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new CDException( "EmployeeManager.save: failed to save a employee" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new CDException( "EmployeeManager.save: failed to save an employee: " + e );
        }
    }

    public List<Employee> restore( Employee modelEmployee ) 
            throws CDException
    {
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address, age, Employee.employeeId from User, Employee where User.userId = Employee.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Employee> employees = new ArrayList<Employee>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelEmployee != null ) {
            if( modelEmployee.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and User.userId = '" + modelEmployee.getId() );
            if( modelEmployee.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelEmployee.getUserName() + "'" );
                
            if( modelEmployee.getFirstName() != null ) {
            	condition.append(" and ");
                condition.append( " fname = '" + modelEmployee.getFirstName() + "'" );
            }

                if( modelEmployee.getLastName() != null ) {
                    condition.append( " and " );
                    condition.append( " lname = '" + modelEmployee.getLastName() + "'" );
                }
            	
                if( modelEmployee.getPassword() != null ){
                    condition.append( " and " );
                    condition.append( " password = '" + modelEmployee.getPassword() + "'" );
                }

                if( modelEmployee.getEmailAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " email = '" + modelEmployee.getEmailAddress() + "'" );
                }

                if( modelEmployee.getPhoneNumber() != null ) {
                    condition.append( " and " );
                    condition.append( " address = '" + modelEmployee.getPhoneNumber() + "'" );
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

                    Employee employee = objectLayer.createEmployee( fname, lname, userName, password, email, phoneNumber );
                    employee.setId( userId );
                    employees.add( employee );

                }
                
                return employees;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new CDException( "EmployeeManager.restore: Could not restore persistent employee object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new CDException( "EmployeeManager.restore: Could not restore persistent employee objects" );
    }
    
    public void delete( Employee employee ) 
            throws CDException
    {
        String               deleteUserSql = "delete t1 from User as t1 "
        								   + "where t1.userId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !employee.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );        
            stmt.setLong( 1, employee.getId() );          
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new CDException( "EmployeeManager.delete: failed to delete this employee from User" );
            }
        }
        catch( SQLException e ) {
            throw new CDException( "EmployeeManager.delete: failed to delete this Employee: " + e.getMessage() );
        }
    }
    
    public void storeEmployeeEmployedByEmployer(Employee employee, Employer employer) throws CDException {
    	String storeSQL = "insert into EmployerEmployee (employerId, employeeId) values (?, ?)";
    	PreparedStatement statement = null;
    	int rowCount;
    	
    	if (!employee.isPersistent()) throw new CDException("EmployeeManager.save: Cannot insert into EmployerEmployee if employee is not persistent.");
    	else if (!employer.isPersistent()) throw new CDException("EmployeeManager.save: Cannot insert into EmployerEmployee if employer is not persistent.");
		
    	try {
    		statement = (PreparedStatement) conn.prepareStatement(storeSQL);
    		statement.setLong(1, employer.getId());
    		statement.setLong(2, employee.getId());
    		rowCount = statement.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw new CDException("EmployeeManager.save: Could not insert into EmployerEmployee: " + e);
    	} // try-catch
    	
    	if (rowCount == 0) throw new CDException("EmployeeManager.save: Could not insert into EmployerEmployee.");
    	
	} // storeEmployeeEmployedByEmployer
    
    public void deleteEmployeeEmployedByEmployer(Employee employee, Employer employer) throws CDException {
    	String deleteSQL = "delete from EmployerEmployee where employerId = ? and employeeId = ?";
    	PreparedStatement statement;
    	int rowCount;
    	
    	if (!employee.isPersistent()) return;
    	else if (!employer.isPersistent()) return;
    	
    	try {
    		statement = (PreparedStatement) conn.prepareStatement(deleteSQL);
    		statement.setLong(1, employer.getId());
    		statement.setLong(2, employee.getId());
    		rowCount = statement.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw new CDException("EmployeeManager.delete: Could not delete from EmployerEmployee: " + e);
    	} // try-catch
    	
    	if (rowCount == 0) throw new CDException("EmployeeManager.delete: Could not delte from EmployerEmployee: employer and employee not associated.");
		
	} // deleteEmployeeEmployedByEmployer
    
    public List<Employee> restoreEmployeeEmployedByEmployer(Employer employer) throws CDException {
    	List<Employee> employees = new ArrayList<Employee>();
    	String restoreSQL = "select from EmployerEmployee where employerId = ?";
		PreparedStatement statement;
		Employee employee = objectLayer.createEmployee();
		
		if (!employer.isPersistent()) throw new CDException("EmployeeManager.restore: Cannot restore employees from employer: employer not persistent.");
		
		try {
			statement = (PreparedStatement) conn.prepareStatement(restoreSQL);
			statement.setLong(1, employer.getId());
			statement.execute();
			ResultSet result = statement.getResultSet();
			while (result.next()) {
				employee.setId(result.getLong(2));
				employee = objectLayer.findEmployee(employee).get(0);
				employees.add(employee);
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CDException("EmployeeManager.restore: Cannot restore employees from EmployerEmployee: " + e);
		} // try-catch
    	
    	return employees;
	} // restoreEmployeeEmployedByEmployer
    
    public Employee restoreEmployeeFromTransaction(Transaction transaction) throws CDException {
    	String restoreSQL = "select from EmployeeTransaction where transactionId = ?";
    	PreparedStatement statement;
    	Employee employee = objectLayer.createEmployee();
    	
    	if (!transaction.isPersistent()) throw new CDException("EmployeeManager.restore: Cannot restore an employee from EmployeeTransaction: transaction is not persistent.");
    	
    	try {
    		statement = (PreparedStatement) conn.prepareStatement(restoreSQL);
    		statement.execute();
    		ResultSet result = statement.getResultSet();
    		while (result.next()) {
    			employee.setId(result.getLong(1));
    			employee = objectLayer.findEmployee(employee).get(0);
    			return employee;
    		} // while
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw new CDException("EmployeeManager.restore: Could not restore employee from EmployeeTransaction: " + e);
    	} // try-catch
	
    	return employee;
	} // restoreEmployeeFromTransaction
}
