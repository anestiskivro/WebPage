package servlets;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import database.tables.EditBooksTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.Book;
import mainClasses.JSON_Converter;

/**
 * Servlet implementation class RegisterBook
 */
public class RegisterBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RegisterBook() {
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
		String json;
		JSON_Converter jc = new JSON_Converter();
		BufferedReader reader = request.getReader();
		json = jc.getJSONFromAjax(reader);
		Gson g=new Gson();
		Book b=g.fromJson(json, Book.class);
		System.out.println(b.getAuthors());
		if(b.getPages()<=0 || b.getPublicationyear()<1200 || !(b.getUrl().startsWith("http")) || !(b.getPhoto().startsWith("http"))) {
			response.setStatus(403);
			return;
		}else {
			if(b.getIsbn().length()<10 || b.getIsbn().length()>13) {
				response.setStatus(403);
			}
			EditBooksTable ebt=new EditBooksTable();
			try {
				ebt.addBookFromJSON(json);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setStatus(200);
			return;
		}
	}

}
