import java.net.*;
import java.io.*;

import javax.net.ssl.SSLServerSocketFactory;

public class MultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        /*	SSL Serversocket
         * 
         *	SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
          	SSLServerSocket sslserversocket =
                    (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
            SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

         */
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server created");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        while (listening)
	    new MultiServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
}