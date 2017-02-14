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
	 * Returns the employee this transaction is associated with.
	 * @return the transaction's employee
	 */
	public Employee getEmployee();
	
	/**
	 * Set transaction's employee.
	 * @param employee set the employee this transaction is associated with
	 */
	public void setEmployee(Employee employee);

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
