package tddd36.grupp3.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * SERVER-SENDER-KLASS
 */

public class Sender {


	private static String COM_IP;
	private static int COM_PORT = 3334;

	private static PrintWriter pw;
	private static SSLSocket s;

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
		System.out.println("broadcastEvent");
		// H�mtar unit
		String unitID = MySQLDatabase.getUsersUnit(Association.getUser(IP));
		// H�mtar alla users i unit
		ArrayList<String> unitsUser = MySQLDatabase.getUnitsUser(unitID);
		for(String s:unitsUser){
			if(Association.getIP(s)!=null){
				String tempIP = Association.getIP(s);
				System.out.println("broadcastEvent skickar: "+a.getJSON());
				if(!tempIP.equals(IP)){
					
					send(a.getJSON(),tempIP);
				}
			}
		}
	}

	public static void broadcastString(String a, String IP){
		System.out.println("broadcastString");
		String unitID = MySQLDatabase.getUsersUnit(Association.getUser(IP));
		ArrayList<String> unitsUser = MySQLDatabase.getUnitsUser(unitID);
		for(String s:unitsUser){
			if(Association.getIP(s)!=null){
				System.out.println("broadcastString skickar: "+a);
				String tempIP = Association.getIP(s);
				if(tempIP!=IP){
					send(a,tempIP);
				}
			}
		}
	}

	public static void setUpConnection() throws UnknownHostException,
			IOException {
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		
		s = (SSLSocket) sslsocketfactory.createSocket(COM_IP, COM_PORT);
		s.setEnabledCipherSuites(new String[] { "SSL_DH_anon_WITH_RC4_128_MD5" });
		SSLSession sslsession = s.getSession();
		//s = new Socket(COM_IP, COM_PORT);
		pw = new PrintWriter(s.getOutputStream(), true);
	}

	public static void closeConnection() throws IOException {
		pw.close();
		s.close();
	}
}
