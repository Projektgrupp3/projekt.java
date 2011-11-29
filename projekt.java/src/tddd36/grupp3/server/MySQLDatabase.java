package tddd36.grupp3.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLDatabase {
	/* Använd denna istället för 
	 * Database i MultiServer.
	 * TODO: Fixa så att ändringar ändras på båda tables.
	 */

	public static Connection con = null;
	public static ResultSet rs = null;
	public static Statement st = null;

	public static void addUser(User u){
		if(checkUser(u.getUserName())){
			System.out.println(u.getUserName()+" already exists in the database");
		}

		else{
			String firstName = u.getFirstName();
			String lastName = u.getLastName();
			String userName = u.getUserName();
			String password = u.getPassword();
			int UnitID = u.getUnitID();
			String assignedUnits = "?";
			connect();
			st=null;
			try {
				st=con.createStatement();
				String query = "INSERT INTO user(firstName,lastName,userName,Password,UnitID,assignedUnits) " +
						"VALUES('"+firstName+"','"+lastName+"','"+userName+"','"+password+"','"+
						UnitID+"','"+assignedUnits+"')";
				st.executeUpdate(query);		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnect();
		}
	}

	public static void addUnit(Unit u){
		if(checkUnit(u.getId())){
			System.out.println(u.getId()+" alreade exists in the database");
		}
		else{
			int unitID = u.getId();
			String regNr = u.getRegnr();
			Status status = u.getState();

			if(checkUnit(unitID)){
				System.out.println(unitID+" finns redan i databasen");
			}
			else{
				connect();
				try {
					st=con.createStatement();
					String query = "INSERT INTO units(unitID,regNr,state) VALUES('"+
							unitID+"','"+regNr+"','"+status+"')";
					st.executeUpdate(query);		
				} catch (SQLException e) {
					e.printStackTrace();
				}
				disconnect();
			}
		}
	}

	public static User getUser(String userName){
		if(checkUser(userName)){
			connect();
			try{
				st=con.createStatement();
				String query = "SELECT * from user WHERE userName='"+userName+"'";
				rs = st.executeQuery(query);

				while(rs.next()){
					if(rs.getString(3).equals(userName)){
						String firstName = rs.getString(1);
						String lastName = rs.getString(2);
						String user = rs.getString(3);
						String password = rs.getString(4);
						int unitID = rs.getInt(5);
						User u = new User(firstName, lastName, user,password, unitID);
						disconnect();
						return u;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();}
			disconnect();
		}
		else
			return null;
		return null;
	}

	public static Unit getUnit(int unitID){
		if(checkUnit(unitID)){
			connect();
			try{
				st=con.createStatement();
				String query = "SELECT * from units WHERE unitID='"+unitID+"'";
				rs = st.executeQuery(query);

				while(rs.next()){
					if(rs.getString(1).equals(unitID)){
						int ID = rs.getInt(1);
						String regNr = rs.getString(2);
						//Status status = rs.getString(3);
						Unit u = new Unit(ID, regNr);
						disconnect();
						return u;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();}
			disconnect();
		}
		else
			return null;
		return null;
	}

	public static void deleteUser(String userName){
		System.out.println("Kör: deleteUser");
		if(checkUser(userName)){
			System.out.println("Klarar if");
			connect();
			String query = "DELETE FROM user WHERE userName ='"+userName+"'";
			try{
				st = con.createStatement();
				st.executeUpdate(query);
			}catch(SQLException e){
				e.printStackTrace();
			}
			disconnect();
		}
	}

	public static void deleteUnit(int unitID){
		if(checkUnit(unitID)){
			connect();
			String query = "DELETE FROM unit WHERE UnitID ="+unitID;
			try{
				st = con.createStatement();
				st.executeUpdate(query);
			}catch(SQLException e){
				e.printStackTrace();
			}
			disconnect();
		}
	}

	public static String printAllUsers(){
		connect();
		rs=null;
		Statement stmt = null;
		String query ="SELECT * FROM user";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append("--------------\nUsername: "+rs.getString(3)+
						"\nPassword: "+rs.getString(4)+"\nUnitID: "+rs.getInt(5)+"\n");
			}
			disconnect();
			return sb.toString();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return "Database does not contain any user entries";
	}

	public static String  printAllUnits(){
		connect();
		rs=null;
		Statement stmt = null;
		String query ="SELECT * FROM units";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append("--------------\nunitID: "+rs.getString(1)+
						"\nregnr: "+rs.getString(2)+"\n");
			}
			disconnect();
			return sb.toString();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return "Database does not contain any unit entries";
	}

	public static ArrayList<String> getAllUnits(){
		connect();
		rs=null;
		Statement stmt = null;
		String query ="SELECT * FROM units";
		ArrayList<String> nameIds = new ArrayList<String>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				nameIds.add(rs.getString(4));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return nameIds;
	}

	public static boolean checkUser(String userName){
		connect();
		st=null;
		try{
			st=con.createStatement();
			String query = "SELECT * from user WHERE userName='"+userName+"'";
			rs = st.executeQuery(query);
			while(rs.next()){
				if(rs.getString(3).equals(userName)){
					disconnect();
					return true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		disconnect();
		return false;
	}

	public static boolean checkUnit(int unitID){
		connect();
		st=null;
		try{
			st=con.createStatement();
			String query = "SELECT * from units WHERE unitID="+unitID;
			rs = st.executeQuery(query);
			while(rs.next()){
				if(rs.getInt(1)==unitID){
					disconnect();
					return true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		disconnect();
		return false;
	}

	public static String getUserPass(String userName){
		if(checkUser(userName)){
			connect();
			st=null;
			try{
				st=con.createStatement();
				String query = "SELECT * from user WHERE userName='"+userName+"'";
				rs = st.executeQuery(query);

				while(rs.next()){
					if(rs.getString(3).equals(userName)){
						String pass = rs.getString(4);
						disconnect();
						return pass;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();}

			disconnect();
		}
		return null;
	}

	public static void setUnitToUser(String userName, int UnitID){
		if(checkUser(userName)){
			connect();
			st=null;
			try{
				st=con.createStatement();
				st.executeUpdate("UPDATE user SET assignedUnits = '"+Integer.toString(UnitID)+"' "
						+ "WHERE userName = '"+userName+"'");
				disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getAssignedUnits(String userName) {
		if(checkUser(userName)){
			connect();
			st=null;
			try{
				st=con.createStatement();
				String query = "SELECT * from user WHERE userName='"+userName+"'";
				rs = st.executeQuery(query);
				while(rs.next()){
					if(rs.getString(3).equals(userName)){
						String s = rs.getString(6);
						disconnect();
						return s;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}

			disconnect();
		}
		return "";
	}

	public static String getUserAssignedToUnit(int UnitID){
		if(checkUnit(UnitID)){
			connect();
			st=null;
			try{
				st=con.createStatement();
				String query = "SELECT * from units WHERE unitID='"+UnitID+"'";
				rs = st.executeQuery(query);
				while(rs.next()){
					if(rs.getString(1).equals(UnitID)){
						String s = rs.getString(5);
						disconnect();
						return s;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}

			disconnect();
		}
		return "";
	}
	public static Event getAlarm(int al) {
		//TODO:
		return null;
	}
	public static void printAllAlarms(){
		//TODO:
	}

	public static boolean checkAlarm(int j){
		//TODO:
		return false;
	}
	public static void addAlarm(Event a){
		//TODO:
	}

	public static void setUserAssignedToUnit(String userName, int UnitID){

	}

	public static boolean checkContact(Contact c){
		String name = c.getName();
		connect();
		st=null;
		try{
			st=con.createStatement();
			
			String query = "SELECT * from contacts WHERE name='"+name+"'";
			rs = st.executeQuery(query);
			while(rs.next()){
				if(rs.getString(1)==name){
					disconnect();
					return true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		disconnect();
		return false;

	}

	public static Contact getContact(Contact c){
		String name = c.getName();
		String sipadress = c.getSipaddress();
		if(checkContact(c)){
			connect();
			try{
				st=con.createStatement();
				String query = "SELECT * from contacts WHERE name='"+name+"'";
				rs = st.executeQuery(query);

				while(rs.next()){
					if(rs.getString(1).equals(name)){
						name = rs.getString(1);
						sipadress = rs.getString(2);

						Contact newContact = new Contact (name, sipadress);
						disconnect();
						return newContact;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();}
			disconnect();
		}
		else
			return null;
		return null;
	}


	public static ArrayList<Contact> getAllContacts(){
		connect();
		rs=null;
		Statement stmt = null;
		String query ="SELECT * FROM contacts";
		ArrayList<Contact> contactList = new ArrayList<Contact>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {

				Contact c = new Contact(rs.getString(1), rs.getString(2));

				contactList.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return contactList;
	}

	public static void setContact(Contact c){
		String name = c.getName();
		String sipaddress = c.getSipaddress();
		if(checkContact(c)){
			try {
				connect();
				st=con.createStatement();
				String query = "update contacts SET sipaddress = '"+sipaddress+"' Where name = '"+name+"'";
				st.executeUpdate(query);       
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnect();

		}
		else{
			connect();
			try {
				st=con.createStatement();
				String query = "INSERT INTO contacts(name,sipaddress) VALUES('"+
						name+"','"+sipaddress+"')";
				st.executeUpdate(query);       
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnect();
		}
	}





	public static void connect(){
		String url = "jdbc:mysql://130.236.227.199:3306/entityList";
		String user = "server";
		String password = "starwars";

		try {
			con = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void disconnect(){
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Database.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
		}
	}

	public static void main(String[] args){
		System.out.println(getAllContacts().toString());
	}

}

