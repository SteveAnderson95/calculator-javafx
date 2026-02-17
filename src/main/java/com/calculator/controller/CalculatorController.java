package com.calculator.controller;

import com.calculator.model.CalculatorModel;
import com.calculator.model.CalculatorModel.CalculationException;
import com.calculator.view.CalculatorView;
import javafx.scene.control.Button;

public class CalculatorController {

    private final CalculatorView view;
    private final CalculatorModel model;

    public CalculatorController(CalculatorView view, CalculatorModel model) {
        this.view = view;
        this.model = model;

        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        view.setOnButtonClick(event -> {
            Button clickedButton = (Button) event.getSource();
            String buttonText = clickedButton.getText();
            handleButtonClick(buttonText);
        });
    }

    private void handleButtonClick(String input) {
        switch (input) {
            case "=" -> calculateResult();
            case "AC" -> clearDisplay();
            case "DEL" -> deleteLastCharacter();
            case "√" -> appendToDisplay("√(");
            case "X²" -> appendToDisplay("²");
            default -> appendToDisplay(input);
        }
    }

    private void calculateResult() {
        String expression = view.getDisplayText();

        if (expression.isEmpty()) {
            return;
        }

        try {
            double result = model.calculate(expression);
            String resultStr = formatResult(result);

            // Ajouter à l'historique
            view.addHistory(expression, resultStr);

            // Afficher le résultat
            view.setDisplayText(resultStr);

        } catch (CalculationException e) {
            view.setDisplayText("Erreur: " + e.getMessage());
        }
    }

    private String formatResult(double result) {
        // Si c'est un entier, afficher sans décimales
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return String.valueOf((long) result);
        }
        // Sinon, limiter à 10 décimales max
        return String.format("%.10f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    private void clearDisplay() {
        view.setDisplayText("");
    }

    private void deleteLastCharacter() {
        String current = view.getDisplayText();
        if (!current.isEmpty()) {
            // Supprimer le dernier caractère en tenant compte des espaces
            view.setDisplayText(current.substring(0, Math.max(0, current.length() - 1)));
        }
    }

    private void appendToDisplay(String text) {
        String current = view.getDisplayText();
        view.setDisplayText(current + text);
    }
}
