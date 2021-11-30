package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerClient implements Runnable{

	Socket socket;
	
	
	public ServerClient(Socket socket) {
		super();
		this.socket = socket;
	}



	@Override
	public void run() {
		DataInputStream dataInputStream = null;
		ObjectInputStream objectInputStream = null;
		
		DataInputStream dataInputStream2 = null;
		ObjectInputStream objectInputStream2 = null;

		
		DataOutputStream dataOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		
		DataOutputStream dataOutputStream2 = null;
		ObjectOutputStream objectOutputStream2 = null;
		
		Socket socket2 = null;
		String instruction;
		Object object = null;
		Object object2 = null;

		
		try {
			socket2 = new Socket("localhost", 9001);
			dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOutputStream = new DataOutputStream(socket2.getOutputStream());
			
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream2 = new ObjectInputStream(socket2.getInputStream());
			objectOutputStream2 = new ObjectOutputStream(socket2.getOutputStream());

			
			while(true) {
			instruction = dataInputStream.readUTF();
			dataOutputStream.writeUTF(instruction);
			
			object = objectInputStream.readObject();
			objectOutputStream2.writeObject(object); 
			
			object2 = objectInputStream2.readObject();
			objectOutputStream.writeObject(object);
			
			//inputStream.read();
			}
		} catch (IOException e) {
			Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            releaseResourcesClient(dataInputStream, dataOutputStream, socket);
        }
	}

	
	private static void releaseResourcesClient(DataInputStream inputStream, DataOutputStream outputStream, Socket socket) {
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
