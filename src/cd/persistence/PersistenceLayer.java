package cd.persistence;

import java.util.List;
import cd.CDException;
import cd.entity.Customer;
import cd.entity.Manager;
import cd.entity.Worker;
import cd.entity.Transaction;
import cd.CDException;

public interface PersistenceLayer {

	/**
	 * Restore all Customer objects that match attributes of model customer.
	 * @param modelCustomer the model Customer; if null is provided, all Customer objects will be returned
	 * @return a List of the located Customer objects
	 * @throws CDException
	 */
	public List<Customer> restoreCustomer(Customer modelCustomer) throws CDException;
	
	/**
	 * Store a given Customer object in persistent data storage. If the object is already persistent,
	 * the persistent object in data storage is updated.
	 * @param customer the Customer to be stored
	 * @throws CDException 
	 */
	public void storeCustomer(Customer customer) throws CDException;
	
	/**
	 * Delete a given Customer object from persistent data storage.
	 * @param customer the Customer to be deleted.
	 * @throws CDException
	 */
	public void deleteCustomer(Customer customer) throws CDException;

	/**
	 * 
	 * @param modelManager
	 * @return
	 * @throws CDException
	 */
	public List<Manager> restoreManager(Manager modelManager) throws CDException;
	public void storeManager(Manager manager) throws CDException;
	public void deleteManger(Manager manager) throws CDException;

	public List<Worker> restoreWorker(Worker modelWorker) throws CDException;
	public void storeWorker(Worker worker) throws CDException;
	public void deleteWorker(Worker worker) throws CDException;

	public List<Transaction> restoreTransaction(Transaction modelTransaction) throws CDException;
	public void storeTransaction(Transaction transaction) throws CDException;
	public void deleteTransaction(Transaction transaction) throws CDException;
	
	/**
	 *  Associations 
	 *  
	 *  Worker      -- employedBy  --> Manager;     Multiplicity: 1...* - 1
	 *  Transaction -- completedBy --> Worker;      Multiplicity: 1...* - 1
	 *  Transaction -- orderedBy   --> Customer;    Multiplicity: 1...* - 1
	 */

	public void storeWorkerEmployedByManager(Worker worker, Manager manager) throws CDException;
	public List<Worker> restoreWorkerEmployedByManager(Manager manager) throws CDException;
	public Manager restoreWorkerEmployedByManager(Worker worker) throws CDException;
	
	public List<Transaction> restoreTransactionCompletedByWorker(Worker worker) throws CDException;
	public List<Transaction> restoreTransactionOrderedByCustomer(Customer customer) throws CDException;

	public Customer restoreTransactionOrderedByCustomer(Transaction transaction) throws CDException;
	public Worker restoreTransactionCompletedByWorker(Transaction transaction) throws CDException;
	
	
}
