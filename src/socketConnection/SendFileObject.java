package socketConnection;

import fileHandler.TokenFile;
import model.FileObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SendFileObject {

    static String token = new TokenFile().getToken();

    public static ArrayList<FileObject> getResult(FileObject file){
        ArrayList<FileObject> list = null;
        try {
            Socket socket = new Socket("localhost",2309);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            DataInputStream dataInputStream =  new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //Send token and get UserID
            dataOutputStream.writeUTF(token);
            long userID = dataInputStream.readLong();
            //Set userID for file to request
            file.setOwnerID(userID);
            objectOutputStream.writeObject(file);
            //Get result list of file return from server
            list = (ArrayList<FileObject>)objectInputStream.readObject();

            //Close all socket connection
            objectInputStream.close();
            objectOutputStream.close();
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  list;
    }

}
