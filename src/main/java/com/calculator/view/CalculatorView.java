package com.calculator.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        VBox header = new VBox(10, resultsListView, operationInput);

        //Buttons
        GridPane buttons = new GridPane();
        buttons.setHgap(3);
        buttons.setVgap(4);
        String[] buttonsLabels = {"C", "(", ")", "mod", "n", "7", "8", "9", "/", "√",
            "4", "5", "6", "x", "X²", "1", "2", "3", "-", "=", "0", ".", "%", "+"
        };
        for (int i = 0; i < buttonsLabels.length; i ++) {
            Button button = new Button(buttonsLabels[i]);
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setHgrow(button, Priority.ALWAYS);
            GridPane.setVgrow(button, Priority.ALWAYS);
            int col = i % 5;
            int row = i / 5;
            if (!buttonsLabels[i].equals("=")) {
                buttons.add(button, col, row);
            }
            else {
                buttons.add(button, col, row, 1, 2);
            }
        }

        //Container
        VBox container = new VBox(15, header, buttons);
        container.setMaxSize(600, Double.MAX_VALUE);
        VBox.setVgrow(header, Priority.ALWAYS);

        this.getChildren().add(container);
        this.setAlignment(Pos.CENTER);
    }
}