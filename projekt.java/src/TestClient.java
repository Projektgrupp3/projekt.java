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
	private static String username="user_", password="pass_";
	private static final String COM_IP = "130.236.226.59";
	private static final int COM_PORT = 4444;

	/**
	 * @param args
	 * @throws IOException 
	 */

	public TestClient() throws IOException{
	}

	public static void createServerConnection() throws UnknownHostException, IOException{
		client = new Socket(COM_IP,COM_PORT);

		in = new InputStreamReader(client.getInputStream());
		out = new PrintWriter(client.getOutputStream(),true);
		reader = new BufferedReader(in);

		System.out.println("Connected to Server @ "+COM_IP+":"+COM_PORT);
	}

	public static void waitForInput() throws IOException{
		while(true){
			System.out.println("Input your username:");
			Scanner in = new Scanner(System.in);
			username +=in.nextLine();
			send(username);
			System.out.println("Input your password:");
			password += in.nextLine();
			send(password);		
			System.out.println("Sending user authentication..");
			String serverInput;	
			if((serverInput = reader.readLine()) != ""){
				System.out.println("Server: "+serverInput);
			}
		}
	}

	public static void send(String output){
		out.println(output);
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		createServerConnection();
		waitForInput();
	}

}
