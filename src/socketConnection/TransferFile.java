package socketConnection;

import fileHandler.TokenFile;
import model.FileObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TransferFile {

    String folderPath = "C:\\Javelin";
    long fileSize;

    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;
    ObjectOutputStream objectOutputStream = null;

    public boolean upload(List<File> files) {
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


    public boolean download(ArrayList<FileObject> list){
        String token = new TokenFile().getToken();
        try {
            socket = new Socket("localhost",2311);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream =  new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(token);             //Send the token to authentication
            boolean auth = dataInputStream.readBoolean(); //Get the authenticaction result.
            if(!auth){
                return false;                             //If token is not valid, notice for client.
            }
            dataOutputStream.writeUTF("download");     //Send the message : I want to upload file.
            objectOutputStream.writeObject(list);          //Send list file will download
            for(FileObject fileObject : list){
                fileSize = dataInputStream.readLong();     //Get the size of file
                if(fileSize==0) continue;
                saveFile(fileObject.getName(), fileSize);
            }
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean saveFile(String fileName, long fileSize) {
        try{
            File folder = new File(folderPath);
            if(!folder.exists()) folder.mkdirs();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            FileOutputStream fos = new FileOutputStream(folderPath + "/" + fileName);
            byte[] buffer = new byte[4096];

            long filesize = fileSize; // Send file size in separate msg
            int read = 0;
            int totalRead = 0;
            long remaining = filesize;
            while((read = dis.read(buffer, 0, getMin(buffer.length,remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }
            fos.close();
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private int getMin(int a, long b){
        if(a<b) return a;
        return (int)b;
    }

}
