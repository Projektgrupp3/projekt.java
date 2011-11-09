import java.util.Scanner;


public class Unit {

	private int unitId;
	private String regnr;
	private Status state;
	private String name;
	public Unit(){
		createUnit();
	}
	public Unit(int id, String regnr){
		this.unitId = id;
		this.regnr = regnr;
		state = Status.OFFLINE;
		this.name = "Ambulans "+id;
	}

	public void createUnit(){
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a unitId");
		this.unitId= in.nextInt();

		System.out.println("Please enter a registration number");
		in.nextLine();
		this.regnr = in.nextLine();
	}

	public int getId() {
		return unitId;
	}

	public void setId(int id) {
		this.unitId = id;
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
