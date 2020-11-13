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

public class LogTableHandler extends HttpServlet {

	public LogTableHandler(){

	}



	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		DataManager record =  new DataManager() ;
		List<String> dateTimes = new ArrayList<String>();

		try {
			String ChannelId = request.getParameter("machines");
			retrieveFromCandyDatabaseMachines(ChannelId,record);
			retrieveFromDateTimeDatabaseMachines(ChannelId,dateTimes);
			record.addDateTime(dateTimes);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("record", record);
		System.out.println("attribute sent to jsp");


		RequestDispatcher dispatcher = request.getRequestDispatcher("TablesPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}







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
			String query = "select CurrentAmount,TotalDispensed from machines where TsChannelId="+ChannelId+";";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String currentCandy = rs.getObject(1).toString();
				String totalCandy = rs.getObject(2).toString();

				record.setCurrentCandy(currentCandy);
				System.out.println(currentCandy);

				record.setTotalCandy(totalCandy);
				System.out.println(totalCandy);
			}




		} catch (SQLException e) {
			e.printStackTrace();
			for(Throwable ex : e) {
				System.err.println("Error occurred " + ex);
			}
			System.out.println("Error in fetching data");
		}

	}
	public void destroy() {}

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
