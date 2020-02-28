import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchGomez")
public class SearchGomez extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchGomez() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionGomez.getDBConnection(getServletContext());
         connection = DBConnectionGomez.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM projectTable";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM projectTable WHERE USERNAME LIKE ?";
            String theUserName = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
             int id = rs.getInt("id");
             String userName = rs.getString("username").trim();
             String item = rs.getString("item").trim();
             String quantity = rs.getString("quantity").trim();
             String location = rs.getString("location").trim();
             String value = rs.getString("value").trim();

             if (keyword.isEmpty() || userName.contains(keyword)) {
                out.println("ID: " + id + ", ");
                out.println("User: " + userName + ", ");
                out.println("Item: " + item + ", ");
                out.println("Quantity: " + quantity +", ");
                out.println("Location: " + location + ", ");
                out.println("Value: " + value + " <br>");
             }
          }
         out.println("<a href=/webproject-inventory-gomez/search_gomez.html>Search Data</a> <br>");
         out.println("<a href=/webproject-inventory-gomez/insert_gomez.html>Insert Data</a> <br>");

         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
