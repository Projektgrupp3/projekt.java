import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Klass som lyssnar på LISTEN_PORT för att se om det är någon
 * som vill ansluta. Hittar den någon som vill ansluta skapar 
 * den en ny tråd för hantering av anslutning.
 * @author Bauwie
 *
 */
public class ConnectionController extends Thread implements Runnable {
	
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
			System.out.println("lyssnar");
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