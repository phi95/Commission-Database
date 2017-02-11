package cd.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cd.CDException;
import cd.entity.Customer;
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.entity.impl.CustomerImpl;
import cd.entity.impl.TransactionImpl;
import cd.entity.impl.WorkerImpl;
import cd.object.ObjectLayer;

public class TransactionManager {

	private ObjectLayer objectLayer = null;
	private Connection con = null;
	
	public TransactionManager(Connection con, ObjectLayer objectLayer) {
		this.con = con;
		this.objectLayer = objectLayer;
	} // TransactionManager
	
	public void store(Transaction transaction) throws CDException {
		String insertTransaction = "insert into Transaction (date, description, transactionAmount) values (?, ?, ?, ?)";
		String updateTransaction = "update Transaction set date = ?, description = ?, transactionAmount = ? where transactionId = ?";
		
		PreparedStatement statement;
		int inscnt;
		long transactionId;
		
		try {
			// Check if updating or inserting
			if (!transaction.isPersistent()) statement = (PreparedStatement) con.prepareStatement(insertTransaction);
			else statement = (PreparedStatement) con.prepareStatement(updateTransaction);
			
			// Set information from the given transaction object.
			if(transaction.getDate() != null) {
				java.util.Date date = transaction.getDate();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				statement.setDate(1, sqlDate);
			} else throw new CDException("TransactionManager.save: cannot save a transaction: date undefined");
			
			if (transaction.getDescription() != null) statement.setString(2, transaction.getDescription());
			else throw new CDException("TransactionManager.save: cannot save a transaction: description undefined");
			
			if (transaction.getTransactionAmount() != -1) statement.setDouble(3, transaction.getTransactionAmount());
			else throw new CDException("TransactionManager.save: cannot save a transaction: transactionAmount undefined");

			if (transaction.isPersistent()) statement.setLong(4, transaction.getId());
			
			inscnt = statement.executeUpdate();
			
			// Establish persistent identifier if this is the first time storing the transaction.
			if (!transaction.isPersistent()) {
				if (inscnt == 1) {
					String sql = "select last_insert_index()";
					if(statement.execute(sql)) {
						ResultSet result = statement.getResultSet();
						while (result.next()) { // Using only first row
							transactionId = result.getLong(1);
							if (transactionId > 0) transaction.setId(transactionId);
						} // while	
					} // if	
				} // if
			} // if
			
			else if (inscnt < 1) throw new CDException("TransactionManager.save: failed to save a transaction");
		} // try
		
		catch (SQLException e) {
			e.printStackTrace();
			throw new CDException("TransactionManager.save: failed to save a transaction!");
		} // catch		
	} // store
	
	public List<Transaction> restore (Transaction modelTransaction) throws CDException {
		String selectTransaction = "select date, description, transactionAmount from Transaction";
		Statement statement = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		List <Transaction> transactionList = new ArrayList<Transaction>();
		
		condition.setLength(0);
		query.append(selectTransaction);
		
		// If a modelTransaction is persistent, its id is unique. Therefore, we only have to restore the modelTransaction.
		if (modelTransaction != null && modelTransaction.isPersistent()) {
			query.append(" where transactionId = " + modelTransaction.getId());
		} // if
		
		else if (modelTransaction != null) {
			if (modelTransaction.getDate() != null) {
				if (condition.length() > 0) condition.append(" and ");
				condition.append(" date = '" + modelTransaction.getDate() + "'");
			} // if 
			
			if (modelTransaction.getDescription() != null) {
				if (condition.length() > 0) condition.append(" and ");
				condition.append(" description = '" + modelTransaction.getDescription() + "'");	
			} // if
			
			if (modelTransaction.getTransactionAmount() != -1) {
				if (condition.length() > 0) condition.append(" and ");
				condition.append(" transactionAmount = " + modelTransaction.getTransactionAmount());
			} // if
			
			if (condition.length() > 0)  {
				query.append(" where ");
				query.append(condition);
			} // if
		} // else if
		
		try {
			statement = con.createStatement();
			if (statement.execute(query.toString())) {
				long transactionId;
				Worker worker = modelTransaction.getWorker();
				Customer customer = modelTransaction.getCustomer();
				Date date;
				String description;
				double transactionAmount;
				
				ResultSet result = statement.getResultSet();
				while (result.next()) {
					transactionId = result.getLong(1);
					date = result.getDate(2);
					description = result.getString(3);
					transactionAmount = result.getDouble(4);
					
					Transaction transaction = objectLayer.createTransaction();
					transaction.setId(transactionId);
					transaction.setDate(date);
					transaction.setDescription(description);
					transaction.setTransactionAmount(transactionAmount);
					
					if (customer == null) customer = objectLayer.getPersistence().restoreCustomerFromTransaction(transaction);
					if (worker == null) worker = objectLayer.getPersistence().restoreWorkerFromTransaction(transaction);
					
					transaction.setCustomer(customer);
					transaction.setWorker(worker);
					transactionList.add(transaction);
				} // while
				return transactionList;
			} // if
		} catch (Exception e) {
			throw new CDException("TransactionManager.restore: Could not restore persistent Transaction objects. Cause: " + e);
		} // try-catch
		
		return transactionList;
		
	} // restore
	
	public void delete (Transaction transaction) throws CDException {
		String deleteTransaction = "delete from Transaction where transactionId = ?";
		PreparedStatement statement = null;
		int inscnt;
		
		if (!transaction.isPersistent()) return;
		
		try {
			statement = (PreparedStatement) con.prepareStatement(deleteTransaction);
			statement.setLong(1, transaction.getId());
			inscnt = statement.executeUpdate();
			if (inscnt == 0) throw new CDException("TransactionManager.delete: failed to delete this Transaction");
		} // try
	
		catch (SQLException e) {
			throw new CDException("TransactionManager.delete: failed to delete this Transaction: " + e.getMessage());
		} // catch
	} // delete
	
	public void storeTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException {
		String storeSQL = "insert into WorkerTransaction (workerId, transactionId) values (?, ?)";
		PreparedStatement statement = null;
		int rowCount;
		
		if (!transaction.isPersistent()) throw new CDException("TransactionManager.save: transaction is not persistent.");
		else if (!worker.isPersistent()) throw new CDException("TransactionManager,save: worker is not persistent.");
		
		try {
			statement = (PreparedStatement) con.prepareStatement(storeSQL);
			statement.setLong(1, worker.getId());
			statement.setLong(2, transaction.getId());
			rowCount = statement.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			throw new CDException("TransactionManager.save: failed to insert into WorkerTransaction: " + e);
		} // try-catch;
		
		if (rowCount < 1) throw new CDException("TransactionManager.save: failed to insert into WorkerTransaction");
	} // storeTransactionCompletedByWorker
	
	public void storeTransactionOrderedByCustomer(Transaction transaction, Customer customer) throws CDException {
		String storeSQL = "insert into CustomerTransaction (customerId, transactionId) values (?, ?)";
		PreparedStatement statement = null;
		int rowCount;
		
		if (!transaction.isPersistent()) throw new CDException("TransactionManager.save: transaction is not persistent.");
		else if (!customer.isPersistent()) throw new CDException("TransactionManager,save: worker is not persistent.");
		
		try {
			statement = (PreparedStatement) con.prepareStatement(storeSQL);
			statement.setLong(1, customer.getId());
			statement.setLong(2, transaction.getId());
			rowCount = statement.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			throw new CDException("TransactionManager.save: failed to insert into CustomerTransaction: " + e);
		} // try-catch;
		
		if (rowCount < 1) throw new CDException("TransactionManager.save: failed to insert into CustomerTransaction");
	} // storeTransactionOrderedByCustomer
	
	public void deleteTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException {
		String deleteSQL = "delete from WorkerTransaction where workerId = ? and transactionId = ?";
		PreparedStatement statement = null;
		int rowCount;
		
		/* No need to delete if this element isn't persistent. */
		if (!transaction.isPersistent()) return;
		else if (!worker.isPersistent()) return;
		
		try {
			statement = (PreparedStatement) con.prepareStatement(deleteSQL);
			statement.setLong(1, worker.getId());
			statement.setLong(2, transaction.getId());
			rowCount = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CDException("TransactionManager.delete: failed to delete from WorkerTransaction: " + e);
		} // try-catch
		
		// If no rows were affected by this query, we know the deletion failed.
		if (rowCount == 0) throw new CDException("TransactionManager.delete: failed to delete from WorkerTransaction.");
	} // deleteTransactionCompletedByWorker
	
	public void deleteTransactionOrderedByCustomer(Transaction transaction, Customer customer) throws CDException {
		String deleteSQL = "delete from WorkerTransaction where customerId = ? and transactionId = ?";
		PreparedStatement statement = null;
		int rowCount;
		
		/* No need to delete if this element isn't persistent. */
		if (!transaction.isPersistent()) return;
		else if (!customer.isPersistent()) return;
		
		try {
			statement = (PreparedStatement) con.prepareStatement(deleteSQL);
			statement.setLong(1, customer.getId());
			statement.setLong(2, transaction.getId());
			rowCount = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CDException("TransactionManager.delete: failed to delete from CustomerTransaction: " + e);
		} // try-catch
		
		// If no rows were affected by this query, we know the deletion failed.
		if (rowCount == 0) throw new CDException("TransactionManager.delete: failed to delete from CustomerTransaction.");		
	} // deleteTransactionOrderedByCustomer
	
	public List<Transaction> restoreTransactionCompletedByWorker(Worker worker) throws CDException {
		String selectSQL = "select from WorkerTransaction where workerId = ?";
		PreparedStatement statement = null;
		List<Transaction> transactionList = new ArrayList<Transaction>();
		long transactionId;
		
		if (!worker.isPersistent()) return transactionList; // Return an empty list
		
		try {
			statement = (PreparedStatement) con.prepareStatement(selectSQL);
			statement.setLong(1, worker.getId());
			statement.execute();
			ResultSet result = statement.getResultSet();
			while (result.next()) {
				transactionId = result.getLong(2);
				Transaction modelTransaction = objectLayer.createTransaction();
				modelTransaction.setId(transactionId);
				List<Transaction> transactionCompletedByWorker = restore(modelTransaction);
				Transaction transaction = transactionCompletedByWorker.get(0); // there can only be one transaction that matches the model.
				transactionList.add(transaction);
			} // while
		} catch (SQLException e) {
			throw new CDException("TransactionManager.restore: Could not restore a list of transactions. Reason: " + e);
		} // try-catch
		
		return transactionList;
	} // restoreTransactionCompletedByWorker
	
	public List<Transaction> restoreTransactionOrderedByCustomer(Customer customer) throws CDException {
		String selectSQL = "select from CustomerTransaction where customerId = ?";
		PreparedStatement statement = null;
		List<Transaction> transactionList = new ArrayList<Transaction>();
		long transactionId;
		
		if (!customer.isPersistent()) return transactionList; // Return an empty list
		
		try {
			statement = (PreparedStatement) con.prepareStatement(selectSQL);
			statement.setLong(1, customer.getId());
			statement.execute();
			ResultSet result = statement.getResultSet();
			while (result.next()) {
				transactionId = result.getLong(2);
				Transaction modelTransaction = objectLayer.createTransaction();
				modelTransaction.setId(transactionId);
				List<Transaction> transactionCompletedByWorker = restore(modelTransaction);
				Transaction transaction = transactionCompletedByWorker.get(0); // there can only be one transaction that matches the model.
				transactionList.add(transaction);
			} // while
		} catch (SQLException e) {
			throw new CDException("TransactionManager.restore: Could not restore a list of transactions. Reason: " + e);
		} // try-catch
		
		return transactionList;
	} // restoreTransactionOrderedByCustomer
	
} // TransactionManager
