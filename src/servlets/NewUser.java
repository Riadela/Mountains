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

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	
	private DbManager dbMan = null;
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			dbMan = new DbManager();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8"); // cambia codifica
		
		response.setContentType("plain/text"); // rimuovere formattazioni

		PrintWriter out = response.getWriter(); // istanzia oggetto out
		
		// Salva i dati passati dal jsp
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean gest = Boolean.parseBoolean(request.getParameter("gestore"));
		
		HttpSession session = request.getSession();

		try {
			// Se e' un gestore
			if (gest){
				if (dbMan.nuovoGestore(username, password)){ // Andato a buon fine
					//session.setAttribute("username", username);   // Salva nella sessione l'username
					out.write("1"); 			// Restituisce 1
				} else { 
					out.write("2");  // Non e' andato a buon fine
				}
			} else { // Non e' un gestore
				if (dbMan.nuovoLavoratore(username, password)){
					//session.setAttribute("username", username);
					out.write("1");
				} else {
					out.write("2");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}

	}

}

















