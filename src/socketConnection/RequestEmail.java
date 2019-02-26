package socketConnection;

import java.io.*;
import java.net.Socket;

public class RequestEmail {

    public static Socket socket;
    DataInputStream inputStream ;
    DataOutputStream outputStream;

    public RequestEmail()
    {
        try {
            socket = new Socket("localhost",2307);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestCodeActive(String email)
    {
        try {
            outputStream.writeUTF(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
