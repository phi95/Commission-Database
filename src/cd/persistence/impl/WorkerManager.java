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
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.object.ObjectLayer;

public class WorkerManager {

    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public WorkerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Worker worker ) 
            throws CDException
    {
        String               insertUserSql = "insert into User ( fname, lname, userName, password, email, phoneNumber ) values ( ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update User  set fname = ?, lname = ?, userName = ?, password = ?, email = ?, phoneNumber = ? where userId = ?";              
        
        String				 insertWorkerSql = "insert into Worker (userId) values ( ? )";

        PreparedStatement    stmt;
        int                  inscnt;
        long                 userId;
        
        try {
            
            if( !worker.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            
            if( worker.getFirstName() != null )
                stmt.setString( 1, worker.getFirstName() );
            else 
                throw new CDException( "WorkerManager.save: can't save a User: fname undefined" );

            if( worker.getLastName() != null )
                stmt.setString( 2, worker.getLastName() );
            else
                throw new CDException( "WorkerManager.save: can't save a User: last name undefined" );

            if( worker.getUserName() != null )
                stmt.setString( 3, worker.getUserName() );
            else 
                throw new CDException( "WorkerManager.save: can't save a User: username undefined" );
            
            if( worker.getPassword() != null )
                stmt.setString( 4, worker.getPassword() );
            else
                throw new CDException( "WorkerManager.save: can't save a User: password undefined" );

            if( worker.getEmailAddress() != null )
                stmt.setString( 5,  worker.getEmailAddress() );
            else
                throw new CDException( "WorkerManager.save: can't save a User: email undefined" );
            
            if( worker.getPhoneNumber() != null )
                stmt.setString( 6, worker.getPhoneNumber() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( worker.isPersistent() )
                stmt.setLong( 7, worker.getId() );

            inscnt = stmt.executeUpdate();

            if( !worker.isPersistent() ) {
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
                                worker.setId( userId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertWorkerSql );
                                stmt.setLong(1,userId);
                                inscnt = stmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new CDException( "WorkerManager.save: failed to save a worker" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new CDException( "WorkerManager.save: failed to save an worker: " + e );
        }
    }

    public List<Worker> restore( Worker modelWorker ) 
            throws CDException
    {
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address, age, Worker.workerId from User, Worker where User.userId = Worker.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Worker> workers = new ArrayList<Worker>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelWorker != null ) {
            if( modelWorker.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and User.userId = '" + modelWorker.getId() );
            if( modelWorker.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelWorker.getUserName() + "'" );
                
            if( modelWorker.getFirstName() != null ) {
            	condition.append(" and ");
                condition.append( " fname = '" + modelWorker.getFirstName() + "'" );
            }

                if( modelWorker.getLastName() != null ) {
                    condition.append( " and " );
                    condition.append( " lname = '" + modelWorker.getLastName() + "'" );
                }
            	
                if( modelWorker.getPassword() != null ){
                    condition.append( " and " );
                    condition.append( " password = '" + modelWorker.getPassword() + "'" );
                }

                if( modelWorker.getEmailAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " email = '" + modelWorker.getEmailAddress() + "'" );
                }

                if( modelWorker.getPhoneNumber() != null ) {
                    condition.append( " and " );
                    condition.append( " address = '" + modelWorker.getPhoneNumber() + "'" );
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

                    Worker worker = objectLayer.createWorker( fname, lname, userName, password, email, phoneNumber );
                    worker.setId( userId );
                    workers.add( worker );

                }
                
                return workers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new CDException( "WorkerManager.restore: Could not restore persistent worker object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new CDException( "WorkerManager.restore: Could not restore persistent worker objects" );
    }
    
    public void delete( Worker worker ) 
            throws CDException
    {
        String               deleteUserSql = "delete t1 from User as t1 "
        								   + "where t1.userId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !worker.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );        
            stmt.setLong( 1, worker.getId() );          
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new CDException( "WorkerManager.delete: failed to delete this worker from User" );
            }
        }
        catch( SQLException e ) {
            throw new CDException( "WorkerManager.delete: failed to delete this Worker: " + e.getMessage() );
        }
    }
    
    public void storeWorkerEmployedByManager(Worker worker, Manager manager) throws CDException {
    	String insertSQL = "insert into ManagerWorker (managerId, workerId) values (?, ?)";
    	PreparedStatement statement = null;
    	int rowCount;
    	
    	if (!worker.isPersistent()) throw new CDException("WorkerManager.save: worker is not persistent.");
    	else if (!manager.isPersistent()) throw new CDException("WorkerManager.save: manager is not persistent.");
    	
    	try {
    		statement = (PreparedStatement) conn.prepareStatement(insertSQL);
    		statement.setLong(1, manager.getId());
    		statement.setLong(2, worker.getId());
    		rowCount = statement.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw new CDException("WorkerManager.save: could not insert into WorkerManager. Reason: " + e);
    	} // try-catch
    	
    	if (rowCount < 0) throw new CDException("WorkerManager.save: could not insert into WorkerManager");
	} // storeWorkerEmployedByManager
    
    public void deleteWorkerEmployedByManager(Worker worker, Manager manager) throws CDException {
		String deleteSQL = "delete from ManagerWorker where managerId = ? and workerId = ?";
		PreparedStatement statement = null;
		int rowCount;
		
		/* No need to delete if this element isn't persistent. */
		if (!manager.isPersistent()) return;
		else if (!worker.isPersistent()) return;
		
		try {
			statement = (PreparedStatement) conn.prepareStatement(deleteSQL);
			statement.setLong(1, manager.getId());
			statement.setLong(2, worker.getId());
			rowCount = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CDException("WorkerManager.delete: failed to delete from ManagerWorker table: " + e);
		} // try-catch
		
		// If no rows were affected by this query, we know the deletion failed.
		if (rowCount == 0) throw new CDException("WorkerManager.delete: failed to delete from ManagerWorker table.");		
	} // deleteWorkerEmployedByManager
    
    public List<Worker> restoreWorkerEmployedByManager(Manager manager) throws CDException {
		String selectSQL = "select from ManagerWorker where managerId = ?";
		PreparedStatement statement = null;
		List<Worker> workerList = new ArrayList<Worker>();
		long workerId;
		
		if (!manager.isPersistent()) return workerList; // Return an empty list
		
		try {
			statement = (PreparedStatement) conn.prepareStatement(selectSQL);
			statement.setLong(1, manager.getId());
			statement.execute();
			ResultSet result = statement.getResultSet();
			while (result.next()) {
				workerId = result.getLong(2);
				Worker modelWorker = objectLayer.createWorker();
				modelWorker.setId(workerId);
				List<Worker> workerEmployedByManager = restore(modelWorker);
				Worker worker = workerEmployedByManager.get(0); // there can only be one worker that matches the model.
				workerList.add(worker);
			} // while
		} catch (SQLException e) {
			throw new CDException("WorkerManager.restore: Could not restore a list of workers. Reason: " + e);
		} // try-catch
		
		return workerList;
	} // restoreWorkerEmployedByManager
    
    public Worker restoreWorkerFromTransaction(Transaction transaction) throws CDException {
		PreparedStatement statement = null;
		Worker resultWorker = null;
		String selectSQL = "select from WorkerTransaction where transactionId = ?";
		
		if (!transaction.isPersistent()) throw new CDException ("WorkerManager.restore: cannot restore a worker from a transaction that is not persistent.");
		
		try {
			statement = (PreparedStatement) conn.prepareStatement(selectSQL);
			statement.setLong(2, transaction.getId());
			statement.execute();
			ResultSet result = statement.getResultSet();
			while (result.next()) {
				long workerId = result.getLong(1);
				Worker modelWorker = objectLayer.createWorker();
				modelWorker.setId(workerId);
				List<Worker> workerProcessedTransaction = objectLayer.getPersistence().restoreWorker(modelWorker);
				resultWorker = workerProcessedTransaction.get(0);
				return resultWorker;
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CDException("WorkerManager.restore: Could not restore a worker. Reason: " + e);
		} // try-catch
	
		return resultWorker;
	} // restoreWorkerFromTransaction
}
