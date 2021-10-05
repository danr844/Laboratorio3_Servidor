package clientServer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class ClientThread extends Thread{

	public final static int SOCKET_PORT = 5000; 
	public final static String SERVER = "192.168.159.128";  // localhost

	public final static int FILE_SIZE = 300000000; // file size temporary hard coded
	// should bigger than the file to be downloaded

	int numeroCliente;
	int cantidadConexiones;

	public ClientThread(int numcliente, int cantCon) {

		numeroCliente = numcliente;	
		cantidadConexiones = cantCon;
	}

	public void run() {

		String
		FILE_TO_RECEIVED = "/home/infracom/archivos/ArchivosRecibidos/" +numeroCliente+ "-Prueba-" + cantidadConexiones +".txt";

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting cliente with id "+ numeroCliente + "...");

			// receive file
			byte [] mybytearray  = new byte [FILE_SIZE];

			// Writes len bytes from the specified byte array starting at offset off to this output stream
			InputStream is = sock.getInputStream();
			String currentPath = new java.io.File(".").getCanonicalPath();

			fos = new FileOutputStream(currentPath+"//archivos//archivo.txt");
			bos = new BufferedOutputStream(fos);


			// Reads up to len bytes of data from the input stream into an array of bytes
			bytesRead = is.read(mybytearray,0,mybytearray.length);
			current = bytesRead;

			do {
				bytesRead =
						is.read(mybytearray, current, (mybytearray.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(bytesRead > -1);


			bos.write(mybytearray, 32 , current);


			bos.flush();


			String string = new String(mybytearray);

			String string1 = string.substring(0, 32);

			System.out.println(string1.trim()); //md5 mandado


			String string2 = string.substring(32, string.length()); // md5 a calcular

			string2 = string2.trim();
			
			byte[] bytesOfMessage = string2.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
						
			StringBuilder sb = new StringBuilder();
		    for(int i=0; i< thedigest.length ;i++)
		    {
		        sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16).substring(1));
		    }

			string2 = sb.toString();
			
			System.out.println(string2);
			
			
			if(string2.equals(string1)) {
				System.out.println("El md5 enviado es igual al md5 calculado");
			}
			else {
				System.out.println("El md5 enviado no es igual al md5 calculado");
			}
			

			System.out.println("File " + FILE_TO_RECEIVED
					+ " downloaded (" + current + " bytes read)");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
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
	
	
	private static String getFileChecksum(MessageDigest digest) throws IOException
	{
      
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
