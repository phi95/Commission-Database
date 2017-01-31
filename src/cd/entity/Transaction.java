package cd.entity;

import cd.CDException;
import cd.persistence.Persistable;
import java.util.Date;
public interface Transaction extends Persistable {	

	/**
	 * Retrieves the date of the transaction.
	 * @return the transaction's date.
	 */
	public Date getDate();
	
	public void setDate(Date date);
	
	public String getDescription();
	
	public void setDescription(String Description);
	
	public Customer getCustomer();
	
	public void setCustomer(Customer customer);
	
	public void setWorker(Worker worker);
	
	public Worker getWorker();
	
	
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
