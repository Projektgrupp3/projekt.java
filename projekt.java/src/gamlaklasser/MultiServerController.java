package gamlaklasser;
import ConnectionThread;
import MySQLDatabase;

import java.io.BufferedReader;
import java.io.IOException;


public class MultiServerController extends AbstractServerController {

	public MultiServerController(BufferedReader in) {
		super(in);
		// TODO Auto-generated constructor stub
	}

	public MultiServerController(BufferedReader in, MultiServerView msv) {
		super(in, msv);
		// TODO Auto-generated constructor stub
	}
	public MultiServerController(BufferedReader in, ConnectionThread mst){
		super(in, mst);
	}
	
	//Metoden som fungerar med SQL
	public boolean authenticate() throws IOException{	
		String user;
		String pass;		
		user = getIn().readLine();
		pass = getIn().readLine();
		if(MySQLDatabase.checkUser(user) && MySQLDatabase.getUserPass(user).equals(pass)){
			setUsername(user);
			getMst().setUser(MySQLDatabase.getUser(user));
			System.out.println("User connected: "+user);
			getView().send("Authenticated");
			return true;
		}
		else{
			getView().send("Authentication failed");
			return false;	
		}
	}
//	public boolean authenticate() throws IOException{	
//		String user;
//		String pass;		
//		user = getIn().readLine();
//		pass = getIn().readLine();
//		if(Database.checkUser(user) && Database.getUserPass(user).equals(pass)){
//			setUsername(user);
//			getMst().setUser(Database.getUser(user));
//			System.out.println("User connected: "+user);
//			getView().send("Authenticated");
//			return true;
//		}
//		else{
//			getView().send("Authentication failed");
//			return false;	
//		}
//	}
}
