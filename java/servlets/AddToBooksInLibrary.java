package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import database.tables.EditBooksInLibraryTable;
import database.tables.EditBooksTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.Book;
import mainClasses.BookInLibrary;

/**
 * Servlet implementation class AddToBooksInLibrary
 */
public class AddToBooksInLibrary extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddToBooksInLibrary() {
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
		String BufferToString = request.getReader().lines().collect(Collectors.joining());
		EditBooksInLibraryTable eblt = new EditBooksInLibraryTable();
		HttpSession session = request.getSession();
		BookInLibrary b = new BookInLibrary();
		b.setIsbn(BufferToString);
		b.setLibrary_id((int)session.getAttribute("lib_id"));
		b.setAvailable("true");
		String json = eblt.bookInLibraryToJSON(b);
		try {
			eblt.addBookInLibraryFromJSON(json);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
