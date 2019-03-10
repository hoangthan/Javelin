package login;

import fileHandler.TokenFile;
import javafx.scene.control.CheckBox;
import model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

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
        String email, password;
        boolean isRemember;
        email = edtEmail.getText().trim();
        password = edtPassword.getText().trim();
        isRemember = ckbRemember.isSelected();

        User user = new User(email,password);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean checkToken(){
        String token = new TokenFile().getToken();

        return true;
    }


}
