
function showStatistiche() {
// Mostra o le campagnare disponibili o le statistiche
	
	if(document.getElementById("listaCampDisp").style.display == "block"){
		document.getElementById("listaCampDisp").style.display = "none";
	} else if(document.getElementById("listaStat").style.display == "block"){
		document.getElementById("listaStat").style.display = "none";
		return;
	}

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			// Parsa il json e pulisce la schermata
			var msg = JSON.parse(this.responseText);
			var ul1 = document.createElement("UL");
			document.getElementById("listaStat").innerHTML = "";
			
			// Scorre i vari task 
			for (i = 0; i < msg.taskAperti.length; i++) {
				
				var li = document.createElement("LI");
				li.id = "liStat_" + i;

				var a = document.createElement("A");
				a.id = "aStat_" + i;
				a.onclick=function() { 
					var name = this.id;
					var nr = name.substring(6);
					setActiveStat(nr); 
				};

				var ul = document.createElement("UL");
				ul.id = "ulStat_" + i;
				ul.style.textAlign = "center";

				var nomeCamp = document.createTextNode(msg.taskAperti[i].nomeCamp);
				
				// Parso a float i vari valori
				var percApp = parseFloat((msg.taskAperti[i].nImmApp));
				var percRif = parseFloat((msg.taskAperti[i].nImmRif));
				var percDaVot = parseFloat((msg.taskAperti[i].nImmDaVot));
				var tot1 = percApp*1 + percRif*1 + percDaVot*1; 
				
				// Mostro le varie statistiche
				var divBarT1 = document.createElement("DIV");
				divBarT1.id = "divBarT1_" + i;
				divBarT1.innerHTML =
					'<div class="progress" style="width: 50%; margin: auto;">'
					+ '<div class="progress-bar progress-bar-success" style="width: '+ (percApp/tot1)*100 + '%">'
					+  '<span class="sr-only"> % Positive </span>'
					+ ((percApp/tot1)*100).toFixed(2) + '%' 
					+ '</div>'
					+ '<div class="progress-bar progress-bar-warning progress-bar-striped" style="width: '+ (percDaVot/tot1)*100 + '%">'
					+  '<span class="sr-only"> % Missing </span>'
					+ ((percDaVot/tot1)*100).toFixed(2) + '%' 
					+ '</div>'
					+ '<div class="progress-bar progress-bar-danger" style="width: '+ (percRif/tot1)*100 + '%">'
					+  '<span class="sr-only"> % Negative </span>'
					+ ((percRif/tot1)*100).toFixed(2) + '%' 
					+ '</div>'
					+'</div>' ;
				divBarT1.style.margin = "auto";
				divBarT1.style.display = "none";
				var txx1 = document.createTextNode("");
				divBarT1.appendChild(txx1);
				
				// Faccio lo stesso anche per il task 2
				var percAnnot = parseFloat((msg.taskAperti[i].nAnnotate));
				var percDaAnnot = parseFloat((msg.taskAperti[i].nDaAnnotare));
				var tot2 = percAnnot*1 + percDaAnnot*1;
				
				var divBarT2 = document.createElement("DIV");
				divBarT2.id = "divBarT2_" + i;
				
				// Non ci siano ancora immagini annotate e nemmeno da annotare
				if((percAnnot + percDaAnnot)==0){
					divBarT2.innerHTML = 	
						'<div class="progress" style="width: 50%; margin: auto;">'
						+ '<div class="progress-bar progress-bar-success" style="width: 0%">'
						+  '<span class="sr-only"> % Complete </span>'
						+ '0%' 
						+ '</div>'
						+ '<div class="progress-bar progress-bar-danger progress-bar-striped" style="width: 100%">'
						+  '<span class="sr-only"> % Missing </span>'
						+ '100%'
						+ '</div>'
						+'</div>' ;
				} else {
				// Ci siano immagini da annotare o annotate
					divBarT2.innerHTML = 	
						'<div class="progress" style="width: 50%; margin: auto;">'
						+ '<div class="progress-bar progress-bar-success" style="width: '+ (percAnnot/tot2)*100 + '%">'
						+  '<span class="sr-only"> % Complete </span>'
						+ ((percAnnot/tot2)*100).toFixed(2) + '%' 
						+ '</div>'
						+ '<div class="progress-bar progress-bar-warning progress-bar-striped" style="width: '+ (percDaAnnot/tot2)*100 + '%">'
						+  '<span class="sr-only"> % Missing </span>'
						+ ((percDaAnnot/tot2)*100).toFixed(2) + '%'
						+ '</div>'
						+'</div>' ;
				}
				
				divBarT2.style.margin = "auto";
				divBarT2.style.display = "none";
				var txx2 = document.createTextNode("");
				divBarT2.appendChild(txx2);
				
				// Salvataggio di task 1 e 2
				var t1Hide = document.createElement("input");
				t1Hide.id = "t1HideStat_" + i;
				t1Hide.type = "hidden";
				t1Hide.value = msg.taskAperti[i].t1;

				var t2Hide = document.createElement("input");
				t2Hide.id = "t2HideStat_" + i;
				t2Hide.type = "hidden";
				t2Hide.value = msg.taskAperti[i].t2;

				a.appendChild(nomeCamp);
				li.appendChild(a);

				li.appendChild(t1Hide);
				li.appendChild(t2Hide);

				document.getElementById("listaStat").appendChild(li);

				if((document.getElementById("t1HideStat_" + i).value) == 'true'){
					// Visualizzo le statistiche in numeri del task 1
					// Prima creo tutti i li poi appendo i vari lbl
					var li4 = document.createElement("LI");
					li4.id = "li4Stat_" + i;
					li4.style.margin = "auto";
					li4.style.display = "none";
					var li5 = document.createElement("LI");
					li5.id = "li5Stat_" + i;
					li5.style.margin = "auto";
					li5.style.display = "none";
					var li6 = document.createElement("LI");
					li6.id = "li6Stat_" + i;
					li6.style.margin = "auto";
					li6.style.display = "none";
					
					var nImmApp = document.createTextNode("Numero immagini approvate: " + msg.taskAperti[i].nImmApp);
					var label1 = document.createElement("LABEL");
					label1.appendChild(nImmApp);
					li4.appendChild(label1);
					ul.appendChild(li4);

					var nImmRif = document.createTextNode("Numero immagini rifiutate: " + msg.taskAperti[i].nImmRif);
					var label2 = document.createElement("LABEL");
					label2.appendChild(nImmRif);
					li5.appendChild(label2);
					ul.appendChild(li5);
					
					var nImmDaVot = document.createTextNode("Numero immagini da votare: " + msg.taskAperti[i].nImmDaVot);
					var label3 = document.createElement("LABEL");
					label3.appendChild(nImmDaVot);
					li6.appendChild(label3);
					ul.appendChild(li6);
			
					ul.appendChild(li4);
					ul.appendChild(li5);
					ul.appendChild(li6);

					ul.appendChild(divBarT1);
				}

				if((document.getElementById("t2HideStat_" + i).value) == 'true'){
					// Visualizzo le statistiche in numeri del task 2
					// Prima creo tutti i li poi appendo i vari lbl
					
					var li7 = document.createElement("LI");
					li7.id = "li7Stat_" + i;
					li7.style.margin = "auto";
					li7.style.display = "none";
					var li8 = document.createElement("LI");
					li8.id = "li8Stat_" + i;
					li8.style.margin = "auto";
					li8.style.display = "none";
					
					var nAnnotate = document.createTextNode("Numero immagini annotate: " + msg.taskAperti[i].nAnnotate);
					var label4 = document.createElement("LABEL");
					label4.appendChild(nAnnotate);
					li7.appendChild(label4);
					ul.appendChild(li7);

					var nDaAnnotare = document.createTextNode("Numero immagini da annotare: " + msg.taskAperti[i].nDaAnnotare);
					var label5 = document.createElement("LABEL");
					label5.appendChild(nDaAnnotare);
					li8.appendChild(label5);
					ul.appendChild(li8);

					ul.appendChild(li7);
					ul.appendChild(li8);

					ul.appendChild(divBarT2);
				}

				li.appendChild(ul);		
			}

			if (msg.taskAperti.length){
				document.getElementById("liStat_" + 0).role = "presentation";
			} else {
				document.getElementById("listaStat").innerHTML = '<div class="alert alert-warning" role="alert">Non hai partecipato a nessuna campagna!<br>'
					+ 'Partecipa ad una campagna e qui visualizzerai le tue statische.</div>';
			}


			for(i = 1; i < msg.taskAperti.length; i++){
				document.getElementById("liStat_" + i).role = "presentation";
			}
			document.getElementById("listaStat").style.display = "block";

		}
	};
	xhttp.open("GET", "../GetTask", true);
	xhttp.send();
}

// // Se viene cliccata una campagna chiude le altre
function setActiveStat(l){

	var ul = document.getElementById("listaStat");
	var items1 = ul.getElementsByTagName("ul");

	for (var i = 0; i < items1.length; i++) {
		var li = document.getElementById("liStat_" + i);
		if (li.classList.contains('active') ){
			li.classList.remove('active');
		}
	}

	for (var i = 0; i < items1.length; i++){

		if((document.getElementById("t1HideStat_" + i).value) == 'true'){
			document.getElementById("divBarT1_" + i).style.display = "none";
			document.getElementById("li4Stat_" + i).style.display = "none";	
			document.getElementById("li5Stat_" + i).style.display = "none";	
			document.getElementById("li6Stat_" + i).style.display = "none";	
		}

		if((document.getElementById("t2HideStat_" + i).value) == 'true'){
			document.getElementById("divBarT2_" + i).style.display = "none";
			document.getElementById("li7Stat_" + i).style.display = "none";	
			document.getElementById("li8Stat_" + i).style.display = "none";	
		}
	}

	document.getElementById("liStat_" + l).classList.add("active");	

	if((document.getElementById("t1HideStat_" + l).value) == 'true'){
		document.getElementById("divBarT1_" + l).style.display = "block";
		document.getElementById("li4Stat_" + l).style.display = "block";	
		document.getElementById("li5Stat_" + l).style.display = "block";	
		document.getElementById("li6Stat_" + l).style.display = "block";	
	}

	if((document.getElementById("t2HideStat_" + l).value) == 'true'){
		document.getElementById("divBarT2_" + l).style.display = "block";
		document.getElementById("li7Stat_" + l).style.display = "block";	
		document.getElementById("li8Stat_" + l).style.display = "block";	
	}


}