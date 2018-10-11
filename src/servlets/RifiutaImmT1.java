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
 * Servlet implementation class RifiutaImmT1
 */
@WebServlet("/RifiutaImmT1")
public class RifiutaImmT1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RifiutaImmT1() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("plain/text"); 

		PrintWriter out = response.getWriter();
		
		// Salva dati id dell'immagine e del lavoro
		int idImm = Integer.parseInt(request.getParameter("idImm"));
		int idLav = Integer.parseInt(request.getParameter("idLav"));
		
		try {
			if(dbMan.rifiutaImmT1(idImm, idLav)){	// chiama metodo db per rifiutare l'immagine
				out.write("0");	
			} else {
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
