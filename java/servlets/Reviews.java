package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import database.tables.EditReviewsTable;
import database.tables.EditStudentsTable;
import database.tables.GeneralQueries;
import jakarta.servlet.http.HttpSession;
import mainClasses.JSON_Converter;
import mainClasses.Review;
import mainClasses.Student;

/**
 * Servlet implementation class Reviews
 */
public class Reviews extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Reviews() {
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
		HttpSession session = request.getSession();
		JSON_Converter jc = new JSON_Converter();
		String json1 = jc.getJSONFromAjax(request.getReader());
		Student s = new Student();
		EditStudentsTable est = new EditStudentsTable();
		try {
			s = est.databaseToStudentUsername((String)session.getAttribute("name"));
			Gson gson = new Gson();
			JsonObject obj = gson.fromJson(json1, JsonObject.class);
			Review r = new Review();
			EditReviewsTable ert = new EditReviewsTable();
			r.setIsbn(obj.get("isbn").getAsString());
			r.setReviewScore("5");
			r.setReviewText(obj.get("review").getAsString());
			r.setUser_id(s.getUser_id());
			String json = ert.reviewToJSON(r);
			ert.addReviewFromJSON(json);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
