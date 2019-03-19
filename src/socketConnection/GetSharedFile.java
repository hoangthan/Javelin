package socketConnection;

import fileHandler.TokenFile;
import model.FileObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class GetSharedFile {
    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    public ArrayList<FileObject> getList(){
        String token = new TokenFile().getToken();
        ArrayList<FileObject> list = new ArrayList<>();
        try {
            socket = new Socket("localhost",2310);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream =  new DataOutputStream(socket.getOutputStream());
            System.out.println("Token in client: "+token);
            dataOutputStream.writeUTF(token);             //Send the token to authentication
            boolean auth = dataInputStream.readBoolean(); //Get the authenticaction result.

            if(!auth){
                return null;                             //If token is not valid, notice for client.
            }

            dataOutputStream.writeUTF("shared");     //Send the message, get all the file is shared with me.
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            list = (ArrayList<FileObject>) objectInputStream.readObject();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
