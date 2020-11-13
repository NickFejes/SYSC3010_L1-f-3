import java.util.List;

public class DataManager {
	String totalCandy;
	String currentCandy;
	List<String> dateTime;

	public DataManager() {


	}

	public void addDateTime(List<String> dateTime) {
		this.dateTime =  dateTime;

	}
	
	public void addDateTime(String i) {

		dateTime.add(i);

	}
	
	public void setTotalCandy(String i) {

		totalCandy =i;
	}
	
	public void setCurrentCandy(String i) {

		currentCandy =i;
	}
	

	public String totalCandy() {

		return totalCandy;
	}

	public String currentCandy() {

		return currentCandy;
	}

	public String getDateTime(int i) {

		return dateTime.get(i);

	}
	
	public int size() {
		
		return dateTime.size();
	}
	


}
