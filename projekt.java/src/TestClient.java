import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;


public class TestClient {

	private static Socket client;
	private static InputStreamReader isr;
	private static PrintWriter pw;
	private static BufferedReader br;
	private static String username, password, serverOutput, clientInput;
	private static final String COM_IP = "130.236.226.59";
	private static final int COM_PORT = 4444;
	Scanner in = new Scanner(System.in);

	/**
	 * @param args
	 * @throws IOException, UnknownHostException
	 */

	public TestClient() throws UnknownHostException, IOException{
		client = new Socket(COM_IP,COM_PORT);

		isr = new InputStreamReader(client.getInputStream());
		pw = new PrintWriter(client.getOutputStream(),true);
		br = new BufferedReader(isr);

		System.out.println("Connected to Server @ "+COM_IP+":"+COM_PORT);
	}

	public void authenticateUser() throws IOException{
		System.out.println("Input your username:");
		username =in.nextLine();
		send(username);
		System.out.println("Input your password:");
		password = in.nextLine();
		send(password);		
		System.out.println("Sending user authentication..");
		if((serverOutput = br.readLine()) != ""){
			System.out.println("Server: "+serverOutput);
			if(!serverOutput.equals("Authenticated")) System.exit(0);
		}
	}

	public void upholdConnection() throws IOException{
		while(true){
			if((clientInput=in.nextLine()) != null){
				if(clientInput.equalsIgnoreCase("exit")) System.exit(0);
				send(clientInput);
			}
			if((serverOutput = br.readLine()) != ""){
				System.out.println("Server: "+serverOutput);
			}
		}
	}

	public static void send(String output){
		pw.println(output);
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		TestClient client = new TestClient();
		client.authenticateUser();
		client.upholdConnection();
	}

}
