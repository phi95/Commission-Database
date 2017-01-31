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
	
	public TransactionImpl(Date date, String description, Customer customer, Worker worker, double transactionAmount) {
		this.date = date;
		this.description = description;
		this.customer = customer;
		this.worker = worker;
		this.transactionAmount = transactionAmount;
		
	} // TransactionImpl
	
	@Override
	public Date getDate() {
		return date;
	} // getDate

	@Override
	public void setDate(Date date) {
		this.date = date;
	} // setDate

	@Override
	public String getDescription() {
		return description;
	} // getDescription

	@Override
	public void setDescription(String description) {
		this.description = description;
	} // setDescription

	@Override
	public Customer getCustomer() {
		return customer;
	} // getCustomer

	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;
	} // setCustomer

	@Override
	public void setWorker(Worker worker) {
		this.worker = worker;
	} // setWorker

	@Override
	public Worker getWorker() {
		return worker;
	} // getWorker

	@Override
	public double getTransactionAmount() {
		return transactionAmount;
	} // getTransactionAmount

	@Override
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	} // setTransactionAmount

}
