package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import database.tables.EditBorrowingTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.Borrowing;

/**
 * Servlet implementation class GetBorrowed
 */
public class GetBorrowed extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetBorrowed() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ArrayList<String> b = new ArrayList<String>();
		EditBorrowingTable ebt = new EditBorrowingTable();
		Gson g = new Gson();
		int id= (int)session.getAttribute("lib_id");
		try {
			b = ebt.databaseToBorrowingRequest(id);
			String json = g.toJson(b);
			response.setStatus(200);
			response.getWriter().write(json);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			response.setStatus(403);
			e.printStackTrace();
		}
	}

}
