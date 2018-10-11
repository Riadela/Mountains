
function login(){
	
	// Controllo se e' il form del login
	if(document.getElementById("formLogin").style.display == "block"){
		
		var user = document.getElementById("user").value;
		var pw = document.getElementById("pw").value;
		var gest;
		if (document.getElementById("gest").checked){
			gest = true;
		} else {
			gest = false;
		}

		var xhttp = new XMLHttpRequest();

		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				var resp = this.responseText;
				
				// Se 1 e' un gestore, se 2 e' un lavoratore, altrimenti vi e' un errore di credenziali
				
				if (resp == "1"){
					document.getElementById("modalBody").innerHTML = '<div class="alert alert-success" role="alert">Login gestore effettuato con successo!</div>';
					setTimeout(function () {
						window.location.href = "homeGestore.jsp"; //will redirect to your page 
				    	}, 1000); //will call the function after 2 secs.
				} else if(resp == "2") {
					document.getElementById("modalBody").innerHTML = '<div class="alert alert-success" role="alert">Login lavoratore effettuato con successo!</div>';
					setTimeout(function () {
						window.location.href = "homeLavoratore.jsp"; //will redirect to your page 
				    	}, 1000); //will call the function after 2 secs.
				} else {
					document.getElementById("modalBody").innerHTML = '<div class="alert alert-danger" role="alert">Credenziali errate!</div>';
				}
				$('#modalLogin').modal('show');
			}
		};
		xhttp.open("POST", "../Login", true);
		xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhttp.send("username=" + user + "&password=" + pw + "&gestore=" + gest);
		} else {
			document.getElementById("formRegistrati").style.display = "none";
			document.getElementById("formLogin").style.display = "block";
		}
}
