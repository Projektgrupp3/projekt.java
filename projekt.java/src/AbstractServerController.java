import java.io.BufferedReader;
import java.io.IOException;


public abstract class AbstractServerController {
	private BufferedReader in;
	private boolean listen = true;
	private MultiServerView view;
	private MultiServerThread mst;
	
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

	public void evaluate(String input){
		//String inputSplit[] = input.split("_", 4);
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
