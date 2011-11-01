import java.util.Scanner;


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
		evalutate(cmd);
		}
		while(true);
		

	}
	public void evalutate(String input){
		if(input.equals("/createuser")){
			User u = new User();
			u.createUser();
		}
	}
}
