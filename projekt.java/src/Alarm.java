import java.util.Scanner;
import java.util.regex.Pattern;


public class Alarm {
	private String accidentType;
	private String coordinateX;
	private String coordinateY;
	private int numberOfInjured;
	private Time time;
	public String tid;
	Prio priority; 
	private String adress = "Hejhej";
	private String typeOfInjury;
	private String accidentAdress;

	public Alarm(){
	}

	public Alarm(Prio Prio){
		this.priority = priority;
	}

	public Alarm(String AccidentType, String CoordinateX, 
			String CoordinateY, Prio Priority, int NumberOfInjured, Time Time, 
			String Adress, String TypeOfInjury, String AccidentAdress){

		this.numberOfInjured = NumberOfInjured;
		this.accidentType = AccidentType;
		this.coordinateX = CoordinateX;
		this.coordinateY = CoordinateY;
		this.time = Time;
		this.priority = Priority;
		this.adress = Adress;
		this.typeOfInjury = TypeOfInjury;
		this.accidentAdress = AccidentAdress;
	}

	public void createAlarm(){
		String tempCoord;
		Time t = new Time();
		tid = t.getTime();
		System.out.println("Alarm crated: " + tid);

		Scanner in = new Scanner(System.in);

		System.out.println("Please enter prio 1-3: ");
		switch(in.nextInt()){

		case 1: 
			this.priority = Prio.RED;
			break;
		case 2: 
			this.priority = Prio.YELLOW;
			break;
		case 3: 
			this.priority = Prio.GREEN;
			break;
		}
		in.nextLine();
		
		System.out.println("Please enter an adress: ");
		this.accidentAdress = in.nextLine();
		
		do{
			System.out.println("Enter X-coordinates: ");
		}
		while(!CheckCoordinateX(tempCoord = in.nextLine()));{
			this.coordinateX = tempCoord;
		}
		do{
			System.out.println("Enter Y-coordinates: ");
		}
		while(!CheckCoordinateY(tempCoord = in.nextLine()));{
			this.coordinateY = tempCoord;
		}
		System.out.println("Please enter type of accident: ");
		this.accidentType = in.nextLine();	

		System.out.println("Please enter number of injured: ");
		this.numberOfInjured = in.nextInt();
		in.nextLine();

		System.out.println("Type of injury/injuries: ");
		this.typeOfInjury = in.nextLine();

	}

	public boolean CheckCoordinateX(String tempStringX){

		if(Pattern.matches("[0-9]{8}", tempStringX)) {
			return true;

		}
		else
			System.out.println("false");
		return false;
	}
	public boolean CheckCoordinateY(String tempStringY){

		if(Pattern.matches("[0-9]{8}", tempStringY)) {
			return true;
		}
		else
			System.out.println("false");
		return false;
	}

	public int getNumberOfInjured() {
		return numberOfInjured;
	}
	public void setNumberOfInjured(int numberOfInjured) {
		this.numberOfInjured = numberOfInjured;
	}

	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType){
		this.accidentType = accidentType;

	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) {
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) {
		this.coordinateY = coordinateY;
	}

	public String getTime() {
		// TODO Auto-generated method stub
		return tid;
	}

	public Prio getPriority() {
		return priority;
	}

	public void setPriority(Prio priority) {
		this.priority = priority;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		System.out.println("set adress");
		this.adress = adress;
	}

	public String getTypeOfInjury() {
		return typeOfInjury;
	}

	public void setTypeOfInjury(String typeOfInjury) {
		this.typeOfInjury = typeOfInjury;
	}



	public void setAccidentAdress(String accidentAdress) {
		this.accidentAdress = accidentAdress;
	}

	public static void main(String[] args){
		//		User u = new User();
		//		u.createUser();
	}

	public String getAccidentAdress() {
		// TODO Auto-generated method stub
		return accidentAdress;
	}
}
