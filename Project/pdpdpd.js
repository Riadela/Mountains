<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Progetto TIW</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>
<div class="panel panel-primary" style="border-style: none; text-align: center;">
    <div class="panel-heading clearfix">
        <h3 class="panel-title pull-left" style="padding-top: 7.5px;">Progetto Web Technologies A.A. 16-17</h3>
    </div>
      <div class="panel-body" id="bodyT2">
        <h1>Benvenuto!</h1>
        <p class="lead">
          Nella prossima pagina l'utente potrà loggarsi o registrarsi, come lavoratore o come gestore.<br>
        </p>
        <p class="lead">
          Il gestore è colui che si occupa di tutto ciò che riguarda una campagna.<br>
          Nella sua schermata ha la possibilità di creare una campagna, modificarne i parametri,<br>
          abilitare i lavoratori e caricare le immagini oggetto dei task. Egli inoltre <br>
          ha la possibiltà di controllare le statistiche delle campagne in corso ed eventualmente<br>
          chiudere quelle completate.<br>
        </p>
        <p class="lead">
          Il lavoratore è colui che svolge effettivamente i task Selezione e Annotazione di immagini.<br>
          Nella sua schermata può scegliere di partecipare alle campagne di cui è <br>
          stato reso partecipe e controllarne le statistiche.<br>
        </p>
        <p class="lead">
          Il task Selezione immagini (task1) richiede all'utente di confermare le immagini<br>
          che rappresentano montagne.<br>
          Il task Annotazione immagini (task2) richiede che sulle immagini scelte nel task1<br>
          vengano tracciate le linee della skyline.<br>
        </p>
        <button onclick="getStarted()" class="btn btn-primary center-block">Comincia!</button>
    </div>
  </div>
  <script>
  function getStarted(){
      window.location.href = "../jsp/login.jsp";
  }
  </script>
  <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</body>
</html>