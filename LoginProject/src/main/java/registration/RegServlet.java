package registration;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String LOAD_DRIVER="com.mysql.cj.jdbc.Driver";
	public static String URL="jdbc:mysql://localhost:3306/userdb";
	public static String USERNAME="root";
	public static String PASSWORD="root";
	Connection connection;
	//no need to load Driver
    
    public RegServlet() {
        super();
        
    }


	public void init(ServletConfig config) throws ServletException {
		try {
			connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String	fname = request.getParameter("fname");
	    String	lname = request.getParameter("lname");
	    String	uname = request.getParameter("uname");
	    String	pword = request.getParameter("pword");
	    try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into userInfo values(?,?,?,?)");
			preparedStatement.setString(1, fname);
			preparedStatement.setString(2, lname);
			preparedStatement.setString(3, uname);
			preparedStatement.setString(4, pword);
			preparedStatement.executeUpdate();
			
			PrintWriter pw=response.getWriter();
			pw.println("<html><body bgcolor=black text=white><center>");
			pw.println("<h2>Registration Successfully.......</h2>");
			pw.println("<a href=login.html>Login</a>");
			pw.println("</center></body></html>");			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
