import java.util.Observable;


public class LoginManager extends Observable implements Runnable {

	public final static int AUTH_FAILED = 0;
	public final static int AUTH_OK = 1;
	public final static int NOT_ASSOCIATED = 2;

	public boolean done = false;

	private String username;
	private String password;
	private String adress;


	public LoginManager(ConnectionThread ct, String adress){
		System.out.println("LoginManager created");
		this.adress = adress;
		addObserver(ct);
	}

	public void lookup(String username, String password){
		this.username = username;
		this.password = password;

		if(Database.checkUser(username) && Database.getUserPass(username).equals(password)){
			notifyAndSet(AUTH_OK);
		}
		else{
			notifyAndSet(AUTH_FAILED);
		}
	}
	public void isAssociated(){
		if(Association.getAdressAssociation(adress))
			notifyAndSet(AUTH_OK);
		notifyAndSet(NOT_ASSOCIATED);
	}

	public void notifyAndSet(int i){
		int value = i;
		setChanged();
		notifyObservers(value);
	}

	public void startManager() {
		isAssociated();
	}

	@Override
	public void run() {
		isAssociated();
	}
}
