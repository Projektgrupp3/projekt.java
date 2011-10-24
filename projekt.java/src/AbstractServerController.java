import java.io.BufferedReader;
import java.io.IOException;


public abstract class AbstractServerController {
	private BufferedReader in;
	private boolean listen;
	private MultiServerView view;

	public AbstractServerController(BufferedReader in){
		this.in = in;
	}
	
	public AbstractServerController(BufferedReader in, MultiServerView msv){
		this.in = in;
		this.view = msv;
	}

	public void listen() throws IOException{
		while(listen){
			String input;
			if((input = in.readLine()) != null){
				System.out.println("Input from Client: "+input);
				evaluate(input);
			}
		}
	}

	public void evaluate(String input){
		String inputSplit[] = input.split("_", 4);
		view.send(inputSplit[0]+" "+inputSplit[1]);
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
