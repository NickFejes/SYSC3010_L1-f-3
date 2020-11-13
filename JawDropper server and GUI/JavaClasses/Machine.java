
public class Machine {
	String name;
	String channelId;

	public Machine(String n,String c) {
		name =n;
		channelId =c;
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


}
