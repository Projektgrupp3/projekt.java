import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiServer {
	public static final int LISTEN_PORT = 4545;
	private static int threadCount = 0;
	private static boolean listening = true;
	static ServerSocket serverSocket = null;
	private static Socket socket;

	public MultiServer() throws Exception{
		serverSocket =  new ServerSocket(LISTEN_PORT);
		System.out.println("Server created.");
	}

	public void runServer() throws IOException{
//		Database d = new Database();
		InputThread it = new InputThread();
		it.start();
       
//		socket.close();


		while (listening) {
			try {				
				socket =  serverSocket.accept();
				new MultiServerThread((socket)).start();

			} catch (IOException ioException) {
				ioException.printStackTrace();
				System.exit(-1);
			} 
		}
		serverSocket.close();
	}

	public static void main(String[] args) throws Exception {
		MultiServer server = new MultiServer();
		server.runServer();
		
	}

	public static void increaseCount(){
		threadCount++;
	}

	public static int getCount(){
		return threadCount;
	}
}