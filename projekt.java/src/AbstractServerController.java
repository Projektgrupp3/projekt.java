import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;


public abstract class AbstractServerController {
	private BufferedReader in;
	private boolean listen = true;
	private MultiServerView view;
	private MultiServerThread mst;
	private String username;
	HashMap<String, String> userID = new HashMap<String, String>();

	public AbstractServerController(BufferedReader in){
		this.in = in;
		userID.put("David", "semst");
		userID.put("Emil", "best");
	}

	public AbstractServerController(BufferedReader in, MultiServerView msv){
		this.in = in;
		this.view = msv;
		userID.put("David", "semst");
		userID.put("Emil", "best");
	}
	public AbstractServerController(BufferedReader in, MultiServerThread mst){
		this.in = in;
		this.mst = mst;
		userID.put("David", "semst");
		userID.put("Emil", "best");
	}

	public void listen() throws IOException {
		try{
			while(listen){
				String input;
				System.out.println("Lyssnar");
				if((input = in.readLine()) != ""){
					if(input.equals(null)){
						listen = false;
					}
					System.out.println("Input from Client: "+input);
					evaluate(input);
				}
			}
		} catch(NullPointerException e){
			listen = false;
		}
	}

	public boolean authenticate() throws IOException{	
		
		String user;
		String pass;		
		user = in.readLine();
		pass = in.readLine();
		if(Database.checkUser(user) && Database.getUserPass(user).equals(pass)){
			username = user;
			view.send("Authenticated");
			return true;
		}
		else{
			view.send("Authentication failed");
			return false;	
		}
//		if(userID.containsKey(user) && userID.get(user).equals(pass)){
//			username = user;
//			view.send("Authenticated");
//			return true;
//		}
//		else{
//			view.send("Authentication failed");
//			return false;	
//		}
	}

	public void evaluate(String input){
		view.send(username+": "+input);
	}

	public boolean isListen() {
		return listen;
	}

	public void setListen(boolean listen) {
		this.listen = listen;
	}

	public MultiServerView getView() {
		return view;
	}

	public void setView(MultiServerView view) {
		this.view = view;
	}

}
