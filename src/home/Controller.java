package home;

import fileHandler.TokenFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import socketConnection.GetSharedFile;
import socketConnection.TransferFile;
import socketConnection.SendFileObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    Button btnMyDrive;

    Node nodeTemp1;
    Image image;
    ImageView imageView;
    VBox vBox;
    Text text;

    double x,y;
    private String token = new TokenFile().getToken();
    private int[] checked = null;
    private ArrayList<FileObject> list = null;

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
        initContextMenu();
    }

    @FXML
    public void myDrive(MouseEvent mouseEvent){
            progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);  //Running effect
            FileObject fileObject = new FileObject();
            fileObject.setParent("root");
            list = new SendFileObject().getResult(fileObject);
            checked = new int[list.size()];

            paneRoot.setCenter(nodeTemp1);
            paneContent.getChildren().clear();

            Image image;
            for(int i = 0; i< list.size();i++){
                final int j = i;
                image = getImageByExtension(getFileExtension(list.get(i).getName()));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(60);
                imageView.setFitHeight(60);
                Text text = new Text(handleNameFile(list.get(i).getName()));
                VBox vBox = new VBox();
                vBox.getChildren().addAll(imageView,text);
                paneContent.getChildren().add(vBox);
                vBox.setOnMouseClicked(e -> {
                    if(checked[j]==0){
                        checked[j] = 1;
                        vBox.setBackground(new Background(new BackgroundFill(Color.web("#b2bec3"), CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                    else{
                        checked[j] = 0;
                        vBox.setBackground(Background.EMPTY);
                    }
                    vBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                        @Override
                        public void handle(ContextMenuEvent event) {
                            contextMenu.show(paneRoot, event.getScreenX(), event.getScreenY());
                        }
                    });
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

    @FXML
    public void getShareWithMe(MouseEvent mouseEvent){
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);  //Running effect
        ArrayList<FileObject> list;
        FileObject fileObject = new FileObject();
        fileObject.setParent("root");
        list = new GetSharedFile().getList();

        paneRoot.setCenter(nodeTemp1);
        paneContent.getChildren().clear();

        Image image = null;
        for(FileObject object: list){
            image = getImageByExtension(getFileExtension(object.getName()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            Text text = new Text(handleNameFile(object.getName()));
            VBox vBox = new VBox();
            vBox.getChildren().addAll(imageView,text);
            paneContent.getChildren().add(vBox);
            vBox.setOnMouseClicked(e -> {
                vBox.setBackground(new Background(new BackgroundFill(Color.web("#b2bec3"), CornerRadii.EMPTY, Insets.EMPTY)));
            });
        }
        progressBar.setProgress(1);
    }

    private void uploadFile(List<File> files){
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        boolean result = new TransferFile().upload(files);
        progressBar.setProgress(1);
        showAlert(result);
    }

    private void showAlert(boolean result){
        if(!result){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upload fail");
            alert.setHeaderText("Some thing went wrong. Try again.");
            alert.setContentText("-Check your internet connection.\n-Check your account.\n-Server can be error.");
            alert.showAndWait();
            new TokenFile().setToken(null);
            goToLogin();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upload file success.");
            alert.setHeaderText("Congratulations!!!");
            alert.setContentText("Your file has been uploaded completely.");
            alert.show();
        }
    }

    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnUpload.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handleNameFile(String nameFile){
        return nameFile.substring(0,9)+"...";
    }

    private String getFileExtension(String fileName){
        int index =fileName.lastIndexOf(".");
        return fileName.substring(index+1);
    }

    private Image getImageByExtension(String extension){
        try{
            return image = new Image("images/"+extension+".png");
        }catch (Exception e){
            return image = new Image("images/file.png");
        }
    }

    public void initContextMenu(){
        MenuItem downnload = new MenuItem("⇩ Download");
        MenuItem delete = new MenuItem("❌ Delete");
        MenuItem rename = new MenuItem("\uD83D\uDCD6 Rename");
        MenuItem share = new MenuItem("\uD83D\uDC65 Share");
        MenuItem cancel = new MenuItem(" Cancel ");
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(downnload,delete,rename,share,cancel);
        downnload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                downloadEvent();
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteEvent();
            }
        });
        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                renameEvent();
            }
        });
        share.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shareEvent();
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Do nothings.
            }
        });
    }

    private void shareEvent() {
    }

    private void renameEvent() {
    }

    private void deleteEvent() {
    }

    private void downloadEvent() {
         ArrayList<FileObject> downloadList = new ArrayList<FileObject>();
         for(int i=0;i<list.size();i++){
             if(checked[i]==1)
                 System.out.println("File id: "+list.get(i).getFileID());
                 downloadList.add(list.get(i));
         }
         boolean result = new TransferFile().download(list);
         if(result) showMessage("Download file successful.");
         else showMessage("Some things went wrong. Try again.");
    }

    private void showMessage(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}