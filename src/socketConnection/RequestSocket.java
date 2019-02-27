package socketConnection;

import java.io.*;
import java.net.Socket;

public class RequestSocket {

    public static Socket socket = null;
    DataInputStream inputStream = null ;
    DataOutputStream outputStream = null;


    public void requestCodeActive(String email) throws IOException {
        socket = new Socket("localhost",2307);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF(email+":"+"1"); //Set 1 is the flag request active code.

        inputStream.close();
        outputStream.close();
        socket.close();
    }

}
