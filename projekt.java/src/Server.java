
public class Server {
	private static CommandThread ct;
	private static ConnectionController cc;
	private static Send send;

	public Server() throws Exception{
		System.out.println("Server created.");
		//Association ac = new Association();
		//Association.addAdressAssociation("130.236.226.154");
		Database.addUser(new User("test", "password"));

		cc = new ConnectionController();
		cc.start();
		ct = new CommandThread();
		ct.start();
		send = new Send();
		
	}

	public void runServer() { 

	}

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		server.runServer();
	}
}
