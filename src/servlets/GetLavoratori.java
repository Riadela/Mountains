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
 * Servlet implementation class GetLavoratori
 */
@WebServlet("/GetLavoratori")
public class GetLavoratori extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLavoratori() {
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
		
		JSONObject lavoratori = new JSONObject();
		
		// Nome della campagna
		int idCamp = Integer.parseInt(request.getParameter("idcamp"));

		PrintWriter out = response.getWriter();
		try {
			lavoratori = dbMan.getLavoratori(idCamp);	//ottiene oggetto json da metodo db
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		out.write(lavoratori.toString());	// resistuisce oggetto json
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
