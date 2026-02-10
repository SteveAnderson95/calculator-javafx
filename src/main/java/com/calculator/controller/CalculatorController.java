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
        if (input.equals("=")) {
            String expression = view.getDisplayText();
            double result = model.calculate(expression);
            if (Double.isNaN(result)) {
                view.setDisplayText("Erreur");
            }
            else {
                view.setDisplayText(String.valueOf(result));
                String historyLine = String.format("%-50s %10s %20s", expression, "=", result);
                view.addHistory(historyLine);
            }
        }
        else if (input.equals("C")) {
            view.setDisplayText("");
        }
        else {
            view.setDisplayText(view.getDisplayText() + input);
        }
    }
}
