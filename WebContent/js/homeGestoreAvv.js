/**
 * 
 */

function showCampagneAvviate(){

	if(document.getElementById("listaCampAvv").style.display == "block"){
		document.getElementById("listaCampAvv").style.display = "none";
		return;
	} else {
		document.getElementById("listaCampCrea").style.display = "none";
		document.getElementById("listaCampAp").style.display = "none";
		document.getElementById("listaCampAvv").style.display = "block";
	}

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			// Ricevo il JSON, lo parso e inizializzo a vuoto la pagina
			var msg = JSON.parse(this.responseText);
			document.getElementById("listaCampAvv").innerHTML = "";
			var resp = this.responseText;
			
			// per ogni campagna avviata gestisco il suo html
			for (i = 0; i < msg.campagneAvviate.length; i++) {
				var li = document.createElement("LI");
				li.id = "liAvv_" + i;

				var idCamp = document.createElement("input");
				idCamp.id = "idCampAvv_" + i;
				idCamp.type = "hidden";
				idCamp.value = msg.campagneAvviate[i].idcamp;

				var valK = document.createElement("input");
				valK.id = "valK_" + i;
				valK.type = "hidden";
				valK.value = msg.campagneAvviate[i].k;

				var a = document.createElement("A");
				a.id="aAvv_" + i;
				a.onclick=function() { 
					var name = this.id;
					var nr = name.substring(5);
					setActive(nr); 
				};

				var ul = document.createElement("UL");
				ul.id = "ulAvv_" + i;
				var li1 = document.createElement("LI");
				li1.id = "liAvv1_" + i;
				li1.style.display = "none";
				var li2 = document.createElement("LI");
				li2.id = "liAvv2_" + i;
				li2.style.display = "none";
				var li3 = document.createElement("LI");
				li3.id = "liAvv3_" + i;
				li3.style.display = "none";
				var li4 = document.createElement("LI");
				li4.id = "liAvv4_" + i;
				li4.style.display = "none";
				var li5 = document.createElement("LI");
				li5.id = "liAvv5_" + i;
				li5.style.display = "none";

				var nome = document.createTextNode("Nome: " + msg.campagneAvviate[i].nome);
				var label1 = document.createElement("LABEL");
				label1.appendChild(nome);

				var n = document.createTextNode("Numero minimo di utenti che devono effettuare il task1 per ogni immagine: " + msg.campagneAvviate[i].n);
				var label2 = document.createElement("LABEL");
				label2.appendChild(n);

				var m = document.createTextNode("Numero minimo di utenti che devono effettuare il task2 per ogni immagine: " + msg.campagneAvviate[i].m);
				var label3 = document.createElement("LABEL");
				label3.appendChild(m);

				var k = document.createTextNode("Numero di valutazioni positive che deve ricevere un’immagine: " + msg.campagneAvviate[i].k);
				var label4 = document.createElement("LABEL");
				label4.appendChild(k);

				var p = document.createTextNode("Dimensioni in pixel della linea di annotazione: " + msg.campagneAvviate[i].p);
				var label5 = document.createElement("LABEL");
				label5.appendChild(p);

				li1.appendChild(label1);
				li1.innerHTML += "<br>";

				li2.appendChild(label2);
				li2.innerHTML += "<br>";

				li3.appendChild(label3);
				li3.innerHTML += "<br>";

				li4.appendChild(label4);
				li4.innerHTML += "<br>";

				li5.appendChild(label5);
				li5.innerHTML += "<br>";
				
				var nome2 = document.createTextNode(msg.campagneAvviate[i].nome);
				a.appendChild(nome2);
				li.appendChild(a);
				
				// Primo bottone visualizza voti
				var btn1 = document.createElement("button");
				btn1.id = "btnImmSelez_" + i;
				btn1.style.display = "none";
				btn1.classList.add('btn');
				btn1.classList.add('btn-primary');
				btn1.classList.add('center-block');
				var txt1 = document.createTextNode('Visualizza voti immagini campagna "' + msg.campagneAvviate[i].nome +'"');
				btn1.appendChild(txt1);
				btn1.onclick=function() { 
					var name = this.id;
					var nr = name.substring(12);
					immSelez(nr); // Chiamata funzione per visualizzare i voti delle imm
				};
				var formGr1 = document.createElement("DIV");
				formGr1.id = "formGr1_" + i;
				formGr1.classList.add('form-group');
				formGr1.appendChild(btn1);
				
				var btn2 = document.createElement("button");
				btn2.id = "btnImmAnnot_" + i;
				btn2.style.display = "none";
				btn2.classList.add('btn');
				btn2.classList.add('btn-primary');
				btn2.classList.add('center-block');
				var txt2 = document.createTextNode('Visualizza le annotazioni della campagna "' + msg.campagneAvviate[i].nome +'"');
				btn2.appendChild(txt2);
				btn2.onclick=function() { 
					var name = this.id;
					var nr = name.substring(12);
					listAnnot(nr); 		// Chiamata funzione per visualizzare lista di annotazioni
				};
				var formGr2 = document.createElement("DIV");
				formGr2.id = "formGr2_" + i;
				formGr2.classList.add('form-group');
				formGr2.appendChild(btn2);
				
				var btn3 = document.createElement("button");
				btn3.id = "btnVisStat_" + i;
				btn3.style.display = "none";
				btn3.classList.add('btn');
				btn3.classList.add('btn-primary');
				btn3.classList.add('center-block');
				var txt3 = document.createTextNode('Visualizza statistiche campagna "' + msg.campagneAvviate[i].nome +'"');
				btn3.appendChild(txt3);
				btn3.onclick=function() { 
					var name = this.id;
					var nr = name.substring(11);
					showStatCamp(nr); 		// Visualizza statistiche della campagna
				};
				var formGr3 = document.createElement("DIV");
				formGr3.id = "formGr3_" + i;
				formGr3.classList.add('form-group');
				formGr3.appendChild(btn3);
				
				var btn3 = document.createElement("button");
				btn3.id = "btnElimina_"+ i;
				btn3.classList.add('btn');
				btn3.classList.add('btn-danger');
				btn3.classList.add('center-block');
				var txt4 = document.createTextNode('Elimina campagna');
				btn3.appendChild(txt4);
				btn3.onclick=function() { 
					var name = this.id;
					var nr = name.substring(11);
					delCampagna(nr); };		// Chiamata unzione per eliminare la campagna
				var formGr4 = document.createElement("DIV");
				formGr4.id = "formGr4_" + i;
				formGr4.classList.add('form-group');
				formGr4.style.display = "none";
				formGr4.appendChild(btn3);
				
				var container = document.createElement("DIV");
				container.id = "container_" + i;
				container.classList.add('container');
				container.style.display = "none";
				container.style.margin = "auto";

				var formHor = document.createElement("FORM");
				formHor.classList.add('form-horizontal');
				formHor.action = "javascript:void(0)";
				
				formHor.appendChild(formGr1);
				formHor.appendChild(formGr2);
				formHor.appendChild(formGr3);
				// Se la campagna e' finita allora visualizza il bottone elimina
				if (msg.campagneAvviate[i].finita == '1'){
					formHor.appendChild(formGr4);
				}
				container.appendChild(formHor);

				ul.appendChild(li1);
				ul.appendChild(li2);
				ul.appendChild(li4);
				ul.appendChild(li3);
				ul.appendChild(li5);
				ul.appendChild(container);

				li.appendChild(ul);
				li.appendChild(idCamp);
				li.appendChild(valK);

				document.getElementById("listaCampAvv").appendChild(li);

			}
			if (msg.campagneAvviate.length){
				document.getElementById("liAvv_" + 0).role = "presentation";
			} else {
				document.getElementById("listaCampAvv").innerHTML = '<div class="alert alert-warning" role="alert">Nessuna campagna avviata! Avvia una campagna!</div>';
			}
			
			// Tutte le campagne con role="presentation"
			for(var i = 1; i < msg.campagneAvviate.length; i++){
				document.getElementById("liAvv_" + i).role = "presentation";
			}

			document.getElementById("listaCampAvv").style.display = "block";

		}
	};
	xhttp.open("GET", "../GetCampagneAvviate", true);
	xhttp.send();
}

// Funzione per quando si clicca su un'altra campagna
function setActive(l){

	var ul = document.getElementById("listaCampAvv");
	var items1 = ul.getElementsByTagName("ul");
	
	// Disattivo quello che in questo momento e' visibile
	for (var i = 0; i < items1.length; i++) {
		var li = document.getElementById("liAvv_" + i);
		if (li.classList.contains('active') ){
			li.classList.remove('active');
		}
	}

	for (var i = 0; i < items1.length; i++){
		document.getElementById("liAvv1_" + i).style.display = "none";	
		document.getElementById("liAvv2_" + i).style.display = "none";	
		document.getElementById("liAvv3_" + i).style.display = "none";	
		document.getElementById("liAvv4_" + i).style.display = "none";	
		document.getElementById("liAvv5_" + i).style.display = "none";	
		document.getElementById("btnImmSelez_" + i).style.display = "none";	
		document.getElementById("btnImmAnnot_" + i).style.display = "none";	
		document.getElementById("btnVisStat_" + i).style.display = "none";
		document.getElementById("container_" + i).style.display = "none";
		if(document.getElementById("formGr4_" + i) !== null){
			document.getElementById("formGr4_" + i).style.display = "none";
		};
		
	}
	
	// Mostro la campagna che e' stata cliccata
	document.getElementById("liAvv_" + l).classList.add("active");	
	document.getElementById("liAvv1_" + l).style.display = "block";	
	document.getElementById("liAvv2_" + l).style.display = "block";	
	document.getElementById("liAvv3_" + l).style.display = "block";	
	document.getElementById("liAvv4_" + l).style.display = "block";	
	document.getElementById("liAvv5_" + l).style.display = "block";	
	document.getElementById("btnImmSelez_" + l).style.display = "block";
	document.getElementById("btnImmAnnot_" + l).style.display = "block";
	document.getElementById("btnVisStat_" + l).style.display = "block";
	document.getElementById("container_" + l).style.display = "block";
	if(document.getElementById("formGr4_" + l) !== null){
		document.getElementById("formGr4_" + l).style.display = "block";
	};

}

// Funzione per eliminare la campagna
function delCampagna(k){
	var nome = document.getElementById("aAvv_" + k).innerHTML;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = this.responseText;
			
			document.getElementById("modalAvvisiTitle").innerHTML = 
				'Avviso Campagna';

			if (resp == "-1"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Ops! Qualcosa è andato storto!<br>'
					+ 'Riprova più tardi!</div>';
			} else if (resp == "0"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Campagna eliminata con successo!<br>'
					+ '</div>';
				showCampagneAvviate();
			}
			$('#modalAvvisi').modal('show');

		}
	};
	xhttp.open("POST", "../EliminaCampagna", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nome=" + nome +"&chiusa=1");
}

/// Funzione per visualizzare i voti delle immagini
function immSelez(m){

	var idcamp = document.getElementById("idCampAvv_" + m).value;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);
			
			document.getElementById("modalImgTitle").innerHTML = 'Immagini Campagna';

			if (resp.immAvv.length == 0){
				document.getElementById("modalImgBody").innerHTML = "Nessuna immagine presente!";
				$('#modalImg').modal('show');
				return;
			}
			
			// Settaggio carousel
			document.getElementById("modalImgBody").innerHTML = 
				'<div id="carouselImg" class="carousel slide" data-ride="carousel">'
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
			
			// Riempio il carousel con le immagini
			for (i = 0; i < resp.immAvv.length; i++){
				// Setto la prima immagina ad active
				if (i == 0){
					document.getElementById("car-indic-IMG").innerHTML =  
						'<li data-target="#carouselImg" data-slide-to="0" class="active"></li>';
				} else {
					document.getElementById("car-indic-IMG").innerHTML +=  
						'<li data-target="#carouselImg" data-slide-to="'+ i +'"></li>';
				}
				
				// Setto la prima immagina ad active
				if (i == 0){
					document.getElementById("car-img-IMG").innerHTML =  
						'<div class="item active">'
						+'<img src="../ImgSrc?path='+ resp.immAvv[i].path +'" alt="..." style="width:100%">'
						+'<div class="carousel-caption">'
						+'Voti positivi: '+resp.immAvv[i].pos
						+'<br>'
						+'Voti negativi: '+resp.immAvv[i].neg
						+'</div>'
						+'</div>';
				} else {
					document.getElementById("car-img-IMG").innerHTML +=  
						'<div class="item">'
						+'<img src="../ImgSrc?path='+ resp.immAvv[i].path +'" alt="..." style="width:100%">'
						+'<div class="carousel-caption">'
						+'Voti positivi: '+resp.immAvv[i].pos
						+'<br>'
						+'Voti negativi: '+resp.immAvv[i].neg
						+'</div>'
						+'</div>';
				}
			}
			document.getElementById("modalImgFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>';
			$('#modalImg').modal('show');
		}
	};
	xhttp.open("POST", "../GetImg", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idcamp=" + idcamp + "&t=2");
}

// Visualizzo la lista delle foto annotate
function listAnnot(m){

	var idcamp = document.getElementById("idCampAvv_" + m).value;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);
			
			document.getElementById("modalListTitle").innerHTML = 'Lista Immagini';

			document.getElementById("modalListBody").innerHTML = '<ul>';
			
			if (resp.immAvv.length == 0){
				document.getElementById("modalListBody").innerHTML = 
					"Nessuna annotazione per questa campagna!";
				$('#modalList').modal('show');
				return;
			}
			
			// Mostra la lista delle foto 
			for (i = 0; i < resp.immAvv.length; i++){
				document.getElementById("modalListBody").innerHTML += 
					'<li>'
					+	'<a href="#" onclick="getAnnotImm(' + resp.immAvv[i].idImm + ')">' + resp.immAvv[i].path + '</a>'	
					+'</li>';
			}

			document.getElementById("modalListBody").innerHTML += '</ul>';
			document.getElementById("modalListFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>';
			$('#modalList').modal('show');
		}
	};
	xhttp.open("POST", "../GetImg", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idcamp=" + idcamp + "&t=2");
}

// Chiamata per le immagini annotate relative all'immagine
function getAnnotImm(idImm){

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);

			$("#modalAnnot").on('hide.bs.modal', function () {
				$('#modalList').modal('show');
			});
			
			document.getElementById("modalAnnotTitle").innerHTML = 'Annotazioni Immagine';

			if (resp.annotImm.length == 0){
				document.getElementById("modalAnnotBody").innerHTML = 
					"Nessuna annotazione per quest'immagine!";
				$('#modalList').modal('hide');
				$('#modalAnnot').modal('show');
				return;
			}
			
			// Settaggio carousel
			document.getElementById("modalAnnotBody").innerHTML = 
				'<div id="carouselAnnot" class="carousel slide" data-ride="carousel">'
				+'<!-- Indicators -->'
				+'<ol id="car-indic-Annot" class="carousel-indicators">'
				+'</ol>'
				+'<!-- Wrapper for slides -->'
				+'<div id="car-img-Annot" class="carousel-inner" role="listbox">'
				+'</div>'
				+'<!-- Controls -->'
				+'<a class="left carousel-control" href="#carouselAnnot" role="button" data-slide="prev">'
				+'<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>'
				+'<span class="sr-only">Previous</span>'
				+'</a>'
				+'<a class="right carousel-control" href="#carouselAnnot" role="button" data-slide="next">'
				+'<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>'
				+'<span class="sr-only">Next</span>'
				+'</a>'
				+'</div>';

			document.getElementById("car-indic-Annot").innerHTML = "";
			document.getElementById("car-img-Annot").innerHTML = "";
			
			// Riempimento carousel
			for (i = 0; i < resp.annotImm.length; i++){
				if (i == 0){
					document.getElementById("car-indic-Annot").innerHTML =  
						'<li data-target="#carouselAnnot" data-slide-to="0" class="active"></li>';
				} else {
					document.getElementById("car-indic-Annot").innerHTML +=  
						'<li data-target="#carouselAnnot" data-slide-to="'+ i +'"></li>';
				}
				if (i == 0){
					document.getElementById("car-img-Annot").innerHTML =  
						'<div class="item active">'
						+'<img src="../ImgSrc?path='+ resp.annotImm[i].path +'&annot=1" alt="..." style="width:100%">'
						+'<div class="carousel-caption">'
						+'</div>'
						+'</div>';
				} else {
					document.getElementById("car-img-Annot").innerHTML +=  
						'<div class="item">'
						+'<img src="../ImgSrc?path='+ resp.annotImm[i].path +'&annot=1" alt="..." style="width:100%">'
						+'<div class="carousel-caption">'
						+'</div>'
						+'</div>';
				}
			}
			// Aggiunta bottone chiusura
			document.getElementById("modalAnnotFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>';

			$('#modalList').modal('hide');
			$('#modalAnnot').modal('show');

		}
	};
	xhttp.open("POST", "../GetAnnot", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idimm=" + idImm);
}

// Funzione che mostra statistiche della campagna
function showStatCamp(f){
	var idcamp = document.getElementById("idCampAvv_" + f).value;
	var k = document.getElementById("valK_" + f).value;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);

			var app = 0;	// Immagini approvate
			var daApp = 0; 	// Immagini da approvare
			var nonApp = 0;	// Immagini non approvate
			var annot = 0;  // Immagini annotate e maggiori di M
			var nAnnot = 0;  // Immagini ancora da annotare
			var totAnn = 0;  // Immagini in totale annotate

			document.getElementById("modalListTitle").innerHTML = 'Statistiche Campagna';
			
			if (resp.immAvv.length == 0){
				document.getElementById("modalListBody").innerHTML = 
					"Non sono disponibili statistiche per questa campagna!";
				$('#modalList').modal('show');
				return;
			}
			
			// Calcolo statistiche sulla base dei voti dei lavoratori
			for (i = 0; i < resp.immAvv.length; i++){
				if (resp.immAvv[i].pos >= k){
					app++;
				} else if ((resp.immAvv[i].neg) > (resp.immAvv[i].pos)){
					nonApp++;
				} else {
					daApp++;
				}
				
				if (resp.immAvv[i].annot > 0){
					totAnn += resp.immAvv[i].annot; // Numero annotazioni per immagine
					if(resp.immAvv[i].annot > resp.immAvv[i].m){
						annot++; // Se annotato e maggiore del numero minimo di persone che devono fare il task
					}
				} else if(resp.immAvv[i].annot=='0' && resp.immAvv[i].pos >= k){
					nAnnot++;
				}
			}
			
			// Output dei valori
			document.getElementById("modalListBody").innerHTML = 
					'<label>Immagini approvate: ' + app + '</label><br>'
				+	'<label>Immagini non approvate: ' + nonApp + '</label><br>'
				+	'<label>Immagini da approvare: ' + daApp + '</label><br>'
				+	'<label>Immagini annotate: ' + annot + '</label><br>'
				+	'<label>Immagini da annotare: ' + nAnnot + '</label><br>'
				+	'<label>Numero medio annotazioni: ' + totAnn/resp.immAvv.length + '</label><br>';
			
			// Output delle varie 
			for (i = 0; i < resp.immAvv.length; i++){
				document.getElementById("modalListBody").innerHTML += 

					'<div class="media">'
					+	'<div class="media-left media-middle">'
					+		'<a href="#">'
					+	    	'<img class="media-object" src="../ImgSrc?path='+ resp.immAvv[i].path +'" alt="..." style="width="128" height="128"">'
					+	    '</a>'
					+	'</div>'
					+	'<div class="media-body">'
					+		'<label id="pos_' + i + '">voti pos: '+ resp.immAvv[i].pos + '</label><br>'
					+		'<label id="neg_' + i + '">voti neg: '+ resp.immAvv[i].neg + '</label><br>'
					+		'<label id="ann_' + i + '">Annotazioni: '+ resp.immAvv[i].annot + '</label><br>'
					+	  	'</div>'
					+	'</div>';					
			}
			document.getElementById("modalListFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>';

			// Settaggio del css delle varie statistiche
			for (i = 0; i < resp.immAvv.length; i++){
				// E' stata accettato piu dell'indice k
				if (resp.immAvv[i].pos >= k){ 
					document.getElementById("pos_" + i).innerHTML += " ✓";
					document.getElementById("pos_" + i).style.color = "green";
					// Se le negative sono maggiori delle positive
				} else if (resp.immAvv[i].neg > resp.immAvv[i].pos){
					document.getElementById("neg_" + i).innerHTML += " ✘";
					document.getElementById("neg_" + i).style.color = "red";
				}
			}

			$('#modalList').modal('show');

		}
	};
	xhttp.open("POST", "../GetImg", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idcamp=" + idcamp + "&t=2");

}







