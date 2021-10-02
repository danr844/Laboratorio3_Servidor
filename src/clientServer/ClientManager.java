package clientServer;

public class ClientManager {
	
	public static void main(String[] args) {
		
		for(int i =0; i<25; i++) {
			Thread thread = new ClientThread();
			thread.run();
		}
		
	}

}
