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
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
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
   		String ssid2 = null;
   		Session session = null;
   		LogicLayer logicLayer = null;
   		
    	httpSession = request.getSession();
    	ssid = (String)httpSession.getAttribute("ssid");
        if( ssid != null ) {
        	System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
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

        if( firstName == null || lastName == null || username == null || password == null || email == null || phoneNumber == null) {
        	System.out.println("A parameter is null");
        	return;
        }
        
        try {
        	if(person.equals("employer")){
	            ssid2 = logicLayer.addEmployer( session, firstName, lastName, username, password, email, phoneNumber );
	            System.out.println( "Obtained ssid: " + ssid );
	            httpSession.setAttribute( "ssid", ssid );
	            System.out.println( "Connection: " + session.getConnection());
        	}else if(person.equals("employee")){
                ssid2 = logicLayer.addEmployee( session, firstName, lastName, username, password, email, phoneNumber );
                System.out.println( "Obtained ssid: " + ssid );
                httpSession.setAttribute( "ssid", ssid );
                System.out.println( "Connection: " + session.getConnection());
        	}
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
        
        if(ssid2 != null)
        	response.sendRedirect("index.jsp");
        else
        	response.sendRedirect("thankyou.jsp");
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
