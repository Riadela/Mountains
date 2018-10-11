function showCampagneDisp() {
	
	// Mostra o quelle disponibili o le statistiche
	if(document.getElementById("listaCampDisp").style.display == "block"){
		document.getElementById("listaCampDisp").style.display = "none";
		return;
	} else if(document.getElementById("listaStat").style.display == "block"){
		document.getElementById("listaStat").style.display = "none";
	}

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			// Parsa il json e cancello il contenuto 
			var msg = JSON.parse(this.responseText);
			var ul1 = document.createElement("UL");
			document.getElementById("listaCampDisp").innerHTML = "";
			
			// Visualizzazione di tutte le campagne
			for (i = 0; i < msg.taskAperti.length; i++) {

				var li = document.createElement("LI");
				li.id = "liDisp_" + i;

				var a = document.createElement("A");
				a.id = "aDisp_" + i;
				a.onclick=function() { 
					var name = this.id;
					var nr = name.substring(6);
					setActiveDisp(nr); 
				};

				var ul = document.createElement("UL");
				ul.id = "ulDisp_" + i;
				ul.style.textAlign = "center";
				var li1 = document.createElement("LI");
				li1.id = "li1Disp_" + i;
				li1.style.display = "none";
				var li2 = document.createElement("LI");
				li2.id = "li2Disp_" + i;
				li2.style.display = "none";
				
				// Salvataggio degli id del task 1 e 2 e della campagna
				var t1Hide = document.createElement("input");
				t1Hide.id = "t1HideDisp_" + i;
				t1Hide.type = "hidden";
				t1Hide.value = msg.taskAperti[i].t1;

				var t2Hide = document.createElement("input");
				t2Hide.id = "t2HideDisp_" + i;
				t2Hide.type = "hidden";
				t2Hide.value = msg.taskAperti[i].t2;
				
				var t3Hide = document.createElement("input");
				t3Hide.id = "t3HideDisp_" + i;
				t3Hide.type = "hidden";
				t3Hide.value = msg.taskAperti[i].nomeCamp;
				
				// Creazione dei bottoni del task 1 e 2
				var btn1 = document.createElement("button");
				btn1.id = "btnPlayT1_" + i;
				btn1.style.display = "none";
				btn1.classList.add('btn');
				btn1.classList.add('btn-primary');
				btn1.classList.add('center-block');
				var txtBtn1 = document.createTextNode("Svolgi task 1 campagna " + msg.taskAperti[i].nomeCamp);
				btn1.appendChild(txtBtn1);
				btn1.onclick=function() { 
					var name = this.id;
					var nr = name.substring(10);
					task1(nr); 			// Chiamata della funzione del task 1
				};
				var formGr1 = document.createElement("DIV");
				formGr1.id = "formGr1_" + i;
				formGr1.classList.add('form-group');
				formGr1.appendChild(btn1);

				var btn2 = document.createElement("button");
				btn2.id = "btnPlayT2_" + i;
				btn2.style.display = "none";
				btn2.classList.add('btn');
				btn2.classList.add('btn-primary');
				btn2.classList.add('center-block');
				var txtBtn2 = document.createTextNode("Svolgi task 2 campagna " + msg.taskAperti[i].nomeCamp);
				btn2.appendChild(txtBtn2);
				btn2.onclick=function() { 
					var name = this.id;
					var nr = name.substring(10);
					task2(nr); 		// Chiamata della funzione del task 2
				};
				var formGr2 = document.createElement("DIV");
				formGr2.id = "formGr2_" + i;
				formGr2.classList.add('form-group');
				formGr2.appendChild(btn2);
				
				var container = document.createElement("DIV");
				container.id = "container_" + i;
				container.classList.add('container');
				container.style.display = "none";
				container.style.margin = "auto";

				var formHor = document.createElement("FORM");
				formHor.classList.add('form-horizontal');
				formHor.action = "javascript:void(0)";

				var camp = document.createTextNode(msg.taskAperti[i].nomeCamp);
				a.appendChild(camp);
				li.appendChild(a);
				
				// Se vi sono immagini ancora da votare
				if (msg.taskAperti[i].t1 && msg.taskAperti[i].nImmDaVot != 0){
					var daVot = document.createTextNode("Hai ancora " + msg.taskAperti[i].nImmDaVot + " immagini da votare per completare il task 1!");
					var label1 = document.createElement("LABEL");
					label1.appendChild(daVot);
					li1.appendChild(label1);
					ul.appendChild(li1);
					formHor.appendChild(formGr1);
				// Se non ci sono piu immagini da votare
				} else if (msg.taskAperti[i].t1 && msg.taskAperti[i].nImmDaVot == 0){
					var votate = document.createTextNode("Hai votato tutte le immagini per completare il task 1!");
					var label1 = document.createElement("LABEL");
					label1.appendChild(votate);
					li1.appendChild(label1);
					ul.appendChild(li1);
					formHor.appendChild(formGr1);
					formGr1.style.display = "none";		
				}
				// Se vi sono immagini da annotare
				if (msg.taskAperti[i].t2 && msg.taskAperti[i].nDaAnnotare != 0){
					var daAnnot = document.createTextNode("Hai ancora " + msg.taskAperti[i].nDaAnnotare + " immagini da annotare per completare il task 2!");
					var label2 = document.createElement("LABEL");
					label2.appendChild(daAnnot);
					li2.appendChild(label2);
					ul.appendChild(li2);
					formHor.appendChild(formGr2);
				// Se non vi sono piu immagini da annotare
				} else if (msg.taskAperti[i].t2 && msg.taskAperti[i].nDaAnnotare == 0){
					var annotate = document.createTextNode("Hai annotato tutte le immagini per completare il task 2!");
					var label2 = document.createElement("LABEL");
					label2.appendChild(annotate);
					li2.appendChild(label2);
					ul.appendChild(li2);
					formHor.appendChild(formGr2);
					formGr2.style.display = "none";
				}
				
				container.appendChild(formHor);
				ul.appendChild(container);
				
				li.appendChild(ul);		
				li.appendChild(t1Hide);
				li.appendChild(t2Hide);
				li.appendChild(t3Hide);

				document.getElementById("listaCampDisp").appendChild(li);
			}
			
			
			if (msg.taskAperti.length){
				document.getElementById("liDisp_" + 0).role = "presentation";
			} else {
				document.getElementById("listaCampDisp").innerHTML = '<div class="alert alert-warning" role="alert">Non sei abilitato a nessuna campagna!<br>'
					+ 'Riprova più tardi!</div>';
			}

			for(i = 1; i < msg.taskAperti.length; i++){
				document.getElementById("liDisp_" + i).role = "presentation";
			}
			
			document.getElementById("listaCampDisp").style.display = "block";
		}
	};
	xhttp.open("GET", "../GetTask", true);
	xhttp.send();		
}

// Se viene cliccata una campagna chiude le altre
function setActiveDisp(l){

	var ul = document.getElementById("listaCampDisp");
	var items1 = ul.getElementsByTagName("ul");

	for (var i = 0; i < items1.length; i++) {
		var li = document.getElementById("liDisp_" + i);
		if (li.classList.contains('active') ){
			li.classList.remove('active');
		}
	}

	for (var i = 0; i < items1.length; i++){
		document.getElementById("container_" + i).style.display = "none";

		if((document.getElementById("t1HideDisp_" + i).value) == 'true'){
			document.getElementById("li1Disp_" + i).style.display = "none";	
			document.getElementById("btnPlayT1_" + i).style.display = "none";	
		}

		if((document.getElementById("t2HideDisp_" + i).value) == 'true'){
			document.getElementById("li2Disp_" + i).style.display = "none";	
			document.getElementById("btnPlayT2_" + i).style.display = "none";	
		}
	}

	document.getElementById("liDisp_" + l).classList.add("active");	
	document.getElementById("container_" + l).style.display = "block";

	if((document.getElementById("t1HideDisp_" + l).value) == 'true'){
		document.getElementById("li1Disp_" + l).style.display = "block";	
		document.getElementById("btnPlayT1_" + l).style.display = "block";	
	}
	if((document.getElementById("t2HideDisp_" + l).value) == 'true'){
		document.getElementById("li2Disp_" + l).style.display = "block";	
		document.getElementById("btnPlayT2_" + l).style.display = "block";	
	}
}

// Funzione per sloggarsi
function logout(){
	
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = this.responseText;
			if(resp == "0") {
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Logout effettuato con successo! A presto!</div>';
				setTimeout(function () {
					window.location.href = "login.jsp"; //will redirect to your page 
			    	}, 1000); //will call the function after 2 secs.
			} else {
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-danger" role="alert">Ops! Qualcosa è andato storto!	</div>';
			}
			$('#modalAvvisi').modal('show');
		}
	};
xhttp.open("GET", "../Logout", true);
xhttp.send();
}


// Funzione nel caso venga cliccato il bottone del task 1
function task1(nrCamp){
	
	var nomecamp = document.getElementById("t3HideDisp_" + nrCamp).value;

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = this.responseText;
			if(resp == "0") {
				window.location.href = "task1.jsp"; 
			} else {
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-danger" role="alert">Ops! Qualcosa è andato storto!</div>';
				$('#modalAvvisi').modal('show');
			}
		}
	};
	xhttp.open("POST", "../Task1", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nomeCampagna=" + nomecamp);
}

//Funzione nel caso venga cliccato il bottone del task 2
function task2(nrCamp){
	
	var nomecamp = document.getElementById("t3HideDisp_" + nrCamp).value;

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = this.responseText;
			if(resp == "0") {
				window.location.href = "task2.jsp"; 
			} else {
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-danger" role="alert">Ops! Qualcosa è andato storto!</div>';
				$('#modalAvvisi').modal('show');
			}
		}
	};
	xhttp.open("POST", "../Task2", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nomeCampagna=" + nomecamp);
}


