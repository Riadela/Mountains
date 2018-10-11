package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import database.DbManager;

/**
 * Servlet implementation class GetImg
 */
@WebServlet("/GetImg")
public class GetImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImg() {
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
		response.setContentType("application/json");  
		
		// Creazione dell'oggetto JSON
		JSONObject imgCamp = new JSONObject();
			
		PrintWriter out = response.getWriter();
		
		// Salvataggio dei dati ricevuti
		int idCamp = Integer.parseInt(request.getParameter("idcamp"));
		int t = Integer.parseInt(request.getParameter("t"));
		
		try {
			if (t == 1){ // se t e' uguale ad uno allora img delle campagne aperte
				imgCamp = dbMan.getImgCampAperte(idCamp);
			} else if (t==2){	// se t e' uguale a 2 di quelle avviate
				imgCamp = dbMan.getImgCampAvv(idCamp);
			}
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}	
				
		out.write(imgCamp.toString());	// ritorna il path dell'immagine
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
