import java.io.BufferedReader;
import java.io.IOException;


public abstract class AbstractServerController {
	private BufferedReader in;
	private boolean listen = true;
	private MultiServerView view;
	private MultiServerThread mst;
	String[] userPass= { "test","hej"};

	public AbstractServerController(BufferedReader in){
		this.in = in;
	}

	public AbstractServerController(BufferedReader in, MultiServerView msv){
		this.in = in;
		this.view = msv;
	}
	public AbstractServerController(BufferedReader in, MultiServerThread mst){
		this.in = in;
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

	public boolean authenticate() throws IOException{
		String user;
		String pass;		
		user = in.readLine();
		pass = in.readLine();		
		String userSplit[] = user.split("_", 2);
		String passSplit[] = user.split("_", 2);
		
		if((userSplit[1].equalsIgnoreCase(userPass[0])) && passSplit[1].equalsIgnoreCase(userPass[1]))
			return true;
		else
			return false;
	}

	public void evaluate(String input){


		//view.send(inputSplit[0]+" "+inputSplit[1]);
		view.send(input);
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
