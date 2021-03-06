package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employer;
import cd.object.ObjectLayer;
import cd.session.Session;
import cd.session.SessionManager;

public class EmployerLoginCtrl {
	private ObjectLayer objectLayer = null;
	
	public EmployerLoginCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public String login(Session session, String userName, String password)
			throws CDException
		{
			String ssid = null;
			
			Employer modelEmployer = objectLayer.createEmployer();
			modelEmployer.setUserName(userName);
			modelEmployer.setPassword(password);
			List<Employer> employers = objectLayer.findEmployer(modelEmployer);
			if(employers.size() > 0){
				Employer employer = employers.get(0);
				session.setUser(employer);
				ssid = SessionManager.storeSession(session);
			}else
				throw new CDException("SessionManager.login: Invalid UserName or Password");
			
			return ssid;
		}
}
