package ru.gavrilov;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.gavrilov.common.GuiForm;
import ru.gavrilov.controllers.DosFormController;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GuiForm<AnchorPane, DosFormController> loader = new GuiForm("dos_form.fxml");
        AnchorPane root = loader.getParent();
        DosFormController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Dos атака");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> closeApp());
        primaryStage.show();
    }

    private void closeApp() {
        Platform.exit();
        System.exit(0);
    }
}
