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
      String title = "Inventory List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<body><header>\n" + //
            "<head><title>" + title + "</title>" + //
            "<style> header { background-color:5e2bff;" + //
                "color:white;" + //
                "text-align:center;"+ //
                "padding:5px;}" + //	 
            "nav {line-height:30px;"+ //
                "background-color:#eeeeee;"+ //
                "height:300px;"+ //
                "width:100px;"+ //
                "float:left;"+ //
                "padding:5px;}"+ //	      
            "section {width:350px;"+ //
                "float:left;"+ //
                "padding:10px;}"+ //
            "footer { background-color:5e2bff;"+ //
                "color:white;"+ //
                "clear:both;"+ //
                "text-align:center;"+ //
                "padding:5px;}"+ //	 	 
  
            "</style></head>\n" + //

            "<h1 align=\"center\">" + title + "</h1>\n</header>" + //
            
            "<nav><a href=search_gomez.html>Search User</a> <br>" + //
      		"<a href=insert_gomez.html>Insert Item</a> <br></nav>" + //
      		"<section>");

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
         
         out.println("User Account <b>" + keyword + "</b><br>");
         out.println("<table>");
         out.println("<tr>");
         out.println("<td><b>Item</b></td>");
         out.println("<td><b>Quantity</b></td>");
         out.println("<td><b>Location</b></td>");
         out.println("<td><b>Value</b></td>");
         out.println("</tr>");


         while (rs.next()) {
             int id = rs.getInt("id");
             String userName = rs.getString("username").trim();
             String item = rs.getString("item").trim();
             String quantity = rs.getString("quantity").trim();
             String location = rs.getString("location").trim();
             String value = rs.getString("value").trim();

             if (keyword.isEmpty() || userName.contains(keyword)) {
//                out.println("ID: " + id + ", ");
//                out.println("User: " + userName + ", ");
                out.println("<tr><td>" + item + "</td>");
                out.println("<td>" + quantity + "</td>");
                out.println("<td>" + location + "</td>");
                out.println("<td>" + value + "</td></tr>");
             }
          }
//         out.println("<a href=/webproject-inventory-gomez/search_gomez.html>Search Data</a> <br>");
//         out.println("<a href=/webproject-inventory-gomez/insert_gomez.html>Insert Data</a> <br>");
         out.println("</table></section><footer>Copyright @Gomez</footer></body></html>");
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
