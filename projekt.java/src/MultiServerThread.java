import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServerThread extends Thread {
	private Socket socket = null;
	private MultiServerController msc;
	private MultiServerView msv;
	private boolean authenticated = false;

	public MultiServerThread(Socket socket) {
		super("MultiServerThread");
		this.socket = socket;
		MultiServer.increaseCount();
		System.out.println("Client #"+MultiServer.getCount()+" connected");
		System.out.println("New thread created");
	}

	public void run() {

		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));

			msc = new MultiServerController(in);
			msv = new MultiServerView(out);
			msc.setView(msv);
			authenticated = msc.authenticate();
			
			while(authenticated){
				msc.listen();
			}
			
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
