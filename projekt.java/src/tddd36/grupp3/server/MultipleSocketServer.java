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

	public static final String REQ_ALL_UNITS = "REQ_ALL_UNITS";
	public static final String REQ_MAP_OBJECTS = "REQ_MAP_OBJECTS";
	public static final String REQ_ALL_CONTACTS = "REQ_ALL_CONTACTS";
	public static final String REQ_CONTACT = "REQ_CONTACT";
	public static final String UPDATE_MAP_OBJECT = "UPDATE_MAP_OBJECT";

	public static final String ACK_RECIEVED_EVENT = "ACK_RECIEVED_EVENT";
	public static final String ACK_ACCEPTED_EVENT = "ACK_ACCEPTED_EVENT";
	public static final String ACK_REJECTED_EVENT = "ACK_REJECTED_EVENT";
	public static final String ACK_STATUS = "ACK_STATUS";
	public static final String ACK_CHOSEN_UNIT = "ACK_CHOSEN_UNIT";
	public static final String ACK_VERIFICATION_REPORT = "ACK_VERIFICATION_REPORT";
	public static final String ACK_WINDOW_REPORT = "ACK_WINDOW_REPORT";

	public static final String LOG_OUT = "LOG_OUT";

	private Socket connection;

	private int ID = 0;
	private int AUTH_STATUS = 0;

	private static final int LISTEN_PORT = 1560;

	private String input;
	private String request;
	private String acknowledge;
	private String user;
	private String password;

	private JSONObject JSONInput;
	private JSONObject JSONOutput;

	private String[] ipToUpdate;
	private int test;

	private RequestType requestType;

	public MultipleSocketServer(Socket connection, int i) {
		this.connection = connection;
		this.ID = i;
	}

	public static void main(String[] args) {
		int count = 0;

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

			while((input = br.readLine()) != null) {

				interpretJSONString(input);

				loginThread.start();

				while (AUTH_STATUS == 0) {
					System.out.print("");
				}
				System.out.println();
				if (AUTH_STATUS != 9) {
					System.out.println("AUTH_STATUS != 9");
					JSONOutput = new JSONObject();

					ipToUpdate = new String[1];
					ipToUpdate[0] = connection.getInetAddress().getHostAddress();

					System.out.println("----------");
					System.out.println("Anslutna enheter: ");
					Association.printAll();
					System.out.println("----------");

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
						System.out.println("Meddelande skickat till " + ip);
					}
				}
				isr.close();
				br.close();
			}
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

		if (acknowledge.equals("unit")) {
			MySQLDatabase.setUsersUnit(JSONInput.getString("user"), JSONInput
					.getString("unit"));
			MySQLDatabase.setUnitsUser(JSONInput.getString("user"), JSONInput
					.getString("unit"),"add");

		} else if (acknowledge.equals("event")) {
			System.out.println("Uppdrag: " + JSONInput.get("eventID")
					+ " har blivit " + JSONInput.get("event"));
			if (JSONInput.getString("event").equals(ACK_RECIEVED_EVENT)) {

			} else if (JSONInput.getString("event").equals(ACK_ACCEPTED_EVENT)) {
				MySQLDatabase.setEvent(JSONInput.getString("user"), JSONInput
						.getString("eventID"));
			} else if (JSONInput.getString("event").equals(ACK_REJECTED_EVENT)) {

			}
		} else if (acknowledge.equals("status")) {
			System.out.println("Användare: " + JSONInput.getString("user")
					+ " status: " + JSONInput.get("status"));
		} else if (acknowledge.equals("report")) {
			if (JSONInput.getString("report").equals(ACK_VERIFICATION_REPORT)) {
				System.out.println("----------");
				System.out.println("Verifikationsrapport mottagen:");
				System.out.println("Event ID: "+JSONInput.getString("eventID"));
				System.out.println("Enhet: "+MySQLDatabase.getUsersUnit(JSONInput.getString("user")));
				System.out.println("Allvarlig händelse: "+JSONInput.getString("seriousEvent"));
				System.out.println("Typ av skador: "+JSONInput.getString("typeOfInjury"));
				System.out.println("Hot / Risker: "+JSONInput.getString("threats"));
				System.out.println("Antal skadade: "+JSONInput.getString("numberOfInjuries"));
				System.out.println("Behöver extra resurser: "+JSONInput.getString("extraResources"));
				System.out.println("Antal % av omrŒdet genomsškt"+JSONInput.getString("areaSearched"));
				System.out.println("Tid för avtransport "+JSONInput.getString("timeOfDeparture"));
				System.out.println("----------");
			} else if (JSONInput.getString("report").equals(ACK_WINDOW_REPORT)) {
				System.out.println("----------");
				System.out.println("Vindruterapport mottagen:");
				System.out.println("Event ID: "+JSONInput.getString("eventID"));
				System.out.println("Enhet: "+MySQLDatabase.getUsersUnit(JSONInput.getString("user")));
				System.out.println("Allvarlig hŠndelse: "+JSONInput.getString("seriousEvent"));
				System.out.println("Typ av skador: "+JSONInput.getString("typeOfInjury"));
				System.out.println("Hot / Risker: "+JSONInput.getString("threats"));
				System.out.println("Antal skadade: "+JSONInput.getString("numberOfInjuries"));
				System.out.println("Behšver extra resurser: "+JSONInput.getString("extraResources"));
				System.out.println("Exakt lokalisation på olycka: "+JSONInput.getString("exactLocation"));
				System.out.println("----------");
			}
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
		JSONOutput.put(UPDATE_MAP_OBJECT, JSONInput.getString("req"));
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
					Sender.sendContact(c, userip[i].toString());
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
			Sender.sendContacts(contacts, ip);
			break;
		case CONTACT:
			handleContact();
			break;
		}
	}

	private void interpretJSONString(String input) throws JSONException {
		JSONInput = new JSONObject(input);

		System.out.println("Meddelande från klient: "+input);

		if (JSONInput.has("user")) {
			this.user = JSONInput.getString("user");
		}
		if (JSONInput.has("pass")) {
			this.password = JSONInput.getString("pass");
		}
		if (JSONInput.has("req")) {
			this.request = JSONInput.getString("req");

			if (request.equals(REQ_ALL_CONTACTS)) {
				requestType = RequestType.ALL_CONTACTS;
			}
			if (request.equals(REQ_CONTACT)) {
				requestType = RequestType.CONTACT;
			}
			if (request.equals(REQ_ALL_UNITS)) {
				requestType = RequestType.ALL_UNITS;
			}
			if (request.equals(UPDATE_MAP_OBJECT)) {
				requestType = RequestType.MAP_OBJECTS;
			}
			if (request.equals(LOG_OUT)) {
				requestType = RequestType.LOG_OUT;
			}
		}
		if (JSONInput.has("ack")) {
			this.acknowledge = (String) JSONInput.get("ack");
			requestType = RequestType.ACKNOWLEDGE;
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
