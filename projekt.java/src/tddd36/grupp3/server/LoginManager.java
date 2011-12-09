package tddd36.grupp3.server;



public class LoginManager implements Runnable {

	public final static int AUTH_FAILED = 1;
	public final static int AUTH_OK = 2;
	public final static int LOGGED_OUT = 9;
	//	public final static int NOT_ASSOCIATED = 3;
	//	public final static int ASSOCIATED = 4;

	public MultipleSocketServer serversocket;
	private String adress;

	public LoginManager(MultipleSocketServer serversocket, String adress){
		System.out.println("LoginManager skapad");
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
		System.out.println("LoginManager kör");
		if(serversocket.getRequestType() != RequestType.LOG_OUT){
			if(MySQLDatabase.checkUser(serversocket.getUser()) && 
					MySQLDatabase.getUserPass(serversocket.getUser()).equals(serversocket.getPassword())){
				Association.addUser(serversocket.getUser(), adress);
				System.out.println("AUTH OK");
				notifyAndSet(AUTH_OK);
			}
			else{
				System.out.println("AUTH FAILED");
				notifyAndSet(AUTH_FAILED);
			}
		}
		else{
			MySQLDatabase.logoutUser(serversocket.getUser());
			logout();
		System.out.println("LoginManager avslutad");
		}
	}
}
