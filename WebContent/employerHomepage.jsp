<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" import="java.sql.*"%>
<%@ page import="cd.session.Session" %>
<%@ page import="cd.session.SessionManager" %>
<%@ page import="cd.logic.LogicLayer" %>
<%@ page import="java.util.List" %>
<%@ page import="cd.entity.*" %>

 <%
	String ssid = (String)session.getAttribute("ssid");
    Session hpSession = SessionManager.getSessionById(ssid);
    LogicLayer logicLayer = hpSession.getLogicLayer();
 %>
    
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Employer Homepage</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    body{
        position: relative;
    }
    .modal {
      z-index: 9999 !important;
    }
    .affix {
        top:0;
        width: 100%;
        z-index: 9999 !important;
    }
    .navbar {
        margin-bottom: 0px;
    }
    .affix ~ .container-fluid {
       position: relative;
       top: 50px;
    }
    .affix ~ .container {
       position: relative;
       top: 50px;
    }
    #Today {padding-top:50px;height:100vh;}
    #Month {padding-top:50px;height:100vh;}
    #Year {padding-top:50px;height:100vh;}
  </style>
</head>

<body data-spy="scroll" data-target=".navbar" data-offset="50">

<div class="container-fluid">
  <div class="jumbotron">
    <h1 class="text-center">Commission Database</h1>
    <p>Welcome Employer!</p>
  </div>
</div>

<nav class="navbar navbar-default" data-spy="affix" data-offset-top="270">
  <div class="container">
    <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    </div>

    <div>
      <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav">
          <li><a href="#Today">Today</a></li>
          <li><a href="#">Month</a></li>
          <li><a href="#">Year</a></li>
          <li style="float: right;"><a href="#" data-toggle="modal" data-target="#accountModal">Account</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>

  <!-- Today -->
  <div id="Today" class="container">
    <h3>Today</h3>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Employee</th>
          <th>Amount</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Dustin</td>
          <td>$100</td>
        </tr>
      </tbody>
    </table>
  </div>

  <div id="accountModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Account Information</h1>
        </div>
        
        <%
        Employer account = (Employer)hpSession.getUser();
        String passwordLength = "";
        for(int n=0; n<account.getPassword().length(); n++){
			passwordLength = passwordLength + "*";
        }        
        %>
        <script>
			function editAccount() {
   				 document.getElementById("fnamedisplay").style.display = 'none';
   				 document.getElementById("lnamedisplay").style.display = 'none';
   				 document.getElementById("passworddisplay").style.display = 'none';
   				 document.getElementById("emaildisplay").style.display = 'none';
   				 document.getElementById("phonedisplay").style.display = 'none';
   				 
   				 document.getElementById("fname").style.display = 'block';
   				 document.getElementById("lname").style.display = 'block';
   				 document.getElementById("password").style.display = 'block';
   				 document.getElementById("email").style.display = 'block';
   				 document.getElementById("phoneNumber").style.display = 'block';
   				 
  				 document.getElementById("editOption").style.display = 'none';
  				 document.getElementById("submitChange").style.display = 'block';
  				 document.getElementById("cancelChange").style.display = 'block';
			}
			
			function cancelEdit(){
  				 document.getElementById("fnamedisplay").style.display = 'block';
   				 document.getElementById("lnamedisplay").style.display = 'block';
   				 document.getElementById("passworddisplay").style.display = 'block';
   				 document.getElementById("emaildisplay").style.display = 'block';
   				 document.getElementById("phonedisplay").style.display = 'block';
   				 
   				 document.getElementById("fname").style.display = 'none';
   				 document.getElementById("lname").style.display = 'none';
   				 document.getElementById("password").style.display = 'none';
   				 document.getElementById("email").style.display = 'none';
   				 document.getElementById("phoneNumber").style.display = 'none';
  				 
  				 document.getElementById("editOption").style.display = 'block';
  				 document.getElementById("submitChange").style.display = 'none';
  				 document.getElementById("cancelChange").style.display = 'none';
			}
			function reload(){
				<% account = (Employer)hpSession.getUser(); %>
				document.getElementById("fnamedisplay").innerHTML = '<%=account.getFirstName()%>';
  				document.getElementById("lnamedisplay").innerHTML = '<%=account.getLastName()%>';
  				document.getElementById("passworddisplay").innerHTML = '<%=account.getPassword()%>';
  				document.getElementById("emaildisplay").innerHTML = '<%=account.getEmailAddress()%>';
  				document.getElementById("phonedisplay").innerHTML = '<%=account.getPhoneNumber()%>';
  				document.forms["account"].submit();
			}
		</script>
        <div class="modal-body">
          <form id="account" class="form-signin" action="UpdateAccount" method = "post">
            <label for="fname">First Name: </label>
            <p id="fnamedisplay"><%=account.getFirstName()%></p>
			<input type="text" id="fname" name="fname" class="form-control" style="display:none;" value="<%=account.getFirstName()%>">
			

            <label for="lname">Last Name: </label>
            <p id="lnamedisplay"><%=account.getLastName()%></p>
            <input type="text" id="lname" name="lname" class="form-control" style="display:none;" value="<%=account.getLastName()%>"> 
            
            <br>
            <label for="username">Username: </label>
            <p id="usernamedisplay"><%=account.getUserName()%></p>
            
            <label for="username">Password: </label>
            <p id="passworddisplay"><%=passwordLength%></p>
            <input type="text" id="password" name="password" class="form-control" style="display:none;">
 
            <br>
            <label for="email">Email: </label>
            <p id="emaildisplay"><%=account.getEmailAddress()%></p>
            <input type="text" id="email" name="email" class="form-control" style="display:none;" value="<%=account.getEmailAddress()%>">
            
            
            <label for="phoneNumber">Address: </label>
            <p id="phonedisplay"><%=account.getPhoneNumber()%></p>
            <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" style="display:none;" value="<%=account.getPhoneNumber()%>">
            
            <div class="modal-footer" id="submitChange" style="display:none;">
          		<input class="btn btn-lg btn-primary" type="button" onclick="reload()" value="Submit">
          	</div>
          </form>

          <div class="modal-footer" id="cancelChange" style="display:none;">
            <span><button class="btn btn-lg btn-primary" onclick="cancelEdit()">Cancel</button></span>
          </div>
          <div class="modal-footer" id="editOption">
              <button class="btn btn-lg btn-primary" onclick="editAccount() ">Edit</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
          </div>
          
        </div>
      </div>
    </div>
  </div>

</body>
</html>
