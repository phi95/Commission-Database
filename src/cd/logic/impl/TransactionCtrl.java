package cd.logic.impl;

import cd.CDException;
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
}
