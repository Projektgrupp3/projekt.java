/**
 * Klass som skapar en server och lägger till en testanvändare
 * User: test
 * Pass: password
 * @author Bauwie
 *
 */
public class Server {
	private static CommandThread ct;
	private static ConnectionController cc;
	private static Send send;

	public Server() throws Exception{
		System.out.println("Server created.");

		Database.addUser(new User("test", "password"));

		cc = new ConnectionController();
		cc.start();
		ct = new CommandThread();
		ct.start();
		send = new Send();
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server();
	}
}
