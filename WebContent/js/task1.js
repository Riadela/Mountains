function doTask(){
	
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);
			
			// Controlla se ha gia effettuato tutto il task
			if (resp.immTask1.length == 0){
				document.getElementById("modalTaskBody").innerHTML = 
					'<div class="alert alert-success" role="alert">Hai completato questo task, controlla le statistiche nella tua homepage!"</div>';
				$('#modalTask').modal('show');
				setTimeout(function () {
					window.location.href = "homeLavoratore.jsp"; //will redirect to your page 
			    	}, 2000);
				return;
			}
			
			// Settaggio del carousel
			document.getElementById("modalTaskBody").innerHTML = 
				'<div id="carouselImg" class="carousel slide" data-ride="carousel" data-interval="false">'
				+'<!-- Indicators -->' 
				+'<ol id="car-indic-IMG" class="carousel-indicators">'
				+'</ol>'
				+'<!-- Wrapper for slides -->'
				+'<div id="car-img-IMG" class="carousel-inner" role="listbox">'
				+'</div>'
				+'<!-- Controls -->'
				+'<a class="left carousel-control" href="#carouselImg" role="button" data-slide="prev">'
				+'<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>'
				+'<span class="sr-only">Previous</span>'
				+'</a>'
				+'<a class="right carousel-control" href="#carouselImg" role="button" data-slide="next">'
				+'<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>'
				+'<span class="sr-only">Next</span>'
				+'</a>'
				+'</div>';

			document.getElementById("car-indic-IMG").innerHTML = "";
			document.getElementById("car-img-IMG").innerHTML = "";
			
			
			// Riempimento del carousel con le immagini
			for (i = 0; i < resp.immTask1.length; i++){
				if (i == 0){
					document.getElementById("car-indic-IMG").innerHTML =  
						'<li data-target="#carouselImg" data-slide-to="0" class="active"></li>';
				} else {
					document.getElementById("car-indic-IMG").innerHTML +=  
						'<li data-target="#carouselImg" data-slide-to="'+ i +'"></li>';
				}
				if (i == 0){
					document.getElementById("car-img-IMG").innerHTML =  
						'<div class="item active">'
						+'<img src="../ImgSrc?path='+ resp.immTask1[i].path +'" alt="..." style="width:100%">'
						+'<div class="carousel-caption">'
						+'<button class="btn btn-danger" onclick="rifiuta(' + resp.immTask1[i].idImm + ', ' + resp.immTask1[i].idLav + ')">Rifiuta</button>'
						+'<button class="btn btn-success" onclick="accetta(' + resp.immTask1[i].idImm + ', ' + resp.immTask1[i].idLav + ')">Accetta</button>'
						+'</div>'
						+'</div>';
				} else {
					document.getElementById("car-img-IMG").innerHTML +=  
						'<div class="item">'
						+'<img src="../ImgSrc?path='+ resp.immTask1[i].path +'" alt="..." style="width:100%">'
						+'<div class="carousel-caption">'
						+'<button class="btn btn-danger" onclick="rifiuta(' + resp.immTask1[i].idImm + ', ' + resp.immTask1[i].idLav + ')">Rifiuta</button>'
						+'<button class="btn btn-success" onclick="accetta(' + resp.immTask1[i].idImm + ', ' + resp.immTask1[i].idLav + ')">Accetta</button>'						+'</div>'
						+'</div>';
				}
			}
			document.getElementById("modalTaskFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>';
			$('#modalTask').modal('show');
		}
	};
	xhttp.open("GET", "../GetImgT1", true);
	xhttp.send();
}

// Funzione relativa all'accettazione di un'immagine
function accetta(idImm, idLav){

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			var resp = this.responseText;
			if(resp == "0") {
				doTask();
			} else {
				document.getElementById("modalTaskBody").innerHTML = '<div class="alert alert-danger" role="alert">Ops! Qualcosa è andato storto!</div>';
				$('#modalTask').modal('show');
			}
		}
	};
	xhttp.open("POST", "../AccettaImmT1", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idImm=" + idImm + "&idLav=" + idLav);
}


//Funzione relativa al rifiuto di un'immagine
function rifiuta(idImm, idLav){

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			var resp = this.responseText;
			if(resp == "0") {
				doTask();
			} else {
				document.getElementById("modalTaskBody").innerHTML = '<div class="alert alert-danger" role="alert">Ops! Qualcosa è andato storto!</div>';
				$('#modalTask').modal('show');
			}
		}
	};
	xhttp.open("POST", "../RifiutaImmT1", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idImm=" + idImm + "&idLav=" + idLav);
}

function back(){
    window.location.href = "../jsp/homeLavoratore.jsp";
}
