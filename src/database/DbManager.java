package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.*;

import com.mysql.jdbc.Statement;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;




public class DbManager {

	private Connection connection = null;

	public DbManager() throws SQLException, ClassNotFoundException {
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/progetto?autoreconnect=true&useSSL=false", "root", "pass");
	}

	public void destroy() throws SQLException{
		if (connection != null){
			connection.close();
		}
	}
	
	// Metodo per il login
	public boolean login(String username, String password, boolean gest) throws SQLException{

		PreparedStatement stmnt = null;
		
		// Se e' un gestore controlla tabella gestore
		if (gest){
			String query = "SELECT * FROM progetto.Gestore WHERE username=? AND password=?";
			stmnt = connection.prepareStatement(query);
			stmnt.setString(1, username);
			stmnt.setString(2, password);
		} else { // Se lavoratore controlla tabella lavoratore
			String query = "SELECT * FROM progetto.Lavoratore WHERE username=? AND password=?";
			stmnt = connection.prepareStatement(query);
			stmnt.setString(1, username);
			stmnt.setString(2, password);
		}

		ResultSet rs = null;
		rs = stmnt.executeQuery();
		
		// Andato a buon fine
		if(rs.next()){
			rs.close();
			stmnt.close();
			return true;   
		} else { // Credenziali sbagliate
			rs.close();
			stmnt.close();
			return false;   
		}
	}


	//METODI INSERIMENTO
	
	// Registrazione di un lavoratore
	public boolean nuovoLavoratore(String username, String password) throws SQLException{
		
		// Controllo esistenza di un username esistente
		PreparedStatement stmnt= null;
		String query = "SELECT * FROM progetto.Lavoratore WHERE username=?";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, username);
		ResultSet rs = stmnt.executeQuery();

		if (rs.next()){
			rs.close();
			stmnt.close();

			return false;
		} else { // Se non esiste gia, allora lo inserisce
			query = "INSERT INTO Lavoratore (username, password) VALUES (?,?)";
			stmnt = connection.prepareStatement(query);
			stmnt.setString(1, username);
			stmnt.setString(2, password);
			stmnt.executeUpdate();
			rs.close();
			stmnt.close();

			return true;
		}

	}
	
	// Registrazione di un gestore
	public boolean nuovoGestore(String username, String password) throws SQLException{
		
		// Controllo esistenza di un username uguale
		PreparedStatement stmnt= null;
		String query = "SELECT * FROM progetto.Gestore WHERE username=?";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, username);
		ResultSet rs = stmnt.executeQuery();
		
		if (rs.next()){
			rs.close();
			stmnt.close();
			return false;
		} else { // Se non esiste allora lo inserisce
			stmnt = null;
			query = "INSERT INTO Gestore (username, password) VALUES (?,?)";
			stmnt = connection.prepareStatement(query);
			stmnt.setString(1, username);
			stmnt.setString(2, password);
			stmnt.executeUpdate();
			rs.close();
			stmnt.close();
			return true;
		}
	}

	// Inserimento di un nuova campagna
	public boolean nuovaCampagna(String nCamp, String username, int n, int m, int k, int p) throws SQLException{
		
		// Controllo se esiste una campagna con lo stesso nome
		PreparedStatement stmnt= null;
		String query = "SELECT * FROM progetto.Campagna WHERE nome=?";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, nCamp);
		ResultSet rs = stmnt.executeQuery();
		
		// Se esiste una campagna con lo stesso nome
		if (rs.next()){
			rs.close();
			stmnt.close();
			return false;
		} else { // Non esiste una campagna con lo stesso nome
			
			// Ottengo l'id gel gestore tramite l'username
			stmnt = null;
			query = "SELECT idGestore FROM progetto.Gestore WHERE username=?";
			stmnt = connection.prepareStatement(query);
			stmnt.setString(1, username);
			rs = stmnt.executeQuery();

			rs.next();
			int idGest =rs.getInt(1); // salvo l'id del gestore
			
			// Inserisco la nuova campagna nella tabella campagna
			query = "INSERT INTO Campagna (nome, idGestore, n, m, k, p, chiusa) VALUES (?,?,?,?,?,?,?)";
			stmnt = connection.prepareStatement(query);
			stmnt.setString(1, nCamp);
			stmnt.setInt(2, idGest);
			stmnt.setInt(3, n);
			stmnt.setInt(4, m);
			stmnt.setInt(5, k);
			stmnt.setInt(6, p);
			stmnt.setInt(7, 0);
			stmnt.executeUpdate();
			rs.close();
			stmnt.close();
			return true;
		}
	}
	
	// Crea la riga immagine e ritorna l'id
	public int aggImmagine1(int idCamp) throws SQLException{

		PreparedStatement stmnt= null;
		ResultSet rs = null;

		
		String query = "INSERT INTO Immagine (idCampagna) VALUES (?)";

		stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	// crea la riga e gli ritorna l'id
		stmnt.setInt(1, idCamp);

		stmnt.executeUpdate();

		int autoKey = -1;

		rs = stmnt.getGeneratedKeys();
		
		// ottiene il nuovo id
		if (rs.next()) {
			autoKey = rs.getInt(1);
			rs.close();
			stmnt.close();
			return autoKey;
		} else {
			rs.close();
			stmnt.close();
			return -1;
		}

	}
	
	// Modifica la riga immagine con i dati nuovi
	public int aggImmagine2(int name, String path) throws SQLException{

		PreparedStatement stmnt= null;

		String query;

		int mod = 0;

		query = "UPDATE Immagine SET link =  ?, pos = 0, neg = 0, annot = 0"
				+ " WHERE idImmagine = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setInt(2, name);
		stmnt.setString(1, path);
		mod = stmnt.executeUpdate();
		
		// se andato a buon fine
		if (mod>0){
			stmnt.close();
			return 0;
		} else {
			stmnt.close();
			return -1;
		}

	}
	
	// aggiunge la riga nella tabella annotazione per ottenere id
	public int aggAnnot1(int idImg, int idLav) throws SQLException {
		
		PreparedStatement stmnt= null;
		ResultSet rs = null;
		String query = "INSERT INTO Annotazione (idImmagine, idLavoratore) VALUES (?,?)";

		stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stmnt.setInt(1, idImg);
		stmnt.setInt(2, idLav);

		stmnt.executeUpdate();

		int autoKey = -1;

		rs = stmnt.getGeneratedKeys();

		if (rs.next()) {
			autoKey = rs.getInt(1);
			rs.close();
			stmnt.close();
			return autoKey;	// ritorna l'id della nuova riga
		} else {
			rs.close();
			stmnt.close();
			return -1;
		}		
	}
	
	// aggiorna la riga del db con l'effettivo link dell'immagine
	public int aggAnnot2(int name, String path) throws SQLException {
		
		PreparedStatement stmnt= null;
		String query;
		int mod = 0;
		
		query = "UPDATE Annotazione SET link =  ?"
				+ " WHERE idAnnotazione = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setInt(2, name);
		stmnt.setString(1, path);
		mod = stmnt.executeUpdate();

		if (mod>0){
			stmnt.close();
			return 0;
		} else {
			stmnt.close();
			return -1;
		}
	}

	public boolean aggAnnot(int idCamp, int idLav, int task) throws SQLException{

		PreparedStatement stmnt= null;
		String query = "";
		if(task=='1'){
			query = "INSERT INTO Lavoro (idCampagna, idLavoratore, t1) VALUES (?,?,?)";
		} else {
			query = "INSERT INTO Lavoro (idCampagna, idLavoratore, t2) VALUES (?,?,?)";
		}


		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);;
		stmnt.setInt(2, idLav);;
		stmnt.setBoolean(3, true);
		int ins = stmnt.executeUpdate();

		if (ins>0){
			stmnt.close();
			return true;
		} else {
			stmnt.close();
			return false;
		}
	}
	
	// metodo per accettare le immagini nel task 1
	public boolean accettaImmT1(int idImm, int idLav) throws SQLException{
		PreparedStatement stmnt= null;
		
		// mette a uno la riga relativa al lavoro dell'immagine nella tabella task1
		String query = "UPDATE Task1 SET votata =  1 "
				+ "WHERE idImmagine = ? AND idLavoro = ?";
		stmnt = connection.prepareStatement(query);

		stmnt.setInt(1, idImm);
		stmnt.setInt(2, idLav);

		int mod = stmnt.executeUpdate();

		if (mod != 0){

			stmnt.close();
			
			// aggiorna la tabella lavoro diminuendo le immagini da votare e aumentanto le immagini accettate
			query = "UPDATE Lavoro "
					+ "SET nImmDaVot = nImmDaVot - 1, nImmApp = nImmApp + 1 "
					+ "WHERE idLavoro = ? AND nImmDaVot > 0";
			stmnt = connection.prepareStatement(query);

			stmnt.setInt(1, idLav);

			mod = stmnt.executeUpdate();

			int idCamp = 0;
			int n = 0;
			int k = 0;
			int pos = 0;
			int neg = 0;

			if (mod != 0){

				stmnt.close();
				
				// aggiorna la tabella immaggini mettendo a +1 l'indicatore pos
				query = "UPDATE Immagine "
						+ "SET pos = pos + 1 "
						+ "WHERE idImmagine = ?";
				stmnt = connection.prepareStatement(query);

				stmnt.setInt(1, idImm);

				mod = stmnt.executeUpdate();

				if(mod != 0){
					
					// selezione l'idcampagna relativa a questo lavoro
					query = "SELECT Lavoro.idCampagna "+
							"FROM Lavoro "+
							"WHERE Lavoro.idLavoro =?";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idLav);
					ResultSet rs = stmnt.executeQuery();
					if(rs.next()){
						idCamp = rs.getInt(1);
						stmnt.close();
						rs.close();
					} else {
						stmnt.close();
						rs.close();
						return false;
					}
					
					// salva i parametri k e n della relativa campagna
					query = "SELECT Campagna.k, Campagna.n "+
							"FROM Campagna "+
							"WHERE Campagna.idCampagna =?";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idCamp);
					rs = stmnt.executeQuery();
					if(rs.next()){
						k = rs.getInt(1);
						n = rs.getInt(2);
						stmnt.close();
						rs.close();
					} else {
						stmnt.close();
						rs.close();
						return false;
					}
					
					// selezione i parametri pos e neg dell'immagine
					query = "SELECT Immagine.pos, Immagine.neg "+
							"FROM Immagine "+
							"WHERE Immagine.idImmagine =?";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idImm);
					rs = stmnt.executeQuery();
					if(rs.next()){
						pos = rs.getInt(1);
						neg = rs.getInt(2);
						stmnt.close();
						rs.close();
					} else {
						stmnt.close();
						rs.close();
						return false;
					}
					
					// controllare 
					if (pos-1 < k && pos >= k){
						query = "UPDATE Lavoro "
								+ "SET nDaAnnotare = nDaAnnotare + 1 "
								+ "WHERE idCampagna = ?";
						stmnt = connection.prepareStatement(query);

						stmnt.setInt(1, idCamp);

						mod = stmnt.executeUpdate();

						stmnt.close();
					}
					
					// se i voti sono maggiori di n
					if (pos + neg >= n){
						// allora mette a 1 la riga relativa all'imagine che e' stata votata
						query = "UPDATE Immagine "
								+ "SET vot = 1 "
								+ "WHERE idImmagine = ?";
						stmnt = connection.prepareStatement(query);

						stmnt.setInt(1, idImm);

						mod = stmnt.executeUpdate();

						stmnt.close();
					}
					
					
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	// Metodo per rifiutare le immagini nel task 1
	public boolean rifiutaImmT1(int idImm, int idLav) throws SQLException{
		
		PreparedStatement stmnt= null;

		int pos = 0;
		int neg = 0;
		int n = 0;
		
		// selezione l'immagine ottenendo i parametri pos neg e n
		String query = "SELECT Immagine.pos, Immagine.neg, Campagna.n "+
				"FROM Immagine JOIN Campagna ON Immagine.idCampagna = Campagna.idCampagna "+
				"WHERE Immagine.idImmagine = ?";
		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idImm);
		ResultSet rs = stmnt.executeQuery();
		
		// li salvo in variabili
		if(rs.next()){
			pos = rs.getInt(1);
			neg = rs.getInt(2) + 1;
			n = rs.getInt(3);
		}else{
			return false;	// errore
		}
		rs.close();
		stmnt.close();
		
		// aggiorna la riga del lavoratore con votata a 1
		query = "UPDATE Task1 SET votata =  1 "
				+ "WHERE idImmagine = ? AND idLavoro = ?";
		stmnt = connection.prepareStatement(query);

		stmnt.setInt(1, idImm);
		stmnt.setInt(2, idLav);

		int mod = stmnt.executeUpdate();

		if (mod != 0){

			stmnt.close();
			// modifica la riga in lavoro relativa all'immagine
			// diminuisce di uno le immagini da votare e aumenta di uno le immagini rifiutate
			query = "UPDATE Lavoro "
					+ "SET nImmDaVot = nImmDaVot - 1, nImmRif = nImmRif + 1 "
					+ "WHERE idLavoro = ? AND nImmDaVot > 0";
			stmnt = connection.prepareStatement(query);

			stmnt.setInt(1, idLav);

			mod = stmnt.executeUpdate();

			if (mod != 0){

				stmnt.close();
				
				// modifica la riga immagine con un voto negativo
				query = "UPDATE Immagine "
						+ "SET neg = neg + 1 "
						+ "WHERE idImmagine = ?";
				stmnt = connection.prepareStatement(query);

				stmnt.setInt(1, idImm);

				mod = stmnt.executeUpdate();

				if(mod != 0){
					
					// controlla se ha ricevuto abbastanza voti >n
					if (pos + neg >= n){
						// se si allora mette a uno il flag di votata
						query = "UPDATE Immagine "
								+ "SET vot = 1 "
								+ "WHERE idImmagine = ?";
						stmnt = connection.prepareStatement(query);

						stmnt.setInt(1, idImm);

						mod = stmnt.executeUpdate();

						stmnt.close();
					}
					
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	// metodo per accettare l'annotazione
	public boolean accettaAnnot(int idImm,int idLav, int idCamp) throws SQLException{
		PreparedStatement stmnt= null;
		
		// mette a 1 il flag annotata nella tabella task2 relativo all'immagine del lavoratore
		String query = "UPDATE Task2 "
				+ "JOIN Lavoro ON Task2.idLavoro = Lavoro.idLavoro "
				+ "SET annotata =  1 "
				+ "WHERE Lavoro.idLavoratore = ? AND Task2.idImmagine = ? AND Lavoro.idCampagna = ?";
		stmnt = connection.prepareStatement(query);

		stmnt.setInt(1, idLav);
		stmnt.setInt(2, idImm);
		stmnt.setInt(3, idCamp);

		int mod = stmnt.executeUpdate();

		int m = 0;
		int annot = 0;

		if (mod != 0){

			stmnt.close();
			
			// aggiorna la tabella immagine aggiornando il flag annotato
			query = "UPDATE Immagine "
					+ "SET annot = annot + 1 "
					+ "WHERE idImmagine = ?";
			stmnt = connection.prepareStatement(query);

			stmnt.setInt(1, idImm);

			mod = stmnt.executeUpdate();

			if(mod != 0){
				
				// salva l'indicatore m relativo alla campagna
				query = "SELECT Campagna.m "+
						"FROM Campagna "+
						"WHERE Campagna.idCampagna =?";
				stmnt = connection.prepareStatement(query);
				stmnt.setInt(1, idCamp);
				ResultSet rs = stmnt.executeQuery();
				if(rs.next()){
					m = rs.getInt(1);
					stmnt.close();
					rs.close();
				} else {
					stmnt.close();
					rs.close();
					return false;
				}	
				
				// salva il numero di annotazioni dell'immagine
				query = "SELECT Immagine.annot "+
						"FROM Immagine "+
						"WHERE Immagine.idImmagine =?";
				stmnt = connection.prepareStatement(query);
				stmnt.setInt(1, idImm);
				rs = stmnt.executeQuery();
				if(rs.next()){
					annot = rs.getInt(1);
					stmnt.close();
					rs.close();
				} else {
					stmnt.close();
					rs.close();
					return false;
				}
				
				// se il numero di annotazione dell'immagine e' maggiore di 0
				if (annot >= 0){
					
					// Le immagini da annotare del lavoratore sono -1, e quelle annotate +1
					query = "UPDATE Lavoro "
							+ "SET nDaAnnotare = nDaAnnotare - 1, "
							+ "nAnnotate = nAnnotate + 1 "
							+ "WHERE idCampagna = ? AND idLavoratore = ?";
					stmnt = connection.prepareStatement(query);

					stmnt.setInt(1, idCamp);
					stmnt.setInt(2, idLav);

					mod = stmnt.executeUpdate();

					stmnt.close();
				}
				return true;
			}
			return false;
		}
		return false;
	}

	//METODI GETTERS
	
	// ottieni l'id del lavoro del lavoratore
	public int getIdLav(String user) throws SQLException{
		PreparedStatement stmnt= null;
		String query = "SELECT Lavoratore.idLavoratore "+
				"FROM Lavoratore "+
				"WHERE Lavoratore.username =?";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, user);
		ResultSet rs = stmnt.executeQuery();

		if(rs.next()){
			int idLav = rs.getInt(1);
			stmnt.close();
			rs.close();
			return idLav;
		} else {
			stmnt.close();
			rs.close();
			return -1;
		}
	}
	
	// ottieni l'id della campagna e dei pixel
	public JSONObject getCamp(String nomeCamp) throws SQLException{

		PreparedStatement stmnt= null;
		JSONObject jO = new JSONObject();

		String query = "SELECT Campagna.idCampagna, Campagna.p "+
				"FROM Campagna "+
				"WHERE Campagna.nome =?";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, nomeCamp);
		ResultSet rs = stmnt.executeQuery();

		if(rs.next()){
			jO.put("idCamp", rs.getInt(1));
			jO.put("p", rs.getInt(2));
			stmnt.close();
			rs.close();
			return jO;
		} else {
			stmnt.close();
			rs.close();
			return jO;
		}
	}
	
	// Metodo per ottenere tutte le campagne aperte di un gestore
	public JSONObject getCampagneAperte(String username) throws SQLException{

		JSONObject mainObj = new JSONObject();

		JSONArray jA = new JSONArray();

		PreparedStatement stmnt= null;
		String query =  "SELECT Campagna.idCampagna, Campagna.nome, Campagna.n, Campagna.m, Campagna.k, Campagna.p "+
				"FROM Campagna JOIN Gestore ON Campagna.idGestore=Gestore.idGestore "+
				"WHERE Campagna.chiusa = 0 AND Gestore.username = ?";



		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, username);
		ResultSet rs = stmnt.executeQuery();

		while (rs.next()){

			JSONObject jO = new JSONObject();

			jO.put("idcamp", rs.getInt(1));
			jO.put("nome",  rs.getString(2));
			jO.put("n", rs.getInt(3));
			jO.put("m", rs.getInt(4));
			jO.put("k", rs.getInt(5));
			jO.put("p", rs.getInt(6));

			jA.put(jO);

		}

		mainObj.put("campagneAperte", jA);	// restituisce l'oggetto che tutte le campagne

		return mainObj;
	}
	
	// Metodo per ottenere il path dell'immagine
	public JSONObject getImgCampAperte(int idCamp) throws SQLException{
		
		// creazione di un oggetto json
		JSONObject mainObj = new JSONObject();
	
		PreparedStatement stmnt= null;
		String query = "SELECT Immagine.link "+
				"FROM Immagine JOIN Campagna ON Immagine.idCampagna = Campagna.idCampagna "+
				"WHERE Campagna.idCampagna=?";
		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		ResultSet rs = stmnt.executeQuery();	// esecuzione query

		JSONArray jAImg = new JSONArray();	// creo l'array in formato json

		while (rs.next()){
			jAImg.put(rs.getString(1));	// salvo il path dell'immagine
		}

		mainObj.put("immAp", jAImg);	// lo aggiungo all'oggetto json

		return mainObj;	// restituisco l'oggetto json
	}
	
	// metodo per ottenere info sull'imagine relativa ad una campagna
	public JSONObject getImgCampAvv(int idCamp) throws SQLException{
		JSONObject mainObj = new JSONObject();
		PreparedStatement stmnt= null;
		String query = "SELECT Immagine.idImmagine, Immagine.link, Immagine.pos, Immagine.neg, Immagine.annot "+
				"FROM Immagine JOIN Campagna ON Immagine.idCampagna = Campagna.idCampagna "+
				"WHERE Campagna.idCampagna=?";
		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		ResultSet rs = stmnt.executeQuery();

		JSONArray jAImg = new JSONArray();
		
		// metto gli oggetti contententi le info nell'array json
		while (rs.next()){
			JSONObject jO = new JSONObject();

			jO.put("idImm", rs.getInt(1));
			jO.put("path", rs.getString(2));
			jO.put("pos", rs.getInt(3));
			jO.put("neg", rs.getInt(4));
			jO.put("annot", rs.getInt(5));

			jAImg.put(jO);
		}
		mainObj.put("immAvv", jAImg);	// salvo l'array nell'oggetto json

		return mainObj;	// ritorno l'oggetto json
	}
	
	// ottieni immagini task 1
	public JSONObject getImgTask1(int idCamp, String username) throws SQLException{
		JSONObject mainObj = new JSONObject();
		PreparedStatement stmnt= null;
		
		// seleziona le immagini relative alla campagna del lavoratore che non sono ancora state votate
		String query = "SELECT Immagine.idImmagine, Immagine.link, Immagine.pos + Immagine.neg as tot, Task1.votata, Lavoro.idLavoro "
				+ "FROM ((Immagine JOIN Task1 ON Immagine.idImmagine = Task1.idImmagine) "
				+ "JOIN Lavoro ON Task1.idLavoro = Lavoro.idLavoro) JOIN Lavoratore ON Lavoro.idLavoratore = Lavoratore.idLavoratore "
				+ "WHERE Task1.votata = 0 AND Lavoro.idCampagna = ? AND Lavoratore.username = ?"
				+ "ORDER BY tot ASC; ";
		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		stmnt.setString(2, username);
		ResultSet rs = stmnt.executeQuery();

		JSONArray jAImg = new JSONArray();
		
		// salva le immagini e gli id
		while (rs.next()){
			JSONObject jO = new JSONObject();

			jO.put("idImm", rs.getInt(1));
			jO.put("path", rs.getString(2));
			jO.put("tot", rs.getInt(3));
			jO.put("votata", rs.getInt(4));
			jO.put("idLav", rs.getInt(5));

			jAImg.put(jO);
		}

		mainObj.put("immTask1", jAImg);

		return mainObj;
	}
	
	// ottieni immagini del task2
	public JSONObject getImgTask2(int idCamp, String username) throws SQLException{
		JSONObject mainObj = new JSONObject();
		PreparedStatement stmnt= null;
		
		// seleziona le immagini relative alla campagna a cui il lavoratore e' abilitato che non sono ancora state votate
		String query = "SELECT Immagine.idImmagine, Immagine.link, Immagine.annot, Task2.annotata, Lavoro.idLavoro "
				+ "FROM ((Immagine JOIN Task2 ON Immagine.idImmagine = Task2.idImmagine) "
				+ "JOIN Lavoro ON Task2.idLavoro = Lavoro.idLavoro) JOIN Lavoratore ON Lavoro.idLavoratore = Lavoratore.idLavoratore "
				+ "WHERE Task2.annotata = 0 AND Lavoro.idCampagna = ? AND Lavoratore.username = ? "
				+ "ORDER BY Immagine.annot ASC";
		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		stmnt.setString(2, username);
		ResultSet rs = stmnt.executeQuery();

		JSONArray jAImg = new JSONArray();
		
		// salva in json le immagini e i vari id
		while (rs.next()){
			JSONObject jO = new JSONObject();

			jO.put("idImm", rs.getInt(1));
			jO.put("path", rs.getString(2));
			jO.put("tot", rs.getInt(3));
			jO.put("votata", rs.getInt(4));
			jO.put("idLav", rs.getInt(5));

			jAImg.put(jO);
		}

		mainObj.put("immTask2", jAImg);

		return mainObj;
	}
	
	// Ottiene i path delle immagini annotate 
	public JSONObject getAnnotaz(int idImm) throws SQLException{
		
		JSONObject mainObj = new JSONObject();
		PreparedStatement stmnt= null;
		
		// Seleziona i link delle immagini annotate di una specifica immagine
		String query = "SELECT Annotazione.link "+
				"FROM Annotazione "+
				"WHERE Annotazione.idImmagine=?";
		
		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idImm);
		ResultSet rs = stmnt.executeQuery();

		JSONArray jAImg = new JSONArray();

		while (rs.next()){

			JSONObject jO = new JSONObject();

			jO.put("path", rs.getString(1));	// salva il path dell'immagine annotata nel json

			jAImg.put(jO);
		}
		mainObj.put("annotImm", jAImg);

		return mainObj;
	}

	// Metodo per ottenere le campagne gia avviate di un determinato gestore
	public JSONObject getCampagneAvviate(String username) throws SQLException{

		JSONObject mainObj = new JSONObject();

		JSONArray jA = new JSONArray();

		PreparedStatement stmnt= null;
		
		// Selezione le campagne gia avviate di un determinato gestore
		String query = "SELECT Campagna.idCampagna, Campagna.nome, Campagna.n, Campagna.m, Campagna.k, Campagna.p "+
				"FROM Campagna JOIN Gestore ON Campagna.idGestore=Gestore.idGestore "+
				"WHERE Campagna.chiusa = 1 AND Gestore.username = ?";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, username);
		ResultSet rs = stmnt.executeQuery();

		int idC = 0;
		

		while (rs.next()){
			
			idC = rs.getInt(1);	// salva l'id della campagna
						
			PreparedStatement stmnt2= null;
			
			// Ottieni info e parametri relative ad ogni immagine di ogni campagna
			String query2 = "SELECT Immagine.idImmagine, Immagine.vot, Campagna.k, Immagine.pos, Campagna.m, Immagine.annot "
					+ "FROM Campagna "
					+ "JOIN Immagine ON Campagna.idCampagna = Immagine.idCampagna "
					+ "WHERE Campagna.idCampagna = ? ";
			stmnt2 = connection.prepareStatement(query2);
			stmnt2.setInt(1, idC);			

			ResultSet rs2 = stmnt2.executeQuery();
			
			boolean chiusa = true;
			
			// controlla anche che la campagna sia finita
			while(rs2.next()){
				
				if(rs2.getInt(2) == 0 || 
						// pos >= k 	annot>=m
					(rs2.getInt(4) >= rs2.getInt(3) && rs2.getInt(6) < rs2.getInt(5))){
					chiusa = false;
				} 
				
			}
			
			rs2.close();
			stmnt2.close();

			JSONObject jO = new JSONObject();

			jO.put("idcamp", rs.getInt(1));
			jO.put("nome",  rs.getString(2));
			jO.put("n", rs.getInt(3));
			jO.put("m", rs.getInt(4));
			jO.put("k", rs.getInt(5));
			jO.put("p", rs.getInt(6));
			// mette finita a 0 o 1
			if(chiusa){
				jO.put("finita", 1);
			} else {
				jO.put("finita", 0);
			}

			jA.put(jO);

		}

		mainObj.put("campagneAvviate", jA);	// mette l'array nell'oggetto

		return mainObj;
	}
	
	// Ottiene le campagne avviate di cui puo svolgere i task
	public JSONObject getTask(String username) throws SQLException{
		JSONObject mainObj = new JSONObject();
		JSONArray jA = new JSONArray();

		PreparedStatement stmnt= null;
		
		// seleziona tutte le campagne a cui e' abilitato e sono avviate
		String query = "SELECT Lavoro.t1, Lavoro.t2, Campagna.nome, Lavoro.nImmApp, Lavoro.nImmRif, " +
				"Lavoro.nImmDaVot, Lavoro.nAnnotate, Lavoro.nDaAnnotare "+
				"FROM Campagna JOIN Lavoro ON Campagna.idCampagna=Lavoro.idCampagna "+				
				"JOIN Lavoratore ON Lavoro.idLavoratore=Lavoratore.idLavoratore "+
				"WHERE Lavoratore.username = ? AND Campagna.chiusa = 1";
		stmnt = connection.prepareStatement(query);
		stmnt.setString(1, username);
		ResultSet rs = stmnt.executeQuery();

		while (rs.next()){

			JSONObject jO = new JSONObject();

			jO.put("t1", rs.getBoolean(1));
			jO.put("t2", rs.getBoolean(2));
			jO.put("nomeCamp", rs.getString(3));
			jO.put("nImmApp", rs.getInt(4));
			jO.put("nImmRif", rs.getInt(5));
			jO.put("nImmDaVot", rs.getInt(6));
			jO.put("nAnnotate", rs.getInt(7));
			jO.put("nDaAnnotare", rs.getInt(8));

			jA.put(jO);

		}

		mainObj.put("taskAperti", jA);

		return mainObj;
	}
	
	// Restituisce i lavoratori abilitati alla campagna e non abilitati a nessun task della campagna
	public JSONObject getLavoratori(int idCamp) throws SQLException{
		JSONObject mainObj = new JSONObject();
		JSONArray jA = new JSONArray();

		PreparedStatement stmnt= null;

		String query =  "SELECT  Lavoratore.idLavoratore, Lavoratore.username, Lavoro.t1, Lavoro.t2, Lavoro.idCampagna "
				+ "FROM Lavoratore LEFT  OUTER JOIN Lavoro ON Lavoratore.idLavoratore=Lavoro.idLavoratore "
				+ "WHERE Lavoro.idCampagna = ? ";

		stmnt = connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		ResultSet rs = stmnt.executeQuery();

		while (rs.next()){

			JSONObject jO = new JSONObject();

			jO.put("idLav", rs.getInt(1));
			jO.put("username", rs.getString(2));
			jO.put("t1", rs.getInt(3));
			jO.put("t2", rs.getInt(4));

			jA.put(jO);
		}

		stmnt.close();
		rs.close();
		
		// trova tutti i lavoratori registrati nel db
		query =  "SELECT  Lavoratore.idLavoratore, Lavoratore.username "
				+ "FROM Lavoratore  ";

		stmnt = connection.prepareStatement(query);
		rs = stmnt.executeQuery();

		while (rs.next()){

			JSONObject jO = new JSONObject();
			boolean found = false;
			int idL =rs.getInt(1);
			
			// cerca se il lavorate e' gia abilitato alla campagna
			for (int i = 0; i < jA.length(); i++){
				JSONObject jsonLavoratore = jA.getJSONObject(i);
				int idLav = jsonLavoratore.getInt("idLav");
				if (idL == idLav){
					found = true;
				}
			}
			
			// se non e' abilitato lo aggiunge al json array e metti i t 1 e 2 a 0
			if (!found){
				jO.put("idLav", rs.getInt(1));
				jO.put("username", rs.getString(2));
				jO.put("t1", 0);
				jO.put("t2", 0);
				jA.put(jO);
			}
		}

		stmnt.close();
		rs.close();

		mainObj.put("lavT", jA);

		return mainObj;
	}

	//METODI MODIFICA
	
	// Modifica la campagna
	public int modCampagna(String nomeNew, int n, int m, int k, int p, String nomeOld) throws SQLException{

		PreparedStatement stmnt= null;

		ResultSet rs = null;

		String query;
		
		// Se ha modificato il nome, ma esiste gia nella tabella
		if(!(nomeNew.equals(nomeOld))){
			query = "SELECT idCampagna FROM Campagna WHERE nome = ?";
			stmnt= connection.prepareStatement(query);
			stmnt.setString(1, nomeNew);
			rs = stmnt.executeQuery();

			if(rs.next()){
				return -2;
			}
		}
		
		// Cerca l'id del vecchio nome
		query = "SELECT idCampagna FROM Campagna WHERE nome = ?";
		stmnt= connection.prepareStatement(query);
		stmnt.setString(1, nomeOld);

		rs = stmnt.executeQuery();
		
		// se lo trova update con i nuovi dati
		if(rs.next()){
			int id = rs.getInt(1);

			query = "UPDATE Campagna SET nome =  ?, n = ?, m = ?, k = ?, p = ?"
					+ " WHERE idCampagna = ?";
			stmnt = connection.prepareStatement(query);

			stmnt.setString(1, nomeNew);
			stmnt.setInt(2, n);
			stmnt.setInt(3, m);
			stmnt.setInt(4, k);
			stmnt.setInt(5, p);
			stmnt.setInt(6, id);

			int mod = stmnt.executeUpdate();

			if (mod>0){
				stmnt.close();
				rs.close();
				return 0;
			} else {
				stmnt.close();
				rs.close();
				return -1;
			}
		} else {
			stmnt.close();
			rs.close();
			return -1;
		}
	}
	
	// Abilita tot lavoratori ad un task della campagna
	public int abilitaLav(int idCamp, int task, String lavJson) throws SQLException{

		PreparedStatement stmnt= null;

		ResultSet rs = null;

		String query;

		JSONArray jsonArray = new JSONArray(lavJson);

		if(jsonArray.length() == 0){
			return -1;
		}


		for (int i = 0; i < jsonArray.length(); i++) {  
			JSONObject jsonLavoratore = jsonArray.getJSONObject(i);
			int idLav = jsonLavoratore.getInt("idL");

			int isAb = jsonLavoratore.getInt("ab");		// e' abilitato o meno a uno dei due task
			
			// se e' gia inserito nella tabella lavoro, quindi abilitato alla campagna in t1 o in t2
			query = "SELECT idLavoro, t1, t2 FROM Lavoro "
					+ "WHERE idCampagna = ? AND idLavoratore = ?";
			stmnt= connection.prepareStatement(query);
			stmnt.setInt(1, idCamp);
			stmnt.setInt(2, idLav);

			rs = stmnt.executeQuery();

			if(rs.next()){

				int idLavoro = rs.getInt(1);
				int t1 = rs.getInt(2);
				int t2 = rs.getInt(3);
				
				// se task 1
				if(task == 1){
					stmnt.close();
					rs.close();
					
					// se e' stato il checkbox diverso dal valore del t1, cambialo in abilitato o disabilitato
					if(isAb != t1){
						query = "UPDATE Lavoro SET t1 = ?"
								+ " WHERE idLavoro = ?";
						stmnt= connection.prepareStatement(query);
						stmnt.setInt(1, isAb);
						stmnt.setInt(2, idLavoro);
						stmnt.executeUpdate();
						stmnt.close();
						rs.close();
					}
				} else if (task == 2){ // se task 2 fa le stesse cose
					stmnt.close();
					rs.close();

					if(isAb != t2){
						query = "UPDATE Lavoro SET t2 = ?"
								+ " WHERE idLavoro = ?";
						stmnt = connection.prepareStatement(query);
						stmnt.setInt(1, isAb);
						stmnt.setInt(2, idLavoro);
						stmnt.executeUpdate();
						stmnt.close();
					}
				}
			} else {	// non e' mai stato abilitato nella campagna
				stmnt.close();
				rs.close();
				
				// se deve essere abilitato ad un task
				if (isAb == 1){
					query = "INSERT INTO Lavoro (idCampagna, idLavoratore, t1, t2, nImmApp, nImmRif, nImmDaVot, nAnnotate, nDaAnnotare) "
							+ "VALUES (?,?,?,?,?,?,?,?,?)";

					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idCamp);
					stmnt.setInt(2, idLav);
					stmnt.setInt(5, 0);
					stmnt.setInt(6, 0);
					stmnt.setInt(7, 0);		
					stmnt.setInt(8, 0);
					stmnt.setInt(9, 0);
					if (task == 1){	// nel task 1
						stmnt.setInt(3, 1);
						stmnt.setInt(4, 0);
					} else {	// nel task 2
						stmnt.setInt(3, 0);
						stmnt.setInt(4, 1);
					}
					stmnt.executeUpdate();

				}
				rs.close();
				stmnt.close();
			}
		}
		
		// Elimina quelli che non sono abilitati da nessuna aprte
		query = "DELETE FROM Lavoro WHERE t1 = 0 AND t2 = 0";
		stmnt= connection.prepareStatement(query);
		stmnt.executeUpdate();
		stmnt.close();
		return 0;		
	}
	
	// Metodo per eliminare l'immagine
	public boolean eliminaImmagine(String path) throws SQLException{
		PreparedStatement stmnt= null;

		ResultSet rs = null;

		String query;

		int ok = 0;

		query = "DELETE FROM Immagine WHERE link = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setString(1, path);
		ok = stmnt.executeUpdate();

		if(ok >0){
			return true;
		} else {
			return false;
		}
	}
	
	// Metodo per avviare la campagna
	public int avviaCampagna(String nome) throws SQLException{
		
		PreparedStatement stmnt= null;
		ResultSet rs = null;
		String query;
		
		// cerca l'id della campagna dal suo nome
		query = "SELECT DISTINCT Immagine.idCampagna " +
				"FROM Immagine JOIN Campagna ON Immagine.idCampagna=Campagna.idCampagna "+
				"WHERE Campagna.nome = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setString(1, nome);
		rs = stmnt.executeQuery();
		
		// se esiste una campagna con quel nome
		if(rs.next()){

			int id = rs.getInt(1);
			stmnt.close();
			rs.close();
			
			query = "SELECT n, m, k, p " +
					"FROM Campagna "+
					"WHERE idCampagna = ?";
			stmnt= connection.prepareStatement(query);
			stmnt.setInt(1, id);
			rs = stmnt.executeQuery();

			int n = 0;
			int m = 0;
			int k = 0;
			int p = 0;

			if(rs.next()){
				n = rs.getInt(1);
				m = rs.getInt(2);
				k = rs.getInt(3);
				p = rs.getInt(4);

				if (n==0 || m==0 || k==0 || p==0){  // gli indicatori devono essere maggiore di 0
					return -4;
				}
			}


			query = "SELECT COUNT(*) " + 
					"FROM Lavoro " + 
					"WHERE idCampagna = ? AND t1 = 1";
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1, id);
			rs = stmnt.executeQuery();
			
			// non vi sono sufficienti lavoratori per svolgere il task1
			if(rs.next()){
				int totLavT1 = rs.getInt(1);
				if (n > totLavT1 || k > totLavT1){
					return -5;
				}
			}

			query = "SELECT COUNT(*) " + 
					"FROM Lavoro " + 
					"WHERE idCampagna = ? AND t2 = 1";
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1, id);
			rs = stmnt.executeQuery();
			
			// insufficienti lavoratori per svolgere il task 2
			if(rs.next()){
				int totLavT2 = rs.getInt(1);
				if (m > totLavT2){
					return -6;
				}
			}
			
			// avvia campagna
			query = "UPDATE Campagna SET chiusa = 1"
					+ " WHERE idCampagna = ?";
			stmnt = connection.prepareStatement(query);

			stmnt.setInt(1, id);

			int mod = stmnt.executeUpdate();

			if (mod>0){
				stmnt.close();
				rs.close();
				
				// numero di immagini nella campagna
				query = "SELECT COUNT(*) AS total FROM Immagine"
						+ " WHERE idCampagna = ?";
				stmnt = connection.prepareStatement(query);
				stmnt.setInt(1, id);
				rs = stmnt.executeQuery();

				if(rs.next()){
					int totImm = rs.getInt("total");
					stmnt.close();
					rs.close();
					
					// modifica la tabella lavoro con il numero di immagini da votare
					query = "UPDATE Lavoro SET nImmDaVot = ?"
							+ " WHERE idCampagna = ?";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, totImm);
					stmnt.setInt(2, id);
					stmnt.executeUpdate();
					stmnt.close();
					rs.close();
					return 0;		// andato a buon fine
				}
				return -3;		// errore improbabile
			} else {
				stmnt.close();
				rs.close();
				return -1;	// non trova la campagna, ma non succedera mai
			}
		} else {
			return -2; 	// non trova immagini con quella campagna
		}
	}
	
	// Inserisci le righe nella tabella task 1
	public int setTask1(String user, int idCamp) throws SQLException{
		
		PreparedStatement stmnt= null;
		ResultSet rs = null;

		String query;

		//trova idLavoro dall'id campagna e del lavoratore
		query = "SELECT Lavoro.idLavoro " +
				"FROM progetto.Lavoro JOIN progetto.Lavoratore ON Lavoro.idLavoratore = Lavoratore.idLavoratore "+
				"WHERE idCampagna = ? and Lavoratore.username = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		stmnt.setString(2, user);
		rs = stmnt.executeQuery();

		int idLavoro = 0;

		if(rs.next()){
			idLavoro = rs.getInt(1);		// salva l'id del lavoro
			stmnt.close();
			rs.close();
		} else {
			//errore non trovato lavoro
			return -1;
		}
		
		//se trovato
		if(idLavoro != 0){

			//controlla se task già iniziato
			query = "SELECT Task1.idTask1 " +
					"FROM Task1 "+
					"WHERE Task1.idLavoro = ?";

			stmnt= connection.prepareStatement(query);
			stmnt.setInt(1, idLavoro);
			rs = stmnt.executeQuery();

			if(rs.next()){
				//gia iniziato
				stmnt.close();
				rs.close();
				return 0;
			} else {
				//non iniziato
				stmnt.close();
				rs.close();

				//setta task1
				
				// prende l'id dell'immagini della campagna
				query = "SELECT Immagine.idImmagine " +
						"FROM Immagine " +
						"WHERE Immagine.idCampagna = ?";

				stmnt= connection.prepareStatement(query);
				stmnt.setInt(1, idCamp);
				rs = stmnt.executeQuery();
				
				// inserisce tutte gli id delle immagini relativo al lavoro con votato a 0
				while(rs.next()){
					query = "INSERT INTO Task1 (idLavoro, idImmagine, votata) VALUES (?,?,?)";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idLavoro);
					stmnt.setInt(2, rs.getInt(1));
					stmnt.setInt(3, 0);
					stmnt.executeUpdate();
				}
				rs.close();
				stmnt.close();
				return 0;
			}
		}
		return -1;
	}
	
	// inserisce le righe nella tabella task 2
	public int setTask2(String user, int idCamp) throws SQLException{
		
		PreparedStatement stmnt= null;
		ResultSet rs = null;
		String query;

		int k = 0;
		
		// seleziona l'indicatore k della campagna
		query = "SELECT k " +
				"FROM Campagna "+
				"WHERE Campagna.idCampagna = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		rs = stmnt.executeQuery();
		if(rs.next()){
			k = rs.getInt(1);		// salvo indicatore k
			stmnt.close();
			rs.close();
		} else {
			stmnt.close();
			rs.close();
			return -1;
		}

		//trova idLavoro relativo ad alla campagna associata ad un lavoratore
		query = "SELECT Lavoro.idLavoro " +
				"FROM progetto.Lavoro JOIN progetto.Lavoratore ON Lavoro.idLavoratore = Lavoratore.idLavoratore "+
				"WHERE idCampagna = ? and Lavoratore.username = ?";

		stmnt= connection.prepareStatement(query);
		stmnt.setInt(1, idCamp);
		stmnt.setString(2, user);
		rs = stmnt.executeQuery();

		int idLavoro = 0;

		if(rs.next()){
			idLavoro = rs.getInt(1);
			stmnt.close();
			rs.close();
		} else {
			//errore non trovato lavoro
			stmnt.close();
			rs.close();
			return -1;
		}
		
		//se trovato
		if(idLavoro != 0){

			//controlla se task già iniziato
			query = "SELECT Task2.idTask2 " +
					"FROM Task2 "+
					"WHERE Task2.idLavoro = ?";

			stmnt= connection.prepareStatement(query);
			stmnt.setInt(1, idLavoro);
			rs = stmnt.executeQuery();

			if(rs.next()){
				//gia iniziato
				stmnt.close();
				rs.close();

				//setta task2
				
				// selezione le immagini della campagna con voti positivi maggiori di k
				query = "SELECT Immagine.idImmagine " +
						"FROM Immagine " +
						"WHERE Immagine.idCampagna = ? AND Immagine.pos > ?";

				stmnt= connection.prepareStatement(query);
				stmnt.setInt(1, idCamp);
				stmnt.setInt(2, k-1);
				rs = stmnt.executeQuery();
				
				// popolamento della tabella task2 di quelle mancanti
				while(rs.next()){

					query = "INSERT INTO Task2 (idLavoro, idImmagine, annotata) "
							+ "SELECT * FROM (SELECT ?, ?, ?) AS tmp "
							+ "WHERE NOT EXISTS "
							+ "(SELECT idLavoro, idImmagine FROM Task2 WHERE idLavoro = ? AND idImmagine = ?)";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idLavoro);
					stmnt.setInt(2, rs.getInt(1));
					stmnt.setInt(3, 0);
					stmnt.setInt(4, idLavoro);
					stmnt.setInt(5, rs.getInt(1));
					stmnt.executeUpdate();
				}
				rs.close();
				stmnt.close();
				return 0;
			} else {
				//non iniziato
				stmnt.close();
				rs.close();

				//setta task2
				// selezione le immagini della campagna con voti positivi maggiori di k
				query = "SELECT Immagine.idImmagine " +
						"FROM Immagine " +
						"WHERE Immagine.idCampagna = ? AND Immagine.pos > ?";

				stmnt= connection.prepareStatement(query);
				stmnt.setInt(1, idCamp);
				stmnt.setInt(2, k-1);
				rs = stmnt.executeQuery();
				
				// popolamento tabella task2
				while(rs.next()){

					query = "INSERT INTO Task2 (idLavoro, idImmagine, annotata) VALUES (?,?,?)";
					stmnt = connection.prepareStatement(query);
					stmnt.setInt(1, idLavoro);
					stmnt.setInt(2, rs.getInt(1));
					stmnt.setInt(3, 0);
					stmnt.executeUpdate();
				}
				rs.close();
				stmnt.close();
				return 0;
			}
		}
		return -1;
	}


	//METODI CANCELLA
	
	// Cancella la campagna
	public boolean deleteCampagna(String nomeCamp) throws SQLException{
		PreparedStatement stmnt= null;
		
		String query = "DELETE FROM Campagna WHERE nome = ?";
		stmnt= connection.prepareStatement(query);
		stmnt.setString(1, nomeCamp);

		int del = stmnt.executeUpdate();	//esegue la query

		if (del>0){
			stmnt.close();
			return true;
		} else {
			stmnt.close();
			return false;
		}
	}

	






}
