package cd.object;

import java.util.Date;
import java.util.List;
import cd.CDException;
import cd.entity.Customer;
import cd.entity.Manager;
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.persistence.PersistenceLayer;
import cd.CDException;

public interface ObjectLayer {
	
	/**
	 * Creates a new customer object with the given attributes.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userName the username (note: should be null)
	 * @param password the password (note: should be null)
	 * @param email the customer's email address.
	 * @param phoneNumbers the customer's phone number.
	 * @return A new customer object with the given attributes.
	 * @throws CDException
	 */
	public Customer createCustomer(String firstName, String lastName, String userName, String password, String email, String phoneNumber) throws CDException;
	
	/**
	 * Creates a customer object with undefined attributes.
	 * @return A customer object.
	 */
	public Customer createCustomer();
	
	/**
	 * Creates a list of Customer objects that satisfy the search criteria.
	 * @param modelCustomer the customer object that specifies the search criteria.
	 * @return A list of customers that match the criteria(s).
	 * @throws CDException may happen if there is a problem retrieving the requested object(s).
	 */
	public List<Customer> findCustomer(Customer modelCustomer) throws CDException;
	
	/**
	 * Stores the given customer in persistent data storage.
	 * @param customer The object to be stored.
	 * @throws CDException may happen if there is a problem with storing the object.
	 */
	public void storeCustomer(Customer customer) throws CDException;
	
	/**
     * Delete this Customer object.
     * @param customer the object to be deleted.
     * @throws CDException in case there is a problem with the deletion of the object
     */
    public void deleteCustomer( Customer customer ) throws CDException;

	/**
	 * Creates a new manager object with the given attributes.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userName the username 
	 * @param password the password 
	 * @param email the customer's email address.
	 * @param phoneNumbers the customer's phone number.
	 * @return A new Manager object with the given attributes.
	 * @throws CDException
	 */
	public Manager createManager(String firstName, String lastName, String userName, String password, String email, String phoneNumber) throws CDException;

	/**
	 * Creates a Manager object with undefined attributes.
	 * @return A Manager object.
	 */
	public Manager createManager();
	
	/**
	 * Creates a list of Manager objects that satisfy the search criteria.
	 * @param modelManager the Manager object that specifies the search criteria.
	 * @return A list of managers that match the criteria(s).
	 * @throws CDException may happen if there is a problem retrieving the requested object(s).
	 */
	public List<Manager> findManager(Manager modelManager) throws CDException;
	
	/**
	 * Stores the given Manager in persistent data storage.
	 * @param manager The object to be stored.
	 * @throws CDException may happen if there is a problem with storing the object.
	 */
	public void storeManager(Manager manager) throws CDException;
	
	/**
     * Delete this Manager object.
     * @param manager the object to be deleted.
     * @throws CDException in case there is a problem with the deletion of the object
     */
    public void deleteManager( Manager manager ) throws CDException;
	
	/**
	 * Creates a Transaction object with the given attributes.
	 * @param date The date this transaction occurred.
	 * @param description A description of this transaction.
	 * @param customer The customer ordering the commission.
	 * @param worker The worker intaking the commission.
	 * @param transactionAmount The amount paid for the commission.
	 * @return A Transaction object.
	 * @throws CDException
	 */
	public Transaction createTransaction(Date date, String description, Customer customer, Worker worker, double transactionAmount) throws CDException; 
	
	/**
	 * Creates a Transaction object with undefined attributes.
	 * @return A Transaction object.
	 */
	public Transaction createTransaction();
	
	/**
	 * Retrieves a List of all transactions that match the specified search criteria.
	 * @param modelTransaction A transaction object that contains the search criteria.
	 * @return A List of transactions.
	 * @throws CDException
	 */
	public List<Transaction> findTransaction(Transaction modelTransaction) throws CDException;
	
	/**
	 * Stores a Transaction object into persistent data storage.
	 * @param transaction The object to be stored.
	 * @throws CDException
	 */
	public void storeTransaction(Transaction transaction) throws CDException;
	
	/**
     * Delete this Transaction object.
     * @param transaction the object to be deleted.
     * @throws CDException in case there is a problem with the deletion of the object
     */
    public void deleteTransaction( Transaction transaction ) throws CDException;
	
	/**
	 * Creates a Worker object with the given attributes.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userName the username 
	 * @param password the password 
	 * @param email the customer's email address.
	 * @param phoneNumbers the customer's phone number.
	 * @return A new Worker object with the given attributes.
	 * @throws CDException
	 */
	public Worker createWorker(String firstName, String lastName, String userName, String password, String email, String phoneNumber) throws CDException;
	
	/**
	 * Creates a Worker with undefined attributes.
	 * @return A Worker object.
	 */
	public Worker createWorker();

	/**
	 * Retrieves a List of all workers that match the specified search criteria.
	 * @param modelTransaction A Worker object that contains the search criteria.
	 * @return A List of workers.
	 * @throws CDException
	 */
	
	public List<Worker> findWorker(Worker modelWorker) throws CDException;
	
	/**
	 * Stores a Worker object into persistent data storage.
	 * @param transaction The object to be stored.
	 * @throws CDException
	 */
	public void storeWorker(Worker worker) throws CDException;
	
	/**
     * Delete this Worker object.
     * @param worker the object to be deleted.
     * @throws CDException in case there is a problem with the deletion of the object
     */
    public void deleteWorker( Worker worker ) throws CDException;
    
    /**
     * Returning this ObectLayer's PersistenceLayer
     */
	public PersistenceLayer getPersistence();
    
    /**
     * setting a persistence layer to this objectlayer
     * @param persistence the PersistenceLayer to set
     */
	public void setPersistence(PersistenceLayer persistence);

}
