
public class Unit {
	
	private int id;
	private String regnr;
	private Status state;
	
	public Unit(int id, String regnr){
		this.id = id;
		this.regnr = regnr;
		state = Status.OFFLINE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegnr() {
		return regnr;
	}

	public void setRegnr(String regnr) {
		this.regnr = regnr;
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
		this.state = state;
	}


}
