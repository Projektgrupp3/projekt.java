import java.util.Scanner;
import java.util.regex.Pattern;


public class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private int unitID;

	public User (){
	}

	public User (String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	public User (String firstName, String lastName, String userName, String password, int unitID){
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.unitID = unitID;
	}

	public void createUser(){
		Scanner in = new Scanner(System.in);
		String tempPass;
		System.out.println("Please enter a username");
		this.userName = in.nextLine();

		do {
			System.out.println("Please enter a password");
		}
		while(!evaluatePassword(tempPass = in.nextLine()));	

		this.password = tempPass;
	}
	public boolean evaluatePassword(String pathString){
		// 8 tecken, minst en stor, minst en liten, minst ett specialtecken, minst en siffra
		char[] temp = password.toCharArray();
		if(temp.length > 7) {
			return true;
		}
		else
			return false;

	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
