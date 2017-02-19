package cd.logic.impl;

import java.sql.Connection;
import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.entity.Transaction;
import cd.entity.User;
import cd.logic.LogicLayer;
import cd.object.ObjectLayer;
import cd.object.impl.ObjectLayerImpl;
import cd.persistence.PersistenceLayer;
import cd.persistence.impl.PersistenceLayerImpl;
import cd.session.Session;

public class LogicLayerImpl implements LogicLayer {
	private ObjectLayer objectLayer = null;
	
	public LogicLayerImpl( Connection conn )
    {
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistenceLayer = new PersistenceLayerImpl( conn, objectLayer );
        objectLayer.setPersistence( persistenceLayer );
    }
    
    public LogicLayerImpl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
    }

	@Override
	public String employerLogin(Session session, String username, String password) throws CDException {
		EmployerLoginCtrl ctrlVerifyEmployer = new EmployerLoginCtrl(objectLayer);
		String ssid = null;
		ssid = ctrlVerifyEmployer.login(session, username, password);	
		return ssid;
	} // employerLogin

	@Override
	public String employeeLogin(Session session, String username, String password) throws CDException {
		EmployeeLoginCtrl verifyEmployee = new EmployeeLoginCtrl(objectLayer);
		String ssid = null;
		ssid = verifyEmployee.login(session, username, password);
		return ssid;
	} // employeeLogin

	@Override
	public String addEmployer(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) throws CDException {
		String ssid = null;
		EmployerRegistrationCtrl verifyEmployer = new EmployerRegistrationCtrl(objectLayer);
		ssid = verifyEmployer.addEmployer(session, firstName, lastName, username, password, email, phoneNumber);

		return ssid;
	} // addEmployer

	@Override
	public String addEmployee(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) throws CDException {
		String ssid = null;
		EmployeeRegistrationCtrl verifyEmployee = new EmployeeRegistrationCtrl(objectLayer);
		ssid = verifyEmployee.addEmployee(session, firstName, lastName, username, password, email, phoneNumber);
		return ssid;
	} // addEmployee

	@Override
	public User updateEmployerAccount(String firstName, String lastName, String username, String password, String email,
			String phoneNumber) {
		EmployerUpdateCtrl employerUpdateCtrl = new EmployerUpdateCtrl(objectLayer);
		return employerUpdateCtrl.updateEmployer(firstName, lastName, username, password, email, phoneNumber);
	}

	@Override
	public User updateEmployeeAccount(String firstName, String lastName, String username, String password, String email,
			String phoneNumber) {
		EmployeeUpdateCtrl employeeUpdateCtrl = new EmployeeUpdateCtrl(objectLayer);
		return employeeUpdateCtrl.updateEmployee(firstName, lastName, username, password, email, phoneNumber);
	}

	@Override
	public void addTransaction(String amount, String description) throws CDException {
		TransactionCtrl transactionCtrl = new TransactionCtrl(objectLayer);
		transactionCtrl.addTransaction(amount, description);
	}

	@Override
	public List<Transaction> getTransactionsFromEmployee(Employee employee) throws CDException {
		TransactionCtrl transactionCtrl = new TransactionCtrl(objectLayer);
		return transactionCtrl.getTransactionFromEmployee(employee);
	} // getTransactionFromEmployee
}
