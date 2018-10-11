/**
 * 
 */
function registrati(){
	
	// Controllo che stia usando il form registrazione
	if(document.getElementById("formRegistrati").style.display == "block"){
		
		var user = document.getElementById("username").value;
		var pw = document.getElementById("password").value;
		var gest;
		// Gestore o lavoratore
		if (document.getElementById("gestR").checked){
			gest = true;
		} else {
			gest = false;
		}

		var xhttp = new XMLHttpRequest();

		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				var resp = this.responseText;
				
				// Se 1 e' andato a buon fine
				if (resp == "1"){
					document.getElementById("modalBody").innerHTML = '<div class="alert alert-success" role="alert">Registrazione effettuata con successo!</div>';
//					setTimeout(function () {
//						window.location.href = "homeGestore.jsp"; //will redirect to your page 
//				    	}, 1000); //will call the function after 2 secs.
				} else {
					// Non e' andato a buon fine
					document.getElementById("modalBody").innerHTML = '<div class="alert alert-danger" role="alert">Nome utente già esistente!</div>';
				}
				
				$('#modalLogin').modal('show');
			}
		};
		xhttp.open("POST", "../NewUser", true);
		xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhttp.send("username=" + user + "&password=" + pw + "&gestore=" + gest);
	} else {
		document.getElementById("formLogin").style.display = "none";
		document.getElementById("formRegistrati").style.display = "block";
	}

}

