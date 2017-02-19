<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" import="java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Commission Database</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">
  <div class="jumbotron">
    <h1 class="text-center">Commission Database</h1>
    <p>Welcome to Commission Database, please sign in or register below.</p>
  </div>
</div>

<div class="container">
  <!-- Employer and Employee Button -> modal -->
  <div class="btn-group btn-group-justified" role="group">
    <div class="btn-group" role=group>
      <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#employerModal">Employer</button>
    </div>
    <div class="btn-group" role=group>
      <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#employeeModal">Employee</button>
    </div>
  </div>

  <!-- employer modal -->
  <div id="employerModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Employer</h1>
        </div>
        <div class="modal-body">
          <ul class="nav nav-tabs" id="tabContent">
              <li class="active"><a href="#employerSignIn" data-toggle="tab">Sign In</a></li>
              <li><a href="#registerEmployer" data-toggle="tab">Register</a></li>
          </ul>

          <div class="tab-content">
              <div class="tab-pane active" id="employerSignIn">
                <form class="form-signin" method="post" action="EmployerLogin">
                  <label for="username" class="sr-only">UserName</label>
                  <input type="text" name="username" class="form-control" placeholder="UserName" required autofocus>
                  <label for="password" class="sr-only">Password</label>
                  <input type="password" name="password" class="form-control" placeholder="Password" required>
                  <div class="modal-footer">
                  	<button class="btn btn-lg btn-primary" type="submit">Sign in</button>
                  	<button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
                  </div>
                </form>
              </div>

              <div class="tab-pane" id="registerEmployer">
                <form class="form-signin" method = "post" action = "Register">
                  <fieldset>
                    <label for="fname" class="sr-only">First Name</label>
                    <input type="text" name="fname" class="form-control" placeholder="First Name" required autofocus>
                    <label for="lname" class="sr-only">Last Name</label>
                    <input type="text" name="lname" class="form-control" placeholder="Last Name" required>
                    <label for="username" class="sr-only">UserName</label>
                    <input type="text" name="username" class="form-control" placeholder="UserName" required>
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" name="password" class="form-control" placeholder="Password" required>
                    <label for="passwordRetype" class="sr-only">Retype Password</label>
                    <input type="password" name="passwordRetype" class="form-control" placeholder="Retype Password" required>
                    <label for="email" class="sr-only">Email</label>
                    <input type="email" name="email" class="form-control" placeholder="Email" required>
                    <label for="phoneNumber" class="sr-only">Phone Number</label>
                    <input type="number" name="phoneNumber" class="form-control" placeholder="Phone Number" required>
                    <input type = "hidden" name = "person" value = "employer">
                  </fieldset>
				  <div class="modal-footer">
                  	<button class="btn btn-lg btn-primary" type="submit">Register</button>
                  	<button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
                  </div>
                </form>
              </div>
          </div>

        </div>
      </div>

    </div>
  </div>

  <!-- employee modal -->
  <div id="employeeModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Employee</h1>
        </div>
        <div class="modal-body">

          <form class="form-signin" method="post" action = "EmployeeLogin">
            <label for="username" class="sr-only">UserName</label>
            <input type="text" name="username" class="form-control" placeholder="UserName" required autofocus>
            <label for="password" class="sr-only">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Password" required>
            <div class="modal-footer">
            	<button class="btn btn-lg btn-primary" type="submit">Sign in</button>
            	<button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>                
          </form>

        </div>
      </div>

    </div>
  </div>

</div>

</body>
</html>
