import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


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
		this.in = new InputStreamReader(client.getInputStream());
		this.out = new PrintWriter(client.getOutputStream(),true);
		this.reader = new BufferedReader(in);
	}
	
	public static void createServerConnection() throws UnknownHostException, IOException{
		client = new Socket("130.236.227.23",4444);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		createServerConnection();
	}

}
