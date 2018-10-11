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

import org.json.JSONObject;

import database.DbManager;

/**
 * Servlet implementation class EliminaCampagna
 */
@WebServlet("/EliminaCampagna")
public class EliminaCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EliminaCampagna() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("plain/text"); 

		PrintWriter out = response.getWriter();
		
		// Salvo in variabili i parametri
		String nome = request.getParameter("nome");
		int chiusa = Integer.parseInt(request.getParameter("chiusa"));		// per eliminare anche cartella annotazioni


		JSONObject infoCamp = new JSONObject();
		int idCamp = 0;

		boolean ret = false;	

		try {
			
			// ottiene id della campagna
			infoCamp = dbMan.getCamp(nome);		
			idCamp = infoCamp.getInt("idCamp");

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ret = dbMan.deleteCampagna(nome);	// elimina la campagna con quell'id
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creao oggetto con quel percorso
		File f1 = new File("/Users/Riad/Documents/workspace/progettoTIW/WebContent/img/" + idCamp);
		removeDirectory(f1);	// chiamata metodo per eliminare directory
		
		// se e' chiusa elimian anche la cartella dentro annot
		if(chiusa == 1){
			File f2 = new File("/Users/Riad/Documents/workspace/progettoTIW/WebContent/annot/" + idCamp);
			removeDirectory(f2);
		}
		
		// se l'elimina e' andato a buon fine
		if(ret){
			out.write("0");
		} else {
			out.write("-1");
		}

	}
	
	public static void removeDirectory(File dir) {
	    if (dir.isDirectory()) {
	        File[] files = dir.listFiles();
	        if (files != null && files.length > 0) {
	            for (File aFile : files) {
	                removeDirectory(aFile);
	            }
	        }
	        dir.delete();
	    } else {
	        dir.delete();
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
