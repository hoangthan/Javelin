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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileObject;
import socketConnection.SendFile;
import socketConnection.SendFileObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @FXML
    ProgressBar progressBar;

    Node nodeTemp1;
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
        nodeTemp1 = paneRoot.getCenter();
    }

    @FXML
    public void myDrive(MouseEvent mouseEvent){
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);  //Running effect
        ArrayList<FileObject> list;
        FileObject fileObject = new FileObject();
        fileObject.setParent("root");
        list = SendFileObject.getResult(fileObject);
        paneRoot.setCenter(nodeTemp1);
        paneContent.getChildren().clear();

        Image image = new Image("images/docx.png");
        for(int i=0;i<100;i++){

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            Text text = new Text("Hello world");
            VBox vBox = new VBox();
            vBox.getChildren().addAll(imageView,text);
            paneContent.getChildren().add(vBox);
            vBox.setOnMouseClicked(e -> {
                vBox.setBackground(new Background(new BackgroundFill(Color.web("#b2bec3"), CornerRadii.EMPTY, Insets.EMPTY)));
            });
        }
        progressBar.setProgress(1);
    }

    @FXML
    public void upload(MouseEvent mouseEvent) throws IOException {
        Image image = new Image("images/addFile.jpg");
        ImageView imageView = new ImageView(image);
        paneRoot.setCenter(imageView);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) paneRoot.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Pick your file you want to upload.");
                List<File> list =  fileChooser.showOpenMultipleDialog(stage);
                uploadFile(list);
            }
        });

        Node node = paneRoot.getCenter();
        node.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getDragboard().hasFiles()){
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
        });

        node.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                List<File> files = event.getDragboard().getFiles();
                uploadFile(files);
            }
        });

    }

    private void uploadFile(List<File> files){
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        boolean result = SendFile.send(files);
        progressBar.setProgress(1);
        showAlert(result);
    }

    private void showAlert(boolean result){
        if(!result){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upload fail");
            alert.setHeaderText("Some thing went wrong. Try again.");
            alert.setContentText("-Check your internet connection.\n-Check your account.\n-Server can be error.");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upload file success.");
            alert.setHeaderText("Congratulations!!!");
            alert.setContentText("Your file has been uploaded completely.");
            alert.show();
        }
    }

}