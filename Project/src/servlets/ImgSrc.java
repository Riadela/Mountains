package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import database.DbManager;

/**
 * Servlet implementation class ImgSrc
 */
@WebServlet("/ImgSrc")
public class ImgSrc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgSrc() {
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
			// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub
		
		String pathDaDb = request.getParameter("path");	// salva il path dell'immagine
		String percorso;
		
		// In base a se sta cercando le immagini annotate o immagini normali
		if(request.getParameter("annot")!=null){
			percorso = "/Users/Riad/Documents/workspace/progettoTIW/WebContent/annot/";
		} else {
			percorso = "/Users/Riad/Documents/workspace/progettoTIW/WebContent/img/";
		}
		Path filePath = Paths.get(percorso, pathDaDb);	// crea l'oggetto path con il percorso al file
		
		File immagine = filePath.toFile();	//ottieni il file del path nell'oggetto
		
		if(immagine.exists()){	// se esiste il file
			
			response.setContentType(Files.probeContentType(filePath)); // setting the type of the file
			InputStream is = new FileInputStream(immagine);	// mette l'oggetto in uno stream di input
			OutputStream os = response.getOutputStream();	// crea l'oggetto per uno stream in output
			IOUtils.copy(is, os);	// mette lo stream di input in quello di output
			
			// chiude i due strem
			is.close();
			os.close();
			
		} else {	// se non esiste il file
			response.sendError(404);
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
