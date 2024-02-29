package gr.aueb.dmst.postfixieCalculator.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The GuiApplication class is the entry point for the JavaFX application.
 * It loads the FXML for the calculator scene and sets up the stage.
 * The size is fixed so Postfixie can be in a small window used for calculations.
 * No need to capture whole user's screen.
 */
public class GuiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML for the calculator scene
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("/calculatorScene.fxml"));

        // Create a new scene with the loaded FXML and set the dimensions
        Scene scene = new Scene(fxmlLoader.load(),292,332);

        // Set the scene for the stage
        stage.setScene(scene);

        // Set the title for the stage
        stage.setTitle("Postfixie Calculator");

        // Make the stage non-resizable
        stage.setResizable(false);

        // Show the stage
        stage.show();
    }
}