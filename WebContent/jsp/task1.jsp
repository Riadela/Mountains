<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task 1</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
	
	<!-- No logged-in user found, so redirect to login page. -->
	<%if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp"); 
    } %>
    
	<div class="panel panel-primary" style="border-style: none; text-align: center;">
	
		<!-- Header con benvenuto utente e tasto logout -->
		<div class="panel-heading clearfix">
		    <h3 class="panel-title pull-left" style="padding-top: 7.5px;">Utente <%=session.getAttribute("username") %></h3>
        	<button onclick="back();" class="btn btn-default pull-right btn-sm">Esci</button>
		</div>
		
		<!-- Presentazione del task -->
  		<div class="panel-body">
  			<h1>Benvenuto nel task Selezione Immagini!</h1>
  			<p class="lead">
  				In questo task ti verranno proposte delle immagini.<br>
  				Premi accetta se l'immagine rappresenta una montagna,<br>
  				altrimenti premi rifiuta!
  			</p>
  			<button onclick="doTask()" class="btn btn-primary center-block">Svolgi Task!</button>
		</div>
	</div>
	
	<!-- Avvisi generali -->
	<div class="modal fade" id="modalTask" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalTaskTitle">Selezione Immagini</h4>
				</div>
				<div id="modalTaskBody" class="modal-body">...</div>
				<div id="modalTaskFooter" class="modal-footer"></div>
			</div>
		</div>
	</div>





	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="../js/task1.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>