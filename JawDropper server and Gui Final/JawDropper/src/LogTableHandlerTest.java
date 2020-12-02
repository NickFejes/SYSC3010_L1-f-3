import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LogTableHandlerTest {


	@Test
	void test() {
		LogTableHandler test = new LogTableHandler();

		DataManager record =  new DataManager() ;
		List<String> dateTimes = new ArrayList<String>();
		String ChannelId = "1159996";

		try {
			test.retrieveFromCandyDatabaseMachines(ChannelId,record);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(record.totalCandy());		//Checks if totalCandy in record has a value from database
		assertNotNull(record.currentCandy());	//Checks if currentCandy in record has a value from database


		try {
			test.retrieveFromDateTimeDatabaseMachines(ChannelId,dateTimes);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertFalse(dateTimes.isEmpty());	//Checks if dateTimes has values from database
	}


}
