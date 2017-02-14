package cd.entity.impl;

import cd.CDException;
import cd.entity.Manager;

public class ManagerImpl extends UserImpl implements Manager {
	public ManagerImpl() {
		super();
	}
	
	public ManagerImpl(String fname, String lname, String userName, String password, String email, String phoneNumber) {
		super(fname, lname, userName, password, email, phoneNumber);
	}
}