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

import database.DbManager;

/**
 * Servlet implementation class AvviaCampagna
 */
@WebServlet("/AvviaCampagna")
public class AvviaCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AvviaCampagna() {
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

		String nome = request.getParameter("nome");	// salva il nome della campagna

		int ret;
		
		try {
			
			ret = dbMan.avviaCampagna(nome);	// avvia la campagna dal metodo di db
			
			if (ret == -2){
				//err nessuna immagine
				out.write("-2");
			} else if(ret == -1){
				////camp non trovata (cosa improbabile)
				out.write("-1");
			} else if(ret == -4){
				////qualche indicatore a 0
				out.write("-4");
			} else if(ret == -5){
				//insufficienti utenti per svolgere T1
				out.write("-5");
			} else if(ret == -6){
				//insufficienti utenti per svolgere T1
				out.write("-6");
			}else if(ret == -3){ // errore che non dovrebbe mai succedere
				out.write(-2);	// non trova nessun'immagine
			}else {
				//ok
				out.write("0");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
