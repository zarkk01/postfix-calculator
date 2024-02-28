package gr.aueb.dmst.postfixieCalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("/calculatorScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),292,302);
        stage.setScene(scene);
        stage.setTitle("Postfixie Calculator");
        stage.setResizable(false);
        stage.show();
    }
}
