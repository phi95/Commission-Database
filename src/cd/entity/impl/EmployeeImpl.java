package cd.entity.impl;

import cd.CDException;
import cd.entity.Employer;
import cd.entity.Employee;

public class EmployeeImpl extends UserImpl implements Employee {
	Employer manager;
	
	public EmployeeImpl() {
		super();
	}
	
	public EmployeeImpl(String fname, String lname, String userName, String password, String email, String phoneNumber) {
		super(fname, lname, userName, password, email, phoneNumber);
	}

	@Override
	public Employer getEmployer() {
		return manager;
	}

	@Override
	public void setEmployer(Employer manager) {
		this.manager = manager;
	}
}
