import java.util.List;

public class DataManager {
	String name;
	String totalCandy;
	String currentCandy;
	String location;
	String channelId;
	List<String> dateTime;

	public DataManager() {


	}

	public void addDateTime(List<String> dateTime) {
		this.dateTime =  dateTime;

	}
	
	public void addDateTime(String i) {

		dateTime.add(i);

	}
	
	public void setName(String i) {

		name =i;
	}
	
	public void setTotalCandy(String i) {

		totalCandy =i;
	}
	
	public void setCurrentCandy(String i) {

		currentCandy =i;
	}
	
	public void setLocation(String i) {

		location =i;
	}
	
	public void setChannelId(String i) {

		channelId =i;
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
	
	public String location() {

		return location;

	}
	
	public String channelId() {

		return channelId;

	}
	
	public String name() {

		return name;

	}
	
	public int size() {
		
		return dateTime.size();
	}
	


}
