<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Home Lavoratore</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
	<!-- Non e' loggato e ridiretto alla pagina login-->
	<%if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp"); 
    } %>
	
	<!-- Modal per i vari avvisi -->
	<div class="modal fade" id="modalAvvisi" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalAvvisiTitle">Lavoratore</h4>
				</div>
				<div id="modalAvvisiBody" class="modal-body">...</div>
			</div>
		</div>
	</div>
	
	
	<div class="panel panel-primary" style="border-style: none;">
		
		<!-- Header con benvenuto e logout -->
		<div class="panel-heading clearfix">
		    <h3 class="panel-title pull-left" style="padding-top: 7.5px;">Benvenuto <%=session.getAttribute("username") %>!</h3>
        	<button onclick="logout();" class="btn btn-default pull-right btn-sm">Logout</button>
		</div>
		
		<!-- Due bottoni -->
  		<div class="panel-body">
			<div class="btn-group btn-group-justified" role="group" aria-label="...">
			  	<div class="btn-group" role="group">
					<button id="btnShowCampagneDisp" onclick="showCampagneDisp()" class="btn btn-default">Mostra Campagne Disponibili</button>
			  	</div>
	  			<div class="btn-group" role="group">
					<button id="btnShowStatistiche" onclick="showStatistiche()" class="btn btn-default">Mostra Statistiche Campagne</button>
	  			</div>
			</div>
		<ul id="listaCampDisp" style="display:none" class="nav nav-pills nav-stacked"></ul>
		<ul id="listaStat" style="display:none" class="nav nav-pills nav-stacked"></ul>
		</div>
	
	</div>
	
	
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../js/homeLavoratoreDisp.js"></script>
<script type="text/javascript" src="../js/homeLavoratoreStat.js"></script> 

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</body>
</html>