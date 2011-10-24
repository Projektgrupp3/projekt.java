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

	/**
	 * @param args
	 * @throws IOException 
	 */

	public TestClient() throws IOException{
	}

	public static void createServerConnection() throws UnknownHostException, IOException{
		String ip = "130.236.227.23";
		ip = "192.168.1.3";
		int port = 4444;
		client = new Socket(ip,port);

		in = new InputStreamReader(client.getInputStream());
		out = new PrintWriter(client.getOutputStream(),true);
		reader = new BufferedReader(in);

		System.out.println("Connected to Server @ "+ip+":"+port);
	}

	public static void waitForInput() throws IOException{

		while(true){

			System.out.println("Input?");
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			send(input);
			
			String serverInput;	
			if((serverInput = reader.readLine()) != ""){
				System.out.println("From server: "+serverInput);
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
