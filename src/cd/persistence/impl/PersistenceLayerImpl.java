package cd.persistence.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Customer;
import cd.entity.Manager;
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.persistence.PersistenceLayer;

public class PersistenceLayerImpl implements PersistenceLayer {

	@Override
	public List<Customer> restoreCustomer(Customer modelCustomer) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeCustomer(Customer customer) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(Customer customer) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Manager> restoreManager(Manager modelManager) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeManager(Manager manager) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteManager(Manager manager) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Worker> restoreWorker(Worker modelWorker) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeWorker(Worker worker) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWorker(Worker worker) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Transaction> restoreTransaction(Transaction modelTransaction) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeTransaction(Transaction transaction) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransaction(Transaction transaction) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeWorkerEmployedByManager(Worker worker, Manager manager) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeTransactionOrderedByCustoemr(Transaction transaction, Customer customer) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWorkerEmployedByManager(Worker worker, Manager manager) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransactionCompletedByWorker(Transaction transaction, Worker worker) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransactionOrderedByCustoemr(Transaction transaction, Customer customer) throws CDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Worker> restoreWorkerEmployedByManager(Manager manager) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manager restoreWorkerEmployedByManager(Worker worker) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> restoreTransactionCompletedByWorker(Worker worker) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> restoreTransactionOrderedByCustomer(Customer customer) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer restoreTransactionOrderedByCustomer(Transaction transaction) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worker restoreTransactionCompletedByWorker(Transaction transaction) throws CDException {
		// TODO Auto-generated method stub
		return null;
	}

}
