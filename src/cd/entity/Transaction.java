package cd.entity;

import cd.CDException;
import cd.persistence.Persistable;
import java.util.Date;
public interface Transaction extends Persistable {	

	/**
	 * Retrieves the date of the transaction.
	 * @return the transaction's date
	 */
	public Date getDate();
	
	/**
	 * Set the date of the transaction.
	 * @param date set new date for transaction's date
	 */
	public void setDate(Date date);
	
	/**
	 * Returns the description of the transaction.
	 * @return the transaction's description
	 */
	public String getDescription();
	
	/**
	 * Set transaction's description.
	 * @param description set new description for transaction
	 */
	public void setDescription(String description);
	
	/**
	 * Returns the customer this transaction is associated with.
	 * @return the transaction's customer
	 */
	public Customer getCustomer();
	
	/**
	 * Set transaction's customer.
	 * @param customer set the customer this transaction is associated with
	 */
	public void setCustomer(Customer customer);
		
	/**
	 * Returns the worker this transaction is associated with.
	 * @return the transaction's worker
	 */
	public Worker getWorker();
	
	/**
	 * Set transaction's worker.
	 * @param worker set the worker this transaction is associated with
	 */
	public void setWorker(Worker worker);

	/**
	 * Accesses the amount of money earned from this commission.
	 * @return the amount of money earned from this commission.
	 */
	public double getTransactionAmount();
	
	/**
	 * Sets the amount of money earned from this commission.
	 * @param transactionAmount the amount of money earned from this commission
	 */
	public void setTransactionAmount(double transactionAmount);
}
