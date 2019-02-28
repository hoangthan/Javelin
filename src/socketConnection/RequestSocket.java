package socketConnection;

import Model.User;

import java.io.*;
import java.net.Socket;

public class RequestSocket {

    public static Socket socket = null;
    DataInputStream inputStream = null ;
    DataOutputStream outputStream = null;


    public boolean requestCodeActive(String email) throws IOException {
        Boolean result;
        socket = new Socket("localhost",2307);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF(email+":"+"101"); //Set 101 is the flag request active code.
        result =  inputStream.readBoolean();        //Get the result response from server if request success
        System.out.println("The result is: "+ result);
        //Close all the connection, return resource
        inputStream.close();
        outputStream.close();
        socket.close();

        return result;
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
