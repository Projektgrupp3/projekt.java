package tddd36.grupp3.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class MultipleSocketServer implements Runnable {
	private Socket connection;

	private int ID = 0;
	private int AUTH_STATUS = 0;
	private static final int LISTEN_PORT = 4445;

	private String input;
	private String request;
	private String acknowledge;
	private String user;
	private String password;

	private JSONObject JSONInput;
	private JSONObject JSONOutput;

	private String[] ipToUpdate;

	private RequestType requestType;

	public MultipleSocketServer(Socket connection, int i) {
		this.connection = connection;
		this.ID = i;
	}

	public static void main(String[] args) {
		int count = 0;
		Database.addUser(new User("enhet1", "password1"));
		Database.addUser(new User("enhet2", "password2"));
		Database.addUser(new User("enhet3", "password3"));
		Database.addUnit(new Unit(0, "ABC123"));

		// Association.addUser("testuser", "130.236.226.171");

		try {
			ServerSocket serversocket = new ServerSocket(LISTEN_PORT);

			System.out.println("Servern startad.");

			Runnable commandrunnable = new CommandThread();
			Thread commandthread = new Thread(commandrunnable);
			commandthread.start();

			while (true) {
				Socket connection = serversocket.accept();
				Runnable runnable = new MultipleSocketServer(connection,
						++count);
				Thread connectionthread = new Thread(runnable);
				connectionthread.start();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(connection
					.getInputStream());
			BufferedReader br = new BufferedReader(isr);

			Runnable loginRunnable = new LoginManager(this, connection
					.getInetAddress().getHostAddress());
			Thread loginThread = new Thread(loginRunnable);

			input = br.readLine();
			interpretJSONString(input);

			loginThread.start();

			while (AUTH_STATUS == 0) {
				System.out.println("Väntar på AUTH_STATUS");
			}

			if (AUTH_STATUS != 9) {
				JSONOutput = new JSONObject();
				System.out.println(AUTH_STATUS);

				ipToUpdate = new String[1];
				ipToUpdate[0] = connection.getInetAddress().getHostAddress();

				switch (AUTH_STATUS) {
				case 1:
					JSONOutput.put("auth", "authfailed");
					break;
				case 2:
					JSONOutput.put("auth", "authenticated");
					handleRequest();
					break;
				}
				System.out.println(JSONOutput.toString());
				for (String ip : ipToUpdate) {
					if (ip != null)
						Sender.send(JSONOutput, ip);
				}
			}
			isr.close();
			br.close();
		} catch (Exception e) {
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleAcknowledge() throws JSONException {
		String ack_type = JSONInput.getString("ack");
		
		if(ack_type.equals("unit")){
			System.out.println(JSONInput.get("unit"));
			//MySQLDatabase.setUsersUnit(user, UnitID);
		}
		if(ack_type.equals("event")){
			System.out.println(JSONInput.get("event"));
			System.out.println(JSONInput.toString());
		}

	}

	private void handleMapObject() throws JSONException {
		System.out.println(JSONInput.toString());
		HashMap<String, String> associations;
		associations = Association.getUserIpAssociations();

		Object[] ip;
		Object[] usernames;

		usernames = associations.keySet().toArray();
		ip = associations.values().toArray();

		JSONOutput = new JSONObject();
		JSONOutput.put("MAP_OBJECTS", JSONInput.getString("req"));
		JSONOutput.put("header", JSONInput.getString("header"));
		JSONOutput.put("description", JSONInput.getString("description"));
		JSONOutput.put("tempCoordX", JSONInput.getString("tempCoordX"));
		JSONOutput.put("tempCoordY", JSONInput.getString("tempCoordY"));
		JSONOutput.put("eventID", JSONInput.get("eventID"));

		ipToUpdate = new String[usernames.length];

		for (int i = 0; i < usernames.length; i++) {
			if (!usernames[i].equals(user)) {
				ipToUpdate[i] = (String) ip[i];
				System.out.println("Update ska skickas till: " + usernames[i]
						+ " @ " + ipToUpdate[i]);
			}
		}
	}

	private void handleAllUnits() throws JSONException {
		ArrayList<String> units;
		units = MySQLDatabase.getAllUnits();
		int count = 0;
		for (String str : units) {
			System.out.println(str);
			JSONOutput.put("unit" + count, str);
			count++;
		}
		JSONOutput.put("ALL_UNITS", count);
	}

	private void handleContact() throws JSONException {
		Contact c = new Contact(JSONInput.getString("contactName"), JSONInput
				.getString("sipaddress"));
		HashMap<String, String> testing = Association.getUserIpAssociations();
		MySQLDatabase.setContact(c);
		Object[] users;
		Object[] userip;

		users = testing.keySet().toArray();
		userip = testing.values().toArray();

		for (int i = 0; i < users.length; i++) {
			if (!userip[i].toString().equals(Association.getIP(getUser()))) {
				try {
					Sender.sendContact(c, 4445, userip[i].toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void handleRequest() throws JSONException {
		switch (requestType) {
		case ALL_UNITS:
			handleAllUnits();
			break;
		case ACKNOWLEDGE:
			handleAcknowledge();
			break;
		case MAP_OBJECTS:
			handleMapObject();
			break;
		case EVENT:
			break;
		case ALL_CONTACTS:
			ArrayList<Contact> contacts = MySQLDatabase.getAllContacts();
			String ip = Association.getIP(user).toString();
			Sender.sendContacts(contacts, ip, 4445);
			break;
		case CONTACT:
			handleContact();
			break;
		}
	}

	private void interpretJSONString(String input) throws JSONException {
		JSONInput = new JSONObject(input);

		System.out.println(input);

		if (JSONInput.has("user")) {
			this.user = JSONInput.getString("user");
		}
		if (JSONInput.has("pass")) {
			this.password = JSONInput.getString("pass");
		}
		if (JSONInput.has("req")) {
			this.request = JSONInput.getString("req");

			if (request.equals("getContacts")) {
				requestType = RequestType.ALL_CONTACTS;
			}
			if (request.equals("contact")) {
				requestType = RequestType.CONTACT;
			}
			if (request.equals(RequestType.ALL_UNITS.toString())) {
				requestType = RequestType.ALL_UNITS;
			}
			if (request.equals(RequestType.MAP_OBJECTS.toString())) {
				requestType = RequestType.MAP_OBJECTS;
			}
			if (request.equals(RequestType.LOGOUT.toString())) {
				requestType = RequestType.LOGOUT;
			}
		}
		if (JSONInput.has("ack")) {
			this.acknowledge = (String) JSONInput.get("ack");
			requestType = RequestType.ACKNOWLEDGE;
			System.out.println("Klienten ackade: " + acknowledge);
		}
	}

	public void setAuthentication(int AUTH_STATUS) {
		this.AUTH_STATUS = AUTH_STATUS;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
}
