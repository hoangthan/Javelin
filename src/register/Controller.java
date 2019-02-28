package register;

import Model.User;
import controller.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    void register(MouseEvent event)
    {
        if(Validate.checkEmail(edtEmail.getText().toLowerCase().trim()))
        {
            if(edtPassword.getText().equals(edtRePassword.getText()))
            {
                if(Validate.checkPassword(edtPassword.getText()))
                {
                    if(edtCode.getText().length()==7){
                        lbNoti.setVisible(false);
                        pgbLogin.setVisible(true);
                        User user = new User(edtEmail.getText().trim(),edtPassword.getText().trim(), Long.parseLong(edtCode.getText().trim()));
                        try {
                            RequestSocket.sendUser(user);
                        } catch (IOException e) {
                            e.printStackTrace();
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

    @FXML
    public void sendActiveCode(MouseEvent event)
    {
        if(!Validate.checkEmail(edtEmail.getText().trim()))
        {
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
            requestSocket.requestCodeActive(edtEmail.getText().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize somethings
    }
}
