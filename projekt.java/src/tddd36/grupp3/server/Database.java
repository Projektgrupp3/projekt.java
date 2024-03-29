package tddd36.grupp3.server;
import java.util.ArrayList;
import java.util.Vector;

public class Database {

	public static Vector<Event> alarm = new Vector<Event>();
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Unit> unit = new ArrayList<Unit>();

	public static void addAlarm(Event a){
		alarm.add(a);
	}
	
	public static void addUser(User u){
		users.add(u);
	}	

	public static void addUnit(Unit u){
		unit.add(u);
	}
	
	public static ArrayList<Unit> getAllUnits(){
		return unit;
	}
	
	public static User getUser(String name){
		for(User u : users){
			if(u.getUserName().equals(name)){
				return u;
			}
		}
		return null;
	}
	
	public static Event getAlarm(int al) {
		for (Event a : alarm){
			if(a.getID().equals(al)){
				return a;
			}
	}
		return null;
	}
	
	public static boolean checkUser(String name){
		for(User u : users){
			if(u.getUserName().equals(name)){
				return true;
			}
		}
		return false;
	}

	public static boolean checkUnit(String i){
		for(Unit u : unit){
			if(u.getId() == i){
				return true;
			}
		}
		return false;
	}
	public static boolean checkAlarm(int j){
		for(Event a : alarm){
			if(a.getID().equals(j)){
				return true;
			}
		}
		return false;
	}
	
	public static String getUserPass(String name){
		for(User u : users){
			if(u.getUserName().equals(name)){
				return u.getPassword();
			}
		}
		return null;
	}
	
	public static void printAllAlarms(){
		for(Event a : alarm){
			if(a != null){
			System.out.println("-------------");
			System.out.println("Alarm created: " + a.getTime());
			System.out.println("Alarm id: " + a.getID());
			System.out.println("UnitID: "+a.getUnitID());
			System.out.println("Priority: " + a.getPriority());
			System.out.println("Accident Adress: " + a.getAccidentAdress());
			System.out.println("Coordinates: (X,Y): " + a.getCoordinateX() + "," + a.getCoordinateY());
			System.out.println("Type of accident: " +a.getAccidentType());
			System.out.println("Number of injured: " +a.getNumberOfInjured());
			System.out.println("Type of injury/injuries: " + a.getTypeOfInjury());
			System.out.println("-------------");
			}
		}
	}
	
	public static void printAllUsers(){
		for(User u : users){
			if(u != null){
			System.out.println("--------------");
			System.out.println("username: "+u.getUserName());
			System.out.println("password: "+u.getPassword());
			System.out.println("unitID: "+u.getUnitID());
			System.out.println("--------------");
			}
		}
	}
	
	public static void printAllUnits(){
		for(Unit u : unit){
			System.out.println("--------------");
			System.out.println("unitId: "+u.getId());
			System.out.println("regnr: "+u.getRegnr());
			System.out.println("--------------");
		}

	}
}
