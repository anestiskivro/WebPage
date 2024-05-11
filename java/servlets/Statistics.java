package servlets;

import database.tables.EditBooksInLibraryTable;
import database.tables.EditBooksTable;
import database.tables.EditStudentsTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;import jakarta.servlet.http.HttpSession;

/**
 *
 * @author anestis
 */
public class Statistics extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
            out.println("<title>Servlet Statistics</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Statistics at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("type");
        System.out.println(type);
        String s1 = new String();
        String s2 = new String();
        String s3 = new String();
        String s5 = new String();
        String s4 = new String();
        String s6 = new String();
        String s8 = new String();
        String s9 = new String();
        HttpSession session = request.getSession();
        if (type.equals("student")) {
            EditStudentsTable s = new EditStudentsTable();
            try {
                s1 = s.CountOfstudents("MSc");
                s8 = s.CountOfstudents("BSc");
                s9 = s.CountOfstudents("PhD");

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (s1 != null && s8 != null && s9 != null) {
                List<String> stud = Arrays.asList(s1, s8, s9);
                String str = stud.stream().collect(Collectors.joining(", "));
                System.out.println(str);
                response.setStatus(200);
                for (String s7 : stud) {
                    response.getWriter().write(s7);
                }

            }

        } else if (type.equals("library")) {
            EditBooksInLibraryTable l2 = new EditBooksInLibraryTable();
            try {
                s1 = l2.CountOfbooks(1);
                s3 = l2.CountOfbooks(2);
                s5 = l2.CountOfbooks(3);
                s4 = l2.CountOfbooks(4);
                s6 = l2.CountOfbooks(5);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (s1 != null && s3 != null && s5 != null && s4 != null && s6 != null) {
                List<String> liststr = Arrays.asList(s4, s1, s3, s5, s6);
                String str = liststr.stream().collect(Collectors.joining(", "));
                System.out.println(str);
                response.setStatus(200);
                for (String s7 : liststr) {
                    response.getWriter().write(s7);
                }
            }

        } else if (type.equals("books")) {
            try {
                EditBooksTable b = new EditBooksTable();
                s1 = b.CountOfbooks("Fantasy");
                s5 = b.CountOfbooks("Novel");
                s6 = b.CountOfbooks("Sports");
                s8 = b.CountOfbooks("Adventure");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (s1 != null && s5 != null && s6 != null && s8 != null) {
                List<String> book = Arrays.asList(s1, s5, s6, s8);
                String str = book.stream().collect(Collectors.joining(", "));
                System.out.println(str);
                response.setStatus(200);
                for (String s7 : book) {
                    response.getWriter().write(s7);
                }

            }
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
        processRequest(request, response);
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
