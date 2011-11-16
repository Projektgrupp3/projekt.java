import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ConnectionThread extends Thread implements Runnable, Observer {

	private Socket socket = null;
	private User connectedUser;
	private BufferedReader in;
	private String message;
	private LoginManager loginManager;

	public ConnectionThread(Socket socket) {
		this.socket = socket;
		System.out.println("Connection Thread Created");
	}

	public void run() {

		try {
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));

			manageUserLogin();

			disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void disconnect(){
		try {

			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void evaluateMessage() {

		try {
			message = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Message from client: "+message);

		if(message.equals("newuser")){
			MySQLDatabase.addUser(new User("kungen2", "kaffeflicka"));
		}
	}

	public void manageUserLogin() throws IOException{
		String ip = socket.getInetAddress().getHostAddress();
		loginManager = new LoginManager(this, ip);
		//loginManager.startManager();
		loginManager.run();
	}

	public void login(){
		String username;
		String password;

		try {
			username = in.readLine();
			password = in.readLine();

			loginManager.lookup(username,password);
		} catch (IOException e) {

			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void setUser(User u){
		this.connectedUser = u;
	}

	public User getUser(){
		return connectedUser;
	}

	@Override
	public void update(Observable o, Object data) {
		if(data instanceof Integer){
			int i = ((Integer) data).intValue();
			System.out.println(i);
			switch(i){
			case 0:
				Send.send("authfailed", socket.getInetAddress().getHostAddress());
				disconnect();
				break;
			case 1:
				System.out.println(socket.getInetAddress().getHostAddress());
				Send.send("authenticated", socket.getInetAddress().getHostAddress());
				evaluateMessage();
				break;
			case 2:
				login();
				break;
			}
		}
	}
}
