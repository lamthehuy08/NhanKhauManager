package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Quản Lý Nhân Khẩu");
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}