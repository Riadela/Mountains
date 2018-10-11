function doTask(){
	
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			// Parse del response della chiamata ajax
			var resp = JSON.parse(this.responseText);
			
			// Controllo se vi sono altre immagini
			if (resp.immTask2.length == 0){
				document.getElementById("modalTaskBody").innerHTML = 
					'<div class="alert alert-success" role="alert">Hai completato questo task, controlla le statistiche nella tua homepage!"</div>';
				$('#modalTask').modal('show');
				setTimeout(function () {
					window.location.href = "homeLavoratore.jsp"; //will redirect to your page 
			    	}, 2000);
				return;
			}	
			
			// Settaggio del canvas e dei vari bottoni
			document.getElementById("bodyT2").innerHTML = '<canvas id="myCanvas" width="740" height="416"></canvas>'
				+'<br>'
				+'<div class="btn-group" role="group" aria-label="...">'
				+	'<button id="canc" onclick="doTask()" type="button" class="btn btn-danger">Cancella</button>'
				+	'<button id="accept" onclick="accetta()" type="button" class="btn btn-success">Accetta</button>'
				+'</div>';
			
			var canvas = document.getElementById('myCanvas');
		    var context = canvas.getContext('2d');
		    var imageObj = new Image();

		    imageObj.onload = function() {
		    	// Rectangle source and destination
		    	context.drawImage(imageObj, 0, 0, imageObj.width, imageObj.height, 0, 0, canvas.width, canvas.height); 
		    };
		    // Caricamento della prima foto nell'oggetto
		    imageObj.src = '../ImgSrc?path='+ resp.immTask2[0].path;
		    var path = resp.immTask2[0].path;  	// prende path campagna/nome.jpeg
		    path = path.substring(path.indexOf("/") + 1);  // prende nome.jpeg
		    var n = path.length;
		    var len = (n*1) - 5;
		    var imgid = path.substring(0, len); // prende "nome"
		    document.getElementById("imgId").value = imgid;
		    document.getElementById("annot").value = "0"
		    InitThis();
		}
	};
	xhttp.open("GET", "../GetImgT2", true);
	xhttp.send();
}

var mousePressed = false;
var lastX, lastY;
var ctx;

// Controlla eventi del mouse
function InitThis() {
    ctx = document.getElementById('myCanvas').getContext("2d");

    $('#myCanvas').mousedown(function (e) {
        mousePressed = true;
        Draw(e.pageX - $(this).offset().left, e.pageY - $(this).offset().top, false);
    });

    $('#myCanvas').mousemove(function (e) {
        if (mousePressed) {
            Draw(e.pageX - $(this).offset().left, e.pageY - $(this).offset().top, true);
        }
    });

    $('#myCanvas').mouseup(function (e) {
        mousePressed = false;
    });
	    $('#myCanvas').mouseleave(function (e) {
        mousePressed = false;
    });
}

// Funzione disegna
function Draw(x, y, isDown) {
	document.getElementById("annot").value = "1";
    if (isDown) {
        ctx.beginPath();
        ctx.strokeStyle = "#FF3300";
        ctx.lineWidth = document.getElementById("pixel").value;
        ctx.lineJoin = "round";
        ctx.moveTo(lastX, lastY);
        ctx.lineTo(x, y);
        ctx.closePath();
        ctx.stroke();
    }
    lastX = x; lastY = y;
}


// Accetta la foto annotata
function accetta(){
	
	var dataImg = document.getElementById("myCanvas").toDataURL(); // Prende l'immagine dal canvas e lo converte in url
	dataImg = dataImg.replace(/^data:image\/(png|jpg);base64,/, ""); 
	
	var username = document.getElementById("username").value;
	var idcamp = document.getElementById("idcamp").value;
	var idimg = document.getElementById("imgId").value;
	var annot = document.getElementById("annot").value;
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = this.responseText;
			if(resp == "-1") {
				document.getElementById("modalTaskBody").innerHTML = '<div class="alert alert-danger" role="alert">Devi tracciare almeno una linea!</div>';
				$('#modalTask').modal('show');
			} else {
				doTask();
			}
		}
	};
	xhttp.open("POST", "../UploadAnnot", true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhttp.send("dataImg=" + dataImg + "&username=" + username + "&idcamp=" + idcamp + "&idimg=" + idimg + "&annot=" + annot);
	
}

function back(){
    window.location.href = "../jsp/homeLavoratore.jsp";
}
