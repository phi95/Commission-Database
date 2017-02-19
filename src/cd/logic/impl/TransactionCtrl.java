package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.entity.Transaction;
import cd.object.ObjectLayer;

public class TransactionCtrl {

private ObjectLayer objectLayer = null;
	
	public TransactionCtrl (ObjectLayer objectLayer) {
		this.objectLayer = objectLayer;
	} // TransactionCtrl
	
	public void addTransaction(String amount, String description) throws CDException{
		Transaction transaction = objectLayer.createTransaction();
		transaction.setTransactionAmount(Double.parseDouble(amount));
		transaction.setDescription(description);
		
		objectLayer.storeTransaction(transaction);
	}//addTransaction
	
	public List<Transaction> getTransactionFromEmployee(Employee employee) {
		List<Transaction> transactions = null;
		
		try {
			transactions = objectLayer.getPersistence().restoreTransactionCompletedByEmployee(employee);
		} catch (CDException e) {
			e.printStackTrace();
		} // try-catch
		
		return transactions;
		
	} // getTransactionFromEmployee
}
