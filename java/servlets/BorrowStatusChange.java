package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import database.tables.EditBorrowingTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.JSON_Converter;

/**
 * Servlet implementation class BorrowStatusChange
 */
public class BorrowStatusChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BorrowStatusChange() {
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
		HttpSession session = request.getSession();
		JSON_Converter jc = new JSON_Converter();
		BufferedReader reader = request.getReader();
		json = jc.getJSONFromAjax(reader);
		Gson g = new Gson();
		JsonObject j = g.fromJson(json, JsonObject.class);
		String isbn = j.get("isbn").getAsString();
		String status = j.get("status").getAsString();
		EditBorrowingTable ebt = new EditBorrowingTable();
		try {
			String borr_id=ebt.BorrowingRequestCheck(isbn,(int)session.getAttribute("lib_id"));
			if(borr_id!=null){
				//if it found a book in the specific library with the given isbn
				if(!status.equals("borrowed") && !status.equals("successEnd")) {
					response.setStatus(403);
					return;
				}
				j = g.fromJson(borr_id, JsonObject.class);
				json=j.get("borrowing_id").getAsString();
				ebt.updateBorrowingStatus(json, status);
				response.setStatus(200);
			}else {
				response.setStatus(403);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
