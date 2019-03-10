package login;

import fileHandler.TokenFile;
import javafx.scene.control.CheckBox;
import model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import socketConnection.RequestSocket;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextField edtEmail;
    @FXML
    TextField edtPassword;
    @FXML
    CheckBox ckbRemember;

    public void login(){

        if(checkToken()){
            //Chuyen sang man hinh Home, dong man hinh hien tai.
        }
        String email, password;
        boolean isRemember;
        email = edtEmail.getText().trim();
        password = edtPassword.getText().trim();
        isRemember = ckbRemember.isSelected();
        User user = new User(email,password);
        user.setRequestCode(104);
        String token = RequestSocket.sendUser(user);
        if(token==null){
            //Thong bao sai email hoac mat khau
            /*
            * Viet code o day
            * */
        }else{
            if(isRemember==true){
                new TokenFile().setToken(token);
            }else new TokenFile().setToken(null);
            //Chuyen sang man hinh home.
            /*
            * Viet code o day
            * */
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean checkToken(){
        String token = new TokenFile().getToken();
        if(token==null) return false;
        boolean result = RequestSocket.sendToken(token);
        return result;
    }

}
