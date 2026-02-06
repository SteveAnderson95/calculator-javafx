package com.calculator.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CalculatorView extends VBox {

    public CalculatorView() {
        //Input for operations & displaying results
        ObservableList<String> resultsList = FXCollections.observableArrayList();
        ListView<String> resultsListView = new ListView<>(resultsList);
        TextField operationInput = new TextField();
        VBox header = new VBox(resultsListView, operationInput);
        VBox.setVgrow(resultsListView, Priority.ALWAYS);
        header.getStyleClass().add("header");

        //Buttons
        GridPane buttons = new GridPane();
        buttons.setHgap(5);
        buttons.setVgap(5);
        String[] buttonsLabels = {"C", "(", ")", "mod", "n", "7", "8", "9", "/", "√",
            "4", "5", "6", "x", "X²", "1", "2", "3", "-", "=", "0", ".", "%", "+"
        };
        for (int i = 0; i < buttonsLabels.length; i ++) {
            Button button = new Button(buttonsLabels[i]);
            GridPane.setHgrow(button, Priority.ALWAYS);
            GridPane.setVgrow(button, Priority.ALWAYS);
            int col = i % 5;
            int row = i / 5;
            if (!buttonsLabels[i].equals("=")) {
                buttons.add(button, col, row);
                button.setPrefSize(120, 50);
            }
            else {
                buttons.add(button, col, row, 1, 2);
                button.getStyleClass().add("equal-button");
                button.setPrefSize(120, 100);
            }
        }
        //Main Container
        VBox container = new VBox(15, header, buttons);
        container.setMaxSize(600, Double.MAX_VALUE);
        container.setPadding(new Insets(10, 0, 10, 0));
        VBox.setVgrow(header, Priority.ALWAYS);
        VBox.setVgrow(container, Priority.ALWAYS);

        this.getChildren().add(container);
        this.setAlignment(Pos.CENTER);
    }
}