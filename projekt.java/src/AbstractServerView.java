import java.io.PrintWriter;


public abstract class AbstractServerView {
	private PrintWriter out;
	
	public AbstractServerView(PrintWriter out){
		this.out = out;
	}
	
	public void send(String output){
		out.println(output);
		System.out.println("Sending to Client: "+output);
	}

}
