/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditLibrarianTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;

/**
 *
 * @author anestis
 */
public class NewServletRequestLibrUpd extends HttpServlet {

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
        EditLibrarianTable eut = new EditLibrarianTable();
        int i = 0;
        BufferedReader inputJSONfromClient = request.getReader();
        String stud = jc.getJSONFromAjax(inputJSONfromClient);
        String s0 = new String();
        String s1 = new String();
        String s2 = new String();
        String s3 = new String();
        String[] couple = stud.split(",");
        for (String couple1 : couple) {
            String[] items = couple1.split(":");
            if (i == 0) {
                s1 = items[1];
            } else if (i == 1) {
                s2 = items[1];
            } else if (i == 2) {
                s3 = items[1];
            }

            i++;
        }
        String s = s1.substring(1, 2);
        System.out.println(s);
        int id = Integer.parseInt(s);
        String s4 = s2.substring(1, s2.length() - 1);
        System.out.println(s4);
        String s5 = s3.substring(1, s3.length() - 2);
        System.out.println(s5);
        if (!s4.equals("username")) {
            try {
                eut.updateLibrarianElem(id, s4, s5);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(NewServletRequestUpd.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            response.setStatus(403);
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
