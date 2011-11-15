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
	}

	public AbstractServerController(BufferedReader in, MultiServerView msv){
		this(in);
		this.view = msv;
	}
	public AbstractServerController(BufferedReader in, MultiServerThread mst){
		this(in);
		this.mst = mst;
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

	public void evaluate(String input){
		if(input.equals("newuser")){
			MySQLDatabase.addUser(new User("kungen2", "kaffeflicka"));
		}
		else
		view.send(username+": "+input);
	}

	public boolean isListening() {
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

	public MultiServerThread getMst() {
		return mst;
	}

	public void setMst(MultiServerThread mst) {
		this.mst = mst;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

}
