package cd.entity.impl;

import cd.entity.Customer;

public class CustomerImpl extends UserImpl implements Customer {
	private String description;
	
	public CustomerImpl() {
		super();
	}
	
	public CustomerImpl(String fname, String lname, String userName, String password, String email, String phoneNumber) {
		super(fname, lname, userName, password, email, phoneNumber);
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

}
