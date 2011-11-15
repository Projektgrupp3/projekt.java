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
		this.adress = adress;
		addObserver(ct);
	}

	public void lookup(String username, String password){
		this.username = username;
		this.password = password;

		if(MySQLDatabase.checkUser(username) && MySQLDatabase.getUserPass(username).equals(password)){
			setChanged();
			notifyAndSet(AUTH_OK);
		}
		else{
			setChanged();
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
		if(i == 1){
			done = true;
		}
	}

	@Override
	public void run() {

		while(!done){
		}

	}
}
