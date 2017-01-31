package cd.entity.impl;

import cd.CDException;
import cd.entity.Manager;
import cd.entity.Worker;

public class WorkerImpl extends UserImpl implements Worker {
	Manager manager;
	
	public WorkerImpl() {
		super();
	}
	
	public WorkerImpl(String fname, String lname, String userName, String password, String email, String phoneNumber) {
		super(fname, lname, userName, password, email, phoneNumber);
	}

	@Override
	public Manager getManager() {
		return manager;
	}

	@Override
	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
