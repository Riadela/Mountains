package servlets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import database.DbManager;

/**
 * Servlet implementation class UploadAnnot
 */
@WebServlet("/UploadAnnot")
public class UploadAnnot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadAnnot() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			dbMan = new DbManager();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		super.destroy();
		if(dbMan!=null){
			try {
				dbMan.destroy();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("plain/text"); 
		PrintWriter out = response.getWriter();
		
		// salva i vari dati in variabili
		String username = request.getParameter("username");
		String idcamp = request.getParameter("idcamp");
		String dataImg = request.getParameter("dataImg");
		int imgId = Integer.parseInt(request.getParameter("idimg"));
		int annot = Integer.parseInt(request.getParameter("annot"));
		dataImg = dataImg.replace(" ", "+");
		
		if(annot != 1){	// non e' stata annotata
			out.write("-1");
			return;
		}
		
		int idLav = 0;
		
		try {
			idLav = dbMan.getIdLav(username);	// id del lavoro dal lavoratore
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 


		int idCamp = Integer.parseInt(idcamp);
		
		// creazione dell'oggeto con il path
		File cartAnnot = new File("/Users/Riad/Documents/workspace/progettoTIW/WebContent/annot/" + idcamp);
		
		// se esiste gia la cartella
		if (!cartAnnot.exists()) {

			cartAnnot.mkdir();		// crea cartella
		} 

		int success1 = -1;

		boolean success2 = false;

		try {

			int newName = -1;

			try {
				newName = dbMan.aggAnnot1(imgId, idLav);	// chiamata db per ottenere id della nuova riga
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// crea oggetto col path con l'id della nuova riga
			String path = "/Users/Riad/Documents/workspace/progettoTIW/WebContent/annot/" + idcamp + "/" + newName + ".png";
			
			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(dataImg);	// decodifica la stringa dataImg
			InputStream is = new ByteArrayInputStream(decodedBytes);			// mette l'immagine nello stream inpute
			File outputfile = new File(path);									// crea l'oggetto file col percorso
			OutputStream os = new FileOutputStream(outputfile);					// crea lo stream in output del percorso della cartella
			IOUtils.copy(is, os);												// copia l'immagine nella cartella
			
			// chiude i due stream
			is.close();
			os.close();

			try {
				success1 = dbMan.aggAnnot2(newName, idcamp + "/" + newName + ".png");	// aggiorna la riga di db con il link
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			success2 = dbMan.accettaAnnot(imgId, idLav, idCamp);	// accetta l'annotazione

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
