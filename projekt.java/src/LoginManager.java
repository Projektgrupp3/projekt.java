import java.net.InetAddress;
import java.util.Observable;


public class LoginManager extends Observable implements Runnable {
	
	public final static int PASSWORD_FAILED = 1;
	public final static int USER_FAILED = 2;
	
	private boolean knownUser;
	private boolean knownAdress;
	
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
		
		
	}

	@Override
	public void run() {

	}
	
	
}
