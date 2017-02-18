package cd.object.impl;

import java.util.Date;
import java.util.List;

import cd.CDException;
import cd.entity.Employer;
import cd.entity.Transaction;
import cd.entity.Employee;
import cd.entity.impl.EmployerImpl;
import cd.entity.impl.TransactionImpl;
import cd.entity.impl.EmployeeImpl;
import cd.object.ObjectLayer;
import cd.persistence.PersistenceLayer;

public class ObjectLayerImpl implements ObjectLayer {

	private PersistenceLayer persistence = null;
	
    public ObjectLayerImpl()
    {
        this.persistence = null;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(): initialized" );
    }
    
    public ObjectLayerImpl( PersistenceLayer persistence )
    {
        this.persistence = persistence;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(persistence): initialized" );
    }

	@Override
	public Employer createEmployer(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) throws CDException {
		Employer employer = new EmployerImpl(firstName, lastName, userName, password, email, phoneNumber);
		return employer;
	}

	@Override
	public Employer createEmployer() {
		Employer employer = new EmployerImpl();
		return employer;
	}

	@Override
	public List<Employer> findEmployer(Employer modelEmployer) throws CDException {
		return persistence.restoreEmployer(modelEmployer);
	}

	@Override
	public void storeEmployer(Employer employer) throws CDException {
		persistence.storeEmployer(employer);
	}

	@Override
	public void deleteEmployer(Employer employer) throws CDException {
		persistence.deleteEmployer(employer);
	}

	@Override
	public Transaction createTransaction(Employee employee, Date date, String description, double transactionAmount) throws CDException {
		Transaction transaction = new TransactionImpl(employee, date, description, transactionAmount);
		return transaction;
	}

	@Override
	public Transaction createTransaction() {
		Transaction transaction = new TransactionImpl();
		return transaction;
	}

	@Override
	public List<Transaction> findTransaction(Transaction modelTransaction) throws CDException {
		return persistence.restoreTransaction(modelTransaction);
	}

	@Override
	public void storeTransaction(Transaction transaction) throws CDException {
		persistence.storeTransaction(transaction);
	}

	@Override
	public void deleteTransaction(Transaction transaction) throws CDException {
		persistence.deleteTransaction(transaction);
	}

	@Override
	public Employee createEmployee(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) throws CDException {
		Employee employee = new EmployeeImpl(firstName, lastName, userName, password, email, phoneNumber);
		return employee;
	}

	@Override
	public Employee createEmployee() {
		Employee employee = new EmployeeImpl();
		return employee;
	}

	@Override
	public List<Employee> findEmployee(Employee modelEmployee) throws CDException {
		return persistence.restoreEmployee(modelEmployee);
	}

	@Override
	public void storeEmployee(Employee employee) throws CDException {
		persistence.storeEmployee(employee);
	}

	@Override
	public void deleteEmployee(Employee employee) throws CDException {
		persistence.deleteEmployee(employee);
	}

	@Override
	public PersistenceLayer getPersistence() {
		return persistence;
	}

	@Override
	public void setPersistence(PersistenceLayer persistence) {
		this.persistence = persistence;
	}

}
