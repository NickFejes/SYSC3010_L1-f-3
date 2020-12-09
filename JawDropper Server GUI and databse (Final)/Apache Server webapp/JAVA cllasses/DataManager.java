import java.util.List;

/**
 * The DataManager class is used to save data about the machine in database so the data can be displayed on the machine page when requested by user and 
 * so the data can be used to create the leader board
 */

public class DataManager {
	String name;
	String totalCandy;
	String currentCandy;
	String location;
	String channelId;
	List<String> dateTime;

	/**
	 * creates a dateTime list of values
	 *
	 * @param dateTime  the date time
	 */
	public void addDateTime(List<String> dateTime) {
		this.dateTime =  dateTime;

	}


	/**
	 * Adds a single values to dateTime List
	 *
	 * @param dateTime  the date time
	 */
	public void addDateTime(String dateTime) {
		this.dateTime.add(dateTime);
	}

	/**
	 * sets the name of the machine
	 *
	 * @param name  the name of machine
	 */
	public void setName(String name) {
		this.name =name;
	}

	/**
	 * sets the number of total candy dispensed by the machine till now
	 *
	 * @param totalCandy  the total candy
	 */
	public void setTotalCandy(String totalCandy) {
		this.totalCandy =totalCandy;
	}

	/**
	 * sets the number of  candy currently in the machine
	 *
	 * @param currentCandy  the current candy
	 */
	public void setCurrentCandy(String currentCandy) {
		this.currentCandy =currentCandy;
	}

	/**
	 * sets the location of the machine
	 *
	 * @param location  the location of machine
	 */
	public void setLocation(String location) {
		this.location =location;
	}

	/**
	 * sets the thingspeak channelId  of the machine
	 *
	 * @param channelId  the channel identifier
	 */
	public void setChannelId(String channelId) {
		this.channelId =channelId;
	}

	/**
	 * gets the number of total candy dispensed by the machine till now
	 * @return totalCandy the total candy
	 */
	public String totalCandy() {
		return totalCandy;
	}

	/**
	 * gets the number of  candy currently in the machine
	 * 
	 * @return currentCandy  the current candy
	 */
	public String currentCandy() {
		return currentCandy;
	}

	/**
	 * gets the datTime list of values of all the times when candy was dispensed from the machine
	 * @return timeLog the record at i in dateTime
	 */
	public String getDateTime(int i) {
		String timeLog = dateTime.get(i);
		return timeLog;
	}

	/**
	 * gets the location of the machine
	 * 
	 * @return location the location of machine
	 */
	public String location() {
		return location;
	}

	/**
	 * gets the thingspeak channelId of the machine
	 * 
	 * @return channelId the thingspeak channelId of the machine
	 */
	public String channelId() {
		return channelId;
	}

	/**
	 * gets the name of the machine
	 * 
	 * @return name the name of the machine
	 */
	public String name() {
		return name;
	}

	/**
	 * gets the size of datTime list
	 * 
	 * @return size the size of datTime list
	 */
	public int size() {
		int size = dateTime.size();
		return size;
	}



}
