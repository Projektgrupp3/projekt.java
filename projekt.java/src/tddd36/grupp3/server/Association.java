package tddd36.grupp3.server;

import java.util.Collection;
import java.util.HashMap;

/**
 * Klass som innehåller en arraylist som lagrar anslutna ip-adresser. Än så
 * länge finns ingen metod för att rensa listan utan den nollställs när man
 * startar om servern.
 * 
 * @author Bauwie
 * 
 */
public class Association {

	private static HashMap<String, String> userIpAssociations = new HashMap<String, String>();

	// username, ip

	public static void addUser(String name, String ip) {
		userIpAssociations.put(name, ip);
	}

	public static String getUser(String useradress) {
		Object[] ip;
		Object[] users;

		users = userIpAssociations.keySet().toArray();
		ip = userIpAssociations.values().toArray();

		for (int i = 0; i < users.length; i++) {
			if (ip[i].equals(useradress))
				return (String) users[i];
		}
		return null;
	}

	public static String getIP(String username) {
		Object[] ip;
		Object[] users;

		users = userIpAssociations.keySet().toArray();
		ip = userIpAssociations.values().toArray();

		for (int i = 0; i < users.length; i++) {
			if (users[i].equals(username))
				return (String) ip[i];
		}
		return null;
	}

	public static void setUserIpAssociations(
			HashMap<String, String> userIpAssociations) {
		Association.userIpAssociations = userIpAssociations;
	}

	public static HashMap<String, String> getUserIpAssociations() {
		return userIpAssociations;
	}

	public static void printAll() {
		Object[] ip;
		Object[] users;

		users = userIpAssociations.keySet().toArray();
		ip = userIpAssociations.values().toArray();

		for (int i = 0; i < users.length; i++) {
			System.out.println("User: " + users[i] + " @ " + ip[i]);
		}
	}

}
