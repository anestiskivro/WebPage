/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditLibrarianTable;
import database.tables.EditStudentsTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;
import mainClasses.Student;

/**
 *
 * @author anestis
 */
public class NewServletRequestLibr extends HttpServlet {

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
            out.println("<title>Servlet NewServletRequestLibr</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServletRequestLibr at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
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

        response.setContentType("text/html;charset=UTF-8");
        JSON_Converter jc = new JSON_Converter();
        EditStudentsTable eut = new EditStudentsTable();
        EditLibrarianTable lut = new EditLibrarianTable();
        Librarian lu1 = new Librarian();
        Librarian lu2 = new Librarian();
        Librarian lu3 = new Librarian();
        Student su = new Student();
        Student su1 = new Student();
        Student su2 = new Student();
        BufferedReader inputJSONfromClient = request.getReader();
        String lib = jc.getJSONFromAjax(inputJSONfromClient);
        System.out.println(lib);
        Librarian lib1 = lut.jsonToLibrarian(lib);
        System.out.println(lib1);
        String username = lib1.getUsername();
        String email = lib1.getEmail();

        try {
            su1 = eut.databaseToStudentUsername(username);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequestLibr.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            su2 = eut.databaseToStudentEmail(email);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequestLibr.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            lu1 = lut.databaseToLibrarianUsename(username);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequestLibr.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            lu2 = lut.databaseToLibrarianEmail(email);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequestLibr.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (su1 != null && su2 != null && lu1 != null && lu2 != null) {
            response.setStatus(403);

        } else {

                String json = lut.librarianToJSON(lib1);
            try {
                lut.addLibrarianFromJSON(json);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NewServletRequestLibr.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setStatus(200);
            response.getWriter().write(json);

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
