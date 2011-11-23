import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Tråd som lyssnar på kommandon från serverns console
 * @author Bauwie
 *
 */

public class CommandThread extends Thread implements Observer, Runnable {

	public CommandThread(){
		System.out.println("New input thread created");
	}

	public void run(){
		Scanner in = new Scanner(System.in);
		String cmd;
		do{
			System.out.println("Enter command");
			cmd = in.nextLine();

			try {
				evalutate(cmd);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}  catch (JSONException e) {
				e.printStackTrace();
			}
		}
		while(true);
	}
	/**
	 * Undersöker vad det är för kommando som matats in
	 * @param input - det angivna kommandot
	 * @throws UnknownHostException
	 * @throws JSONException
	 */
	public void evalutate(String input) throws UnknownHostException, JSONException{
		if(input.equals("/msg")){
			Scanner in = new Scanner(System.in);
			System.out.println("Message?");
			String message;
			message = in.nextLine();
			//Hårdkodad "ip", har inte hittat något sätt att lösa detta på.
			Send.send(message, "234");
		}

		if(input.equals("/createuser")){
			User u = new User();
			u.createUser();
			MySQLDatabase.addUser(u);
		}
		if(input.equals("/createunit")){
			Unit u = new Unit();
			MySQLDatabase.addUnit(u);
		}
		if(input.equals("/createevent")){
			Event a = new Event();
//			MySQLDatabase.addAlarm(a);	Lägg in alarm i Databas
			Send.send(a.getJSON(),"1");

		}
		if(input.equals("/skickalarm")){
			JSONObject a = new JSONObject();
			a.put("larm","");
			a.put("adress","Rydsvägen 1337");
			a.put("numberOfInjured","10");
			a.put("alarmID","312");
			Send.send(a, "1");

		}
		if(input.equals("/print")){
			MySQLDatabase.printAllUsers();
			MySQLDatabase.printAllUnits();
			MySQLDatabase.printAllAlarms();
		}
		if(input.equals("/mapuserunit")){
			Scanner in = new Scanner(System.in);
			String name;
			int unitId;

			do {
				System.out.println("username to map");
				name = in.nextLine();
				System.out.println("unitId to map");
				unitId = in.nextInt(); 
			}
			while(!(MySQLDatabase.checkUser(name)) && !(MySQLDatabase.checkUnit(unitId)));

			User user = MySQLDatabase.getUser(name);
			user.setUnitID(unitId);
			System.out.println("map completed");
		}
		if(input.equals("/mapalarmunit")){
			Scanner in = new Scanner(System.in);
			int alarmId;
			int unitId;

			do{
				System.out.println("alarmId to map");
				alarmId = in.nextInt();
				System.out.println("unitId to map");
				unitId = in.nextInt();
			}
			while(!(MySQLDatabase.checkAlarm(alarmId)) && !(MySQLDatabase.checkUnit(unitId)));

			Event alarm = MySQLDatabase.getAlarm(alarmId);
			alarm.setUnitID(unitId);
			System.out.println("map completed");
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}

