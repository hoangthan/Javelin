package socketConnection;

import model.User;

import java.io.*;
import java.net.Socket;
import java.util.Spliterator;

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

    public static String sendUser(User user)  {
        try {
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
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error at Request socket.");
            return null;
        }
    }

    public static boolean sendToken(String token){
        try {
            //Initialize sokect connection at port 2307.
            Socket socket =  new Socket("localhost", 2307);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream =  new DataInputStream(socket.getInputStream());

            //Send token by the code: 106, then get the result response
            outputStream.writeUTF(token+":"+"106");
            boolean res = inputStream.readBoolean();

            //Close all the socket connection.
            outputStream.close();
            inputStream.close();
            socket.close();

            return res;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error at sendToken in RequestSocket");
            return false;
        }
    }

}
