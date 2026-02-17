package com.calculator;

import com.calculator.controller.CalculatorController;
import com.calculator.model.CalculatorModel;
import com.calculator.view.CalculatorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final String APP_TITLE = "Calculatrice";
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 600;
    private static final String STYLESHEET = "/style.css";

    @Override
    public void start(Stage primaryStage) {
        // Initialisation du pattern MVC
        CalculatorView view = new CalculatorView();
        CalculatorModel model = new CalculatorModel();
        new CalculatorController(view, model);

        // Configuration de la scène
        Scene scene = new Scene(view, WINDOW_WIDTH, WINDOW_HEIGHT);
        loadStylesheet(scene);

        // Configuration de la fenêtre
        configureStage(primaryStage, scene);
        primaryStage.show();
    }

    private void loadStylesheet(Scene scene) {
        try {
            String stylesheetUrl = getClass().getResource(STYLESHEET).toExternalForm();
            scene.getStylesheets().add(stylesheetUrl);
        } catch (NullPointerException e) {
            System.err.println("Erreur: Fichier CSS non trouvé - " + STYLESHEET);
        }
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
