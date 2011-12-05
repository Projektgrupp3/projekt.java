package tddd36.grupp3.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * SERVER-SENDER-KLASS
 */

public class Sender {


	private static String COM_IP;
	private static int COM_PORT = 3334;

	private static PrintWriter pw;
	private static Socket s;

	public static void send(String message, String ip) {
		COM_IP = ip;

		JSONObject jsonMessage = new JSONObject();

		try {
			jsonMessage.put("msg", message);
			setUpConnection();
			pw.println(jsonMessage.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void send(String message, String ip, int port) {
		COM_IP = ip;
		COM_PORT = port;

		JSONObject jsonMessage = new JSONObject();

		try {
			setUpConnection();
			pw.println(jsonMessage.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void send(JSONObject object, String ip) {
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

	public static void send(JSONObject object, String ip, int port) {
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
	public static void sendContact(Contact c, String ip ) throws IOException, JSONException{
		JSONObject test = new JSONObject();
		StringBuffer sb = new StringBuffer();
		COM_IP = ip;
		try {
			String name=c.getName();
			String sipaddress=c.getSipaddress();	
			sb.append(name+","+ sipaddress+"/");
			setUpConnection();
			test.put("contacts", sb);
			pw.println(test.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static void sendContacts(ArrayList<Contact> list, String ip, int port) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		StringBuffer sb = new StringBuffer();
		String name;
		String sipaddress;
		COM_IP = ip;
		COM_PORT = port;
		try {
			setUpConnection();
			for(Contact c: list){
				name=c.getName();
				sipaddress=c.getSipaddress();	
				sb.append(name+","+ sipaddress+"/");
			}
			System.out.println(sb.toString());
			jsonObject.put("contacts", sb);
			System.out.println();
			pw.println(jsonObject.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendContacts(ArrayList<Contact> list, String ip) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		StringBuffer sb = new StringBuffer();
		String name;
		String sipaddress;
		COM_IP = ip;
		try {
			setUpConnection();
			for(Contact c: list){
				name=c.getName();
				sipaddress=c.getSipaddress();	
				sb.append(name+","+ sipaddress+"/");
			}
			jsonObject.put("contacts", sb);
			pw.println(jsonObject.toString());
			closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void broadcastEvent(Event a, String IP){
		String unitID = MySQLDatabase.getUsersUnit(Association.getUser(IP));
		a.setUnitID(unitID);
		ArrayList<String> unitsUser = MySQLDatabase.getUnitsUser(unitID);
		for(String s:unitsUser){
			String tempIP = Association.getIP(s);
			if(tempIP != IP){
				send(a.getJSON(),tempIP);
			}
		}
	}
	
	public static void broadcastString(String a, String IP){
		String unitID = MySQLDatabase.getUsersUnit(Association.getUser(IP));
		ArrayList<String> unitsUser = MySQLDatabase.getUnitsUser(unitID);
		for(String s:unitsUser){
			String tempIP = Association.getIP(s);
			if(tempIP != IP){
				send(a,tempIP);
			}
		}
	}

	public static void setUpConnection() throws UnknownHostException,
			IOException {
		s = new Socket(COM_IP, COM_PORT);
		pw = new PrintWriter(s.getOutputStream(), true);
	}

	public static void closeConnection() throws IOException {
		pw.close();
		s.close();
	}
}
