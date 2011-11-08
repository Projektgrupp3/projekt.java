import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;


public class Alarm {
	JSONObject json = new JSONObject();
	private int alarmId;
	private String accidentType;
	private String coordinateX;
	private String coordinateY;
	private int numberOfInjured;
	private Time time;
	public String tid;
	Prio priority; 

	private String adress;
	private String typeOfInjury;
	private String accidentAdress;
	private int unitID;

	public Alarm() throws JSONException{
		createAlarm();
	}

	public Alarm(Prio Prio){
		this.priority = priority;
	}

	public Alarm(String AccidentType, String CoordinateX, 
			String CoordinateY, Prio Priority, int NumberOfInjured, Time Time, 
			String TypeOfInjury, String AccidentAdress, int AlarmId, int UnitID){


		this.numberOfInjured = NumberOfInjured;
		this.accidentType = AccidentType;
		this.coordinateX = CoordinateX;
		this.coordinateY = CoordinateY;
		this.time = Time;
		this.priority = Priority;
		this.typeOfInjury = TypeOfInjury;
		this.accidentAdress = AccidentAdress;
		this.alarmId = AlarmId;
		this.unitID = UnitID;
		
	}

	public void createAlarm() throws JSONException{
		String tempCoord;

		Time t = new Time();
		tid = t.getTime();
		System.out.println("Alarm crated: " + tid);

		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter alarm id: ");
		this.alarmId = in.nextInt();

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
		//this.accidentAdress = in.nextLine();
		String adress= in.nextLine();
		json.put("adress",adress);
		setAccidentAdress(adress);

		do{
			System.out.println("Enter X-coordinates: ");
		}
		while(!CheckCoordinateX(tempCoord = in.nextLine()));{
			json.put("tempCoordX",tempCoord);
			setCoordinateX(tempCoord);
		}
		
		do{
			System.out.println("Enter Y-coordinates: ");
		}
		while(!CheckCoordinateY(tempCoord = in.nextLine()));{
			json.put("tempCoordY",tempCoord );
			setCoordinateY(tempCoord);
		}
		
		System.out.println("Please enter type of accident: ");
		String accidentType = in.nextLine();
		json.put("accidentType", accidentType);	
		setAccidentType(accidentType);

		System.out.println("Please enter number of injured: ");
		int number = in.nextInt();
		json.put("numberOfInjured", number);
		setNumberOfInjured(number);

		in.nextLine();

		System.out.println("Type of injury/injuries: ");
		String typeOfInjury = in.nextLine();
		json.put("typeOfInjury", typeOfInjury);
		setTypeOfInjury(typeOfInjury);
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
	public void setNumberOfInjured(int numberOfInjured) throws JSONException {
		json.put("numberOfInjured",numberOfInjured);
		numberOfInjured=json.getInt("numberOfInjured");
		this.numberOfInjured= numberOfInjured;
	}

	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType)throws JSONException {
		json.put("accidentType",accidentType);
		accidentType=json.getString("accidentType");
		this.accidentType= accidentType;
	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) throws JSONException {
		json.put("coordinateX",coordinateX);
		coordinateX=json.getString("coordinateX");
		this.coordinateX= coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) throws JSONException {
		json.put("coordinateY",coordinateY);	
		coordinateY=json.getString("coordinateY");
		this.coordinateY= coordinateY;
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

	public String getTypeOfInjury() {
		return typeOfInjury;
	}

	public void setTypeOfInjury(String typeOfInjury) throws JSONException {
		json.put("typeOfInjury", typeOfInjury);
		this.typeOfInjury= typeOfInjury;
	}

	public void setAccidentAdress(String accidentAdress) throws JSONException {
		json.put("accidentAdress", accidentAdress);
		this.accidentAdress=accidentAdress;
	}

	public String getAccidentAdress() {
		// TODO Auto-generated method stub
		return accidentAdress;
	}

	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}
	
	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public static void main(String[] args){
		//		User u = new User();
		//		u.createUser();
	}
	
}
