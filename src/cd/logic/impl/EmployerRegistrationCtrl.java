package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employer;
import cd.object.ObjectLayer;
import cd.session.Session;
import cd.session.SessionManager;

public class EmployerRegistrationCtrl {

	private ObjectLayer objectLayer = null;
	
	public EmployerRegistrationCtrl(ObjectLayer objectLayer) {
		this.objectLayer = objectLayer;
	} // EmployeeRegistrationCtrl
	
	
	
	public String addEmployer(Session session, String fname, String lname, String userName, String password, String email, String phoneNumber) throws CDException {
		String ssid = null;
		
		// Check if username already exists.
		Employer employer = objectLayer.createEmployer();
		employer.setUserName(userName);
		List<Employer> employersWithUsername = objectLayer.findEmployer(employer);
		if (!employersWithUsername.isEmpty()) throw new CDException("A person with this username already exists.");
		
		// Create user if username is unique.
		employer = objectLayer.createEmployer(fname, lname, userName, password, email, phoneNumber);
		objectLayer.storeEmployer(employer);
		
		// Session handling
		session.setUser(employer);
		ssid = SessionManager.storeSession(session);
		return ssid;
	} // addEmployee

	
	
}
