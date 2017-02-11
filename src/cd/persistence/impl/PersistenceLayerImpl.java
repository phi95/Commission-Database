package cd.persistence.impl;

import java.util.List;

import java.sql.Connection;

import cd.CDException;
import cd.entity.Customer;
import cd.entity.Manager;
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.object.ObjectLayer;
import cd.persistence.PersistenceLayer;

public class PersistenceLayerImpl implements PersistenceLayer {

	private CustomerManager customerManager = null;
	private ManagerManager managerManager = null;
	private TransactionManager transactionManager = null;
	private WorkerManager workerManager = null;
	
	public PersistenceLayerImpl(Connection conn, ObjectLayer objectLayer){
		customerManager = new CustomerManager(conn, objectLayer);
		managerManager = new ManagerManager(conn, objectLayer);
		transactionManager = new TransactionManager(conn, objectLayer);
		workerManager = new WorkerManager(conn, objectLayer);
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
	public List<Manager> restoreManager(Manager modelManager) throws CDException {
		return managerManager.restore(modelManager);
	}

	@Override
	public void storeManager(Manager manager) throws CDException {
		managerManager.store(manager);
	}

	@Override
	public void deleteManager(Manager manager) throws CDException {
		managerManager.delete(manager);
	}

	@Override
	public List<Worker> restoreWorker(Worker modelWorker) throws CDException {
		return workerManager.restore(modelWorker);
	}

	@Override
	public void storeWorker(Worker worker) throws CDException {
		workerManager.store(worker);
	}

	@Override
	public void deleteWorker(Worker worker) throws CDException {
		workerManager.delete(worker);
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
	public void storeWorkerEmployedByManager(Worker worker, Manager manager) throws CDException {
		workerManager.storeWorkerEmployedByManager(worker, manager);
	}

	@Override
	public void storeTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException {
		transactionManager.storeTransactionCompletedByWorker(transaction, worker);
	}

	@Override
	public void storeTransactionOrderedByCustomer(Transaction transaction, Customer customer) throws CDException {
		transactionManager.storeTransactionOrderedByCustomer(transaction, customer);
		
	}

	@Override
	public void deleteWorkerEmployedByManager(Worker worker, Manager manager) throws CDException {
		workerManager.deleteWorkerEmployedByManager(worker, manager);
	}

	@Override
	public void deleteTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException {
		transactionManager.deleteTransactionCompletedByWorker(transaction, worker);
	}

	@Override
	public void deleteTransactionOrderedByCustomer(Transaction transaction, Customer customer) throws CDException {
		transactionManager.deleteTransactionOrderedByCustomer(transaction, customer);
	}

	@Override
	public List<Worker> restoreWorkerEmployedByManager(Manager manager) throws CDException {
		return workerManager.restoreWorkerEmployedByManager(manager);
	}

	@Override
	public Manager restoreManagerFromWorker(Worker worker) throws CDException {
		return managerManager.restoreManagerFromWorker(worker);
	}

	@Override
	public List<Transaction> restoreTransactionCompletedByWorker(Worker worker) throws CDException {
		return transactionManager.restoreTransactionCompletedByWorker(worker);
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
	public Worker restoreWorkerFromTransaction(Transaction transaction) throws CDException {
		return workerManager.restoreWorkerFromTransaction(transaction);
	}

}
