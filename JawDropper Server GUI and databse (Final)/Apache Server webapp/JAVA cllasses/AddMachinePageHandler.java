import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The AddMachinePageHandler class adds machine to the database with information provided on Add Machine  page 
 */

public class AddMachinePageHandler extends HttpServlet {

	/**
	 * Takes values inputed on add machine page and uses the values to create a new machine in the database 
	 * 
	 * @param request  the http request
	 * @param response  the http response
	 * @throws   ClassNotFoundException 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String machineName = request.getParameter("name");
		String ChannelId = request.getParameter("channelId");
		String readKey = request.getParameter("readKey");
		String location = request.getParameter("location");

		try {
			System.out.println("reached to post of add machine");
			addMachineToDatabase(ChannelId,readKey, machineName,location);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		response.sendRedirect("SucessfullyAddedMachinePage.html");
	}

	/**
	 * Takes values and runs query to add a machine in database with the given attributes
	 * 
	 * @param ChannelId  the channel identifier for machine
	 * @param readKey  the read key for thingspeak channel of machine
	 * @param machineName  the machine name
	 * @param location  the location of machine
	 * @throws SQLException
	 */

	public static void addMachineToDatabase(String ChannelId, String readKey,String machineName,String location) throws ClassNotFoundException {


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
			String query = "INSERT INTO machines values("+ChannelId+",'"+readKey+"','"+machineName+"',NULL,NULL,'"+location+"');";
			stmt.executeUpdate(query);
			System.out.println("INSERT INTO machines values("+ChannelId+",'"+readKey+"','"+machineName+"',NULL,NULL,'"+location+"');");

		} catch (SQLException e) {
			e.printStackTrace();
			for(Throwable ex : e) {
				System.err.println("Error occurred " + ex);
			}
			System.out.println("Error in fetching data");
		}

	}

}


