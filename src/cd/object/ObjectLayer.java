package cd.object;

import java.util.Date;
import java.util.List;
import cd.CDException;
import cd.entity.Employer;
import cd.entity.Transaction;
import cd.entity.Employee;
import cd.persistence.PersistenceLayer;
import cd.CDException;

public interface ObjectLayer {

	/**
	 * Creates a new employer object with the given attributes.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userName the username 
	 * @param password the password 
	 * @param email the customer's email address.
	 * @param phoneNumbers the customer's phone number.
	 * @return A new Employer object with the given attributes.
	 * @throws CDException
	 */
	public Employer createEmployer(String firstName, String lastName, String userName, String password, String email, String phoneNumber) throws CDException;

	/**
	 * Creates a Employer object with undefined attributes.
	 * @return A Employer object.
	 */
	public Employer createEmployer();
	
	/**
	 * Creates a list of Employer objects that satisfy the search criteria.
	 * @param modelEmployer the Employer object that specifies the search criteria.
	 * @return A list of employers that match the criteria(s).
	 * @throws CDException may happen if there is a problem retrieving the requested object(s).
	 */
	public List<Employer> findEmployer(Employer modelEmployer) throws CDException;
	
	/**
	 * Stores the given Employer in persistent data storage.
	 * @param employer The object to be stored.
	 * @throws CDException may happen if there is a problem with storing the object.
	 */
	public void storeEmployer(Employer employer) throws CDException;
	
	/**
     * Delete this Employer object.
     * @param employer the object to be deleted.
     * @throws CDException in case there is a problem with the deletion of the object
     */
    public void deleteEmployer( Employer employer ) throws CDException;
	
	/**
	 * Creates a Transaction object with the given attributes.
	 * @param date The date this transaction occurred.
	 * @param description A description of this transaction.
	 * @param customer The customer ordering the commission.
	 * @param employee The employee intaking the commission.
	 * @param transactionAmount The amount paid for the commission.
	 * @return A Transaction object.
	 * @throws CDException
	 */
	public Transaction createTransaction(Employee employee, Date date, String description, double transactionAmount) throws CDException; 
	
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
	 * Creates a Employee object with the given attributes.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userName the username 
	 * @param password the password 
	 * @param email the customer's email address.
	 * @param phoneNumbers the customer's phone number.
	 * @return A new Employee object with the given attributes.
	 * @throws CDException
	 */
	public Employee createEmployee(String firstName, String lastName, String userName, String password, String email, String phoneNumber) throws CDException;
	
	/**
	 * Creates a Employee with undefined attributes.
	 * @return A Employee object.
	 */
	public Employee createEmployee();

	/**
	 * Retrieves a List of all employees that match the specified search criteria.
	 * @param modelTransaction A Employee object that contains the search criteria.
	 * @return A List of employees.
	 * @throws CDException
	 */
	
	public List<Employee> findEmployee(Employee modelEmployee) throws CDException;
	
	/**
	 * Stores a Employee object into persistent data storage.
	 * @param transaction The object to be stored.
	 * @throws CDException
	 */
	public void storeEmployee(Employee employee) throws CDException;
	
	/**
     * Delete this Employee object.
     * @param employee the object to be deleted.
     * @throws CDException in case there is a problem with the deletion of the object
     */
    public void deleteEmployee( Employee employee ) throws CDException;
    
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
