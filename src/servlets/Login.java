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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DbManager dbMan = null;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			dbMan = new DbManager();  // connette al db
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void destroy() {
		super.destroy();
		if(dbMan!=null){
			try {
				dbMan.destroy();  // disconnette dal db
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
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Codifica e toglie formattazione al testo
		request.setCharacterEncoding("UTF-8");
		response.setContentType("plain/text"); 
		PrintWriter out = response.getWriter();
		
		// Salva i dati passati
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean gest = Boolean.parseBoolean(request.getParameter("gestore"));
		
		HttpSession session = request.getSession();	// Crea oggetto sessione
		
		try {
			if (dbMan.login(username, password, gest)){	// se andato a buon fine
				session.setAttribute("username", username);		// salva nella sessione l'username
				if (gest){	
					out.write("1");	// Login del gestore
				} else {
					out.write("2"); // Login del lavoratore
				}
			} else { // Credenziali sbagliate
				out.write("3");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}

	}
}












