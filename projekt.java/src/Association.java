import java.util.ArrayList;

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
