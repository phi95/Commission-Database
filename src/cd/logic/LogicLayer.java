package cd.logic;

import cd.CDException;
import cd.session.Session;

public interface LogicLayer {

	String employerLogin(Session session, String username, String password);

	String employeeLogin(Session session, String username, String password);

	String addEmployer(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber);

	String addEmployee(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber);

}
