package com.calculator.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class CalculatorView extends VBox {
    public TextField operationInput = new TextField();
    GridPane buttons = new GridPane();
    ObservableList<HBox> resultsList = FXCollections.observableArrayList();

    public CalculatorView() {
        //Input for operations & displaying results
        ListView<HBox> resultsListView = new ListView<>(resultsList);
        resultsListView.setFocusTraversable(false);
        Platform.runLater(() -> operationInput.requestFocus());
        operationInput.textProperty().addListener((obs, oldText, newText) -> {
            Platform.runLater(() -> {
                operationInput.positionCaret(operationInput.getText().length());
            });
        });
        operationInput.textProperty().addListener((obs, oldText, newText) -> {
            String clean = newText.replace(" ", "");
            operationInput.setText(clean.replace("*", " × ")
                    .replace("/", " ÷ ")
                    .replace("mod", " mod ")
                    .replace("+", " + ")
                    .replace("-", " - ")
                    .replace("%", " % ")
                    .replace("×", " × ")
                    .replace("÷", " ÷ ")
                    .replace("=", " = "));
        });
        VBox header = new VBox(resultsListView, operationInput);
        VBox.setVgrow(resultsListView, Priority.ALWAYS);
        header.getStyleClass().add("header");

        //Buttons
        buttons.setHgap(5);
        buttons.setVgap(5);
        String[] buttonsLabels = {"mod", "(", ")", "DEL", "AC", "7", "8", "9", "÷", "√",
            "4", "5", "6", "×", "X²", "1", "2", "3", "-", "=", "0", ".", "%", "+"
        };
        for (int i = 0; i < buttonsLabels.length; i ++) {
            Button button = new Button(buttonsLabels[i]);
            button.setFocusTraversable(false);
            GridPane.setHgrow(button, Priority.ALWAYS);
            GridPane.setVgrow(button, Priority.ALWAYS);
            int col = i % 5;
            int row = i / 5;
            if (buttonsLabels[i].equals("=")) {
                buttons.add(button, col, row, 1, 2);
                button.getStyleClass().add("special-button");
                button.setPrefSize(120, 100);
                button.setDefaultButton(true);
            }
            else if (buttonsLabels[i].equals("DEL") || buttonsLabels[i].equals("AC")) {
                buttons.add(button, col, row);
                button.getStyleClass().add("special-button");
                button.setPrefSize(120, 50);
            }
            else {
                buttons.add(button, col, row);
                button.setPrefSize(120, 50);
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

    public void setOnButtonClick (EventHandler<ActionEvent> manager) {
        for (var node : buttons.getChildren()) {
            if (node instanceof Button button)
                button.setOnAction(manager);
        }
    }

    public void setDisplayText (String text) {
        operationInput.setText(text);
    }

    public String getDisplayText () {
        return operationInput.getText();
    }

    public void addHistory(String expression, String result) {
        Label expressionLabel = new Label(expression);
        expressionLabel.setPrefWidth(300);
        expressionLabel.setTextFill(Color.WHITE);
        Label resultLabel = new Label(result);
        resultLabel.setPrefWidth(200);
        resultLabel.setTextFill(Color.WHITE);
        resultLabel.setAlignment(Pos.CENTER_RIGHT);
        Label equalLabel = new Label(" = ");
        equalLabel.setTextFill(Color.WHITE);
        HBox line = new HBox(20, expressionLabel, equalLabel, resultLabel);
        resultsList.add(0, line);
    }
}