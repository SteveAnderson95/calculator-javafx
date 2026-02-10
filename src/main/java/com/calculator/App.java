package com.calculator;

import com.calculator.controller.CalculatorController;
import com.calculator.model.CalculatorModel;
import com.calculator.view.CalculatorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage primaryStage) {
        CalculatorView view = new CalculatorView();
        CalculatorModel model = new CalculatorModel();
        new CalculatorController(view, model);

        Scene scene = new Scene(view);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main( String[] args )
    {
        launch(args);
    }
}
