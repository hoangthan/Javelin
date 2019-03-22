package login;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import controller.Validate;
import fileHandler.TokenFile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import socketConnection.RequestSocket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXPasswordField edtPassword;

    @FXML
    private JFXTextField edtEmail;

    @FXML
    private JFXCheckBox ckbRemember;

    @FXML
    Button btnRegister;

    @FXML
    Button btnLogin;

    @FXML
    private JFXProgressBar pgbLogin;

    private String token = null;
    private double x,y;

    @FXML
    void mouseDraged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void mousePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void minimize(MouseEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void close(MouseEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }



    public synchronized void login(){
        String email, password;
        boolean isRemember;
        email = edtEmail.getText().trim();
        password = edtPassword.getText().trim();
        isRemember = ckbRemember.isSelected();

        if(Validate.checkEmail(email)){
            if(Validate.checkPassword(password)){

            }else {
                showDialog("Password is not valid. Please to check again.");
                return;
            }
        }else {
            showDialog("Email is not valid. Please to check again.");
            return;
        }

        User user = new User(email,password);
        user.setRequestCode(104);
        String token = RequestSocket.sendUser(user);

        if(token==null){
            showDialog("Email or password is not true. Please to check again.");
            new TokenFile().setToken(token);
        }else {
            goToHome();
            new TokenFile().setToken(token);
        }

    }

    private void goToHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home/Home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEnterPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER)  {
            login();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");
    }

    private void showDialog(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Please to enter valid field.");
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void goToRegister(MouseEvent mouseEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Register/Register.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRecoverPassword(MouseEvent mouseEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/recoverPassword/RecoverPassword.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
