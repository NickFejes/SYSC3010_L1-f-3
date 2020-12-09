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
 * The LogTableHandler manages the logtable page that is it get times at which candy was dispensed from a machine 
 */
public class LogTableHandler extends HttpServlet {

	/** 
	 * Retrieves a class DataManager with all information about the machine chosen
	 *
	 * @param request  the http request
	 * @param response  the http response
	 * @throws   ClassNotFoundException,SQLException  
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		DataManager record =  new DataManager() ;
		List<String> dateTimes = new ArrayList<String>();
		String ChannelId = request.getParameter("machines");
		
		request.getSession().setAttribute("machineId", ChannelId);

		try {
			retrieveFromCandyDatabaseMachines(ChannelId,record);
			retrieveFromDateTimeDatabaseMachines(ChannelId,dateTimes);
			record.addDateTime(dateTimes);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		request.setAttribute("record", record);
		System.out.println("attribute sent to jsp");


		RequestDispatcher dispatcher = request.getRequestDispatcher("TablesPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}






	/** 
	 * Retreive's attributes except time log from of machine which has the same channel id as provided and put them in record
	 *
	 * @param ChannelId  the channel identifier for machine
	 * @param record  the record
	 * @throws   ClassNotFoundException 
	 */

	public static void retrieveFromCandyDatabaseMachines(String ChannelId, DataManager record) throws ClassNotFoundException {


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
			String query = "select CurrentAmount,TotalDispensed,location,MachineName from machines where TsChannelId="+ChannelId+";";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String currentCandy = rs.getObject(1).toString();
				String totalCandy = rs.getObject(2).toString();
				String location = rs.getObject(3).toString();
				String name = rs.getObject(4).toString();

				record.setChannelId(ChannelId);

				record.setCurrentCandy(currentCandy);
				System.out.println(currentCandy);

				record.setTotalCandy(totalCandy);
				System.out.println(totalCandy);

				record.setLocation(location);
				System.out.println(location);

				record.setName(name);
				System.out.println(name);

			}




		} catch (SQLException e) {
			e.printStackTrace();
			for(Throwable ex : e) {
				System.err.println("Error occurred " + ex);
			}
			System.out.println("Error in fetching data");
		}

	}


	/** 
	 *
	 * Retreive's list of time logs of machine which has the same channel id as provided and puts them in dateTimes
	 *
	 * @param ChannelId  the channel identifier for machine
	 * @param dateTimes  the list that contains time logs
	 * @throws   ClassNotFoundException 
	 */
	public static void retrieveFromDateTimeDatabaseMachines(String ChannelId, List<String> dateTimes) throws ClassNotFoundException {


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
			System.out.println("Connected to database 2nd time!");
		} else {
			System.out.println("Failed to make connection!");
		}

		try {

			Statement stmt = connection.createStatement();
			String query = "select record from timeLog where TsChannelId="+ChannelId+";";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				System.out.println("adding record");
				String dateTime = rs.getObject(1).toString();
				dateTimes.add(dateTime);
				System.out.println(dateTime);

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
