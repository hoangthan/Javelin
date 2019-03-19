package socketConnection;

import fileHandler.TokenFile;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SendFile {

    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    public boolean send(List<File> files) {
        String token = new TokenFile().getToken();
        try {
            socket = new Socket("localhost",2310);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream =  new DataOutputStream(socket.getOutputStream());
            System.out.println("Token in client: "+token);
            dataOutputStream.writeUTF(token);             //Send the token to authentication
            boolean auth = dataInputStream.readBoolean(); //Get the authenticaction result.

            if(!auth){
                return false;                             //If token is not valid, notice for client.
            }

            dataOutputStream.writeUTF("upload");     //Send the message : I want to upload file.
            dataOutputStream.writeInt(files.size()  );      //Send number of file to server.
            for(File file: files){
                dataOutputStream.writeUTF(file.getName());  //Send the name of file before
                dataOutputStream.writeLong(file.length()); //Send the length of file
                System.out.println("Size of file "+file.length());
                sendFile(socket, file);           //Send the file
            }
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private  void sendFile(Socket socket, File file) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];

        int read;
        while ((read=fis.read(buffer)) > 0) {
            dos.write(buffer,0,read);
        }
        fis.close();
        dos.close();
    }


}
