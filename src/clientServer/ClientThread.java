package clientServer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread{
	
	public final static int SOCKET_PORT = 5000; 
	public final static String SERVER = "127.0.0.1";  // localhost

	public final static int FILE_SIZE = 20022386; // file size temporary hard coded
	                                               // should bigger than the file to be downloaded
	
	public void run() {
		
		String
	       FILE_TO_RECEIVED = "c:/Source/source" +Math.random()+ ".pdf";
		
		int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket sock = null;
	    try {
	      sock = new Socket(SERVER, SOCKET_PORT);
	      System.out.println("Connecting...");

	      // receive file
	      byte [] mybytearray  = new byte [FILE_SIZE];
	      InputStream is = sock.getInputStream();
	      fos = new FileOutputStream(FILE_TO_RECEIVED);
	      bos = new BufferedOutputStream(fos);
	      bytesRead = is.read(mybytearray,0,mybytearray.length);
	      current = bytesRead;

	      do {
	         bytesRead =
	            is.read(mybytearray, current, (mybytearray.length-current));
	         if(bytesRead >= 0) current += bytesRead;
	      } while(bytesRead > -1);

	      bos.write(mybytearray, 0 , current);
	      bos.flush();
	      System.out.println("File " + FILE_TO_RECEIVED
	          + " downloaded (" + current + " bytes read)");
	    } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally {
	      try {
	      if (fos != null) fos.close();
	      if (bos != null) bos.close();
	      if (sock != null) sock.close();
	      }catch(IOException e) {
	    	  e.printStackTrace();
	      }
	    }
		
		
	}

}
