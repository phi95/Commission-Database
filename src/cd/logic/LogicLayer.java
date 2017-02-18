package cd.logic;

import cd.CDException;
import cd.entity.User;
import cd.session.Session;

public interface LogicLayer {

	/**
	 * Logs into the system as an employer
	 * @return the ssid of the employer.
	 */
	String employerLogin(Session session, String username, String password) throws CDException;

	/**
	 * Logs into the system as an employee
	 * @return ssid of the employee.
	 */
	String employeeLogin(Session session, String username, String password) throws CDException;

	/**
	 * Register an employer
	 * @return ssid of the new employer.
	 */
	String addEmployer(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) throws CDException;

	/**
	 * Register an employee.
	 * @return ssid of the new employee.
	 */
	String addEmployee(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) throws CDException;

	User updateEmployerAccount(String firstName, String lastName, String username, String password, String email,
			String phoneNumber);

	User updateEmployeeAccount(String firstName, String lastName, String username, String password, String email,
			String phoneNumber);

	void addTransaction(String amount, String description) throws CDException;

}
