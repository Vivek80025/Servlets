package login;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.PreparableStatement;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//we will add mysql connector jar file inside tomcat(servlet ke pass,lib me) for allways.
	
	public static String LOAD_DRIVER="com.mysql.cj.jdbc.Driver";
	public static String URL="jdbc:mysql://localhost:3306/userdb";
	public static String USERNAME="root";
	public static String PASSWORD="root";
	Connection connection;
	//no need to load driver
	
    public LoginServlet() {
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
	    String	uname = request.getParameter("uname");
	    String	pword = request.getParameter("pword");
	    try {
	    	PreparedStatement preparedStatement = connection.prepareStatement("select * from userInfo where uname=?");
	    	preparedStatement.setString(1,uname);
	    	ResultSet rs = preparedStatement.executeQuery();
	    	PrintWriter pw=response.getWriter();
	    	
	    	pw.println("<html><body bgcolor=black text=white><center>");
	    	if(rs.next()) {
	    		pw.println("Welcome "+uname);
	    	}
	    	else {
	    		pw.println("User not valid........");
	    	}
	    	pw.println("</center></body></html>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
