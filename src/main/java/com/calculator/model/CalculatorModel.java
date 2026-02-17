package com.calculator.model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorModel {

    public String toCalculableExpression(String displayExpression) {
        return displayExpression
                .replace(" ", "")
                .replace("×", "*")
                .replace("÷", "/")
                .replace("√", "sqrt")
                .replace("²", "^2")
                .replace("%", "/100")
                .replace("mod", "%");
    }

    public double calculate(String displayExpression) throws CalculationException {
        if (displayExpression == null || displayExpression.trim().isEmpty()) {
            throw new CalculationException("Expression vide");
        }

        String calculableExpr = toCalculableExpression(displayExpression);

        try {
            Expression e = new ExpressionBuilder(calculableExpr).build();
            double result = e.evaluate();

            if (Double.isInfinite(result)) {
                throw new CalculationException("Division par zéro");
            }
            if (Double.isNaN(result)) {
                throw new CalculationException("Résultat invalide");
            }

            return result;
        } catch (IllegalArgumentException e) {
            throw new CalculationException("Expression invalide: " + e.getMessage());
        } catch (Exception e) {
            throw new CalculationException("Erreur de calcul: " + e.getMessage());
        }
    }

    public static class CalculationException extends Exception {
        public CalculationException(String message) {
            super(message);
        }
    }
}
