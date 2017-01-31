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
	 * Restore all Manager objects that match attributes of model manager.
	 * @param modelManager the model Manager; if null is provided, all Manager objects will be returned.
	 * @return a List of the located Manager objects.
	 * @throws CDException
	 */
	public List<Manager> restoreManager(Manager modelManager) throws CDException;
	
	/**
	 * Store a given Manager object in persistent data storage. If the object is already persistent,
	 * the persistent object is updated.
	 * @param manager the Manager to be stored
	 * @throws CDException
	 */
	public void storeManager(Manager manager) throws CDException;
	
	/**
	 * Delete a given Manager object from persistent data storage.
	 * @param manager the Manager to be deleted.
	 * @throws CDException
	 */
	public void deleteManager(Manager manager) throws CDException;

	/**
	 * Restore all Worker objects that match attributes of the model worker.
	 * @param modelWorker the model worker; if null is provided, all Worker objects will be returned.
	 * @return a List of the located Worker objects.
	 * @throws CDException
	 */
	public List<Worker> restoreWorker(Worker modelWorker) throws CDException;
	
	/**
	 * Store a given Worker object in persistent data storage. If the object is already persistent,
	 * the persistent object is updated.
	 * @param worker the Worker to be stored.
	 * @throws CDException
	 */
	public void storeWorker(Worker worker) throws CDException;
	
	/**
	 * Delete a given Worker from persistent data storage.
	 * @param worker The worker to be deleted.
	 * @throws CDException
	 */
	public void deleteWorker(Worker worker) throws CDException;

	/**
	 * Restore all Transactions that match attributes of the model transaction.
	 * @param modelTransaction the model Transaction; if null is provided, all Transaction objects will be returned.
	 * @return a List of the located Transaction objects.
	 * @throws CDException
	 */
	public List<Transaction> restoreTransaction(Transaction modelTransaction) throws CDException;
	
	/**
	 * Store a given Transaction in persistent data storage. If the object is already persistent,
	 * the persistent object is updated.
	 * @param transaction The transaction to be stored.
	 * @throws CDException
	 */
	public void storeTransaction(Transaction transaction) throws CDException;
	
	/**
	 * Delete a given Transaction from persistent data storage.
	 * @param transaction The transaction to be deleted.
	 * @throws CDException
	 */
	public void deleteTransaction(Transaction transaction) throws CDException;
	
	/**
	 *  Associations 
	 *  
	 *  Worker      -- employedBy  --> Manager;     Multiplicity: 1...* - 1
	 *  Transaction -- completedBy --> Worker;      Multiplicity: 1...* - 1
	 *  Transaction -- orderedBy   --> Customer;    Multiplicity: 1...* - 1
	 */
	
	/**
	 * Store a link between a Worker employed by a Manager.
	 * @param worker The worker to be linked.
	 * @param manager The manager to be linked.
	 * @throws CDException
	 */
	public void storeWorkerEmployedByManager(Worker worker, Manager manager) throws CDException;
	
	/**
	 * Store a link between a Transaction completed by a Worker.
	 * @param transaction The transaction to be linked.
	 * @param worker The worker to be linked.
	 * @throws CDException
	 */
	public void storeTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException;
	
	/**
	 * Store a link between a Transaction ordered by a Customer.
	 * @param transaction The transaction to be linked.
	 * @param customer The customer to be linked.
	 * @throws CDException
	 */
	public void storeTransactionOrderedByCustoemr(Transaction transaction, Customer customer) throws CDException;
	
	/**
	 * Delete a link between a Worker employed by a Manager
	 * @param worker The worker whose link is to be deleted.
	 * @param manager The manager whose link is to be deleted.
	 * @throws CDException
	 */
	public void deleteWorkerEmployedByManager(Worker worker, Manager manager) throws CDException;
	
	/**
	 * Delete a link between a Transaction completed by a Worker.
	 * @param transaction The transaction
	 * @param worker The worker
	 * @throws CDException
	 */
	public void deleteTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException;
	
	/**
	 * Delete a link between Transaction ordered by a customer
	 * @param transaction The transaction
	 * @param customer The customer
	 * @throws CDException
	 */
	public void deleteTransactionOrderedByCustoemr(Transaction transaction, Customer customer) throws CDException;	
	
	/**
	 * Return a List of workers employed by a given Manager.
	 * @param manager The manager.
	 * @return A List of workers.
	 * @throws CDException
	 */
	public List<Worker> restoreWorkerEmployedByManager(Manager manager) throws CDException;

	/**
	 * Return the Manager who employs a particular worker.
	 * @param worker The worker.
	 * @return The manager who employs the worker.
	 * @throws CDException
	 */
	public Manager restoreWorkerEmployedByManager(Worker worker) throws CDException;
	
	/**
	 * Return a List of Transactions completed by a particular worker.
	 * @param worker The worker.
	 * @return A List of Transactions.
	 * @throws CDException
	 */
	public List<Transaction> restoreTransactionCompletedByWorker(Worker worker) throws CDException;
	
	/**
	 * Return a List of Transactions ordered by a particular Customer.
	 * @param customer The customer.
	 * @return A List of Transactions.
	 * @throws CDException
	 */
	public List<Transaction> restoreTransactionOrderedByCustomer(Customer customer) throws CDException;
	
	/**
	 * Return the customer who ordered a particular Transaction.
	 * @param transaction The Transaction.
	 * @return The Customer who ordered the transaction.
	 * @throws CDException
	 */
	public Customer restoreTransactionOrderedByCustomer(Transaction transaction) throws CDException;
	
	/**
	 * Return the worker who completed a particular transaction.
	 * @param transaction The transaction.
	 * @return THe workered who completed the transaction.
	 * @throws CDException
	 */
	public Worker restoreTransactionCompletedByWorker(Transaction transaction) throws CDException;
	
}
