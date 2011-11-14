import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Tråd som lyssnar på input från serverns console
 *./createuser skapar en ny användare
 *./createunit skapar en ny enhet(ambulans)(unit)
 *./mapuserunit mappar en användare till en enhet(unit)
 * @author Bauwie
 *
 */

public class InputThread extends Thread implements Observer {
	private final String COM_IP = "130.236.226.122";
	private final int COM_PORT = 4445;
	private InputStreamReader isr;
	private PrintWriter pw;
	private BufferedReader br;
	private Socket s;
	
	public InputThread(){
		super("InputThread");
		System.out.println("New thread created");
	}
	public void setUpConnection() throws UnknownHostException, IOException{
		s = new Socket(COM_IP, COM_PORT);
		isr = new InputStreamReader(s.getInputStream());
		pw = new PrintWriter(s.getOutputStream(), true);
		br = new BufferedReader(isr);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true);
	}
	public void evalutate(String input) throws UnknownHostException, IOException, JSONException{
		if(input.equals("/msg")){
			setUpConnection();
			Scanner in = new Scanner(System.in);
			System.out.println("Message?");
			String message;
			message = in.nextLine();
			pw.println(message);
			s.close();
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
		if(input.equals("/createalarm")){
			Alarm a = new Alarm();
			MySQLDatabase.addAlarm(a);	//L‰gg in alarm i Database
			
			
			setUpConnection();
			
//			System.out.println(a.json.toString());
//			JSONObject david = new JSONObject();
//			david.put("namn", "david");
//			String hille = david.toString();
//			JSONObject emil = new JSONObject(hille);
			
			pw.println(a.json.toString());
			s.close();
			
		}
		if(input.equals("/alarm")){
			JSONObject a = new JSONObject();
			a.put("adress","Rydsvägen 1337");
			a.put("numberOfInjured","10");
			a.put("alarmID","312");
			setUpConnection();

			pw.println(a.toString());
			s.close();
			
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
			
			Alarm alarm = MySQLDatabase.getAlarm(alarmId);
			alarm.setUnitID(unitId);
			System.out.println("map completed");
		}
	}
//	public void evalutate(String input) throws UnknownHostException, IOException, JSONException{
//		if(input.equals("/msg")){
//			setUpConnection();
//			Scanner in = new Scanner(System.in);
//			System.out.println("Message?");
//			String message;
//			message = in.nextLine();
//			pw.println(message);
//			s.close();
//		}
//
//		if(input.equals("/createuser")){
//			User u = new User();
//			u.createUser();
//			Database.addUser(u);
//		}
//		if(input.equals("/createunit")){
//			Unit u = new Unit();
//			Database.addUnit(u);
//		}
//		if(input.equals("/createalarm")){
//			Alarm a = new Alarm();
//			Database.addAlarm(a);	//L‰gg in alarm i Database
//			
//			
//			setUpConnection();
//			
////			System.out.println(a.json.toString());
////			JSONObject david = new JSONObject();
////			david.put("namn", "david");
////			String hille = david.toString();
////			JSONObject emil = new JSONObject(hille);
//			
//			pw.println(a.json.toString());
//			s.close();
//			
//		}
//		if(input.equals("/alarm")){
//			JSONObject a = new JSONObject();
//			a.put("adress","Rydsvägen 1337");
//			a.put("numberOfInjured","10");
//			a.put("alarmID","312");
//			setUpConnection();
//
//			pw.println(a.toString());
//			s.close();
//			
//		}
//		if(input.equals("/print")){
//			Database.printAllUsers();
//			Database.printAllUnits();
//			Database.printAllAlarms();
//		}
//		if(input.equals("/mapuserunit")){
//			Scanner in = new Scanner(System.in);
//			String name;
//			int unitId;
//
//			do {
//				System.out.println("username to map");
//				name = in.nextLine();
//				System.out.println("unitId to map");
//				unitId = in.nextInt(); 
//			}
//			while(!(Database.checkUser(name)) && !(Database.checkUnit(unitId)));
//
//			User user = Database.getUser(name);
//			user.setUnitID(unitId);
//			System.out.println("map completed");
//		}
//		if(input.equals("/mapalarmunit")){
//			Scanner in = new Scanner(System.in);
//			int alarmId;
//			int unitId;
//			
//			do{
//				System.out.println("alarmId to map");
//				alarmId = in.nextInt();
//				System.out.println("unitId to map");
//				unitId = in.nextInt();
//			}
//			while(!(Database.checkAlarm(alarmId)) && !(Database.checkUnit(unitId)));
//			
//			Alarm alarm = Database.getAlarm(alarmId);
//			alarm.setUnitID(unitId);
//			System.out.println("map completed");
//		}
//	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}

