package clientServer;

// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.Scanner;



// Server class
public class Server
{
	public static void main(String[] args) throws IOException
	{
		
		
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Ingresa que tipo de archivo deseas transmitir 100MB o 250MB");
	    System.out.println("Escribe 1 para 100Mb o 2 para 250MB: ");
	    
	    int tipoArchivo = Integer.parseInt(myObj.nextLine());  // Read user input
		
		// server is listening on port 5000
		ServerSocket ss = new ServerSocket(5000);
		
		// running infinite loop for getting
		// client request
		while (true)
		{
			Socket s = null;
			
			try
			{
				// socket object to receive incoming client requests
				s = ss.accept();
				
				System.out.println("A new client is connected : " + s);
								
				System.out.println("Assigning new thread for this client");

				// create a new thread object
				Thread t = new ClientHandler(s, tipoArchivo);

				// Invoking the start() method
				t.start();
				
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}

// ClientHandler class
class ClientHandler extends Thread
{ 
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    
	final Socket s;
	public final static int SOCKET_PORT = 5000;  
	public final static String FILE_TO_SEND1 = "/home/infracom/archivos/server/100mb.bin"; 
	public final static String FILE_TO_SEND2 = "/home/infracom/archivos/server/250mb.bin"; 
	
	final int tipoArchivo;
	

	// Constructor
	public ClientHandler(Socket s, int t)
	{
		this.s = s;
		this.tipoArchivo = t;
	}

	@Override
	public void run()
	{
		
		while (true)
		{
			try {
				
			  File myFile = new File ("z://server//servidor.txt"); 
			  String aEnviar = FILE_TO_SEND1;
			  
			  if(tipoArchivo == 2) {
				  myFile = new File (FILE_TO_SEND2); 
				  aEnviar = FILE_TO_SEND2;
			  }
			  
		            		  	
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          
	                  
	          // Creates a BufferedInputStreamand saves its argument, the input stream in
	          bis = new BufferedInputStream(fis);
	          // Reads bytes from this byte-input stream into the specified byte array
	          bis.read(mybytearray,0,mybytearray.length);
	          
	          // Returns an output stream for this socket
	          os = s.getOutputStream();
	          
	          System.out.println("Sending " + aEnviar + "(" + mybytearray.length + " bytes)");
	          
	          MessageDigest md5Digest = MessageDigest.getInstance("MD5");
	          
	          //Get the checksum
	          String checksum = getFileChecksum(md5Digest, myFile);
	          
	          
	          os.write(checksum.getBytes(),0, checksum.length());
	          
	          // Writes len bytes from the specified byte array starting at offset off to this output stream
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	                       
	          System.out.println("Done.");
							
			  System.out.println("Connection closed");
			}catch(Exception e) {

			}
				
			  finally {
		          
				  try {
					  if (bis != null) bis.close();
			          if (os != null) os.close();
			          if (s!=null) s.close();
				  }catch(IOException e) {
					  e.printStackTrace();
				  }
		      }
		}
	}
	
	
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException
	{
	    //Get file input stream for reading the file content
	    FileInputStream fis = new FileInputStream(file);
	     
	    //Create byte array to read data in chunks
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0; 
	      
	    //Read file data and update in message digest
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	     
	    //close the stream; We don't need it now.
	    fis.close();
	     
	    //Get the hash's bytes
	    byte[] bytes = digest.digest();
	     
	    //This bytes[] has bytes in decimal format;
	    //Convert it to hexadecimal format
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    //return complete hash
	   return sb.toString();
	}
		

}
