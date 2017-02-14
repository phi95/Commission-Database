package cd.logic;

import cd.CDException;
import cd.session.Session;

public interface LogicLayer {

	String employerLogin(Session session, String username, String password);

}
