import java.util.*;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The RemoveMachineHandler class removes machine from the database 
 */

public class RemoveMachineHandler extends HttpServlet {

	/**
	 * The AddMachinePageHandler class remove machine to the database with information provided on Add Machine  page 
	 * 
	 * @param request  the http request
	 * @param response  the http response
	 * @throws   ClassNotFoundException 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


		try {
			String ChannelId = (String) request.getSession().getAttribute("machineId");
			removeFromCandyDatabaseMachines(ChannelId);
			System.out.println(ChannelId);
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}

		response.sendRedirect("SucessfullyRemovedMachinePage.html");
	}





	/**
	 * Takes id of machine  and runs query to remove that machine from database 
	 * 
	 * @param ChannelId  the channel identifier
	 * @throws   ClassNotFoundException,SQLException 
	 */
	public static void removeFromCandyDatabaseMachines(String ChannelId) throws ClassNotFoundException {


		String host = "jdbc:mysql://localhost:3306/CandyDispenser";
		String username ="root";
		String password ="moonis221199";

		Connection connection =null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(host, username, password);

		} catch (SQLException e) {
			for(Throwable ex : e) {
				System.err.println("Error occurred " + ex);
			}
		}

		if (connection != null) {
			System.out.println("Connected to database!");
		} else {
			System.out.println("Failed to make connection!");
		}

		try {

			Statement stmt = connection.createStatement();
			String query = "DELETE from machines where TsChannelId="+ChannelId+";";		
			stmt.executeUpdate(query);



		} catch (SQLException e) {
			e.printStackTrace();
			for(Throwable ex : e) {
				System.err.println("Error occurred " + ex);
			}
			System.out.println("Error in fetching data");
		}

	}



}
