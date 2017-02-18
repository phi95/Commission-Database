package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.entity.User;
import cd.object.ObjectLayer;

public class EmployeeUpdateCtrl {
	private ObjectLayer objectLayer = null;
	
	public EmployeeUpdateCtrl (ObjectLayer objectLayer) {
		this.objectLayer = objectLayer;
	} // EmployerUpdateCtrl
	
	public User updateEmployee(String fname, String lname, String username, String password, String email, String phoneNumber) {
		Employee employee = objectLayer.createEmployee();
		employee.setUserName(username);
		
		try {
			// finding persistent employer w/ matching username
			List<Employee> employeesWithUserName = objectLayer.findEmployee(employee);
			if (employeesWithUserName.size() == 1) employee = employeesWithUserName.get(0);
			else return null;
			
			// Updating employer info
			employee.setFirstName(fname);
			employee.setLastName(lname);
			employee.setPassword(password);
			employee.setEmailAddress(email);
			employee.setPhoneNumber(phoneNumber);
			objectLayer.storeEmployee(employee);
		} catch (CDException e) {
			e.printStackTrace();
		} // try-catch
		
		return employee;
	} // updateEmployer
}
