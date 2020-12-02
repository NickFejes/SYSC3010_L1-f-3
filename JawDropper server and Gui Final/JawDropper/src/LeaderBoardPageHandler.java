import java.util.Collections;

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

public class LeaderBoardPageHandler extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {



		List<Machine> machines = new ArrayList<Machine>();
		try {
			retrieveFromDatabaseMachines(machines);
			Collections.sort(machines, Collections.reverseOrder());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("machines", machines);


		RequestDispatcher dispatcher = request.getRequestDispatcher("LeaderBoardPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//response.sendRedirect("MachinesPage.jsp");
	}

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
			String query = "select MachineName,TotalDispensed from machines ;";
			ResultSet rs = stmt.executeQuery(query);
			machines.add(new Machine("M2",1));

			while (rs.next()) {
				String name = rs.getObject(1).toString();
				String TotalDispensedString = rs.getObject(2).toString();
				Integer TotalDispensedInteger = Integer.parseInt(TotalDispensedString);
				System.out.println(" " + name + " " );
				machines.add(new Machine(name,TotalDispensedInteger));
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

}
