package Home;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import fileHandler.TokenFile;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileObject;
import socketConnection.SendFileObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TilePane paneContent;
    @FXML
    ContextMenu contextMenu;
    @FXML
    Button btnUpload;
    @FXML
    BorderPane paneRoot;
    @FXML
    AnchorPane paneCenter;

    Image image;
    ImageView imageView;
    VBox vBox;
    Text text;

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

        paneContent.getChildren().clear();

        for(int i=0;i<100;i++){
            image = new Image("images/docx.png");
            imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            text = new Text("Hello world");
            vBox = new VBox();
            vBox.getChildren().addAll(imageView,text);
            paneContent.getChildren().add(vBox);

            vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    vBox.setBackground(new Background(new BackgroundFill(Color.web("#b2bec3"), CornerRadii.EMPTY, Insets.EMPTY)));
                }
            });
        }
    }

    @FXML
    public void upload(MouseEvent mouseEvent) throws IOException {
        /*Stage stage = (Stage) paneRoot.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick your file you want to upload.");
        File file =  fileChooser.showOpenDialog(stage);*/
        Image image = new Image("images/baseline-add_circle_outline-24px.svg");
        ImageView imageView = new ImageView(image);

    }

}