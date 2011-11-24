import java.util.ArrayList;
import java.util.HashMap;
/**
 * Klass som innehåller en arraylist som lagrar anslutna ip-adresser.
 * Än så länge finns ingen metod för att rensa listan utan den nollställs när man 
 * startar om servern.
 * @author Bauwie
 *
 */
public class Association {

	private static HashMap<String, String> connectedClients = new HashMap<String, String>();
	
	private static ArrayList<String> associations = new ArrayList<String>();

	public static boolean getAdressAssociation(String ip){
		for(String str : associations){
			if(ip.equals(str)){
				return true;
			}
		}
		return false;
	}

	public static void addAdressAssociation(String adress){
		associations.add(adress);
	}

	public static void setAssociations(ArrayList<String> assoc) {
		associations = assoc;
	}

	public static ArrayList<String> getAssociations() {
		return associations;
	}

}
