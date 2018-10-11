<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task 2</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
	
	<!-- No logged-in user found, so redirect to login page. -->
	<%
	if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp"); } 
        %>
     
    <div class="panel panel-primary" style="border-style: none; text-align: center;">
    
    	<!-- Header -->
		<div class="panel-heading clearfix">
		    <h3 class="panel-title pull-left" style="padding-top: 7.5px;">Utente <%=session.getAttribute("username") %></h3>
        	<button onclick="back();" class="btn btn-default pull-right btn-sm">Esci</button>
		</div>
		
		<!-- Descrizione task2 -->
  		<div class="panel-body" id="bodyT2">
  			<h1>Benvenuto nel task Annotazione Immagini!</h1>
  			<p class="lead">
  				In questo task ti verranno proposte delle immagini.<br>
  				Traccia una linea che delimita la skyline della montagna,<br>
  				premi elimina per ricominciare!
  			</p>
  			<button onclick="doTask()" class="btn btn-primary center-block">Svolgi Task!</button>
		</div>
	</div>
	
	<!-- Vari avvisi -->
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
    <input type="hidden" id="pixel" value="<%=session.getAttribute("p")%>"/>
    <input id="idcamp" name="idCamp" type="hidden" value="<%=session.getAttribute("idCamp") %>">
    <input id="username" name="username" type="hidden" value="<%=session.getAttribute("username") %>">
    <input id="imgId" name="imgId" type="hidden">
    <input id="annot" name="annot" type="hidden" value="0">
    
    
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="../js/task2.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
</body>
</html>