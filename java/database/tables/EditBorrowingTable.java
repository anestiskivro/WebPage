/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.Borrowing;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditBorrowingTable {

   
    public void addBorrowingFromJSON(String json) throws ClassNotFoundException{
         Borrowing r=jsonToBorrowing(json);
         createNewBorrowing(r);
    }
    
    
     public Borrowing databaseToBorrowing(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
         Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM borrowing WHERE borrowing_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Borrowing bt = gson.fromJson(json, Borrowing.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    public String BorrowingRequestCheck(String isbn,int id) throws ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

       ResultSet rs;
       try {
           rs = stmt.executeQuery("SELECT borrowing.borrowing_id from borrowing where bookcopy_id IN(select bookcopy_id from booksinlibraries where library_id="+id+" and isbn ='"+isbn+"')");
           rs.next();
           String json=DB_Connection.getResultsToJSON(rs);
           return json;
       } catch (Exception e) {
           System.err.println("Got an exception! ");
           System.err.println(e.getMessage());
       }
       return null;
    }
    
    public ArrayList<String> databaseToBorrowingRequest(int id) throws ClassNotFoundException, SQLException{
    	Connection con = DB_Connection.getConnection();
    	Statement stmt = con.createStatement();
    	ArrayList<String> b = new ArrayList<String>();
    	ResultSet rs;
    	try {
    		rs = stmt.executeQuery("SELECT booksinlibraries.bookcopy_id,books.isbn, books.title,"
    				+ "students.firstname,students.lastname,students.university,borrowing.fromdate,borrowing.todate,borrowing.status "
    				+ "FROM books,booksinlibraries,borrowing,students "
    				+ "where booksinlibraries.isbn=books.isbn and "
    				+ "booksinlibraries.bookcopy_id= borrowing.bookcopy_id "
    				+ "and booksinlibraries.library_id="+id+" and "
    				+ "borrowing.user_id= students.user_id and (borrowing.status='returned' or borrowing.status='requested' or borrowing.status='borrowed')"
    				);
    		while(rs.next()) {
    			String json = DB_Connection.getResultsToJSON(rs);
    			b.add(json);
    		}
    		return b;
    	}catch(Exception e) {
    		 System.err.println("Got an exception! ");
             System.err.println(e.getMessage());
    	}
    	return null;
    }
    
    public ArrayList<String> BorrowRequests(int id) throws ClassNotFoundException, SQLException{
    	Connection con = DB_Connection.getConnection();
    	Statement stmt = con.createStatement();
    	ArrayList<String> b = new ArrayList<String>();
    	ResultSet rs;
    	try {
    		rs = stmt.executeQuery("SELECT books.isbn, books.title,"
    				+ "students.firstname,students.lastname,students.university,borrowing.fromdate,borrowing.todate,borrowing.status "
    				+ "FROM books,booksinlibraries,borrowing,students "
    				+ "where booksinlibraries.isbn=books.isbn and "
    				+ "booksinlibraries.bookcopy_id= borrowing.bookcopy_id "
    				+ "and booksinlibraries.library_id="+id+" and "
    				+ "borrowing.user_id= students.user_id and (borrowing.status='returned' or borrowing.status='borrowed')"
    				);
    		while(rs.next()) {
    			String json = DB_Connection.getResultsToJSON(rs);
    			b.add(json);
    		}
    		return b;
    	}catch(Exception e) {
    		 System.err.println("Got an exception! ");
             System.err.println(e.getMessage());
    	}
    	return null;
    }
      
     public Borrowing jsonToBorrowing(String json) {
        Gson gson = new Gson();
        Borrowing r = gson.fromJson(json, Borrowing.class);
        return r;
    }
     
         
      public String borrowingToJSON(Borrowing r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Borrowing.class);
        return json;
    }


    public void updateBorrowing(int borrowingID, int userID, String info, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE borrowing SET status";//...
        
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }
    public void updateBorrowingStatus(String id,String status) throws ClassNotFoundException, SQLException {
    	Connection con = DB_Connection.getConnection();
    	Statement stmt = con.createStatement();
        String updateQuery = "UPDATE borrowing SET status='"+status+"' WHERE borrowing_id='"+id+"'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
        }

    public void deleteBorrowing(int randevouzID) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM borrowing WHERE borrowing_id='" + randevouzID + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }



    public void createBorrowingTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE borrowing "
                + "(borrowing_id INTEGER not NULL AUTO_INCREMENT, "
                + " bookcopy_id INTEGER not NULL, "
                + " user_id INTEGER not NULL, "
                + " fromdate DATE not NULL, "
                + " todate DATE not NULL, "
                + " status VARCHAR(15) not NULL, "
                + "FOREIGN KEY (user_id) REFERENCES students(user_id), "
                + "FOREIGN KEY (bookcopy_id) REFERENCES booksinlibraries(bookcopy_id), "
                + " PRIMARY KEY (borrowing_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewBorrowing(Borrowing bor) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " borrowing (bookcopy_id,user_id,fromDate,toDate,status)"
                    + " VALUES ("
                    + "'" + bor.getBookcopy_id() + "',"
                    + "'" + bor.getUser_id() + "',"
                    + "'" + bor.getFromDate() + "',"
                    + "'" + bor.getToDate() + "',"
                    + "'" + bor.getStatus() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The borrowing was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditBorrowingTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
