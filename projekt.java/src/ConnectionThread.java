import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionThread extends Thread implements Runnable, Observer {

	private Socket socket = null;
	private User connectedUser;
	private BufferedReader in;
	private LoginManager loginManager;
	private JSONObject fromClient;

	private String message;
	private String input;
	private String user;
	private String password;

	private int state;


	public ConnectionThread(Socket socket) {
		this.socket = socket;
		System.out.println("Connection Thread Created");
	}

	public void jsonManager() throws JSONException{
		fromClient = new JSONObject(input);

		if(fromClient.has("user")){
			this.user = fromClient.getString("user");
		}
		if(fromClient.has("pass")){
			this.password = fromClient.getString("pass");
		}
		if(fromClient.has("msg")){
			this.message = fromClient.getString("msg");
		}
	}

	public void run() {

		try {
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));

			input = in.readLine();
			System.out.println(input);
			jsonManager();

			manageUserLogin();

			evaluateMessage();

			disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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

		if(message != null)
			System.out.println("Message from client: "+message);
		disconnect();
	}

	public void manageUserLogin() throws IOException{
		String ip = socket.getInetAddress().getHostAddress();
		loginManager = new LoginManager(this, ip);
		loginManager.run();
	}

	public void login(){
		
		try {
			loginManager.lookup(user,password);
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
			case 2:
				System.out.println("Login krävs");
				login();
				break;
			case 3:
				System.out.println(socket.getInetAddress().getHostAddress()+" associerad med server");
				//Send.send("authenticated", socket.getInetAddress().getHostAddress());
				break;
			}
			//			AUTH_FAILED = 0;
			//			AUTH_OK = 1;
			//			NOT_ASSOCIATED = 2;
			//			ASSOCIATED = 3;
		}
	}
}
