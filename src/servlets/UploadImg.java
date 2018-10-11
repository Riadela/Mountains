package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import database.DbManager;

/**
 * Servlet implementation class UploadImg
 */
@WebServlet("/UploadImg")
@MultipartConfig
public class UploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbManager dbMan = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadImg() {
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

		HttpSession session = request.getSession();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request); // controllo se vi e' una richiesta http
		if(!isMultipart){
			System.out.println("no multip");
		} else {

			FileItemFactory factory = new DiskFileItemFactory();  // Creazione di un oggetto file item da tenere in memoria
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("rawtypes")
			List items = null; // creazione di una lista

			try {
				items = upload.parseRequest(request); // 
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			@SuppressWarnings("rawtypes")
			
			Iterator itr = items.iterator(); // creazione delle iteratore sulla lista item
			String idcamp = "";

			if(itr.hasNext()){ 
				FileItem item = (FileItem) itr.next(); 
				idcamp = item.getString();
			}

			int idCamp = Integer.parseInt(idcamp);

			File cartCampagna = new File("/Users/Riad/Documents/workspace/progettoTIW/WebContent/img/" + idcamp);

			if (!cartCampagna.exists()) {

				cartCampagna.mkdir();
			} 
			
			int success = -1;
			
			session.setAttribute("errImg", 0);


			String type;
			int cnt = 0;
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();

				if (item.isFormField()) { 	// Se non e' un immagine
					if (cnt == 0){
						session.setAttribute("errImg", 1); // setta errore
					}
				} else { // se e' un immagine
					
					if (item.getContentType() != null){   // se ci sono immagini
						String fileName = item.getContentType();	// prendi il tipo del file
						type = fileName.split("/")[0];	// prende il tipo del file (image/"png"ecc)
						if(type.equals("image")){		// se il tipo e' un immagine
							try {

								int newName = -1;

								try {
									newName = dbMan.aggImmagine1(idCamp);		// crea una riga in tab immagine per ottenere id dell'immagine
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								
								String path = "/Users/Riad/Documents/workspace/progettoTIW/WebContent/img/" + idcamp + "/" + newName + ".jpeg";
								File savedFile = new File(path);		// crea file con percorso
								item.write(savedFile);  	

								try {
									success = dbMan.aggImmagine2(newName, idcamp + "/" + newName + ".jpeg");	// aggiunge effettivamente nel db
									if(success==0){	// avevo gia settatato errImg a 0

									} else {
										session.setAttribute("errImg", 1);		// errore nel caricamento e setto a 1
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}						
						} else {
							session.setAttribute("errImg", 1); 	// errore
						}
					} else {
						session.setAttribute("errImg", 1);		//errore
					}

				}
				cnt++;
			}

		}
		response.sendRedirect("/progettoTIW/jsp/homeGestore.jsp");  // rimanda alla home del gestore
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
