package login;

import fileHandler.TokenFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import socketConnection.RequestSocket;

public class Login extends Application {

    private Stage stage = null;
    private String token = new TokenFile().getToken();

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root;
        if(checkToken()){
            root = FXMLLoader.load(getClass().getResource("/home/Home.fxml"));
        }else{
            root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        }
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean checkToken(){
        if(token==null || token.equals("null")){
            return false;
        }
        return RequestSocket.sendToken(token);
    }



}
