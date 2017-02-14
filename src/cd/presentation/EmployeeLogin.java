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
 * Servlet implementation class EmployeeLogin
 */
@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession httpSession = null;
    	String username;
    	String password;
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
            
        username = request.getParameter( "username" );
        password = request.getParameter( "password" );
            
        if( username == null || password == null ) {
        	System.out.println("Username or password null");
        	return;
        }
            
        try {          
            ssid2 = logicLayer.employeeLogin( session, username, password );
            System.out.println( "Obtained ssid: " + ssid2 );
            httpSession.setAttribute( "ssid", ssid2 );
            System.out.println( "Connection: " + session.getConnection() );
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
                
        if (ssid2!=null){
        	response.sendRedirect("employerHomepage.jsp");
        }else
    	{
    		response.sendRedirect("invalidLogin.jsp");
    	}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
