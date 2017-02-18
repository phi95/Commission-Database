package cd.entity.impl;

import java.util.Date;

import cd.CDException;
import cd.entity.Transaction;
import cd.entity.Employee;
import cd.persistence.impl.Persistent;

public class TransactionImpl extends Persistent implements Transaction {

	private Date date;
	private String description;
	private Employee employee;
	private double transactionAmount;
	
	public TransactionImpl(){
		date = null;
		description = null;
		employee = null;
		transactionAmount = 0;
	}
	
	public TransactionImpl(Employee worker, Date date, String description, double transactionAmount) {
		this.date = date;
		this.description = description;
		this.employee = worker;
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
	public void setEmployee(Employee employee) {
		this.employee = employee;
	} // setWorker

	@Override
	public Employee getEmployee() {
		return employee;
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
