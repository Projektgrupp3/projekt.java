package tddd36.grupp3.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {
	JSONObject json = new JSONObject();
	private String eventID;
	private String accidentType;
	private String header;
	private String coordinateX;
	private String coordinateY;
	private int numberOfInjured;
	public String tid;
	Prio priority; 
	private String adress;
	private String typeOfInjury;
	private String accidentAdress;
	private String description;
	private String unitID;

	// TODO: MSTE FIXA String message och allt vad det innebŠr

	public Event() throws JSONException{
	}

	public Event(Prio Prio){
		this.priority = priority;
	}
	
	//Map-event
	public Event(String header, String CoordinateX, 
			String CoordinateY,
			String adress, String unitID, String description){

		this.header = header;
		this.coordinateX = CoordinateX;
		this.coordinateY = CoordinateY;
		this.adress = adress;
		this.eventID = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		this.unitID = unitID;
		this.description = description;

	}
	//Injury
	public Event(String AccidentType, String CoordinateX, 
			String CoordinateY, Prio Priority, int NumberOfInjured, 
			String Adress, String TypeOfInjury, String AccidentAdress, String UnitID, String description){

		this.numberOfInjured = NumberOfInjured;
		this.accidentType = AccidentType;
		this.coordinateX = CoordinateX;
		this.coordinateY = CoordinateY;
		this.priority = Priority;
		this.adress = Adress;
		this.typeOfInjury = TypeOfInjury;
		this.accidentAdress = AccidentAdress;
		this.eventID = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		this.unitID = UnitID;
		this.description = description;

	}
	public void createTestEvent() throws JSONException{
		this.eventID = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		System.out.println("EventID: "+eventID);

		json.put("event",eventID);
		json.put("priority","Yellow");
		json.put("adress","E18");
		json.put("tempCoordX","58395730");
		json.put("tempCoordY","15573080" );
		json.put("accidentType", "Bilolycka");	
		json.put("numberOfInjured", "3");
		json.put("typeOfInjury", "Ryggont och öppna skärsr");
		json.put("description", "Ihopörning mellan två bilar på Riksväg 12");
		

	}
	public void createEvent() throws JSONException{
		String tempCoord;

		this.eventID = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		json.put("event",eventID);
		System.out.println("EventID: " + eventID);

		Scanner in = new Scanner(System.in);

		System.out.println("Please enter priority 1-3: ");
		switch(in.nextInt()){

		case 1: 
			this.priority = Prio.RED;
			json.put("priority","red");
			break;
		case 2: 
			this.priority = Prio.YELLOW;
			json.put("priority","yellow");
			break;
		case 3: 
			this.priority = Prio.GREEN;
			json.put("priority","green");
			break;
		}

		in.nextLine();

		System.out.println("Please enter an adress: ");
		this.adress = in.nextLine();
		json.put("adress",adress);

		//		do{
		//			System.out.println("Enter X-coordinates: ");
		//		}
		//		while(!CheckCoordinateX(tempCoord = in.nextLine()));{
		//			json.put("tempCoordX",tempCoord);
		//			this.coordinateX = tempCoord;
		//		}
		//		do{
		//			System.out.println("Enter Y-coordinates: ");
		//		}
		//		while(!CheckCoordinateY(tempCoord = in.nextLine()));{
		//			json.put("tempCoordY",tempCoord );
		//			this.coordinateY = tempCoord;
		//		}
		json.put("tempCoordX","58395730");
		json.put("tempCoordY","15573080" );

		System.out.println("Please enter type of accident: ");
		this.accidentType = in.nextLine();
		json.put("accidentType", accidentType);

		System.out.println("Please enter number of injured: ");
		this.numberOfInjured = in.nextInt();
		json.put("numberOfInjured", numberOfInjured);

		in.nextLine();

		System.out.println("Type of injury/injuries: ");
		this.typeOfInjury = in.nextLine();
		json.put("typeOfInjury", typeOfInjury);

		System.out.println("Description: ");
		this.description = in.nextLine();
		json.put("description", description);

		json.put("unitID","3");

		System.out.println(json);
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
		this.numberOfInjured = numberOfInjured;
	}

	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType)throws JSONException {
		json.put("accidentType",accidentType);
		this.accidentType = accidentType;
	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) throws JSONException {
		json.put("coordinateX",coordinateX);
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) throws JSONException {
		json.put("coordinateY",coordinateY);
		this.coordinateY = coordinateY;
	}

	public String getTime() {
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
		this.adress = adress;
	}

	public String getTypeOfInjury() {
		return typeOfInjury;
	}

	public void setTypeOfInjury(String typeOfInjury)throws JSONException {
		json.put("typeOfInjury", typeOfInjury);
		this.typeOfInjury = typeOfInjury;
	}



	public void setAccidentAdress(String accidentAdress) throws JSONException {
		json.put("accidentAdress", accidentAdress);
		this.accidentAdress = accidentAdress;
	}

	public String getAccidentAdress() {
		return accidentAdress;
	}

	public String getID() {
		return eventID;
	}

	public void setID(String eventID)throws JSONException {
		json.put("eventID", eventID);
		this.eventID = eventID;
	}

	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
		try {
			json.put("unitID", unitID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String processInput(String typeOfAccident) {
		return typeOfAccident;
	}

	public JSONObject getJSON(){
		return json;
	}
}
