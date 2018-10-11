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
 * Servlet implementation class ModCampagna
 */
@WebServlet("/ModCampagna")
public class ModCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModCampagna() {
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
		
		// Salva i nuovi dati
		String nomeNew = request.getParameter("nomeN");
		int n = Integer.parseInt(request.getParameter("n"));
		int m = Integer.parseInt(request.getParameter("m"));
		int k = Integer.parseInt(request.getParameter("k"));
		int p = Integer.parseInt(request.getParameter("p"));
		String nomeOld = request.getParameter("nomeO");
	
		try {
			
			int ret = dbMan.modCampagna(nomeNew, n, m, k, p, nomeOld);  // modifica della riga della campagna
			
			if (ret == -2){
				//gia esistente
				out.write("-2");
			} else if(ret == -1){
				//err server
				out.write("-1");
			} else {
				// andato a buon fine
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
