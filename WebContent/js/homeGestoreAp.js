/**
 * 
 */
function showCampagneAperte() {

	if(document.getElementById("listaCampAp").style.display == "block"){
		document.getElementById("listaCampAp").style.display = "none";
		return;
	} else {
		document.getElementById("listaCampCrea").style.display = "none";
		document.getElementById("listaCampAvv").style.display = "none";
		document.getElementById("listaCampAp").style.display = "block";
	}

	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			// Ricevo il JSON, lo parso e inizializzo a vuoto la pagina
			var msg = JSON.parse(this.responseText);
			var ul1 = document.createElement("UL");
			document.getElementById("listaCampAp").innerHTML = "";
			
			for (i = 0; i < msg.campagneAperte.length; i++) {
				
				var container = document.createElement("DIV");
				container.classList.add('container');
				container.style.margin = "auto";

				var formHor = document.createElement("FORM");
				formHor.classList.add('form-horizontal');
				formHor.action = "javascript:configCampagne(" + i + ")";
				
				var container2 = document.createElement("DIV");
				container2.classList.add('container');
				container2.style.margin = "auto";

				var formHor2 = document.createElement("FORM");
				formHor2.classList.add('form-horizontal');
				formHor2.action = "javascript:void(0)";
				
				var formGr1 = document.createElement("DIV");
				formGr1.id = "formGr1_" + i;
				formGr1.classList.add('form-group');
				formGr1.style.display = "none";
				var label1 = document.createElement("LABEL");
				var inputNome = document.createElement("INPUT");
				var nome = document.createTextNode("Nome: " + msg.campagneAperte[i].nome);
				inputNome.id = "txt1Ap_" + i;
				inputNome.type = "text";
				inputNome.name = "nomeCampagna";
				inputNome.value = msg.campagneAperte[i].nome;
				inputNome.classList.add('form-control');
				label1.appendChild(nome);
				formGr1.appendChild(label1);
				formGr1.appendChild(inputNome);
				
				var formGr2 = document.createElement("DIV");
				formGr2.id = "formGr2_" + i;
				formGr2.classList.add('form-group');
				formGr2.style.display = "none";
				var label2 = document.createElement("LABEL");
				var inputN = document.createElement("INPUT");
				var n = document.createTextNode("Numero minimo di utenti che devono effettuare il task1 per ogni immagine: " + msg.campagneAperte[i].n);
				inputN.id = "txt2Ap_" + i;
				inputN.type = "number";
				inputN.name = "nValutaz";
				inputN.value = 0;
				inputN.min = 0;
				inputN.value = msg.campagneAperte[i].n;
				inputN.classList.add('form-control');
				label2.appendChild(n);
				formGr2.appendChild(label2);
				formGr2.appendChild(inputN);
				
				var formGr3 = document.createElement("DIV");
				formGr3.id = "formGr3_" + i;
				formGr3.classList.add('form-group');
				formGr3.style.display = "none";
				var label3 = document.createElement("LABEL");
				var inputM = document.createElement("INPUT");
				var m = document.createTextNode("Numero minimo di utenti che devono effettuare il task2 per ogni immagine: " + msg.campagneAperte[i].m);
				inputM.id = "txt3Ap_" + i;
				inputM.type = "number";
				inputM.name = "mValutaz";
				inputM.value = 0;
				inputM.min = 0;
				inputM.value = msg.campagneAperte[i].m;
				inputM.classList.add('form-control');
				label3.appendChild(m);
				formGr3.appendChild(label3);
				formGr3.appendChild(inputM);
				
				var formGr4 = document.createElement("DIV");
				formGr4.id = "formGr4_" + i;
				formGr4.classList.add('form-group');
				formGr4.style.display = "none";
				var label4 = document.createElement("LABEL");
				var inputK = document.createElement("INPUT");
				var k = document.createTextNode("Numero di valutazioni positive che deve ricevere un’immagine: " + msg.campagneAperte[i].k);
				inputK.id = "txt4Ap_" + i;
				inputK.type = "number";
				inputK.name = "kValutaz";
				inputK.value = 0;
				inputK.min = 0;
				inputK.value = msg.campagneAperte[i].k;
				inputK.classList.add('form-control');
				label4.appendChild(k);
				formGr4.appendChild(label4);
				formGr4.appendChild(inputK);
				
				var formGr5 = document.createElement("DIV");
				formGr5.id = "formGr5_" + i;
				formGr5.classList.add('form-group');
				formGr5.style.display = "none";
				var label5 = document.createElement("LABEL");
				var inputP = document.createElement("INPUT");
				var p = document.createTextNode("Dimensioni in pixel della linea di annotazione: " + msg.campagneAperte[i].p);
				inputP.id = "txt5Ap_" + i;
				inputP.type = "number";
				inputP.name = "pAnnotaz";
				inputP.value = 0;
				inputP.min = 0;
				inputP.value = msg.campagneAperte[i].p;
				inputP.classList.add('form-control');
				label5.appendChild(p);
				formGr5.appendChild(label5);
				formGr5.appendChild(inputP);
				
				formHor.appendChild(formGr1);
				formHor.appendChild(formGr2);
				formHor.appendChild(formGr3);
				formHor.appendChild(formGr4);
				formHor.appendChild(formGr5);
				
				var oldName = document.createElement("input");
				oldName.id = "oldNameAp_" + i;
				oldName.type = "hidden";
				oldName.value = msg.campagneAperte[i].nome;
				
				var btn1 = document.createElement("button");
				btn1.id = "btnConfigCampagneAp_" + i;
				btn1.classList.add('btn');
				btn1.classList.add('btn-primary');
				btn1.classList.add('center-block');
				btn1.type = "submit";
				var txt6 = document.createTextNode('Conferma modifiche campagna');
				btn1.appendChild(txt6);
				var formGr6 = document.createElement("DIV");
				formGr6.id = "formGr6_" + i;
				formGr6.classList.add('form-group');
				formGr6.style.display = "none";
				formGr6.appendChild(btn1);

				var btn2 = document.createElement("button");
				btn2.id = "btnAvviaCampagnaAp_" + i;
				btn2.classList.add('btn');
				btn2.classList.add('btn-primary');
				btn2.classList.add('center-block');
				var txt7 = document.createTextNode('Avvia campagna');
				btn2.appendChild(txt7);
				btn2.onclick=function() { 
					var name = this.id;
					var nr = name.substring(19);
					avviaCampagna(nr); };				// Chiamata funzione relativa avvio campagna 
				var formGr7 = document.createElement("DIV");
				formGr7.id = "formGr7_" + i;
				formGr7.classList.add('form-group');
				formGr7.style.display = "none";
				formGr7.appendChild(btn2);

				var btn3 = document.createElement("button");
				btn3.id = "btnSelLavT1Ap_" + i;
				btn3.classList.add('btn');
				btn3.classList.add('btn-primary');
				btn3.classList.add('center-block');
				var txt8 = document.createTextNode('Abilita lavoratori task 1 campagna');
				btn3.appendChild(txt8);
				btn3.onclick=function() { 
					var name = this.id;
					var nr = name.substring(14);
					showLavT1(nr); };		// Chiamata funzione relativa all'assegnamento lavoratoti task 1
				var formGr8 = document.createElement("DIV");
				formGr8.id = "formGr8_" + i;
				formGr8.classList.add('form-group');
				formGr8.style.display = "none";
				formGr8.appendChild(btn3);
					
				var btn4 = document.createElement("button");
				btn4.id = "btnSelLavT2Ap_" + i;
				btn4.classList.add('btn');
				btn4.classList.add('btn-primary');
				btn4.classList.add('center-block');
				var txt9 = document.createTextNode('Abilita lavoratori task 2 campagna');
				btn4.appendChild(txt9);
				btn4.onclick=function() { 
					var name = this.id;
					var nr = name.substring(14);
					showLavT2(nr); };			// Chiamata funzione relativa all'assegnamento lavoratoti task 2
				var formGr9 = document.createElement("DIV");
				formGr9.id = "formGr9_" + i;
				formGr9.classList.add('form-group');
				formGr9.style.display = "none";
				formGr9.appendChild(btn4);
				
				var btn5 = document.createElement("button");
				btn5.id = "btnModal_"+ i;
				btn5.classList.add('btn');
				btn5.classList.add('btn-primary');
				btn5.classList.add('center-block');
				var txt10 = document.createTextNode('Visualizza immagini campagna');
				btn5.appendChild(txt10);
				btn5.onclick=function() { 
					var name = this.id;
					var nr = name.substring(9);
					getImg(nr); };					// Chiamata funzione relativa a immagini campagna
				var formGr10 = document.createElement("DIV");
				formGr10.id = "formGr10_" + i;
				formGr10.classList.add('form-group');
				formGr10.style.display = "none";
				formGr10.appendChild(btn5);
				
				var btn6 = document.createElement("button");
				btn6.id = "btnElimina_"+ i;
				btn6.classList.add('btn');
				btn6.classList.add('btn-danger');
				btn6.classList.add('center-block');
				var txt13 = document.createTextNode('Elimina campagna');
				btn6.appendChild(txt13);
				btn6.onclick=function() { 
					var name = this.id;
					var nr = name.substring(11);
					delCampagnaAperta(nr); };		// Chiamata funzione relativa a elimina campagna
				var formGr12 = document.createElement("DIV");
				formGr12.id = "formGr12_" + i;
				formGr12.classList.add('form-group');
				formGr12.style.display = "none";
				formGr12.appendChild(btn6);
				
				
				// Selezione e caricamento immagini
				var lblFile = document.createElement("label");
				lblFile.classList.add('btn');
				lblFile.classList.add('btn-primary');
				lblFile.classList.add('btn-file');
				lblFile.classList.add('center-block');
				var fileHidden = document.createElement("input");
				fileHidden.id = "fileHidden_" + i;
				fileHidden.type = "file";
				fileHidden.accept="image/x-png,image/gif,image/jpeg";
				fileHidden.multiple = "true";
				fileHidden.name = "fileHidden_" + i;
				fileHidden.style.display = "none";
				var txt11 = document.createTextNode('Seleziona immagine campagna');
				var idCamp = document.createElement("input");
				idCamp.id = "idCampAp_" + i;
				idCamp.name = "idCamp";
				idCamp.type = "hidden";
				idCamp.value = msg.campagneAperte[i].idcamp;
				var btn7 = document.createElement("input");
				btn7.value="Carica immagine campagna";
				btn7.id = "btnUpImg_"+ i;
				btn7.type = "submit";
				btn7.name ="submit";
				btn7.classList.add('btn');
				btn7.classList.add('btn-primary');
				btn7.classList.add('center-block');
				var txt12 = document.createTextNode('Carica immagine campagna');
				var formGr11 = document.createElement("FORM");
				formGr11.id = "formGr11_" + i;
				formGr11.method = "POST";
				formGr11.action = "../UploadImg";
				formGr11.enctype="multipart/form-data";
				formGr11.classList.add('form-group');
				formGr11.classList.add('text-center');
				formGr11.style.display = "none";
				var btnG = document.createElement("div");
				btnG.role = "group";
				btnG.classList.add('btn-group');
				lblFile.appendChild(txt11);
				lblFile.appendChild(fileHidden);
				btn7.appendChild(txt12);
				formGr11.appendChild(idCamp);
				btnG.appendChild(lblFile);
				btnG.appendChild(btn7);
				formGr11.appendChild(btnG);
				
				
				var li = document.createElement("LI");
				li.id = "liAp_" + i;
				
				
				var a = document.createElement("A");
				a.id = "a_" + i;
				a.onclick=function() { 
					var name = this.id;
					var nr = name.substring(2);
					setActiveAp(nr); };
	
				var camp = document.createTextNode(msg.campagneAperte[i].nome);
				a.appendChild(camp);
				li.appendChild(a);
				
				formHor.appendChild(formGr6);
				formHor2.appendChild(formGr8);
				formHor2.appendChild(formGr9);
				formHor2.appendChild(formGr11);
				formHor2.appendChild(formGr10);
				formHor2.appendChild(formGr7);
				formHor2.appendChild(formGr12);
				container.appendChild(formHor);				
				container2.appendChild(formHor2);				
				container.appendChild(oldName);
				li.appendChild(container);
				li.appendChild(container2);
				
				document.getElementById("listaCampAp").appendChild(li);
			}
			if (msg.campagneAperte.length){
				document.getElementById("liAp_" + 0).role = "presentation";
			} else {
				document.getElementById("listaCampAp").innerHTML = '<div class="alert alert-warning" role="alert">Nessuna campagna aperta! Crea una campagna!</div>';
			}
			
			// Metto a role presentation tutte le altre campagne
			for(i = 1; i < msg.campagneAperte.length; i++){
				document.getElementById("liAp_" + i).role = "presentation";
			}
			document.getElementById("listaCampAp").style.display = "block";
			
		}
	};
	xhttp.open("GET", "../GetCampagneAperte", true);
	xhttp.send();
}


// Per il bottone visualizza immagini campagna
function getImg(m){
	
	var idcamp = document.getElementById("idCampAp_" + m).value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);
			
			var nomeCamp = document.getElementById("oldNameAp_" + m).value;
			
			document.getElementById("modalImgTitle").innerHTML = 'Immagini Campagna';
			
			// Controllo se esiste almeno un immagine
			if (resp.immAp.length == 0){
				document.getElementById("modalImgFooter").innerHTML = 
					'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>';
				document.getElementById("modalImgBody").innerHTML = "Nessuna immagine presente!";
				$('#modalImg').modal('show');
				return;
			}
			
			// Settaggio del carousel per mostrare le immagini
			document.getElementById("modalImgBody").innerHTML = 
			'<div id="carouselImm" class="carousel slide" data-ride="carousel">'
				+'<!-- Indicators -->'
				+'<ol id="car-indic-Imm" class="carousel-indicators">'
				+'</ol>'
				+'<!-- Wrapper for slides -->'
				+'<div id="car-img-Imm" class="carousel-inner" role="listbox">'
				+'</div>'
				+'<!-- Controls -->'
					+'<a class="left carousel-control" href="#carouselImm" role="button" data-slide="prev">'
						+'<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>'
						+'<span class="sr-only">Previous</span>'
					+'</a>'
					+'<a class="right carousel-control" href="#carouselImm" role="button" data-slide="next">'
						+'<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>'
						+'<span class="sr-only">Next</span>'
					+'</a>'
			+'</div>';
			
			// Aggiunta di due bottoni elimina immagine e chiudi 
			document.getElementById("modalImgFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>'
				+		'<button type="button" class="btn btn-danger" onclick="eliminaImmagine(' + m + ')">Elimina immagine</button>'
			
			document.getElementById("car-indic-Imm").innerHTML = "";
			document.getElementById("car-img-Imm").innerHTML = "";
			
			// Inserimento immagini nel carousel
			for (i = 0; i < resp.immAp.length; i++){
				if (i == 0){
					document.getElementById("car-indic-Imm").innerHTML =  
						'<li data-target="#carouselImm" data-slide-to="0" class="active"></li>';
				} else {
					document.getElementById("car-indic-Imm").innerHTML +=  
					'<li data-target="#carouselImm" data-slide-to="'+ i +'"></li>';
				}
				
				if (i == 0){
					document.getElementById("car-img-Imm").innerHTML =  
						'<div class="item active">'
					    	+'<img src="../ImgSrc?path='+ resp.immAp[i] +'" alt="..." style="width:100%">'
					    	+'<div class="carousel-caption">'
					     		+ resp.immAp[i]
					     	+'</div>'
					    +'</div>';
				} else {
					document.getElementById("car-img-Imm").innerHTML +=  
						'<div class="item">'
							+'<img src="../ImgSrc?path='+ resp.immAp[i] +'" alt="..." style="width:100%">'
						   	+'<div class="carousel-caption">'
						   		+ resp.immAp[i]
						    +'</div>'
						+'</div>';
				}
			}
			$('#modalImg').modal('show');
		}
	};
	xhttp.open("POST", "../GetImg", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idcamp=" + idcamp + "&t=1");
}


// Funzione per il bottone elimina immagine
function eliminaImmagine(x){
	
	var y = document.getElementsByClassName('item active');
	var itemActive = y[0];
	var pathImm = itemActive.getElementsByClassName('carousel-caption')[0].innerHTML;
	
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = this.responseText;
			
			if (resp == "-1"){
				alert("non canc");
			} else {
				getImg(x);
			}
			
		}
	};
	xhttp.open("POST", "../EliminaImmagine", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("path=" + pathImm);
}

// Funzione per il bottone modifica campagna
function configCampagne(j) {
	
	var oldName = document.getElementById("oldNameAp_" + j).value;
	var nome = document.getElementById("txt1Ap_" + j).value;
	var n = document.getElementById("txt2Ap_" + j).value;
	var m = document.getElementById("txt3Ap_" + j).value;
	var k = document.getElementById("txt4Ap_" + j).value;
	var p = document.getElementById("txt5Ap_" + j).value;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = this.responseText;
			
			document.getElementById("modalAvvisiTitle").innerHTML = 
				'Avviso Campagna';

			if (resp == "-2"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Nome campagna già esistente!<br>'
					+ 'Scegline un altro!</div>';
			} else if (resp == "-1"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Ops! Qualcosa è andato storto!<br>'
					+ 'Contatta l\'amministratore!</div>';
			} else if (resp == "0"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Campagna modificata con successo!<br>'
					+ '</div>';
			}
			$('#modalAvvisi').modal('show');

		}
	};
	xhttp.open("POST", "../ModCampagna", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nomeN=" + nome + "&n=" + n + "&m=" + m + "&k=" + k + "&p=" + p + "&nomeO=" + oldName);		
}

// Funzione per il bottone avvia campagna
function avviaCampagna(k){
	var nome = document.getElementById("oldNameAp_" + k).value;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = this.responseText;
			
			document.getElementById("modalAvvisiTitle").innerHTML = 
				'Avviso Campagna';

			if (resp == "-2"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Devi aggiungere delle immagini per avviare la campagna!<br>'
					+ '</div>';
			} else if (resp == "-1"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Ops! Qualcosa è andato storto!<br>'
					+ 'Riprova più tardi!</div>';
			} else if (resp == "-4"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">I parametri della campagna devono essere '
					+ 'maggiori di zero per poter avviare la campagna!</div>';
			}else if (resp == "-5"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Non hai autorizzato abbastanza lavoratori '
					+ 'per effettuare il task Selezione Immagini!</div>';
			}else if (resp == "-6"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Non hai autorizzato abbastanza lavoratori '
					+ 'per effettuare il task Annotazione Immagini!</div>';
			} else if (resp == "0"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Campagna avviata con successo!<br>'
					+ '</div>';
				showCampagneAperte();
			}
			$('#modalAvvisi').modal('show');

		}
	};
	xhttp.open("POST", "../AvviaCampagna", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nome=" + nome);
}

// Cambio campagna
function setActiveAp(l){

	var ul = document.getElementById("listaCampAp");
	var items1 = ul.getElementsByTagName("li");

	// Disattivo tutti
	for (var i = 0; i < items1.length; i++) {
		var li = document.getElementById("liAp_" + i);
		if (li.classList.contains('active') ){
			li.classList.remove('active');
		}
	}
	
	// Nascondo tutte le campagne
	for (var i = 0; i < items1.length; i++){
		document.getElementById("formGr1_" + i).style.display = "none";	
		document.getElementById("formGr2_" + i).style.display = "none";	
		document.getElementById("formGr3_" + i).style.display = "none";	
		document.getElementById("formGr4_" + i).style.display = "none";	
		document.getElementById("formGr5_" + i).style.display = "none";	
		document.getElementById("formGr6_" + i).style.display = "none";	
		document.getElementById("formGr7_" + i).style.display = "none";	
		document.getElementById("formGr8_" + i).style.display = "none";	
		document.getElementById("formGr9_" + i).style.display = "none";	
		document.getElementById("formGr10_" + i).style.display = "none";
		document.getElementById("formGr11_" + i).style.display = "none";
		document.getElementById("formGr12_" + i).style.display = "none";
		
	}
	
	// Apro la campagna selezionata
	document.getElementById("liAp_" + l).classList.add("active");	
	document.getElementById("formGr1_" + l).style.display = "block";	
	document.getElementById("formGr2_" + l).style.display = "block";	
	document.getElementById("formGr3_" + l).style.display = "block";	
	document.getElementById("formGr4_" + l).style.display = "block";	
	document.getElementById("formGr5_" + l).style.display = "block";	
	document.getElementById("formGr6_" + l).style.display = "block";
	document.getElementById("formGr7_" + l).style.display = "block";
	document.getElementById("formGr8_" + l).style.display = "block";	
	document.getElementById("formGr9_" + l).style.display = "block";	
	document.getElementById("formGr10_" + l).style.display = "block";	
	document.getElementById("formGr11_" + l).style.display = "block";	
	document.getElementById("formGr12_" + l).style.display = "block";	
	
}

// Funzione per il bottone Abilita lavoratori task 1
function showLavT1(z){
	
	var idcamp = document.getElementById("idCampAp_" + z).value;
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);
			
			document.getElementById("modalListTitle").innerHTML = 
				'Lista lavoratori disponibili';

			
			if (resp.lavT.length == 0){
				document.getElementById("modalAnnotBody").innerHTML = 
					"Nessun lavoratore disponibile!";
				$('#modalList').modal('show');
				return;
			}
			
			document.getElementById("modalListBody").innerHTML = '<div class="input-group">';
			
			// Stampo tutti i nomi dei lavoratori
			for (i = 0; i < resp.lavT.length; i++){
				document.getElementById("modalListBody").innerHTML += 
						'<div>'
					+		'<label><input id="lavT1_'+i+'" type="checkbox" value="'+ resp.lavT[i].idLav +'">' + resp.lavT[i].username + '</label>'
					+	'</div>';
			}
			document.getElementById("modalListBody").innerHTML += "</div>";
			
			// Metto checkati quelli che sono gia assegnati
			for (i = 0; i < resp.lavT.length; i++){
				if (resp.lavT[i].t1 == "1"){
					document.getElementById("lavT1_"+i).checked = true;
				}
			}
			
			// Aggiungo bottoni chiudi e abilita
			document.getElementById("modalListFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>'
				+'<button id="abiLav1" type="button" class="btn btn-success">Abilita lavoratori</button>';
			
			$('#modalList').modal('show');
			
			var btt = document.getElementById("abiLav1");
			btt.onclick=function() { 
				abilitaLav(idcamp, '1'); };
		}
	};
	xhttp.open("POST", "../GetLavoratori", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idcamp=" + idcamp);
}

// Per il task 2
function showLavT2(z){
	
	var idcamp = document.getElementById("idCampAp_" + z).value;
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = JSON.parse(this.responseText);
			
			document.getElementById("modalListTitle").innerHTML = 
				'Lista lavoratori disponibili';
			
			if (resp.lavT.length == 0){
				document.getElementById("modalListBody").innerHTML = 
					"Nessun lavoratore disponibile!";
				$('#modalList').modal('show');
				return;
			}
			
			document.getElementById("modalListBody").innerHTML = '<div class="input-group">';

			for (i = 0; i < resp.lavT.length; i++){
				document.getElementById("modalListBody").innerHTML += 
					'<div>'
					+		'<label><input id="lavT2_'+i+'" type="checkbox" value="'+ resp.lavT[i].idLav +'">' + resp.lavT[i].username + '</label>'
					+	'</div>';
			}
			document.getElementById("modalListBody").innerHTML += "</div>";
			for (i = 0; i < resp.lavT.length; i++){
				if (resp.lavT[i].t2 == "1"){
					document.getElementById("lavT2_"+i).checked = true;
				}
			}
			document.getElementById("modalListFooter").innerHTML = 
				'<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>'
				+'<button id="abiLav2" type="button" class="btn btn-success">Abilita lavoratori</button>';
			
			$('#modalList').modal('show');
			
			var btt = document.getElementById("abiLav2");
			btt.onclick=function() { 
				abilitaLav(idcamp, '2'); };
		}
	};
	xhttp.open("POST", "../GetLavoratori", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idcamp=" + idcamp);
}

// Funzione per abilitare i lavoratori al task della campagna
function abilitaLav(idCamp,task){
	
	var elencoLav = document.getElementById("modalListBody");
	var arrLav = [];
	var lung = elencoLav.getElementsByTagName("input");
	var idLav = 0;
	var abilita = 0;
	
	// Controllo se task1 o 2
	if (task == '1'){
		// Modifico array se checkato a 1 o 0
		for (i=0; i<lung.length; i++){
			idLav = document.getElementById("lavT1_" + i).value;
			if (document.getElementById("lavT1_" + i).checked){
				arrLav.push({idL: idLav, ab: '1'});
			} else {
				arrLav.push({idL: idLav, ab: '0'});
			}
		}
	} else {
		// Modifico array se checkato a 1 o 0
		for (i=0; i<lung.length; i++){
			idLav = document.getElementById("lavT2_" + i).value;
			if (document.getElementById("lavT2_" + i).checked){
				arrLav.push({idL: idLav, ab: '1'});
			} else {
				arrLav.push({idL: idLav, ab: '0'});
			}
		}
	}
	
	
	var lavJson = JSON.stringify(arrLav);
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			$('#modalList').modal('hide');
			
			document.getElementById("modalAvvisiTitle").innerHTML = 
				'Avviso Campagna';
			
			var resp = this.responseText;

			if (resp == "0"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Lavoratori abilitati con successo!<br>'
					+ '</div>';
			} else {
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Ops! Qualcosa è andato storto!<br>'
					+ 'Riprova più tardi!</div>';
			}
			$('#modalAvvisi').modal('show');
		}
	};
	xhttp.open("POST", "../AbilitaLavoratori", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("idCamp=" + idCamp + "&task=" + task + "&lavJson=" + lavJson);
}

// Funzione per eliminare una campagna
function delCampagnaAperta(k){
	var nome = document.getElementById("oldNameAp_" + k).value;

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
				showCampagneAperte();
			}
			$('#modalAvvisi').modal('show');

		}
	};
	xhttp.open("POST", "../EliminaCampagna", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nome=" + nome + "&chiusa=0");
}

// Controllo se vi sono errori precedenti col caricamento delle foto
function errImg(n){
	if (n == "0"){
		document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Immagini aggiunte correttamente!<br>'
			+ '</div>';
	} else if (n=="1"){
		document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-warning" role="alert">Saranno caricate solo le immagini selezionate!<br>'
			+ '</div>';
	} 
	$('#modalAvvisi').modal('show');
}


