package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employer;
import cd.entity.User;
import cd.object.ObjectLayer;

public class EmployerUpdateCtrl {

	private ObjectLayer objectLayer = null;
	
	public EmployerUpdateCtrl (ObjectLayer objectLayer) {
		this.objectLayer = objectLayer;
	} // EmployerUpdateCtrl
	
	public User updateEmployer(String fname, String lname, String username, String password, String email, String phoneNumber) {
		Employer employer = objectLayer.createEmployer();
		employer.setUserName(username);
		
		try {
			// finding persistent employer w/ matching username
			List<Employer> employersWithUserName = objectLayer.findEmployer(employer);
			if (employersWithUserName.size() == 1) employer = employersWithUserName.get(0);
			else return null;
			
			// Updating employer info
			employer.setFirstName(fname);
			employer.setLastName(lname);
			employer.setPassword(password);
			employer.setEmailAddress(email);
			employer.setPhoneNumber(phoneNumber);
			objectLayer.storeEmployer(employer);
		} catch (CDException e) {
			e.printStackTrace();
		} // try-catch
		
		return employer;
	} // updateEmployer
	
} // EmployerUpdateCtrl
