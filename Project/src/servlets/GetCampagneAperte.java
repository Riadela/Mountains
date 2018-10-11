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
import javax.servlet.http.HttpSession;

import database.DbManager;

import org.json.*;

/**
 * Servlet implementation class getCampagne
 */
@WebServlet("/GetCampagneAperte")
public class GetCampagneAperte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DbManager dbMan = null;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCampagneAperte() {
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
		
		response.setContentType("application/json");  
		HttpSession session = request.getSession();
		
		JSONObject campAperte = new JSONObject();
		
		String username = (String) session.getAttribute("username");
		
		PrintWriter out = response.getWriter();
		
		try {
			campAperte = dbMan.getCampagneAperte(username);		// riceve un oggetto json con tutte le campagne aperte
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}	
				
		out.write(campAperte.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
