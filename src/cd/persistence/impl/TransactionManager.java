package cd.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cd.CDException;
import cd.entity.Transaction;
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
			if(transaction.getDate() != null) statement.setString(1, transaction.getDate().toString());
			else throw new CDException("TransactionManager.save: cannot save a transaction: date undefined");
			
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
							if (transactionId > 0) {
								transaction.setId(transactionId);
								statement.setLong(4, transactionId);
							} // if
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
		List <Transaction> transactions = new ArrayList<Transaction>();
		
		condition.setLength(0);
		query.append(selectTransaction);
		
		if (modelTransaction != null) {
	
		} // if
		return null;
		
	} // restore
	
	public void delete (Transaction transaction) throws CDException {
		
	} // delete
	
}
