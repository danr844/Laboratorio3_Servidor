package clientServer;

import java.util.Scanner;

public class ClientManager {
	
	public static void main(String[] args) {
		
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Por favor ingrese el número de clientes que se desea conectar: ");
	    
	    int numClientes = Integer.parseInt(myObj.nextLine());  // Read user input
		
		for(int i =0; i<numClientes; i++) {
			Thread thread = new ClientThread();
			thread.run();
		}
		
	}

}
