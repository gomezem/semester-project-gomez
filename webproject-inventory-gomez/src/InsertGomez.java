
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
				"<body><header>\\n" + //
				"<h1 align=\"center\">Inserted Data</h1>\n" + //
				"<style> header { background-color:blue;" + //
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
				"footer { background-color:blue;"+ //
					"color:white;"+ //
					"clear:both;"+ //
					"text-align:center;"+ //
					"padding:5px;}"+ //	 	 

	            "</style></head>\n" + //

	            "</header>" + //

	            "<nav><a href=search_gomez.html>Search User</a> <br>" + //
	            "<a href=insert_gomez.html>Insert Item</a> <br></nav>" + //
	            "<section>" + //
	            
	            
	            "For user <b>" + userName + "</b> the following has been added:" + //
	            "<ul>\n" + //

		            "  <li><b>Item</b>: " + item + "\n" + //
		            "  <li><b>Quantity</b>: " + quantity + "\n" + //
		            "  <li><b>Location</b>: " + location + "\n" + //
		            "  <li><b>Value</b>: " + value + "\n" + //

				"</ul>\n");

		out.println("</table></section><footer>Copyright @Gomez</footer></body></html>");
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
