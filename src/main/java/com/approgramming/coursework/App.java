package com.approgramming.coursework;

import com.approgramming.coursework.authorization.Authorization;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Authorization authWindow = new Authorization();
        authWindow.display(window);
    }
}