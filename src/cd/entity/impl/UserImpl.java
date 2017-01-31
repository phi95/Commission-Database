package cd.entity.impl;

import cd.CDException;
import cd.entity.User;
import cd.persistence.impl.Persistent;

public class UserImpl extends Persistent implements User {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	
	public UserImpl() {
		firstName = null;
		lastName = null;
		userName = null;
		password = null;
		email = null;		
		
	} // UserImpl
	
	public UserImpl(String firstName, String lastName, String userName, String password, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;

	} // UserImpl
	
	@Override
	public String getFirstName() {
		return firstName;
	} // getFirstName

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	} // setFirstName

	@Override
	public String getLastName() {
		return lastName;
	} // getLastName

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	} // setLastName

	@Override
	public String getUserName() {
		return userName;
	} // getUserName

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	} // setUserName

	@Override
	public String getPassword() {
		return password;
	} // password

	@Override
	public void setPassword(String password) {
		this.password = password;
	} // setPassword

	@Override
	public String getEmailAddress() {
		return email;
	} // getEmailAddress

	@Override
	public void setEmailAddress(String emailAddress) {
		email = emailAddress;
	} // setEmailAddress

}
