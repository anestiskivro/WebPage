/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditAdminMessageTable;
import database.tables.EditLibrarianTable;
import database.tables.EditStudentsTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;
import mainClasses.Student;

/**
 *
 * @author anestis
 */
public class admin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet admin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet admin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    	HttpSession session = request.getSession();
    	if(session.getAttribute("username")!=null) {
    		response.setStatus(200);
			response.getWriter().write((String)session.getAttribute("username"));
    	}else {
    		response.setStatus(403);
    	}
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSON_Converter jc = new JSON_Converter();
        EditAdminMessageTable admin = new EditAdminMessageTable();
        EditStudentsTable eut = new EditStudentsTable();
        EditLibrarianTable eul = new EditLibrarianTable();
        Student s,s1=null;
        Librarian l = null;
        Gson g = new Gson();
        String data = new String();
        response.setContentType("text/html;charset=UTF-8");
        BufferedReader inputJSONfromClient = request.getReader();
        data = jc.getJSONFromAjax(inputJSONfromClient);
        s= g.fromJson(data, Student.class);
        String password = "";
        String username = "";
        HttpSession session=request.getSession(true);
        try {
        	l = eul.databaseToLibrarian(s.getUsername(), s.getPassword()); 
        	s1 = eut.databaseToStudent(s.getUsername(), s.getPassword());
			if( l != null) {
				username = l.getUsername();
				password = l.getPassword();
			}else {
				username = s.getUsername();
				password = s.getPassword();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if ((username.equals("admin")) && (password.equals("admin12*"))) {
        	
        	response.setStatus(200);
            session.setAttribute("username", username);
        } else if(l != null){
        	response.setStatus(202);
        	session.setAttribute("lib_id", l.getLibrary_id());
        	session.setAttribute("username", "librarian");
        }else if(s1!=null) {
        	response.setStatus(203);
        	session.setAttribute("username", "student");
        	session.setAttribute("name", s.getUsername());
        }else {
        	response.setStatus(403);
        }

    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
