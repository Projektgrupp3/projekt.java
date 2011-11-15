import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionController implements Runnable {


	public static final int LISTEN_PORT = 4444;
	private static boolean listening = true;
	ServerSocket serverSocket = null;
	private Socket socket;

	public ConnectionController(){
		try {
			serverSocket = new ServerSocket(LISTEN_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (listening) {
			try {				
				socket =  serverSocket.accept();
				new ConnectionThread((socket)).run();

			} catch (IOException ioException) {
				ioException.printStackTrace();
				System.exit(-1);
			} 
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}