package cd.persistence;

import java.util.List;
import cd.CDException;
import cd.entity.Employer;
import cd.entity.Employee;
import cd.entity.Transaction;
import cd.CDException;

public interface PersistenceLayer {

	/**
	 * Restore all Employer objects that match attributes of model employer.
	 * @param modelEmployer the model Employer; if null is provided, all Employer objects will be returned.
	 * @return a List of the located Employer objects.
	 * @throws CDException
	 */
	public List<Employer> restoreEmployer(Employer modelEmployer) throws CDException;
	
	/**
	 * Store a given Employer object in persistent data storage. If the object is already persistent,
	 * the persistent object is updated.
	 * @param employer the Employer to be stored
	 * @throws CDException
	 */
	public void storeEmployer(Employer employer) throws CDException;
	
	/**
	 * Delete a given Employer object from persistent data storage.
	 * @param employer the Employer to be deleted.
	 * @throws CDException
	 */
	public void deleteEmployer(Employer employer) throws CDException;

	/**
	 * Restore all Employee objects that match attributes of the model employee.
	 * @param modelEmployee the model employee; if null is provided, all Employee objects will be returned.
	 * @return a List of the located Employee objects.
	 * @throws CDException
	 */
	public List<Employee> restoreEmployee(Employee modelEmployee) throws CDException;
	
	/**
	 * Store a given Employee object in persistent data storage. If the object is already persistent,
	 * the persistent object is updated.
	 * @param employee the Employee to be stored.
	 * @throws CDException
	 */
	public void storeEmployee(Employee employee) throws CDException;
	
	/**
	 * Delete a given Employee from persistent data storage.
	 * @param employee The employee to be deleted.
	 * @throws CDException
	 */
	public void deleteEmployee(Employee employee) throws CDException;

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
	 *  Employee      -- employedBy  --> Employer;     Multiplicity: 1...* - 1
	 *  Transaction -- completedBy --> Employee;      Multiplicity: 1...* - 1
	 *  Transaction -- orderedBy   --> Customer;    Multiplicity: 1...* - 1
	 */
	
	/**
	 * Store a link between a Employee employed by a Employer.
	 * @param employee The employee to be linked.
	 * @param employer The employer to be linked.
	 * @throws CDException
	 */
	public void storeEmployeeEmployedByEmployer(Employee employee, Employer employer) throws CDException;
	
	/**
	 * Store a link between a Transaction completed by a Employee.
	 * @param transaction The transaction to be linked.
	 * @param employee The employee to be linked.
	 * @throws CDException
	 */
	public void storeTransactionCompletedByEmployee(Transaction transaction, Employee employee) throws CDException;
	
	/**
	 * Delete a link between a Employee employed by a Employer
	 * @param employee The employee whose link is to be deleted.
	 * @param employer The employer whose link is to be deleted.
	 * @throws CDException
	 */
	public void deleteEmployeeEmployedByEmployer(Employee employee, Employer employer) throws CDException;
	
	/**
	 * Delete a link between a Transaction completed by a Employee.
	 * @param transaction The transaction
	 * @param employee The employee
	 * @throws CDException
	 */
	public void deleteTransactionCompletedByEmployee(Transaction transaction, Employee employee) throws CDException;
	
	/**
	 * Return a List of employees employed by a given Employer.
	 * @param employer The employer.
	 * @return A List of employees.
	 * @throws CDException
	 */
	public List<Employee> restoreEmployeeEmployedByEmployer(Employer employer) throws CDException;

	/**
	 * Return the Employer who employs a particular employee.
	 * @param employee The employee.
	 * @return The employer who employs the employee.
	 * @throws CDException
	 */
	public Employer restoreEmployerFromEmployee(Employee employee) throws CDException;
	
	/**
	 * Return a List of Transactions completed by a particular employee.
	 * @param employee The employee.
	 * @return A List of Transactions.
	 * @throws CDException
	 */
	public List<Transaction> restoreTransactionCompletedByEmployee(Employee employee) throws CDException;
	
	/**
	 * Return the employee who completed a particular transaction.
	 * @param transaction The transaction.
	 * @return THe employee who completed the transaction.
	 * @throws CDException
	 */
	public Employee restoreEmployeeFromTransaction(Transaction transaction) throws CDException;
	
}
