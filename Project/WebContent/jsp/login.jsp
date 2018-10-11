<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<div class="panel panel-primary" style="width: 100%; margin: 0 auto; border-style: none;">
	
		<!-- Modal per login effettuato Gestore/Lavoratore o credenziali errate -->
		
		<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Login</h4>
					</div>
					<div id="modalBody" class="modal-body">...</div>
				</div>
			</div>
		</div>

		<!-- Form login -->
		
		<div class="container" style="margin: auto;">
 			<form class="form-horizontal" id="formLogin" method=POST action="javascript:login()" style="display: block;">
    			<div class="form-group">
      				<label for="nome">Username:</label>
        			<input id = "user" type='text' name='username' placeholder='Username' class="form-control" autofocus required>
    			</div>
    			<div class="form-group">	
      				<label for="pw">Password:</label>
        			<input id = "pw" type='password' name='password' placeholder='Password' class="form-control" required>
   				</div>
   				<div class="form-check">
					<label class="form-check-label">
		        	    <input class="form-check-input" id = "gest" name='gestore' type="checkbox" value="true"> Gestore Campagne
					</label>
        		</div>
        		<div class="form-group">        
        			<div class="btn-group btn-group-justified" role="group" aria-label="...">
						<div class="btn-group" role="group">
							<input type='submit' value="Accedi" class="btn btn-primary center-block">
						</div>
						<div class="btn-group" role="group">
							<input type="button" onclick="registrati();" value="Registrati" class="btn btn-primary">
						</div>
					</div>
				</div>
  			</form>
		</div>
		
		<!--  Form registrazione -->
		
		<div class="container" style="margin: auto;">
 			<form class="form-horizontal" id="formRegistrati" method=POST action="javascript:registrati()" style="display: none;">
    			<div class="form-group">
      				<label for="nome">Username:</label>
        			<input id = "username" type='text' name='username' placeholder='Username' class="form-control" autofocus required>
    			</div>
    			<div class="form-group">	
      				<label for="ripPw">Password:</label>
        			<input id = "password" type='password' name='password' placeholder='Password' class="form-control" required>
   				</div>
    			<div class="form-group">	
      				<label for="pw">Ripeti Password:</label>
        			<input id = "passwordCheck" type='password' placeholder='Ripeti password' class="form-control" required>
   				</div>
   				<div class="form-check">
					<label class="form-check-label">
		        	    <input class="form-check-input" id ="gestR" name='gestore' type="checkbox" value="true"> Gestore Campagne
					</label>
        		</div>
        		<div class="form-group">        
        			<div class="btn-group btn-group-justified" role="group" aria-label="...">
						<div class="btn-group" role="group">
							<input type='button' onclick="login();" value="Accedi" class="btn btn-primary center-block">
						</div>
						<div class="btn-group" role="group">
							<input type="submit" value="Registrati" class="btn btn-primary">
						</div>
					</div>
				</div>
  			</form>
		</div>
	</div>
	
	<script>
		// Controllo password uguali
		
		var password = document.getElementById("password");
		var passwordCheck = document.getElementById("passwordCheck");
		
		function validatePassword() {
			if (password.value != passwordCheck.value) {
				passwordCheck.setCustomValidity("Passwords Differenti");
			} else {
				passwordCheck.setCustomValidity('');
			}
		}
		password.onchange = validatePassword;
		passwordCheck.onkeyup = validatePassword;
	</script>

	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="../js/login.js"></script>
	<script type="text/javascript" src="../js/registrati.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>