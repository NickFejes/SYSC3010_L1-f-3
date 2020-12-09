/**
 * The Machine class is used to create drop down list for machines page
 * it keeps names and id's of all the machines in database  for the drop down list
 */
public class Machine implements Comparable<Machine>  {
	String name;
	String channelId;
	String location;
	Integer totalCandyDispensed;

	/**
	 * Creates a machine with given name and channel Id
	 *  * It is a constructor. 
	 *  
	 * @param name  the name
	 * @param channelId  the channel identifier
	 */
	public Machine(String name,String channelId){
		this.name =name;
		this.channelId =channelId;
		this.totalCandyDispensed = 0;

	}


	/**
	 * Creates a machine with given name and totalCandyDispensed
	 * It is a constructor. 
	 *
	 * @param name  the name of machine
	 * @param channelId  the thingspeak channel identifier
	 */
	public Machine(String name,Integer totalCandyDispensed){
		this.name =name;
		this.totalCandyDispensed = totalCandyDispensed;
	}
	
	/**
	 * sets the total Candy Dispensed by the machine
	 * 
	 * @param totalCandyDispensed the total Candy Dispensed by the machine
	 */
	public void addTotalCandyDispensed(int totalCandyDispensed) {
		this.totalCandyDispensed = totalCandyDispensed;
	}

	/**
	 * gets channelId of the machine
	 * 
	* @return channelId the thingspeak channel identifier
	 */
	public String channelId() {
		return channelId;
	}

	/**
	 * gets the name of the machine
	 * 
	 *  @return name the name of the machine
	 */
	public String name() {
		return name;
	}

	/**
	 * gets name with id formatted for drop down list
	 * 
	 * @return name with id formatted for drop down list
	 */
	public String nameWithId() {
		String IdwithName = name +"("+"ID:"+channelId+")";
		return IdwithName;
	}

	/**
	 * gets the total Candy Dispensed by the machine
	 * 
	 * @return totalCandyDispensed the total Candy Dispensed by the machine
	 */
	public  Integer totalCandyDispensed() {	
		return totalCandyDispensed;
	}

	/**
	 * compares machines on the basis of total candy dispensed
	 * returns An int value: 0 if the total candy dispensed is equal to the other machine"s total candy dispensed.
	 * < 0 if the total candy dispensed is less than other machine"s total candy dispensed
	 * > 0 if the total candy dispensed greater than the other machine"s total candy dispensed
	 * @return compare compares machines
	 */
	public int compareTo(Machine machine) {
		int compare = this.totalCandyDispensed().compareTo(machine.totalCandyDispensed());
		return compare;
	}


}
