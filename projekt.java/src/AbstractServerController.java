import java.io.BufferedReader;
import java.io.IOException;


public abstract class AbstractServerController {
	private BufferedReader in;
	private boolean listen;

	public AbstractServerController(BufferedReader in){
		this.in = in;
	}

	public void listen() throws IOException{
		while(listen){
			String input;
			if((input = in.readLine()) != null){
				evaluate(input);
			}
		}
	}

	public void evaluate(String input){
		String inputSplit[] = input.split("_", 4);
		System.out.println("Input from Client: "+inputSplit[0]+" "+inputSplit[1]);
	}

}
