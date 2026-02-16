package com.calculator.controller;

import com.calculator.model.CalculatorModel;
import com.calculator.view.CalculatorView;
import javafx.scene.control.Button;

public class CalculatorController {

    private CalculatorView view;
    private CalculatorModel model;

    public CalculatorController(CalculatorView view, CalculatorModel model) {
        this.view = view;
        this.model = model;

        this.view.setOnButtonClick(e -> {
            Button clickedButton = (Button) e.getSource();
            String buttonText = clickedButton.getText();

            manageLogic(buttonText);
        });
    }

    public void manageLogic(String input) {
        String expression = view.getDisplayText().replace("*", " × ")
                .replace("/", " ÷ ");
        double result = model.calculate(expression);
        if (input.equals("=")) {
            if (Double.isNaN(result)) {
                view.setDisplayText("Erreur");
            }
            else {
                view.setDisplayText(String.valueOf(result));
                String historyLine = String.format("%-50s %10s %20s", expression, "=", result);
                view.addHistory(expression, String.valueOf(result));
            }
        }
        else if (input.equals("AC")) {
            view.setDisplayText("");
        }
        else if (input.equals("DEL")) {
            view.setDisplayText(expression.substring(0, expression.length()-1));
        }
        else if (input.equals("√")) {
            view.setDisplayText(expression + "√(");
        }
        else if (input.equals("X²")) {
            view.setDisplayText(expression + "²");
        }
        else {
            view.setDisplayText(expression + input);
        }
    }
}
