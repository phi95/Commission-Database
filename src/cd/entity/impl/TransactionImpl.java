package cd.entity.impl;

import java.util.Date;

import cd.CDException;
import cd.entity.Customer;
import cd.entity.Transaction;
import cd.entity.Worker;
import cd.persistence.impl.Persistent;

public class TransactionImpl extends Persistent implements Transaction {

	private Date date;
	private String description;
	private Customer customer;
	private Worker worker;
	private double transactionAmount;
	
	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String Description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Worker getWorker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTransactionAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTransactionAmount(double transactionAmount) {
		// TODO Auto-generated method stub
		
	}

}
