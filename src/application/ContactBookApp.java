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

    private static final String DB_PATH = "testDatabase.json";
    private Database db;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader =new FXMLLoader(getClass().getResource(
                "/fxml/mainWindow.fxml"));
        Parent root  = (Parent) loader.load();
        MainWindowController controller = loader.<MainWindowController>getController();
        this.db = new Database(DB_PATH);
        controller.setDatabase(this.db);
        controller.setContactsTable();
        controller.setStage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            getClass().getResource("/css/root.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Phonebook");
        stage.show();
    }

    @Override
    public void stop(){
        this.db.saveData();
    }

    public static void main(String[] args) {
        launch();
    }
}
