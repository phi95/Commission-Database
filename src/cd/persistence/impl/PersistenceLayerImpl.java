package cd.persistence.impl;

import java.util.List;

import java.sql.Connection;

import cd.CDException;
import cd.entity.Customer;
import cd.entity.Employer;
import cd.entity.Transaction;
import cd.entity.Employee;
import cd.object.ObjectLayer;
import cd.persistence.PersistenceLayer;

public class PersistenceLayerImpl implements PersistenceLayer {

	private CustomerManager customerManager = null;
	private EmployerManager employerManager = null;
	private TransactionManager transactionManager = null;
	private EmployeeManager employeeManager = null;
	
	public PersistenceLayerImpl(Connection conn, ObjectLayer objectLayer){
		customerManager = new CustomerManager(conn, objectLayer);
		employerManager = new EmployerManager(conn, objectLayer);
		transactionManager = new TransactionManager(conn, objectLayer);
		employeeManager = new EmployeeManager(conn, objectLayer);
	}
	
	@Override
	public List<Customer> restoreCustomer(Customer modelCustomer) throws CDException {
		return customerManager.restore(modelCustomer);
	}

	@Override
	public void storeCustomer(Customer customer) throws CDException {
		customerManager.store(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) throws CDException {
		customerManager.delete(customer);
	}

	@Override
	public List<Employer> restoreEmployer(Employer modelEmployer) throws CDException {
		return employerManager.restore(modelEmployer);
	}

	@Override
	public void storeEmployer(Employer employer) throws CDException {
		employerManager.store(employer);
	}

	@Override
	public void deleteEmployer(Employer employer) throws CDException {
		employerManager.delete(employer);
	}

	@Override
	public List<Employee> restoreEmployee(Employee modelEmployee) throws CDException {
		return employeeManager.restore(modelEmployee);
	}

	@Override
	public void storeEmployee(Employee employee) throws CDException {
		employeeManager.store(employee);
	}

	@Override
	public void deleteEmployee(Employee employee) throws CDException {
		employeeManager.delete(employee);
	}

	@Override
	public List<Transaction> restoreTransaction(Transaction modelTransaction) throws CDException {
		return transactionManager.restore(modelTransaction);
	}

	@Override
	public void storeTransaction(Transaction transaction) throws CDException {
		transactionManager.store(transaction);
	}

	@Override
	public void deleteTransaction(Transaction transaction) throws CDException {
		transactionManager.delete(transaction);
	}

	@Override
	public void storeEmployeeEmployedByEmployer(Employee employee, Employer employer) throws CDException {
		employeeManager.storeEmployeeEmployedByEmployer(employee, employer);
	}

	@Override
	public void storeTransactionCompletedByEmployee(Transaction transaction, Employee employee) throws CDException {
		transactionManager.storeTransactionCompletedByEmployee(transaction, employee);
	}

	@Override
	public void storeTransactionOrderedByCustomer(Transaction transaction, Customer customer) throws CDException {
		transactionManager.storeTransactionOrderedByCustomer(transaction, customer);
		
	}

	@Override
	public void deleteEmployeeEmployedByEmployer(Employee employee, Employer employer) throws CDException {
		employeeManager.deleteEmployeeEmployedByEmployer(employee, employer);
	}

	@Override
	public void deleteTransactionCompletedByEmployee(Transaction transaction, Employee employee) throws CDException {
		transactionManager.deleteTransactionCompletedByEmployee(transaction, employee);
	}

	@Override
	public void deleteTransactionOrderedByCustomer(Transaction transaction, Customer customer) throws CDException {
		transactionManager.deleteTransactionOrderedByCustomer(transaction, customer);
	}

	@Override
	public List<Employee> restoreEmployeeEmployedByEmployer(Employer employer) throws CDException {
		return employeeManager.restoreEmployeeEmployedByEmployer(employer);
	}

	@Override
	public Employer restoreEmployerFromEmployee(Employee employee) throws CDException {
		return employerManager.restoreEmployerFromEmployee(employee);
	}

	@Override
	public List<Transaction> restoreTransactionCompletedByEmployee(Employee employee) throws CDException {
		return transactionManager.restoreTransactionCompletedByEmployee(employee);
	}

	@Override
	public List<Transaction> restoreTransactionOrderedByCustomer(Customer customer) throws CDException {
		return transactionManager.restoreTransactionOrderedByCustomer(customer);
	}

	@Override
	public Customer restoreCustomerFromTransaction(Transaction transaction) throws CDException {
		return customerManager.restoreCustomerFromTransaction(transaction);
	}

	@Override
	public Employee restoreEmployeeFromTransaction(Transaction transaction) throws CDException {
		return employeeManager.restoreEmployeeFromTransaction(transaction);
	}

}
