package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import database.tables.EditStudentsTable;
import database.tables.GeneralQueries;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.Student;

/**
 * Servlet implementation class ShowActiveBorrowings
 */
public class ShowActiveBorrowings extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ShowActiveBorrowings() {
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
		GeneralQueries g = new GeneralQueries();
		HttpSession session = request.getSession();
		Student s = new Student();
		EditStudentsTable est = new EditStudentsTable();
		Gson gson = new Gson();
		try {
			s = est.databaseToStudentUsername((String)session.getAttribute("name"));
			JsonArray ja = g.allOpenBorrowingsOfAStudent(s.getUser_id());
			String json = gson.toJson(ja);
			response.getWriter().write(json);
			response.setStatus(200);
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(403);
			e.printStackTrace();
		}
		
	}

}
