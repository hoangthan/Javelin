package socketConnection;

import model.User;

import java.io.*;
import java.net.Socket;

public class RequestSocket {

    public void requestCodeActive(String email) throws IOException {
        Socket socket = new Socket("localhost",2307);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF(email+":"+"1"); //Set 1 is the flag request active code.
        //Close all the connection, return resource
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    public static void sendUser(User user) throws IOException {
        Socket socket = new Socket("localhost",2308);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        //Send User
        outputStream.writeObject(user);

        inputStream.close();
        outputStream.close();
        socket.close();
    }

}
