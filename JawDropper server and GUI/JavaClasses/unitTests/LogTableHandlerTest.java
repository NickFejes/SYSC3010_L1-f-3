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
		assertNotNull(record.totalCandy());
		assertNotNull(record.currentCandy());
		
		
		try {
			test.retrieveFromDateTimeDatabaseMachines(ChannelId,dateTimes);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(dateTimes.isEmpty());
	}
	

}
