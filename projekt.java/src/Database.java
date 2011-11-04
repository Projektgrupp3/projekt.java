import java.util.ArrayList;
import java.util.Vector;

public class Database {

	public static ArrayList<User> users = new ArrayList<User>();
	public static Vector<Unit> unit = new Vector<Unit>();

	public static void addUser(User u){
		users.add(u);
	}	

	public static void addUnit(Unit u){
		unit.add(u);
	}
	public static User getUser(String name){
		for(User u : users){
			if(u.getUserName().equals(name)){
				return u;
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

	public static boolean checkUnit(int i){
		for(Unit u : unit){
			if(u.getId() == i){
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
	public static void printAllUsers(){
		for(User u : users){
			System.out.println("--------------");
			System.out.println("username: "+u.getUserName());
			System.out.println("password: "+u.getPassword());
			if(u.getUnitID() != 0)
				System.out.println("unitID: "+u.getUnitID());
			System.out.println("--------------");
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

	public static void main(String[] args){

	}
}
