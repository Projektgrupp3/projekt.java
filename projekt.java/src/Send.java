import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;


public class Send {
	
	private String COM_IP = "130.236.226.122";
	private int COM_PORT = 4445;
	private PrintWriter pw;
	private Socket s;
	
	public void send(String message, String ip){
		this.COM_IP = ip;
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
	
	public void send(String message, String ip, int port){
		this.COM_IP = ip;
		this.COM_PORT = port;
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
	
	public void send(JSONObject object, String ip){
		this.COM_IP = ip;
		
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
	
	public void send(JSONObject object, String ip, int port){
		this.COM_IP = ip;
		this.COM_PORT = port;
		
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
	
	public void setUpConnection() throws UnknownHostException, IOException{
		s = new Socket(COM_IP, COM_PORT);
		pw = new PrintWriter(s.getOutputStream(), true);
	}
	public void closeConnection() throws IOException{
		pw.close();
		s.close();
	}
}
