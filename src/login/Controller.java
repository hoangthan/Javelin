package login;

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

    public void login(){
        String email, password;
        email = edtEmail.getText().trim();
        password = edtPassword.getText();
        User user = new User(email, password);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
