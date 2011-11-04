import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLDatabase {


	public static Connection con = null;
	public static ResultSet rs = null;
	public static Statement st = null;

	public static void addUser(User u){
		//String firstName = u.getFirstName();
		//String lastName = u.getLastName();
		String userName = u.getUserName();
		String password = u.getPassword();
		//int UnitID =13;
		
		if(checkUser(userName)){
			System.out.println(userName+" finns redan i databasen");
			
		}
		else{
			connect();
			st=null;
			try {
				st=con.createStatement();
				String query = "INSERT INTO user(firstName,lastName,userName,Password,UnitID) " +
						"VALUES('N/A','N/A','"+userName+"','"+password+"','0')";
				//String query = "INSERT INTO user(firstName,lastName,userName,Password,UnitID) VALUES('"+
				//		firstName+"','"+lastName+"','"+userName+"','"+password+"','"+UnitID+"')";
				st.executeUpdate(query);		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnect();
		}
	}	

	public static void addUnit(Unit u){
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
	
	public static User getUser(String name){
		connect();
		try{
			st=con.createStatement();
			String query = "SELECT * from user WHERE userName='"+name+"'";
			rs = st.executeQuery(query);

			while(rs.next()){
				if(rs.getString(3).equals(name)){
					String firstName = rs.getString(1);
					String lastName = rs.getString(2);
					String userName = rs.getString(3);
					String password = rs.getString(4);
					int unitID = rs.getInt(5);
					User u = new User(firstName, lastName, userName,password, unitID);
					disconnect();
					return u;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();}
		disconnect();
		return null;
	}

	public static void deleteUser(User u){
		connect();
		String userName = u.getUserName();
		String query = "DELETE FROM user WHERE Username ="+userName;
		try{
			st = con.createStatement();
			st.executeUpdate(query);
		}catch(SQLException e){
			e.printStackTrace();
		}
		disconnect();
	}
	
	public static void deleteUnit(Unit u){
		connect();
		int UnitID= u.getId();
		String query = "DELETE FROM unit WHERE UnitID ="+UnitID;
		try{
			st = con.createStatement();
			st.executeUpdate(query);
		}catch(SQLException e){
			e.printStackTrace();
		}
		disconnect();
	}

	public static boolean checkUser(String name){
		connect();
		st=null;
		try{
			st=con.createStatement();
			String query = "SELECT * from user WHERE userName='"+name+"'";
			rs = st.executeQuery(query);
			while(rs.next()){
				if(rs.getString(3).equals(name)){
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

	public static boolean checkUnit(int i){
		connect();
		st=null;
		try{
			st=con.createStatement();
			String query = "SELECT * from units WHERE unitID="+i;
			rs = st.executeQuery(query);
			while(rs.next()){
				if(rs.getInt(1)==i){
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
	
	public static String getUserPass(String name){
		connect();
		st=null;
		try{
			st=con.createStatement();
			String query = "SELECT * from user WHERE userName='"+name+"'";
			rs = st.executeQuery(query);
			
			while(rs.next()){
				if(rs.getString(3).equals(name)){
					String pass = rs.getString(4);
					disconnect();
					return pass;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();}
				
		/*for(User u : users){
			if(u.getUserName().equals(name)){
				return u.getPassword();
			}*/
		disconnect();
		return null;
	}
	
	public static void printAllUsers(){
		connect();
    	rs=null;
    	Statement stmt = null;
		String query ="SELECT * FROM user";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("--------------");
				System.out.println("Username: "+rs.getString(3));
				System.out.println("Password: "+rs.getString(4));
				System.out.println("UnitID: "+rs.getInt(5));
	    	 
  	      }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*for(User u : users){
			System.out.println("--------------");
			System.out.println("username: "+u.getUserName());
			System.out.println("password: "+u.getPassword());
			if(u.getUnitID() != 0)
				System.out.println("unitID: "+u.getUnitID());
			System.out.println("--------------");
		}*/
		disconnect();
	}
	
	public static void printAllUnits(){
		connect();
    	rs=null;
    	Statement stmt = null;
		String query ="SELECT * FROM units";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("--------------");
				System.out.println("unitID: "+rs.getString(1));
				System.out.println("regnr: "+rs.getString(2));
				System.out.println("--------------");
  	      }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for(Unit u : unit){
			System.out.println("--------------");
			System.out.println("unitId: "+u.getId());
			System.out.println("regnr: "+u.getRegnr());
			System.out.println("--------------");
		}*/
		disconnect();
	}
	
	public static void connect(){
		String url = "jdbc:mysql://localhost:3306/entityList";
		String user = "server";
		String password = "starwars";

		try {
			con = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to MySQL DB.");
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
			System.out.println("Disconnected from MySQL DB.");
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Database.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
		}
	}
	
	public static void main(String[] args){	

		
		

	}

}

