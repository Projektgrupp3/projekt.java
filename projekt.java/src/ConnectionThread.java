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

	private int state;

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

			evaluateMessage();

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
			if(state == 3){
				message = in.readLine();
				message = in.readLine();
			}

			message = in.readLine();

			System.out.println("Message from client: "+message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void manageUserLogin() throws IOException{
		String ip = socket.getInetAddress().getHostAddress();
		loginManager = new LoginManager(this, ip);
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
			int state = ((Integer) data).intValue();
			System.out.println(state);
			switch(state){
			case 0:
				Send.send("authfailed", socket.getInetAddress().getHostAddress());
				disconnect();
				break;
			case 1:
				System.out.println(socket.getInetAddress().getHostAddress()+" angav rätt username & pass");
				Association.addAdressAssociation(socket.getInetAddress().getHostAddress());
				Send.send("authenticated", socket.getInetAddress().getHostAddress());
				break;
			case 2: // Ip finns inte associerad med server
				System.out.println("login");
				login();
				break;
			case 3: // Ip redan associerad med server
				System.out.println(socket.getInetAddress().getHostAddress()+" associerad med server");
				Send.send("authenticated", socket.getInetAddress().getHostAddress());
				break;
			}
		}
	}
}
