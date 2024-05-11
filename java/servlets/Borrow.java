package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import database.tables.EditBooksInLibraryTable;
import database.tables.EditBorrowingTable;
import database.tables.EditStudentsTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.Borrowing;
import mainClasses.JSON_Converter;

/**
 * Servlet implementation class Borrow
 */
public class Borrow extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Borrow() {
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
		Gson g = new Gson();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime after = LocalDateTime.now().plusMonths(1);
		EditBooksInLibraryTable ebt = new EditBooksInLibraryTable();
		HttpSession session = request.getSession();
		JSON_Converter j = new JSON_Converter();
		String json = j.getJSONFromAjax(request.getReader());
		JsonObject jo = g.fromJson(json, JsonObject.class);
		Borrowing b = new Borrowing();
		EditBorrowingTable eb = new EditBorrowingTable();
		EditStudentsTable est = new EditStudentsTable();
		String isbn = jo.get("isbn").getAsString();
		String lib_id = jo.get("libid").getAsString();
		
		try {
			String id = ebt.getBookFromLibrary(isbn, lib_id);
			jo = g.fromJson(id,JsonObject.class);
			String bookcopy_id = jo.get("bookcopy_id").getAsString();
			
			ebt.updateBookInLibrary(bookcopy_id,"false");
			
			b.setBookcopy_id(Integer.parseInt(bookcopy_id));
			b.setUser_id((est.databaseToStudentUsername((String)session.getAttribute("name")).getUser_id()));
			b.setFromDate(dtf.format(now));
			b.setToDate(dtf.format(after));
			b.setStatus("requested");
			String entry = eb.borrowingToJSON(b);
			eb.addBorrowingFromJSON(entry);
			response.setStatus(200);
		} catch (ClassNotFoundException | SQLException e) {
			response.setStatus(403);
			e.printStackTrace();
		}
		
		
	}

}
