package tddd36.grupp3.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Timer;

import misc.EventTimer;

import org.json.JSONException;

/**
 * Tråd som lyssnar på kommandon från serverns console
 * @author Bauwie
 *
 */

public class CommandThread implements Runnable, Observer{

	public CommandThread(){
		System.out.println("Kommandotråd skapad");
	}

	public void run(){
		Scanner in = new Scanner(System.in);
		String cmd;
		do{
			System.out.println("Kommando:");
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

			System.out.println("Till vilket ip?");
			String ip;
			ip = in.nextLine();

			Sender.send(message, ip);
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
			a.createTestEvent();

			Scanner in = new Scanner(System.in);
			System.out.println("Till vilket ip?");
			String ip;
			ip = in.nextLine();
			
			String unitID = MySQLDatabase.getUsersUnit(Association.getUser(ip));
			a.setUnitID(unitID);
			
			//MySQLDatabase.addAlarm(a);	L�gg in alarm i Databas
			//Sender.broadcastEvent(a,ip);

			Sender.send(a.getJSON(),ip);
			Sender.broadcastEvent(a,ip);
//			Runnable EventTimerRunnable = new EventTimer(CommandThread.this);
//			Thread t = new Thread(EventTimerRunnable);
//			t.start();
			
			MySQLDatabase.setEvent(a);
		}
		if(input.equals("/sendevent")){
			Event e = new Event();
			e.createEvent();

			Scanner in = new Scanner(System.in);
			System.out.println("Till vilket ip?");
			String ip;
			ip = in.nextLine();
			
			String unitID = MySQLDatabase.getUsersUnit(Association.getUser(ip));
			e.setUnitID(unitID);

			//			MySQLDatabase.addAlarm(a);	Lägg in alarm i Databas
			Sender.send(e.getJSON(),ip);
			Sender.broadcastEvent(e, ip);
			
//			Runnable EventTimerRunnable = new EventTimer(CommandThread.this);
//			Thread t = new Thread(EventTimerRunnable);
//			t.start();
			MySQLDatabase.setEvent(e);
		}
		if(input.equals("/print")){
			System.out.println(MySQLDatabase.printAllUsers());
			System.out.println(MySQLDatabase.printAllUnits());
			//MySQLDatabase.printAllAlarms();
		}
		if(input.equals("/mapuserunit")){
			Scanner in = new Scanner(System.in);
			String name;
			String unitId;

			do {
				System.out.println("username to map");
				name = in.nextLine();
				System.out.println("unitId to map");
				unitId = in.next(); 
			}
			while(!(MySQLDatabase.checkUser(name)) && !(MySQLDatabase.checkUnit(unitId)));

			User user = MySQLDatabase.getUser(name);
			user.setUnitID(unitId);
			System.out.println("map completed");
		}
		if(input.equals("/mapalarmunit")){
			Scanner in = new Scanner(System.in);
			int alarmId;
			String unitId;

			do{
				System.out.println("alarmId to map");
				alarmId = in.nextInt();
				System.out.println("unitId to map");
				unitId = in.next();
			}
			while(!(MySQLDatabase.checkAlarm(alarmId)) && !(MySQLDatabase.checkUnit(unitId)));

			Event alarm = MySQLDatabase.getAlarm(alarmId);
			alarm.setUnitID(unitId);
			System.out.println("map completed");
		}
		if(input.equals("/sendcontacts")){
			ArrayList<Contact> hej = MySQLDatabase.getAllContacts();			
			HashMap<String, String> testing = Association.getUserIpAssociations();

			Object[] users;
			Object[] userip;

			users = testing.keySet().toArray();
			userip = testing.values().toArray();

			for (int i = 0; i < users.length; i++) {
				Sender.sendContacts(hej, userip[i].toString());
			}

		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String countDownFromEvent = (String) arg1;
		if(countDownFromEvent.equals("0")){
			System.out.println("Klienten svarade inte på uppdraget inom utsatt tid!");
		}
	}
}
