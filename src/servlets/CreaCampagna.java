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
 * Servlet implementation class CreaCampagna
 */
@WebServlet("/CreaCampagna")
public class CreaCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreaCampagna() {
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
		HttpSession session = request.getSession();
		
		// Salvataggio di tutti i dati da inserire poi nella tabella campagna
		String username = (String) session.getAttribute("username");
		String nomeCamp = request.getParameter("nomeCampagna");
		int nValutaz = Integer.parseInt(request.getParameter("nValutaz"));
		int mValutaz = Integer.parseInt(request.getParameter("mValutaz"));
		int kValutaz = Integer.parseInt(request.getParameter("kValutaz"));
		int pAnnotaz = Integer.parseInt(request.getParameter("pAnnotaz"));
		
		try {
			if (dbMan.nuovaCampagna(nomeCamp, username, nValutaz, mValutaz, kValutaz, pAnnotaz)){ // Inserimento andato a buon fine
				out.write("0");
			} else { // Non e' riuscito a creare la nuova campagna
				out.write("-1");
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
