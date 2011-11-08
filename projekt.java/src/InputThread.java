import java.util.Scanner;

import org.json.JSONException;

/**
 * Tråd som lyssnar på input från serverns console
 *./createuser skapar en ny användare
 *./createunit skapar en ny enhet(ambulans)(unit)
 *./mapuserunit mappar en användare till en enhet(unit)
 * @author Bauwie
 *
 */

public class InputThread extends Thread {

	public InputThread(){
		super("InputThread");
		System.out.println("New thread created");
	}

	public void run(){
		Scanner in = new Scanner(System.in);
		String cmd;
		do{
			System.out.println("Enter command");
			cmd = in.nextLine();
			try {
				evalutate(cmd);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true);


	}
	public void evalutate(String input) throws JSONException{
		if(input.equals("/createuser")){
			User u = new User();
			u.createUser();
			Database.addUser(u);
		}
		if(input.equals("/createunit")){
			Unit u = new Unit();
			Database.addUnit(u);
		}
		if(input.equals("/createalarm")){
			Alarm a = new Alarm();
			Database.addAlarm(a);		//L‰gg in alarm i Database
			a.createAlarm();
			Database.addAlarm(a);
		}
		if(input.equals("/print")){
			Database.printAllUsers();
			Database.printAllUnits();
			Database.printAllAlarms();
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
			while(!(Database.checkUser(name)) && !(Database.checkUnit(unitId)));

			User user = Database.getUser(name);
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
			while(!(Database.checkAlarm(alarmId)) && !(Database.checkUnit(unitId)));
			
			Alarm alarm = Database.getAlarm(alarmId);
			alarm.setUnitID(unitId);
			System.out.println("map completed");
		}
	}

}

