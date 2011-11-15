 
public class Server {
	private static CommandThread it;
	private static ConnectionController cc;

	public Server() throws Exception{
		System.out.println("Server created.");
		it = new CommandThread();
		cc = new ConnectionController();
		//User test = new User("test", "password");
		//Database.addUser(test);
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		it.run();
		cc.run();
	}
}
