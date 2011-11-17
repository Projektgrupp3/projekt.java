import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;


public class Send {
	
	private static String COM_IP = "130.236.226.186";
	private static int COM_PORT = 4445;
	private static PrintWriter pw;
	private static Socket s;
	
	public static void send(String message, String ip){
		//COM_IP = ip;
		try {
			setUpConnection();
			pw.println(message);
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void send(String message, String ip, int port){
		COM_IP = ip;
		COM_PORT = port;
		try {
			setUpConnection();
			pw.println(message);
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void send(JSONObject object, String ip){
		COM_IP = ip;
		
		try {
			setUpConnection();
			pw.println(object.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void send(JSONObject object, String ip, int port){
		COM_IP = ip;
		COM_PORT = port;
		
		try {
			setUpConnection();
			pw.println(object.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setUpConnection() throws UnknownHostException, IOException{
		s = new Socket(COM_IP, COM_PORT);
		pw = new PrintWriter(s.getOutputStream(), true);
	}
	
	public static void closeConnection() throws IOException{
		pw.close();
		s.close();
	}
}