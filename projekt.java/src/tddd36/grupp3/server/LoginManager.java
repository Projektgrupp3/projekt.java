package tddd36.grupp3.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import org.json.JSONException;


public class LoginManager implements Runnable {

	public final static int AUTH_FAILED = 1;
	public final static int AUTH_OK = 2;
	public final static int LOGGED_OUT = 9;
	//	public final static int NOT_ASSOCIATED = 3;
	//	public final static int ASSOCIATED = 4;

	public MultipleSocketServer serversocket;

	//	private String username;
	//	private String password;
	private String adress;

	public LoginManager(MultipleSocketServer serversocket, String adress){
		System.out.println("LoginManager created");
		this.adress = adress;
		this.serversocket = serversocket;
	}

	public void notifyAndSet(int i){
		serversocket.setAuthentication(i);
	}
	public void logout(){
		Association.removeUser(serversocket.getUser());
		notifyAndSet(LOGGED_OUT);
	}

	@Override
	public void run() {
		if(serversocket.getRequestType() != RequestType.LOGOUT){
			if(Database.checkUser(serversocket.getUser()) && 
					Database.getUserPass(serversocket.getUser()).equals(serversocket.getPassword())){
				Association.addUser(serversocket.getUser(), adress);
				notifyAndSet(AUTH_OK);

			}
			else{
				notifyAndSet(AUTH_FAILED);
			}
		}
		else
			logout();
	}
}
