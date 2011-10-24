import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread {
	private Socket socket = null;
	private MultiServerController msc;
	private MultiServerView msv;

	public MultiServerThread(Socket socket) {
		super("MultiServerThread");
		this.socket = socket;
		System.out.println("MultiServerThread created");
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
			
			msc.listen();
			
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}