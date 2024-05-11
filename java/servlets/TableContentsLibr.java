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
public class TableContentsLibr extends HttpServlet {

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
            out.println("<title>Servlet TableContentsLibr</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TableContentsLibr at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        EditStudentsTable eut = new EditStudentsTable();
        EditLibrarianTable eul = new EditLibrarianTable();
        Student s = new Student();
        Librarian l = new Librarian();
        JSON_Converter jc = new JSON_Converter();
        String data = new String();
        response.setContentType("text/html;charset=UTF-8");
        BufferedReader inputJSONfromClient = request.getReader();
        data = jc.getJSONFromAjax(inputJSONfromClient);
        System.out.println(data);
        int i = 0;
        l = eul.jsonToLibrarian(data);
        System.out.println(l);
        String s0 = new String();
        String s2 = new String();
        String s1 = new String();
        String s3 = new String();
        String[] couple = data.split(",");
        for (String couple1 : couple) {
            String[] items = couple1.split(":");
            if (i == 0) {
                s1 = items[1];
            } else if (i == 1) {
                s2 = items[1];
            }
            i++;
        }
        String s4 = s1.substring(1, s1.length() - 1);
        String s5 = s2.substring(1, s2.length() - 2);
        System.out.println(s4);
        System.out.println(s5);
        if (l != null) {
            try {
                eul.DeleteLibrarian(s4, s5);
            } catch (SQLException | ClassNotFoundException ex) {
                response.setStatus(403);
                Logger.getLogger(TableContents.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        response.setStatus(200);

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
