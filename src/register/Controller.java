package register;

import fileHandler.TokenFile;
import model.User;
import controller.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socketConnection.RequestSocket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    double x,y;

    @FXML
    Text lbNoti;
    @FXML
    TextField edtEmail;
    @FXML
    TextField edtPassword;
    @FXML
    TextField edtRePassword;
    @FXML
    ProgressBar pgbLogin;
    @FXML
    TextField edtCode;
    @FXML
    Text lbSendCode;

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

    @FXML
    void register(MouseEvent event) throws IOException {
        if(Validate.checkEmail(edtEmail.getText().toLowerCase().trim()))
        {
            if(edtPassword.getText().equals(edtRePassword.getText()))
            {
                if(Validate.checkPassword(edtPassword.getText()))
                {
                    if(edtCode.getText().length()==7){
                        lbNoti.setVisible(false);
                        pgbLogin.setVisible(true);
                        User user = new User(edtEmail.getText().trim(),edtPassword.getText(), Long.parseLong(edtCode.getText().trim()));

                        try {
                            lbNoti.setVisible(false);
                            pgbLogin.setVisible(true);
                            lbSendCode.setDisable(true);
                            sendUser(user);
                        } catch (Exception e) {
                            e.printStackTrace();
                            lbNoti.setText("Network is error.");
                            pgbLogin.setVisible(false);
                            lbNoti.setVisible(true);
                        }
                    }else {
                        lbNoti.setText("Active code is invalid.");
                    }
                }else {
                    lbNoti.setText("Password is too weak.");
                }
            }else {
                lbNoti.setText("Password is not match.");
            }
        }else {
            lbNoti.setText("Email is not valid");
            lbNoti.setVisible(true);
            lbNoti.setStyle("-fx-text-inner-color: #0f9c58;");
        }
    }

    private void sendUser(User user)  {
        user.setRequestCode(103);
        String token = null;
        try {
            token = RequestSocket.sendUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The token: "+token);
        if(token!=null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Congratulations!!!");
            alert.setContentText("Login to use Javelin.");
            edtCode.setText("");
            edtPassword.setText("");
            edtRePassword.setText("");
            edtEmail.setText("");
            alert.showAndWait();
            pgbLogin.setVisible(false);
            //Save token for the next login
            new TokenFile().setToken(token);
        }else {
            lbNoti.setText("Register fail.");
            lbNoti.setVisible(true);
        }
    }

    @FXML
    public void sendActiveCode(MouseEvent event)
    {
        boolean isSuccess;
        pgbLogin.setVisible(true);
        if(!Validate.checkEmail(edtEmail.getText().trim()))
        {
            pgbLogin.setVisible(false);
            lbNoti.setText("Email is not valid.");
            lbNoti.setVisible(true);
            return;
        }
        RequestSocket requestSocket = new RequestSocket();
        lbNoti.setVisible(false);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                lbSendCode.setDisable(false);
            }
        };
        lbSendCode.setDisable(true);
        Timer timer = new Timer();
        timer.schedule(timerTask,120000);

        try {
           isSuccess =  requestSocket.requestCodeActive(edtEmail.getText().trim());
           Thread.sleep(1000);
           pgbLogin.setVisible(false);
           if(isSuccess){
                lbNoti.setText("Please to check your email.");
                lbNoti.setVisible(true);
           }else {
               lbNoti.setText("This email is already registed.");
               lbNoti.setVisible(true);
           }
        } catch (IOException e) {
            lbNoti.setText("Network is error.");
            lbNoti.setVisible(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize somethings
    }
}
