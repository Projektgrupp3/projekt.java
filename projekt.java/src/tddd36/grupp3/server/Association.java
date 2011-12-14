package tddd36.grupp3.server;

import java.util.Collection;
import java.util.HashMap;

/**
 * Klass som inneh�ller en arraylist som lagrar anslutna ip-adresser. �n s�
 * l�nge finns ingen metod f�r att rensa listan utan den nollst�lls n�r man
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
	public static void removeUser(String username){
		userIpAssociations.remove(username);
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
			System.out.println(users[i] + " @ " + ip[i]);
		}
	}

}
