import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class TestClient {

	private static Socket client;
	private static InputStreamReader in;
	private static PrintWriter out;
	private static BufferedReader reader;
	private static String username="user_", password="pass_", serverInput;
	private static final String COM_IP = "130.236.226.59";
	private static final int COM_PORT = 4444;

	/**
	 * @param args
	 * @throws IOException, UnknownHostException
	 */

	public TestClient() throws UnknownHostException, IOException{
		client = new Socket(COM_IP,COM_PORT);

		in = new InputStreamReader(client.getInputStream());
		out = new PrintWriter(client.getOutputStream(),true);
		reader = new BufferedReader(in);

		System.out.println("Connected to Server @ "+COM_IP+":"+COM_PORT);
	}
	
	public void authenticateUser() throws IOException{
		System.out.println("Input your username:");
		Scanner in = new Scanner(System.in);
		username +=in.nextLine();
		send(username);
		System.out.println("Input your password:");
		password += in.nextLine();
		send(password);		
		System.out.println("Sending user authentication..");
		if((serverInput = reader.readLine()) != ""){
			System.out.println("Server: "+serverInput);
			if(!serverInput.equals("authenticated")) System.exit(0);
		}
	}

//	public void waitForInput() throws IOException{
//		while(true){
//			
//			String serverInput;	
//			
//		}
//	}

	public static void send(String output){
		out.println(output);
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		TestClient client = new TestClient();
		client.authenticateUser();
	}

}
