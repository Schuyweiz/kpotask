package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public enum Alerts {

    INFO(Alert.AlertType.INFORMATION, "Take notice of the following message:","-fx-background-image: url(/images/im2.jpg);" +
            "-fx-background-size: 100% 100%", "Information dialogue."),
    WARNING(Alert.AlertType.WARNING, "Oups, something went wrong:","-fx-background-image: url(/images/im3.jpg);" +
            "-fx-background-size: 100% 100%", "Warning dialogue."),
    ERROR(Alert.AlertType.ERROR, "Error","/images/im2.jpg", "Error dialogue.");

    private Alert alert;
    private Alerts(Alert.AlertType alertType, String header, String imagePath, String title){
        this.alert = new Alert(alertType);
        this.alert.setTitle(title);
        this.alert.setHeaderText(header);

        Button temp = new Button();
        temp.setStyle(imagePath);
        temp.setMinWidth(100);
        temp.setMinHeight(100);
        this.alert.setHeight(300);
        this.alert.setWidth(300);

        this.alert.getDialogPane().setGraphic(temp);
    }

    public void display(String message){
        this.alert.setContentText(message);
        alert.showAndWait();
    }
}
