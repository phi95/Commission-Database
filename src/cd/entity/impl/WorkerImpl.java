package cd.entity.impl;

import cd.CDException;
import cd.entity.Worker;

public class WorkerImpl extends UserImpl implements Worker {
	public WorkerImpl() {
		super();
	}
	
	public WorkerImpl(String fname, String lname, String userName, String password, String email, String phoneNumber) {
		super(fname, lname, userName, password, email, phoneNumber);
	}
}
