import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class TestClient {

	private static Socket client;
	private static InputStreamReader isr;
	private static PrintWriter pw;
	private static BufferedReader br;
	private static String username, password, serverOutput, clientInput, accept;
	private static final String COM_IP = "130.236.226.159";
	private static final int COM_PORT = 4444;
	private static final int LISTEN_PORT = 4445;
	private static ServerSocket serverSocket;
	private static Socket socket;

	static Scanner in = new Scanner(System.in);

	/**
	 * @param args
	 * @throws IOException, UnknownHostException
	 */

	public TestClient() throws UnknownHostException, IOException{
	}
	public static void establishConenction() throws UnknownHostException, IOException{
		client = new Socket(COM_IP,COM_PORT);
		pw = new PrintWriter(client.getOutputStream(),true);
	}

	public static void waitForServer(){
		boolean lyssnar = true;
		try {
			serverSocket = new ServerSocket(LISTEN_PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(lyssnar){
			try {
				socket =  serverSocket.accept();
				isr = new InputStreamReader(client.getInputStream());
				br = new BufferedReader(isr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	public static void login() throws IOException{
		System.out.println("Input your username:");
		username = in.nextLine();
		send(username);
		System.out.println("Input your password:");
		password = in.nextLine();
		send(password);		
		System.out.println("Sending user authentication..");
		if((serverOutput = br.readLine()) != ""){
			System.out.println("Server: "+serverOutput);
			if(!serverOutput.equals("authenticated")) 
				System.exit(0);
			else
				System.out.println("auth succeded");
		}
		pw.close();
		isr.close();
		br.close();
		socket.close();
	}

	public void acceptAlarm() throws IOException{
		//String accident; 
		//accident = new JSONObject().put("accidentType",accidentType).toString(); 
		System.out.println("Accept alarm (yes):");
		accept =in.nextLine();
		send(accept);	
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
		establishConenction();
		waitForServer();
		login();
	}

}
