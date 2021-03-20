package application;

import backend.Database;
import frontend.controllers.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ContactBookApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader =new FXMLLoader(getClass().getResource(
                "/fxml/mainWindow.fxml"));
        Parent root  = (Parent) loader.load();
        MainWindowController controller = loader.<MainWindowController>getController();
        controller.setPath("target/testDatabase.json");
        controller.setDatabase();
        controller.setContactsTable();
        Scene scene = new Scene(root);
        Button b = new Button();
        Scene sc = new Scene(b, 100,200);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
