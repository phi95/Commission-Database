package cd.presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cd.logic.LogicLayer;
import cd.session.Session;
import cd.session.SessionManager;

/**
 * Servlet implementation class EmployerUpdate
 */
@WebServlet("/UpdateAccount")
public class UpdateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    	
    	HttpSession httpSession = null;
    	String firstName;
    	String lastName;
    	String username;
    	String password;
    	String email;
    	String phoneNumber;
    	String person;
   		String ssid = null;
   		Session session = null;
   		LogicLayer logicLayer = null;
   		
    	httpSession = request.getSession();
    	ssid = (String)httpSession.getAttribute("ssid");
        if( ssid != null ) {
        	System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
            //System.out.println( "Connection: " + session.getConnection() );
        }
        else
            System.out.println( "ssid is null" );
          
            
        if( session == null ) {
        	try {
        		session = SessionManager.createSession();
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }
            
        logicLayer = session.getLogicLayer();
        
    	firstName = request.getParameter("fname");
    	lastName = request.getParameter("lname");
    	username = request.getParameter("username");
    	password = request.getParameter("password");
    	email = request.getParameter("email");
    	phoneNumber = request.getParameter("phoneNumber");
    	person = request.getParameter("person");
    	
    	try {
    		if(person.equals("employer")){
    			session.setUser(logicLayer.updateEmployerAccount(firstName, lastName, username, password, email, phoneNumber));
    			response.sendRedirect("employerHomepage.jsp");
    		}else if(person.equals("employee")){
    			session.setUser(logicLayer.updateEmployeeAccount(firstName, lastName, username, password, email, phoneNumber));
    			response.sendRedirect("employeeHomepage.jsp");
    		}
    	} 
    	catch ( Exception e ) {
    		e.printStackTrace();
    	}
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
