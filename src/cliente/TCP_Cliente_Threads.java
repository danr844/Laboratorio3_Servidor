package cliente;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCP_Cliente_Threads implements Runnable{
	
	//ExecuterThread

	private String nombreArchivo;
	private int numeroThread;
	
	public TCP_Cliente_Threads(String pNombre, int pNum) {
		nombreArchivo = pNombre;
		numeroThread = pNum;
	}
	
	public void run() {
		try {
			
			Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);
			byte[] contents = new byte[10000];
			//Initialize the FileOutputStream to the output file's full path.
			String archivoAEnviar = "Z:\\client\\" + nombreArchivo + numeroThread + ".txt";
			FileOutputStream fos = new FileOutputStream(archivoAEnviar);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			InputStream is = socket.getInputStream();
			//No of bytes read in one read() call
			int bytesRead = 0;
			while((bytesRead=is.read(contents))!=-1)
				bos.write(contents, 0, bytesRead);
			bos.flush();
			socket.close();
			System.out.println("File from Thread " + numeroThread + " send succesfully");
			
		}catch(Exception e) {
			
		}
		
	}
	
	


}
