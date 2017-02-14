package cd.entity.impl;

import cd.CDException;
import cd.entity.Employer;

public class EmployerImpl extends UserImpl implements Employer {
	public EmployerImpl() {
		super();
	}
	
	public EmployerImpl(String fname, String lname, String userName, String password, String email, String phoneNumber) {
		super(fname, lname, userName, password, email, phoneNumber);
	}
}