/**
 * 
 */
function showFormCrea(){
	
	if(document.getElementById("listaCampCrea").style.display == "block"){
		document.getElementById("listaCampCrea").style.display = "none";
		return;
	} else {
		document.getElementById("listaCampAvv").style.display = "none";
		document.getElementById("listaCampAp").style.display = "none";
		document.getElementById("listaCampCrea").style.display = "block";
	}
	
}


function creaCamp() {
	
	// Salvataggio degli input text in variabili
	var nome = document.getElementById("txt1Crea_").value;
	var n = document.getElementById("txt2Crea_").value;
	var m = document.getElementById("txt3Crea_").value;
	var k = document.getElementById("txt4Crea_").value;
	var p = document.getElementById("txt5Crea_").value;

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var resp = this.responseText;
			// Se 0 la campagna e' stata creata con successo altrimenti vi e' un errore
			if (resp == "0"){
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-success" role="alert">Campagna creata con successo!</div>';
			} else {
				document.getElementById("modalAvvisiBody").innerHTML = '<div class="alert alert-danger" role="alert">Nome campagna già presente, scegline un altro!</div>';
			}
			$('#modalAvvisi').modal('show');
		}
	};
	xhttp.open("POST", "../CreaCampagna", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("nomeCampagna=" + nome + "&nValutaz=" + n + "&mValutaz=" + m + "&kValutaz=" + k + "&pAnnotaz=" + p);	
}

function logout(){
	
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = this.responseText;
			
			// Se 0 effettutato con successo altrimenti errore
			if(resp == "0") {
				document.getElementById("modalAvvisiTitle").innerHTML = 'Gestore';
				document.getElementById("modalAvvisiBody").innerHTML = 
					'<div class="alert alert-success" role="alert">Logout effettuato con successo! A presto!</div>';
				setTimeout(function () {
					window.location.href = "login.jsp"; //will redirect to your page 
			    	}, 1000); //will call the function after 2 secs.
			} else {
				document.getElementById("modalBody").innerHTML = '<div class="alert alert-danger" role="alert">Credenziali errate!</div>';
			}
			$('#modalAvvisi').modal('show');
		}
	};
xhttp.open("GET", "../Logout", true);
xhttp.send();
}

