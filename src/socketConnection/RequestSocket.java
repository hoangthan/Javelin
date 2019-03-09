package socketConnection;

import model.User;

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

    public static String sendUser(User user) throws IOException {
        Socket socket = new Socket("localhost",2308);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        DataInputStream dataInputStream =  new DataInputStream(socket.getInputStream());

        outputStream.writeObject(user);  //Send user
        String token = dataInputStream.readUTF(); //Get response from server

        inputStream.close();
        outputStream.close();
        dataInputStream.close();
        socket.close();
        return token;
    }

}
