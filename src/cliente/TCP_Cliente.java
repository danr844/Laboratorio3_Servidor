package cliente;

public class TCP_Cliente {
	

	public static void main(String[] args) throws Exception{
		//Initialize socket
		int numConcurrencia = 3;
		
		for(int i=0; i<numConcurrencia; i++) {
			Thread thread = new Thread(new TCP_Cliente_Threads("Thread", i));
			thread.start();
		}
	}
	
}
