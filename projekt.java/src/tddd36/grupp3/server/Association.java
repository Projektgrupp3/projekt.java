package tddd36.grupp3.server;
import java.util.ArrayList;
/**
 * Klass som inneh�ller en arraylist som lagrar anslutna ip-adresser.
 * �n s� l�nge finns ingen metod f�r att rensa listan utan den nollst�lls n�r man 
 * startar om servern.
 * @author Bauwie
 *
 */
public class Association {

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
