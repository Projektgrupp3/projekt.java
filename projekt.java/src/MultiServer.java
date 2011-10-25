import java.net.*;
import java.io.*;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class MultiServer {
	public static final int LISTEN_PORT = 8080;
	private static int threadCount = 0;
	ServerSocket serverSocket = null;
	private static Socket socket;
	
	public MultiServer() throws Exception{
		serverSocket =  new ServerSocket(LISTEN_PORT);
		System.err.println("Could not listen to port: "+LISTEN_PORT);
	}

	public void runServer() throws IOException{
		while (true) {
			try {				
				System.err.println("Waiting for client..");
				socket =  serverSocket.accept();
				new MultiServerThread((socket)).start();
				
			} catch (IOException ioException) {
				ioException.printStackTrace();
				System.exit(-1);
			} 
			socket.close();
		}
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