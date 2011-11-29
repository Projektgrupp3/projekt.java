package tddd36.grupp3.server;

import java.util.Observable;


public class LoginManager implements Runnable {

	public final static int AUTH_FAILED = 1;
	public final static int AUTH_OK = 2;
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

	@Override
	public void run() {
		if(Database.checkUser(serversocket.getUser()) && 
				Database.getUserPass(serversocket.getUser()).equals(serversocket.getPassword())){
			Association.addUser(serversocket.getUser(), adress);
			notifyAndSet(AUTH_OK);
		}
		else{
			notifyAndSet(AUTH_FAILED);
		}
	}
}
