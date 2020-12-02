import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MachinesPageHandlerTest {

	@Test
	void  test() {

		MachinesPageHandler test = new MachinesPageHandler();
		List<Machine> machines = new ArrayList<Machine>();
		try {

			test.retrieveFromDatabaseMachines(machines);
			
			
			assertFalse(machines.isEmpty());//Checks if machines has values from database

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}





