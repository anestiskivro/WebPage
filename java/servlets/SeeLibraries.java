package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import database.tables.EditLibrarianTable;
import database.tables.EditStudentsTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;
import mainClasses.Student;

/**
 * Servlet implementation class SeeLibraries
 */
public class SeeLibraries extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SeeLibraries() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Librarian> l;
		HttpSession session = request.getSession();
		EditLibrarianTable elt = new EditLibrarianTable();
		EditStudentsTable est = new EditStudentsTable();
		JSON_Converter jc = new JSON_Converter();
		String isbn;
		isbn = jc.getJSONFromAjax(request.getReader());
		Gson g = new Gson();
		System.out.println(isbn);
		
		
		//get the lat and lon of current signed in student
		
		try {
			l = elt.getLibrarians(isbn);
			if(l.isEmpty()) {
				response.setStatus(403);
			}else {
				String json = g.toJson(l);
				
				Student s = est.databaseToStudentUsername((String)session.getAttribute("name"));
				JsonObject j1 = new JsonObject();
				j1.addProperty("latt",s.getLat());
				j1.addProperty("lonn", s.getLon());
				JsonArray j = g.fromJson(json, JsonArray.class);
				j.add(j1);
				json = g.toJson(j);
				
				response.getWriter().write(json);
				response.setStatus(200);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
