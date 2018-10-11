<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Home Gestore</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


</head>
<body>

	<%
	// Se nessuno e' loggato lo rimanda alla pagina di login
	if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp"); 
    } 
    %>

	<div class="panel panel-primary" style="border-style: none;">
	
		<!-- Header della pagina -->
		<div class="panel-heading clearfix">
			<h3 class="panel-title pull-left" style="padding-top: 7.5px;">
				Benvenuto <%=session.getAttribute("username") %>!
			</h3>
			<button onclick="logout();" class="btn btn-default pull-right btn-sm">Logout</button>
		</div>
		
		<!-- Insieme dei bottoni da usare -->
		<div class="btn-group btn-group-justified" role="group" aria-label="...">
			<!-- Primo bottone -->
			<div class="btn-group" role="group">
				<button onclick="showFormCrea()" class="btn btn-default">Crea Campagna</button>
			</div>
			<!-- Secondo bottone -->
			<div class="btn-group" role="group">
				<button id="btnShowCampagneAperte" onclick="showCampagneAperte()" class="btn btn-default">Configura Campagne Editabili</button>
			</div>
			<!-- Terzo bottone -->
			<div class="btn-group" role="group">
				<button id="btnShowCampagneAvviate" onclick="showCampagneAvviate()" class="btn btn-default">Mostra Campagne Avviate</button>
			</div>
		</div>

		<!-- Modal per le immagini della campagna-->
		<div class="modal fade" id="modalImg" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="modalImgTitle">Immagini Campagna</h4>
					</div>
					<div id="modalImgBody" class="modal-body">...</div>
					<div id="modalImgFooter" class="modal-footer"></div>
				</div>
			</div>
		</div>

		<!-- Modal per le immagini della campagna annotate-->
		<div class="modal fade" id="modalAnnot" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="modalAnnotTitle">Annotazioni Immagine</h4>
					</div>
					<div id="modalAnnotBody" class="modal-body">...</div>
					<div id="modalAnnotFooter" class="modal-footer"></div>
				</div>
			</div>
		</div>
		
		
		
		<!-- Modal per le immagini della Campagna con vari avvisi-->
		<div class="modal fade" id="modalAvvisi" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="modalAvvisiTitle">Campagna</h4>
					</div>
					<div id="modalAvvisiBody" class="modal-body">...</div>
				</div>
			</div>
		</div>


		<div class="modal fade" id="modalList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="modalListTitle">Lista Immagini</h4>
					</div>
					<div id="modalListBody" class="modal-body">...</div>
					<div id="modalListFooter" class="modal-footer"></div>
				</div>
			</div>
		</div>

		<ul id="listaCampAp" style="border-style: none; display: none" class="nav nav-pills nav-stacked"></ul>
		<ul id="listaCampAvv" style="border-style: none; display: none" class="nav nav-pills nav-stacked"></ul>
		<ul id="listaCampCrea" style="border-style: none; display: none" class="nav nav-pills nav-stacked">
		
			<li id="liCrea_">
				<div class="container" style="margin: auto;">
					<form class="form-horizontal" action="javascript:creaCamp();">
						<div class="form-group">
							<label for="nome">Nome:</label> <input type="text"
								class="form-control" id="txt1Crea_" placeholder="nome campagna"
								name="nomeCampagna" required>
						</div>
						<div class="form-group">
							<label for="n">Numero minimo di utenti che devono
								effettuare il task1 per ogni immagine:</label> <input type="number"
								class="form-control" id="txt2Crea_" placeholder="N"
								name="nValutaz" required min="0">
						</div>
						<div class="form-group">
							<label for="n">Numero di valutazioni positive che deve
								ricevere un’immagine:</label> <input type="number" class="form-control"
								id="txt4Crea_" placeholder="K" name="kValutaz" required min="0">
						</div>
						<div class="form-group">
							<label for="n">Numero minimo di utenti che devono
								effettuare il task2 per ogni immagine:</label> <input type="number"
								class="form-control" id="txt3Crea_" placeholder="M"
								name="mValutaz" required min="0">
						</div>
						<div class="form-group">
							<label for="n">Dimensioni in pixel della linea di
								annotazione:</label> <input type="number" class="form-control"
								id="txt5Crea_" placeholder="P" name="pAnnotaz" required min="0">
						</div>
						<div class="form-group">
							<button id="btnCrea" type="submit"
								class="btn btn-primary center-block">Crea Campagna</button>
						</div>
					</form>
				</div>
			</li>
		</ul>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="../js/homeGestoreAp.js"></script>
	<script type="text/javascript" src="../js/homeGestoreAvv.js"></script>
	<script type="text/javascript" src="../js/homeGestoreCrea.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<%
	if(null == session.getAttribute("errImg")){
		
	} else if(session.getAttribute("errImg").equals(1)) {
		session.removeAttribute("errImg");
	%>
	<script>
	errImg(1);
	</script>
	<%
	}else if(session.getAttribute("errImg").equals(0)){ 
		session.removeAttribute("errImg");
	%>
	<script>
	errImg(0); 
   	</script>
	<%}  %>

</body>
</html>