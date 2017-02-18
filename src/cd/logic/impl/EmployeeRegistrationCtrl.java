package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.object.ObjectLayer;
import cd.session.Session;
import cd.session.SessionManager;



public class EmployeeRegistrationCtrl {

	private ObjectLayer objectLayer = null;
	
	public EmployeeRegistrationCtrl(ObjectLayer objectLayer) {
		this.objectLayer = objectLayer;
	} // EmployeeRegistrationCtrl
	
	
	
	public String addEmployee(Session session, String fname, String lname, String userName, String password, String email, String phoneNumber) throws CDException {
		String ssid = null;
		
		// PLAN: TO REPLACE WITH! ONLY EMPLOYERS CAN REGISTER EMPLOYEES!
		
		// Check if username already exists.
		Employee employee = objectLayer.createEmployee();
		employee.setUserName(userName);
		List<Employee> employeesWithUsername = objectLayer.findEmployee(employee);
		if (!employeesWithUsername.isEmpty()) throw new CDException("A person with this username already exists.");
		
		// Create user if username is unique.
		employee = objectLayer.createEmployee(fname, lname, userName, password, email, phoneNumber);
		objectLayer.storeEmployee(employee);
		
		// Session handling
		session.setUser(employee);
		ssid = SessionManager.storeSession(session);
		return ssid;
	} // addEmployee
	
} // EmployeeRegistrationCtrl
