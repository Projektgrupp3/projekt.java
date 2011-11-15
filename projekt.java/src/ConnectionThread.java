import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ConnectionThread implements Runnable, Observer {
	
	private Socket socket = null;
	private User connectedUser;
	private BufferedReader in;
	private String message;

	public ConnectionThread(Socket socket) {
		this.socket = socket;
		System.out.println("Connection Thread Created");
	}

	public void run() {
		
		try {
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			
			evaluateMessage();
			
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void evaluateMessage() throws IOException{
		
		LoginManager loginManager = new LoginManager(this, socket.getInetAddress().getHostAddress());
		
		message = in.readLine();
		System.out.println("Message from client: "+message);
		
		if(message.equals("newuser")){
			MySQLDatabase.addUser(new User("kungen2", "kaffeflicka"));
		}
		
	}
	
	public void setUser(User u){
		this.connectedUser = u;
	}
	
	public User getUser(){
		return connectedUser;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
