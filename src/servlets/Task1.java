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

import org.json.JSONObject;

import database.DbManager;

/**
 * Servlet implementation class Task1
 */
@WebServlet("/Task1")
public class Task1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Task1() {
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
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("plain/text"); 
		PrintWriter out = response.getWriter();
		
		JSONObject infoCamp = new JSONObject();
		
		String nomeCamp = request.getParameter("nomeCampagna");	// salvataggio nome della campagna
		
		HttpSession session = request.getSession();

		int id = 0;
		
		try {
			infoCamp = dbMan.getCamp(nomeCamp);		// ottiene l'id della campagna tramite il nome
			id = infoCamp.getInt("idCamp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(id != 0 || id!= -1){
			session.setAttribute("idCamp", id);		// setta la sessione con l'id della campagna
		} else {
			out.write("-1");	// errore
			return;
		}
		
		String username = (String) session.getAttribute("username");	// salva l'username del lavoratore in una var
		
		int setTask = 1;

		try {
			setTask = dbMan.setTask1(username, id);	// chiama il metodo task1 del db
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (setTask == 0){
			out.write("0");
		} else if(setTask == -1){
			out.write("-1");
		} else {
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
