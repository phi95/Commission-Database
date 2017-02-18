package cd.logic.impl;

import java.sql.Connection;

import cd.CDException;
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
	public String employerLogin(Session session, String username, String password) {
		EmployerLoginCtrl ctrlVerifyEmployer = new EmployerLoginCtrl(objectLayer);
		String ssid = null;
		try {
			ssid = ctrlVerifyEmployer.login(session, username, password);
		} catch (CDException e) {
			e.printStackTrace();
		} // try-catch
		
		return ssid;
	} // employerLogin

	@Override
	public String employeeLogin(Session session, String username, String password) {
		EmployeeLoginCtrl verifyEmployee = new EmployeeLoginCtrl(objectLayer);
		String ssid = null;
		try {
			ssid = verifyEmployee.login(session, username, password);
		} catch (CDException e) {
			e.printStackTrace();
		} // try-catch
		return ssid;
	} // employeeLogin

	@Override
	public String addEmployer(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) {
		String ssd = null;
		EmployerRegistrationCtrl verifyEmployer = new EmployerRegistrationCtrl(objectLayer);
		try {
			ssd = verifyEmployer.addEmployer(session, firstName, lastName, username, password, email, phoneNumber);
		} catch (CDException e) {
			e.printStackTrace();
			System.out.println("LogicLayerImpl.add: Failed to verify employer.");
		} // try-catch
		return ssd;
	} // addEmployer

	@Override
	public String addEmployee(Session session, String firstName, String lastName, String username, String password,
			String email, String phoneNumber) {
		String ssd = null;
		EmployeeRegistrationCtrl verifyEmployee = new EmployeeRegistrationCtrl(objectLayer);
		try {
			ssd = verifyEmployee.addEmployee(session, firstName, lastName, username, password, email, phoneNumber);
		} catch (CDException e) {
			e.printStackTrace();
			System.out.println("LogicLayerImpl.add: Failed to verify employee.");
		} // try-catch
		return ssd;
	} // addEmployee
}
