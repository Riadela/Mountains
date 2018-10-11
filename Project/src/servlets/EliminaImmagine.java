package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbManager;

/**
 * Servlet implementation class EliminaImmagine
 */
@WebServlet("/EliminaImmagine")
public class EliminaImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EliminaImmagine() {
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
		String path = request.getParameter("path");
		
		try {
			dbMan.eliminaImmagine(path);		// elimina la riga immagine dal db
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// crea oggetto con il percorso
		File deleteFile = new File("/Users/Riad/Documents/workspace/progettoTIW/WebContent/img/" + path) ;
		
		// se esiste il file
		if( deleteFile.exists() ){
			deleteFile.delete() ;	// elimina il file
			out.write("0");
		} else {	// se non esiste il file da eliminare
			out.write("-1");
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
