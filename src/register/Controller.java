package register;

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

import java.net.URL;
import java.util.ResourceBundle;

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
                    if(edtCode.getText().trim()=="123"){
                        lbNoti.setVisible(false);
                        pgbLogin.setVisible(true);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize somethings
    }
}
