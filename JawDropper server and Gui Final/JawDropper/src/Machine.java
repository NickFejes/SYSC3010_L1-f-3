
public class Machine implements Comparable<Machine>  {
	String name;
	String channelId;
	String location;
	Integer totalCandyDispensed;

	public Machine(String n,String c){
		name =n;
		channelId =c;
		totalCandyDispensed = 0;
		
	}
	
	public Machine(String n,Integer t){
		name =n;
		totalCandyDispensed = t;
	}
	public String channelId() {

		return channelId;
	}
	public String name() {

		return name;
	}

	public String nameWithId() {

		String IdName = name +"("+"ID:"+channelId+")";

		return IdName;
	}
	
	public  Integer totalCandyDispensed() {
		
		return totalCandyDispensed;
	}

	public void addTotalCandyDispensed(int i) {

		totalCandyDispensed = i;
	}

	public int compareTo(Machine machine) {
		return this.totalCandyDispensed().compareTo(machine.totalCandyDispensed());
	}


}
