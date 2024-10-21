package com.example.act2_sisa2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private HelloController helloController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Actividad 2 - SISA");
        stage.setScene(scene);
        stage.show();

        helloController = fxmlLoader.getController();
    }

    @Override
    public void stop() {
        if (helloController != null) {
            helloController.borrarTablaUsuarios();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}