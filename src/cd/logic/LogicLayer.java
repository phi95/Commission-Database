package cd.logic;

import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.entity.Employer;
import cd.entity.Transaction;
import cd.entity.User;
import cd.session.Session;

public interface LogicLayer {

	/**
	 * Logs into the system as an employer
	 * @return the ssid of the employer.
	 */
	public String employerLogin(Session session, String username, String password) throws CDException;

	/**
	 * Logs into the system as an employee
	 * @return ssid of the employee.
	 */
	public String employeeLogin(Session session, String username, String password) throws CDException;

	/**
	 * Register an employer
	 * @return ssid of the new employer.
	 */
	public String addEmployer(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) throws CDException;

	/**
	 * Register an employee.
	 * @return ssid of the new employee.
	 */
	public String addEmployee(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) throws CDException;

	public User updateEmployerAccount(String firstName, String lastName, String username, String password, String email,
			String phoneNumber);

	public User updateEmployeeAccount(String firstName, String lastName, String username, String password, String email,
			String phoneNumber);

	public void addTransaction(String amount, String description) throws CDException;
	
	public List<Transaction> getTransactionsFromEmployee(Employee employee) throws CDException;
	
	public List<Employee> getEmployeesFromEmployer(Employer employer) throws CDException;
	
	public double totalTransactions(List<Transaction> transactions) throws CDException;

}
