
/**
 * @file InsertGomez.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertGomez")
public class InsertGomez extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertGomez() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userName = request.getParameter("userName");
      String item = request.getParameter("item");
      String quantity = request.getParameter("quantity");
      String location = request.getParameter("location");
      String value = request.getParameter("value");


      Connection connection = null;
      String insertSql = " INSERT INTO projectTable (id, USERNAME, ITEM, QUANTITY, LOCATION, VALUE) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionGomez.getDBConnection(getServletContext());
         connection = DBConnectionGomez.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, userName);
         preparedStmt.setString(2, item);
         preparedStmt.setString(3, quantity);
         preparedStmt.setString(4, location);
         preparedStmt.setString(5, value);

         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>User Name</b>: " + userName + "\n" + //
            "  <li><b>Item</b>: " + item + "\n" + //
            "  <li><b>Quantity</b>: " + quantity + "\n" + //
            "  <li><b>Location</b>: " + location + "\n" + //
            "  <li><b>Value</b>: " + value + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-inventory-gomez/search_gomez.html>Search Data</a> <br>");
      out.println("<a href=/webproject-inventory-gomez/insert_gomez.html>Insert Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
