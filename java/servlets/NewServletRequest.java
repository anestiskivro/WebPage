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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.Librarian;
import mainClasses.Student;
import mainClasses.JSON_Converter;

/**
 *
 * @author anestis
 */
public class NewServletRequest extends HttpServlet {

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
        Librarian lu = new Librarian();
        Librarian lu1 = new Librarian();
        Student su1 = new Student();
        Student su2 = new Student();
        Student su3 = new Student();
        BufferedReader inputJSONfromClient = request.getReader();
        String stud = jc.getJSONFromAjax(inputJSONfromClient);
        System.out.println(stud);
        Student s = eut.jsonToStudent(stud);
        String username = s.getUsername();
        System.out.println(username);
        String email = s.getEmail();
        String id = s.getStudent_id();

        try {
            su1 = eut.databaseToStudentUsername(username);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (su1 == null) {
                System.out.println("O xristis den uparxei");
            } else {
                System.out.println("O xristis uparxei");
            }
        //System.out.println(su1);
        try {
            su2 = eut.databaseToStudentEmail(email);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (su2 == null) {
                System.out.println("O xristis den uparxei");
            } else {
                System.out.println("O xristis uparxei");
            }
            //System.out.println(su1);
        try {
            su3 = eut.databaseToStudentID(id);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (su3 == null) {
                System.out.println("O xristis den uparxei");
            } else {
                System.out.println("O xristis uparxei");
            }
            //System.out.println(su1);
        try {
            lu = lut.databaseToLibrarianUsename(username);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            lu1 = lut.databaseToLibrarianEmail(email);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NewServletRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (su1 != null && lu != null && su2 != null && su3 != null && lu1 != null) {
            response.setStatus(403);

        } else {

                String json = eut.studentToJSON(s);
            try {
                eut.addStudentFromJSON(json);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NewServletRequest.class.getName()).log(Level.SEVERE, null, ex);
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
    }
}
