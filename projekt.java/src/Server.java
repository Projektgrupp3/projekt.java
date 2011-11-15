
public class Server {
	private static CommandThread ct;
	private static ConnectionController cc;
	private static Send send;

	public Server() throws Exception{
		System.out.println("Server created.");
		
		cc = new ConnectionController();
		cc.run();
		ct = new CommandThread();
		ct.run();
		send = new Send();
		User test = new User("test", "password");
		Database.addUser(test);
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server();
	}
}
