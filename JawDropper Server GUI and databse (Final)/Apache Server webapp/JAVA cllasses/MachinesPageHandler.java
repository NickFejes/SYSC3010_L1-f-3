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
 * The MachinesPageHandler class creates a list of machines for dropdown list containing all machines
 */
public class MachinesPageHandler extends HttpServlet  {

	/**
	 * creates a list of machines for drop down list containing all machines
	 * 
	 * @param request  the http request
	 * @param response  the http response
	 * @throws   IOException, ServletException 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {



		List<Machine> machines = new ArrayList<Machine>();
		try {
			retrieveFromDatabaseMachines(machines);
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
		request.setAttribute("machines", machines);

		System.out.println(machines.get(0).name());

		RequestDispatcher dispatcher = request.getRequestDispatcher("MachinesPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	/**
	 * Retrieves a list of class DataManger list with each instance of class DataManager representing  a single machine from the database with 
	 * all the information about individual machines stored individual instance of datamanager class
	 * 
	 * @param machines  the machines
	 * @throws   ClassNotFoundException, SQLException
	 */
	public static void retrieveFromDatabaseMachines(List<Machine> machines) throws ClassNotFoundException {


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
			String query = "select MachineName,TsChannelId from machines ;";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String name = rs.getObject(1).toString();
				String channelID = rs.getObject(2).toString();

				System.out.println(" " + name + " " );
				machines.add(new Machine(name,channelID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			for(Throwable ex : e) {
				System.err.println("Error occurred " + ex);
			}
			System.out.println("Error in fetching data");
		}

	}

}
