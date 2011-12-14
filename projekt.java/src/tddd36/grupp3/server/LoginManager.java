package tddd36.grupp3.server;



public class LoginManager implements Runnable {

	public final static int AUTH_FAILED = 1;
	public final static int AUTH_OK = 2;
	public final static int LOGGED_OUT = 9;
	public static int CURRENT_AUTH;
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

	@Override
	public void run() {
		System.out.println("LoginManager kör");
		if(serversocket.getRequestType() != RequestType.LOG_OUT){
			if(MySQLDatabase.checkUser(serversocket.getUser()) && 
					MySQLDatabase.getUserPass(serversocket.getUser()).equals(serversocket.getPassword())){
				Association.addUser(serversocket.getUser(), adress);
				CURRENT_AUTH = AUTH_OK;
			}
			else{
				CURRENT_AUTH = AUTH_FAILED;
			}
		}
		else{
			MySQLDatabase.logoutUser(serversocket.getUser());
			Association.removeUser(serversocket.getUser());
			CURRENT_AUTH = LOGGED_OUT;
			System.out.println(serversocket.getUser()+" @ "+adress+" har loggat ut.");
		}
		System.out.println("LoginManager avslutad");
		notifyAndSet(CURRENT_AUTH);
	}
}
