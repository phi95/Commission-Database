package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.object.ObjectLayer;
import cd.session.Session;
import cd.session.SessionManager;

public class EmployeeLoginCtrl {
	private ObjectLayer objectLayer = null;
	
	public EmployeeLoginCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public String login(Session session, String userName, String password)
			throws CDException
		{
			String ssid = null;
			
			Employee modelEmployee = objectLayer.createEmployee();
			modelEmployee.setUserName(userName);
			modelEmployee.setPassword(password);
			List<Employee> employees = objectLayer.findEmployee(modelEmployee);
			if(employees.size() > 0){
				Employee employee = employees.get(0);
				session.setUser(employee);
				ssid = SessionManager.storeSession(session);
			}else
				throw new CDException("SessionManager.login: Invalid UserName or Password");
			
			return ssid;
		}
}