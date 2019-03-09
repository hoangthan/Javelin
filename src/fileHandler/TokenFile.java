package fileHandler;

import java.io.*;

public class TokenFile {

    public boolean setToken(String token){
        try {
            File file = new File("token.txt");
            FileWriter fileWriter = new FileWriter(file,false);
            fileWriter.write(token);
            fileWriter.close();
        }catch (Exception e){
            return false;
        }
        return  true;
    }

    public  String getToken() {
        try {
            String token = null;
            File file = new File("token.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            token = br.readLine();
            br.close();
            return token;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
