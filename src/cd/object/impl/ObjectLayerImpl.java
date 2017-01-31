package cd.object.impl;

import java.util.Date;
import java.util.List;

import cd.CDException;
import cd.entity.Customer;
import cd.entity.Manager;
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.entity.impl.CustomerImpl;
import cd.entity.impl.ManagerImpl;
import cd.entity.impl.TransactionImpl;
import cd.entity.impl.WorkerImpl;
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
	public Customer createCustomer(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) throws CDException {
		Customer customer = new CustomerImpl(firstName, lastName, userName, password, email, phoneNumber);
		return customer;
	}

	@Override
	public Customer createCustomer() {
		Customer customer = new CustomerImpl();
		return customer;
	}

	@Override
	public List<Customer> findCustomer(Customer modelCustomer) throws CDException {
		return persistence.restoreCustomer(modelCustomer);
	}

	@Override
	public void storeCustomer(Customer customer) throws CDException {
		persistence.storeCustomer(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) throws CDException {
		persistence.deleteCustomer(customer);
	}

	@Override
	public Manager createManager(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) throws CDException {
		Manager manager = new ManagerImpl(firstName, lastName, userName, password, email, phoneNumber);
		return manager;
	}

	@Override
	public Manager createManager() {
		Manager manager = new ManagerImpl();
		return manager;
	}

	@Override
	public List<Manager> findManager(Manager modelManager) throws CDException {
		return persistence.restoreManager(modelManager);
	}

	@Override
	public void storeManager(Manager manager) throws CDException {
		persistence.storeManager(manager);
	}

	@Override
	public void deleteManager(Manager manager) throws CDException {
		persistence.deleteManager(manager);
	}

	@Override
	public Transaction createTransaction(Date date, String description, Customer customer, Worker worker,
			double transactionAmount) throws CDException {
		Transaction transaction = new TransactionImpl(date, description, customer, worker, transactionAmount);
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
	public Worker createWorker(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) throws CDException {
		Worker worker = new WorkerImpl(firstName, lastName, userName, password, email, phoneNumber);
		return worker;
	}

	@Override
	public Worker createWorker() {
		Worker worker = new WorkerImpl();
		return worker;
	}

	@Override
	public List<Worker> findWorker(Worker modelWorker) throws CDException {
		return persistence.restoreWorker(modelWorker);
	}

	@Override
	public void storeWorker(Worker worker) throws CDException {
		persistence.storeWorker(worker);
	}

	@Override
	public void deleteWorker(Worker worker) throws CDException {
		persistence.deleteWorker(worker);
	}

}
