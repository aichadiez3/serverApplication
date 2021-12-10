package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadCreation implements Runnable{

	ServerSocket serverSocket;
	
	public ThreadCreation(ServerSocket serverSocket) {
		super();
		this.serverSocket = serverSocket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
            //This executes when we have a client
				try {
					while (true) {
		            	 Socket socket = serverSocket.accept();
			             new Thread(new ServerToDB(socket)).start();	
			             
		            }
				} catch (IOException e) {
					System.out.println("Client finished");
				}
	}
}