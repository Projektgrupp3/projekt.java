import java.util.ArrayList;

public class Database {

	public static ArrayList<User> users = new ArrayList<User>();

	public static void addUser(User u){
		users.add(u);
	}

	public static boolean checkUser(String name){
		for(User u : users){
			if(u.getUserName().equals(name)){
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

	public static void main(String[] args){

	}
}
