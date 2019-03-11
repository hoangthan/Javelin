package Home;

import fileHandler.TokenFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.FileObject;
import socketConnection.SendFileObject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    double x,y;
    private String token = new TokenFile().getToken();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void myDrive(MouseEvent mouseEvent){
        ArrayList<FileObject> list;
        FileObject fileObject = new FileObject();
        fileObject.setParent("root");
        list = SendFileObject.getResult(fileObject);
        System.out.println(list);
    }

}
